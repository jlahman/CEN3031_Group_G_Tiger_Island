/**
 * Created by madashi on 3/23/17.
 */
public class Hex {
    //Hexes are made up of a terrain and level. Terrain is unchangeable.
    private final Terrain terrain;
    private final int N = 6;
    private int level;
    public Hex adjHex[];
    private boolean isSpace;
    private Tile tile;
    private int meeple;
    private int totoro;
    private int settlementID;
    public int radius;
    public int distance;
    public Player owner;

    //The constructor for the hex class
    public Hex() {
        isSpace = true;
        level = 0;
        terrain = null;
        tile = null;
        adjHex = new Hex[N];
        meeple = 0;
        totoro = 0;
        settlementID = -1;
        radius = 0;
        distance = 0;
        owner = null;
    }

    public Hex(Terrain type) {

        terrain = type;
        level = 0;
        isSpace = false;
        tile = null;
        adjHex = new Hex[N];
        meeple = 0;
        totoro = 0;
        settlementID = -1;
        radius = 0;
        distance = 0;
        owner = null;

        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].adjHex[(i + 3) % 6] = this;
            if(i != 0)
                adjHex[i].adjHex[(i+4)%6] = adjHex[i-1];
        }
        adjHex[0].adjHex[4] = adjHex[5];
        for (int i = 0; i < 6; i++){
            adjHex[i].adjHex[(i+2)%6] = adjHex[(i+1)%6];
        }
    }

    //Getter for Hex terrain
    public Terrain getTerrain() {
        return terrain;
    }

    public boolean isSpace() {
        return isSpace;
    }

    //Getter for Hex terrain
    public String getTerrainAsString() {
        return terrain.getTerrainText();
    }

    //Getter for level
    public int getLevel() {
        return level;
    }

    //Setter for level
    public void setLevel(int level) {
        this.level = level;
    }


    public void updateAdjHexes() {
        for (int i = 0; i < 6 && adjHex[i] != null; i++) {
            adjHex[i].adjHex[(i + 3) % 6] = this;
        }
    }

    public void updateAdjHexes(int index) {
        if(adjHex[index] != null)
            adjHex[index].adjHex[(index + 3) % 6] = this;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile parentTile) {
        this.tile = parentTile;
    }

    public boolean hasTotoro(){
        if(totoro > 0)
            return true;
        return false;
    }

    public int getMeeple() {
        return meeple;
    }

    public void setMeeple(int meeple) {
        this.meeple = meeple;
    }

    public int getTotoro() {
        return totoro;
    }

    public void setTotoro(int totoro) {
        this.totoro = totoro;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public Player getOwner(){
        return owner;
    }
}
