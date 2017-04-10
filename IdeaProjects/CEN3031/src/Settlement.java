
import java.util.List;
import java.util.Vector;

/**
 * Created by Justin Lahman on 3/23/17.
 */
public class Settlement {

    private int settlementID;
    public List<Hex> hexesInSettlement = new Vector<Hex>();

    public Settlement(Hex hex, int ID){
        hexesInSettlement.add(hex);
        settlementID = ID;
        hex.setSettlementID(settlementID);
    }

    public Settlement(List<Hex> hexList, int ID){
        hexesInSettlement = hexList;
        settlementID = ID;
        for (Hex hex : hexesInSettlement){
            hex.setSettlementID(settlementID);
        }
    }

    public int getSettlementID() {
        return settlementID;
    }

    public int settlementSize(){
        return hexesInSettlement.size();
    }

    public void addHex(Hex hex){
        hexesInSettlement.add(hex);
        hex.setSettlementID(settlementID);
        hex.setOwner(getOwner());
    }

    public void removeHex(Hex hex){
        hexesInSettlement.remove(hex);
    }

    public Player getOwner(){
        if(hexesInSettlement.size() == 0)
            return null;
        return hexesInSettlement.get(0).getOwner();
    }

    public boolean hasTotoro(){
        for(Hex h: hexesInSettlement){
            if(h.hasTotoro())
                return true;
        }
        return false;
    }

    public boolean hasTiger(){
        for(Hex h: hexesInSettlement){
            if(h.hasTiger())
                return true;
        }
        return false;
    }
}
