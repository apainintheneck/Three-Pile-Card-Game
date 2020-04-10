import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

//Phase 1 Controller Class for Low Card Game
public class GameController
{
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

}
