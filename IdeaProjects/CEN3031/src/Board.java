import java.util.LinkedList;
import java.util.List;


/**
 * Created by Justin Lahman on 3/16/17.
 */
public class Board {

    private Hex rootVolcano;
    private List<Hex> space = new LinkedList<Hex>();
    private List<Tile> playedTiles =  new LinkedList<Tile>();

    public Board(){
        this.rootVolcano = new Hex(Terrain.VOLCANO);
        this.rootVolcano.setLevel(0);
        this.rootVolcano.setParentIndex(-1);
        this.space.add(rootVolcano);
        for (int i = 0; i < 6; i++) {
           rootVolcano.getAdjHex(i).init();
        }
    }

    public String toString(){
        String temp = "Root = " + rootVolcano.toString();
        return temp;
    }

    public void placeTile(Tile tile, int orientation, int indexOfSpace, int connectingHex){
        if(isPlacementValid(tile, orientation, indexOfSpace, connectingHex)) {
            //replace volcano hex in free tile list if placed on volcano
            //find and replace v, t1, t2 in terrain tree if tile placed on is volcano
            //set level appropriately
            //update all surrounding links to the three hexes
            //foreach hex in tile, copy links from replaced tile, adjusted for orientation, apply links
            //for current tile peices
            Hex temp = space.get(indexOfSpace);
            if(playedTiles.size()==0) {
                tile.getTileHex(0).setParentIndex(-1);
            }
            else if(temp.getParentIndex() != -1) {
                if (temp.getParent().getChild(1) == temp) {
                    temp.getParent().setChild(1, tile.getTileHex(connectingHex));
                } else if (temp.getParent().getChild(2) == temp) {
                    temp.getParent().setChild(2, tile.getTileHex(connectingHex));
                }
            }
//            temp.getChild(1).setParent(tile.getTileHex(connectingHex));
    //        temp.getChild(2).setParent(tile.getTileHex(connectingHex));


            updateHexNeighbors(temp, tile.getTileHex(connectingHex), connectingHex);

            int adjIndex[] = new int[3];

            switch(connectingHex){
                case 0: adjIndex[1] = (0 + orientation) % 6;
                        adjIndex[2] = (1 + orientation) % 6;
                        updateHexNeighbors(temp.getAdjHex((0+orientation)%6), tile.getTileHex(1), 1);
                        updateHexNeighbors(temp.getAdjHex((1+orientation)%6), tile.getTileHex(2), 2);
                        break;
                case 1: adjIndex[0] = (3 + orientation) % 6;
                        adjIndex[2] = (2 + orientation) % 6;
                        updateHexNeighbors(temp.getAdjHex((3+orientation)%6), tile.getTileHex(0), 0);
                        updateHexNeighbors(temp.getAdjHex((2+orientation)%6), tile.getTileHex(2), 2);
                        break;
                case 2: adjIndex[1] = (5 + orientation) % 6;
                        adjIndex[0] = (4 + orientation) % 6;
                        updateHexNeighbors(temp.getAdjHex((5+orientation)%6), tile.getTileHex(1), 1);
                        updateHexNeighbors(temp.getAdjHex((4+orientation)%6), tile.getTileHex(0), 0);
                        break;
                default: break;
            }

            playedTiles.add(tile);
            tile.setLevel(space.get(indexOfSpace).getLevel() + 1);
            //update maybe playable spaces
            //test if was placed one level 1 or if one volcano
            //if(tile.getTileHex(0).getLevel() == 1){
                if(playedTiles.size() == 1 || temp.getHexTerrain() == Terrain.VOLCANO){
                    space.set(indexOfSpace, tile.getTileHex(0));
                    if(playedTiles.size() == 1) rootVolcano = space.get(0);
                }
                //else if( temp.getAdjHex(adjIndex[0]).getHexTerrain() == Terrain.VOLCANO){

                //}
                else space.add(tile.getTileHex(0));

                for(int i = 2; i < 6 && tile.getTileHex(0).getAdjHex(i).isSpaceTile(); i++) {
                    space.add(tile.getTileHex(0).getAdjHex(i));
                }
                for(int i = 4; i < 6 && tile.getTileHex(1).getAdjHex(i).isSpaceTile(); i++){
                    space.add(tile.getTileHex(1).getAdjHex(i));
                }
                for(int i = 0; i < 4 && tile.getTileHex(2).getAdjHex(i).isSpaceTile(); i++){
                    space.add(tile.getTileHex(2).getAdjHex(i));
                }
           // }
            if(temp.getAdjHex(adjIndex[0]).getHexTerrain() == Terrain.VOLCANO)
                space.set(indexOfSpace, tile.getTileHex(0));

        }
    }

    private boolean isPlacementValid(Tile tile, int orientation, int indexOfSpace, int hexIndex){
        int adjIndex[] = new int[3];
        Hex temp = space.get(indexOfSpace);

        //set turn as global flags in the game class in future
        boolean volHex = false;
        boolean levZero = false;
        boolean firstTurn = false;
        boolean sameLevel = false;
        boolean isSpace = false;
        boolean notSameTile = false;
        //test if the placement is on level 1, aka no tiles below
        if(temp.getLevel() == 0)
            levZero = true;

        //test if there are no tiles on the field
        if(space.contains(rootVolcano))
            firstTurn = true;

        //test if there are three spaces available on the same level
        switch(hexIndex) {
            case 0:
                adjIndex[1] = (0 + orientation) % 6;
                adjIndex[2] = (1 + orientation) % 6;
                if (temp.isSpaceTile() && temp.getAdjHex(adjIndex[1]).isSpaceTile() &&
                        temp.getAdjHex(adjIndex[2]).isSpaceTile())
                    isSpace = true;
                else if (temp.getLevel() == temp.getAdjHex(adjIndex[1]).getLevel() &&
                        temp.getLevel() == temp.getAdjHex(adjIndex[2]).getLevel() &&
                        temp.getAdjHex(adjIndex[1]).getLevel() == temp.getAdjHex(adjIndex[2]).getLevel())
                    sameLevel = true;
                for (Tile tileF : playedTiles) {
                    if (tileF.isHexInTile(temp) && tileF.isHexInTile(temp.getAdjHex(adjIndex[1])) &&
                            tileF.isHexInTile(temp.getAdjHex(adjIndex[2]))) {
                        notSameTile = true;
                        break;
                    }
                }
                break;
            case 1:
                adjIndex[0] = (3 + orientation) % 6;
                adjIndex[2] = (2 + orientation) % 6;
                if (temp.isSpaceTile() && temp.getAdjHex(adjIndex[0]).isSpaceTile() &&
                        temp.getAdjHex(adjIndex[2]).isSpaceTile())
                    isSpace = true;
                else if (temp.getLevel() == temp.getAdjHex(adjIndex[0]).getLevel() &&
                        temp.getLevel() == temp.getAdjHex(adjIndex[2]).getLevel() &&
                        temp.getAdjHex(adjIndex[0]).getLevel() == temp.getAdjHex(adjIndex[2]).getLevel())
                    sameLevel = true;

                for (Tile tileF: playedTiles) {
                    if (tileF.isHexInTile(temp) && tileF.isHexInTile(temp.getAdjHex(adjIndex[1])) &&
                            tileF.isHexInTile(temp.getAdjHex(adjIndex[2]))) {
                        notSameTile = true;
                        break;
                    }
                }
                break;

            case 2:
                adjIndex[1] = (5 + orientation) % 6;
                adjIndex[0] = (4 + orientation) % 6;
                //if(temp.isSpaceTile() && (temp.getAdjHex(adjIndex[1]) == null) &&
                  //      temp.getAdjHex(adjIndex[0]) == null)
                    //isSpace = true;
                //else
                if(temp.isSpaceTile() && (temp.getAdjHex(adjIndex[1]).isSpaceTile()) &&
                        temp.getAdjHex(adjIndex[0]).isSpaceTile())
                    isSpace = true;
               // if (temp.getAdjHex(adjIndex[1]) != null){
                else if(temp.getLevel() == temp.getAdjHex(adjIndex[1]).getLevel() &&
                        temp.getLevel() == temp.getAdjHex(adjIndex[0]).getLevel() &&
                        temp.getAdjHex(adjIndex[1]).getLevel() == temp.getAdjHex(adjIndex[0]).getLevel())
                {
                    sameLevel = true;}
                for (Tile tileF: playedTiles) {
                    if (tileF.isHexInTile(temp) && tileF.isHexInTile(temp.getAdjHex(adjIndex[1])) &&
                            tileF.isHexInTile(temp.getAdjHex(adjIndex[2]))) {
                        notSameTile = true;
                        break;
                    }
                }
                break;
            default:
                break;
        }
        //test if the tiles volcanic hex is on a volcano
        if(hexIndex == 0){
            if(space.get(indexOfSpace).getHexTerrain() == Terrain.VOLCANO) {
                volHex = true;
            }
            else
                volHex = false;
        }
        else {
            if( temp.getAdjHex(adjIndex[0]).getHexTerrain() == Terrain.VOLCANO)
                volHex = true;
            else
                volHex = false;
        }

        return (firstTurn || (sameLevel && volHex && notSameTile) || (levZero && isSpace));
    }

    private void updateHexNeighbors(Hex oldHex, Hex newHex, int hexIndex) {
        for (int i = 0; i < 6 && oldHex.getAdjHex(i) != null; i++) {
            oldHex.getAdjHex(i).setAdjHex((i + 3) % 6, newHex);

            if(i != (hexIndex*2) && i != (hexIndex*2 + 1))
                newHex.setAdjHex(i, oldHex.getAdjHex((i + newHex.getOrientation() - oldHex.getOrientation())%6));

        }
        if(hexIndex != 0){
            if (oldHex.getHexTerrain() == Terrain.VOLCANO)
                space.remove(oldHex);
        }
    }
