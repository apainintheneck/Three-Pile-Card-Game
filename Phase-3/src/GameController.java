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
   
   //Tracks if button has been pressed
   private static boolean isPressed = false;
   private static int currentPile = -1;
   private static int selectedCard = -1;
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
         
         view.init();
         view.initPile(model.initializePiles());
         view.setComputerHand(model.getNumCardsPerHand(), model.getCardBackIcon());
         view.setHumanHand(model.getHumanHandIcons());
         model.setUserMove(HUMAN);
         for(int i = 0; i < model.getNumCardsPerHand(); i++) {
            view.getHumanHand()[i].addActionListener(new ActionListener() {               
               @Override
               public void actionPerformed(ActionEvent ae) {
                  //Check if button is already pressed
                  if(isPressed == true)
                     return;
                  else
                     isPressed = true;
                  
                  // Only allow click events if the current user move is human
                  if(model.getUserMove() != HUMAN) {
                     return;
                  }
                  String action = ae.getActionCommand();
                  int k = Integer.parseInt(action);
                  // If the pile is already selected, play the card that is selected if valid
                  if(currentPile >= 0) {
                     if(model.playCardToPile(HUMAN, k, currentPile)) {
                        model.takeCard(HUMAN);
                        view.initPile(model.getPiles());
                        model.setUserMove(COMPUTER);
                        view.clearHumanHand();
                        view.setHumanHand(model.getHumanHandIcons());
                        triggerComputerMove(true);
                     }
                     currentPile = -1;
                     selectedCard = -1;
                     isPressed = false;
                  } 
                  // Else, mark the selected card and play when human selects the pile
                  else {
                     selectedCard = k;
                     isPressed = false;
                     return;
                  }
               }
            });
         }
         // Attaching listeners to the three piles
         for(int i = 0; i < 3; i++) {
            view.getPileButtons()[i].addActionListener(new ActionListener() {               
               @Override
               public void actionPerformed(ActionEvent ae) {
                  // Only trigger actions if the current user move is human
                  if(model.getUserMove() != HUMAN) {
                     return;
                  }
                  String action = ae.getActionCommand();
                  int k = Integer.parseInt(action);
                  currentPile = k;
                  // Check if there is a selected card already by the human
                  if(selectedCard < 0) {
                     return;
                  }
                  // Play the selected card to the selected pile
                  if(model.playCardToPile(HUMAN, selectedCard, k)) {
                     currentPile = -1;
                     selectedCard = -1;
                     // Update the icons on the pile(s)
                     view.initPile(model.getPiles());
                     // Draw a new card from the deck
                     model.takeCard(HUMAN);
                     // Re-render the user hand
                     view.clearHumanHand();
                     view.setHumanHand(model.getHumanHandIcons());
                     // Prepare and trigger the computer move
                     model.setUserMove(COMPUTER);
                     triggerComputerMove(true);
                  }
               }
            });
         }
         // Attach listener to the cannot play button
         view.getCannotPlay().addActionListener(new ActionListener() {               
            @Override
            public void actionPerformed(ActionEvent ae) {
               // Only trigger if the current user move is set to human
              if(model.getUserMove() == HUMAN) {
                 // Increment and update user score and trigger comptuer move
                 model.setUserMove(COMPUTER);
                 model.setUserScore(model.getUserScore() + 1);
                 triggerComputerMove(false);
                 view.setHumanScore(model.getUserScore());
              }
            }
         });
      }
   }
   
   /**
    * Function to trigger computer move using a timer
    * @param isUserMove flag indicating if is triggered by a user move or cannot play button is clicked
    */
   private void triggerComputerMove(boolean isUserMove) {
      // If user move is computer, trigger the computer move, else, don't do anything.
      if (model.getUserMove() == COMPUTER){
         Timer timer4 = new Timer(2000, e -> {
            // Find a card (if applicable) in the computer hand and play it in the respective pile
            if(model.findAndPlayCardToPile(COMPUTER)) {
               // Draw a new card and update the computer's hand view
               model.takeCard(COMPUTER);
               view.initPile(model.getPiles());
               view.clearComputerHand();
               view.setComputerHand(model.getHand(COMPUTER).getNumCards(),
                     GUICard.getBackCardIcon());
            } else {
               // If the computer cannot find a move, increment the computer's score and update the view
               model.setCompScore(model.getCompScore() + 1);
               view.setComputerScore(model.getCompScore());
               // If the cannot play button is pressed by the user, and the computer cannot make a move
               // and there are no more cards in the deck, trigger the end of the game
               if(model.getNumCardInDeck() < 1 && !isUserMove) {
                  triggerEndGame();
               }
               // If there are still cards in the deck and the computer AND user cannot make a move, re-deal
               // into the piles. If the deck is less than 3 cards, only some of the piles will be refreshed. 
               else if(model.getNumCardInDeck() >= 1 && !isUserMove) {
                  model.initializePiles();
                  view.initPile(model.getPiles());
               }
            }
            model.setUserMove(HUMAN);
            isPressed = false;
         });
         timer4.setRepeats(false);
         timer4.start();
      }
   }

   /**
    * Function to determine the winner and update the text
    */
   private void triggerEndGame() {
      int winner = model.determineWinner();
      if(winner == 1) {
         view.setMessageLabel("You won!");
      } else if (winner == -1) {
         view.setMessageLabel("You lost!");
      } else {
         view.setMessageLabel("You tied!");
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
