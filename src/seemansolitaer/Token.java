package seemansolitaer;

public class Token {

    private int positionX;
    private int positionY;
    private Tokentype tokentype;

    public Token(int positionX, int positionY, Tokentype tokentype) {
        this.tokentype = tokentype;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Tokentype getTokentype() {
        return tokentype;
    }

    public void setTokentype(Tokentype tokentype) {
        this.tokentype = tokentype;
    }
}
