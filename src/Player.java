
/*
Logical control of each time a player name is submitted and accepted.  Instances created both of welcome
screen, and opponent name submission in multiplayer mode.
 */

public class Player {

	String playerName;
	
	int aiWins, aiLoses = 0;
	int pvpWins, pvpLoses = 0;
	int currentPlayerNo;
	
	
	public Player(String name) {
		playerName = name;
	}

	public String getName() {
		return playerName;
	}

	public int getCurrentPlayerNo() {
		return currentPlayerNo;
	}

	public void aiWin() {
		aiWins++;
	}
	
	public void pvpWin() {
		pvpWins++;
	}
	
	public void aiLose() {
		aiLoses++;
	}
	
	public void pvpLose() {
		aiWins++;
	}

	public void setPlayerNo(int playerNo) {
		currentPlayerNo = playerNo;
	}
}
