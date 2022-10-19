public class CBC {

    public static String encrpytCBC(String input, String key, String IV) {
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
            String tempString = new String(Support.encryptPlusXOR(splitInput[i], key.toCharArray(), IV));
            result = result + tempString;
            IV = tempString;
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
            String tempString = new String(Support.encryptPlusXOR(remainingChars, key.toCharArray(), IV));
            result = result + tempString;
        }
        return result;
    }

    public static String decryptCBC(String input, String key, String IV) {
        String res = "";
        int runs = input.length() / 35;
        char[] fullInput = input.toCharArray();
        char[][] splitInput = new char[runs][35];
        for (int j = 0; j < runs; j++) {
            for (int i = 0; i < 35; i++) {
                splitInput[j][i] = fullInput[j * 35 + i];
            }
        }
        for (int i = 0; i < runs; i++) {
            String tempString = new String(Support.decryptNoChars(splitInput[i], key.toCharArray()));
            char[] xord = Support.addToKey(tempString.toCharArray(), IV.toCharArray());
            String strRep = "";
            for (int j = 0; j < xord.length; j++) {
                strRep = strRep + xord[j];
                if (strRep.length() == 7) {
                    int decimal = Integer.parseInt(strRep, 2);
                    res = res + (char) decimal;
                    strRep = "";
                }
            }
            IV = String.valueOf(splitInput[i]);
        }

        return res;
    }
}
