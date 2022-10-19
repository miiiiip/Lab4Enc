import java.util.Scanner;

public class BLM {
    public static void main(String[] args) {

        String IV = "10001011101011001000110011110000101";
        String IV19 = "1110101100100011001";
        // The scanner takes an input (for now prefereably with no spaces) and converts
        // it into a char array
        // So out methods can use it.
        System.out.println("Enter the plaintext here: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        scan.close();
        // { 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0,
        // 0, 1, 1, 0, 0, 0, 1, 0, 0, 1 };
        // Make a string of 1's and 0's first and then convert to a char array cause
        // this is easier than typing out
        // all the commas for the key. Also there is a weird interaction when typing
        // integers into a char array
        // when trying to reconvert those integers into numbers again.
        String key = "11010101010100010101111010010010101";

        // ECB Encryption
        System.out.println("--------------------------------ECB--------------------------------");
        System.out.println("Encrypted Text: ");
        String encryptedText = ECB.encrypt(input, key);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(ECB.decrypt(encryptedText, key));

        // CBC Encryption
        System.out.println("--------------------------------CBC--------------------------------");
        System.out.println("Encrypted Text: ");
        encryptedText = CBC.encrypt(input, key, IV);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(CBC.decrypt(encryptedText, key, IV));

        // CFB Encryption
        System.out.println("--------------------------------CFB--------------------------------");
        System.out.println("Encrypted Text: ");
        encryptedText = CFB.encrypt(input, key, IV);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(CFB.decrypt(encryptedText, key, IV));

        // CBC Encryption
        System.out.println("--------------------------------OFB--------------------------------");
        System.out.println("Encrypted Text: ");
        encryptedText = OFB.encrypt(input, key, IV);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(OFB.decrypt(encryptedText, key, IV));

        // CTR Encryption
        System.out.println("--------------------------------CTR--------------------------------");
        System.out.println("Encrypted Text: ");
        encryptedText = CTR.encrypt(input, key, IV19);
        System.out.println(encryptedText);
        System.out.println();
        System.out.println("Decrypted Text: ");
        System.out.println(CTR.decrypt(encryptedText, key, IV19));

        // The 1-bit error, For this test we encrypted: "Potato"

        // ECB encryption:
        // 0100000101100110101010100101000101111001110100100010101111010010010101
        String ECBErrorText = "0110000101100110101010100101000101111001110100100010101111010010010101";
        System.out.println("\nECB Error: " + ECB.decrypt(ECBErrorText, key));

        // CBC encryption:
        // 1111000000010011001110111100111101110110000100100110011100111101011010
        String CBCErrorText = "1111000100010011001110111100111101111110000100100110011100111101011010";
        System.out.println("CBC error: " + CBC.decrypt(CBCErrorText, key, IV));

        // CFB encryption:
        // 0101100010110110110010001110110011100100111010010011110111011110000011
        String CFBErrorText = "0101100010110110110010001110100011101100111010010011110111011110000011";
        System.out.println("CFB error: " + CFB.decrypt(CFBErrorText, key, IV));

        // OFB encryption: 010110001011011011001000111011001110010011
        String OFBErrorText = "010110001011011011001010111011001110010011";
        System.out.println("OFB error: " + OFB.decrypt(OFBErrorText, key, IV));

        // CTR encryption: 010010101001110010000110000111101000011010
        String CTRErrorText = "010010101001110000000110000111101000011010";
        System.out.println("CTR error: " + CTR.decrypt(CTRErrorText, key, IV19));

    }
}