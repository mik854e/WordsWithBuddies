import java.util.*;

/**
 * 
 * @author Michael Saltzman, James Thompson, Olivier Jin
 *
 * Creates a player to play the words Game.
 */
public class WordsPlayer  {
	private int score;
	private ArrayList<wordsTile> rack;
	
	/**
	 * Default Constructor
	 */
	public WordsPlayer(){
		score = 0;
		rack = new ArrayList<wordsTile>();
	}
	
	/**
	 * 
	 * @return boolean indicates whether the player has 
	 * any tiles left on his/her rack
	 */
	public boolean hasTiles() {
		return !rack.isEmpty();
	}
	
	/**
	 * Fills each player's rack with 7 tiles.
	 * @param gameIn the Letters with Buddies game logic
	 */
	public void initRacks(wordsGame gameIn){
		for(int i=0; i<7; i++){
			addToRack(gameIn.getLetter());
		}
	}

	/**
	 * Adds a tile from the tile stack to the player's rack.
	 * @param t tile to be added to player's rack
	 */
	public void addToRack(wordsTile t){
		rack.add(t);
	}
	
	/**
	 * Gets Tile at a certain position in the rack.
	 * @param i index of Tile position
	 * @return Tile at index i
	 */
	public wordsTile getFromRack(int i) {
		return rack.get(i);
	}
	
	/**
	 * Sets the Tile at a certain position in the rack.
	 * @param i index 
	 * @param t Tile
	 */
	public void setRackTile(int i, wordsTile t){
		rack.set(i, t);
	}
	/**
	 * After a turn, resets the player's rack by filling in the missing tiles.
	 * @param game Letters with Buddies logic
	 * @param currentrack ArrayList that holds the tiles being displayed in the GUI
	 */
	public void resetRack(wordsGame game, ArrayList<wordsTile> currentrack) {
		int ctr = 0;
		for (int i = currentrack.size() - 1; i >= 0; i--) {
			if (currentrack.get(i).getTileText().equals("empty")) {
				rack.remove(i);
				ctr++;
			}
		}
		
		for (int i = 0; i < ctr; i++) {
			rack.add(game.getLetter());
		}
	}
	
	/**
	 *  Getter for the rack.
	 * @return rack The Player's rack
	 */
	public ArrayList<wordsTile> getRack() {
		return rack;
	}
	
	/**
	 * Getter for the player's score.
	 * @return score The Player's score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Increments the player's score.
	 * @param n current word score
	 */
	public void incrementScore(int n) {
		score += n;
	}

	
}