import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * View class for MVC design.
 * 
 * @author kevinrobell
 *
 */
public class GameView
{
   private final static int NUM_PLAYERS = 2;
   private final static int DEFAULT_CARDS_PER_HAND = 7;
   //Constants to make array values more readable
   private static final int HUMAN = 1;
   private static final int COMPUTER = 0;
   
   private int numCardsPerHand;
   
   private CardTable myCardTable;
   private JLabel[] computerHandLabels;
   private JButton[] humanHandButtons; 
   private JButton[] playedCardLabels  = new JButton[NUM_PLAYERS]; 
   private JButton messageLabel = new JButton();
   private JButton cannotPlay;
   
   /**
    * Constructor
    * @param numCardsPerHand
    */
   GameView(int numCardsPerHand) {
      if(numCardsPerHand <= 0)
         this.numCardsPerHand = DEFAULT_CARDS_PER_HAND;
      else
         this.numCardsPerHand = numCardsPerHand;
      
      myCardTable = new CardTable("Card Table", this.numCardsPerHand, NUM_PLAYERS);
      
      // establish main frame in which program will run
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //Set arrays of labels and buttons for both players hands
      computerHandLabels = new JLabel[numCardsPerHand];
      humanHandButtons = new JButton[numCardsPerHand];  
   }
   
   /**
    * Default Constructor
    * numCardsPerHand is set to default of 7.
    */
   GameView() {
      this(DEFAULT_CARDS_PER_HAND);
   }
   
   /**
    * Creates labels and buttons for CardTable and places them in the frame.
    * 
    * Call setComputerHand and setHumanHand to place image icons on those 
    * labels and buttons.
    */
   public void init()
   {
      //Create labels and buttons for both hands
      for(int i = 0; i < numCardsPerHand; i++) {
         computerHandLabels[i] = new JLabel();
         computerHandLabels[i].setHorizontalAlignment(JLabel.CENTER);
         humanHandButtons[i] = new JButton();
         humanHandButtons[i].setActionCommand(Integer.toString(i));
      }
      
      playedCardLabels[COMPUTER] = new JButton("Computer: 0 ");
      playedCardLabels[COMPUTER].setIcon(new ImageIcon());
      playedCardLabels[COMPUTER].setHorizontalAlignment(JLabel.CENTER);
      playedCardLabels[COMPUTER].setVerticalTextPosition(SwingConstants.BOTTOM);
      playedCardLabels[COMPUTER].setHorizontalTextPosition(SwingConstants.CENTER);
      playedCardLabels[COMPUTER].setActionCommand(Integer.toString(0));

      //Set played card invisible
      playedCardLabels[HUMAN] = new JButton("Human: 0 ");
      playedCardLabels[HUMAN].setIcon(new ImageIcon());
      playedCardLabels[HUMAN].setHorizontalAlignment(JLabel.CENTER);
      playedCardLabels[HUMAN].setVerticalTextPosition(SwingConstants.BOTTOM);
      playedCardLabels[HUMAN].setHorizontalTextPosition(SwingConstants.CENTER);
      playedCardLabels[HUMAN].setActionCommand(Integer.toString(2));

      //Set played card invisible
      messageLabel = new JButton();
      messageLabel.setHorizontalAlignment(JLabel.CENTER);
      messageLabel.setText("");
      messageLabel.setHorizontalAlignment(JLabel.CENTER);
      messageLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
      messageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
      messageLabel.setActionCommand(Integer.toString(1));

      //Add jlabels to play area
      myCardTable.pn1PlayArea.add(playedCardLabels[COMPUTER]);
      myCardTable.pn1PlayArea.add(messageLabel);
      myCardTable.pn1PlayArea.add(playedCardLabels[HUMAN]);

      // Add the cannot play JButton
      cannotPlay = new JButton();
      cannotPlay.setText("I cannot play");
      myCardTable.pn1PlayArea.add(cannotPlay);
   }
   
   /**
    * Returns CardTable(derived from JFrame).
    * 
    * Useful for making frame visible.
    * @return CardTable
    */
   public CardTable getFrame() { return myCardTable; }
   
   public void clearComputerHand() {
      myCardTable.pn1ComputerHand.removeAll();
      myCardTable.pn1ComputerHand.revalidate();
      myCardTable.pn1ComputerHand.repaint();
   }
      
   public void clearHumanHand() {
      myCardTable.pn1HumanHand.removeAll();
      myCardTable.pn1HumanHand.revalidate();
      myCardTable.pn1HumanHand.repaint();
   }
   
   /**
    * Sets computer hand with numCards of face down cards.
    * 
    * @param numCards
    * @param cardBackIcon
    */
   public void setComputerHand(int numCards, Icon cardBackIcon) {
      for(int i = 0; i < numCards; i++)
      {
         computerHandLabels[i].setIcon(cardBackIcon);
         myCardTable.pn1ComputerHand.add(computerHandLabels[i]);
      }
   }
   
   /**
    * Sets computer hand with icons in humanHandIcons.
    * 
    * Note: Doesn't create new JButtons. Just moves around icon images.
    * @param humanHandIcons
    */
   public void setHumanHand(Icon[] humanHandIcons) {
      for(int i = 0; i < humanHandIcons.length; i++)
      {
         humanHandButtons[i].setIcon(humanHandIcons[i]);
         //humanHandButtons[i].setActionCommand(Integer.toString(i));
         myCardTable.pn1HumanHand.add(humanHandButtons[i]);
      }
   }
   
   /**
    * Returns the array of JButtons for cards in the human's hand.
    * 
    * @return JButton[]
    */
   public JButton[] getHumanHand() { return humanHandButtons; }
    /**
    * Returns the array of JButtons for cards in the human's hand.
    * 
    * @return JButton[]
    */
    public JButton[] getPileButtons() { return new JButton[] {playedCardLabels[COMPUTER], messageLabel, playedCardLabels[HUMAN]}; }
   
   /**
    * Changes the text under the computer's card in the play area.
    * 
    * @param score
    */
   public void setComputerScore(int score) {
      playedCardLabels[COMPUTER].setText("Computer: " + Integer.toString(score));
   }
   
   /**
    * Changes the text under the human's card in the play area.
    * 
    * @param score
    */
   public void setHumanScore(int score) {
      playedCardLabels[HUMAN].setText("Human: " + Integer.toString(score));
   }
   
   /**
    * 
    * @param text
    */
   public void setMessageLabel(String text) {
      messageLabel.setText(text);
   }
   
   /**
    * Getter for the cannot play button
    * @return JButton for the cannot play button
    */
   public JButton getCannotPlay() {
      return cannotPlay;
   }

   /**
    * Changes icon on computer's card in play area.
    * 
    * @param cardIcon
    */
   public void playComputerCard(Icon cardIcon) {
      playedCardLabels[COMPUTER].setIcon(cardIcon);
   }
   
   /**
    * Changes icon on human's card in play area.
    * 
    * @param cardIcon
    */
   public void playHumanCard(Icon cardIcon) {
      playedCardLabels[HUMAN].setIcon(cardIcon);
   }
   
   /**
    * Sets both card icons in play area to card backs.
    * 
    * @param cardBackIcon
    */
   void resetPlayArea(Icon cardBackIcon) {
      playedCardLabels[COMPUTER].setIcon(cardBackIcon);
      playedCardLabels[HUMAN].setIcon(cardBackIcon);
   }

   /**
    * Update the icons for the piles
    * @param init
    */
   public void initPile(Card [] init) {
      playedCardLabels[COMPUTER].setIcon(GUICard.getIcon(init[0]));
      messageLabel.setIcon(GUICard.getIcon(init[1]));
      playedCardLabels[HUMAN].setIcon(GUICard.getIcon(init[2]));

   }
   
}
