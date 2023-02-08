package ca.ewanbaxter.util;

public class Output {

    //lists contents of array for user prompt
    public static void listOptions(String prompt, String[] options) {
        System.out.println("\n" + prompt);
        for(int i = 0; i < options.length; i++) {
            System.out.printf("[%d] %s\n", i+1, options[i]);
        }
    }
}
