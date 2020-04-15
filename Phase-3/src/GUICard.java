import javax.swing.Icon;
import javax.swing.ImageIcon;

//Purpose: Initializes a 2d array of card Icons.
//Allows these icons to be accessed through getters.
public class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   
   private static String[] suitArray = {"C", "D", "H", "S"};
   
   GUICard()
   {
      if(iconsLoaded == false)
         loadCardIcons();
   }
   
   //Purpose: A one time initialization of a 2d array of card Icons.
   static private void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop. For each file name, read it in and use it to
      // instantiate each of the 56 Icons in a 2d array + set the iconBack as well.
      for(int numSuit = 0; numSuit < 4; numSuit++)
      {
         for(int numCardValue = 0; numCardValue < 14; numCardValue++)
         {
            iconCards[numCardValue][numSuit] = new ImageIcon("images/" + 
                  intToCardValue(numCardValue) + intToSuit(numSuit) + ".gif");
         }      
      }
      
      iconBack = new ImageIcon("images/" + "BK" + ".gif");
      
      iconsLoaded = true;
   }
   
   //Purpose: Finds and returns matching Icon to given Card.
   //Parameter: Card
   //Returns: Icon
   static public Icon getIcon(Card card)
   {
      if(iconsLoaded == false)
         return new ImageIcon();
      
      int cardValueNum = cardValueToInt(card.getValue());
      int suitNum = suitToInt(card.getSuit());
    
      if(cardValueNum == -1 || suitNum == -1)
         return iconBack;
      else
         return iconCards[cardValueNum][suitNum];
   }
   
   static public Icon getBackCardIcon()
   {
      if(iconsLoaded == false)
         return new ImageIcon();
      
      return iconBack;
   }
   
   static private String intToSuit(int numSuit) 
   { 
      return suitArray[numSuit]; 
   };
   
   static private char intToCardValue(int numCardValue) 
   { 
      return Card.valuRanks[numCardValue];
   };
   
   //Purpose: Changes Card.Suit enum to Int
   //Receives: Card.Suit enum
   //Returns: Value of suit or -1 if input suit is invalid.
   static private int suitToInt(Card.Suit cardSuit) 
   {
      int suitNum = 0;
      
      //Look for equivalent suit value
      for(Card.Suit suit : Card.Suit.values())
      {
         if(cardSuit == suit)
            return suitNum;
         else
            suitNum++;
      }
      
      //If suit is not valid, return -1.
      return -1;
   };
   
   //Purpose: Changes Card.Suit enum to Int
   //Receives: Card.Suit enum
   //Returns: Value of suit or -1 if input suit is invalid.
   static private int cardValueToInt(char cardValue) 
   {
      int cardValueNum = 0;
      
      //Look for equivalent card value
      for(char value : Card.valuRanks)
      {
         if(cardValue == value)
            return cardValueNum;
         else
            cardValueNum++;
      }
      
      //If card value is not valid, return -1.
      return -1;
   };
}
