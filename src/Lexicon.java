import java.io.*;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Lexicon {
    private ArrayList<Word> wordList;
    private StringTokenizer stringTokenizer;
    private BufferedReader reader;
    private FileInputStream inputFile1;
    private FileInputStream inputFile2;
    private SequenceInputStream sequenceInputStream; // will help us read the two files one by one.

    public Lexicon() {
        try {
            wordList = new ArrayList<>();
            inputFile1 = new FileInputStream("./src/sample1-pp");
            inputFile2 = new FileInputStream("./src/sample-zoo");
            sequenceInputStream = new SequenceInputStream(inputFile1, inputFile2);// this will read both files in a sequence
            reader = new BufferedReader(new InputStreamReader(sequenceInputStream));
            run();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }

    /* This function calls the method to read the two text files.
    It also calls the method to sort the words.
     */
    public void run() {
        readFromFile();
        sort();
        neighbours();

        //  Write the Lexicon to the File
        try {

            PrintWriter writer = new PrintWriter("./src/sample3-wordlist");
            for (Word word : wordList) {
                word.writeToFile(writer);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    /* This function will start reading the lines from the two text files and call the method that splits the line into words.

     */
    public void readFromFile() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                // condition to skip empty lines
                if (!line.isEmpty()) {
                    splitIntoWords(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Something went wrong while reading through the text files");
        }
    }

    /* This function will split a line into words

     */
    public void splitIntoWords(String line) {
        stringTokenizer = new StringTokenizer(line);
        while (stringTokenizer.hasMoreTokens()) {   // removea all the punctuations from the word.
            String newWord = stringTokenizer.nextToken().replaceAll("[^A-Za-z]+", "").toLowerCase().trim();
            // condition to avoid storing words that were numeric characters like "1"
            if (newWord.length() == 0) {
                // do nothing
                continue;
            } else {   // create the Word Object
                Word word = new Word(newWord);

                // checking if the Array list is empty or not
                if (wordList.size() == 0) {
                    // add it to the word List
                    wordList.add(word);
                } else {
                    // check if the word is already present in the wordList.If it is, then increase its frequency.

                    if (wordList.contains(word)) {
                        // increase the frequency
                        int index = wordList.indexOf(word);

                        wordList.get(index).incrementWordFrequency();

                    } else {
                        // add it to the word List
                        wordList.add(word);
                    }


                }

            }
        }
    }

    /* This function uses Quick Sort  to sort the Array list of Word objects

     */
    public void sort() {
        quickSort(wordList, 0, wordList.size() - 1);
    }

    /* This function implements Quick Sort on wordList

     */
    public void quickSort(ArrayList<Word> wordList, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int q = partition(wordList, startIndex, endIndex);
            quickSort(wordList, startIndex, q - 1);
            quickSort(wordList, q + 1, endIndex);
        }
    }

    /* This function acts as a helper method for the Quick Sort

     */
    public int partition(ArrayList<Word> wordList, int startIndex, int endIndex) {
        // choose the pivot
        Word pivot = wordList.get(endIndex);
        int i = startIndex - 1;
        for (int j = startIndex; j < endIndex; j++) {
            if (wordList.get(j).compareTo(pivot) < 0) {
                i++;
                Word temp = wordList.get(i);
                wordList.set(i, wordList.get(j));
                wordList.set(j, temp);
            }
        }
        Word temp = wordList.get(i + 1);
        wordList.set(i + 1, pivot);
        wordList.set(endIndex, temp);
        return i + 1;

    }

    /* This function finds neighbours for each word present in the wordList

     */
    public void neighbours() {    // keeps the track of number of different chars between two words
        // ArrayList for words that are the neighbours
        ArrayList<Word> neigbours = new ArrayList<>();
        for (Word word : wordList) {
            String word_1 = word.getWordName();
            for (Word word2 : wordList) {
                int count = 0;
                String word_2 = word2.getWordName();
                // check if both the words are of the same length
                if (word_1.length() == word_2.length()) {
                    // check if they only differ by one char
                    for (int i = 0; i < word_1.length(); i++) {
                        if (word_1.charAt(i) != word_2.charAt(i)) {
                            count++; // Increase the count if the two char differ
                        }
                    }
                }
                // if they only differ by one char that means that word_2 is a neighbour of word_1
                if (count == 1) {   // add word2 as a neigbour for word
                    int index1 = wordList.indexOf(word); // don't do it again and again as you can do this in the main for loop
                    int index2 = wordList.indexOf(word2);
                    wordList.get(index1).addNeighbour(wordList.get(index2));
                }
            }
        }
    }

    /*
    This Function takes the pattern and returns an Array list containing words that match the pattern
     */
    public ArrayList<Word> matchPattern(String pattern) {
        ArrayList<Word> wordMatchingPattern = new ArrayList<>();

        for (Word word : wordList) {
            if (match(word.getWordName(), pattern)) {
                // if the word matches pattern add it to the array list
                wordMatchingPattern.add(word);

            }
        }
        return wordMatchingPattern;


    }

    /* This function will take  the word and pattern as parameter and check if the word matches pattern or not

     */
    public Boolean match(String word, String pattern) {


        int i = 0; // use to keep a track of the pointer of  word
        int j = 0;// use to keep a track of pointer on the pattern
        int wordIndex = -1;
        int iIndex = -1;
        // loop continues till the length of the word
        while (i < word.length()) {
            if (j < pattern.length() && (pattern.charAt(j) == '?' || pattern.charAt(j) == word.charAt(i))) {
                ++i;
                ++j;
            } else if (j < pattern.length() && pattern.charAt(j) == '*') { // if the char is * then loop through the words as it can match 0 or any chars
                wordIndex = j;
                iIndex = i;
                j++; // increment the pointer in the pattern and move to the next one
            } else if (wordIndex != -1) { // this loop is executed after we find '*' in the pattern. This matches it with 0 or many chars present in the word
                j = wordIndex + 1;
                i = iIndex + 1;
                iIndex++;
            } else {
                return false;
            }
        }
        /* loop through the existing element if the pattern length has not  been
         *fulfilled
         */
        while (j < pattern.length() && pattern.charAt(j) == '*') {
            ++j;
        }
        return j == pattern.length(); // returns true if the pointer on the pattern reaches the end
    }

    /* This function helps to display the words on the screen  that matches  pattern  and also write them to file

     */


}









