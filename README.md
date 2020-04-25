# Three-Pile-Card-Game
This project involved building three different but related card games in Java Swing. Each phase is a self contained Java program.

## Phase 1: "Low Card" Game with MVC Design Pattern
The point of this phase was to design a game using the MVC design pattern. The rule of "low card" are simple: the person with the lowest card wins each round wins and the winner plays first next round. At the end, the player who won the most wins the game or it is a tie.
<table>
  <tr>
    <td valign="top"><img src="screenshots/Screenshot_1582745092.png"></td>
    <td valign="top"><img src="screenshots/Screenshot_1582745125.png"></td>
  </tr>
 </table>
 
 ## Phase 2: "Low Card" Game with a timer
The point of this phase was to add a timer to the previous design. This timer must run on a seperate thread so that it doesn't get interrupted by the gameplay. While the timer does run, it doesn't affect the gameplay. It is merely ornamental.
<table>
  <tr>
    <td valign="top"><img src="screenshots/Screenshot_1582745092.png"></td>
    <td valign="top"><img src="screenshots/Screenshot_1582745125.png"></td>
  </tr>
 </table>

## Phase 3: "Three Pile" Game
This time we created an entirely new game. Each turn the player must try to play a card from their hand to one of the three piles in the play area. They must play an adjacent card in value to card on the table. For example: if a 3 is on the table, you may play a 2 or a 4. If you cannot play, you may skip your turn but your score increments by one. You play until the deck is empty and the winner is the player with the least points.
<table>
  <tr>
    <td valign="top"><img src="screenshots/Screenshot_1582745092.png"></td>
    <td valign="top"><img src="screenshots/Screenshot_1582745125.png"></td>
  </tr>
 </table>
