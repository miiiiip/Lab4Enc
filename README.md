## 1-bit error resaults

1. ECB
Original Input: Potato
Encrypted Text: 0100000101100110101010100101000101111001110100100010101111010010010101
1-bit Error text: 0110000101100110101010100101000101111001110100100010101111010010010101

When we introduce the 1-bit error here, we get Potauo. 
Only one block was affected.

2. CBC
Original Input: Potato
Encrypted Text: 0100000101100110101010100101000101111001110100100010101111010010010101
1-bit Error text: 1111000100010011001110111100111101111110000100100110011100111101011010

When introduced, we get Totato@.
It seems that the first block was affected and it looks like an extra block got added?
That or the padding block got affected in a weird way.


3. CFB
Original Input: Potato
Encrypted Text: 0100000101100110101010100101000101111001110100100010101111010010010101
1-bit Error text: 0101100010110110110010001110100011101100111010010011110111011110000011

When Introduced, we get PotaTO.
The last two blocks were affected here. 

4. OFB
Original Input: Potato
Encrypted Text: 0100000101100110101010100101000101111001110100100010101111010010010101
1-bit Error text: 010110001011011011001010111011001110010011

When introduced, we get PotAto.
Only one block was affected.

5. CTR
Original Input: Potato
Encrypted Text: 0100000101100110101010100101000101111001110100100010101111010010010101
1-bit Error text: 010010101001110000000110000111101000011010

When introduced, we get Podato.
Only one block was affected.
