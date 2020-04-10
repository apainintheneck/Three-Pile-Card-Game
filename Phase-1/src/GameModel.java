import javax.swing.Icon;
import javax.swing.ImageIcon;

//Phase 1 Model Class for Low Card Game
public class GameModel
{
   private CardGameFramework LowCardGame;
   private GUICard cardIcons = new GUICard();
   
   private static int userMove = 1;
   private static int computerScore = 0;
   private int humanScore = 0;
   
   private int numPlayers;
   private int numCardsPerHand;
   
   GameModel(int numPacksPerDeck, int numJokersPerPack, int numUnusedCardsPerPack, 
         Card[] unusedCardsPerPack, int numPlayers, int numCardsPerHand) {
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      LowCardGame = new CardGameFramework(numPacksPerDeck, numJokersPerPack, 
            numUnusedCardsPerPack, unusedCardsPerPack, numPlayers, numCardsPerHand);
   }
   
   //Getters and setters from CardGameFramework
   public boolean dealNewHands() { return LowCardGame.deal(); }
   public void sortHands() { LowCardGame.sortHands(); }
   public Hand getHand(int handNum) { return LowCardGame.getHand(handNum); }
   public int getNumCardInDeck() { return LowCardGame.getNumCardsRemainingInDeck(); }
   public int getNumCardsPerHand() { return numCardsPerHand; }
   public int getNumPlayers() { return numPlayers; }
   public Card playCard(int playerIndex, int cardIndex) {
      return LowCardGame.playCard(playerIndex, cardIndex);
   }
   public boolean takeCard(int playerIndex) { return LowCardGame.takeCard(playerIndex); }
   
   //Getter and update userMove
   public int getUserMove() { return userMove; }
   public void nextPlayerMove() { userMove = (userMove + 1) % numPlayers; }
   
   //Getters and setters for scores
   public int getHumanScore() { return humanScore; }
   public void incrementHumanScore() { humanScore++; }
   public int getComputerScore() { return computerScore; }
   public void incrementComputerScore() { computerScore++; }
   
   //Get icons using GUICard class
   public Icon[] getHumanHandIcons() {
      int numCardsInHand = getHand(1).getNumCards();
      
      Icon[] humanHandIcons = new ImageIcon[numCardsInHand];
      for(int i = 0; i < numCardsInHand; i++) {
         humanHandIcons[i] = GUICard.getIcon(getHand(1).inspectCard(i));
      }
      
      return humanHandIcons;
   }
   public Icon getCardIcon(Card card) { return GUICard.getIcon(card); }
   public Icon getCardBackIcon() { return GUICard.getBackCardIcon(); }
}
