public class ECB {
    public static String encrypt(String input, String key) {
        int lengthRemainder = input.length() % 5;
        int runs = input.length() / 5;
        String result = "";
        char[] fullInput = input.toCharArray();
        char[][] splitInput = new char[runs][5];
        for (int j = 0; j < runs; j++) {
            for (int i = 0; i < 5; i++) {
                splitInput[j][i] = fullInput[j * 5 + i];
            }
        }
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.encrypt(splitInput[i], key.toCharArray()));
            result = result + tempString;
        }
        if (lengthRemainder > 0) {
            char[] remainingChars = new char[5];
            for (int i = 0; i < 5; i++) {
                if (i >= lengthRemainder) {
                    remainingChars[i] = (char) 0;
                } else {
                    int clean = (runs * 5);
                    remainingChars[i] = fullInput[clean + i];
                }

            }
            String tempString = new String(Support.encrypt(remainingChars, key.toCharArray()));
            result = result + tempString;
        }
        return result;
    }

    public static String decrypt(String input, String key) {
        int runs = input.length() / 35;
        String result = "";
        char[] fullInput = input.toCharArray();
        char[][] splitInput = new char[runs][35];
        for (int j = 0; j < runs; j++) {
            for (int i = 0; i < 35; i++) {
                splitInput[j][i] = fullInput[j * 35 + i];
            }
        }
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.decrypt(splitInput[i], key.toCharArray()));
            result = result + tempString;
        }
        return result;
    }
}
