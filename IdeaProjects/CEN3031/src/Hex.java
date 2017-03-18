/**
 * Created by kevin on 3/15/2017.
 */
public class Hex {
    //Hexes are made up of a terrain and level. Terrain is unchangeable.
    private final Terrain terrain;
    private final int  N = 6;
    private int level;
    private Hex adjHex[] = new Hex[N];
    private int parentIndex;//, childOneIndex, childTwoIndex;
    private boolean isSpace;
    private int orientation;

    //The constructor for the hex class
    public Hex(){
        isSpace = true;
        level = 0;
        terrain = null;
    }

    public Hex(Terrain type){

        terrain = type;
        level = 0;
        isSpace = false;
        orientation = 0;
        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].setParentIndex((i+3)%6);
            adjHex[i].setAdjHex((i+3)%6, this);
        }
    }

    public void init(){
        for (int i = 0; i < 6; i++) {
            adjHex[i] = new Hex();
            adjHex[i].setParentIndex((i+3)%6);
            adjHex[i].setAdjHex((i+3)%6, this);
        }
    }

    //Getter for Hex terrain
    public Terrain getHexTerrain(){
        return terrain;
    }

    public void setSpace(boolean space) {
        isSpace = space;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

   /* public int getChildOneIndex() {
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
    }*/


    public Hex getParent() {
        return this.adjHex[parentIndex];
    }

    public void setParent(Hex parent) {
        this.adjHex[parentIndex] = parent;
    }

   /* public Hex getChild(int index) {
        if(index != parentIndex) {
            return adjHex[index];
        }
        else
        }
            return this.adjHex[childOneIndex];
        else
            return this.adjHex[childTwoIndex];
    }

    public void setChild(int index, Hex child) {
        if(index == 1)
            this.adjHex[childOneIndex] = child;
        else
            this.adjHex[childTwoIndex] = child;
    }*/

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
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

    //Getter for level
    public int getLevel(){
        return level;
    }

    //Setter for level
    public void setLevel(int level){
        this.level = level;
    }

    //when not pressed for time, add proper error checking
    public Hex getAdjHex(int index) {
        return adjHex[index];
    }

    public void setAdjHex(int index, Hex hex) {
        adjHex[index] = hex;
    }


    public String toString(){
        return "test ";
    }
}
