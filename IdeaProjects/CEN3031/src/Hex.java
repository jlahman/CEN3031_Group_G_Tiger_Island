/**
 * Created by madashi on 3/23/17.
 */
public class Hex {
    //Hexes are made up of a terrain and level. Terrain is unchangeable.
    private final Terrain terrain;
    private final int N = 6;
    private int level;
    //public int getAdjHex[];
    public int indexX, indexY;
    //private boolean isEmpty;
    private Tile tile;
    private int meeple;
    private int totoro;
    private int tiger;
    private int settlementID;

    private Player owner;

    //The constructor for the hex class
    public Hex() {
        level = 0;
        terrain = null;
        tile = null;
        //adjHex = new Hex[N];
        meeple = 0;
        totoro = 0;
        tiger = 0;
        settlementID = -1;
        owner = null;
        indexX = -1;
        indexY = -1;
    }

    public Hex(Terrain type) {

        terrain = type;
        level = 0;
        tile = null;
        indexX = -1;
        indexY = -1;
        meeple = 0;
        totoro = 0;
        tiger = 0;
        settlementID = -1;
        owner = null;
    }

    //Getter for Hex terrain
    public Terrain getTerrain() {
        return terrain;
    }

    public boolean isEmpty() {
        if(owner == null && meeple == 0 && tiger == 0 && totoro == 0)
            return true;
        return false;
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
        if(level>0)
            this.level = level;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile parentTile) {
        this.tile = parentTile;
    }

    public boolean hasTotoro(){
        if(totoro > 0 && totoro < 2)
            return true;
        return false;
    }

    public int getMeeple() {
        return meeple;
    }

    public void setMeeple(int meeple) {
        if(meeple >= 0)
            this.meeple = meeple;
    }

    public void addMeeple(){
        this.meeple = this.level;
    }

    public void addTotoro(){
        this.totoro = 1;
    }

    public void addTiger(){
        this.tiger = 1;
    }

    public int getTotoro() {
        return totoro;
    }

    public void setTotoro(int totoro) {
        if (totoro == 0 || totoro == 1)
            this.totoro = totoro;
    }

    public int getTiger() {
        return tiger;
    }

    public void setTiger(int tiger) {
        if(tiger == 0 || tiger == 1)
            this.tiger = tiger;
    }

    public boolean hasTiger(){
        if(tiger > 0 && tiger < 2)
            return true;
        return false;
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}


/*
file new maven create from archt, quckstart
whateer gid
perferences
*/