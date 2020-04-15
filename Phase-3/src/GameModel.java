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
   
   private int numPlayers;
   private int numCardsPerHand;
   
   private Card playedCards[];

   private Card piles[];

   private int compScore = 0;
   private int userScore = 0;
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

   /**
    * Card validator to check if the card played is valid based on the target pile card 
    *
    * @param human the card to check against the pile
    * @param pile the pile card to check
    * @return true if the card is valid, false if not
    */
   public boolean validateCard(Card human, Card pile){
      int i = 0;
      int j = 0;
      for (; i<Card.valuRanks.length; i++){
         if (human.getValue() == Card.valuRanks[i]){
            break;
         }
      }
      for (; j<Card.valuRanks.length; j++){
         if (pile.getValue() == Card.valuRanks[j]){
            break;
         }
      }
      // If the card to check is not immediately larger/smaller than the pile card
      // or equal to the pile card, return false
      if((i < (j - 1) || i > (j + 1)) || (i == j)) {
         return false;
      }
      return true;
   }
   
   //Getter and setter for userMove
   public int getUserMove() { return userMove; }
   public void setUserMove(int userMove) { this.userMove = userMove; }
   
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
   /**
    * Initialize or re-deal cards to the piles
    * 
    * @return card []
    */
    public Card [] initializePiles() {
      
      Card [] pile = new Card [3];
      int pileSize = 3;

      // If there are less than 3 cards left in the deck, only refresh the first n piles
      // where n is the number of cards left in the deck.
      if(getNumCardInDeck() < pileSize) {
         pileSize = getNumCardInDeck();
      }
      for(int i = 0; i < pileSize; i++) {
         pile[i] = LowCardGame.getCardFromDeck();
      }
      
      piles = pile;
      return pile;
   }

   /**
    * Plays the card from the specified user hand to the target pile 
    *
    * @param userInd the index of the user
    * @param handInd the index of the card inside the hand
    * @param pileInd the index of the pile to play the card to
    * @return true if the card is succesfully played, false if not
    */
   public boolean playCardToPile(int userInd, int handInd, int pileInd) {
      // If the card is valid to the pile, play the card to the pile and update the internal pile
      if(validateCard(getHand(userInd).inspectCard(handInd), getPiles()[pileInd])) {
         playCard(userInd, handInd); 
         piles[pileInd] = playedCards[userInd];
         return true;
      } else {
         return false;
      }
   }

   /**
    * Finds a card inside the specified user's hand to play to a pile 
    *
    * @param userInd index of user to find a move for
    * @return true if a move for the specified user can be made, false if not
    */
   public boolean findAndPlayCardToPile(int userInd) {
      int numCardsInHand = getHand(userInd).getNumCards();
      // Iterates through the piles and the hand and plays the first card in the hand that can be played
      for(int j = 0; j < 3; j++) {
         for(int i = 0; i < numCardsInHand; i++) {
            if(playCardToPile(userInd, i, j)) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Getter for the piles
    *
    * @return Card[] piles
    */
   public Card[] getPiles() {
      return piles;
   }
   
   /**
    * Getter for the cannot-play counter for the computer 
    *
    * @return the cannot-play counter for the computer
    */
   public int getCompScore() {
      return compScore;
   }

   /**
    * Setter for the computer cannot-play counter
    * @param compScoreSet the score to update
    */
   public void setCompScore(int compScoreSet) {
      compScore = compScoreSet;
   }

   /**
    * Getter for the cannot-play counter for the human 
    *
    * @return the cannot-play counter for the human
    */
   public int getUserScore() {
      return userScore;
   }

   /**
    * Setter for the human cannot-play counter
    * @param userScoreSet the score to update
    */
   public void setUserScore(int userScoreSet) {
      userScore = userScoreSet;
   }


   /**
    * Determines the winner for the game based on the user and computer score
    *
    * @return
    */
   public int determineWinner() {
      // If the user has less cannot-plays than the computer, user wins
      if(userScore < compScore) {
         return 1;
      }
      // If the computer has less cannot-plays than the computer, user loses 
      else if (userScore > compScore) {
         return -1;
      }
      // If there is a tie 
      else {
         return 0;
      }
   }
   //Getters for GUICard methods
   public Icon getCardIcon(Card card) { return GUICard.getIcon(card); }
   public Icon getCardBackIcon() { return GUICard.getBackCardIcon(); }
}
