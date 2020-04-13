//Card Class
//Imitates a playing card by holding suit and value.
//Author: Bryce Randolph

import java.lang.*;

public class Card
{
   public enum Suit 
   {
      CLUBS, DIAMONDS, HEARTS, SPADES
   }

   private char value; //14 value options
   
   private Suit suit; //4 suit options
   
   private boolean errorFlag;
   
   //array holds char values with the smallest first
   public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7', '8',
         '9', 'T', 'J', 'Q', 'K', 'X'};
   
   //no parameter constructor
   public Card()
   {
      set('A', Suit.SPADES);
   }
   
   //value and suit constructor
   public Card(char newValue, Suit newSuit)
   {
      if(set(newValue, newSuit) == false)
         errorFlag = true;
      
      else
         set(newValue, newSuit);
   }
   
   //value only constructor
   public Card (char newValue)
   {
      if(set(newValue, Suit.SPADES) == false)
         errorFlag = true;
      
      else
         set(newValue, Suit.SPADES);
   }
  
   //suit only constructor
   public Card (Suit newSuit)
   {
      if(set('A', newSuit) == false)
         errorFlag = true;
      
      else 
         set('A', newSuit);
   }
   
   //Copy Constructor
   public Card (Card oldCard)
   {
      value = oldCard.getValue();
      suit = oldCard.getSuit();
      errorFlag = oldCard.getError();
   }
   
   //searches valuRanks array and returns which value the passed char has 
   static private int cardValueToInt(char cardValue) 
   {
      int cardValueNum = 0;

      for(char value : Card.valuRanks)
      {
         if(cardValue == value) {
            return cardValueNum;

         }
         else
            cardValueNum++;
      }
      return -1;
   }
   
   //bubble sort routine to order incoming array of cards 
   public static void arraySort(Card[] card, int arraySize) 
   {
      for(int i = 0; i < arraySize; i++)
         for(int j = 0; j < (arraySize - i - 1); j++)
            if(cardValueToInt(card[j].value) > cardValueToInt(card[j + 1].value))
             {
                 //swap temp and arr[i]
                 Card temp = card[j];
                 card[j] = card[j + 1];
                 card[j + 1] = temp;
             }   
   }

   //accessor for card value
   public char getValue()
   {
        return value;
   }
   
   //accessor for card Suit
   public Suit getSuit()
   {
        return suit;
   }
   
   //accessor for errorFlag  
   public boolean getError() 
   {
      return errorFlag;
   }

   //mutator for value and suit, also sets errorFlag
   public boolean set(char newValue, Suit newSuit)
   {
      errorFlag = !isValid(newValue, newSuit);
      
      if(errorFlag == false)
      {
         this.value = newValue;
         suit = newSuit;
      }
      
      return !errorFlag;  
   }
   
   //returns true if all fields are identical and false if not
   public boolean equals(Card theCard)
   {
      if(value == theCard.value && suit == theCard.suit
            && errorFlag == theCard.errorFlag)
         return true ;
         
      else
         return false; 
   }
   
   public String toString()
   {
      if(errorFlag == true) 
         return "[invalid]";
     
      else
          return getValue() + " of " + getSuit();
   }

   private boolean isValid(char passedValue, Suit passedSuit) 
   {
      boolean valueValid = false;
      
      //iterates through all legal values and checks passedValue against them
      for (char element : valuRanks)
      {
         if(element == passedValue)
         {
            valueValid = true;
            break;
         }

         else
            valueValid = false;
      }
      /* 
      boolean suitValid = false;
      
      //iterates through all legal suits and checks passedSuit against them
      //currently don't need this according to spec
      for (Suit element : Suit.values())
      {
         if(element == passedSuit)
         {
            suitValid = true;
            break;
         }
         
         else
            suitValid = false;
      }
      */
      return valueValid; //&& suitValid ; 
   }

}
