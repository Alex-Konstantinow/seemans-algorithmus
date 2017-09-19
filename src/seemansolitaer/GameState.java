package seemansolitaer;

public class GameState {

    private Field[][] gameState;
    private Field emptyField;

    public GameState(Field[][] gameState, Field emptyField) {
        this.gameState = gameState;
        this.emptyField = emptyField;
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
}
