import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author Michael Saltzman, James Thompson, Olivier Jin
 *
 */
public class wordsGUI extends JFrame {
	private wordsGame game;
	private wordsTile[][] currentboard;
	private ArrayList<wordsTile> currentrack;
	private wordsTile currentTile;
	private ArrayList<wordsTile> currentword;
	private boolean selected;
	private JPanel boardpanel;
	private JLabel letterslabel;
	private WordsPlayer currentplayer;
	private JLabel p1label;
	private JLabel p2label;
	private JPanel tilerack;
	
	/**
	 * Constructor for the GUI that displays the game board.
	 * @param g Letters With Buddies logic
	 */
	public wordsGUI(wordsGame g) {
		
		selected = false;
		game = g;
		currentplayer = game.getP1();
		currentboard = new wordsTile[15][15];
		currentrack = new ArrayList<wordsTile>();
		currentword = new ArrayList<wordsTile>();
		setSize(550,700);	
		addPanels();
		setResizable(false);
		setTitle ("Letters with Buddies");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Adds the panels to the frame.
	 */
	public void addPanels() {
		setLayout(new BorderLayout());
		boardpanel = getBoardPanel();
		JPanel contentpane = (JPanel) getContentPane();
		contentpane.add (getStatusPanel(), BorderLayout.NORTH);
		contentpane.add (boardpanel, BorderLayout.CENTER);
		contentpane.add (getInputPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * Creates a panel containing the scores and letters remaining.
	 * @return JPanel containing the player scores and letters remaining.
	 */
	public JPanel getStatusPanel() {
		JPanel statuspanel = new JPanel();
		statuspanel.setBorder(BorderFactory.createTitledBorder(""));
		statuspanel.setLayout (new GridLayout (1,3,1,1));
		p1label = new JLabel ("");
		p2label = new JLabel ("");
		letterslabel = new JLabel("" + game.getLettersRemaining());
		p1label.setBorder (BorderFactory.createTitledBorder ("Player 1"));
		p2label.setBorder (BorderFactory.createTitledBorder ("Player 2"));
		letterslabel.setBorder (BorderFactory.createTitledBorder ("Letters Remaining"));
		statuspanel.add (p1label);
		statuspanel.add (p2label);
		statuspanel.add (letterslabel);

		return statuspanel;
	}
	/**
	 * Creates a JPanel containing the game board.
	 * @return JPanel boardpanel containing the game board
	 */
	public JPanel getBoardPanel() {
	
		boardpanel = new JPanel();
		boardpanel.setBorder(BorderFactory.createTitledBorder(""));
		boardpanel.setLayout (new GridLayout(15,15));
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++){
				currentboard[i][j] = new wordsTile(game.getTileAt(i,j).getTileText(),i,j);
				wordsTile t = currentboard[i][j];
				t.addActionListener(getBoardListener(t));
				boardpanel.add(t);
			}
				
		return boardpanel;
	}
/**
 * Creates a JPanel containing the tiles in the current player's rack.
 * Also contains buttons for entering moves.
 * @return JPanel with the rack and buttons
 */
	public JPanel getInputPanel() {
		JPanel inputpanel = new JPanel();
		inputpanel.setBorder(BorderFactory.createTitledBorder(""));
		tilerack = new JPanel();
		tilerack.setBorder (BorderFactory.createTitledBorder ("Player 1's Turn"));
		tilerack.setLayout (new GridLayout(1,6,1,1));
			//System.out.println(currentrack.get(i).getTileText());
			//currentrack.get(i) = new wordsTile(game.getP1().getFromRack(i).getTileText());
		for (wordsTile t : game.getP1().getRack()){
			wordsTile temp = new wordsTile(t.getTileText());
			currentrack.add(temp);
			temp.setMinimumSize(new Dimension(35,40));
			temp.setPreferredSize(new Dimension(35,40));
			temp.setMaximumSize(new Dimension(35,40));
			temp.addActionListener(getRackListener(temp));
			tilerack.add(temp);
		}
		inputpanel.setLayout (new BoxLayout(inputpanel,BoxLayout.LINE_AXIS));
		JButton skipbutton = new JButton("Skip Turn");
		JButton undobutton = new JButton ("Undo");
		JButton gobutton = new JButton ("GO!");
		inputpanel.add(skipbutton);
		inputpanel.add(tilerack);
		inputpanel.add(undobutton);
		inputpanel.add(gobutton);
		
		gobutton.addActionListener(getGoListener());
		
		undobutton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
				   undo();
				}
			}
		);
		
		skipbutton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					syncToGame();
				}	
			}
		);
		return inputpanel;
	} 
	
	/**
	 * Returns tiles currently placed in the board to the player's rack.
	 * Player maintains turn.
	 */
	public void undo() {
		for (int i = 0; i < 15; i++){
			for (int j = 0; j < 15; j++) 
				{
					currentboard[i][j].setTileText(game.getTileAt(i,j).getTileText());
				}
			}
		
			for (int x=0; x < 7; x++)
				currentrack.get(x).setTileText(currentplayer.getFromRack(x).getTileText());
			
			currentTile = null;
			selected = false;
			currentword.clear();
			repaint();
	}
	/**
	 * Syncs the game logic with the GUI and prepares the GUI for the next player's turn.
	 */
	public void syncToGame(){
		p1label.setText("" + game.getP1().getScore());
		p2label.setText("" + game.getP2().getScore());
		
		for (int i = 0; i < 15; i++){
			for (int j = 0; j < 15; j++) 
				{
					currentboard[i][j].setTileText(game.getTileAt(i,j).getTileText());
				}
			}

			currentplayer.resetRack(game, currentrack);
			currentTile = null;
			selected = false;
			currentword.clear();
			letterslabel.setText("" + game.getLettersRemaining());
			
			if (currentplayer == game.getP1()) {
				currentplayer = game.getP2();
				tilerack.setBorder (BorderFactory.createTitledBorder ("Player 2's Turn"));
			}
			else {
				currentplayer = game.getP1();
				tilerack.setBorder (BorderFactory.createTitledBorder ("Player 1's Turn"));
			}
			
			for (int x=0; x < 7; x++)
				currentrack.get(x).setTileText(currentplayer.getFromRack(x).getTileText());
			
			repaint();
	}
	/**
	 * Creates a Listener for each tile on the board.
	 * @param t Tile to which the listener is added.
	 * @return ActionListener for the tile on the board that responds
	 * to tiles placed on to it.
	 */
	public ActionListener getBoardListener(wordsTile t) {
			final wordsTile wt = t;
			return new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					if (selected && currentTile != null && !currentboard[wt.getRow()][wt.getCol()].isLetter()){
						currentboard[wt.getRow()][wt.getCol()].setTileText(currentTile.getTileText());
						selected = false;
						currentword.add(new wordsTile(wt.getTileText(), wt.getRow(), wt.getCol()));
					}				
				}
			};	
	}	
	
	/**
	 * Creates a Listener for each Tile in the GUI's rack.
	 * @param t Tile to which the listener is added.
	 * @return ActionListener for each Tile in the GUI's rack
	 */
		public ActionListener getRackListener(wordsTile t)
	{
		final wordsTile wt = t;
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (wt.isLetter() && !selected){
				if (wt.getTileText().equals("_"))
					wt.setTileText(blankDialog());
				selected = true;
				currentTile = new wordsTile(wt.getTileText());
				wt.setTileText("empty");
				}
			}
		};	
	}
	/**
	 * Creates a dialog for the user to choose a letter for the blank tile.
	 * @return the letter chosen by the user
	 */
	public String blankDialog() {
		String letter = "  ";
		while ( letter.length() !=1){
		  letter = JOptionPane.showInputDialog(this,"Choose a letter:");
		}
		
		return letter.toUpperCase();
	}
	/**
	 * Creates a listener for the Go button.
	 * @return ActionListener for the button
	 */
	public ActionListener getGoListener(){ 
	        return new ActionListener(){ 
	                    public void actionPerformed(ActionEvent arg0){ 
	                        if (game.validateWord(currentboard, currentword)) { 
	                            currentplayer.incrementScore(game.getScore()); 
	                            syncToGame(); 
	                        }                         
	                        else{ 
	                            JOptionPane.showMessageDialog(null, "Invalid Word\n Try again.", "Illegal move", JOptionPane.ERROR_MESSAGE);  
	                            undo(); 
	                            } 
	                        if (game.getLettersRemaining() <= 0){ 
	                            String winnermessage; 
	                            if (game.getP1().getScore() > game.getP2().getScore()) 
	                                winnermessage = "Player 1 wins!!!"; 
	                            else if (game.getP2().getScore() > game.getP1().getScore()) 
	                                winnermessage = "Player 2 wins!!!"; 
	                            else  
	                                winnermessage = "Draw!!"; 
	                                 
	                            JOptionPane.showMessageDialog(null, "winnermessage", "Winner", JOptionPane.INFORMATION_MESSAGE); 
	                        }        
	                    } 
	             
	        }; 
	    }
}
