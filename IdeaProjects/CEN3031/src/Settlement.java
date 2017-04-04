
import java.util.List;
import java.util.Vector;

/**
 * Created by madashi on 3/23/17.
 */
public class Settlement {

    private int settlementID;
    public List<Hex> hexesInSettlement = new Vector<>();

    public Settlement(Hex hex, int ID){
        hexesInSettlement.add(hex);
        settlementID = ID;
        hex.setSettlementID(settlementID);
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

    public Player getOwner(){
        if(hexesInSettlement.size() == 0)
            return null;
        return hexesInSettlement.get(0).getOwner();
    }

}
