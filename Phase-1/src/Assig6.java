/**
 * Main for new MVC design of Low Card Game.
 * 
 * @author kevinrobell
 *
 */
public class Assig6
{

   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;
      int numPlayers = 2;
      int numCardsPerHand = 7;
      
      GameView view = new GameView(7);
      GameModel model = new GameModel(numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, numPlayers, numCardsPerHand);
      GameController controller = new GameController(model, view);
      
      view.getFrame().setVisible(true);
   }

}
