package seemansolitaer;

public class Field {

    private int xPositionOnField;
    private int yPositionOnField;

    private Token token;

    public Field (int x, int y, Token token) {
        this.xPositionOnField = x;
        this.yPositionOnField = y;
        this.token = token;
    }
}
