public class CBC {
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

        //XOR and encrypt the IV with the first block, use the result to XOR and encrypt the next block, and so on
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.encryptPlusXOR(splitInput[i], key.toCharArray(), IV));
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
            String tempString = new String(Support.encryptPlusXOR(remainingChars, key.toCharArray(), IV));
            result = result + tempString;
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

        //For every array of 35 bit blocks, decrypt using the key and then XOR the IV to that key for use later.
        //From there, convert the block into its ASCII representation one character (7 bits) at a time, adding the XORD IV along the way. Return the result.
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.decryptNoChars(splitInput[i], key.toCharArray()));
            char[] xord = Support.addToKey(tempString.toCharArray(), IV.toCharArray());
            String strRep = "";
            for (int j = 0; j < xord.length; j++) {
                strRep = strRep + xord[j];
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
