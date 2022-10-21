public class OFB {

    public static String encrypt(String input, String key, String IV) {
        String stream = "";
        int runs = input.length() / 5;
        char[] result = new char[input.length()];
        int lengthRemainder = input.length() % 5;

        //If there is a remainder, add 1 to the number of runs
        if (lengthRemainder > 0) {
            runs++;
        }

        //Create the encryption stream by encrypting the IV with the key, character by character, using the result of
        //the first character for the following and so on, appending the result to the stream with each step
        for (int index = 0; index < runs; index++) {
            String encIV = String.valueOf(Support.encryptBinary(IV.toCharArray(), key.toCharArray()));
            stream = stream + encIV;
            IV = encIV;
            
        }

        //XOR the plaintext with their corresponding stream blocks using the encryption stream just created previously
        result = Support.addToKey(stream.toCharArray(), Support.charToBinary(input.toCharArray()));
        return String.valueOf(result);
    }

    public static String decrypt(String input, String key, String IV) {

        String stream = "";
        int runs = input.length() / 35;
        int lengthRemainder = input.length() % 35;

        //If there is a remainder, add 1 to the number of runs
        if (lengthRemainder > 0) {
            runs++;
        }

        //Create the encryption stream by encrypting the IV with the key, character by character, using the result of
        //the first character for the following and so on, appending the result to the stream with each step. No need
        //to invert the cipher.
        for (int index = 0; index < runs; index++) {
            String encIV = String.valueOf(Support.encryptBinary(IV.toCharArray(), key.toCharArray()));
            stream = stream + encIV;
            IV = encIV;
        }

        //XOR the ciphertext with their corresponding stream blocks using the de-encryption stream just created previously
        char[] result = new char[input.length()];
        result = Support.addToKey(stream.toCharArray(), input.toCharArray());

        //Convert to string representation
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
