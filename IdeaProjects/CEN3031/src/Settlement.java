
import java.util.List;
import java.util.Vector;

/**
 * Created by madashi on 3/23/17.
 */
public class Settlement {
   // private int settlementSize;
    private List<Hex> hexesInSettlement = new Vector<>();

    public int settlementSize(){
        return hexesInSettlement.size();
    }

    public void addHex(Hex hex){
        hexesInSettlement.add(hex);
    }

    public Player getOwner(){
        if(hexesInSettlement.size() == 0)
            return null;
        return hexesInSettlement.get(0).getOwner();
    }

}
