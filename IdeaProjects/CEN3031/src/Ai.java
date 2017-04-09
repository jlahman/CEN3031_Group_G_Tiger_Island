import java.util.List;
import java.util.Vector;

/**
 * Created by mquac on 4/8/2017.
 */

public class Ai {
    public Game game;
    public Board board;
    private int gameID;
    private boolean gameEnd = false;
    private List<int[]> validTilePlacement = new Vector<int[]>();

    private List<int[]> buildSettlement = new Vector<int[]>();
    private List<int[]> expandSettlement = new Vector<int[]>();
    private List<int[]> buildTotoro= new Vector<int[]>();
    private List<int[]> buildTiger = new Vector<int[]>();

    // 0 is x, 1 y, 2 orientation
    //x, y, terraintype
    //int size 3, 0-4


    public Ai(int ID) {
        gameID = ID;
    }

    public void setGameID(int gameID) { this.gameID = gameID; }

    public int getGameID() { return gameID; }

    public void startNewGame() {
        game = new Game();
        resetLists();
        updateLists();
    }

    private void updateLists(){
        updateListsSubtractions();
        updateListsAdditions();
        updateValidTilePlacement();
        updateBuildSettlement();
        updateExpandSettlement();
        updateBuildTotoro();
        updateBuildTiger();
    }

    private void updateListsAdditions() {

    }

    private void updateListsSubtractions(){

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

    private void updateBuildSettlement() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
                temp.setOrientation(i);
                currentArr[0] = hex.indexX;
                currentArr[1] = hex.indexY;
                if (game.board.)
            }
        }
    }

    private void updateExpandSettlement(){

    }

    private void updateBuildTotoro() {

    }

    private void updateBuildTiger() {

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
