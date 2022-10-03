import java.util.Arrays;
import java.util.Scanner;

public class BLM {

    public static void main(String[] args) {
        BLM runner = new BLM();

        // The scanner takes an input (for now prefereably with no spaces) and converts it into a char array
        // So out methods can use it.
        System.out.println("Enter the plaintext here: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        scan.close();
        char[] text = input.toCharArray();
        System.out.println("This is what the input looks like as a char Array: " + Arrays.toString(text));
        
        // { 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1 };
        // Make a string of 1's and 0's first and then convert to a char array cause this is easier than typing out 
        // all the commas for the key. Also there is a weird interaction when typing integers into a char array 
        // when trying to reconvert those integers into numbers again.
        String keyTemp = "11000010110101101101001000110001001";
        char[] key = keyTemp.toCharArray();

        System.out.println("Encrypted Text: ");
        System.out.println(Arrays.toString(runner.encrypt(text, key)));
        System.out.println("Decrypted Text: ");
        System.out.println(runner.decrypt(runner.encrypt(text, key), key));
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

    // This just adds the two char arrays together using weird notation found online to convert char into an integer
    // and then convert it back to char for its actual value and not ASCII value (hence the + '0' at the end)
    public char[] addToKey(char[] input, char[] key) {
        char[] resultant = new char[key.length];
        for (int i = 0; i < resultant.length; i++) {
            // This turns into something like: (char)(((1 + 0)mod 2) + '0')
            resultant[i] =  (char)(((Character.getNumericValue(input[i]) + Character.getNumericValue(key[i])) % 2) + '0');
        }
        return resultant;
    }

    // This takes an array of characters and converts each character into its binary representation
    // The binary representation of each char is put into a string until all the chars are converted
    // then the string gets converted into a char array of all the 1's and 0's obtained.
    public char[] charToBinary(char[] input) {
        String temp = "";
        for (int i = 0; i < input.length; i++) {
            temp = temp + Integer.toBinaryString((int) input[i]);
        }
        char[] res = temp.toCharArray();
        return res;
    }

    public char[] encrypt(char[] input, char[] key) {   
        char[] charArr = charToBinary(input); // Convert to binary (Result is a char array of 1's and 0's)
        char[] plain = shiftRightByThree(charArr); // Shift binary representation to the right (circular)
        char[] enc = new char[plain.length]; 
        enc = addToKey(plain, key); // Add the shifted binary represention of the text with the key (The result is still a char array of 1's and 0's)
        return (enc);
    }

    public String decrypt(char[] encryptedText, char[] key){
        char[] deKeyd = new char[key.length];
        deKeyd = addToKey(encryptedText, key);
        deKeyd = shiftLeftByThree(deKeyd);
        String strRep = "";
        String res = "";
        System.out.println(deKeyd.length);
        for (int i = 0; i < deKeyd.length; i++) {
            strRep = strRep+deKeyd[i];
            // System.out.println(strRep);
            if(strRep.length() == 7){
                int decimal = Integer.parseInt(strRep, 2);
                System.out.println(decimal);
                System.out.println((char) decimal);
                res = res + (char) decimal;
                strRep = "";
            }
        }
        return res;
    }

}