import cucumber.api.java.tr.Ve;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by mquac on 4/8/2017.
 */

public class AI {
    public Game game;
    private String gameID;
    private boolean gameEnd = false;

    public List<myIntArr> validTilePlacement = new Vector<myIntArr>();
    public List<myIntArr> buildSettlement = new Vector<myIntArr>();
    public List<myIntArr> expandSettlement = new Vector<myIntArr>();
    public List<myIntArr> buildTotoro= new Vector<myIntArr>();
    public List<myIntArr> buildTiger = new Vector<myIntArr>();

    private class myIntArr{
        public int[] array;

        public myIntArr(int size){
            array = new int[size];
        }

        public myIntArr(int[] size){
            array = size;
        }

        @Override
        public boolean equals(Object a2){
            if(a2 instanceof myIntArr){
                myIntArr tt = (myIntArr)a2;
                return Arrays.equals(array, tt.array);
            }
            else return false;

        }

        private void doesEquals(int[] array1, myIntArr array2){

        }
    }


    public AI() {

    }

    public void setGameID(String gameID) { this.gameID = gameID; }

    public String getGameID() { return gameID; }

    public void startNewGame() {
        game = new Game();
        resetLists();
        updateLists();
    }

    protected void updateLists(){
        updateValidTilePlacement();
        updateBuildSettlement();
        updateExpandSettlement();
        updateBuildTotoro();
        updateBuildTiger();
    }

    private void updateValidTilePlacement() {
        for (Hex hex : game.board.playedHexes) {
            Tile temp = new Tile(TileType.JJ);
            for (int i = 0; i < 6; i++) {
                temp.setOrientation(i);

                for (int j = 0; j < 3; j++) {
                    int[] currentArr = new int[4];

                    currentArr[0] = hex.indexX;
                    currentArr[1] = hex.indexY;
                    currentArr[2] = i;
                    currentArr[3] = j;
                    myIntArr tempArr = new myIntArr(currentArr);
                    if (game.board.isPlacementValid(temp, hex, j)) {
                        if(!validTilePlacement.contains(tempArr)) {
                            validTilePlacement.add(tempArr);
                        }

                    }else //if(!game.board.isPlacementValid(temp, hex, j)){
                        //if (validTilePlacement.contains(currentArr)) {
                            //System.out.println("VTP SIZE BEFORE REMOVE " + validTilePlacement.size());
                            validTilePlacement.remove(tempArr);
                            //System.out.println("VTP SIZE AFTER REMOVE " + validTilePlacement.size());

                        //}
                    //}
                }
            }
        }
    }

    protected void updateBuildSettlement() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if (game.build.isBuildSettlementValid(hex, game.player1.getMeepleCount())) {
                if(!buildSettlement.contains(tempArr)) {
                    buildSettlement.add(tempArr);
                }

            }else //if(!(game.build.isBuildSettlementValid(hex, game.player1.getMeepleCount()))){
              //  if(buildSettlement.contains(currentArr)) {
                    buildSettlement.remove(tempArr);
               // }
            //}
        }
    }

    private void updateExpandSettlement(){
        Vector<myIntArr> expandToRemove = new Vector<myIntArr>();
        for(myIntArr m: expandSettlement){
            if (!game.build.isExpandSettlementValid(game.board, m.array[0], Terrain.values()[m.array[2]], game.player1)) {
                expandToRemove.add(m);
            }
        }
        //for(myIntArr move : expandToRemove){
        expandSettlement.removeAll(expandToRemove);
        //}


        for (Settlement s : game.board.settlementList) {
            Hex hex = s.hexesInSettlement.get(0);
            if(hex.getOwner() == game.player1){

                for(int i = 0; i < 4; i++) {
                    int[] currentArr = new int[3];

                    currentArr[0] = s.getSettlementID();//hex.indexX;
                    currentArr[1] = -1;//hex.indexY;
                    currentArr[2] = i;
                    myIntArr tempArr = new myIntArr(currentArr);

                    if (game.build.isExpandSettlementValid(game.board, s.getSettlementID(), Terrain.values()[i], game.player1)) {
                        if(!expandSettlement.contains(tempArr)) {
                            expandSettlement.add(tempArr);
                        }

                    }else //if (!game.build.isExpandSettlementValid(game.board, hex.getSettlementID(), hex.getTerrain(), game.player1))
                    //{
                       // if (expandSettlement.contains(currentArr)) {
                            expandSettlement.remove(tempArr);
                       // }
                    //}
                }
            }

        }


    }

    private void updateBuildTotoro() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if(game.build.isBuildTotoroSanctuaryValid(game.board, hex, game.player1)) {
                if(!buildTotoro.contains(tempArr)) {
                    buildTotoro.add(tempArr);
                }

            }else //if(!game.build.isBuildTotoroSanctuaryValid(game.board, hex, game.player1)){
                //if(buildTotoro.contains(tempArr)) {
                    buildTotoro.remove(tempArr);
                //}
            //}

        }
    }

    private void updateBuildTiger() {
        for (Hex hex : game.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if(game.build.isBuildTigerSanctuaryValid(game.board, hex, game.player1)) {
                if(!buildTiger.contains(tempArr)) {
                    buildTiger.add(tempArr);
                }

            }else //if(!game.build.isBuildTigerSanctuaryValid(game.board, hex, game.player1)) {
                //if (buildTiger.contains(currentArr)) {
                    buildTiger.remove(tempArr);
                //}
            //}
        }
    }

    private void resetLists(){
        buildSettlement.removeAll(buildSettlement);
        expandSettlement.removeAll(expandSettlement);// = new Vector<int[]>();
        buildTotoro.removeAll(buildTotoro);//= new Vector<int[]>();
        buildTiger.removeAll(buildTiger);// = new Vector<int[]>();
        validTilePlacement.removeAll(validTilePlacement);// = new Vector<int[]>();
    }

    public String playMove(Tile tile, int time) {
        int tilePlay[] = playTile(tile);
        updateLists();

        int buildPlay[] = playBuild();
        Terrain t = null;
        if(buildPlay[3] != -1){
            t = Terrain.values()[buildPlay[3]];
        }
        updateLists();

        String temp = tile.getTileTypeString() + " " + tilePlay[0] + " " + tilePlay[1] + " " + tilePlay[2] + " " + buildPlay[2] + " " + buildPlay[0] + " " + buildPlay[1];

        if(buildPlay[2] == 1){
            temp+= " " + t.getTerrainText();
        }

        return temp;//+ ;
    }

    private int[] playBuild() {
        Random rand = new Random();
        int n;
        int[] xbybbNum = new int[4];
        xbybbNum[0] = -1;
        xbybbNum[1] = -1;
        xbybbNum[2] = -1;
        xbybbNum[3] = -1;
        Terrain t = null;
        int flip = rand.nextInt(2);
        n = -1;
        if(buildTotoro.size() != 0){
            n = 2;
        }else if(buildTiger.size() != 0){
            n = 3;
        } else if(expandSettlement.size() != 0 && (flip == 0 || game.player1.getTotoroCount() == 1 )){
            n = 1;
        } else if(buildSettlement.size() != 0){
            n = 0;
        }
        switch (n){
            case 0:if(buildSettlement.size() > 0)
                         n = rand.nextInt(buildSettlement.size());
                    else{
                    System.out.println(buildSettlement.size());
                        break;}
                xbybbNum[0] = buildSettlement.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.board.rootHex.indexX;
                xbybbNum[1] = buildSettlement.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.board.rootHex.indexY;
                xbybbNum[2] = 0;
                game.playMove(0, buildSettlement.get(n).array[0], buildSettlement.get(n).array[1]);
                break;
            case 1: if(expandSettlement.size() > 0)
                        n = rand.nextInt(expandSettlement.size());
                    else
                        break;
                xbybbNum[0] = game.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexX;
                xbybbNum[0] = xbybbNum[0] - game.board.rootHex.indexX;
                xbybbNum[1] = game.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexY;
                xbybbNum[1] = xbybbNum[1] - game.board.rootHex.indexY;
                xbybbNum[2] = 1;
                xbybbNum[3] = expandSettlement.get(n).array[2];
                t = Terrain.values()[expandSettlement.get(n).array[2]];
                game.playMove(1, game.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexX, game.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexY, t);
                break;
            case 2: if(buildTotoro.size() > 0)
                n = rand.nextInt(buildTotoro.size());
            else
                break;
                xbybbNum[0] = buildTotoro.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.board.rootHex.indexX;
                xbybbNum[1] = buildTotoro.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.board.rootHex.indexY;
                xbybbNum[2] = 2;
                game.playMove(2, buildTotoro.get(n).array[0], buildTotoro.get(n).array[1]);
                break;
            case 3:if(buildTiger.size() > 0)
                n = rand.nextInt(buildTiger.size());
            else
                break;
                xbybbNum[0] = buildTiger.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.board.rootHex.indexX;
                xbybbNum[1] = buildTiger.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.board.rootHex.indexY;
                xbybbNum[2] = 3;
                game.playMove(3, buildTiger.get(n).array[0], buildTiger.get(n).array[1]);
                break;
        }
        return xbybbNum;
    }

    private int[] playTile(Tile tile) {
        Random rand = new Random();
        int n = rand.nextInt(validTilePlacement.size());
        //check if need to mod
        int[] p = new int[4];
        p[0]  = validTilePlacement.get(n).array[0];
        p[0] = p[0] - game.board.rootHex.indexX;
        p[1] = validTilePlacement.get(n).array[1];
        p[1] = p[1] - game.board.rootHex.indexY;
        p[3] = validTilePlacement.get(n).array[3];
        if(p[3] == 1){
            p[0] = game.board.getAdjHexIndexX(game.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (3 + validTilePlacement.get(n).array[2])%6);
            p[0] = p[0] - game.board.rootHex.indexX;
            p[1] = game.board.getAdjHexIndexY(game.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (3 + validTilePlacement.get(n).array[2])%6);
            p[1] = p[1] - game.board.rootHex.indexY;
        } else if(p[3] == 2){
            p[0] = game.board.getAdjHexIndexX(game.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (4 + validTilePlacement.get(n).array[2])%6);
            p[0] = p[0] - game.board.rootHex.indexX;
            p[1] = game.board.getAdjHexIndexY(game.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (4 + validTilePlacement.get(n).array[2])%6);
            p[1] = p[1] - game.board.rootHex.indexY;
        }


        p[2] = validTilePlacement.get(n).array[2] + 1;
        tile.setOrientation(p[2] -1);
        game.board.placeTile(tile, game.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], validTilePlacement.get(n).array[3]);
        return p;
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb) {
        x = x + game.board.rootHex.indexX;
        y = y + game.board.rootHex.indexY;
        xb = xb + game.board.rootHex.indexX;
        yb = yb + game.board.rootHex.indexY;

        game.board.placeTile(t, x, y, connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb);
        updateLists();
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb, Terrain terrainType) {
        x = x + game.board.rootHex.indexX;
        y = y + game.board.rootHex.indexY;
        xb = xb + game.board.rootHex.indexX;
        yb = yb + game.board.rootHex.indexY;
        game.board.placeTile(t, x,y, connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb, terrainType);
        updateLists();
    }

    public void endGame() {
        gameEnd = true;
    }

}