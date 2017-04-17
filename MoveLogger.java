import java.util.*;

/**
 * Created by madashi on 4/16/17.
 */
public class MoveLogger {

    private HashMap<GameState, List<Moves>> movesGameState;

    public MoveLogger(){
        movesGameState = new HashMap<GameState, List<Moves>>(1000);
    }

    public MoveLogger(String Filename){
        movesGameState = getMovesGameStateFromFile(Filename);
    }

    public void recordMove(GameState gs, Moves m, boolean win){
        if(win){
            movesGameState.get(gs).get(movesGameState.get(gs).indexOf(m)).wins++;
        }
        movesGameState.get(gs).get(movesGameState.get(gs).indexOf(m)).total++;
    }

    public Moves getBestMove(GameState gs, boolean build ){
        List<Moves> temp = movesGameState.get(gs);
        int highestWinPercent = -1;
        if(temp != null){
            for (Moves m: temp) {
                if(m.wins/m.total > temp.get(highestWinPercent).wins/temp.get(highestWinPercent).total){
                    highestWinPercent = temp.indexOf(m);
                }
            }
        }
        if(highestWinPercent != -1){
            return temp.get(highestWinPercent);
        }
        else
            return null;
    }

    private HashMap<GameState, List<Moves>> getMovesGameStateFromFile(String FileName){
        return new HashMap<GameState, List<Moves>>(1000);
    }
    //record a move's, not turn, succuss the gamestate made in.
    //get best move for the game state if tile, or build.


}
