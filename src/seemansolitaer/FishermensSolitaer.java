package seemansolitaer;

public class FishermensSolitaer {

    private final static int COL = 5;
    private final static int ROW = 5;
    private final static int NUMBER_OF_INITIAL_TOKENS = 8;

    private static int whiteTokensToInitiate;
    private static int blackTokensToInitiate;
    private static int emptyFieldToInitiate;

    private static Field[][] spielfeld;

    public FishermensSolitaer() {
        FishermensSolitaer.spielfeld = new Field[COL][ROW];
        FishermensSolitaer.whiteTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
        FishermensSolitaer.blackTokensToInitiate = NUMBER_OF_INITIAL_TOKENS;
        FishermensSolitaer.emptyFieldToInitiate = 1;
        initGame();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                spielfeld[i][j].setNeighbours(spielfeld, i, j);
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
                System.out.print(" ");
                if ((i == 0 || i == 1) && (j == 0 || j == 1) || ((i == 3 || i == 4) && (j == 3 || j == 4))) {
                    FishermensSolitaer.spielfeld[i][j] = new Field(i, j, Token.NOFIELD);
                    System.out.print("[X]");
                } else {
                    if (FishermensSolitaer.whiteTokensToInitiate != 0) {
                        FishermensSolitaer.setFieldWithWhiteToken(i, j);
                    } else if (FishermensSolitaer.whiteTokensToInitiate == 2 && FishermensSolitaer.blackTokensToInitiate != 6) {
                        FishermensSolitaer.setFieldWithBlackToken(i, j);
                    } else if (i == 2 && j == 2) {
                        FishermensSolitaer.spielfeld[i][j] = new Field(i, j, Token.EMPTY);
                        FishermensSolitaer.emptyFieldToInitiate--;
                        System.out.print("[E]");
                    } else if(FishermensSolitaer.emptyFieldToInitiate == 0 && FishermensSolitaer.whiteTokensToInitiate != 0) {
                        FishermensSolitaer.setFieldWithWhiteToken(i, j);
                    } else {
                        FishermensSolitaer.setFieldWithBlackToken(i, j);
                    }
                }
            }
            System.out.println();
        }
    }

    private static void setFieldWithWhiteToken(int x, int y) {
        FishermensSolitaer.spielfeld[x][y] = new Field(x, y, Token.WHITE);
        FishermensSolitaer.whiteTokensToInitiate--;
        System.out.print("[W]");
    }

    private static void setFieldWithBlackToken(int x, int y) {
        FishermensSolitaer.spielfeld[x][y] = new Field(x, y, Token.BLACK);
        FishermensSolitaer.blackTokensToInitiate--;
        System.out.print("[B]");
    }

    public static void main(String[] args) {
        FishermensSolitaer game = new FishermensSolitaer();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                System.out.println("TOKEN: " + FishermensSolitaer.spielfeld[i][j].getToekn().toString());
                System.out.println("NORTH: " + FishermensSolitaer.spielfeld[i][j].getNextNorth());
                System.out.println("SOUTH: " + FishermensSolitaer.spielfeld[i][j].getNextSouth());
                System.out.println("WEST: " + FishermensSolitaer.spielfeld[i][j].getNextWest());
                System.out.println("EAST: " + FishermensSolitaer.spielfeld[i][j].getNextEast());
            }
        }
    }
}
