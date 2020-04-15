//Hand Class
//A class that imitates a hand of cards. 
//Author: Harsandeep Singh, Bryce Randolph
public class Hand {
   Card[] myCards;
   int numCards;
   public static final int MAX_CARDS = 56;
  
   public Hand(){
       myCards = new Card[MAX_CARDS];
       numCards = 0;
   }
   
   //Use lazy deletion to reset hand to zero cards.
   public void resetHand(){ numCards = 0; }
  
   public boolean takeCard(Card card){
      
       // find the next available position in myCards
       int index = numCards;
      
       // if index is greater than max cards return false
       if(index >= MAX_CARDS){
           return false;
       }
       // insert card into last available index using a deep copy.
       else{
           myCards[index] = new Card(card);
           numCards++;
           return true;
           
       }
      
   }
  
   public String toString(){
      
      String hand = "";
  
      // Iterate through myCards array
      for(int i = 0; i<numCards; i++){
          
         hand = hand + myCards[i].toString() + ",";
      }
      
      // Remove trailing comma
      hand = hand.replaceAll(",$", "");
      
      // Insert output string into parenthesis 
      hand = "Hand = (" + hand + ")";
      return hand;
       
   }
   
   public int getNumCards() 
   { 
      return numCards; 
   }
   
   //sort the hand by calling the arraySort() method in the Card class
   void sort()
   {
      Card.arraySort(myCards, numCards);
   }
   
   //revised method that removes the card at a location and slides
   //all of the cards down one spot in the myCards array
   public Card playCard(int cardIndex)
   {
      if ( numCards == 0 ) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);
      }
      
      //Decreases numCards.
      Card card = myCards[cardIndex];
      
      numCards--;
      
      for(int i = cardIndex; i < numCards; i++)
         myCards[i] = myCards[i+1];

      myCards[numCards] = null;
      
      return card;
    }
   
   public Card inspectCard(int k){
      
      //If card is not in the hand, return an invalid card.
      if(k >= numCards || k < 0) { return new Card('b'); }
      //Else return the card asked for.
      else { return myCards[k]; }
       
   }

}