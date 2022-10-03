import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ByteCodePrintDemo {
    public static final int COLUMN_NUMBER = 8;
    private static String SEMI_COLUMN_SPACE = "  ";
    private static String SIGN_SPACE = "  ";
    private static String BYTES_SPACE = "    ";
    private static String pathToClass = "";

    public static void main(String[] args) {
        try {
//            pathToClass = args[0];
            pathToClass = "e:/TMP/Hello-1.class";
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("He-He-He!!! Enter some path to class-file........");
            e.printStackTrace();
        }

        byte[] byteCodeArray = readFileToByteArray(pathToClass);

        if (byteCodeArray == null) {
            return;
        }

        ByteCodeAnalizer byteCodeAnalizer = new ByteCodeAnalizer(byteCodeArray);
        byteCodeAnalizer.printConstantArray();

        System.out.println("");
        printHeader();
        printByteHexArray(byteCodeArray);
        System.out.println("");
        System.out.println("");

        byte number = 14;
        printConstantByte(number, byteCodeAnalizer, byteCodeArray);
    }

    public static void printConstantByte(byte num, ByteCodeAnalizer byteCodeAnalizer, byte[] byteCodeArray) {
        byte number = (byte) (num - 1);
        int position = byteCodeAnalizer.constantArray[number].address;
        byte type = byteCodeAnalizer.constantArray[number].type;

        System.out.printf("%-22s", byteCodeAnalizer.getConstantTypeName(type));
        System.out.printf(" length=%2s   ", byteCodeAnalizer.constantArray[number].lenthg);
        System.out.printf("position = %-2s", position);
        System.out.println("");

        for (int i = position; i < (position + byteCodeAnalizer.constantArray[number].lenthg); i++) {
            String str1 = String.format("%2s", Integer.toHexString(byteCodeArray[i] & 0xFF)).replace(' ', '0');
            System.out.print(str1 + " ");
        }
    }

    private static byte[] readFileToByteArray (String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printByteHexArray (byte[] b) {
        AtomicInteger index = new AtomicInteger();
        IntStream.range(0, b.length).forEach(i->{
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%2s", Integer.toHexString(b[i] & 0xFF)).replace(' ', '0'));
            if(index.get() % 2 != 0){
                sb.append(SEMI_COLUMN_SPACE);
                sb.append(SIGN_SPACE);
                sb.append(SEMI_COLUMN_SPACE);
            }
            if(index.get() != 0 && (index.get() + 1) % (COLUMN_NUMBER*2) == 0){
                sb.append("\n");
            }
            System.out.print(sb.toString());
            index.getAndIncrement();
        });
    }

    private static void printHeader () {
        System.out.print(SEMI_COLUMN_SPACE + 0);
        IntStream.range(1, COLUMN_NUMBER).forEach(i->{
            String number = String.format("%4d", i).substring(4-SIGN_SPACE.length(), 4);
            System.out.print(SEMI_COLUMN_SPACE + BYTES_SPACE + SEMI_COLUMN_SPACE + number);
        });
        System.out.println("");
    }


}
