public class Support {

    public static char[] shiftRightByThree(char[] input) {
        for (int J = 0; J < 3; J++) {
            char store = input[0];
            for (int i = 0; i < input.length; i++) {
                char temp = input[(i + 1) % input.length];
                input[(i + 1) % input.length] = store;
                store = temp;
            }
        }
        return input;
    }

    public static char[] shiftLeftByThree(char[] input) {
        for (int J = 0; J < 3; J++) {
            char store = input[input.length - 1];
            for (int i = input.length - 1; i >= 0; i--) {
                char temp = input[(i - 1 + input.length) % input.length];
                input[(i - 1 + input.length) % input.length] = store;
                store = temp;
            }
        }
        return input;
    }

    // This just adds the two char arrays together using weird notation found online
    // to convert char into an integer
    // and then convert it back to char for its actual value and not ASCII value
    // (hence the + '0' at the end)
    public static char[] addToKey(char[] input, char[] key) {
        char[] resultant = new char[key.length];
        for (int i = 0; i < resultant.length; i++) {
            // This turns into something like: (char)(((1 + 0)mod 2) + '0')
            resultant[i] = (char) (((Character.getNumericValue(input[i]) + Character.getNumericValue(key[i])) % 2)
                    + '0');
        }
        return resultant;
    }

    /**
     * This function takes a number and converts it to the binary representation.
     * We assume it will take a number between 0 and 65536 so the length of the binary representation
     * is at most 16 bits long. For every representation less than 16 bits long, we add zero's to the left
     * to add "padding," so the length remains consistent. This is only used in CTR.
     * @param input Input is some number between 0 and 65536
     * @return returns the binary representation of the input with "padding" in the front
     */
    public static String binaryIV(int input) {
        String binRep = Integer.toBinaryString(input);
        while (binRep.length() < 16) {
            binRep = "0" + binRep;
        }
        return binRep;
    }

    /**
     * The binary representation of each char is put into a string until all the chars are converted.
     * Then the string gets converted into a char array of all the 1's and 0's obtained.
     * @param input Assumed to be an array of characters.
     * @return Returns the binary representation of each character in the form of a char array.
     * (Typically every chunk of 7 positions in the array will be 1 converted character)
     */
    public static char[] charToBinary(char[] input) {
        String temp = "";
        for (int i = 0; i < input.length; i++) {
            String augInput = Integer.toBinaryString((int) input[i]);
            while (augInput.length() < 7) {
                augInput = "0" + augInput;
            }
            temp = temp + augInput;
        }

        char[] res = temp.toCharArray();
        return res;
    }

    /**
     * This is the function we use to encrypt any block of five characters.
     * It starts by converting the characters to binary, then shifting the binary representation
     * by three positions to the right, then it uses binary addition to add the key to the shifted plaintext.
     * @param input The input is assumed to be a char array of any ASCII character.
     * @param key The key is assumed to be a char array of length 35, where the contents are 1's and 0's
     * @return This returns the encrypted plaintext in the form of 1's and 0's where every 1 or 0 is a position in the array.
     */
    public static char[] encrypt(char[] input, char[] key) {
        char[] charArr = charToBinary(input); // Convert to binary (Result is a char array of 1's and 0's)
        char[] plain = shiftRightByThree(charArr); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length];
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is
                                    // still a char array of 1's and 0's)
        return (enc);
    }

     /**
     * This is the function we use to encrypt any block of 35 1's and 0's. We use it to encrypt IV's.
     * It starts by converting the characters to binary, then shifting the binary representation
     * by three positions to the right, then it uses binary addition to add the key to the shifted plaintext.
     * @param input The input is assumed to be a char array of 1's and 0's.
     * @param key The key is assumed to be a char array of length 35, where the contents are 1's and 0's
     * @return This returns the encrypted plaintext in the form of 1's and 0's where every 1 or 0 is a position in the array.
     */
    public static char[] encryptBinary(char[] input, char[] key) {
        char[] plain = shiftRightByThree(input); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length];
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is
                                    // still a char array of 1's and 0's)
        return (enc);
    }

    public static char[] encryptPlusXOR(char[] input, char[] key, String vector) {
        char[] vectArr = vector.toCharArray();
        char[] charArr = charToBinary(input); // Convert to binary (Result is a char array of 1's and 0's)
        charArr = addToKey(charArr, vectArr);
        char[] plain = shiftRightByThree(charArr); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length];
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is
                                    // still a char array of 1's and 0's)
        return (enc);
    }

    public static String decrypt(char[] encryptedText, char[] key) {
        char[] deKeyd = new char[key.length];
        deKeyd = addToKey(encryptedText, key);
        deKeyd = shiftLeftByThree(deKeyd);
        String strRep = "";
        String res = "";
        for (int i = 0; i < deKeyd.length; i++) {
            strRep = strRep + deKeyd[i];
            if (strRep.length() == 7) {
                int decimal = Integer.parseInt(strRep, 2);
                res = res + (char) decimal;
                strRep = "";
            }
        }
        return res;
    }

    public static String decryptNoChars(char[] encryptedText, char[] key) {
        char[] deKeyd = new char[key.length];
        deKeyd = addToKey(encryptedText, key);
        deKeyd = shiftLeftByThree(deKeyd);
        String strRep = String.valueOf(deKeyd);
        return strRep;
    }
}
