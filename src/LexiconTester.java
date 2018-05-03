import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LexiconTester {
    private static PrintWriter out1;
    private static PrintWriter out2;

    public static void main(String[] args) {
        ArrayList<Word> wordsMatchingPattern = new ArrayList<>(); // it will  contain all  the words that match a certain pattern
        // out1 will be used to write the output to file
        try {
            out1 = new PrintWriter("./src/sample4-results");
        } catch (FileNotFoundException e) {
            System.out.println("No File Found");
        }

        // This PrintWriter object will be used to display output to screen
        out2 = new PrintWriter(System.out);



        /* Create a Lexicon Object that reads two text files ,Creates  a lexicon
        and then writes that lexicon to a text file
         */
        Lexicon lexicon = new Lexicon();

        // Part 2 of the Assignment

        /*
        Test case 1 pattern will return all the words that end with the char 'd'
         */
        String pattern1 = "*d";
        wordsMatchingPattern = lexicon.matchPattern(pattern1);
        result(pattern1, wordsMatchingPattern);

       /*
       Test case 2 will display all the words that are of  length=2 and begin with a
        */
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        String pattern2 = "a?";
        wordsMatchingPattern = lexicon.matchPattern(pattern2);
        result(pattern2, wordsMatchingPattern);
        /*
        Test case 3 will output the words that are of  length=3 and the middle char between them is  'a'
         */
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        String pattern3 = "?a?";
        wordsMatchingPattern = lexicon.matchPattern(pattern3);
        result(pattern3, wordsMatchingPattern);
        /*
        Test case 4 will output the words that are of length 3 and end with  the char'x'.If no
        word is found it will print a message
         */
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        String pattern4 = "??x";
        wordsMatchingPattern = lexicon.matchPattern(pattern4);
        result(pattern4, wordsMatchingPattern);
        /*
        Test case 5 will  output words that begin with f
         */
        String pattern5 = "f*";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern5);
        result(pattern5, wordsMatchingPattern);
        /*
        Test case 6 pattern will output the words that has second char 'f'
         */
        String pattern6 = "?f";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern6);
        result(pattern6, wordsMatchingPattern);

        /*
        Test case 7 will output the words that has char 'i' in them
         */
        String pattern7 = "*i*";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern7);
        result(pattern7, wordsMatchingPattern);

        /*
        Test case 8 will outout words that begin with "ma"
         */
        String pattern8 = "ma*";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern8);
        result(pattern8, wordsMatchingPattern);
        /*
        Test case 9 will output words that starts with x
         */
        String pattern9 = "x*";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern9);
        result(pattern9, wordsMatchingPattern);
        /*
        Test case 10 will output the words have "nn" in them
         */
        String pattern10 = "*nn*";
        out1.println();
        out2.println();
        wordsMatchingPattern.clear();
        wordsMatchingPattern = lexicon.matchPattern(pattern10);
        result(pattern10, wordsMatchingPattern);


        // closing the print writer objects
        out1.close();
        out2.close();


    }

    /*
    This function takes the  pattern and the matching words and then displays them to screen and write to file with the help of a helper method
     */
    public static void result(String pattern, ArrayList<Word> myList) {
        writeMessage(out1, "The Pattern : ");
        writeMessage(out2, "The Pattern : ");
        writeMessage(out1, pattern);
        writeMessage(out2, pattern);
        writeMessage(out1, "may result in the output ");
        writeMessage(out2, "may result in the output ");

        if (myList.size() == 0) {
            writeMessage(out1, "No Words in the lexicon match  the pattern ");
            writeMessage(out2, "No Words in the lexicon match the pattern ");

        } else {

            for (Word word : myList) {
                writeMessage(out1, word.getWordName() + "    " + word.getWordFrequency());
                writeMessage(out2, word.getWordName() + "    " + word.getWordFrequency());

            }

        }


    }

    /*
    This is a helper method for the result()
     */
    public static void writeMessage(PrintWriter writer, String msg) {

        writer.println(msg);
    }
}
