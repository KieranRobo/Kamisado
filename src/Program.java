import javafx.application.Application;
import javafx.stage.Stage;

/*
Main control class and host of global variables and methods used throughout the Kamisado
program.
 */


public class Program extends Application {

	static KamisadoGame currentGame;
	static Player currentPlayer;

	Stage welcomeStage;
	Stage menuStage;
	Stage gameStage;

	public static void main(String[] args) {
		currentGame = null;
		currentPlayer = null;
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new WelcomeUI(primaryStage);
		welcomeStage = primaryStage;
	}




}
