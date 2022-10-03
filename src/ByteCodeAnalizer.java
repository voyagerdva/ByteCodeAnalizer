public class ByteCodeAnalizer {
    byte[] byteCodeArray;
    ConstantInfo[] constantArray;

    public ByteCodeAnalizer (byte[] byteCodeArray) {
        this.byteCodeArray = byteCodeArray;
    }

    void printConstantArray() {
        proccessAnaliz();
        System.out.println("");
        for (int i = 0; i < constantArray.length; i++) {
            printConstantInfo(constantArray[i]);
        }
    }

    public void printConstantInfo(ConstantInfo constantInfo) {
        System.out.printf("#%-2s", constantInfo.number);
        System.out.printf(" %-22s", getConstantTypeName(constantInfo.type));
        System.out.printf(" length=%2s   ", constantInfo.length);
        String str = String.format("position=%4s  ", constantInfo.address).replace(' ', ' ');
        System.out.print(str);
        System.out.printf("   type=%2s", constantInfo.type);
        System.out.println("");
    }

    public void proccessAnaliz () {
        int constantCount = readConstantCount();
        constantArray = new ConstantInfo[constantCount];
        int byteArrayOffset = 10;
        for (int i = 0; i < constantCount; i++) {
            constantArray[i] = readConstantInfo(i+1, byteArrayOffset);
            byteArrayOffset += constantArray[i].length;
        }
    }

    private ConstantInfo readConstantInfo (int number, int byteArrayOffset) {
        byte constantTypeCode = byteCodeArray[byteArrayOffset];
        int constantLength = calculateConstantLength(constantTypeCode, byteArrayOffset);
        ConstantInfo cnstInf = new ConstantInfo();
        cnstInf.length = constantLength;
        cnstInf.type = constantTypeCode;
        cnstInf.number = number;
        cnstInf.address = byteArrayOffset;
        return cnstInf;
    }

    private int calculateConstantLength (byte constantTypeCode, int byteArrayOffset) {
        int length = 0;
        switch (constantTypeCode) {
            case 7: { //CONSTANT_Class
                length = length + 3;
                System.out.println("CONSTANT_Class" + " " + "lenght=" + length);
                break;
            }
            case 9: { //CONSTANT_Fieldref
                length = length + 5;
                System.out.println("CONSTANT_Fieldref" + " " + "lenght=" + length);
                break;
            }
            case 10: { //CONSTANT_Methodref
                length = length + 5;
                System.out.println("CONSTANT_Methodref" + " " + "lenght=" + length);
                break;
            }
            case 11: { //InterfaceMethodref
                length = length + 5;
                System.out.println("CONSTANT_InterfaceMethodref" + " " + "lenght=" + length);
                break;
            }
            case 8: { //CONSTANT_String
                length = length + 3;
                System.out.println("CONSTANT_String" + " " + "lenght=" + length);
                break;
            }
            case 3: { //CONSTANT_Integer
                length = length + 5;
                System.out.println("CONSTANT_Integer" + " " + "lenght=" + length);
                break;
            }
            case 4: { //CONSTANT_Float
                length = length + 5;
                System.out.println("CONSTANT_Float" + " " + "lenght=" + length);
                break;
            }
            case 5: { //CONSTANT_Long
                length = length + 9;
                System.out.println("CONSTANT_Long" + " " + "lenght=" + length);
                break;
            }
            case 6: { //CONSTANT_Double
                length = length + 9;
                System.out.println("CONSTANT_Double" + " " + "lenght=" + length);
                break;
            }
            case 12: { //CONSTANT_NameAndType
                length = length + 5;
                System.out.println("CONSTANT_NameAndType" + " " + "lenght=" + length);
                break;
            }
            case 1: { //CONSTANT_Utf8
                int firstByte = byteCodeArray[byteArrayOffset + 1];
                int secondByte = byteCodeArray[byteArrayOffset + 2];
                int stringLength = firstByte * 256 + secondByte;
                length = length + 3 + stringLength;
                System.out.println("CONSTANT_Utf8" + " " + "lenght=" + length);
                break;
            }
            case 15: { //CONSTANT_MethodHandle
                length = length + 5;
                System.out.println("CONSTANT_MethodHandle" + " " + "lenght=" + length);
                break;
            }
            case 16: { //CONSTANT_MethodType
                length = length + 3;
                System.out.println("CONSTANT_MethodType" + " " + "lenght=" + length);
                break;
            }
            case 18: { //CONSTANT_InvokeDynamic
                length = length + 5;
                System.out.println("CONSTANT_InvokeDynamic" + " " + "lenght=" + length);
                break;
            }
            default: {
                break;
            }
        }
        return length;
    }

    public static String getConstantTypeName (byte constantTypeCode) {
        String name = "";
        switch (constantTypeCode) {
            case 7: { //CONSTANT_Class
                name = "CONSTANT_Class";
                break;
            }

            case 9: { //CONSTANT_Fieldref
                name = "CONSTANT_Fieldref";
                break;
            }

            case 10: { //CONSTANT_Methodref
                name = "CONSTANT_Methodref";
                break;
            }

            case 11: { //InterfaceMethodref
                name = "InterfaceMethodref";
                break;
            }

            case 8: { //CONSTANT_String
                name = "CONSTANT_String";
                break;
            }

            case 3: { //CONSTANT_Integer
                name = "CONSTANT_Integer";
                break;
            }

            case 4: { //CONSTANT_Float
                name = "CONSTANT_Float";
                break;
            }
            case 5: { //CONSTANT_Long
                name = "CONSTANT_Long";
                break;
            }

            case 6: { //CONSTANT_Double
                name = "CONSTANT_Double";
                break;
            }

            case 12: { //CONSTANT_NameAndType
                name = "CONSTANT_NameAndType";
                break;
            }

            case 1: { //CONSTANT_Utf8
                name = "CONSTANT_Utf8";
                break;
            }

            case 15: { //CONSTANT_MethodHandle
                name = "CONSTANT_MethodHandle";
                break;
            }

            case 16: { //CONSTANT_MethodType
                name = "CONSTANT_MethodType";
                break;
            }

            case 18: { //CONSTANT_InvokeDynamic
                name = "CONSTANT_InvokeDynamic";
                break;
            }

            default: {
                break;
            }
        }
        return name;
    }

    private int readConstantCount () {
        int firstByte = byteCodeArray[8];
        int secondByte = byteCodeArray[9];
        return firstByte * 256 + secondByte;
    }
}
