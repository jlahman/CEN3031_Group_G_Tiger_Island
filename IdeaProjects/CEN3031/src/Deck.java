/**
 * Created by kevin on 3/15/2017.
 */
public class Deck {
   // public static void main(String[] args) {

     //   Deck deckOne = new Deck();
       // System.out.println(deckOne.showTileInDeck(1));
        //System.out.println(deckOne.drawTile());
    //}
    private Tile[] deck;
    private int tilesUsed; //how many tiles have been used from the deck

    public Deck() {
        deck = new Tile[48];

        tilesUsed = 0;
        /*Create the 16 tile types as per pdf*/
        for(TileType tileType: TileType.values()){
            /*3 times*/
            for(int tileNum = 0; tileNum < 3; tileNum++){
                deck[tilesUsed] = new Tile(tileType);
                tilesUsed++;
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

        Tile t = deck[tilesUsed];
        tilesUsed++;
        return t;
    }
    //Returns the amount of unused tiles
    public int tilesLeft(){
        return deck.length - tilesUsed;
    }
    //Does what it says it does.
    public Tile showTileInDeck(int index){
        return deck[index];
    }
}
