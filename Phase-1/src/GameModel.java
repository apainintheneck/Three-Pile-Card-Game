//Phase 1 Model Class for Low Card Game
public class GameModel
{
   CardGameFramework LowCardGame;
   GUICard cardIcons = new GUICard();
   
   GameModel(int numPacksPerDeck, int numJokersPerPack, int numUnusedCardsPerPack, 
         Card[] unusedCardsPerPack, int numPlayers, int numCardsPerHand) {
      
      LowCardGame = new CardGameFramework(numPacksPerDeck, numJokersPerPack, 
            numUnusedCardsPerPack, unusedCardsPerPack, numPlayers, numCardsPerHand);
   }
   
   //Getters and setters from CardGameFramework
   public boolean dealNewHands() { return LowCardGame.deal(); }
   public void sortHands() { LowCardGame.sortHands(); }
   public Hand getHand(int handNum) { return LowCardGame.getHand(handNum); }
   public int getNumCardInDeck() { return LowCardGame.getNumCardsRemainingInDeck(); }
   public Card playCard(int playerIndex, int cardIndex) {
      return LowCardGame.playCard(playerIndex, cardIndex);
   }
   public boolean takeCard(int playerIndex) { return LowCardGame.takeCard(playerIndex); }
}
