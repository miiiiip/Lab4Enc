public class CFB {
    
    public static String encrypt(String input, String key, String IV) {
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

        //Encrypt the IV and then XOR it with the first block to receive the first encrypted block, which is appended to
        //the result and then encrypted itself to be XOR'd with the next block and so on.
        for (int i = 0; i < runs; i++) {
            char[] encryptIV = Support.encryptBinary(IV.toCharArray(), key.toCharArray());
            char[] res = Support.addToKey(Support.charToBinary(splitInput[i]), encryptIV);
            String tempString = String.valueOf(res);
            result = result + tempString;
            IV = tempString;
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
            char[] encryptIV = Support.encrypt(IV.toCharArray(), key.toCharArray());
            char[] res = Support.addToKey(Support.charToBinary(remainingChars), encryptIV);
            result = result + String.valueOf(res);
        }
        return result;
    }

    public static String decrypt(String input, String key, String IV) {
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

        //Encrypt the IV with the key and XOR it with the first block to receive the first decrypted block. Encrypt the
        //result with the first decrypted block and XOR it with the next to get the next block and so on, converting to
        //its string representation with each block along the way
        for (int i = 0; i < runs; i++) {
            char[] encryptedIV = Support.encryptBinary(IV.toCharArray(), key.toCharArray());
            char[] added = Support.addToKey(splitInput[i], encryptedIV);
            String strRep = "";
            for (int j = 0; j < added.length; j++) {
                strRep = strRep + added[j];
                if (strRep.length() == 7) {
                    int decimal = Integer.parseInt(strRep, 2);
                    result = result + (char) decimal;
                    strRep = "";
                }
            }
            IV = String.valueOf(splitInput[i]);
        }
        return result;
    }
}
