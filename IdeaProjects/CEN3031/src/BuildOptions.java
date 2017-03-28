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
        if(isExpandSettlementValid(board, settlementID, terrain, remainingMeeple)){
            addHexesToSettlement(board, settlementID, terrain);
        }
    }

    private boolean isExpandSettlementValid(Board board, int settlementID, Terrain terrain, int remainingMeeple){
        /*check all tiles going to be adding check the levels
        and sum to see if enough meeples remaining
        in expand settlement its remaining meeples*/
       //Next to Do
        boolean isValidTerrain = (terrain != Terrain.VOLCANO); //non volcano terrain type
        boolean isAdjTerrain = false;


        //At least one hex of the same terrain type adj to the settlement
        //Settlement contains a list hexes as a vector
        Settlement  temp = board.settlementList.get(settlementID);
        //for each hex in the settlement, check adj hexes
        Hex hex = null;
        int j=0;
        for(int i = 0; i < temp.settlementSize(); i++){
            hex = temp.hexesInSettlement.get(i);

            //while the adj hex is not of the same terrain and not of the same settlement, check each adj hex
            while (terrain != board.getAdjHex(hex,j).getTerrain()
                    && (!temp.hexesInSettlement.contains(board.getAdjHex(hex, j)))
                    || j > 5){
                if(terrain == board.getAdjHex(hex,j).getTerrain()){
                    isAdjTerrain = true;
                    break;
                }
                j++;
            }
            if(isAdjTerrain)
                break;
        }

        //Enough Meeple to do action
        List<Hex> hexList = BFSForTerrain(board, hex, terrain);
        //noinspection StatementWithEmptyBody
        int needMeepleNum = 0;
        for(Hex tempHex: hexList){
            //sum up meeples for each hex
            if (!temp.hexesInSettlement.contains(tempHex)) {
                needMeepleNum += tempHex.getLevel();
            }
        }
        boolean isEnoughMeeple = (remainingMeeple > needMeepleNum);
        return(isValidTerrain && isEnoughMeeple && isAdjTerrain);
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
        if(isBuildTotoroSanctuaryValid(board, settlementID, x, y)){
            board.hexArr[x][y].addTotoro();
            board.settlementList.get(settlementID).addHex(board.hexArr[x][y]);
        }
    }

    private boolean isBuildTotoroSanctuaryValid(Board board, int settlementID, int x, int y){
        //do next
        boolean settlementIsLargeEnough = false;
        boolean settlementHasTotoro = false;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        Terrain terrain = board.hexArr[x][y].getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        //Hex is empty

        if(board.hexArr[x][y].hasTotoro()
            || board.hexArr[x][y].hasTiger()
            || board.hexArr[x][y].getMeeple() > 0){
            hexIsEmpty = true;
        }

        //adj to size 5+ settlement
        Settlement  temp = board.settlementList.get(settlementID);
        if(temp.settlementSize() >= 5){
            settlementIsLargeEnough = true;
        }

        Hex hex;
        //settlement doesn't already contain totoro sanctuary
        for(int i = 0; i < temp.settlementSize(); i++) {
            hex = temp.hexesInSettlement.get(i);
            if (hex.hasTotoro()){
                settlementHasTotoro = true;
            }
        }
        return (isValidTerrain && settlementIsLargeEnough && !settlementHasTotoro && hexIsEmpty);
    }

    // Build tiger sanctuary
    public void buildTigerSanctuary(Board board, int x, int y, int settlementID){
        if(isBuildTigerSanctuaryValid(board, x, y, settlementID)){
            board.hexArr[x][y].addTiger();
            board.settlementList.get(settlementID).addHex(board.hexArr[x][y]);
        }
    }

    private boolean isBuildTigerSanctuaryValid(Board board, int x, int y, int settlementID){
        boolean hexIsHighEnough = false;
        boolean hexIsAdjToSettlement = false;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        Terrain terrain = board.hexArr[x][y].getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        //Hex is empty

        if(board.hexArr[x][y].hasTotoro()
                || board.hexArr[x][y].hasTiger()
                || board.hexArr[x][y].getMeeple() > 0){
            hexIsEmpty = true;
        }

        //hex is level 3+

        if (board.hexArr[x][y].getLevel() >= 3){
            hexIsHighEnough = true;
        }

        //hex is adjacent to settlement
        Hex hex;
        int j = 0;
        Settlement  temp = board.settlementList.get(settlementID);
        for(int i = 0; i < 6; i++){
            hex = board.getAdjHex(board.hexArr[x][y], i);
            if(temp.hexesInSettlement.contains(hex)){
                hexIsAdjToSettlement = true;
            }
        }
        return (isValidTerrain && hexIsAdjToSettlement && hexIsHighEnough && hexIsEmpty);
    }
}
