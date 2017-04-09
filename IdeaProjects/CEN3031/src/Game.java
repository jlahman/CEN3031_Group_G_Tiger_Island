import java.util.Scanner;

/**
 * Created by Justin Lahman on 3/17/17.
 */
public class Game {
    public Board board;
    public Player player1;
    public Player player2;
    public int turn;
    public boolean quit;
    public BuildOptions build;

    public Game(){
        board = new Board();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        turn = 0;
        quit = false;
        build = new BuildOptions();
        placeStartingTile();
    }

    private void placeStartingTile() {
        board.placeHex(new Hex(Terrain.VOLCANO), board.rootHex);

        board.placeHex(new Hex(Terrain.JUNGLE), board.getAdjHex(board.rootHex, 0));
        board.placeHex(new Hex(Terrain.LAKE), board.getAdjHex(board.rootHex, 1));

        board.placeHex(new Hex(Terrain.GRASSLANDS), board.getAdjHex(board.rootHex, 3));
        board.placeHex(new Hex(Terrain.ROCKY), board.getAdjHex(board.rootHex, 4));

    }

    public void setBuildOption(int bNum, int x, int y){
        switch (bNum){
            case 0: build.buildSettlement(board, board.hexArr[x][y], player2);
                break;
            case 2: build.buildTotoroSanctuary(board, board.hexArr[x][y], player2);
                break;
            case 3: build.buildTigerSanctuary(board, board.hexArr[x][y], player2);
                break;
        }
   }

    public void setBuildOption(int bNum, int x, int y, Terrain terrain){
        if(bNum == 1){
            build.expandSettlement(board, board.hexArr[x][y].getSettlementID(), terrain, player2);
        }
    }

    public void playMove(int bNum, int x, int y){
        switch (bNum){
            case 0: build.buildSettlement(board, board.hexArr[x][y], player1);
                break;
            case 2: build.buildTotoroSanctuary(board, board.hexArr[x][y], player1);
                break;
            case 3: build.buildTigerSanctuary(board, board.hexArr[x][y], player1);
                break;
        }
    }

    public void playMove(int bNum, int x, int y, Terrain t){
        if(bNum == 1){
            build.expandSettlement(board, board.hexArr[x][y].getSettlementID(), t, player1);
        }
    }
}
