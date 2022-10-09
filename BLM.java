import java.util.Arrays;
import java.util.Scanner;

import javax.xml.transform.Templates;

public class BLM {
    public static void main(String[] args) {
        BLM runner = new BLM();

        String TestEnc = "11100110000000000000000000000000000";
        String IV = "10001011101011001000110011110000101";
        // The scanner takes an input (for now prefereably with no spaces) and converts
        // it into a char array
        // So out methods can use it.
        System.out.println("Enter the plaintext here: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
         scan.close();
         char[] text = input.toCharArray();
        // System.out.println("This is what the input looks like as a char Array: " + Arrays.toString(text));

        

        // { 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0,
        // 0, 1, 1, 0, 0, 0, 1, 0, 0, 1 };
        // Make a string of 1's and 0's first and then convert to a char array cause
        // this is easier than typing out
        // all the commas for the key. Also there is a weird interaction when typing
        // integers into a char array
        // when trying to reconvert those integers into numbers again.
        String keyTemp = "11010101010100010101111010010010101";
        char[] key = keyTemp.toCharArray();


        System.out.println("Encrypted Text: ");
        System.out.println((runner.CFB(input, keyTemp, IV)));
        System.out.println("Decrypted Text: ");
        System.out.println(runner.decryptCFB(runner.CFB(input, keyTemp, IV), keyTemp, IV));

        //System.out.println("Nick garbage: " + Integer.toBinaryString((char) 0));
    }

    public char[] shiftRightByThree(char[] input) {
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

    public char[] shiftLeftByThree(char[] input) {
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
    public char[] addToKey(char[] input, char[] key) {
        char[] resultant = new char[key.length];
        for (int i = 0; i < resultant.length; i++) {
            // This turns into something like: (char)(((1 + 0)mod 2) + '0')
            resultant[i] = (char) (((Character.getNumericValue(input[i]) + Character.getNumericValue(key[i])) % 2)
                    + '0');
        }
        return resultant;
    }

    // This takes an array of characters and converts each character into its binary
    // representation
    // The binary representation of each char is put into a string until all the
    // chars are converted
    // then the string gets converted into a char array of all the 1's and 0's
    // obtained.
    public char[] charToBinary(char[] input) {
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

    public char[] encrypt(char[] input, char[] key) {
        char[] charArr = charToBinary(input); // Convert to binary (Result is a char array of 1's and 0's)
        char[] plain = shiftRightByThree(charArr); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length];
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is
                                    // still a char array of 1's and 0's)
        return (enc);
    }

    public char[] encryptPlusXOR(char[] input, char[] key, String vector) {
        char[] vectArr = vector.toCharArray();
        char[] charArr = charToBinary(input); // Convert to binary (Result is a char array of 1's and 0's)
        charArr = addToKey(charArr, vectArr);
        char[] plain = shiftRightByThree(charArr); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length];
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is
                                    // still a char array of 1's and 0's)
        return (enc);
    }

    public String decrypt(char[] encryptedText, char[] key) {
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

    public String decryptNoChars(char[] encryptedText, char[] key) {
        char[] deKeyd = new char[key.length];
        deKeyd = addToKey(encryptedText, key);
        deKeyd = shiftLeftByThree(deKeyd);
        String strRep = String.valueOf(deKeyd);
        return strRep;
    }

    public String ECB(String input, String key) {
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
            String tempString = new String(encrypt(splitInput[i], key.toCharArray()));
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
            System.out.println("THISONE " + Arrays.toString(remainingChars));
            String tempString = new String(encrypt(remainingChars, key.toCharArray()));
            System.out.println("bruh   " + tempString);
            result = result + tempString;
        }
        return result;
    }

    public String CBC(String input, String key, String IV) {
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
            String tempString = new String(encryptPlusXOR(splitInput[i], key.toCharArray(), IV));
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
            String tempString = new String(encryptPlusXOR(remainingChars, key.toCharArray(), IV));
            result = result + tempString;
        }
        return result;
    }

    public String CFB(String input, String key, String IV){
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
            char[] encryptIV = encrypt(IV.toCharArray(), key.toCharArray());
            char[] res = addToKey(charToBinary(splitInput[i]), encryptIV);
            String tempString = String.valueOf(res);
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
            char[] encryptIV = encrypt(IV.toCharArray(), key.toCharArray());
            
            char[] res = addToKey(charToBinary(remainingChars), encryptIV);
            result = result + String.valueOf(res);
        }
        return result;
    }

    public String decryptECB(String input, String key) {
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
            String tempString = new String(decrypt(splitInput[i], key.toCharArray()));
            result = result + tempString;
        }
        return result;
    }

    public String decryptCFB(String input, String key, String IV) {
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
            char[] encryptedIV = encrypt(IV.toCharArray(), key.toCharArray());
            char[] added = addToKey(splitInput[i], encryptedIV);
            String strRep = "";
            for (int j = 0; j < added.length; j++) {
                strRep = strRep + added[j];
                if (strRep.length() == 7) {
                    int decimal = Integer.parseInt(strRep, 2);
                    res = res + (char) decimal;
                    System.out.println("Our character maybe?: "+ strRep);
                    strRep = "";
                }
            }
            IV = String.valueOf(splitInput[i]);
        }
        return res;
    }


    public String decryptCBC(String input, String key, String IV) {
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
            String tempString = new String(decryptNoChars(splitInput[i], key.toCharArray()));
            System.out.println("The current run: "+ tempString);
            // System.out.println("The big bean: " + String.valueOf(tempString.toCharArray()) + "\n The Bigger Bean " + String.valueOf(IV.toCharArray()));
            char[] xord = addToKey(tempString.toCharArray(), IV.toCharArray());
            System.out.println("THE IV TO CHAR ARR: "+ String.valueOf(xord));
            String strRep = "";
            for (int j = 0; j < xord.length; j++) {
                strRep = strRep + xord[j];
                if (strRep.length() == 7) {
                    int decimal = Integer.parseInt(strRep, 2);
                    res = res + (char) decimal;
                    System.out.println("Our character maybe?: "+ strRep);
                    strRep = "";
                }
            }
            IV = String.valueOf(splitInput[i]);
        }

        
        return res;
    }

}