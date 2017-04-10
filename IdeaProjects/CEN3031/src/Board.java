import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by Justin Lahman on 3/23/17.
 */
public class Board {
    public Hex rootHex;
    public List<Settlement> settlementList = new Vector<Settlement>();
    public List<Hex> playedHexes = new Vector<Hex>();
    public Hex hexArr[][];
    private int tileNumber = 48*2*2;

    public Board(){
        rootHex = new Hex();

        hexArr = new Hex[tileNumber][tileNumber];
        rootHex.indexX = rootHex.indexY = tileNumber/2;
        hexArr[rootHex.indexX][rootHex.indexY] = rootHex;
        for(int i = 0; i < 6; i++){
                int tempX, tempY;
                tempX = getAdjHexIndexX(rootHex, i);
                tempY = getAdjHexIndexY(rootHex, i);
                hexArr[tempX][tempY] = new Hex();
                hexArr[tempX][tempY].indexX = tempX;
                hexArr[tempX][tempY].indexY = tempY;
        }
    }

    public void placeTile(Tile tile, Hex oldHex, int connectingHex){
        if(isPlacementValid(tile, oldHex, connectingHex)) {
            placeHex(tile.getTileHex(connectingHex), oldHex);

            Hex temp = getAdjHex(hexArr[oldHex.indexX][oldHex.indexY], (2 * connectingHex + tile.getOrientation()) % 6);
            placeHex(tile.getTileHex((connectingHex + 1) % 3), temp);

            temp = getAdjHex(hexArr[oldHex.indexX][oldHex.indexY], (2 * connectingHex + tile.getOrientation() + 1) % 6);
            placeHex(tile.getTileHex((connectingHex + 2) % 3), temp);
        }
    }

    private void updateSettlementList( Hex oldHex){
        if(oldHex.getSettlementID() != -1){
            int oldID = oldHex.getSettlementID();
            Settlement temp = settlementList.get(oldID);
            temp.removeHex(oldHex);

            List<Hex> tList = new Vector<Hex>();
            List<List<Hex>> settlementsToMake = new Vector<List<Hex>>();

            for(int i = 0; i < 6; i++){
                if(temp.hexesInSettlement.contains(getAdjHex(oldHex, i))){
                    tList.add(getAdjHex(oldHex, i));
                }
            }
            for (Hex hex : tList) {
                boolean contains = false;
                List<Hex> BFSList = BFSForSettlement(hex);
                for(List<Hex> hexList : settlementsToMake){
                    if(hexList.containsAll(BFSList)){
                        contains = true;
                        break;
                    }
                }
                if(contains == false)
                    settlementsToMake.add(BFSList);
            }

            if(settlementsToMake.size() > 1) {
                for (List<Hex> hexList : settlementsToMake) {
                    int settlementID = settlementList.size();
                    settlementList.add(new Settlement(hexList, settlementID));
                }
                settlementList.remove(oldHex.getSettlementID());
            }
        }
    }

    public List<Hex> BFSForSettlement(Hex hex){
        List<Hex> temp = new Vector<Hex>();
        boolean quit = false;
        Hex current = hex;
        int ID = hex.getSettlementID();
        int index = -1;
        while (!quit) {
            for (int i = 0; i < 6; i++) {
                if(getAdjHex(current, i) != null) {
                    if (getAdjHex(current, i).getSettlementID() == ID) {
                        if (!temp.contains(getAdjHex(current, i))) {
                            temp.add(getAdjHex(current, i));
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

    protected void placeHex(Hex newHex, Hex oldHex){

        newHex.indexX = oldHex.indexX;
        newHex.indexY = oldHex.indexY;

        hexArr[newHex.indexX][newHex.indexY] = newHex;
        updateAdjHexes(hexArr[newHex.indexX][newHex.indexY]);


        newHex.setLevel(oldHex.getLevel() + 1);



        if(oldHex == rootHex)
            rootHex = newHex;

        playedHexes.remove(oldHex);
        playedHexes.add(newHex);
        updateSettlementList(oldHex);
    }

    private void updateAdjHexes(Hex hex){
        for(int i = 0; i < 6; i++){
            if(getAdjHex(hex, i) == null){
                int tempX, tempY;
                tempX = getAdjHexIndexX(hex, i);
                tempY = getAdjHexIndexY(hex, i);
                hexArr[tempX][tempY] = new Hex();
                hexArr[tempX][tempY].indexX = tempX;
                hexArr[tempX][tempY].indexY = tempY;
                playedHexes.add(hexArr[tempX][tempY]);
            }
        }
    }

    public boolean isPlacementValid(Tile tile, Hex oldHex, int connectingHex){

        if(oldHex == null || tile == null || (connectingHex > 2) || connectingHex < 0 || !searchFor(oldHex))
            return false;

        boolean sameLevel = isHexesBelowSameLevel(tile, oldHex, connectingHex);
        boolean onVolcano = isTileVolcanoOnVolcano(tile, oldHex, connectingHex);
        boolean isSpace = isTilePlacingOnSpace(oldHex);
        boolean isNotNuking = isTileNotNukingSizeOneSettlement(tile, oldHex, connectingHex);
        boolean isNotOnOneTile = isTileOnMoreThanOneTile(tile, oldHex, connectingHex);
        boolean isNotNukingTotoro = isTileNotNukingTotoro(tile, oldHex, connectingHex);
        boolean isNotNuking2 = isNotNukingEntireSettlement(tile, oldHex, connectingHex);
        boolean isNotNukingTiger = isTileNotNukingTiger(tile, oldHex, connectingHex);

        boolean validOnSpace = isSpace && sameLevel;
        boolean validOnBoard = sameLevel && onVolcano && isNotNuking && isNotNuking2 && isNotNukingTotoro && isNotNukingTiger && isNotOnOneTile;

        //if first turn
        if(settlementList.size() == 0){
            if(oldHex == rootHex && oldHex.getLevel() == 0){
                return true;
            }
        }
        if(validOnSpace){
            return true;
        }
        else if(validOnBoard){
            return true;
        }

        return false;
    }

    protected int getAdjHexIndexX(Hex hex, int index){
        int tempX = -1;
        switch(index){
            case 0: tempX = hex.indexX;
                break;
            case 1: tempX = (hex.indexX + 1)%tileNumber;
                break;
            case 2: tempX = (hex.indexX + 1)%tileNumber;
                break;
            case 3: tempX = hex.indexX;
                break;
            case 4: tempX = (hex.indexX + tileNumber- 1)%tileNumber;
                break;
            case 5: tempX = (hex.indexX + tileNumber- 1)%tileNumber;
                break;
        }
        return tempX;
    }

    protected int getAdjHexIndexY(Hex hex, int index){
        int tempY = -1;
        switch(index){
            case 0: tempY = (hex.indexY + tileNumber- 1)%tileNumber;
                break;
            case 1: tempY = (hex.indexY + tileNumber- 1)%tileNumber;
                break;
            case 2: tempY = hex.indexY;
                break;
            case 3: tempY = (hex.indexY + 1)%tileNumber;
                break;
            case 4: tempY = (hex.indexY + 1)%tileNumber;
                break;
            case 5: tempY = hex.indexY;
                break;
        }
        return tempY;
    }

    public Hex getAdjHex(Hex hex, int index){
        if(hex.indexX > -1 && hex.indexY > -1)
            return hexArr[getAdjHexIndexX(hex, index)][getAdjHexIndexY(hex, index)];
        else
            return null;
    }


    private boolean isHexesBelowSameLevel(Tile tile, Hex oldHex, int connectingHex){
        int hexRightLevel, hexLeftLevel;

        if(getAdjHex(oldHex, (2*connectingHex + tile.getOrientation())%6) == null)
            hexRightLevel = 0;
        else
            hexRightLevel = getAdjHex(oldHex, (2*connectingHex + tile.getOrientation())%6).getLevel();

        if(getAdjHex(oldHex, (2*connectingHex + tile.getOrientation() + 1)%6) == null)
            hexLeftLevel = 0;
        else
            hexLeftLevel = getAdjHex(oldHex, (2*connectingHex + tile.getOrientation() + 1)%6).getLevel();

        if(oldHex.getLevel() == hexLeftLevel && oldHex.getLevel() == hexRightLevel){
            return true;
        }
        else
            return false;
    }

    private boolean isTileVolcanoOnVolcano(Tile tile, Hex oldHex, int connectingHex){
        Hex temp;
        if(connectingHex != 0)
            temp = getAdjHex(oldHex, (connectingHex + 2 + tile.getOrientation())%6);
        else
            temp = oldHex;
        if(temp == null)
            return false;
        if(temp.getTerrain() == Terrain.VOLCANO)
            return true;
        return false;
    }

    private boolean isTilePlacingOnSpace(Hex oldHex){
        return oldHex.getLevel() == 0;
    }

    private boolean isTileNotNukingSizeOneSettlement(Tile tile, Hex oldHex, int connectingHex){
        Hex temp1, temp2;
        if(connectingHex == 0){
            temp1 = getAdjHex(oldHex, 0 + tile.getOrientation());
            temp2 = getAdjHex(oldHex, (1 + tile.getOrientation())%6);
        }
        else if(connectingHex == 1){
            temp1 = oldHex;
            temp2 = getAdjHex(oldHex, (2 + tile.getOrientation())%6);
        }
        else{
            temp2 = oldHex;
            temp1 = getAdjHex(oldHex, (5 + tile.getOrientation())%6);
        }
        //only false when temp 1 or temp2 contains a settlement of size 1
        //check left
            //if left is null, skip
            //else if left sID is -1, skip
            //else if left sID is not -1, and settlementList(ID) == 1
            //return false
        if(temp1 != null && temp1.getSettlementID() != -1 && settlementList.get(temp1.getSettlementID()).settlementSize() == 1)
            return false;
        if(temp2 != null && temp2.getSettlementID() != -1 && settlementList.get(temp2.getSettlementID()).settlementSize() == 1)
            return false;

        return true;
    }

    private boolean isTileOnMoreThanOneTile(Tile tile, Hex oldHex, int connectingHex){
        Hex hexLeft = getAdjHex(oldHex, (2*connectingHex + tile.getOrientation() + 1)%6); //oldHex.adjHex[(2*connectingHex + tile.getOrientation() + 1)%6];
        Hex hexRight = getAdjHex(oldHex, (2*connectingHex + tile.getOrientation())%6); //oldHex.adjHex[(2*connectingHex + tile.getOrientation())%6];

        if(hexLeft != null && hexRight != null) {
            if (oldHex.getTile() == hexLeft.getTile() && oldHex.getTile() == hexRight.getTile())
                return false;
        }
        return true;
    }

    private boolean isTileNotNukingTotoro(Tile tile, Hex oldHex, int connectingHex){
        Hex temp1, temp2;
        if(connectingHex == 0){
            temp1 = getAdjHex(oldHex, 0 + tile.getOrientation());
            temp2 = getAdjHex(oldHex, (1 + tile.getOrientation())%6);
        }
        else if(connectingHex == 1){
            temp1 = oldHex;
            temp2 = getAdjHex(oldHex, (2 + tile.getOrientation())%6);
        }
        else{
            temp2 = oldHex;
            temp1 = getAdjHex(oldHex, (5 + tile.getOrientation())%6);
        }
        if(temp1 == null && temp2 == null){
            return true;
        }
        if(temp1 != null && temp1.hasTotoro() == true){
            return false;
        }
        if(temp2 != null && temp2.hasTotoro() == true){
            return false;
        }
        return true;

    }

    private boolean isTileNotNukingTiger(Tile tile, Hex oldHex, int connectingHex){
        Hex temp1, temp2;
        if(connectingHex == 0){
            temp1 = getAdjHex(oldHex, 0 + tile.getOrientation());
            temp2 = getAdjHex(oldHex, (1 + tile.getOrientation())%6);
        }
        else if(connectingHex == 1){
            temp1 = oldHex;
            temp2 = getAdjHex(oldHex, (2 + tile.getOrientation())%6);
        }
        else{
            temp2 = oldHex;
            temp1 = getAdjHex(oldHex, (5 + tile.getOrientation())%6);
        }
        if(temp1 == null && temp2 == null){
            return true;
        }
        if(temp1 != null && temp1.hasTiger() == true){
            return false;
        }
        if(temp2 != null && temp2.hasTiger() == true){
            return false;
        }
        return true;
    }

    private boolean isNotNukingEntireSettlement(Tile tile, Hex oldHex, int connectingHex){
        Hex temp1, temp2;
        if(connectingHex == 0){
            temp1 = getAdjHex(oldHex, 0 + tile.getOrientation());
            temp2 = getAdjHex(oldHex, (1 + tile.getOrientation())%6);
        }
        else if(connectingHex == 1){
            temp1 = oldHex;
            temp2 = getAdjHex(oldHex, (2 + tile.getOrientation())%6);
        }
        else{
            temp2 = oldHex;
            temp1 = getAdjHex(oldHex, (5 + tile.getOrientation())%6);
        }



        if(temp1 == null || temp2 == null){
            return true;
        }
        if(temp1.getSettlementID() == temp2.getSettlementID() && temp1.getSettlementID() != -1){
            if(settlementList.get(temp1.getSettlementID()).settlementSize() == 2)
                return false;
        }

        return true;
    }

    private boolean searchFor(Hex h){
        return true;
    }

    /*public void draw(){
        String temp = "Game Board: \n";
        for(int i = 0; i < 2*tileNumber; i ++){
            for(int j = 0; j < 2*tileNumber; j++){
                if(hexArr[j][i] == null)
                    temp += "[\t\tnull\t\t]";
                else if(hexArr[j][i].getTerrain() == null)
                    temp += "[\t\tspace\t\t]";
                else
                    temp += "[\t\t" + hexArr[j][i].getTerrainAsString() + "\t\t]";
            }
            temp += "\n";
        }

        System.out.print(temp);
    }*/

}
