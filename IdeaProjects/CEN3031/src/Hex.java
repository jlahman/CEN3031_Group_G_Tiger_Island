/**
 * Created by kevin on 3/15/2017.
 */
public class Hex {
    //Codes for type of terrain
    private final static int JUNGLE = 0;
    private final static int LAKE = 1;
    private final static int GRASS = 2;
    private final static int ROCKY = 3;
    private final static int VOLCANO = 4;

    //Hexes are made up of a terrain and level. Terrain is unchangeable.
    private final int terrain;
    private int level;

    //The constructor for the hex class
    public Hex(int type){
        terrain = type;
        level = 0;
    }
    //Getter for Hex terrain
    public int getHexTerrain(){
        return terrain;
    }

    //Getter for Hex terrain
    public String getHexTerrainAsString() {
        switch (terrain) {
            case ROCKY:
                return "Rocky";
            case JUNGLE:
                return "Jungle";
            case LAKE:
                return "Lake";
            case GRASS:
                return "Grass";
            case VOLCANO:
                return "Volcano";
            default:    return "Error: no Terrain";
        }
    }
    //Setter for Hex terrain
    public void setHexTerrain(int terrainType){

    }
    //Getter for level
    public int getLevel(){
        return level;
    }
    /*//Setter for level
    public void increaseLevel(){
        level++;
    }*/
}
