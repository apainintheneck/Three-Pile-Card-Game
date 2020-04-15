import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * View class for MVC design.
 * 
 * @author kevinrobell
 *
 */
public class GameView
{
   private final static int NUM_PILES = 3;
   private final static int DEFAULT_CARDS_PER_HAND = 7;
   //Constants to make array values more readable
   private static final int PILE_2 = 1;
   private static final int PILE_1 = 0;
   private static final int PILE_3 = 2;

   private int numCardsPerHand;
   
   private CardTable myCardTable;
   private JLabel[] computerHandLabels;
   private JButton[] humanHandButtons; 
   private JButton[] piles  = new JButton[NUM_PILES]; 
   private JButton messageLabel = new JButton();
   private JButton cannotPlay;
   private JPanel infoArea, timerArea;
   
   /**
    * Constructor
    * @param numCardsPerHand
    */
   GameView(int numCardsPerHand) {
      if(numCardsPerHand <= 0)
         this.numCardsPerHand = DEFAULT_CARDS_PER_HAND;
      else
         this.numCardsPerHand = numCardsPerHand;
      
      myCardTable = new CardTable("Card Table", this.numCardsPerHand, NUM_PILES);
      
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
      
      //Setup Time area panel. This panel holds the timer for the game and have
      //start stop buttons. 
      timerArea = new JPanel();
      Border pn1TimerBorder = BorderFactory.createTitledBorder("Timer");
      timerArea.setBorder(pn1TimerBorder);
      timerArea.setLayout(new BorderLayout());
      
      //The Start/Stop timer button together. 
      Timer autoTimer = new Timer(true);
      JButton timerToggleButton = autoTimer.getButtonToStartTimer();
      timerToggleButton.setText("Start/Stop");
      timerToggleButton.setFont(new Font("Serif", Font.BOLD, 16));
      
      //Add time and timer start/stop button to timer area JPanel
      timerArea.add(timerToggleButton, BorderLayout.PAGE_END);
      timerArea.add(autoTimer, BorderLayout.CENTER);
      
      //Setup infoArea panel
      infoArea = new JPanel();
      infoArea.setLayout(new GridLayout(2,1));
      
      //Add timer to infoArea panel
      infoArea.add(timerArea);
      
      //Create and add the cannot play JButton
      cannotPlay = new JButton();
      cannotPlay.setText("I cannot play");
      infoArea.add(cannotPlay);
      
      //Add infoArea panel to playArea
      myCardTable.pn1PlayArea.add(infoArea);
      
      piles[PILE_1] = new JButton("Computer: 0 ");
      piles[PILE_1].setIcon(new ImageIcon());
      piles[PILE_1].setHorizontalAlignment(JLabel.CENTER);
      piles[PILE_1].setVerticalTextPosition(SwingConstants.BOTTOM);
      piles[PILE_1].setHorizontalTextPosition(SwingConstants.CENTER);
      piles[PILE_1].setActionCommand(Integer.toString(0));

      //Set played card invisible
      piles[PILE_2] = new JButton();
      piles[PILE_2].setHorizontalAlignment(JLabel.CENTER);
      piles[PILE_2].setText("");
      piles[PILE_2].setHorizontalAlignment(JLabel.CENTER);
      piles[PILE_2].setVerticalTextPosition(SwingConstants.BOTTOM);
      piles[PILE_2].setHorizontalTextPosition(SwingConstants.CENTER);
      piles[PILE_2].setActionCommand(Integer.toString(1));
      
      //Set played card invisible
      piles[PILE_3] = new JButton("Human: 0 ");
      piles[PILE_3].setIcon(new ImageIcon());
      piles[PILE_3].setHorizontalAlignment(JLabel.CENTER);
      piles[PILE_3].setVerticalTextPosition(SwingConstants.BOTTOM);
      piles[PILE_3].setHorizontalTextPosition(SwingConstants.CENTER);
      piles[PILE_3].setActionCommand(Integer.toString(2));

      //Add jlabels to play area
      myCardTable.pn1PlayArea.add(piles[PILE_1]);
      myCardTable.pn1PlayArea.add(piles[PILE_2]);
      myCardTable.pn1PlayArea.add(piles[PILE_3]);
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
    public JButton[] getPileButtons() { return piles; }
   
   /**
    * Changes the text under the computer's card in the play area.
    * 
    * @param score
    */
   public void setComputerScore(int score) {
      piles[PILE_1].setText("Computer: " + Integer.toString(score));
   }
   
   /**
    * Changes the text under the human's card in the play area.
    * 
    * @param score
    */
   public void setHumanScore(int score) {
      piles[PILE_2].setText("Human: " + Integer.toString(score));
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
      piles[PILE_1].setIcon(cardIcon);
   }
   
   /**
    * Changes icon on human's card in play area.
    * 
    * @param cardIcon
    */
   public void playHumanCard(Icon cardIcon) {
      piles[PILE_3].setIcon(cardIcon);
   }
   
   /**
    * Sets both card icons in play area to card backs.
    * 
    * @param cardBackIcon
    */
   void resetPlayArea(Icon cardBackIcon) {
      piles[PILE_1].setIcon(cardBackIcon);
      piles[PILE_3].setIcon(cardBackIcon);
   }

   /**
    * Update the icons for the piles
    * @param init
    */
   public void initPile(Card [] init) {
      piles[PILE_1].setIcon(GUICard.getIcon(init[0]));
      piles[PILE_2].setIcon(GUICard.getIcon(init[1]));
      piles[PILE_3].setIcon(GUICard.getIcon(init[2]));

   }
   
}
