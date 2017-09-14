import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.*;
import javafx.stage.Stage;

/*
User Interface for main menu prompting user to select a game type or proceed to leaderboard.
 */

public class MenuUI extends Program {

    FlowPane layout = new FlowPane(Orientation.VERTICAL);

    public MenuUI(final Stage menuStage) throws Exception {



        menuStage.setTitle("Kamisado Game");

        layout.setColumnHalignment(HPos.LEFT); // align labels on left
        layout.setAlignment(Pos.CENTER);

        try {
            mainMenu(menuStage);
        }
        catch (Exception e) {}


        Scene scene = new Scene(layout, 960, 720);
        menuStage.setScene(scene);
        menuStage.show();
    }

    private void mainMenu(final Stage stage) throws Exception {
        Text welcomeText = new Text("Welcome to Kamisado " + currentPlayer.getName() + "!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(welcomeText);

        Button singlePlayerButton = new Button();
        singlePlayerButton.setText("Single Player Mode");
        layout.getChildren().add(singlePlayerButton);
        singlePlayerButton.isDisabled(); // REMOVE ON SINGLE PLAYER FUNCTIONALITY

        Button multiplayerButton = new Button();
        multiplayerButton.setText("Multiplayer Mode");
        layout.getChildren().add(multiplayerButton);


        multiplayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    multiplayerSelection(stage); // Proceed to multiplayer options
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });


        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    singlePlayerSelection(stage); // Proceed to multiplayer options
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });
    }

    // Needs speed mode functionality later
    private void singlePlayerSelection(final Stage stage) throws Exception {
        Text titleText = new Text("Single Player");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(titleText);

        Button newGameButton = new Button("New Game");
        layout.getChildren().add(newGameButton);

        Button loadGameButton = new Button("Load Game");
        layout.getChildren().add(loadGameButton);

        Button backButton = new Button("Go Back");
        layout.getChildren().add(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    mainMenu(stage);
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    singlePlayerConfig(stage);
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });


    }

    private void singlePlayerConfig(final Stage stage) throws Exception {
        Text titleText = new Text("New Game Configuration");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(titleText);

        final CheckBox speedmodeCheck = new CheckBox("Speed Mode?");
        layout.getChildren().add(speedmodeCheck);

        final CheckBox difficultyCheck = new CheckBox("Hard Difficulty?");
        layout.getChildren().add(difficultyCheck);

        final CheckBox colourMode = new CheckBox("Random Colour Board?");
        layout.getChildren().add(colourMode);

        /*
        SUMO MODE CURRENTLY DISABLED IN SINGLE PLAYER
        final CheckBox sumomodeCheck = new CheckBox("Sumo Mode?");
        layout.getChildren().add(sumomodeCheck);

        final Text sumoScoreText = new Text("First to score: (1-100)");
        layout.getChildren().add(sumoScoreText);
        sumoScoreText.setVisible(false);

        final TextField sumoScore = new TextField();
        layout.getChildren().add(sumoScore);
        sumoScore.setVisible(false);
        */

        final Button submitButton = new Button();
        submitButton.setText("Play Kamisado!");
        layout.getChildren().add(submitButton);

        Button backButton = new Button("Go Back");
        layout.getChildren().add(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    singlePlayerSelection(stage);
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });

        final Text errorZone = new Text();
        layout.getChildren().add(errorZone);


        /*
        sumomodeCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sumoScoreText.isVisible() && sumoScore.isVisible()) {
                    sumoScoreText.setVisible(false);
                    sumoScore.setVisible(false);
                }
                else {
                    sumoScoreText.setVisible(true);
                    sumoScore.setVisible(true);
                }
            }
        });
        */

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                    currentGame = new KamisadoGame(currentPlayer, new AIPlayer(difficultyCheck.isSelected()), speedmodeCheck.isSelected(),
                            0, 1, colourMode.isSelected());

                    stage.close(); // Close current window in way for new one

                    try {
                        new KamisadoUI(new Stage()); // Proceed to Kamisado user interface
                    } catch (Exception e) {
                        System.out.println("exception");
                    }


            }
        });
    }

    private void multiplayerSelection(final Stage stage) throws Exception {
        Text instructionsText = new Text("Multiplayer Game Configuration");
        instructionsText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(instructionsText);

        final Text opponentText = new Text("Opponent Player Name:");
        layout.getChildren().add(opponentText);

        final TextField opponentEntry = new TextField();
        layout.getChildren().add(opponentEntry);

        final CheckBox speedmodeCheck = new CheckBox("Speed Mode?");
        layout.getChildren().add(speedmodeCheck);

        final CheckBox sumomodeCheck = new CheckBox("Sumo Mode?");
        layout.getChildren().add(sumomodeCheck);

        final CheckBox randomColours = new CheckBox("Random Colour Board?");
        layout.getChildren().add(randomColours);

        final Text sumoScoreText = new Text("First to score: (1-100)");
        layout.getChildren().add(sumoScoreText);
        sumoScoreText.setVisible(false);

        final TextField sumoScore = new TextField();
        layout.getChildren().add(sumoScore);
        sumoScore.setVisible(false);

        final Button submitButton = new Button();
        submitButton.setText("Play Kamisado!");
        layout.getChildren().add(submitButton);

        final Button backButton = new Button("Go Back");
        layout.getChildren().add(backButton);

        final Text errorZone = new Text();
        layout.getChildren().add(errorZone);



        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout.getChildren().clear();
                try {
                    mainMenu(stage);
                } catch (Exception e) {
                    System.out.println("exception");
                }
            }
        });

        sumomodeCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sumoScoreText.isVisible() && sumoScore.isVisible()) {
                    sumoScoreText.setVisible(false);
                    sumoScore.setVisible(false);
                }
                else {
                    sumoScoreText.setVisible(true);
                    sumoScore.setVisible(true);
                }
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String playerInput = opponentEntry.getText();
                int sumoScoreInput = 0;
                try {
                    sumoScoreInput = Integer.parseInt(sumoScore.getText());
                }
                catch (NumberFormatException e) {
                    errorZone.setText("Invalid entry for the winning score in the sumo game. (1-100 is the limits)");
                }

                if (playerInput.length() < 3) {
                    errorZone.setText("Player names needs to be at least 3 characters long!");
                }
                else {
                    if ((sumoScoreInput < 1 || sumoScoreInput > 100) && sumomodeCheck.isSelected()) {
                        errorZone.setText("Winning score for sumo game must be between 1-100!");
                    }
                    else {
                        if (!sumomodeCheck.isSelected())
                            sumoScoreInput = 1;

                        Player opponentPlayer = new Player(playerInput);
                        if (speedmodeCheck.isSelected())
                            currentGame = new KamisadoGame(currentPlayer, opponentPlayer, true, 0, sumoScoreInput, randomColours.isSelected());
                        else
                            currentGame = new KamisadoGame(currentPlayer, opponentPlayer, false, 0, sumoScoreInput, randomColours.isSelected());

                        // Console tests showing players in multiplayer game
                        System.out.println("Main Player is: " + currentGame.getPlayer().getName());
                        System.out.println("Opponent Player is: " + currentGame.getOpponent().getName());
                        if (currentGame.getGameMode())
                            System.out.println("Game Type: Speed Mode");
                        else
                            System.out.println("Game Type: Classic Mode");




                        //menuStage.hide(); // Close current window in way for new one

                        /*
                        if(stage.getScene() != null)
                            stage.getScene().setRoot(null);
                            */

                        stage.close();
                        gameStage = new Stage();

                            new KamisadoUI(gameStage); // Proceed to Kamisado user interface

                    }
                }
            }
        });

    }


}
