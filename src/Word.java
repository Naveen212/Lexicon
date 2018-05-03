import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class Word implements Comparable<Word> {
    private String wordName;
    private int wordFrequency=1;
    ArrayList<Word> neighbours;


    public Word(String wordName) {
        this.wordName = wordName;
        neighbours=new ArrayList<>();
    }

    public String getWordName() {
        return wordName;
    }

    public int getWordFrequency() {
        return wordFrequency;
    }

    public ArrayList<Word> getNeighbours() {
        return neighbours;
    }
    public void addNeighbour(Word word)
    {
        neighbours.add(word);
    }


    /*  If the word appears more than one time in the text file we increase its frequency

     */
    public void incrementWordFrequency() {
        this.wordFrequency += 1;
    }

    public void setNeighbours(ArrayList<Word> neighbours) {
        this.neighbours = neighbours;
    }

    // Method to write the Word object to file
    public void writeToFile(PrintWriter writer) {
        // concatenate all the neighbours of the Word in a string if it has one.
        String neighbour = "[";
        for (Word word : neighbours) {
            neighbour +=word.getWordName()+"," ;
        }
      // neighbour= neighbour.substring(0,neighbour.length()-1); // to remove the ',' from the end

        neighbour=neighbour+"]";

        writer.println(wordName + "    " + wordFrequency + "  " + neighbour);
        writer.println();

    }

    // Overriding the equals and hash  code methods. This will be helpful when we're searching in the Array List of Words
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(wordName, word.wordName);  // returns true if both word objects have the same name
    }

    @Override
    public int hashCode() {

        return Objects.hash(wordName);
    }
    @Override
    public int compareTo(Word word)
    {
        int nameDifference  =this.wordName.compareTo(word.wordName);
        return nameDifference;
    }
}
