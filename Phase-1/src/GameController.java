import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.Timer;

/**
 * Game class for MVC design.
 * 
 * @author kevinrobell
 *
 */
public class GameController
{
   private static final int HUMAN = 1;
   private static final int COMPUTER = 0;
   
   GameModel model;
   GameView view;
   
   /**
    * Constructor
    * 
    * @param model
    * @param view
    */
   GameController(GameModel model, GameView view) {
      this.model = model;
      this.view = view;
      init();
   }
   
   /**
    * Sets up view and model classes and sets actionListener to JButtons.
    */
   private void init() {
      if(model.dealNewHands()) {
         model.sortHands();
         
         view.init(model.getCardBackIcon());
         view.setComputerHand(model.getNumCardsPerHand(), model.getCardBackIcon());
         view.setHumanHand(model.getHumanHandIcons());
         for(int i = 0; i < model.getNumCardsPerHand(); i++) {
            view.getHumanHand()[i].addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent ae) {
                  String action = ae.getActionCommand();
                  int k = Integer.parseInt(action);
                  // plays the card that was clicked on
                  model.playCard(HUMAN,k);
                  
                  String status;
                  //view.playHumanCard(model.getCardIcon(model.getHand(HUMAN).inspectCard(k)));
                  view.playHumanCard(model.getCardIcon(model.getPlayedCard(HUMAN)));
                  // if user goes first
                  if (model.getUserMove() == HUMAN){
                     computerPlaysSecond();
                  }
                  //otherwise computer already went first
                  else {
                     //Play
                     int result = model.compareCards(model.getPlayedCard(HUMAN), 
                           model.getPlayedCard(COMPUTER));
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
                        view.setComputerScore(model.getComputerScore());
                     }
                     //score.setText(status);
                     view.setMessageLabel(status);
                   }
                   // plays the card that was clicked on
                   //model.playCard(HUMAN,k);
                   // render the cpu and user hands
                   view.clearComputerHand();
                   view.clearHumanHand();
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
                   view.setComputerHand(model.getHand(COMPUTER).getNumCards(), 
                         GUICard.getBackCardIcon());
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
            });
         }
      }
   }
   
   /**
    * Computer decides which card to play based upon card played by human.
    */
   public void computerPlaysSecond(){
      
      String status = "Player Wins";
      model.setUserMove(HUMAN);
      int cardIndex = model.getHand(COMPUTER).getNumCards()-1;
      for (; cardIndex>=0; cardIndex--){
         // check if cpu's card's value is less than player's card's value
         if (model.compareCards(model.getPlayedCard(HUMAN), 
               model.getHand(COMPUTER).inspectCard(cardIndex)) == -1) {
               status = "CPU Wins";
               model.setUserMove(COMPUTER);
               model.incrementComputerScore();
               view.setComputerScore(model.getComputerScore());
               break;
         }
      }
      // Computer doesn't have a lower card.
      if (cardIndex<0){
         cardIndex = 0;
      }
      // Check for a tie.
      if (model.compareCards(model.getPlayedCard(HUMAN), 
            model.getHand(COMPUTER).inspectCard(cardIndex)) == 0) {
         status = "It's a tie!";
         model.setUserMove(HUMAN);
      }
      // Human wins this round.
      else if(model.getUserMove() != COMPUTER){
         model.incrementHumanScore();
         view.setHumanScore(model.getHumanScore());
      }  
      //Display winner message.
      view.setMessageLabel(status);
      
      //Play and display computer's card.
      model.playCard(COMPUTER, cardIndex);
      Icon cardIcon = model.getCardIcon(model.getPlayedCard(COMPUTER));
      view.playComputerCard(cardIcon);
   }

}
