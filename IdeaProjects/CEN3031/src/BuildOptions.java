import java.util.Collections;
import java.util.List;

/**
 * Created by Harrison on 3/25/2017.
 */
public class BuildOptions {

    // Build settlement
    public void buildSettlement (Board board, int x, int y, Player p) {
        if(isBuildSettlementValid(board.hexArr[x][y], p.getMeepleCount())){
            Hex settlementHex = board.hexArr[x][y];
            settlementHex.addMeeple();
            int settlementID = board.settlementList.size();
            board.settlementList.add(new Settlement(settlementHex, settlementID));
        }
    }

    private boolean isBuildSettlementValid(Hex hex, int remainingMeeple){
        if(hex == null)
            return false;
        else {
            boolean isLevelOne = (hex.getLevel() == 1);
            boolean isEmptyHex = (hex.isEmpty());
            boolean isATerrain = (hex.getTerrain() != Terrain.VOLCANO);
            boolean isEnoughMeeple = (remainingMeeple > 0);

            return (isLevelOne && isEmptyHex && isATerrain && isEnoughMeeple);
        }
    }

    // Expand settlement
    public void expandSettlement(Board board, int settlementID, Terrain terrain, int remainingMeeple){
        if(isExpandSettlementValid()){
            addHexesToSettlement(board, settlementID, terrain);
        }
    }

    private boolean isExpandSettlementValid(){
       //Next to Do
       return true;
    }

    private void addHexesToSettlement(Board board, int settlementID, Terrain terrain){
        Settlement  temp = board.settlementList.get(settlementID);
        List<Hex> expandToHexList = BFSForTerrain(board, temp.hexesInSettlement.get(0), terrain);
        for (Hex hex : expandToHexList) {
            if(!temp.hexesInSettlement.contains(hex)){
                temp.hexesInSettlement.add(hex);
            }
        }

    }

    private List<Hex> BFSForTerrain(Board board, Hex hex, Terrain terrain){
        List<Hex> temp = Collections.emptyList();
        boolean quit = false;
        Hex current = hex;
        int index = -1;
        while (!quit) {
            for (int i = 0; i < 6; i++) {
                if(board.getAdjHex(current, i) != null) {
                    if (board.getAdjHex(current, i).getTerrain() == terrain) {
                        if (!temp.contains(board.getAdjHex(current, i))) {
                            temp.add(board.getAdjHex(current, i));
                        }
                    }
                }
            }
            if(index == temp.size() - 1)
                quit = true;
            else {
                index++;// = temp.indexOf(current) + 1;
                current = temp.get(index);
            }

        }
        return temp;
    }

    // Place Totoro
    public void buildTotoroSanctuary(Board board, int x, int y, int settlementID){
        if(isBuildTotoroSanctuaryValid()){
            board.hexArr[x][y].addTotoro();
            board.settlementList.get(settlementID).addHex(board.hexArr[x][y]);
        }
    }

    private boolean isBuildTotoroSanctuaryValid(){
        return true;
    }

    // Build tiger sanctuary
    public void buildTigerSanctuary(Board board, int x, int y, int settlementID){
        if(isBuildTigerSanctuaryValid()){
            board.hexArr[x][y].addTiger();
            board.settlementList.get(settlementID).addHex(board.hexArr[x][y]);
        }
    }

    private boolean isBuildTigerSanctuaryValid(){
        return true;
    }
}
