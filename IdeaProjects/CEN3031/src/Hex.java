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
    private int parentIndex, childOneIndex, childTwoIndex;

    public void setSpace(boolean space) {
        isSpace = space;
    }

    private boolean isSpace;
    private int orientation;

    public void init(){
        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].setParentIndex((i+3)%6);
            adjHex[i].setAdjHex((i+3)%6, this);
            // if(i!=0) adjHex[i].setAdjHex(i, adjHex[i-1]);
            // adjHex[i].setSpace(true);
        }
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getChildOneIndex() {
        return childOneIndex;
    }

    public void setChildOneIndex(int childOneIndex) {
        this.childOneIndex = childOneIndex;
    }

    public int getChildTwoIndex() {
        return childTwoIndex;
    }

    public void setChildTwoIndex(int childTwoIndex) {
        this.childTwoIndex = childTwoIndex;
    }


    public Hex getParent() {
        return this.adjHex[parentIndex];
    }

    public void setParent(Hex parent) {
        this.adjHex[parentIndex] = parent;
    }

    public Hex getChild(int index) {
        if(index == 1)
            return this.adjHex[childOneIndex];
        else
            return this.adjHex[childTwoIndex];
    }

    public void setChild(int index, Hex child) {
        if(index == 1)
            this.adjHex[childOneIndex] = child;
        else
            this.adjHex[childTwoIndex] = child;
    }

    //The constructor for the hex class
    public Hex(){
        isSpace = true;
        //adjHex = new Hex[6];
        level = 0;
        terrain = null;

    }

    public int getOrientation() {
        return orientation;
    }

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
            adjHex[i].setParentIndex((i+3)%6);
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
