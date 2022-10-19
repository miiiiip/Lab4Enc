import java.util.Arrays;
import java.util.Scanner;

import javax.xml.transform.Templates;

public class BLM {
    public static void main(String[] args) {
        BLM runner = new BLM();

        String TestEnc = "11100110000000000000000000000000000";
        String IV = "10001011101011001000110011110000101";
        String IV19 = "1110101100100011001";
        // The scanner takes an input (for now prefereably with no spaces) and converts
        // it into a char array
        // So out methods can use it.
        System.out.println("Enter the plaintext here: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        scan.close();
        char[] text = input.toCharArray();
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
        String encryptedText = CTR.encryptCTR(input, keyTemp, IV19);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(CTR.decryptCTR(encryptedText, keyTemp, IV19));
    }
}