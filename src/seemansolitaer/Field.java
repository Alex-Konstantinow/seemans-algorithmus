package seemansolitaer;

public class Field {

    private int xPositionOnField;
    private int yPositionOnField;

    private Token token;

    private Field nextNorth;
    private Field nextSouth;
    private Field nextWest;
    private Field nextEast;

    public Field (int x, int y, Token token) {
        this.xPositionOnField = x;
        this.yPositionOnField = y;
        this.token = token;
    }

    public void setNeighbours(Field[][] fields, int x, int y) {
        if(x == 0) {
            this.nextWest = null;
        }
        if(x == 4) {
            this.nextEast = null;
        }
        if(y == 0) {
            this.nextNorth = null;
        }
        if(y == 4) {
            this.nextSouth = null;
        }
        if(x != 4) {
            if(fields[x+1][y].token.equals(Token.NOFIELD)) {
                this.nextEast = null;
            } else {
                this.nextEast = fields[x+1][y];
            }
        }
        if(x != 0) {
            if(fields[x-1][y].token.equals(Token.NOFIELD)) {
                this.nextWest = null;
            } else {
                if(x != 0) {
                    this.nextWest = fields[x-1][y];
                }
            }
        }
        if(y != 4) {
            if(fields[x][y+1].token.equals(Token.NOFIELD)) {
                this.nextNorth = null;
            } else {
                this.nextNorth = fields[x][y+1];
            }
        }
        if( y != 0) {
            if(fields[x][y-1].token.equals(Token.NOFIELD)) {
                this.nextSouth = null;
            } else {
                this.nextSouth = fields[x][y-1];
            }
        }

    }

    public Token getToekn() {
        return token;
    }

    public Field getNextEast() {
        return nextEast;
    }

    public Field getNextNorth() {
        return nextNorth;
    }

    public Field getNextSouth() {
        return nextSouth;
    }

    public Field getNextWest() {
        return nextWest;
    }

    public void setNextNorth(Field[][] fields, int x, int y) {

    }

    public void setNextSouth(Field[][] fields, int x, int y) {

    }

    public void setNextWest(Field[][] fields, int x, int y) {

    }

    public void setNextEast(Field[][] fields, int x, int y) {

    }
}
