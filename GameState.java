import java.util.List;
import java.util.Vector;

/**
 * Created by madashi on 4/16/17.
 */
public class GameState {
    public Player player1;
    public Player player2;

    public int tilesRemaining;
    //public List<Settlement> settlementsP1;
    //public List<Settlement> settlementsP2;

    public Board board;

    public GameState(){
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        tilesRemaining = 48;
        board = new Board();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof GameState ){
            GameState that = (GameState)o;
            boolean sameMeeple = this.player1.getMeepleCount() == that.player1.getMeepleCount();
            boolean sameTotoro = this.player1.getTotoroCount() == that.player1.getTotoroCount();
            boolean sameTiger = this.player1.getTigerCount() == that.player1.getTigerCount();

            boolean sameMeeple2 = this.player2.getMeepleCount() == that.player2.getMeepleCount();
            boolean sameTotoro2 = this.player2.getTotoroCount() == that.player2.getTotoroCount();
            boolean sameTiger2 = this.player2.getTigerCount() == that.player2.getTigerCount();

            boolean sameTilesRemaining = this.tilesRemaining == that.tilesRemaining;

            boolean sameSSize1 = this.player1Settlements().size() == that.player1Settlements().size();
            boolean sameSSize2 = this.player2Settlements().size() == that.player2Settlements().size();
            boolean sameToSize1 = this.player1SettlementsCanTotoroAlmost() == that.player1SettlementsCanTotoroAlmost();
            boolean sameToSize2 = this.player2SettlementsCanTotoroAlmost() == that.player2SettlementsCanTotoroAlmost();
            boolean sameTiSize1 = this.player1SettlementsCanTigerAlmost() == that.player1SettlementsCanTigerAlmost();
            boolean sameTiSize2 = this.player2SettlementsCanTigerAlmost() == that.player2SettlementsCanTigerAlmost();

            return (sameMeeple && sameMeeple2 && sameTiger && sameTiger2 && sameTotoro && sameTotoro2 && sameTilesRemaining && sameSSize1 && sameSSize2 && sameTiSize1 && sameTiSize2 && sameToSize1 && sameToSize2);
        }
        else
            return false;
    }

    private List<Settlement> player1Settlements(){
        Vector<Settlement> sList = new Vector<Settlement>();
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player1){
                sList.add(bs);
            }
        }
        return sList;
    }

    private List<Settlement> player2Settlements(){
        Vector<Settlement> sList = new Vector<Settlement>();
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player2){
                sList.add(bs);
            }
        }
        return sList;
    }

    private int player1SettlementsCanTotoroAlmost(){
        int i = 0;
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player1 && bs.settlementSize() >= 4){
                i++;
            }
        }
        return i;
    }

    private int player2SettlementsCanTotoroAlmost(){
        int i = 0;
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player2 && bs.settlementSize() >= 4){
                i++;
            }
        }
        return i;
    }

    private int player1SettlementsCanTigerAlmost(){
        int h = 0;
        boolean done = false;
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player1){
                done = false;
                for(Hex hex : bs.hexesInSettlement){
                    if(done == true)
                        break;
                    if(hex.getLevel() > 2){
                        h++;
                        break;
                    }
                    else if(hex.getLevel() == 2){
                        for(int j = 0; j < 6; j++){
                            if(board.getAdjHex(hex, j).getLevel() > 2&&board.getAdjHex(hex, j).isEmpty() ){
                                h++;
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return h;
    }
    private int player2SettlementsCanTigerAlmost(){
        int h = 0;
        boolean done = false;
        for(Settlement bs: board.settlementList){
            if(bs.getOwner() == player2){
                done = false;
                for(Hex hex : bs.hexesInSettlement){
                    if(done == true)
                        break;
                    if(hex.getLevel() > 2){
                        h++;
                        break;
                    }
                    else if(hex.getLevel() == 2){
                        for(int j = 0; j < 6; j++){
                            if(board.getAdjHex(hex, j).getLevel() > 2&&board.getAdjHex(hex, j).isEmpty() ){
                                h++;
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return h;
    }



}
