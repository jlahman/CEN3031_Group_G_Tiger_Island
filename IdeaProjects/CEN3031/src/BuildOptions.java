import java.util.Vector;
import java.util.List;

/**
 * Created by Harrison on 3/25/2017.
 */
public class BuildOptions {
    private List<Hex> placedHexes = new Vector<Hex>();
    private List<Settlement> settlementsToJoin = new Vector<Settlement>();

    // Build settlement
    public void buildSettlement (Board board, Hex hex, Player p) {
        if(isBuildSettlementValid(hex, p.getMeepleCount())){
            //Hex settlementHex = hex;
           hex.addMeeple(p);
            hex.setOwner(p);
            p.increaseScore(1);

            int settlementID = board.settlementList.size();
            board.settlementList.add(new Settlement(hex, settlementID));

            placedHexes.add(hex);

            checkForAdjSettlements(board);
            joinSettlements(board, hex.getSettlementID());
            cleanLists();
        }
    }

    private void checkForAdjSettlements(Board board){
        for(Hex hex : placedHexes){
            for(int i = 0; i < 6; i++){
                if(board.getAdjHex(hex, i) != null){
                    if(board.getAdjHex(hex, i).getOwner() == hex.getOwner() && board.getAdjHex(hex, i).getSettlementID() != hex.getSettlementID()){
                        settlementsToJoin.add(board.settlementList.get(board.getAdjHex(hex, i).getSettlementID()));
                    }
                }
            }
        }
    }

    private void joinSettlements(Board board, int id){
        Settlement toJoinTo = board.settlementList.get(id);
        for (Settlement s: settlementsToJoin){
            for(Settlement higher: board.settlementList){
                if(higher.getSettlementID() > s.getSettlementID()){
                    higher.setSettlementID(higher.getSettlementID() -1);// = new Settlement(higher.hexesInSettlement, higher.getSettlementID() -1);
                }
            }
            board.settlementList.remove(s);
        }

        for (Settlement s: settlementsToJoin){
            combineSettlements(board, toJoinTo.getSettlementID(), s);
        }
    }

    private void combineSettlements(Board board, int id, Settlement s){
        for(Hex hex: s.hexesInSettlement){
            board.settlementList.get(id).addHex(hex);
        }
    }

    private void cleanLists(){
        placedHexes = new Vector<Hex>();
        settlementsToJoin = new Vector<Settlement>();
    }

    public boolean isBuildSettlementValid(Hex hex, int remainingMeeple){
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

            checkForAdjSettlements(board);
            joinSettlements(board, settlementID);
            cleanLists();
        }
    }

    public boolean isExpandSettlementValid(Board board, int settlementID, Terrain terrain, int remainingMeeple){
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
                temp.addHex(hex);
                placedHexes.add(hex);
                hex.addMeeple(p);
                p.increaseScore(hex.getMeeple() * hex.getLevel());
            }
        }

    }

    protected List<Hex> BFSForTerrain(Board board, Hex hex, Terrain terrain){
        List<Hex> temp = new Vector<Hex>();
        boolean quit = false;
        Hex current = hex;
        int index = -1;
        while (!quit) {
            for (int i = 0; i < 6; i++) {
                if(board.getAdjHex(current, i) != null) {
                    if ((board.getAdjHex(current, i).isEmpty() && board.getAdjHex(current, i).getTerrain() == terrain) || board.getAdjHex(current, i).getSettlementID() == hex.getSettlementID()) {
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
    public void buildTotoroSanctuary(Board board, Hex hex, Player p){
        if(isBuildTotoroSanctuaryValid(board, hex, p)){
            hex.addTotoro(p);
            int id = findAdjSettlementForTotoro(board, hex, p);
            board.settlementList.get(id).addHex(hex);
            placedHexes.add(hex);
            p.increaseScore(200);

            checkForAdjSettlements(board);
            joinSettlements(board, id);
            cleanLists();
        }
    }

    private int findAdjSettlementForTotoro(Board board, Hex hex, Player P) {
        for(int i = 0; i <6; i++){
            if(board.getAdjHex(hex, i) != null){
                if(board.getAdjHex(hex, i).getSettlementID() != -1 && board.getAdjHex(hex, i).getOwner() == P && board.settlementList.get(board.getAdjHex(hex, i).getSettlementID()).settlementSize() >= 5 && !board.settlementList.get(board.getAdjHex(hex, i).getSettlementID()).hasTotoro())
                    return board.getAdjHex(hex, i).getSettlementID();
            }
        }
        return -1;
    }

    private int findAdjSettlementForTiger(Board board, Hex hex, Player P) {
        for(int i = 0; i <6; i++){
            if(board.getAdjHex(hex, i) != null){
                if(board.getAdjHex(hex, i).getSettlementID() != -1 && board.getAdjHex(hex, i).getOwner() == P  && !board.settlementList.get(board.getAdjHex(hex, i).getSettlementID()).hasTiger())
                    return board.getAdjHex(hex, i).getSettlementID();
            }
        }
        return -1;
    }

    public boolean isBuildTotoroSanctuaryValid(Board board, Hex hex, Player p){
        //do next
        boolean settlementIsLargeEnough = false;
        boolean settlementHasTotoro = true;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        boolean hasEnoughTotoro = (p.getTotoroCount() > 0);
        Terrain terrain = hex.getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        int id = findAdjSettlementForTotoro(board, hex, p);


        //Hex is empty
        if(hex.isEmpty()){
            hexIsEmpty = true;
        }

        //adj to size 5+ settlement

        if(id ==-1) {
            settlementIsLargeEnough = false;
            settlementHasTotoro = false;
        }
        else {
            settlementIsLargeEnough = true;
            settlementHasTotoro = board.settlementList.get(id).hasTotoro();
        }

        return (isValidTerrain && settlementIsLargeEnough && !settlementHasTotoro && hexIsEmpty && hasEnoughTotoro);
    }

    // Build tiger sanctuary
    public void buildTigerSanctuary(Board board, Hex hex, Player p){
        if(isBuildTigerSanctuaryValid(board, hex, p)){
            hex.addTiger(p);
            int id = findAdjSettlementForTiger(board, hex, p);
            board.settlementList.get(id).addHex(hex);
            placedHexes.add(hex);
            p.increaseScore(75);

            checkForAdjSettlements(board);
            joinSettlements(board, id);
            cleanLists();
        }
    }

    public boolean isBuildTigerSanctuaryValid(Board board, Hex hex, Player p){
        boolean hexIsHighEnough = false;
        boolean hexIsAdjToSettlement = false;
        boolean settlementHasTiger = true;
        boolean hexIsEmpty = false;
        boolean isValidTerrain; //non volcano terrain type
        boolean hasEnoughTiger = (p.getTigerCount() > 0);
        //boolean settlementOwnerSameAsPlacer = ();
        Terrain terrain = hex.getTerrain();
        isValidTerrain = (terrain != Terrain.VOLCANO);

        int id = findAdjSettlementForTiger(board, hex, p);


        //Hex is empty

        if(hex.isEmpty()){
            hexIsEmpty = true;
        }

        //hex is level 3+

        if (hex.getLevel() >= 3){
            hexIsHighEnough = true;
        }

        if(id ==-1) {
            settlementHasTiger = false;
        }
        else {
            settlementHasTiger = board.settlementList.get(id).hasTotoro();
        }

        return (isValidTerrain && hexIsHighEnough && hexIsEmpty && !settlementHasTiger && hasEnoughTiger);
    }
}
