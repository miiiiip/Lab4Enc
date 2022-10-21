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


# Decrypted OFB from Zeke and Biruk.

Conor hEtinGeR had always hated thick The dungeon with its curried, crooked computers. It was a place where he felt depressed. He was a shallow, benevlolent, monster drinker with moist fingers and fat armpits. His friends saw him as a great, grated goofy. Once, he had even made a cup of tea for a silky conor jr. That's the sort of man he was. Conor walked over to the window and reflected on his moist surroundings. The drizzle rained like dancing dogs. Then he saw something in the distance, or rather someone. It was the figure of Nick Gilbertson. Nick was a thoughtful brute with greasy fingers and fluffy armpits. Conor gulped. He was not prepared for Nick. As Conor stepped outside and Nick came closer, he could see the prickly glint in her eye. Nick gazed with the affection of 1068 vile green giraffes. She said, in hushed tones, "I love you and I want Beans." Conor looked back, even more confused and still fingering the stripy kettle. "Nick, you suck," he replied. They looked at each other with intrigued feelings, like two old, old-fashioned ostriches boating at a very forgetful Halloween party, which had reggae music playing in the background and two sweet uncles coding to the beat. Conor regarded Nick's greasy fingers and fluffy armpits. "I feel the same way!" revealed Conor with a delighted grin. Nick looked frustrated, her emotions blushing like a screeching, steady sausage. Then Nick came inside for a nice drink of monster. THE END

# Decrypted CFB from Zeke and Biruk

dictum sit amet justo donec enim diam vulputate ut pharetra sit amet aliquam id diam maecenas ultricies mi eget mauris pharetra et ultrices neque ornare aenean euismod elementum nisi quis eleifend quam adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus urna neque viverra justo nec ultrices dui sapien eget mi proin sed libero enim sed faucibus turpis in eu mi bibendum neque egestas congue quisque egestas diam in arcu cursus euismod quis viverra nibh cras pulvinar mattis nunc sed blandit libero volutpat sed cras ornare arcu dui vivamus arcu felis bibendum ut tristique et egestas quis ipsum suspendisse ultrices gravida dictum fusce ut placerat orci nulla pellentesque dignissim enim sit amet venenatis urna cursus eget nunc scelerisque viverra mauris in aliquam sem fringilla ut morbi tincidunt augue interdum velit euismod in pellentesque massa placerat duis ultricies lacus sed turpis tincidunt id aliquet risus feugiat in ante metus dictum at tempor commodo ullamcorper a lacus vestibulum sed arcu non odio euismod lacinia at quis risus sed vulputate odio ut enim blandit volutpat maecenas volutpat blandit aliquam etiam erat velit scelerisque in dictum non consectetur a erat nam at lectus urna duis convallis convallis tellus id interdum velit laoreet id donec ultrices


