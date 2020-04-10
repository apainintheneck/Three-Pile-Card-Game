import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

//Purpose: Creates a card table window.
//It has space for the computer's hand, the human's hand, and the play area.
public class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
   
   private int numCardsPerHand;
   private int numPlayers;
   
   //Create three panels for the window.
   public JPanel pn1ComputerHand, pn1HumanHand, pn1PlayArea;
   
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super();
      
      setTitle(title);
      
      //Validate cards per hand
      if(numCardsPerHand < 0 || numCardsPerHand > MAX_CARDS_PER_HAND)
         this.numCardsPerHand = MAX_CARDS_PER_HAND;
      else
         this.numCardsPerHand = numCardsPerHand;
      
      //Validate number of players
      if(numPlayers < 0 || numPlayers > MAX_PLAYERS)
         this.numPlayers = MAX_PLAYERS;
      else
         this.numPlayers = numPlayers;
      
      this.setLayout(new GridLayout(3, 1));
      
      //Setup Computer Hand panel. This panel holds the computers hand and is 
      //set up with the GridLayout. The grid is 1x(number of cards per hand).
      pn1ComputerHand = new JPanel();
      Border ComputerHandBorder = BorderFactory.createTitledBorder("Computer Hand");
      pn1ComputerHand.setBorder(ComputerHandBorder);
      pn1ComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
      
      //Setup Human Hand panel. This panel holds the humans hand and is set up
      //with the GridLayout. The grid is 1x(number of cards per hand).
      pn1HumanHand = new JPanel();
      Border HumanHandBorder = BorderFactory.createTitledBorder("Human Hand");
      pn1HumanHand.setBorder(HumanHandBorder);
      pn1HumanHand.setLayout(new GridLayout(1, numCardsPerHand));
      
      //Setup Play Area panel. This panel holds the cards played and is set up
      //as a GridLayout. The grid is 1x(number players + 1).
      pn1PlayArea = new JPanel();
      Border PlayAreaBorder = BorderFactory.createTitledBorder("Play Area");
      pn1PlayArea.setBorder(PlayAreaBorder);
      pn1PlayArea.setLayout(new GridLayout(1, numPlayers + 1));
      
      //Add panels to JFrame.
      this.add(pn1ComputerHand);
      this.add(pn1PlayArea);
      this.add(pn1HumanHand);
   }
   
   CardTable()
   {
      this("CardTable", MAX_PLAYERS, MAX_CARDS_PER_HAND);
   }
   
   public int getNumCardsPerHand() { return numCardsPerHand; }
   public int getNumPlayers() { return numPlayers; }
}
