
public class Main {

    public static void main(String[] args) {
        Board gameBoard = new Board();

        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);

        gameBoard.placeTile(t1, gameBoard.rootHex, 0);
        //gameBoard.debug(1);
        gameBoard.placeTile(t2, t1.getTileHex(2).adjHex[3], 1);
        //gameBoard.debug(1);
        gameBoard.placeTile(t3, t1.getTileHex(0), 0);
        //gameBoard.debug(1);
        t3.setOrientation(1);
        gameBoard.placeTile(t3, t1.getTileHex(0), 0);
        int i = 3;
        System.out.println(i);
    }
}
