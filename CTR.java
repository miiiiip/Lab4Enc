public class CTR {

    public static String encrypt(String input, String key, String IV) {
        int lengthRemainder = input.length() % 5;
        int runs = input.length() / 5;

        //If there is a remainder, add 1 to the number of runs
        if (lengthRemainder > 0) {
            runs++;
        }
        String stream = "";
        for (int index = 0; index < runs; index++) {
            char[] tempChar = (String.valueOf(IV) + Support.binaryIV(index)).toCharArray();
            stream = stream + String.valueOf(Support.encryptBinary(tempChar, key.toCharArray()));
        }
        char[] result = new char[input.length()];
        result = Support.addToKey(stream.toCharArray(), Support.charToBinary(input.toCharArray()));
        return String.valueOf(result);
    }

    public static String decrypt(String input, String key, String IV) {
        int lengthRemainder = input.length() % 35;
        int runs = input.length() / 35;

        //If there is a remainder, add 1 to the number of runs
        if (lengthRemainder > 0) {
            runs++;
        }
        String stream = "";
        for (int index = 0; index < runs; index++) {
            char[] tempChar = (String.valueOf(IV) + Support.binaryIV(index)).toCharArray();
            stream = stream + String.valueOf(Support.encryptBinary(tempChar, key.toCharArray()));
        }
        char[] result = new char[input.length()];
        result = Support.addToKey(stream.toCharArray(), input.toCharArray());
        String res = "";
        String strRep = "";
        for (int j = 0; j < result.length; j++) {
            strRep = strRep + result[j];
            if (strRep.length() == 7) {
                int decimal = Integer.parseInt(strRep, 2);
                res = res + (char) decimal;
                strRep = "";
            }
        }
        return res;
    }
}
