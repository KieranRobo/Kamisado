import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/*
User Interface for welcome screen prompting user to enter their player name.
 */

public class WelcomeUI extends Program {

    public WelcomeUI(final Stage stage) throws Exception {
        stage.setTitle("Kamisado Game");

        GridPane grid = new GridPane(); // Outer layout (Holds error text for pressing button)
        FlowPane layout = new FlowPane(Orientation.VERTICAL); // Inner layout (holds most elements)
        grid.add(layout, 1, 0);
        layout.setColumnHalignment(HPos.LEFT); // align labels on left
        layout.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);


        // Need to figure out how to center these elements properly.
        final Text welcomeText = new Text("Welcome to Kamisado!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(welcomeText);

        final Text nameText = new Text("Please enter your player name below:");
        layout.getChildren().add(nameText);

        final TextField nameEntry = new TextField();
        layout.getChildren().add(nameEntry);

        final Button playButton = new Button();
        playButton.setText("Play Kamisado!");
        layout.getChildren().add(playButton);

        final Text errorZone = new Text(); // Used to display errors in name input
        grid.add(errorZone, 1, 1);


        // Runs when Play kamisado button is pressed
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String playerInput = nameEntry.getText();

                if (playerInput.length() < 3) {
                    errorZone.setText("Your player name needs to be at least 3 characters long!");
                }
                else
                {
                    currentPlayer = new Player(nameEntry.getText());
                    System.out.println("Player name is: " + currentPlayer.getName());

                    stage.close(); // Close current window in way for new one

                    menuStage = new Stage();

                    try {
                        new MenuUI(menuStage); // Proceed to menu user interface
                    } catch (Exception e) {
                        System.out.println("exception");
                    }
                }
            }
        });


        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);

        stage.show();
    }
}
