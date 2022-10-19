public class OFB {

    public static String encrypt(String input, String key, String IV) {
        int lengthRemainder = input.length() % 5;
        int runs = input.length() / 5;
        if (lengthRemainder > 0) {
            runs++;
        }
        String stream = "";
        for (int index = 0; index < runs; index++) {
            stream = stream + String.valueOf(Support.encrypt(IV.toCharArray(), key.toCharArray()));
        }
        char[] result = new char[input.length()];
        result = Support.addToKey(stream.toCharArray(), Support.charToBinary(input.toCharArray()));
        return String.valueOf(result);
    }

    public static String decrypt(String input, String key, String IV) {
        int lengthRemainder = input.length() % 35;
        int runs = input.length() / 35;
        if (lengthRemainder > 0) {
            runs++;
        }
        String stream = "";
        for (int index = 0; index < runs; index++) {
            stream = stream + String.valueOf(Support.encrypt(IV.toCharArray(), key.toCharArray()));
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
