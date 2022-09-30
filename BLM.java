import java.util.Arrays;

public class BLM {

    public static void main(String[] args) {
        BLM runner = new BLM();
        char[] text = { 'H', 'e', 'l', 'l', 'o' };
        int[] key = { 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0,
                0, 1 };

        System.out.println(Arrays.toString(runner.encrypt(text, key)));
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

    public String charToBinary(char input) {
        return Integer.toBinaryString((int) input);
    }

    public int[] encrypt(char[] input, int[] key) {
        String res = "";
        for (int i = 0; i < input.length; i++) {
            res = res + charToBinary(input[i]);
        }
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
            if(i%7==0 && i != 0){
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