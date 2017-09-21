package seemansolitaer;

public class GameState{

    private Field[][] fields;
    private Field emptyField;
    private int movesDone;
    private int heuristic;

    public GameState(Field[][] fields, Field emptyField) {
        this.fields = fields;
        this.emptyField = emptyField;
        this.movesDone = 0;
        this.heuristic = calculateHeuristic();
    }

    public GameState(GameState currentGameState) {
        this.fields = new Field[5][5];
        for(int i = 0; i< 5; i++) {
            for( int j = 0; j< 5; j++) {
                Field field = currentGameState.fields[i][j];
                this.fields[i][j] = new Field(field.getxPositionOnField(), field.getyPositionOnField(),
                        field.getTargetXPosition(), field.getTargetYPosition(), field.getToken());
            }
        }
        Field field = currentGameState.emptyField;
        this.emptyField = new Field(field.getxPositionOnField(), field.getyPositionOnField(),
                field.getTargetXPosition(), field.getTargetYPosition(), field.getToken());
        this.movesDone = currentGameState.movesDone + 1;
        this.heuristic = calculateHeuristic();
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
                switch (fields[i][j].getToken().getTokentype()) {
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
                if (positionY != 0 && !fields[positionX][positionY - 1].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY - 1);
                }
                break;
            case SOUTH:
                if (positionY != 4 && !fields[positionX][positionY + 1].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY + 1);
                }
                break;
            case WEST:
                if (positionX != 0 && !fields[positionX - 1][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX - 1, positionY);
                }
                break;
            case EAST:
                if (positionX != 4 && !fields[positionX + 1][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
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
                if (positionY > 1 && !fields[positionX][positionY - 2].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY - 2);
                }
                break;
            case SOUTH:
                if (positionY < 3 && !fields[positionX][positionY + 2].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX, positionY + 2);
                }
                break;
            case WEST:
                if (positionX > 1 && !fields[positionX - 2][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
                    swap(positionX - 2, positionY);
                }
                break;
            case EAST:
                if (positionX < 3 && !fields[positionX + 2][positionY].getToken().getTokentype().equals(Tokentype.NOFIELD)) {
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
        Field swapedField = new Field(newPositionX, newPositionY, fields[positionX][positionY].getTargetXPosition(),
                fields[positionX][positionY].getTargetYPosition(), fields[positionX][positionY].getToken());
        emptyField.setPositionOnField(positionX, positionY);
        fields[positionX][positionY] = this.emptyField;
        fields[newPositionX][newPositionY] = swapedField;
        heuristic = calculateHeuristic();
    }

    /**
     * generates the expected turns left for the current gamestate
     *
     * @return heuristic divided by 2, because we expect to do always 2 steps per turn
     */
    private int calculateHeuristic() {
        int xMovesLeft = 0;
        int yMovesLeft = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                xMovesLeft += Math.abs(fields[i][j].getxPositionOnField() - fields[i][j].getTargetXPosition());
                yMovesLeft += Math.abs(fields[i][j].getyPositionOnField() - fields[i][j].getTargetYPosition());

            }
        }
        return (xMovesLeft + yMovesLeft) / 2;
    }

    public Field[][] getFields() {

        return fields;
    }

    public void setFields(Field[][] fields) {

        this.fields = fields;
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
