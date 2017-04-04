import java.util.Collections;
import java.util.List;

/**
 * Created by Harrison on 3/25/2017.
 */
public class BuildOptions {

    // Build settlement
    public void buildSettlement (Board board, Hex hex, Player p) {
        if(isBuildSettlementValid(hex, p.getMeepleCount())){
            Hex settlementHex = hex;
            settlementHex.addMeeple();
            settlementHex.setOwner(p);
            p.increaseScore(1);

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
    public void expandSettlement(Board board, int settlementID, Terrain terrain, Player p){
        if(isExpandSettlementValid(board, settlementID, terrain, p.getMeepleCount())){
            addHexesToSettlement(board, settlementID, terrain, p);
        }
    }

    private boolean isExpandSettlementValid(Board board, int settlementID, Terrain terrain, int remainingMeeple){
        /*check all tiles going to be adding check the levels
        and sum to see if enough meeples remaining
        in expand settlement its remaining meeples*/
        boolean isValidTerrain = (terrain != Terrain.VOLCANO); //non volcano terrain type
        boolean isAdjTerrain = false;


        //At least one hex of the same terrain type adj to the settlement
        //Settlement contains a list hexes as a vector
        Settlement  temp = board.settlementList.get(settlementID);
        //for each hex in the settlement, check adj hexes
        /*Hex hex = null;
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
        }*/

        //Enough Meeple to do action

        List<Hex> hexList = BFSForTerrain(board, temp.hexesInSettlement.get(0), terrain);
        //noinspection StatementWithEmptyBody
        if(hexList.size() > temp.settlementSize())
            isAdjTerrain = true;
        int needMeepleNum = 0;
        for(Hex tempHex: hexList){
            //sum up meeples for each hex
            if (!temp.hexesInSettlement.contains(tempHex)) {
                needMeepleNum += tempHex.getLevel();
            }
        }
        boolean isEnoughMeeple = (remainingMeeple >= needMeepleNum);
        return(isValidTerrain && isEnoughMeeple && isAdjTerrain);
    }

    private void addHexesToSettlement(Board board, int settlementID, Terrain terrain, Player p){
        Settlement  temp = board.settlementList.get(settlementID);
        List<Hex> expandToHexList = BFSForTerrain(board, temp.hexesInSettlement.get(0), terrain);
        for (Hex hex : expandToHexList) {
            if(!temp.hexesInSettlement.contains(hex)){
                temp.hexesInSettlement.add(hex);
                hex.addMeeple();
                p.increaseScore(hex.getMeeple() * hex.getLevel());
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
    public void buildTotoroSanctuary(Board board, Hex hex, int settlementID, Player p){
        if(isBuildTotoroSanctuaryValid(board, hex, settlementID, p.getTotoroCount())){
            hex.addTotoro();
            board.settlementList.get(settlementID).addHex(hex);
            p.increaseScore(200);
        }
    }

    private boolean isBuildTotoroSanctuaryValid(Board board, Hex hex, int settlementID, int totoroRemaining){
        //do next
        boolean settlementIsLargeEnough = false;
        boolean settlementHasTotoro = false;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        boolean hasEnoughTotoro = (totoroRemaining > 0);
        Terrain terrain = hex.getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        //Hex is empty
        if(hex.isEmpty()){
            hexIsEmpty = true;
        }

        //adj to size 5+ settlement
        Settlement temp = board.settlementList.get(settlementID);
        if(temp.settlementSize() >= 5){
            settlementIsLargeEnough = true;
        }

        Hex tHex;
        //settlement doesn't already contain totoro sanctuary
        for(int i = 0; i < temp.settlementSize(); i++) {
            tHex = temp.hexesInSettlement.get(i);
            if (tHex.hasTotoro()){
                settlementHasTotoro = true;
            }
        }
        return (isValidTerrain && settlementIsLargeEnough && !settlementHasTotoro && hexIsEmpty && hasEnoughTotoro);
    }

    // Build tiger sanctuary
    public void buildTigerSanctuary(Board board, Hex hex, int settlementID, Player p){
        if(isBuildTigerSanctuaryValid(board, hex, settlementID, p.getTigerCount())){
            hex.addTiger();
            board.settlementList.get(settlementID).addHex(hex);
            p.increaseScore(75);
        }
    }

    private boolean isBuildTigerSanctuaryValid(Board board, Hex hex, int settlementID, int remainingTiger){
        boolean hexIsHighEnough = false;
        boolean hexIsAdjToSettlement = false;
        boolean settlementHasTiger = true;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        boolean hasEnoughTiger = (remainingTiger > 0);
        //boolean settlementOwnerSameAsPlacer = ();
        Terrain terrain = hex.getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        //Hex is empty

        if(hex.isEmpty()){
            hexIsEmpty = true;
        }

        //hex is level 3+

        if (hex.getLevel() >= 3){
            hexIsHighEnough = true;
        }

        Settlement  temp = board.settlementList.get(settlementID);

        Hex tHex;
        //settlement doesn't already contain tiger sanctuary
        for(int i = 0; i < temp.settlementSize(); i++) {
            tHex = temp.hexesInSettlement.get(i);
            if (tHex.hasTotoro()){
                settlementHasTiger = true;
            }
        }
        //hex is adjacent to settlement
        for(int i = 0; i < 6; i++){
            tHex = board.getAdjHex(hex, i);
            if(temp.hexesInSettlement.contains(tHex)){
                hexIsAdjToSettlement = true;
                i = 6;
            }
        }
        return (isValidTerrain && hexIsAdjToSettlement && hexIsHighEnough && hexIsEmpty && !settlementHasTiger && hasEnoughTiger);
    }
}
