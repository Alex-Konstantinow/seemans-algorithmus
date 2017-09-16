package seemansolitaer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FishermensSolitaer {

    private final static int COL = 5;
    private final static int ROW = 5;
    private final static int NUMBER_OF_INITIAL_TOKENS = 8;

    private static int whiteTokensToInitiate;
    private static int blackTokensToInitiate;
    private static int emptyFieldToInitiate;

    private Field[][] gameState;
    private static Field[][] solution;
    private Field emptyField;

    private static Map<Integer, Field[][]> todoContent = new HashMap<Integer, Field[][]>();
    private static ArrayList<Field[][]> todo = new ArrayList<Field[][]>();

    public FishermensSolitaer() {
        this.gameState = new Field[COL][ROW];
        FishermensSolitaer.solution = new Field[COL][ROW];
        FishermensSolitaer.whiteTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
        FishermensSolitaer.blackTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
        FishermensSolitaer.emptyFieldToInitiate = 1;
        initGame();
        solutionTest();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gameState[i][j].setNeighbours(gameState, i, j);
            }
        }
    }

    /**
     * first 2x2 positions and last 2x2 positions are empty:
     * X = no FishermensSolitaer, W = White, B = Black, E = Empty
     * XXWWW
     * XXWWW
     * BBEWW
     * BBBXX
     * BBBXX
     */
    private void initGame() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 || i == 1) && (j == 0 || j == 1) || ((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    this.gameState[i][j] = new Field(i, j, Token.NOFIELD);
                } else {
                    if (FishermensSolitaer.whiteTokensToInitiate > 2) {
                        setFieldWithWhiteToken(i, j);
                    } else if (FishermensSolitaer.whiteTokensToInitiate == 2 && FishermensSolitaer.blackTokensToInitiate != 6) {
                        setFieldWithBlackToken(i, j);
                    } else if (i == 2 && j == 2) {
                        this.emptyField = new Field(i, j, Token.EMPTY);
                        this.gameState[i][j] = emptyField;
                        FishermensSolitaer.emptyFieldToInitiate--;
                    } else if (FishermensSolitaer.emptyFieldToInitiate == 0 && FishermensSolitaer.whiteTokensToInitiate != 0) {
                        setFieldWithWhiteToken(i, j);
                    } else {
                        setFieldWithBlackToken(i, j);
                    }
                }
            }
        }
    }

    private void solutionTest() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                solution[i][j] = gameState[j][i];
            }
        }
    }

    private void setFieldWithWhiteToken(int x, int y) {
        this.gameState[x][y] = new Field(x, y, Token.WHITE);
        FishermensSolitaer.whiteTokensToInitiate--;
    }

    private void setFieldWithBlackToken(int x, int y) {
        this.gameState[x][y] = new Field(x, y, Token.BLACK);
        FishermensSolitaer.blackTokensToInitiate--;
    }

    public Field[][] getGameState() {
        return gameState;
    }

    public void changePostion(Field chosenField) {
        int emptyPositionX = this.emptyField.getxPositionOnField();
        int emptyPositionY = this.emptyField.getyPositionOnField();

        this.emptyField.setxPositionOnField(chosenField.getxPositionOnField());
        this.emptyField.setyPositionOnField(chosenField.getyPositionOnField());
        this.gameState[chosenField.getxPositionOnField()][chosenField.getyPositionOnField()] = this.emptyField;
        chosenField.setxPositionOnField(emptyPositionX);
        chosenField.setyPositionOnField(emptyPositionY);
        this.gameState[emptyPositionX][emptyPositionY] = chosenField;
        emptyField.setNeighbours(gameState, this.emptyField.getxPositionOnField(), this.emptyField.getyPositionOnField());
        chosenField.setNeighbours(gameState, chosenField.getxPositionOnField(), chosenField.getyPositionOnField());
    }

    public void drawGameState() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (this.gameState[i][j].getToken()) {
                    case NOFIELD:
                        System.out.print("[X]");
                        break;
                    case EMPTY:
                        System.out.print("[E]");
                        break;
                    case BLACK:
                        System.out.print("[B]");
                        break;
                    case WHITE:
                        System.out.print("[W]");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        FishermensSolitaer game = new FishermensSolitaer();
        FishermensSolitaer.todoContent.put(Arrays.deepHashCode(game.getGameState()), game.getGameState());
        game.drawGameState();
        System.out.println(Arrays.deepHashCode(game.getGameState()));
        game.changePostion(game.getGameState()[2][3]);
        game.drawGameState();
        System.out.println(Arrays.deepHashCode(game.getGameState()));
        game.changePostion(game.getGameState()[2][2]);
        game.drawGameState();
        System.out.println(FishermensSolitaer.todoContent.containsKey(Arrays.deepHashCode(game.getGameState())));
        System.out.println(Arrays.deepHashCode(game.getGameState()));
    }
}
