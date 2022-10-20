public class ECB {
    public static String encrypt(String input, String key) {
        String result = "";
        int runs = input.length() / 5;
        char[] fullInput = input.toCharArray();
        char[][] splitInput = new char[runs][5];
        int lengthRemainder = input.length() % 5;

        //Split the data up into an array of arrays of 5 character blocks
        for (int j = 0; j < runs; j++) {
            for (int i = 0; i < 5; i++) {
                splitInput[j][i] = fullInput[j * 5 + i];
            }
        }

        //Encrypt every block with the key to receive the resulting ciphertext blocks
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.encrypt(splitInput[i], key.toCharArray()));
            result = result + tempString;
        }

        //Pad the last block with 0s if there is a remainder, and then finish off encrypting the result
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
        String result = "";
        int runs = input.length() / 35;
        char[] fullInput = input.toCharArray();
        char[][] splitInput = new char[runs][35];

        //Split up the input into an array of arrays of 35 bit blocks
        for (int j = 0; j < runs; j++) {
            for (int i = 0; i < 35; i++) {
                splitInput[j][i] = fullInput[j * 35 + i];
            }
        }
        //For every array of 35 bit blocks, decrypt using the key and return the resulting character array
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.decrypt(splitInput[i], key.toCharArray()));
            result = result + tempString;
        }
        return result;
    }
}
