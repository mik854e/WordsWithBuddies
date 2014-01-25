import java.util.*;
/**
 * Creates the logic end for the Letters with Buddies game.
 * @author Michael Saltzman, James Thompson, Olivier Jin
 *
 */
public class wordsGame {

	private wordsTile[][] board;
	private Stack<wordsTile> tilestack;
	private WordsPlayer p1, p2, winner;
	private WordsDict dict;
	private boolean isFirstTurn;
	private  ArrayList<String> wordsnew;
/**
 * Default Constructor
 * Initializes the components of the game for the GUI.
 */
	public wordsGame() {
		tilestack = new Stack<wordsTile>();
		initStack();
		p1 = new WordsPlayer();
		p2 = new WordsPlayer();
		p1.initRacks(this);
		p2.initRacks(this);
		dict = WordsDict.getInstanceOfDict();
		dict.getDictionary();
		isFirstTurn = true;
		
		board = new wordsTile[15][15];
		for (int i = 0; i < 15; i++){
			for (int j = 0; j < 15; j++){
				int dist = (int)(Math.pow(Math.abs(i-7),2)+Math.pow(Math.abs(j-7),2));
				String s;
				switch (dist) {
				case 0: s = "start"; break;
				case 65: s = "TW"; break;
				case 16: s = "DW"; break;
				case 40: s = "DW"; break;
				case 8:  s = "TL"; break;
				case 32: s = "TL"; break;
				case 10: s = "DL"; break;
				case 34: s = "DL"; break;
				case 61: s = "DL"; break;
				default: s = "empty";
				}
				board[i][j] = new wordsTile(s,i,j);
			}
		}
	}
	/**
	 * Fills the Tile stack with letters.
	 */
	public void initStack() {
		for (int i = 0; i < 9; i++){
			tilestack.push(new wordsTile("A"));
		}
		for(int i = 0; i < 2; i++){
			tilestack.push(new wordsTile("B"));
			tilestack.push(new wordsTile("C"));
			tilestack.push(new wordsTile("F"));
			tilestack.push(new wordsTile("M"));
			tilestack.push(new wordsTile("P"));
			tilestack.push(new wordsTile("V"));
			tilestack.push(new wordsTile("W"));
			tilestack.push(new wordsTile("Y"));
			tilestack.push(new wordsTile("_"));
		}
		for (int i = 0; i < 3; i++)
			tilestack.push(new wordsTile("G"));
		for(int i = 0; i < 13; i++)
			tilestack.push(new wordsTile("E"));
		for (int i = 0; i < 4; i++){
			tilestack.push(new wordsTile("H"));
			tilestack.push(new wordsTile("L"));
			tilestack.push(new wordsTile("U"));
		}
		for(int i = 0; i < 5; i ++){
			tilestack.push(new wordsTile("D"));
			tilestack.push(new wordsTile("N"));
			tilestack.push(new wordsTile("S"));
		}
		for(int i = 0; i < 8; i++){
			tilestack.push(new wordsTile("I"));
			tilestack.push(new wordsTile("O"));
		}
		for(int i = 0; i < 6; i++)
			tilestack.push(new wordsTile("R"));
		for(int i = 0; i < 7; i++)
			tilestack.push(new wordsTile("T"));
			tilestack.push(new wordsTile("Q"));
			tilestack.push(new wordsTile("J"));
			tilestack.push(new wordsTile("Z"));
			tilestack.push(new wordsTile("X"));
			tilestack.push(new wordsTile("K"));

		Collections.shuffle(tilestack);
	}
	/**
	 * 
	 * @return
	 */
	public int getLettersRemaining(){
		return tilestack.size();
	}
	
	public wordsTile getLetter() {
		// System.out.println(tilestack.size());
		return tilestack.pop();
	}
	
	public wordsTile getTileAt(int x, int y){
		return board[x][y];
	}
	/**
	 * Validates words based on positioning and dictionary checking.
	 * @param tmpb
	 * @param newTiles
	 * @return
	 */
	public boolean validateWord(wordsTile[][] tmpb, ArrayList<wordsTile> newTiles){
		
		boolean tilePlacement = false;
		if (newTiles.isEmpty()) {
			return false;
		}
		
		Collections.sort(newTiles);
		
		if (isFirstTurn == true) {
			for (wordsTile t : newTiles){
				if(t.getRow() == 7 && t.getCol() == 7)
					tilePlacement = true;
			}
			if (tilePlacement == false){
				System.out.println("1st turn");
				return false;
			}
		}

		if (newTiles.size() > 1) {
			int r1 = newTiles.get(0).getRow();
			int c1 = newTiles.get(0).getCol();
			boolean isHorizontal = false;
			if (r1 == newTiles.get(1).getRow())
				isHorizontal = true;
			
			for (wordsTile t : newTiles) {
				if (isHorizontal && t.getRow() != r1) {
					System.out.println("bad row");
					return false;
				}
				else if (!isHorizontal && t.getCol() != c1) {
					System.out.println("bad col");
					return false;
				}
			}
			
			if (isHorizontal) {
				int c = c1;
				int ctr = 0;
				while (tmpb[r1][c].isLetter() && c < 15 && r1 < 15) {
					c++;
					ctr++;
				}
				if (ctr < newTiles.size()) {
					System.out.println("gaps col");
					return false;
				}
			}
			
			else {
				int r = r1;
				int ctr = 0;
				while (tmpb[r][c1].isLetter() && r < 1 && c1 < 15) {
					r++;
					ctr++;
				}
				if (ctr < newTiles.size()){
					System.out.println("gaps row");
					return false;
				}
			}
		}
		
		if (newTiles.size() == 1 &&
			!tmpb[newTiles.get(0).getRow()][newTiles.get(0).getCol() - 1].isLetter() &&
			!tmpb[newTiles.get(0).getRow() - 1][newTiles.get(0).getCol()].isLetter()) {
				boolean rowplus = newTiles.get(0).getRow() + 1 < 14 && !tmpb[newTiles.get(0).getRow() + 1][newTiles.get(0).getCol()].isLetter();
				boolean colplus = newTiles.get(0).getCol() + 1 < 14 && !tmpb[newTiles.get(0).getRow()][newTiles.get(0).getCol() + 1].isLetter();
		 
			if (rowplus && colplus)
				return false;
		}
			
		wordsnew = new ArrayList<String>();
		ArrayList<String> wordsold= new ArrayList<String>();
		String row = "";
		String col = "";
		String rowold = "";
		String colold = "";
		for (int i = 0; i < 15; i++){	
			for (int j=0; j< 15; j++) {	
				if (tmpb[j][i].isLetter())
					row += tmpb[j][i].getTileText();
				else
					row += " ";
				
				if (board[j][i].isLetter())
					rowold += board[j][i].getTileText();
				else
					rowold += " ";
			}
				
		}
		
		for (int i = 0; i < 15; i++){	
			for (int j=0; j< 15; j++) {
				
				if (tmpb[i][j].isLetter())
					col += tmpb[i][j].getTileText();
				else
					col += " ";
				
				if (board[i][j].isLetter())
					colold+= board[i][j].getTileText();
				else
					colold+= " ";
					
			}
		}
		
		String[] rowstrings = row.trim().split("[ ]+");
		String[] colstrings = col.trim().split("[ ]+");
		String[] rowstringsold = rowold.trim().split("[ ]+");
		String[] colstringsold = colold.trim().split("[ ]+");

		for(int i = 0; i < rowstrings.length; i++){
		  if (rowstrings[i].length() == 1 || dict.checkWord(rowstrings[i])){
			wordsnew.add(rowstrings[i]);
			System.out.println(rowstrings[i]);
		  }
		  else
			  return false;
		}
		
		for (int i = 0; i < rowstringsold.length; i++){
		  if (rowstringsold.length > 0){
			wordsold.add(rowstringsold[i]);	
			System.out.println(rowstringsold[i]);
		  }
		}
		
		for(int i = 0; i < colstrings.length; i++){
		 if (colstrings[i].length() == 1 || dict.checkWord(colstrings[i])) {
			wordsnew.add(colstrings[i]);
			System.out.println(colstrings[i]);
		 }
		 else
			 return false;
		}
		
		for (int i=0; i < colstringsold.length; i++){
		if (colstringsold.length > 0) {
			wordsold.add(colstringsold[i]);
			System.out.println(colstringsold[i]);
		}
		}
		
	    for (String s: wordsold)
			wordsnew.remove(s);
		
	    for (String s: wordsnew){
			System.out.println(s);
		
		}
		
		for (int a = 0; a < 15; a++) {
			for (int b = 0; b < 15; b++)
				board[a][b] = new wordsTile (tmpb[a][b].getTileText());
		
		}
		isFirstTurn = false;
		return true;
}
	
	public WordsPlayer getP1() {
		return p1;
	}
	
	public WordsPlayer getP2() {
		return p2;
	}
	
	public int getScore() { 
	    int sc = 0; 
	    for (String s: wordsnew) { 
	        if (s.length() <= 1) 
	            continue; 
	        for (int i = 0; i < s.length(); i++){ 
	            int newsc = (new wordsTile(s.charAt(i)+"")).getPoints(); 
	            sc+=newsc; 
	        } 
	    } 
	     
	    wordsnew.clear(); 
	    return sc; 
	     
	}
}
		
		
	
				
		
	
