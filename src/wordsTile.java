import java.awt.*;
import javax.swing.*;
/**
 * A Tile that represents a Scrabble Tile
 * @author Michael Saltzman, James Thompson, Olivier Jin
 *
 */
public class wordsTile extends JButton  implements Comparable {

	private String text;
	private int row, col, points;
	
/**
 * Constructor 
 * @param textIn letter on the tile
 * @param rowIn row of the tile
 * @param colIn column of the tile
 */
	public wordsTile(String textIn, int rowIn, int colIn){
		this.text=textIn;
		this.row=rowIn;
		this.col=colIn;
		this.points = findPointValue(text);

		ImageIcon ic = new ImageIcon("images/"+text+".png");
		Image img = ic.getImage();  
		Image scaledimg = img.getScaledInstance( 35,40, Image.SCALE_SMOOTH ) ;  
		Icon ic2 = new ImageIcon(scaledimg);
		setIcon(ic2);
		this.setMinimumSize(new Dimension(35,40));
		this.setPreferredSize(new Dimension(35,40));
		this.setMaximumSize(new Dimension(35,40));
	}
/**
 * Constructor
 * @param text letter on the tile
 */
	public wordsTile(String text) {
		this(text,-1,-1);

	}
/**
 * Getter for Tile text
 * @return String of letter
 */
	public String getTileText() {
		return text;
	}
/**
 * Getter for tile's points valie
 * @return int points value
 */
	public int getPoints(){
		return points;
	}
/**
 * Sets text of the tile with image for GUI.
 * @param textIn letter to be set
 */
	public void setTileText(String textIn) {
		this.text=textIn;
		ImageIcon ic = new ImageIcon("images/"+text+".png");
		Image img = ic.getImage();  
		Image scaledimg = img.getScaledInstance( 35,40, Image.SCALE_SMOOTH ) ;  
		Icon ic2 = new ImageIcon(scaledimg);
		setIcon(ic2);
	}
/**
 * Checks to see if the Tile has a letter.
 * @return true or false
 */
	public boolean isLetter(){
		return (text.length() == 1);
	}
/**
 * Getter for the row.
 * @return int row
 */
	public int getRow(){
		return row;
	}
/**
 * Getter for the column.
 * @return int column
 */
	public int getCol(){
		return col;
	}
	
/**
 * Compares Tiles based on their rows and columns
 */
	public int compareTo(Object o) {
		wordsTile other = (wordsTile)o;
		if (this.getRow() > other.getRow())
			return 1;
		else if (this.getRow() < other.getRow())
			return -1;
		else if (this.getCol() > other.getCol())
			return 1;
		else if (this.getCol() < other.getCol())
			return -1;
		else
			return 0;

	}
/**
 * Converts tile information to a string containing the letter
 * and location.
 */
	public String toString(){
		return this.getTileText()+ " at " + this.getRow() + " , " + this.getCol();

	}
/**
 * Finds point value of Tile.
 * @param textIn letter
 * @return int points value
 */
	public int findPointValue(String textIn){
		if(textIn.equals("A") || textIn.equals("E") || textIn.equals("I") || textIn.equals("O") || 
				textIn.equals("S") || textIn.equals("R") || textIn.equals("T")){
			return 1;
		}
		else if(textIn.equals("D") || textIn.equals("L") || textIn.equals("N") || textIn.equals("U")){
			return 2;
		}
		else if(textIn.equals("G") || textIn.equals("Y") || textIn.equals("H")){
			return 3;
		}
		else if(textIn.equals("B") || textIn.equals("C") || textIn.equals("F") || textIn.equals("M") || 
				textIn.equals("P") || textIn.equals("W")){
			return 4;
		}
		else if(textIn.equals("K") || textIn.equals("V")){
			return 5;
		}
		else if(textIn.equals("X")){
			return 8;
		}
		else if(textIn.equals("J") || textIn.equals("Q") ||textIn.equals("Z")){
			return 10;
		}
		else{
			return 0;
		}
	}



}
