/**
 * Created by kevin on 3/15/2017.
 * A tile is essentially a Hex configuration
 */
public class Tile{

    private TileType type; /*Types are JJ for Jungle Jungle,
                        JL JG and JR and so on until RR (double Rocky)*/
    private static final int numHexes = 3; /*There are 3 hexes per tile*/
    private static Hex hexes[] = new Hex[numHexes]; //Array containing hex objects

   /*Tile constructor*/
    public Tile(TileType tileType){

        Terrain terrainOne = getTerrainOne(tileType);
        Terrain terrainTwo = getTerrainTwo(tileType);
        /*Create new hexes for the tile*/
        hexes[0] = new Hex(Terrain.VOLCANO);
        hexes[1] = new Hex(terrainOne);
        hexes[2] = new Hex(terrainTwo);
    }
    /*Test method to see if hexes are correct*/
    public String showTile(){
        String str = "";
        for(Hex h: hexes){
            str += h.getHexTerrainAsString();
        }
        return str;
    }

    public int getTilelevel() {
        return hexes[0].getLevel();
    }

    public Terrain getTerrainOne(TileType tileType){
        /*If Terrain is a Jungle,*/
        double terrainValue = tileType.ordinal();
        Terrain terrainType = null;
        if(Math.floor(terrainValue/4) == 0){
            terrainType = Terrain.JUNGLE;
        }
        /*Elseif terrain is a lake*/
        else if(Math.floor(terrainValue/4) == 1){
            terrainType = Terrain.LAKE;
        }
        /*A Grass terrain*/
        else if(Math.floor(terrainValue/4) == 2){
            terrainType = Terrain.GRASSLANDS;
        }
        /*Rocky terrain*/
        else if(Math.floor(terrainValue/4) == 3){
            terrainType = Terrain.ROCKY;
        }
        return terrainType;
    }
    public Terrain getTerrainTwo(TileType tileType){
        /*If Terrain is a Jungle,*/
        int terrainValue = tileType.ordinal();
        Terrain terrainType = null;

        if( terrainValue % 4 == 0){
            terrainType = Terrain.JUNGLE;
        }
        /*Elseif terrain is a lake*/
        else if(terrainValue % 4 == 1){
            terrainType = Terrain.LAKE;
        }
        /*A Grass terrain*/
        else if(terrainValue % 4 == 2){
            terrainType = Terrain.GRASSLANDS;
        }
        /*Rocky terrain*/
        else if(terrainValue % 4 == 3){
            terrainType = Terrain.ROCKY;
        }
        return terrainType;
    }

}
