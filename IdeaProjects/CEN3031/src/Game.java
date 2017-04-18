import java.util.Scanner;

/**
 * Created by Justin Lahman on 3/17/17.
 */
public class Game {
    public GameState gameState;
    public boolean quit;
    public BuildOptions build;

    public Game(){
        gameState = new GameState();
        /*board = new Board();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        turn = 0;*/
        quit = false;
        build = new BuildOptions();
        placeStartingTile();
    }

    public Game(GameState gs){
        gameState = gs;
        build = new BuildOptions();
        quit = (gameState.tilesRemaining == 0);
        if (gameState.tilesRemaining == 48){
            placeStartingTile();
        }
    }

    private void placeStartingTile() {
        gameState.board.placeHex(new Hex(Terrain.VOLCANO), gameState.board.rootHex);

        gameState.board.placeHex(new Hex(Terrain.JUNGLE), gameState.board.getAdjHex(gameState.board.rootHex, 0));
        gameState.board.placeHex(new Hex(Terrain.LAKE), gameState.board.getAdjHex(gameState.board.rootHex, 1));

        gameState.board.placeHex(new Hex(Terrain.GRASSLANDS), gameState.board.getAdjHex(gameState.board.rootHex, 3));
        gameState.board.placeHex(new Hex(Terrain.ROCKY), gameState.board.getAdjHex(gameState.board.rootHex, 4));

    }

    public void setBuildOption(int bNum, int x, int y){
        switch (bNum){
            case 0: build.buildSettlement(gameState.board, gameState.board.hexArr[x][y], gameState.player2);
                break;
            case 2: build.buildTotoroSanctuary(gameState.board, gameState.board.hexArr[x][y], gameState.player2);
                break;
            case 3: build.buildTigerSanctuary(gameState.board, gameState.board.hexArr[x][y], gameState.player2);
                break;
        }
   }

    public void setBuildOption(int bNum, int x, int y, Terrain terrain){
        if(bNum == 1){
            build.expandSettlement(gameState.board, gameState.board.hexArr[x][y].getSettlementID(), terrain, gameState.player2);
        }
    }

    public void playMove(int bNum, int x, int y){
        switch (bNum){
            case 0: build.buildSettlement(gameState.board, gameState.board.hexArr[x][y], gameState.player1);
                break;
            case 2: build.buildTotoroSanctuary(gameState.board, gameState.board.hexArr[x][y], gameState.player1);
                break;
            case 3: build.buildTigerSanctuary(gameState.board, gameState.board.hexArr[x][y], gameState.player1);
                break;
        }
    }

    public void playMove(int bNum, int x, int y, Terrain t){
        if(bNum == 1){
            build.expandSettlement(gameState.board, gameState.board.hexArr[x][y].getSettlementID(), t, gameState.player1);
        }
    }
}
