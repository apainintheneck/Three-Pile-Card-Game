import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.Timer;

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
      model.playCard(HUMAN, k);
      
      String status;
      view.playHumanCard(model.getCardIcon(model.getHand(HUMAN).inspectCard(k)));
      // if user goes first
      if (model.getUserMove() == HUMAN){
         computerPlaysSecond();
      }
      //otherwise computer already went first
      else {
         //Play
         int result = model.compareCards(model.getPlayedCard(HUMAN).getValue(), 
               model.getPlayedCard(COMPUTER).getValue());
         if (result == 0){
            status = "It's a tie!";
            model.setUserMove(HUMAN);
         }
         else if (result == 1){
            status = "Player Wins";
            model.setUserMove(HUMAN);
            model.incrementHumanScore();
            view.setHumanScore(model.getHumanScore());
         }
         else {
            status = "Computer Wins";
            model.setUserMove(COMPUTER);
            model.incrementComputerScore();
            view.setHumanScore(model.getHumanScore());
         }
         //score.setText(status);
         view.setMessageLabel(status);
       }
       // plays the card that was clicked on
       model.playCard(HUMAN,k);
       // render the cpu and user hands
       view.resetComputerHand();
       view.resetHumanHand();
       //if there's no cards left in your hand the game is over
       if (model.getHand(HUMAN).getNumCards() == 0) {
          Timer timer4 = new Timer(2000, e -> {
             view.playHumanCard(model.getCardBackIcon());
             view.playComputerCard(model.getCardBackIcon());
             if (model.getComputerScore() < model.getHumanScore()) {
                view.setMessageLabel("<html>Game Over <br> You Won!</html>");
             }
             else if (model.getComputerScore() > model.getHumanScore()) {
                view.setMessageLabel("<html>Game Over <br> You Lost!</html>");
             }
             else {
                view.setMessageLabel("<html>Game Over <br> You Tied!</html>");
             }
          });
          timer4.setRepeats(false);
          timer4.start();
          return;
       }
       // adding the cards to cpu and human hand (visually)
       view.setComputerHand(model.getHand(COMPUTER).getNumCards(), GUICard.getBackCardIcon());
       view.setHumanHand(model.getHumanHandIcons());
       // if cpu has first move after winning -- wait 2 seconds before showing it's card
       if (model.getUserMove() == COMPUTER){
          Timer timer4 = new Timer(2000, e -> {
             //Reset humans played card
             view.playHumanCard(model.getCardBackIcon());
             
             //Play card from the computer
             model.playCard(COMPUTER, 0);
             
             //Set card computer just played
             Icon cardIcon = model.getCardIcon(model.getPlayedCard(COMPUTER));
             view.playComputerCard(cardIcon);
          });
          timer4.setRepeats(false);
          timer4.start();
       }
       else {
          Timer timer4 = new Timer(2000, e -> {
             //Reset both players played cards
             view.playHumanCard(model.getCardBackIcon());
             view.playComputerCard(model.getCardBackIcon());
          });
          timer4.setRepeats(false);
          timer4.start();
       }
   }
   
   public void computerPlaysSecond(){
      
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
