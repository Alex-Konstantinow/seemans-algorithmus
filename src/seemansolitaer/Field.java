package seemansolitaer;

public class Field {

    private int xPositionOnField;
    private int targetXPosition;
    private int yPositionOnField;
    private int targetYPosition;
    private Token token;

    public Field(int x, int y,int targetXPosition, int targetYPosition, Token token) {
        this.xPositionOnField = x;
        this.targetXPosition = targetXPosition;
        this.yPositionOnField = y;
        this.targetYPosition = targetYPosition;
        this.token = token;
    }

    public int getxPositionOnField() {
        return xPositionOnField;
    }

    public int getyPositionOnField() {
        return yPositionOnField;
    }

    public void setPositionOnField(int newPositionX, int newPositionY) {

        this.xPositionOnField = newPositionX;
        this.yPositionOnField = newPositionY;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getTargetXPosition() {
        return targetXPosition;
    }

    public void setTargetXPosition(int targetXPosition) {
        this.targetXPosition = targetXPosition;
    }

    public int getTargetYPosition() {
        return targetYPosition;
    }

    public void setTargetYPosition(int targetYPosition) {
        this.targetYPosition = targetYPosition;
    }
}
