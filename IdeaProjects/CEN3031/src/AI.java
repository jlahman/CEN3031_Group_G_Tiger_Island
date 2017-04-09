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

    public int getGameID() { return gameID; }

    private void startNewGame() {
        game = new Game();
        resetLists();
        updateLists();
    }

    private void updateLists(Tile tile, int settlementID, int terrain, int remainingMeeple, int totoroRemaining, int remainingTiger){
        updateValidTilePlacement(tile);
        updateBuildSettlement(remainingMeeple);
        updateExpandSettlement(settlementID, terrain, remainingMeeple);
        updateBuildTotoro(settlementID, totoroRemaining);
        updateBuildTiger(settlementID, remainingTiger);
    }

    private void updateValidTilePlacement(Tile tile) {
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
                    if (game.board.isPlacementValid(tile, hex, j)) {
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

    private void updateBuildSettlement(int remainingMeeple) {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if (game.board.isBuildSettlementValid(hex, remainingMeeple)) {
                if(!buildSettlement.contains(currentArr)) {
                   buildSettlement.add(currentArr);
                }
                else if(buildSettlement.contains(currentArr)) {
                    buildSettlement.remove(currentArr);
                }
            }
        }
    }

    private void updateExpandSettlement(int settlementID, int terrain, int remainingMeeple){
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[3];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            currentArr[2] = terrain;
            if (game.board.isExpandSettlementValid(game.board, settlementID, terrain, remainingMeeple)) {
                if(!expandSettlement.contains(currentArr)) {
                    expandSettlement.add(currentArr);
                }
                else if(expandSettlement.contains(currentArr)) {
                    expandSettlement.remove(currentArr);
                }
            }
        }

    }

    private void updateBuildTotoro(int settlementID, int totoroRemaining) {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if(game.board.isBuildTotoroSanctuaryValid(game.board, hex, settlementID, totoroRemaining)) {
                if(!buildTotoro.contains(currentArr)) {
                    buildTotoro.add(currentArr);
                }
                else if(buildTotoro.contains(currentArr)) {
                    buildTotoro.remove(currentArr);
                }
            }
        }
    }

    private void updateBuildTiger(int settlementID, int remainingTiger) {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            if(game.board.isBuildTigerSanctuaryValid(game.board, hex, settlementID, remainingTiger)) {
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

    public void playMove() {

    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb) {
        game.getBoard().placeTile(t, game.getBoard().hexArr[x][y], connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb);
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb, Terrain terrainType) {
        game.getBoard().placeTile(t, game.getBoard().hexArr[x][y], connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb, terrainType);
    }

    public void endGame() {
        gameEnd = true;
    }

    private void playableMove() {

    }

}
