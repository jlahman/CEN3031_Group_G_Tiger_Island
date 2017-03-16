/**
 * Created by kevin on 3/15/2017.
 */
public class Deck {
    public static void main(String[] args) {
        /*Tile tile = new Tile(6);
        System.out.println(tile.showTile());
        System.out.println(tile.getTilelevel());*/
        Deck deckOne = new Deck();
        deckOne.createDeck();
    }
    private Tile[] deck;
    private int tilesUsed; //how many tiles have been used from the deck

    public Deck() {
        deck = new Tile[48];

        tilesUsed = 0;
        /*Create the 16 tile types as per pdf*/
        for(int tileType = 0; tileType < 16; tileType++){
            /*3 times*/
            for(int tileNum = 0; tileNum < 3; tileNum++){
                deck[tilesUsed] = new Tile(tileType);
                System.out.println(deck[tilesUsed].showTile());
            }
        }
        tilesUsed = 0;
    }
    /*Add each tile to the deck*/
    public void createDeck(){


    }
    public void shuffle(){
        for ( int i = deck.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Tile temp = deck[i];
            deck[i] = deck[rand];
            deck[rand] = temp;
        }
        tilesUsed = 0;
    }
    public Tile drawTile(){
        if (tilesUsed == deck.length) {
            throw new IllegalStateException("No tiles left in the deck");
        }
        tilesUsed++;
        Tile t = deck[tilesUsed];
        return t;
    }
    public int tilesLeft(){
        return deck.length - tilesUsed;
    }
}
