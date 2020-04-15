import java.util.Random; 

/**
 * @author Jeannie Davis, Bryce Randolph
 * @date 3-23-20 
 * Deck Class
 */

public class Deck 
{
   public final int MAX_CARDS = 336;
   private static Card masterPack [];
   private Card[] cards;
   private int topCard;
   /**
    * Default Constructor
    */
   public Deck() 
   {
      allocateMasterPack();
      init(1);
   }
   /**
    * overloaded constructor
    * @param numPacks - number of packs in the deck
    */
   Deck (int numPacks)
   {
      allocateMasterPack();
      init(numPacks);
   }
     /**
      * initialize the card array to the size of the number of packs in the deck * 56
      * @param numPacks - the number of packs in the deck
      */
   public void init(int numPacks) 
   {
      if (numPacks * 56 > MAX_CARDS || numPacks <= 0){
         //Set to default if invalid value
         numPacks = 1;
      }
        cards = new Card [numPacks * 56];
        int cardCounter = 0;
        topCard = numPacks * 56 - 1;
        
        for (int x = 0; x < numPacks; x++){
            for (int i = 0; i < masterPack.length; i++){
                cards[cardCounter] = masterPack[i];
                cardCounter++;
            }
        }
    }
    /**
     * this method will shuffle the deck 
     * uses a random number generator to generate a number between 0 and the deck's length -1 
     */
   public void shuffle (){
      Random rand = new Random(); 
      for (int i = 0; i < topCard; i++) 
      { 
         // Random for remaining positions. 
         int r = i + rand.nextInt(topCard + 1 - i); 
         //swapping the elements 
         Card temp = cards[r]; 
         cards[r] = cards[i]; 
         cards[i] = temp; 
      } 
    }
    /**
     * This function returns and removes the card that is in index 0. 
     * Then it will reposition the remaining cards
     * IF there is no cards in the deck it will return a card with errorFlag = true;
     * @return the top Card
     */
   public Card dealCard()
   {
      if (topCard < 0)
      {
         return new Card('x');
      }
      else 
      {
         Card temp = cards[topCard];
         topCard--;
         return temp;
      }
    }
    /**
     * this function will only run once at the very beginning to initialize masterPack. 
     */
   private static void allocateMasterPack()
   {
      int x = 0;
      int deckValueCounter = 0;
      
      if (masterPack != null) 
         return;

      else 
      {
         masterPack = new Card [56];
         for (int i = 0; i < 56; i++)
         {
            if (i != 0 && i%14 == 0)
            {
               x++;
               deckValueCounter = 0;
            }
            masterPack[i] = new Card(Card.valuRanks[deckValueCounter], 
                  Card.Suit.values()[x]);
            deckValueCounter++;
         }
      }  
    }
    /**
     * This accessor will return top card
     * @return integer of the top card's index
     */
   public int getTopCard(){
      return topCard;
    }
    /**
     * 
     * @param k - the placement of the card we want to find
     * @return - IF the card exists, will return the card at position k -- otherwise will return a card with errorFlag = true
     */
   public Card inspectCard(int k) {

      if (k < 0 || k > topCard){
         return new Card('M');
      }
      return cards[k];

   }
   
   //adds card to top of deck if there are not already too many of it in deck
   public boolean addCard(Card card)
   {
      //Check if number in deck is less than max.
      //This part: (cards.length / 56) checks how many decks are in use.
      //Each deck has a duplicate.
      if(countInDeck(card) < (cards.length / 56))
      {
         //check if deck is already full.
         if(cards.length <= getTopCard() + 1)
         {
            //Return false since deck is already full.
            topCard = cards.length - 1;
            return false;
         }
         else //Add card to the top of the deck.
         {
            topCard++;
            cards[getTopCard()] = card;
            return true;
         }
      }
      else //Deck already contains the max of given card.
         return false;

   }

   //remove a specific card from the deck.  Put the current top card into 
   //its place.  Be sure the card you need is actually still in the deck, 
   //if not return false.
   public boolean removeCard(Card card)
   {
      for(int i = 0; i <= topCard; i++)
      {
         if(card.equals(cards[i]))
         {
            cards[i] = cards[getTopCard()];
            topCard--;
            return true;
         }
      }
      
      return false;
   }
   
   //put all of the cards in the deck back into the right order by value
   public void sort()
   {
      Card.arraySort(cards, cards.length);
      
   }

   public int getNumCards(){
      return getTopCard()+1;
   }

   //Counts the number of a given card in the deck.
   private int countInDeck(Card card)
   {
      int count = 0;
      
      for(int i = 0; i <= topCard; i++)
      {
         if(card.equals(cards[i]))
            count++;     
      }

      return count;
   }

}

