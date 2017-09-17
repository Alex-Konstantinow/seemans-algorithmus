package seemansolitaer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FishermensSolitaer {

    private static final int COL = 5;
    private static final int ROW = 5;
    private static final int NUMBER_OF_INITIAL_TOKENS = 8;
    private static int expandCount = 0;

    private static int whiteTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
    private static int blackTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
    private static int emptyFieldToInitiate = 1;

    private Field[][] currentGameState = new Field[COL][ROW];
    private static Field[][] solution = new Field[COL][ROW];
    private Field currentEmptyField;

    private static HashMap<Long, GameState> todoContent = new HashMap();
    private static ArrayList<GameState> todo = new ArrayList<>();

    private FishermensSolitaer() {
        initGame();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                currentGameState[i][j].setNeighbours(currentGameState, i, j);
            }
        }
        createSolutionTest();
        add(expand());
        todo.remove(0);
        solveFishermensSolitaer();
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
                    this.currentGameState[i][j] = new Field(i, j, Token.NOFIELD);
                } else {
                    if (whiteTokensToInitiate > 2) {
                        setFieldWithWhiteToken(i, j);
                    } else if (whiteTokensToInitiate == 2 && blackTokensToInitiate != 6) {
                        setFieldWithBlackToken(i, j);
                    } else if (i == 2 && j == 2) {
                        this.currentEmptyField = new Field(i, j, Token.EMPTY);
                        this.currentGameState[i][j] = this.currentEmptyField;
                        emptyFieldToInitiate--;
                    } else if (emptyFieldToInitiate == 0 && whiteTokensToInitiate != 0) {
                        setFieldWithWhiteToken(i, j);
                    } else {
                        setFieldWithBlackToken(i, j);
                    }
                }
            }
        }
        GameState startState = new GameState(this.currentGameState, this.currentEmptyField);
        todoContent.put(hashCode(this.currentGameState), startState);
        todo.add(startState);
    }

    private void createSolutionTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                solution[i][j] = this.currentGameState[j][i];
            }
        }
    }

    private void setFieldWithWhiteToken(int x, int y) {
        this.currentGameState[x][y] = new Field(x, y, Token.WHITE);
        whiteTokensToInitiate--;
    }

    private void setFieldWithBlackToken(int x, int y) {
        this.currentGameState[x][y] = new Field(x, y, Token.BLACK);
        blackTokensToInitiate--;
    }

    private Field[][] getCurrentGameState() {
        return currentGameState;
    }

    private GameState changePostion(Field chosenField) {

        Field[][] expandedGameState = new Field[this.currentGameState.length][];
        for (int i = 0; i < COL; i++) {
            expandedGameState[i] = this.currentGameState[i].clone();
        }
        Field expandedEmptyField = new Field(this.currentEmptyField.getxPositionOnField(), this.currentEmptyField.getyPositionOnField(), Token.EMPTY);

        int emptyPositionX = this.currentEmptyField.getxPositionOnField();
        int emptyPositionY = this.currentEmptyField.getyPositionOnField();

        expandedEmptyField.setxPositionOnField(chosenField.getxPositionOnField());
        expandedEmptyField.setyPositionOnField(chosenField.getyPositionOnField());
        expandedGameState[chosenField.getxPositionOnField()][chosenField.getyPositionOnField()] = expandedEmptyField;
        chosenField.setxPositionOnField(emptyPositionX);
        chosenField.setyPositionOnField(emptyPositionY);
        expandedGameState[emptyPositionX][emptyPositionY] = chosenField;
        System.out.println("Current:");
        drawGameState(currentGameState);
        System.out.println("Expanded:");
        drawGameState(expandedGameState);
        return new GameState(expandedGameState, expandedEmptyField);
    }

    private static void drawGameState(Field[][] fields) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (fields[i][j].getToken()) {
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

    private boolean isSolution() {
        return hashCode(this.currentGameState) == hashCode(solution);
    }

    private ArrayList<GameState> expand() {
        expandCount++;
        ArrayList<GameState> expandedStates = new ArrayList<>();
        System.out.println(currentEmptyField.getxPositionOnField() + ":" + currentEmptyField.getyPositionOnField());
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                currentGameState[i][j].setNeighbours(currentGameState, i, j);
            }
        }
        currentEmptyField.setNeighbours(currentGameState, currentEmptyField.getxPositionOnField(), currentEmptyField.getyPositionOnField());
        if (this.currentEmptyField.getNextNorth() != null) {
            expandedStates.add(changePostion(this.currentEmptyField.getNextNorth()));
            if (this.currentEmptyField.isNextFieldToNeighbourFree(Direction.NORTH)) {
                expandedStates.add(changePostion(this.currentEmptyField.getNextNorth().getNextNorth()));
            }
        }

        if (this.currentEmptyField.getNextSouth() != null) {
            expandedStates.add(changePostion(this.currentEmptyField.getNextSouth()));
            if (this.currentEmptyField.isNextFieldToNeighbourFree(Direction.SOUTH)) {
                expandedStates.add(changePostion(this.currentEmptyField.getNextSouth().getNextSouth()));
            }
        }
        if (this.currentEmptyField.getNextWest() != null) {
            expandedStates.add(changePostion(this.currentEmptyField.getNextWest()));
            if (this.currentEmptyField.isNextFieldToNeighbourFree(Direction.WEST)) {
                expandedStates.add(changePostion(this.currentEmptyField.getNextWest().getNextWest()));
            }
        }
        if (this.currentEmptyField.getNextEast() != null) {
            expandedStates.add(changePostion(this.currentEmptyField.getNextEast()));
            if (this.currentEmptyField.isNextFieldToNeighbourFree(Direction.EAST)) {
                expandedStates.add(changePostion(this.currentEmptyField.getNextEast().getNextEast()));
            }
        }
        return expandedStates;
    }

    private static void add(ArrayList<GameState> expandedStates) {
        Iterator<GameState> iterator = expandedStates.iterator();
        GameState nextElement;
        while (iterator.hasNext()) {
            nextElement = iterator.next();
            if (!todoContent.containsKey(hashCode(nextElement.getGameState()))) {
                todoContent.put(hashCode(nextElement.getGameState()), nextElement);
                todo.add(nextElement);
            }
        }
    }

    public static long hashCode(Field[][] fields) {
        String code = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (fields[i][j].getToken()) {
                    case NOFIELD:
                        code += "00";
                        break;
                    case EMPTY:
                        code += "01";
                        break;
                    case WHITE:
                        code += "10";
                        break;
                    case BLACK:
                        code += "11";
                        break;
                }
            }
        }
        return Long.parseLong(code, 2);
    }

    public void solveFishermensSolitaer() {
        System.out.println("start solving...");
        while (!todo.isEmpty()) {
            this.currentGameState = todo.get(0).getGameState();
            this.currentEmptyField = todo.get(0).getEmptyField();
            todo.remove(0);
            if (isSolution()) {
                System.out.println("Solution found after " + expandCount + "turns.");
            } else {
                add(expand());
            }
        }
        System.out.println("Fail, no solution found");
    }

    public static void main(String[] args) {
        FishermensSolitaer game = new FishermensSolitaer();
    }
}
