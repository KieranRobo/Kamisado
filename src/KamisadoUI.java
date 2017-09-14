import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image ;
import javafx.util.Duration;


/*
User Interface control for Kamisado games in progress.
 */

public class KamisadoUI extends Program {

    int cellDimensions = 75;
    static int speedModeTime = 12; // Time for player to make move in speed mode (seconds)

    static GridPane mainGrid = new GridPane();
    static GridPane boardGrid = new GridPane();

    static BoardButton[][] positions; // Holds each instance of of board locations on board

    static Tower selectedTower = null;
    static BoardButton cellSelected = null;

    static BoardButton currentKeyboardHover = null;

    // Speed mode timers
    static PauseTransition timeout = new PauseTransition(Duration.seconds(speedModeTime));
    static PauseTransition timeoutWarning = new PauseTransition(Duration.seconds(speedModeTime/2));

    static Text warningText;



    Text invalidMoveText = new Text();

    public KamisadoUI(Stage stage) {

        Scene scene = new Scene(mainGrid, 960, 720); // causing error on reopen
        System.out.println("After scene");

        stage.setTitle("Kamisado Multiplayer Game");

        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setHalignment(HPos.CENTER);
        mainGrid.getColumnConstraints().addAll(constraints, constraints);

        positions = new BoardButton[8][8]; // 8x8 game board


        invalidMoveText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        mainGrid.add(invalidMoveText, 0, 1);

        BoardButton randomBoard[][] = currentGame.generateRandomBoard();
        for (int columns = 0; columns < 8; columns++) {
            for (int rows = 0; rows < 8; rows++) {

                if (currentGame.getColourMode() == true)
                    positions[columns][rows] = randomBoard[columns][rows];
                else
                    positions[columns][rows] = new BoardButton(columns,rows);


                //positions[columns][rows].setText("X:" + columns + " Y:" + rows); // use for debugging

                positions[columns][rows].setMinHeight(cellDimensions);
                positions[columns][rows].setMaxHeight(cellDimensions);
                positions[columns][rows].setMinWidth(cellDimensions);
                positions[columns][rows].setMaxWidth(cellDimensions);

                
                final BoardButton zoneButton = positions[columns][rows];
                zoneButton.setStyle(" -fx-background-color: " + zoneButton.getCellColourHex() + ";");

                final BoardButton zone = positions[columns][rows];

                // Mouse hover [entry]
                positions[columns][rows].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                            String cursorColour = null;
                            if (currentGame.getTurn() == 0) // hover and selection border colour depends on whos turn it is
                                cursorColour = "black";
                            else
                                cursorColour = "white";
                            if (cellSelected != zone)
                                zone.setStyle("-fx-background-color: " + zone.getCellColourHex() + "; -fx-border-color: " + cursorColour + "; -fx-border-width: 2px;");
                        }
                    });

                // Mouse hover [exit]
                positions[columns][rows].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                            if (cellSelected != zone)
                                zone.setStyle(" -fx-background-color: " + zone.getCellColourHex() + ";"); // make border invisible
                        }
                    });

                // Zone selected
                positions[columns][rows].setOnAction(new EventHandler<ActionEvent>() {
                   @Override
                   public void handle(ActionEvent event) {
                            moveTowerUI(zone);
                        }
                    });

                boardGrid.add(positions[columns][rows], columns, rows);

            }
        }

        currentGame.populateBoard();


        // Keyboard input (for each key press) [Begin]
        // NOTE: Combination of mouse + keyboard input mostly breaks input - Use one or another
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                int selectedX;
                int selectedY;
                BoardButton oldSelection = currentKeyboardHover;

                if (currentKeyboardHover != null) {
                    selectedX = oldSelection.getxLocation();
                    selectedY = oldSelection.getyLocation();
                    System.out.println("selectedx is "+ selectedX + " selectedy is "+ selectedY);
                }
                else
                {
                    selectedX = 0;
                    selectedY = 0;
                }

                String cursorColour = null;
                if (currentGame.getTurn() == 0) // hover and selection border colour depends on whos turn it is
                    cursorColour = "black";
                else
                    cursorColour = "white";


                if (event.getCode() == KeyCode.ENTER) {
                    cellSelected.setStyle(" -fx-background-color: " + cellSelected.getCellColourHex() + ";");
                    moveTowerUI(currentKeyboardHover);
                }

                if (event.getCode() == KeyCode.UP) {
                    System.out.println("Up Pressed");
                    if (selectedY != 0) { // top reached
                        KamisadoUI.currentKeyboardHover = positions[selectedX][selectedY - 1];
                        positions[selectedX][selectedY - 1].setStyle("-fx-background-color: " + positions[selectedX][selectedY - 1].getCellColourHex() + "; -fx-border-color: " + cursorColour + "; -fx-border-width: 2px;");

                    }
                }
                if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("Down Pressed");

                    if (selectedY != 7) { // bottom reached
                        System.out.println("selected y + 1 is: " + selectedY + 1);
                        KamisadoUI.currentKeyboardHover = positions[selectedX][selectedY + 1];
                        positions[selectedX][selectedY + 1].setStyle("-fx-background-color: " + positions[selectedX][selectedY + 1].getCellColourHex() + "; -fx-border-color: " + cursorColour + "; -fx-border-width: 2px;");

                    }
                }
                if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("Left Pressed");
                    if (selectedX != 0) { // left reached
                        KamisadoUI.currentKeyboardHover = positions[selectedX-1][selectedY];
                        positions[selectedX-1][selectedY].setStyle("-fx-background-color: " + positions[selectedX-1][selectedY].getCellColourHex() + "; -fx-border-color: " + cursorColour + "; -fx-border-width: 2px;");

                    }

                }
                if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("Right Pressed");
                    if (selectedX != 7) { // right reached
                        KamisadoUI.currentKeyboardHover = positions[selectedX+1][selectedY];
                        positions[selectedX+1][selectedY].setStyle("-fx-background-color: " + positions[selectedX+1][selectedY].getCellColourHex() + "; -fx-border-color: " + cursorColour + "; -fx-border-width: 2px;");

                    }
                }

                if (event.getCode() == KeyCode.Z && currentGame.getOpponent() == null && currentGame.getTurn() == 0) { // Undo move (single player only)
                    cellSelected = null;
                    currentGame.undoLastMove();
                    currentGame.undoLastMove();
                }

                if (oldSelection != null) {
                    if (cellSelected != null)
                        cellSelected.setStyle("-fx-background-color: " + cellSelected.getCellColourHex() + "; -fx-border-width: 10px; -fx-border-color: " + cursorColour + ";");

                    oldSelection.setStyle(" -fx-background-color: " + oldSelection.getCellColourHex() + ";");
                }


            }
        });
        // Keyboard input [End]


        // Prepare board on UI
        initiliseTowers();
        mainGrid.add(boardGrid, 0, 0);


        // Right info zone [Begin]
        FlowPane infoPane = new FlowPane(Orientation.VERTICAL);
        infoPane.setAlignment(Pos.CENTER);

        String opponentName;
        if (currentGame.getOpponent() != null)
            opponentName = currentGame.getOpponent().getName();
        else
            opponentName = "Donald J Trump (ai)";

        Text versusTextP1 = new Text(opponentName + " (white)");
        Text versusTextVS = new Text("versus");
        Text versusTextP2 = new Text(currentGame.getPlayer().getName() + " (black)");

        versusTextP1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        versusTextP1.setTextAlignment(TextAlignment.CENTER);
        versusTextP2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        versusTextP2.setTextAlignment(TextAlignment.CENTER);

        infoPane.getChildren().add(versusTextP1);
        infoPane.getChildren().add(versusTextVS);
        infoPane.getChildren().add(versusTextP2);

        if (currentGame.getGameMode() == true) { // its speed mode
            Text speedModeText = new Text("SPEED MODE");
            infoPane.getChildren().add(speedModeText);

            Text timeLeftText = new Text("Make a move in " + speedModeTime + " seconds or you will LOSE!");
            infoPane.getChildren().add(timeLeftText);

            timeout.setOnFinished((e) -> {
                System.out.println("game over time up!");

                mainGrid.getChildren().clear();
                try {
                    if (currentGame.getTurn() == 0)
                        gameOverScreen(1); // white wins
                    else
                        gameOverScreen(0); // black wins
                } catch (Exception ex) {
                    System.out.println("exception");
                }
            });

            // waring text
            warningText = new Text();
            infoPane.getChildren().add(warningText);

            timeoutWarning.setOnFinished((e) -> {
                warningText.setText("TIME IS RUNNING OUT!!!!!");
                timeoutWarning.playFromStart();
            });

            // Begin speed mode timers
            timeout.play();
            timeoutWarning.play();
        }

        if (currentGame.getOpponent() == null) { // single player
            Button undoMove = new Button("Hit 'Z' To Undo Last Move!");
            infoPane.getChildren().add(undoMove);

            undoMove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    currentGame.undoLastMove();
                    currentGame.undoLastMove();
                }
            });
        }

        Button leaveButton = new Button("Leave Game");
        infoPane.getChildren().add(leaveButton);

        leaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                stage.setScene(null);

                currentGame = null;

                menuStage = new Stage();
                try {
                    new MenuUI(menuStage); // Proceed to menu user interface

                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });




        mainGrid.add(infoPane, 1, 0);
        // Right info zone [End]


        stage.setScene(scene);
        stage.show();
    }



    /* moveTowerUI called when a zone on the game board is selected.
       Method either simply selects a zone/tower, or moves a tower to the selected zone if a zone had already been
       selected before hand.
     */
    private void moveTowerUI(BoardButton selectionZone) {
        String cursorColour = null;
        if (currentGame.getTurn() == 0) // hover and selection border colour depends on whos turn it is
            cursorColour = "black";
        else
            cursorColour = "white";

        if (cellSelected == selectionZone) {
            System.out.println("same cell selected");
            cellSelected.setStyle(" -fx-background-color: " + cellSelected.getCellColourHex() + ";");
            cellSelected = null;
        }
        else
        {
            boolean towerTransfer = false; // tracking variable to prevent chain tower moves
            if (cellSelected != null) { // first check if selected zone is actually a tower

                for (int index = 0; index < 8; index++) {
                    if (cellSelected.getyLocation() == currentGame.mainPlayerTowers[index].getyLocation() &&
                            cellSelected.getxLocation() == currentGame.mainPlayerTowers[index].getxLocation())
                        selectedTower = currentGame.mainPlayerTowers[index];
                    if (cellSelected.getyLocation() == currentGame.opponentTowers[index].getyLocation() &&
                            cellSelected.getxLocation() == currentGame.opponentTowers[index].getxLocation())
                        selectedTower = currentGame.opponentTowers[index];
                }
                if ((selectedTower != null) && (currentGame.getTurn() == selectedTower.getOwner())) { // selected tower wont be null if selection is tower
                    towerTransfer = true;
                    if (currentGame.checkValidMove(cellSelected, selectionZone, selectedTower, selectedTower.getOwner()) == false) { // make sure its a valid move before proceeding
                        invalidMoveText.setText("INVALID MOVE!");
                    }
                    else // move is valid - update game and UI
                    {
                        currentGame.moveTower(selectedTower, selectionZone);

                        if ((selectionZone.getyLocation() == 0 && selectedTower.getOwner() == 0) || (selectionZone.getyLocation() == 7 && selectedTower.getOwner() == 1)) { // Tower in winning position?
                            if (currentGame.roundOver(currentGame.getTurn(), selectedTower) == true) { // check if sumo game over
                                System.out.println("GAME OVER!");
                                mainGrid.getChildren().clear();
                                try {
                                    gameOverScreen(currentGame.getTurn()); // Player on current turn has won
                                } catch (Exception e) {
                                    System.out.println("exception");
                                }
                            }
                            else
                            {
                                selectedTower.increaseSumoLevel();
                                currentGame.timeout.stop();
                                
                                resetBoard(selectedTower);
                                clearPosition(cellSelected);
                                positions[selectedTower.getDefaultXLocation()][selectedTower.getDefaultYLocation()].setGraphic(selectedTower.getTowerImage());
                                invalidMoveText.setText("Round complete, upgrading sumo tower!");

                                //sumoWin(selectedTower);
                                // NEXT SUMO ROUND CODE HERE
                                // NEXT SUMO ROUND CODE HERE
                                // NEXT SUMO ROUND CODE HERE
                                // NEXT SUMO ROUND CODE HERE
                                // NEXT SUMO ROUND CODE HERE
                                // NEXT SUMO ROUND CODE HERE
                                System.out.println("NEXT SUMO ROUND");
                            }
                        }
                        else {
                            //currentGame.switchTurn();
                            if (currentGame.checkDeadlock() == true) { // deadlock for other player detected!
                                invalidMoveText.setText("Deadlock detected! Skipping turn.");
                                currentGame.switchTurn();
                            } else {
                                invalidMoveText.setText(null);
                            }

                            clearPosition(cellSelected);
                        }
                    }
                    selectedTower = null; // reset the selection
                }
                if (towerTransfer == false)
                    cellSelected.setStyle(" -fx-background-color: " + cellSelected.getCellColourHex() + ";");
            }

            if (towerTransfer == false) // we need to reset cell selection if tower transfer is complete
                cellSelected = selectionZone;

            selectionZone.setStyle("-fx-background-color: " + selectionZone.getCellColourHex() + "; -fx-border-width: 10px; -fx-border-color: " + cursorColour + ";");
        }
    }


    // Sets board zone to be blank (when a tower is moved for example, the old zone must be updated)
    public static void clearPosition(BoardButton position) {
        positions[position.getxLocation()][position.getyLocation()].setStyle("-fx-background-color: " + position.getCellColourHex() + ";");
        positions[position.getxLocation()][position.getyLocation()].setGraphic(null);
    }


    // Adds default towers to default locations as per Kamisado rules
    private void initiliseTowers() {
        Tower[] mainPlayerTowers = currentGame.getMainPlayerTowers();
        Tower[] opponentTowers = currentGame.getOpponentTowers();

        Image image;
        int xLocation;
        int yLocation;
        for (int index = 0; index < 8; index++) { // add towers
            xLocation = mainPlayerTowers[index].getxLocation();
            yLocation = mainPlayerTowers[index].getyLocation();

            positions[xLocation][yLocation].setOccupied(true);
            positions[xLocation][yLocation].setGraphic(mainPlayerTowers[index].getTowerImage());
            positions[xLocation][yLocation].setStyle("-fx-background-color: " + positions[xLocation][yLocation].getCellColourHex() + ";");


            xLocation = opponentTowers[index].getxLocation();
            yLocation = opponentTowers[index].getyLocation();

            positions[xLocation][yLocation].setOccupied(true);
            positions[xLocation][yLocation].setGraphic(opponentTowers[index].getTowerImage());
            positions[xLocation][yLocation].setStyle("-fx-background-color: " + positions[xLocation][yLocation].getCellColourHex() + ";");
        }
    }


    // Resets board back to initial state.  Currently used for a sumo game round win.
    public void resetBoard(Tower winningTower) {
        Tower[] mainPlayerTowers = currentGame.getMainPlayerTowers();
        Tower[] opponentTowers = currentGame.getOpponentTowers();

        for (int index = 0; index < 8; index++) {
            Tower mainTower = mainPlayerTowers[index];
            Tower opponentTower = opponentTowers[index];

            if (mainTower != winningTower && opponentTower != winningTower) {
                clearPosition(positions[mainTower.getxLocation()][mainTower.getyLocation()]);
                clearPosition(positions[opponentTower.getxLocation()][opponentTower.getyLocation()]);
            }

            currentGame.moveTower(mainTower, positions[index][7]);
            currentGame.moveTower(opponentTower, positions[index][0]);

            currentGame.setNextTower(null);
        }
    }


    //  Ends game, and proceeds to game over zone.
    public static void gameOverScreen(int winningPlayer) throws Exception {

        Text gameOverText = new Text("The game is now complete.");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        mainGrid.add(gameOverText, 0, 0);
        Text winnerText;

        timeout.stop();
        if (currentGame.getOpponent() == null) // single player!
        {
            if (winningPlayer == 0)  // black win
                winnerText = new Text("Congratulations! You won the game!");
            else
                winnerText = new Text("You have just lost against the AI, Unlucky, better luck next time!");
        }
        else
        {
            Player winner;
            if (currentGame.getPlayer().getCurrentPlayerNo() == winningPlayer)
                winner = currentGame.getPlayer();
            else
                winner = currentGame.getOpponent();


            if (winningPlayer == 0)  // black win
                winnerText = new Text("Congratulations to " + winner.getName() + " (black) who won the game!");
            else
                winnerText = new Text("Congratulations to " + winner.getName() + " (white) who won the game!");
        }

        winnerText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        winnerText.setTextAlignment(TextAlignment.CENTER);
        mainGrid.add(winnerText, 0, 1);
    }


}
