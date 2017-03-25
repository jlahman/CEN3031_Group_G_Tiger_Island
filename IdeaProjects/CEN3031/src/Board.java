import java.util.List;
import java.util.Vector;

/**
 * Created by madashi on 3/23/17.
 */
public class Board {
    public Hex rootHex;
    public List<Settlement> settlementList = new Vector<Settlement>();
    //private List<List<Hex>> coord = new Vector<List<Hex>>();

    public Board(){
        rootHex = new Hex();
    }

    public void placeTile(Tile tile, Hex oldHex, int connectingHex){
        if(isPlacementValid(tile, oldHex, connectingHex)){
            /*Hex temp1 = tile.getTileHex((connectingHex + 1)%3);
            Hex temp2 = tile.getTileHex((connectingHex + 2)%3);
            tile.getOrientation();
            switch(oldHex.distance%oldHex.radius){
                case 0: switch(tile.getOrientation()) {
                    case 0:
                        temp1.radius = oldHex.radius + 1;
                        temp1.distance = oldHex.distance;
                    case 1:
                        temp1.radius = oldHex.radius + 1;
                        temp1.distance = oldHex.distance + 1;
                    case 2:
                        temp1.radius = oldHex.radius;
                        temp1.distance = oldHex.distance + 1;
                    case 5:
                        temp1.radius = oldHex.radius;
                        temp1.distance = oldHex.distance - 1;
                    case 3:
                        temp1.radius = oldHex.radius - 1;
                        temp1.distance = oldHex.distance;
                    case 4:
                        temp1.radius = oldHex.radius - 1;
                        temp1.distance = oldHex.distance - 1;
                }
            }*/
            placeHex(tile.getTileHex(connectingHex), oldHex);
            placeHex(tile.getTileHex((connectingHex + 1)%3), tile.getTileHex(connectingHex).adjHex[(2*connectingHex + tile.getOrientation())%6]);
            placeHex(tile.getTileHex((connectingHex + 2)%3), tile.getTileHex(connectingHex).adjHex[(2*connectingHex + tile.getOrientation() + 1)%6]);
        }
    }

    private void updatePlus(Hex newHex, Hex plusHex, int index){
        index = (index+5)%6;
        if(plusHex.adjHex[index] == newHex) return;
        else {
            plusHex.adjHex[index] = newHex;
            plusHex.updateAdjHexes(index);
        }
        if(plusHex.adjHex[(index+1)%6] == null)
            return;
        else
            updatePlus(newHex, plusHex.adjHex[(index+1)%6], index);
    }

    private void updateMinus(Hex newHex, Hex minusHex, int index){
        index = (index+1)%6;
        if(minusHex.adjHex[index] == newHex) return;
        else {
            minusHex.adjHex[index] = newHex;
            minusHex.updateAdjHexes(index);
        }
        if(minusHex.adjHex[(index+5)%6] == null)
            return;
        else
            updatePlus(newHex, minusHex.adjHex[(index+5)%6], index);
    }


    private void placeHex(Hex newHex, Hex oldHex){
        for(int i = 0; i < 6 /*&& oldHex.adjHex[i] != null*/; i++){
            if(oldHex.adjHex[i] == null){
                if( oldHex.adjHex[(i+5)%6] != null)
                    updateMinus(newHex.adjHex[i], oldHex.adjHex[(i+5)%6], i);
                    //oldHex.adjHex[(i+5)%6].adjHex[(i+1)%6] = newHex.adjHex[i];
                if( oldHex.adjHex[(i+1)%6] != null)
                    updatePlus(newHex.adjHex[i], oldHex.adjHex[(i+1)%6], i);
                    //oldHex.adjHex[(i+1)%6].adjHex[(i+5)%6] = newHex.adjHex[i];
            }
            else newHex.adjHex[i] = oldHex.adjHex[i];
        }
        newHex.radius = oldHex.radius;
        newHex.distance = oldHex.distance;
        if(oldHex.isSpace()){
            newHex.setLevel(1);

        }
        else {
            newHex.setLevel(oldHex.getLevel() + 1);
        }
        newHex.updateAdjHexes();
        if(oldHex == rootHex)
            rootHex = newHex;
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

        boolean validOnSpace = isSpace && sameLevel;
        boolean validOnBoard = sameLevel && onVolcano && isNotNuking && isNotNukingTotoro && isNotOnOneTile;

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

    private boolean isHexesBelowSameLevel(Tile tile, Hex oldHex, int connectingHex){
        int hexRightLevel, hexLeftLevel;
        if(oldHex.adjHex[(2*connectingHex + tile.getOrientation())%6] == null)
            hexRightLevel = 0;
        else
            hexRightLevel = oldHex.adjHex[(2*connectingHex + tile.getOrientation())%6].getLevel();
        if(oldHex.adjHex[(2*connectingHex + tile.getOrientation() + 1)%6] == null)
            hexLeftLevel = 0;
        else
            hexLeftLevel = oldHex.adjHex[(2*connectingHex + tile.getOrientation() + 1)%6].getLevel();

        if(oldHex.getLevel() == hexLeftLevel && oldHex.getLevel() == hexRightLevel){
            return true;
        }
        else
            return false;
    }

    private boolean isTileVolcanoOnVolcano(Tile tile, Hex oldHex, int connectingHex){
        Hex temp;
        if(connectingHex != 0)
            temp = oldHex.adjHex[(connectingHex + 2 + tile.getOrientation())%6];
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
            temp1 = oldHex.adjHex[0 + tile.getOrientation()];
            temp2 = oldHex.adjHex[(1 + tile.getOrientation())%6];
        }
        else if(connectingHex == 1){
            temp1 = oldHex;
            temp2 = oldHex.adjHex[(2 + tile.getOrientation())%6];
        }
        else{
            temp2 = oldHex;
            temp1 = oldHex.adjHex[(5 + tile.getOrientation())%6];
        }

        if(temp1 == null && temp2 == null){
            return true;
        }
        if(temp1 != null) {
            if (temp2 == null && (temp1.getSettlementID() == -1 || settlementList.get(temp1.getSettlementID()).settlementSize() != 1))
                return true;
        }
        if(temp2 != null) {
            if (temp1 == null && (temp2.getSettlementID() == -1 || settlementList.get(temp2.getSettlementID()).settlementSize() != 1))
                return true;
        }
        if(temp1.getSettlementID() == -1 && temp2.getSettlementID() == -1)
            return true;
        if(temp2.getSettlementID() != -1 && temp1.getSettlementID() == -1 && settlementList.get(temp2.getSettlementID()).settlementSize() != 1)
            return true;
        if(temp1.getSettlementID() != -1 && temp2.getSettlementID() == -1 && settlementList.get(temp1.getSettlementID()).settlementSize() != 1)
            return true;
        if(settlementList.get(temp1.getSettlementID()).settlementSize() != 1 && settlementList.get(temp2.getSettlementID()).settlementSize() != 1)
            return true;

        return false;
    }

    private boolean isTileOnMoreThanOneTile(Tile tile, Hex oldHex, int connectingHex){
        int numberOfTiles = 0;
        Hex hexLeft = oldHex.adjHex[(2*connectingHex + tile.getOrientation() + 1)%6];
        Hex hexRight = oldHex.adjHex[(2*connectingHex + tile.getOrientation())%6];

        if(hexLeft != null && hexRight != null) {
            if (oldHex.getTile() == hexLeft.getTile() && oldHex.getTile() == hexRight.getTile())
                return false;
        }
        return true;
    }

    private boolean isTileNotNukingTotoro(Tile tile, Hex oldHex, int connectingHex){
        Hex hexLeft = oldHex.adjHex[(2*connectingHex + tile.getOrientation() + 1)%6];
        Hex hexRight = oldHex.adjHex[(2*connectingHex + tile.getOrientation())%6];

        if(hexLeft == null && hexRight == null){
            return true;
        }
        if(hexLeft != null && hexLeft.hasTotoro() == true){
            return false;
        }
        if(hexRight != null && hexRight.hasTotoro() == true){
            return false;
        }
        return true;

    }

    private boolean searchFor(Hex h){
        return true;
    }

    public void debug(int i){
        Hex temp = rootHex.adjHex[0];
        String ppp = "Root: ";
        ppp += rootHex + "\n";
        ppp+= temp;
        for (int j = 0; j < 6; j++) {
            for(int k = 0; k < i; k++){
                temp = temp.adjHex[(2+j)%6];
                ppp += temp;
            }
        }
        System.out.print(ppp+"\n");
    }

}
