
/*
Full logical control on every Kamisado game, of all types. Instance of class created upon game
game configurations decided by user.
 */

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KamisadoGame extends Program {

	Player mainPlayer;
	Player opponentPlayer;
	AIPlayer ai;

	boolean speedMode;
	boolean colourMode;
	Tower nextTower;
	String previousColour;
	int turn; // 0 for black turn, 1 for white turn

	int firstToScore;
	int currentRound;
	int mainPlayerScore;
	int opponentScore;

	Tower[] mainPlayerTowers = new Tower[8];
	Tower[] opponentTowers = new Tower[8];

	PauseTransition timeout = new PauseTransition(Duration.seconds(0.5));;

	// ArrayLists for holding move history.  Currently, they are indirectly linked in size.  Potentially better way??
	ArrayList<Tower> towerMoves = new ArrayList<Tower>();
	ArrayList<BoardButton> positionMoves = new ArrayList<BoardButton>();



	// Multiplayer Constructor
	public KamisadoGame(Player main, Player opponent, boolean mode, int playerTurn, int winningScore, boolean randomColours) {
		mainPlayer = main;
		opponentPlayer = opponent;
		ai = null;

		speedMode = mode;
		colourMode = randomColours;
		previousColour = null;
		nextTower = null;

		turn = playerTurn; // black always goes first

		firstToScore = winningScore;
		currentRound = 1;
		mainPlayerScore = 0;
		opponentScore = 0;

		//populateBoard(); // Fill the board with initial towers for both players
	}

	// Single Player Constructor (Sumo games not implemented yet)
	public KamisadoGame(Player main, AIPlayer opponent, boolean mode, int playerTurn , int winningScore, boolean randomColours) {
		mainPlayer = main;
		opponentPlayer = null;
		ai =  opponent;

		speedMode = mode;
		colourMode = randomColours;
		previousColour = null;
		nextTower = null;

		turn = playerTurn; // black always goes first

		firstToScore = winningScore;
		currentRound = 1;
		mainPlayerScore = 0;
		opponentScore = 0;

		//populateBoard(); // Fill the board with initial towers for both players
		System.out.println("single player");
	}

	public void populateBoard() {

		/*
		// add main players' towers (black ones)
		mainPlayerTowers[0] = new Tower(0,7, 0, "brown");
		mainPlayerTowers[1] = new Tower(1,7, 0, "green");
		mainPlayerTowers[2] = new Tower(2,7, 0, "red");
		mainPlayerTowers[3] = new Tower(3,7, 0, "yellow");
		mainPlayerTowers[4] = new Tower(4,7, 0, "pink");
		mainPlayerTowers[5] = new Tower(5,7, 0, "purple");
		mainPlayerTowers[6] = new Tower(6,7, 0, "blue");
		mainPlayerTowers[7] = new Tower(7,7, 0, "orange");

		// add opponents' towers (white ones)
		opponentTowers[0] = new Tower(0,0, 1, "orange");
		opponentTowers[1] = new Tower(1,0, 1, "blue");
		opponentTowers[2] = new Tower(2,0, 1, "purple");
		opponentTowers[3] = new Tower(3,0, 1, "pink");
		opponentTowers[4] = new Tower(4,0, 1, "yellow");
		opponentTowers[5] = new Tower(5,0, 1, "red");
		opponentTowers[6] = new Tower(6,0, 1, "green");
		opponentTowers[7] = new Tower(7,0, 1, "brown");
		*/


		// add main players' towers (black ones)

		mainPlayerTowers[0] = new Tower(0,7, 0, KamisadoUI.positions[0][7].getCellColour());
		mainPlayerTowers[1] = new Tower(1,7, 0, KamisadoUI.positions[1][7].getCellColour());
		mainPlayerTowers[2] = new Tower(2,7, 0, KamisadoUI.positions[2][7].getCellColour());
		mainPlayerTowers[3] = new Tower(3,7, 0, KamisadoUI.positions[3][7].getCellColour());
		mainPlayerTowers[4] = new Tower(4,7, 0, KamisadoUI.positions[4][7].getCellColour());
		mainPlayerTowers[5] = new Tower(5,7, 0, KamisadoUI.positions[5][7].getCellColour());
		mainPlayerTowers[6] = new Tower(6,7, 0, KamisadoUI.positions[6][7].getCellColour());
		mainPlayerTowers[7] = new Tower(7,7, 0, KamisadoUI.positions[7][7].getCellColour());

		// add opponents' towers (white ones)
		opponentTowers[0] = new Tower(0,0, 1, KamisadoUI.positions[0][0].getCellColour());
		opponentTowers[1] = new Tower(1,0, 1, KamisadoUI.positions[1][0].getCellColour());
		opponentTowers[2] = new Tower(2,0, 1, KamisadoUI.positions[2][0].getCellColour());
		opponentTowers[3] = new Tower(3,0, 1, KamisadoUI.positions[3][0].getCellColour());
		opponentTowers[4] = new Tower(4,0, 1, KamisadoUI.positions[4][0].getCellColour());
		opponentTowers[5] = new Tower(5,0, 1, KamisadoUI.positions[5][0].getCellColour());
		opponentTowers[6] = new Tower(6,0, 1, KamisadoUI.positions[6][0].getCellColour());
		opponentTowers[7] = new Tower(7,0, 1, KamisadoUI.positions[7][0].getCellColour());

	}


	public void moveTower(Tower tower, BoardButton newPosition) {
		boolean gameOver = false;
		boolean undoMove = false;

		if (!towerMoves.isEmpty()) { // checks to see if move is actually an UNDO by comparing move to last move in history
			if (towerMoves.get(towerMoves.size() - 1) == tower && positionMoves.get(positionMoves.size() - 1) == newPosition) {
				undoMove = true;
			}
		}

		newPosition.setGraphic(tower.getTowerImage());


		// Update the validTower variable to hold the only tower that opposite player can move
		Tower otherTowers[];
		if (tower.getOwner() == 0)
			otherTowers = this.getOpponentTowers();
		else
			otherTowers = this.getMainPlayerTowers();

		for (int index = 0; index < 8; index++) {
			if (otherTowers[index].getTowerColour().equals(newPosition.getCellColour()))
				nextTower = otherTowers[index];
		}


		//this.setNextTower(newPosition.getCellColour()); // opponent needs to move [colour] tower

		if (!undoMove)
			addHistory(tower, KamisadoUI.positions[tower.getxLocation()][tower.getyLocation()]);


		KamisadoUI.positions[tower.getxLocation()][tower.getyLocation()].setOccupied(false);
		newPosition.setOccupied(true);

		// Update tower coordinates
		tower.setLocation(newPosition.getxLocation(), newPosition.getyLocation());

		if (currentGame.getGameMode() == true) {
			KamisadoUI.timeout.playFromStart();
			KamisadoUI.timeoutWarning.playFromStart();
			KamisadoUI.warningText.setText("");
		}

		switchTurn();

		// Process AI move now
		if(opponentPlayer==null && tower.getOwner()==0 && !getTowerMoves().isEmpty()) {
				timeout.play();
				System.out.println("processing ai move");

			timeout.setOnFinished((e) -> {
				try {
					ai.makeMove(nextTower);
				}
				catch (NullPointerException ex) { };

				System.out.println("Moving AI tower");

			});
		}



	}



	public boolean checkValidMove(BoardButton currentPosition, BoardButton newPosition, Tower tower, int player) { // player is 0 for black, 1 for white
		int currentXPosition = currentPosition.getxLocation();
		int currentYPosition = currentPosition.getyLocation();
		int newXPosition = newPosition.getxLocation();
		int newYPosition = newPosition.getyLocation();

		if (currentPosition.equals(newPosition)) // moving to same position?
			return false;


		// Ensure tower not moves backwards/sideways
		if ((player == 0) && (newPosition.getyLocation() >= currentPosition.getyLocation())) // black is moving down/sideways
			return false;
		if ((player == 1) && (newPosition.getyLocation() <= currentPosition.getyLocation())) // white is moving down/sideways
			return false;




		// Check for either straight line or diagonal move
		if (newPosition.getxLocation() != currentPosition.getxLocation() &&
				newPosition.getyLocation() != currentPosition.getyLocation()) { // Not up or across.  Diagonal?

			// Check diagonal move
			int xDifference, yDifference;
			yDifference = Math.abs(currentPosition.getyLocation() - newPosition.getyLocation());
			xDifference = Math.abs(newPosition.getxLocation() - currentPosition.getxLocation());

			if (yDifference != xDifference) // its not diagonal either!
				return false;

		}

		//Rule 3: A tower can only move to an empty square (two towers can’t occupy the same space).
		if (newPosition.isOccupied())
			return false;

		// Only tower of previous colour can be moved as per rules
		/*if (previousColour != null && (tower.getTowerColour() != previousColour))
			return false;
			*/

		if (nextTower != null && tower != nextTower)
			return false;


		// Towers cannot be moved through other towers
		ArrayList<BoardButton> interceptZones = interceptZones(currentPosition, newPosition);
		for (int index = 0; index < interceptZones.size(); index++) {
			if (interceptZones.get(index).isOccupied())
				return false;
		}

		// Sumo tower move check. 1 Teeth = 5 spaces, 2 Teeth = 3 space, 2 Teeth = 1 space
		System.out.println("Intercept zones: " + interceptZones.size());
		if (tower.getSumoLevel() == 1 && interceptZones.size() > 5)
			return false;
		if (tower.getSumoLevel() == 2 && interceptZones.size() > 3)
			return false;
		if (tower.getSumoLevel() == 3 && interceptZones.size() > 1)
			return false;


		return true; // no rules broken, return true.
	}


	/* Helper method for checking if towers moves through other towers.
	   Returns all board locations between two locations (assuming valid Kamisado move) */
	private ArrayList<BoardButton> interceptZones(BoardButton initialPosition, BoardButton finalPosition) {
		ArrayList<BoardButton> locations = new ArrayList<BoardButton>();

		int yPointer = initialPosition.getyLocation();
		int xPointer = initialPosition.getxLocation();

		if (initialPosition.getxLocation() == finalPosition.getxLocation()) { // Vertical move
			while (yPointer != finalPosition.getyLocation()) {

				if (finalPosition.getyLocation() < initialPosition.getyLocation()) // VERT-UP
					yPointer--;
				else // VERT-DOWN
					yPointer++;

				locations.add(KamisadoUI.positions[xPointer][yPointer]);
			}
		}
		else // Diagonal move
		{
			while ((yPointer != finalPosition.getyLocation()) && (xPointer != finalPosition.getxLocation())) {

				if (finalPosition.getyLocation() < initialPosition.getyLocation()) // DIAG-UP
					yPointer--;
				else // DIAG-DOWN
					yPointer++;

				if (finalPosition.getxLocation() < initialPosition.getxLocation()) // DIAG-LEFT
					xPointer--;
				else // DIAG-RIGHT
					xPointer++;

				locations.add(KamisadoUI.positions[xPointer][yPointer]);
			}
		}

		return locations;
	}


	/* Checks for deadlock in the current players turn.
	   (deadlock is when there is no possible moves for a player to make)
	   Returns TRUE if there is a deadlock, false if not.
	 */
	public boolean checkDeadlock() {

		BoardButton diagLeft ;
		BoardButton vert;
		BoardButton diagRight;

		try {
			if (nextTower.getOwner() == 0) { // bottom player can only move UP
				diagLeft = KamisadoUI.positions[nextTower.getxLocation() - 1][nextTower.getyLocation() - 1];
				vert = KamisadoUI.positions[nextTower.getxLocation()][nextTower.getyLocation() - 1];
				diagRight = KamisadoUI.positions[nextTower.getxLocation() + 1][nextTower.getyLocation() - 1];
			} else {
				diagLeft = KamisadoUI.positions[nextTower.getxLocation() - 1][nextTower.getyLocation() + 1];
				vert = KamisadoUI.positions[nextTower.getxLocation()][nextTower.getyLocation() + 1];
				diagRight = KamisadoUI.positions[nextTower.getxLocation() + 1][nextTower.getyLocation() + 1];
			}

			if (diagLeft.isOccupied() && vert.isOccupied() && diagRight.isOccupied()) // deadlock detected in next turn
				return true;
		}
		catch (IndexOutOfBoundsException e) { // Will throw out of bounds if check is on edge of game board/array

		}

		return false;
	}


	public boolean undoLastMove() {
		ArrayList<Tower> towerHistory = currentGame.getTowerMoves();
		ArrayList<BoardButton> positionHistory = currentGame.getPositionMoves();

		int lastMoveNumber = towerHistory.size()-1;

		if (towerHistory.isEmpty()) // no previous moves
			return false;

		int oldXLocation = towerHistory.get(lastMoveNumber).getxLocation();
		int oldYLocation = towerHistory.get(lastMoveNumber).getyLocation();


		/*
		if (turn == 0)
			timeout.stop();
		else
			timeout.playFromStart();
		*/

		KamisadoUI.clearPosition(KamisadoUI.positions[oldXLocation][oldYLocation]);
		currentGame.moveTower(towerHistory.get(lastMoveNumber), positionHistory.get(lastMoveNumber));

		// Re update the next tower to be moved
		Tower otherTowers[];
		if (turn == 1)
			otherTowers = this.getOpponentTowers();
		else
			otherTowers = this.getMainPlayerTowers();

		for (int index = 0; index < 8; index++) {
			if (otherTowers[index].getTowerColour().equals(positionHistory.get(lastMoveNumber).getCellColour()))
				nextTower = otherTowers[index];
		}


		currentGame.removeTopMove();

		if (towerHistory.isEmpty())
			nextTower = null;




		return true;
	}


	private void addHistory(Tower tower, BoardButton position) {
		towerMoves.add(tower);
		positionMoves.add(position);
		System.out.println("added move to history");
	}



	/* Runs each time a game/round is complete.
	   Updates player score.
	   Returns true if a tournament is complete, and false if more rounds to go.
	   Always returns true on normal game modes.
	 */
	public boolean roundOver(int winner, Tower winningTower) {

		// Dup code pls fix me
		if (winner == 0) {
			if (winningTower.getSumoLevel() == 0)
				mainPlayerScore = mainPlayerScore+1;
			if (winningTower.getSumoLevel() == 1)
				mainPlayerScore = mainPlayerScore+3;
			if (winningTower.getSumoLevel() == 2)
				mainPlayerScore = mainPlayerScore+7;
			if (winningTower.getSumoLevel() == 3)
				mainPlayerScore = mainPlayerScore+15;
		}
		else {
			if (winningTower.getSumoLevel() == 0)
				opponentScore = opponentScore+1;
			if (winningTower.getSumoLevel() == 1)
				opponentScore = opponentScore+3;
			if (winningTower.getSumoLevel() == 2)
				opponentScore = opponentScore+7;
			if (winningTower.getSumoLevel() == 3)
				opponentScore = opponentScore+15;
		}

		if (mainPlayerScore >= firstToScore || opponentScore >= firstToScore) { // Sumo game now over
			return true;
		}


		return false; // Sumo game not yet complete
	}



	/*
	Generates a Kamisado board with random colours, abiding by the 1 colour per row/column rule for set-up.
	NOTE: The algorithm for finding an appropriate colour is shockingly bad - I need fixed.
	It mostly works but goes into an infinite loop unless hard stopped. (Hard stopping after 128 attempts currently)
	 */
	public BoardButton[][] generateRandomBoard() {
		BoardButton[][] tempBoard = new BoardButton[8][8];
		ArrayList<String> everyColour = new ArrayList<String>();

		// Adds hex colours to full list:   Orange,Blue,Purple,Pink,Yellow,Red,Green,Brown
		everyColour.addAll(Arrays.asList("#FFA500", "#0000FF", "#7D3C98", "#FFB6C1", "#FFFF00", "#FF0000", "#008000", "#641E16"));

		ArrayList<String> tempLine = everyColour;

		boolean dupSearch;
		String colour = null;
		Random rand = new Random();
		// Scans from top to bottom, adding appropriate colour
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				dupSearch = true;
				int terminate=0;
				while (dupSearch == true) {
					dupSearch = false; // assume no dup found until proved otherwise
					int randChoice = rand.nextInt(8); // 0 - 7

					colour = everyColour.get(randChoice);

					for (int scanIndex = 0; scanIndex < 8; scanIndex++) {

						BoardButton columnScan = tempBoard[column][scanIndex];
						BoardButton rowScan = tempBoard[scanIndex][row];

						if (columnScan != null) {
							if (columnScan.getCellColourHex().equals(colour))
								dupSearch = true;
						}
						if (rowScan != null) {
							if (rowScan.getCellColourHex().equals(colour))
								dupSearch = true;
						}

						if (dupSearch == true)
							System.out.println("[" + row + "]["+column+"] Dup found, researching");

					}


					// Fix me later?
					if (terminate == 128) {
						dupSearch = false;
						System.out.println("TIMING OUT...");

					}
					terminate++;

				}

				tempBoard[column][row] = new BoardButton(column, row, colour);
				System.out.println("added colour to temp board");


			}
		}

		return tempBoard;
	}


	public Player getPlayer(){
		return mainPlayer;
	}

	public Player getOpponent(){
		return opponentPlayer;
	}

	public boolean getGameMode(){
		return speedMode;
	}

	public boolean getColourMode() {
		return colourMode;
	}

	public Tower[] getMainPlayerTowers() {
		return mainPlayerTowers;
	}

	public Tower[] getOpponentTowers() {
		return opponentTowers;
	}

	public ArrayList<Tower> getTowerMoves() {
		return towerMoves;
	}

	public ArrayList<BoardButton> getPositionMoves() {
		return positionMoves;
	}

	public void removeTopMove() {
		towerMoves.remove(towerMoves.size()-1);
		positionMoves.remove(positionMoves.size()-1);

		if (towerMoves.isEmpty())
			previousColour = null;
	}

	public int getTurn() {
		return turn;
	}

	public int switchTurn() {
		System.out.println("Switching turn");
		if (turn == 0) {
			turn = 1;
			return turn;
		}
		if (turn == 1) {
			turn = 0;
			return turn;
		}
		return 0;
	}

	public void setNextTower(Tower tower) {
		nextTower = tower;
	}
}
