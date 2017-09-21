package seemansolitaer;

public class GameState{

    private Field[][] gameState;
    private Field emptyField;
    private int movesDone;
    private int heuristic;

    public GameState(Field[][] gameState, Field emptyField) {
        this.gameState = gameState;
        this.emptyField = emptyField;
        this.movesDone = 0;
        this.heuristic = calculateHeuristic();
    }

    public GameState(GameState currentGameState) {
        this.gameState = new Field[5][5];
        for(int i = 0; i< 5; i++) {
            for( int j = 0; j< 5; j++) {
                Field field = currentGameState.gameState[i][j];
                this.gameState[i][j] = new Field(field.getxPositionOnField(), field.getyPositionOnField(),
                        field.getTargetXPosition(), field.getTargetYPosition(), field.getToken());
            }
        }
        Field field = currentGameState.emptyField;
        this.emptyField = new Field(field.getxPositionOnField(), field.getyPositionOnField(),
                field.getTargetXPosition(), field.getTargetYPosition(), field.getToken());
        this.movesDone = currentGameState.movesDone + 1;
        // System.out.println("Moves DONE: " + this.movesDone);
        this.heuristic = calculateHeuristic();
        // System.out.println("Heuristik: " + this.heuristic);
    }

    /**
     * generates a hashcode based on a binary string.
     *
     * @return Integer parsed from the binarystring
     */
    public long hashCode(int radix) {
        String code = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                switch (gameState[i][j].getToken().getTokentype()) {
                    case NOFIELD:
                        code += "11";
                        break;
                    case EMPTY:
                        code += "00";
                        break;
                    case WHITE:
                        code += "01";
                        break;
                    case BLACK:
                        code += "10";
                        break;
                }
            }
        }
        return Long.parseLong(code, radix);
    }

    /**
     * Does check the possibility to swap with a 1-field distanced entity to a specific direction.
     *
     * @param direction possiblemovedirections are north, south, west, east
     */
    public GameState swapWithFirstNeighbour(Direction direction) {
        int positionX = emptyField.getxPositionOnField();
        int positionY = emptyField.getyPositionOnField();
        switch (direction) {
            case NORTH:
                if (positionY != 0 && !gameState[positionX][positionY - 1].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY - 1);
                }
                break;
            case SOUTH:
                if (positionY != 4 && !gameState[positionX][positionY + 1].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY + 1);
                }
                break;
            case WEST:
                if (positionX != 0 && !gameState[positionX - 1][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX - 1, positionY);
                }
                break;
            case EAST:
                if (positionX != 4 && !gameState[positionX + 1][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX + 1, positionY);
                }
        }
        return this;
    }

    /**
     * Does check the possibility to swap with a 2-field distanced entity to a specific direction.
     *
     * @param direction possiblemovedirections are north, south, west, east
     */
    public GameState swapWithSecondNeighbour(Direction direction) {
        int positionX = emptyField.getxPositionOnField();
        int positionY = emptyField.getyPositionOnField();
        switch (direction) {
            case NORTH:
                if (positionY > 1 && !gameState[positionX][positionY - 2].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY - 2);
                }
                break;
            case SOUTH:
                if (positionY < 3 && !gameState[positionX][positionY + 2].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY + 2);
                }
                break;
            case WEST:
                if (positionX > 1 && !gameState[positionX - 2][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX - 2, positionY);
                }
                break;
            case EAST:
                if (positionX < 3 && !gameState[positionX + 2][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX + 2, positionY);
                }
        }
        return this;
    }

    /**
     * Swaps two entitys and changes theire positions on field.
     *
     * @param positionX x position of target field
     * @param positionY y position of target field
     */
    private void swap(int positionX, int positionY) {
        int newPositionX = this.emptyField.getxPositionOnField();
        int newPositionY = this.emptyField.getyPositionOnField();
        Field swapedField = new Field(newPositionX, newPositionY, gameState[positionX][positionY].getTargetXPosition(),
                gameState[positionX][positionY].getTargetYPosition(), gameState[positionX][positionY].getToken());
        emptyField.setPositionOnField(positionX, positionY);
        gameState[positionX][positionY] = this.emptyField;
        gameState[newPositionX][newPositionY] = swapedField;
        heuristic = calculateHeuristic();
        // System.out.println("Heuristik: " + heuristic);
    }

    /**
     * generates the expected turns left for the current gamestate
     *
     * @return
     */
    private int calculateHeuristic() {
        int xMovesLeft = 0;
        int yMovesLeft = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                xMovesLeft += Math.abs(gameState[i][j].getxPositionOnField() - gameState[i][j].getTargetXPosition());
                yMovesLeft += Math.abs(gameState[i][j].getyPositionOnField() - gameState[i][j].getTargetYPosition());

            }
        }
        return (xMovesLeft + yMovesLeft) / 2;
    }

    public Field[][] getGameState() {

        return gameState;
    }

    public void setGameState(Field[][] gameState) {

        this.gameState = gameState;
    }

    public Field getEmptyField() {

        return emptyField;
    }

    public void setEmptyField(Field emptyField) {
        this.emptyField = emptyField;
    }

    public int getMovesDone() {
        return movesDone;
    }

    public void setMovesDone(int movesDone) {
        this.movesDone = movesDone;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
}
