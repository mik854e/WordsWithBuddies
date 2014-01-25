import java.io.*;
import java.util.*;
/**
 * Creates a dictionary for word validation. Uses the Singleton pattern.
 * @author Michael Saltzman, James Thompson, Olivier Jin
 *
 */
public class WordsDict {
	private HashSet<String> dictionary;
	private static WordsDict instance;
	
	/**
	 * Private Constructor
	 */
	private WordsDict(){
		dictionary = new HashSet<String>();
	}
	/**
	 * Retrieves the words for the dictionary from a text file.
	 */
	public void getDictionary(){
		try{
		   	File dictFile = new File("Dictionary.txt");
		   	FileReader dictIn = new FileReader(dictFile);
		   	BufferedReader dictReader = new BufferedReader(dictIn);
		   	String word = "";
		 
			while((word = dictReader.readLine()) != null){
				dictionary.add(word);
			}
		}
		catch(IOException badFile){ System.out.println("Bad file!"); }
	}
	/**
	 * Checks to see if a word exists in the dictionary.
	 * @param wordIn to be validated
	 * @return true or false
	 */
	public boolean checkWord(String wordIn){
	    System.out.println ("dict reads "+ wordIn);
		if(dictionary.contains(wordIn.toLowerCase())){
			System.out.println("valid word: " + wordIn);
			return true;
		}
		return false;
	}
	/**
	 * Creates an instance if it does not already exist. Otherwise,
	 * returns the instance.
	 * @return instance of the WordsDict class
	 */
	public static WordsDict getInstanceOfDict() {
		if (instance == null) {
			instance = new WordsDict();
			return instance;
		}
		else
			return instance;
			
	}
}
