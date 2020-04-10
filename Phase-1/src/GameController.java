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
      
      LowCardGame.getHand(1).inspectCard(k);
      String status;
       human.setIcon(GUICard.getIcon(LowCardGame.getHand(1).inspectCard(k)));
       // if user goes first
       if (userMove ==1){
          int cpu = duel(LowCardGame.getHand(1).inspectCard(k), LowCardGame.getHand(0));
          computer.setIcon(GUICard.getIcon(LowCardGame.playCard(0,cpu)));
       }
       //otherwise cpu goes first
       else {
          int result = checkCards(LowCardGame.getHand(1).inspectCard(k).getValue(), LowCardGame.playCard(0,0).getValue());
          if (result == 0){
             status = "It's a tie!";
             userMove =1;
          }
          else if (result == 1){
             status = "Player Wins";
             userMove = 1;
             humanScore++;
             human.setText("Human: " + Integer.toString(humanScore));
          }
          else {
             status = "CPU Wins";
             userMove = 0;
             cpuScore++;
            computer.setText("Computer: " + Integer.toString(cpuScore));
          }
          score.setText(status);
       }
       // plays the card that was clicked on
       LowCardGame.playCard(1,k);
       // render the cpu and user hands
       myCardTable.pn1ComputerHand.removeAll();
       myCardTable.pn1ComputerHand.revalidate();
       myCardTable.pn1ComputerHand.repaint();
       myCardTable.pn1HumanHand.removeAll();
       myCardTable.pn1HumanHand.revalidate();
       myCardTable.pn1HumanHand.repaint();
       //if there's no cards left in your hand the game is over
       if (LowCardGame.getHand(1).getNumCards() ==0){
          Timer timer4 = new Timer(2000, e -> {
             human.setIcon(GUICard.getBackCardIcon());
             computer.setIcon(GUICard.getBackCardIcon());
             if (cpuScore<humanScore){
                score.setText ("<html>Game Over <br> You Won!</html>");
             }
             else if (cpuScore>humanScore){
                score.setText ("<html>Game Over <br> You Lost!</html>");
             }
             else {
                score.setText ("<html>Game Over <br> You Tied!</html>");
             }
          });
          timer4.setRepeats(false);
          timer4.start();
          return;
       }
       // adding the cards to cpu and human hand (visually)
       for(int j = 0; j < LowCardGame.getHand(1).getNumCards(); j++){
          humanLabels[j].setIcon(GUICard.getIcon(LowCardGame.getHand(1).inspectCard(j)));
          myCardTable.pn1ComputerHand.add(computerLabels[j]);
          myCardTable.pn1HumanHand.add(humanLabels[j]);
       }
       // if cpu has first move after winning -- wait 2 seconds before showing it's card
       if (userMove ==0){
          Timer timer4 = new Timer(2000, e -> {
             human.setIcon(GUICard.getBackCardIcon());
             computer.setIcon(GUICard.getIcon(LowCardGame.getHand(0).inspectCard(0)));
          });
          timer4.setRepeats(false);
          timer4.start();
       }
       else {
          Timer timer4 = new Timer(2000, e -> {
             human.setIcon(GUICard.getBackCardIcon());
             computer.setIcon(GUICard.getBackCardIcon());
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
