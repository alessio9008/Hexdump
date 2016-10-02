package hexdump;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class HexDump {

	public HexDump() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		if (args.length < 2) {
			System.out.println("inserire due parametri : path del file e numero di byte\nesempio = C:\\hexdump.txt 16");
			System.exit(1);
		}
		Path file = getPathFile(args[0]);
		if (file == null) {
			System.out.println("il file inserito non è un file valido o non hai i permessi per leggerlo");
			System.exit(2);
		}
		int byteNumber = byteNumber(args[1]);
		if (byteNumber <= 0) {
			System.out.println("il numero di byte da leggere non è valido");
			System.exit(3);
		}
		byte[] result=readByteFile(file, byteNumber);
		if(result==null){
			System.out.println("impossibile leggere file");
			System.exit(4);
		}
		String convertHex=convertByteToHex(result);
		if(convertHex==null || "".equals(convertHex.trim())){
			System.out.println("Impossibile convertire in Hex");
			System.exit(5);
		}
		System.out.println("Il valore esadecimale è = "+convertHex);
		System.exit(0);
	}

	public static int byteNumber(String args) {
		int byteNumber = -1;
		try {
			byteNumber = Integer.parseInt(args);
		} catch (Exception e) {
			byteNumber = -1;
		}
		return byteNumber;
	}

	public static Path getPathFile(String args) {
		Path returnValue = null;
		Path tempReturnValue = null;
		try {
			tempReturnValue = Paths.get(args);
		} catch (Exception e) {
			tempReturnValue = null;
		}
		try {
			if (tempReturnValue != null && Files.exists(tempReturnValue) && !Files.isDirectory(tempReturnValue) && Files.isReadable(tempReturnValue)) {
				returnValue = tempReturnValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static byte[] readByteFile(Path file, int byteNumber) {
		byte[] returnValue = null;
		try (InputStream in = new BufferedInputStream(Files.newInputStream(file), byteNumber)) {
			byte[] tempReturnValue = new byte[byteNumber];
			int len = in.read(tempReturnValue);
			returnValue = Arrays.copyOf(tempReturnValue, len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public static String convertByteToHex(byte[] byteArr) {
		try {
			final StringBuilder builder = new StringBuilder();
			for (byte b : byteArr) {
				builder.append(String.format("%02x ", b));
			}
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
