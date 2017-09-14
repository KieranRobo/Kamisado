import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Kieran on 21/03/2017.
 */
        public class AIPlayer extends Program {

            boolean difficult;

            public AIPlayer(boolean hardDifficulty) {

                difficult = hardDifficulty;
                if (difficult == true)
                    System.out.println("Hard difficulty");
            }

            public void makeMove(Tower validTower) {
                Tower aiTowers[] = currentGame.getOpponentTowers();


                int towerX = validTower.getxLocation();
                int towerY = validTower.getyLocation();

                ArrayList<BoardButton> possiblePositions = new ArrayList<BoardButton>();

                boolean valid;
                for (int columns = 0; columns < 8; columns++) {
                    for (int rows = 0; rows < 8; rows++) {
                        valid = currentGame.checkValidMove(KamisadoUI.positions[towerX][towerY], KamisadoUI.positions[columns][rows], validTower, 1);
                        if (valid == true)
                            possiblePositions.add(KamisadoUI.positions[columns][rows]);
                    }
                }

                if (!possiblePositions.isEmpty()) {
                    Random rand = new Random();
                    int randomSelection = rand.nextInt(possiblePositions.size());

                    BoardButton newMove = null;
                    if (difficult == false) // Just move to random valid positon
                        newMove = possiblePositions.get(randomSelection);
                    else // Hard mode: check for winning move
                    {
                        for (int index = 0; index < possiblePositions.size(); index++) {
                            if (possiblePositions.get(index).getyLocation() == 7) // winning move
                                newMove = possiblePositions.get(index);
                        }
                        if (newMove == null) // still no winning move
                            newMove = possiblePositions.get(randomSelection);
                    }

                    KamisadoUI.clearPosition(KamisadoUI.positions[towerX][towerY]);
                    currentGame.moveTower(validTower, newMove);

                    if (newMove.getyLocation() == 7) {
                        try {
                            // Shouldn't be referencing to UI class. Fix me?
                            KamisadoUI.mainGrid.getChildren().clear();
                            KamisadoUI.gameOverScreen(1);
                        }
                        catch (Exception e) {}
                    }
                }
                else
                {
                    //deadlock for ai.  No possible positons found
                }

            }
        }
