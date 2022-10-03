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
        

        int[] key = { 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0,
                0, 1 };

        System.out.println("Encrypted Text: ");
        System.out.println(Arrays.toString(runner.encrypt(text, key)));
        System.out.println("Decrypted Text: ");
        System.out.println(runner.decrypt(runner.encrypt(text, key), key));
    }


    public int[] shiftRightByThree(int[] input) {
        for (int J = 0; J < 3; J++) {
            int store = input[0];
            for (int i = 0; i < input.length; i++) {
                int temp = input[(i + 1) % input.length];
                input[(i + 1) % input.length] = store;
                store = temp;
            }
        }
        return input;
    }

    public int[] shiftLeftByThree(int[] input) {
        for (int J = 0; J < 3; J++) {
            int store = input[input.length - 1];
            for (int i = input.length - 1; i >= 0; i--) {
                int temp = input[(i - 1 + input.length) % input.length];
                input[(i - 1 + input.length) % input.length] = store;
                store = temp;
            }
        }
        return input;
    }

    public int[] addToKey(int[] input, int[] key) {
        int[] resultant = new int[key.length];
        for (int i = 0; i < resultant.length; i++) {
            resultant[i] = (input[i] + key[i]) % 2;
        }
        return resultant;
    }

    public String charToBinary(char[] input) {
        String res = "";
        for (int i = 0; i < input.length; i++) {
            res = res + Integer.toBinaryString((int) input[i]);
        }
        return res;
    }

    public int[] encrypt(char[] input, int[] key) {
        String res = charToBinary(input);

        char[] charArr = res.toCharArray();
        int[] plain = new int[charArr.length];
        for (int i = 0; i < charArr.length; i++) {
            plain[i] = charArr[i];
        }
        plain = shiftRightByThree(plain);
        int[] enc = new int[plain.length];
        enc = addToKey(plain, key);
        return (enc);
    }

    public String decrypt(int[] encryptedText, int[] key){
        int[] deKeyd = new int[key.length];
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