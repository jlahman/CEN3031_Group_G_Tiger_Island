/**
 * Created by kevin on 3/15/2017.
 * A tile is essentially a Hex configuration
 */
public class Tile{

    private int type; /*Types are JJ for Jungle Jungle,
                        JL JG and JR and so on until RR (double Rocky)*/
    private static final int numHexes = 3; /*There are 3 hexes per tile*/
    private static Hex hexes[] = new Hex[numHexes]; //Array containing hex objects

   /*Tile constructor*/
    public Tile(int tileType){

        int terrainOne = getTerrainOne(tileType);
        int terrainTwo = getTerrainTwo(tileType);
        /*Create new hexes for the tile*/
        hexes[0] = new Hex(4);
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

    public int getTerrainOne(int tileType){
        /*If Terrain is a Jungle,*/
        double terrainValue = tileType;
        if(Math.floor(terrainValue/4) == 0){
            terrainValue = 0;
        }
        /*Elseif terrain is a lake*/
        else if(Math.floor(terrainValue/4) == 1){
            terrainValue = 1;
        }
        /*A Grass terrain*/
        else if(Math.floor(terrainValue/4) == 2){
            terrainValue = 2;
        }
        /*Rocky terrain*/
        else if(Math.floor(terrainValue/4) == 3){
            terrainValue = 3;
        }
        tileType = (int) terrainValue;
        return tileType;
    }
    public int getTerrainTwo(int tileType){
        /*If Terrain is a Jungle,*/
        int terrainValue = 0;
        if(tileType % 4 == 0){
            terrainValue = 0;
        }
        /*Elseif terrain is a lake*/
        else if(tileType % 4 == 1){
            terrainValue = 1;
        }
        /*A Grass terrain*/
        else if(tileType % 4 == 2){
            terrainValue = 2;
        }
        /*Rocky terrain*/
        else if(tileType % 4 == 3){
            terrainValue = 3;
        }
        return terrainValue;
    }

}
