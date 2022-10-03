// Преобразование массива байтов в строку в Java
package TestHelloWorld;

import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BytesToString {
    public static void main(String[] args) throws IOException
    {
        byte[] bytes = "Techie Delight".getBytes();

        for (int i=0; i < bytes.length; i++) {
            System.out.print(bytes[i] + " ");
        }
        System.out.println("");

        for (int i=0; i < bytes.length; i++) {
            System.out.printf("%s ", bytes[i]);
        }
        System.out.println("");

        // System.out.println(Arrays.toString(bytes));

        // Создаем строку из массива байтов без указания
        // кодировка символов
        String string = new String(bytes);
        System.out.println(string);
//----------------------------------------------------------------------------------
        System.out.println("----------------------------------------------------------------------------------");
        String example = "Hello World !!!";

        // string to byte[]
        byte[] bytes1 = example.getBytes(StandardCharsets.UTF_8);

        // print
        System.out.println("Text : " + example);
        System.out.println("Text [Byte Format] : " + Arrays.toString(bytes1));
    }
}
