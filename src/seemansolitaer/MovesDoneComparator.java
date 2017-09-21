package seemansolitaer;

import java.util.Comparator;

public class MovesDoneComparator implements Comparator<GameState> {
   
    @Override
    /**
     * Sorts the PriorityQueue according to their value of moves done + expextet moves left
     */
    public int compare(GameState left, GameState right) {
        if (left.getMovesDone() + left.getHeuristic() < right.getMovesDone() + right.getHeuristic()) {
            return -1;
        }
        if (left.getMovesDone() + left.getHeuristic() > right.getMovesDone() + right.getHeuristic()) {
            return 1;
        }
        return 0;
    }
}
