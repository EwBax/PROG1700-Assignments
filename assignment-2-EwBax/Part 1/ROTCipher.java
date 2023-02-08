import java.util.Arrays;

public class ROTCipher {
	//Command arg flags
	public static final String MIN_WORD_LENGTH = "-minWordLength";
	public static final String SECRET_NUMBER_FLAG = "-secretNumber";
	public static final String HELP_FLAG = "-help";
	//Magic number alphabet length used for shifting alphabet
	public static final int ALPHABET_LENGTH = 26;	
	
	public static void main(String[] args) {
		
		//variables to manage whether encrypting or decrypting (encrypt by default) and boolean variable used to stop program from running if error encountered
		String function = "encrypt";
		
		//Parsing input, checking valid length and first argument
		if (args.length < 2) {
			System.out.println("ERROR: not enough arguments\n");
			help();
			//System.exit(int status) docs https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/System.html#exit(int)
			System.exit(0);
		}
		else if (!args[0].equalsIgnoreCase("encrypt") && !args[0].equalsIgnoreCase("decrypt")) {
			help(args[0]);
			System.exit(0);
		} else {
			//at this point we know args[0] is either "encrypt" or "decrypt", so set function to that value
			function = args[0].toLowerCase();
		} 
		
		int secretNumber = 13; //default 13 for ROT13 encryption
		int minWordLength = 1; //default 1 so every word is encrypted
		
		//Parsing input to find other optional command arguments
		for (int i=1; i<args.length - 1; i++) {
			if (args[i].charAt(0) == '-') {	//Checking if argument starts with '-'
				if (args[i].equalsIgnoreCase(SECRET_NUMBER_FLAG)) {			//checking if -secretNumber command was entered
					try{
						secretNumber = Integer.parseInt(args[i+1]);
					} catch (java.lang.NumberFormatException e){
						System.out.println("ERROR: secretNumber must be an integer.\n");
						help();
						System.exit(0);
					}	
					i++;	//incrementing i because we just used args[i+1] to set secretNumber so we don't need to check it next time through the loop
					//Math.abs(int a) docs https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Math.html#abs(int)
					secretNumber = Math.abs(secretNumber % ALPHABET_LENGTH);		//Finding remainder after dividing by 26, and making it positive (alphabet length) to simplify secretNumber
					if (secretNumber <= 0) {										
						//error message if secretNumber will not work
						System.out.println("ERROR: This encryption key will not encrypt the data. " +
										   "secretNumber cannot be 0 or a multiple of 26.\n");
						help();
						System.exit(0);
					}
				} else if (args[i].equalsIgnoreCase(MIN_WORD_LENGTH)) {			//checking if -minWordLength command was entered
					try {
						minWordLength = Integer.parseInt(args[i+1]);
					} catch (java.lang.NumberFormatException e) {
						System.out.println("ERROR: minWordLength must be an integer.\n");
						help();
						System.exit(0);
					}
					
					if (minWordLength <= 0) {
						System.out.println("ERROR: minWordLength must be greater than 0.\n");
						help();
						System.exit(0);
					}
					i++;	//incrementing i because we just used args[i+1] to set minWordLength so we don't need to check it next time through the loop
				} else if (args[i].equalsIgnoreCase(HELP_FLAG)) {			//checking if -help command was entered
					help();
					System.exit(0);
				} else {		//code will only reach this point if argument starts with '-' but does not match any of the proper command arguments					
					help(args[i]);
					System.exit(0);
				}
			} else if (i < args.length - 1) {		//This block is to make sure the data to be encrypted/decrypted is wrapped in quotes
				System.out.println("ERROR: Improper format. Make sure to start commands with \"-\", and put your data in quotation marks\n");
				help();								//If arg value does not start with '-' it must either be an int following a '-' command, which we took care of earlier...
				System.exit(0);						//Or it is the start of the data, in which case it should be the final value in args if it was properly wrapped in quotes
			}
		}
		
		
		String result = "";
		if(function.equals("decrypt")) result = decrypt(args[args.length-1], secretNumber, minWordLength);
		else result = encrypt(args[args.length-1], secretNumber, minWordLength);
		
		System.out.println("Your " + function + "ed data is: " + result);
	}
	
	public static String encrypt (String text, int secretNumber, int minWordLength) {
		//splits input string into array for easier manipulation and checking word length
		secretNumber = secretNumber % ALPHABET_LENGTH;
		String[] textAsArray = text.split("\\s+");
		text = "";		//sets text to blank so that we can ammend encrypted/decrypted values to it for returning at the end
		for (String s: textAsArray) {
			if (s.length() >= minWordLength) {	//making sure word is long enough to encrypt/decrypt
				for (char c: s.toCharArray()) {
					if (c >= 'A' && c <= 'Z') 	{				
						c += secretNumber;
						if (c > 'Z') c -= ALPHABET_LENGTH;	//this is how the value wraps back to the start of the alphabet
					} else if (c >= 'a' && c <= 'z') {
						c += secretNumber;
						if (c > 'z') c -= ALPHABET_LENGTH;
					}
					text += c;
				}
			} else text += s;	//if word is too short to be encrypted
			if(Arrays.asList(textAsArray).indexOf(s) != textAsArray.length-1) text += " ";	//adding space between words unless it is the last word in the array
		}
		return text;
	}
	
	public static String decrypt (String text, int secretNumber, int minWordLength) {
		secretNumber = secretNumber % ALPHABET_LENGTH;
		//to decrypt using the same method, changed secretNumber to the difference between the encryption key secretNumber and 26 (alphabet length) so that it wraps back
		//to the original value i.e. is shifted a full 26 positions back to the original
		secretNumber = ALPHABET_LENGTH - secretNumber;
		text = encrypt(text, secretNumber, minWordLength);
		return text;
	}
	
	//This is the general help method
	public static void help () {
		System.out.println("This program takes a phrase and encrypts it using ROT13 by default, with options to set custom minimum word length to be " +
						   "encrypted, and custom encryption key through command line arguments entered at runtime.\n");
		System.out.println("The format for command line arguments is as follows (items in [] are mandatory fields and <> are optional) :");
		System.out.println("[encrypt|decrypt] <-minWordLength [INT]> <-secretNumber [INT]> [\"STRING TO ENCRYPT\"]");
	}
	
	//This help method is to give an additional error message for an improper command
	public static void help (String errorMessage) {
		System.out.println("\"" + errorMessage + "\" is not a valid command.");
		help();
	}
	
}