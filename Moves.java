/**
 * Created by madashi on 4/16/17.
 */

/*DO not use the smart option, moves would have to be put into catagories according to their effects
*   then the AI would have to choose a move with a similar effect. Also, Game state would have to
*   change its equals to be "fuzzier" aka compare similar gamestate and hash them to the same
*   value in the Move Logger. -Justin*/
public class Moves {
    public myIntArr move;
    public int typeOfMove;
    public int wins;
    public int total;
    public boolean killedEnemySettlementNearTotoro;
    public boolean killedFriendlySettlementWithTotoro;



    @Override
    public boolean equals(Object o){
        if(o instanceof  Moves){
            Moves m = (Moves)o;
            return m.move.equals(this.move) && this.typeOfMove == m.typeOfMove;
        }
        return false;
    }
}
