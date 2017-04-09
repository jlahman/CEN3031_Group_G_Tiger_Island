import java.util.List;
import java.util.Vector;

/**
 * Created by mquac on 4/8/2017.
 */

public class AI {
    public Game game;
    private String gameID;
    private boolean gameEnd = false;

    private List<int[]> validTilePlacement = new Vector<int[]>();
    private List<int[]> buildSettlement = new Vector<int[]>();
    private List<int[]> expandSettlement = new Vector<int[]>();
    private List<int[]> buildTotoro= new Vector<int[]>();
    private List<int[]> buildTiger = new Vector<int[]>();


    public AI(String ID) {
        gameID = ID;
    }

    public void setGameID(String gameID) { this.gameID = gameID; }

    public String getGameID() { return gameID; }

    private void startNewGame() {
        game = new Game();
        resetLists();
        updateLists();
    }

    private void updateLists(){
        updateValidTilePlacement();
        updateBuildSettlement();
        updateExpandSettlement();
        updateBuildTotoro();
        updateBuildTiger();
    }

    private void updateValidTilePlacement() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[4];
            Tile temp = new Tile(TileType.JJ);
            for (int i = 0; i < 6; i++) {
                temp.setOrientation(i);
                currentArr[0] = hex.indexX;
                currentArr[1] = hex.indexY;
                currentArr[2] = i;
                for (int j = 0; j < 3; j++) {
                    currentArr[3] = j;
                    if (game.board.isPlacementValid(temp, hex, j)) {
                        if(!validTilePlacement.contains(currentArr)) {
                            validTilePlacement.add(currentArr);
                        }
                        else if (validTilePlacement.contains(currentArr)) {
                            validTilePlacement.remove(currentArr);
                        }
                    }
                }
            }
        }
    }

    private void updateBuildSettlement() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if (game.build.isBuildSettlementValid(hex, player1.getMeepleCount())) {
                if(!buildSettlement.contains(currentArr)) {
                    buildSettlement.add(currentArr);
                }
                else if(buildSettlement.contains(currentArr)) {
                    buildSettlement.remove(currentArr);
                }
            }
        }
    }

    private void updateExpandSettlement(){
        for (Settlement s : game.board.SettlementList) {
            int[] currentArr = new int[3];
            Hex hex = s.hexesInSettlement.get(0);
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            for(int i = 0; i < 4; i++) {
                currentArr[2] = i;
                if (game.build.isExpandSettlementValid(game.board, hex.getSettlementID(), hex.getTerrain(), game.player1.getMeepleCount())) {
                    if(!expandSettlement.contains(currentArr)) {
                        expandSettlement.add(currentArr);
                    }
                    else if(expandSettlement.contains(currentArr)) {
                        expandSettlement.remove(currentArr);
                    }
                }
            }
        }

    }

    private void updateBuildTotoro() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if(game.build.isBuildTotoroSanctuaryValid(game.board, hex, game.player1.getTotoroCount)) {
                if(!buildTotoro.contains(currentArr)) {
                    buildTotoro.add(currentArr);
                }
                else if(buildTotoro.contains(currentArr)) {
                    buildTotoro.remove(currentArr);
                }
            }
        }
    }

    private void updateBuildTiger() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if(game.build.isBuildTigerSanctuaryValid(game.board, hex, game.player1.getTigerCount)) {
                if(!buildTiger.contains(currentArr)) {
                    buildTiger.add(currentArr);
                }
                else if(buildTiger.contains(currentArr)) {
                    buildTiger.remove(currentArr);
                }
            }
        }
    }

    private void resetLists(){
        buildSettlement = new Vector<int[]>();
        expandSettlement = new Vector<int[]>();
        buildTotoro= new Vector<int[]>();
        buildTiger = new Vector<int[]>();
        validTilePlacement = new Vector<int[]>();
    }

    public String playMove(Tile tile, int time) {
        game.b.placeTile();
        chooseTileToPlace();
        updateLists();
        chooseBuildOption(); // int array
        int x = x + game.board.rootHex.indexX;
        int y = y + game.board.rootHex.indexY;
        game.playBuildOption(x, y);
        updateLists();

        //% size of array - 1

        // x = x - game.board.rootHex.indexX

        return tile.getTileTypeString() + " " + ;
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb) {
        x = x + game.board.rootHex.indexX;
        y = y + game.board.rootHex.indexY;
        xb = xb + game.board.rootHex.indexX;
        yb = yb + game.board.rootHex.indexY;
        game.board.placeTile(t, game.getBoard().hexArr[x][y], connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb);
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb, Terrain terrainType) {
        game.board.placeTile(t, game.getBoard().hexArr[x][y], connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb, terrainType);
    }

    public void endGame() {
        gameEnd = true;
    }

}