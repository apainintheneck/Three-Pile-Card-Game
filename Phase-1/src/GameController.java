import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

//Phase 1 Controller Class for Low Card Game
public class GameController
{
   private static final int HUMAN = 1;
   private static final int COMPUTER = 0;
   
   GameModel model;
   GameView view;
   
   GameController(GameModel model, GameView view) {
      this.model = model;
      this.view = view;
      init();
   }
   
   private void init() {
      if(model.dealNewHands()) {
         model.sortHands();
         
         view.setComputerHand(model.getNumCardsPerHand(), GUICard.getBackCardIcon());
         view.setHumanHand(model.getHumanHandIcons());
         for(int i = 0; i < model.getNumCardsPerHand(); i++) {
            view.getHumanHand()[i].addActionListener(ae -> chooseCard(ae));
         }
      }
   }

   private void chooseCard(ActionEvent ae)
   {
      String action = ae.getActionCommand();
      int k = Integer.parseInt(action);
   }
   
   public void duel (){
      
      String status = "Player Wins";
      model.setUserMove(1);
      int cardIndex = model.getHand(0).getNumCards()-1;
      for (; cardIndex>=0; cardIndex--){
         // check if cpu's card's value is less than player's card's value
         if (model.compareCards(model.getPlayedCard(HUMAN).getValue(), 
               model.getHand(COMPUTER).inspectCard(cardIndex).getValue()) == -1) {
               status = "CPU Wins";
               model.setUserMove(0);
               model.incrementComputerScore();
               view.setComputerScore(model.getComputerScore());
               break;
         }
      }
      if (cardIndex<0){
         cardIndex = 0;
      }
      if (model.compareCards(model.getPlayedCard(HUMAN).getValue(), 
            model.getHand(COMPUTER).inspectCard(cardIndex).getValue()) == 0) {
         status = "It's a tie!";
         model.setUserMove(1);
      }
      else if(model.getUserMove() != 0){
         model.incrementHumanScore();
         view.setHumanScore(model.getHumanScore());
      }  
      view.setMessageLabel(status);
   
      Icon cardIcon = GUICard.getIcon(model.playCard(COMPUTER, cardIndex));
      view.playComputerCard(cardIcon);
   }

}
