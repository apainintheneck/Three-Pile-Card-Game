import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//Phase 1 View Class for Low Card Game
public class GameView
{
   private final static int NUM_PLAYERS = 2;
   private final static int DEFAULT_CARDS_PER_HAND = 7;
   
   private int numCardsPerHand;
   
   private CardTable myCardTable;
   private JLabel[] computerHandLabels;
   private JButton[] humanHandButtons; 
   private JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   private JLabel messageLabel = new JLabel();
   
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
   
   GameView() {
      this(DEFAULT_CARDS_PER_HAND);
   }
   
   //Set buttons and labels
   public void init(Icon cardBackIcon, Icon[] humanHandIcons)
   {
      //Set labels and buttons for both hands
      setComputerHand(numCardsPerHand, cardBackIcon);
      setHumanHand(humanHandIcons);
      
      playedCardLabels[0] = new JLabel("Computer: 0 ");
      playedCardLabels[0].setIcon(cardBackIcon);
      playedCardLabels[0].setHorizontalAlignment(JLabel.CENTER);
      playedCardLabels[0].setVerticalTextPosition(SwingConstants.BOTTOM);
      playedCardLabels[0].setHorizontalTextPosition(SwingConstants.CENTER);
      //Set played card invisible
      playedCardLabels[1] = new JLabel("Human: 0 ");
      playedCardLabels[1].setIcon(cardBackIcon);
      playedCardLabels[1].setHorizontalAlignment(JLabel.CENTER);
      playedCardLabels[1].setVerticalTextPosition(SwingConstants.BOTTOM);
      playedCardLabels[1].setHorizontalTextPosition(SwingConstants.CENTER);
      //Set played card invisible
      messageLabel = new JLabel();
      messageLabel.setHorizontalAlignment(JLabel.CENTER);
      messageLabel.setText("Play Low Card!");
   }
   
   public CardTable getFrame() { return myCardTable; }
   
   public void resetComputerHand() {
      myCardTable.pn1ComputerHand.removeAll();
      myCardTable.pn1ComputerHand.revalidate();
      myCardTable.pn1ComputerHand.repaint();
   }
      
   public void resetHumanHand() {
      myCardTable.pn1HumanHand.removeAll();
      myCardTable.pn1HumanHand.revalidate();
      myCardTable.pn1HumanHand.repaint();
   }
   
   public void setComputerHand(int numCards, Icon cardBackIcon) {
      for(int i = 0; i < numCards; i++)
      {
         computerHandLabels[i] = new JLabel(cardBackIcon);
         myCardTable.pn1ComputerHand.add(computerHandLabels[i]);
      }
   }
   
   public void setHumanHand(Icon[] humanHandIcons) {
      for(int i = 0; i < humanHandIcons.length; i++)
      {
         humanHandButtons[i] = new JButton(humanHandIcons[i]);
         myCardTable.pn1HumanHand.add(humanHandButtons[i]);
      }
   }
   
   public JButton[] getHumanHand() { return humanHandButtons; }
   
   public void setComputerScore(int score) {
      playedCardLabels[0].setText("Computer: " + score);
   }
   
   public void setHumanScore(int score) {
      playedCardLabels[1].setText("Human: " + score);
   }
   
   public void setMessageLabel(String text) {
      messageLabel.setText(text);
   }
   
   public void playComputerCard(Icon cardIcon) {
      playedCardLabels[0].setIcon(cardIcon);
   }
   
   public void playHumanCard(Icon cardIcon) {
      playedCardLabels[1].setIcon(cardIcon);
   }
   
   void resetPlayArea(Icon cardBackIcon) {
      playedCardLabels[0].setIcon(cardBackIcon);
      playedCardLabels[1].setIcon(cardBackIcon);
   }
   
}
