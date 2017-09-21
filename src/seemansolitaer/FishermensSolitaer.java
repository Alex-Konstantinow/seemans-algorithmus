package seemansolitaer;

import jdk.nashorn.internal.parser.TokenType;

import java.lang.reflect.Array;
import java.util.*;

public class FishermensSolitaer {

    private static final int COL = 5;
    private static final int ROW = 5;
    private static final int NUMBER_OF_INITIAL_TOKENS = 8;
    private static final int NUMBER_OF_INITIAL_EMPTYFIELDS = 1;

    private static int whiteTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
    private static int blackTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
    private static int emptyFieldToInitiate = NUMBER_OF_INITIAL_EMPTYFIELDS;

    private Field[][] currentGameState = new Field[COL][ROW];
    private Field currentEmptyField;
    private static Field[][] solution = new Field[COL][ROW];

    private static HashMap<Long, GameState> todoContent = new HashMap();
    private static Comparator<GameState> comparator = new MovesDoneComparator();
    private static PriorityQueue<GameState> todo = new PriorityQueue<GameState>(1, comparator);

    public FishermensSolitaer() {
        initGame();
    }

    private void initGame() {
        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                if ((i == 0 || i == 1) && (j == 0 || j == 1) || ((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    this.currentGameState[i][j] = new Field(i, j, COL - i - 1, ROW - j - 1,
                            new Token(i, j, Tokentype.NOFIELD));
                } else {
                    if (whiteTokensToInitiate > 2) {
                        this.currentGameState[i][j] = new Field(i, j, COL - i - 1, ROW - j - 1,
                                new Token(i, j, Tokentype.WHITE));
                        whiteTokensToInitiate--;
                    } else if (whiteTokensToInitiate == 2 && blackTokensToInitiate != 6) {
                        this.currentGameState[i][j] = new Field(i, j, COL - i - 1, ROW - j - 1,
                                new Token(i, j, Tokentype.BLACK));
                        blackTokensToInitiate--;
                    } else if (i == 2 && j == 2) {
                        this.currentEmptyField = new Field(i, j, COL - i - 1, ROW - j - 1,
                                new Token(i, j, Tokentype.EMPTY));
                        this.currentGameState[i][j] = this.currentEmptyField;
                        emptyFieldToInitiate--;
                    } else if (emptyFieldToInitiate == 0 && whiteTokensToInitiate != 0) {
                        this.currentGameState[i][j] = new Field(i, j, COL - i - 1, ROW - j - 1,
                                new Token(i, j, Tokentype.WHITE));
                        whiteTokensToInitiate--;
                    } else {
                        this.currentGameState[i][j] = new Field(i, j, COL - i - 1, ROW - j - 1,
                                new Token(i, j, Tokentype.BLACK));
                        blackTokensToInitiate--;
                    }
                }
            }
        }
        GameState startState = new GameState(this.currentGameState, this.currentEmptyField);
        todoContent.put(startState.hashCode(2), startState);
        todo.add(startState);
    }

    public static void drawGameState(Field[][] currentGameState) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (currentGameState[i][j].getToken().getTokentype()) {
                    case NOFIELD:
                        System.out.print("[X]");
                        break;
                    case EMPTY:
                        System.out.print("[O]");
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

    public String solverFishermensSolitar() {
        while (!todo.isEmpty()) {
            GameState nextStateToExpand = todo.poll();
            currentGameState = nextStateToExpand.getGameState();
            currentEmptyField = nextStateToExpand.getEmptyField();
            if (isSolution(nextStateToExpand)) {
                return "Geschafft!";
            } else {
                ArrayList<GameState> expandedStates = expand(nextStateToExpand);
                add(expandedStates);
            }
        }
        return "Keine Lösung gefunden!";
    }

    private boolean isSolution(GameState gameState) {
        return gameState.getHeuristic() == 0;
    }

    private void add(ArrayList<GameState> expandedStates) {
        Iterator<GameState> iterator = expandedStates.iterator();
        while (iterator.hasNext()) {
            GameState gameState = iterator.next();
            if (todoContent.containsKey(gameState.hashCode(2))) {
                GameState existingGameState = todoContent.get(gameState.hashCode(2));
                if (gameState.getMovesDone() < existingGameState.getMovesDone()) {
                    todo.remove(todoContent.get(existingGameState.hashCode(2)));
                    todoContent.replace(gameState.hashCode(2), existingGameState, gameState);
                    todo.add(gameState);
                    drawGameState(gameState.getGameState());
                }
            } else {
                todoContent.put(gameState.hashCode(2), gameState);
                todo.add(gameState);
                //drawGameState(gameState.getGameState());

            }
            //System.out.println("Listencontenlänge: " + todoContent.size());
            //System.out.println("Listenlänge: " + todo.size());
        }
    }

    public ArrayList<GameState> expand(GameState nextStateToExpand) {
        ArrayList<GameState> expandedStates = new ArrayList<GameState>();
        //1 field move
        expandedStates.add(new GameState(nextStateToExpand).swapWithFirstNeighbour(Direction.NORTH));
        expandedStates.add(new GameState(nextStateToExpand).swapWithFirstNeighbour(Direction.SOUTH));
        expandedStates.add(new GameState(nextStateToExpand).swapWithFirstNeighbour(Direction.WEST));
        expandedStates.add(new GameState(nextStateToExpand).swapWithFirstNeighbour(Direction.EAST));
        //2 field move
        expandedStates.add(new GameState(nextStateToExpand).swapWithSecondNeighbour(Direction.NORTH));
        expandedStates.add(new GameState(nextStateToExpand).swapWithSecondNeighbour(Direction.SOUTH));
        expandedStates.add(new GameState(nextStateToExpand).swapWithSecondNeighbour(Direction.WEST));
        expandedStates.add(new GameState(nextStateToExpand).swapWithSecondNeighbour(Direction.EAST));

        return expandedStates;
    }
}
