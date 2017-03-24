/**
 * Created by kevin on 3/15/2017.
 */
public class Hex {
    //Codes for type of terrain
    //update to use the enum terrain kevin defined
    /* private final static int JUNGLE = 0;
    private final static int LAKE = 1;
    private final static int GRASS = 2;
    private final static int ROCKY = 3;
    private final static int VOLCANO = 4;*/

    //Hexes are made up of a terrain and level. Terrain is unchangeable.
    private final Terrain terrain;
    private int level;
    private Hex adjHex[] = new Hex[6];
    private int hexMeeplePopulation;
    private int hexTotoroPopulation;
    private int hexTigerPopulation;
    private int ownerID;
    private int settlementID;

    public void setSpace(boolean space) {
        isSpace = space;
    }

    private boolean isSpace;
    private int orientation;

    public void init(){
        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].setAdjHex((i+3)%6, this);
            // if(i!=0) adjHex[i].setAdjHex(i, adjHex[i-1]);
            // adjHex[i].setSpace(true);
        }
    }

    //The constructor for the hex class
    public Hex(){
        isSpace = true;
        //adjHex = new Hex[6];
        level = 0;
        terrain = null;

    }

    public int getHexLevel() {
        return level;
    }

    public void setHexLevel(int level) {
        this.level = level;
    }

    public int getHexMeeplePopulation() {
        return hexMeeplePopulation;
    }

    public void setHexMeeplePopulation(int hexMeeplePopulation) {
        this.hexMeeplePopulation = hexMeeplePopulation;
    }

    public int getHexTotoroPopulation() {
        return hexTotoroPopulation;
    }

    public void setHexTotoroPopulation(int hexTotoroPopulation) {
        this.hexTotoroPopulation = hexTotoroPopulation;
    }

    public int getHexTigerPopulation() {
        return hexTigerPopulation;
    }

    public void setHexTigerPopulation(int hexTigerPopulation) {
        this.hexTigerPopulation = hexTigerPopulation;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public int getOrientation() { return orientation; }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Hex(Terrain type){
        terrain = type;
        level = 0;
        //adjHex = new Hex[6];
        isSpace = false;
        orientation = 0;
        //init();
        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].setAdjHex((i+3)%6, this);
           // if(i!=0) adjHex[i].setAdjHex(i, adjHex[i-1]);
           // adjHex[i].setSpace(true);
        }
        /*adjHex[0].setAdjHex(5, adjHex[5]);
        for(int i = 0; i<5; i++){
            adjHex[i].setAdjHex(i+1, adjHex[i+1]);
        }
        adjHex[5].setAdjHex(0, adjHex[0]);*/
    }
    //Getter for Hex terrain
    public Terrain getHexTerrain(){
        return terrain;
    }

    public boolean isSpaceTile(){
        return isSpace;
    }

    //Getter for Hex terrain
    public String getHexTerrainAsString() {
     /*   switch (terrain) {
            case ROCKY:
                return "Rocky";
            case JUNGLE:
                return "Jungle";
            case LAKE:
                return "Lake";
            case GRASSLANDS:
                return "Grass";
            case VOLCANO:
                return "Volcano";
            default:    return "Error: no Terrain";
        }*/
     return terrain.getTerrainText();
    }
    //Setter for Hex terrain
    public void setHexTerrain(Terrain terrainType){

    }
    //Getter for level
    public int getLevel(){
        return level;
    }

    //when not pressed for time, add proper error checking

    public Hex getAdjHex(int index) {
        return adjHex[index];
    }

    public void setAdjHex(int index, Hex hex) {
        adjHex[index] = hex;
    }
    //Setter for level
    public void setLevel(int level){
        this.level = level;
    }

    public String toString(){
        return "test ";
    }
}
