import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Model class for MVC design.
 * 
 * @author kevinrobell
 *
 */
public class GameModel
{  
   private CardGameFramework LowCardGame;
   //Need cardIcons to load card icons even though compiler says it's unused.
   private GUICard cardIcons = new GUICard();
   
   private int userMove = 1;
   private int computerScore = 0;
   private int humanScore = 0;
   
   private int numPlayers;
   private int numCardsPerHand;
   
   private Card playedCards[];
   
   /**
    * Constructor
    * 
    * @param numPacksPerDeck
    * @param numJokersPerPack
    * @param numUnusedCardsPerPack
    * @param unusedCardsPerPack
    * @param numPlayers
    * @param numCardsPerHand
    */
   GameModel(int numPacksPerDeck, int numJokersPerPack, int numUnusedCardsPerPack, 
         Card[] unusedCardsPerPack, int numPlayers, int numCardsPerHand) {
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      playedCards = new Card[numPlayers];
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
   
   /**
    * Removes card from Hand and places it in playedCard array at playerIndex.
    * 
    * @param playerIndex
    * @param cardIndex
    */
   public void playCard(int playerIndex, int cardIndex) {
      playedCards[playerIndex] = LowCardGame.playCard(playerIndex, cardIndex);
   }
   public Card getPlayedCard(int playerIndex) { return playedCards[playerIndex]; }
   
   /**
    * Returns true if the player was able to take a card.
    * 
    * @param playerIndex
    * @return boolean
    */
   public boolean takeCard(int playerIndex) { return LowCardGame.takeCard(playerIndex); }
   
   /**
    * Returns 1 if human wins. Returns 0 for ties. Returns -1 if computer wins.
    * 
    * @param human
    * @param computer
    * @return int
    */
   public int compareCards(Card human, Card computer){
      int i = 0;
      int j = 0;
      for (; i<Card.valuRanks.length; i++){
         if (human.getValue() == Card.valuRanks[i]){
            break;
         }
      }
      for (; j<Card.valuRanks.length; j++){
         if (computer.getValue() == Card.valuRanks[j]){
            break;
         }
      }
      // if the two cards are equal
      if (i == j){
         return 0;
      }
      // if the human lost to the computer
      else if (j<i){
         return -1;
      }
      //otherwise human won
      else{
         return 1;
      }
   }
   
   //Getter and setter for userMove
   public int getUserMove() { return userMove; }
   public void setUserMove(int userMove) { this.userMove = userMove; }
   
   //Getters and setters for scores
   public int getHumanScore() { return humanScore; }
   public void incrementHumanScore() { humanScore++; }
   public int getComputerScore() { return computerScore; }
   public void incrementComputerScore() { computerScore++; }
   
   /**
    * Create an array of card icons based upon the human's hand.
    * 
    * @return Icon[]
    */
   public Icon[] getHumanHandIcons() {
      int numCardsInHand = getHand(1).getNumCards();
      
      Icon[] humanHandIcons = new ImageIcon[numCardsInHand];
      for(int i = 0; i < numCardsInHand; i++) {
         humanHandIcons[i] = GUICard.getIcon(getHand(1).inspectCard(i));
      }
      
      return humanHandIcons;
   }
   
   //Getters for GUICard methods
   public Icon getCardIcon(Card card) { return GUICard.getIcon(card); }
   public Icon getCardBackIcon() { return GUICard.getBackCardIcon(); }
}
