import java.util.*;

/**
 * Created by mquac on 4/8/2017.
 */

public class AI {
    public Game game;
    private boolean smart = false;
    private String gameID;
    private boolean gameEnd = false;

    public List<myIntArr> validTilePlacement = new Vector<myIntArr>();
    public List<myIntArr> buildSettlement = new Vector<myIntArr>();
    public List<myIntArr> expandSettlement = new Vector<myIntArr>();
    public List<myIntArr> buildTotoro= new Vector<myIntArr>();
    public List<myIntArr> buildTiger = new Vector<myIntArr>();

    public MoveLogger moveLogger;
    private HashMap<GameState, Moves> movesToLog = new HashMap<GameState, Moves>(50);



    public AI() {

    }

    public AI(MoveLogger ml) {
        this.smart = true;
        moveLogger = ml;
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
        for (Hex hex : game.gameState.board.playedHexes) {
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
                    if (game.gameState.board.isPlacementValid(temp, hex, j)) {
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
        for (Hex hex : game.gameState.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if (game.build.isBuildSettlementValid(hex, game.gameState.player1.getMeepleCount())) {
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
            if (!game.build.isExpandSettlementValid(game.gameState.board, m.array[0], Terrain.values()[m.array[2]], game.gameState.player1)) {
                expandToRemove.add(m);
            }
        }
        //for(myIntArr move : expandToRemove){
        expandSettlement.clear();
        assert expandSettlement.size()==0;
        //}


        for (Settlement s : game.gameState.board.settlementList) {
            Hex hex = s.hexesInSettlement.get(0);
            if(hex.getOwner() == game.gameState.player1){

                for(int i = 0; i < 4; i++) {
                    int[] currentArr = new int[3];

                    currentArr[0] = s.getSettlementID();//hex.indexX;
                    currentArr[1] = -1;//hex.indexY;
                    currentArr[2] = i;
                    myIntArr tempArr = new myIntArr(currentArr);

                    if (game.build.isExpandSettlementValid(game.gameState.board, s.getSettlementID(), Terrain.values()[i], game.gameState.player1)) {
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
        for (Hex hex : game.gameState.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if(game.build.isBuildTotoroSanctuaryValid(game.gameState.board, hex, game.gameState.player1)) {
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
        for (Hex hex : game.gameState.board.playedHexes) {
            int[] currentArr = new int[2];
            currentArr[0] = hex.indexX;
            currentArr[1] = hex.indexY;
            myIntArr tempArr = new myIntArr(currentArr);

            if(game.build.isBuildTigerSanctuaryValid(game.gameState.board, hex, game.gameState.player1)) {
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
        buildSettlement.clear();//removeAll(buildSettlement);
        expandSettlement.clear();//.removeAll(expandSettlement);// = new Vector<int[]>();
        buildTotoro.clear();//.removeAll(buildTotoro);//= new Vector<int[]>();
        buildTiger.clear();//.removeAll(buildTiger);// = new Vector<int[]>();
        validTilePlacement.clear();//.removeAll(validTilePlacement);// = new Vector<int[]>();
    }

    public String playMove(Tile tile, int time) {
        int tilePlay[] = playTile(tile);
        resetLists();
        updateLists();

        int buildPlay[] = playBuild();
        Terrain t = null;
        if(buildPlay[3] != -1){
            t = Terrain.values()[buildPlay[3]];
        }
        resetLists();
        updateLists();

        String temp = tile.getTileTypeString() + " " + tilePlay[0] + " " + tilePlay[1] + " " + tilePlay[2] + " " + buildPlay[2] + " " + buildPlay[0] + " " + buildPlay[1];

        if(buildPlay[2] == 1){
            temp+= " " + t.getTerrainText();
        }
        if(buildPlay[2] == -1){
            System.out.println("Meeple Count: " + game.gameState.player1.getMeepleCount());
        }

        return temp;//+ ;
    }

    private int[] chooseBuild(){
        int buildArr[] = new int[4];


        return buildArr;
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
        int flip = rand.nextInt(3);
        n = -1;
        if(buildTiger.size() != 0){
            n = 3;
        }else if(buildTotoro.size() != 0){
                n = 2;
        } else if(expandSettlement.size() != 0 && (flip != 0 || game.gameState.player1.getTotoroCount() == 1 || game.gameState.player1.getTigerCount() ==0) && (game.gameState.player1.getMeepleCount() - game.gameState.player2.getMeepleCount() <= 4 || game.gameState.player1.getTigerCount() ==0)){
            n = 1;
        } else if(buildSettlement.size() != 0){
            n = 0;
        }
        //TODO: add checks for each case to choose a good valid build.
        switch (n){
            case 0:if(buildSettlement.size() > 0)
                         n = rand.nextInt(buildSettlement.size());
                    else{
                    System.out.println(buildSettlement.size());
                        break;}
                xbybbNum[0] = buildSettlement.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.gameState.board.rootHex.indexX;
                xbybbNum[1] = buildSettlement.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.gameState.board.rootHex.indexY;
                xbybbNum[2] = 0;
                game.playMove(0, buildSettlement.get(n).array[0], buildSettlement.get(n).array[1]);
                break;
            case 1: if(expandSettlement.size() > 0) {
                boolean condition = true;
                        int bestExpansionNumber = 0;
                        n = 0;
                        for (myIntArr mIA : expandSettlement) {
                            List<Hex> temp = game.build.BFSForTerrain(game.gameState.board, game.gameState.board.settlementList.get(mIA.array[0]).hexesInSettlement.get(0), Terrain.values()[mIA.array[2]]);
                            for(Hex h: temp){
                                if(!game.gameState.board.settlementList.get(mIA.array[0]).hexesInSettlement.contains(h) && h.getLevel()>=2){
                                    n = expandSettlement.indexOf(mIA);
                                    condition = false;
                                }
                            }
                            if(condition){
                            if (temp.size() - game.gameState.board.settlementList.get(mIA.array[0]).settlementSize() > bestExpansionNumber
                                    && !game.gameState.board.settlementList.get(mIA.array[0]).hasTotoro()
                                    && temp.size() < 6 ) {
                                bestExpansionNumber = temp.size() - game.gameState.board.settlementList.get(mIA.array[0]).settlementSize();
                                n = expandSettlement.indexOf(mIA);
                            }
                            }
                            else
                                break;
                        }
                    }//n = rand.nextInt(expandSettlement.size());
                    else
                        break;
                xbybbNum[0] = game.gameState.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexX;
                xbybbNum[0] = xbybbNum[0] - game.gameState.board.rootHex.indexX;
                xbybbNum[1] = game.gameState.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexY;
                xbybbNum[1] = xbybbNum[1] - game.gameState.board.rootHex.indexY;
                xbybbNum[2] = 1;
                xbybbNum[3] = expandSettlement.get(n).array[2];
                t = Terrain.values()[expandSettlement.get(n).array[2]];
                game.playMove(1, game.gameState.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexX, game.gameState.board.settlementList.get(expandSettlement.get(n).array[0]).hexesInSettlement.get(0).indexY, t);
                break;
            case 2: if(buildTotoro.size() > 0)
                n = rand.nextInt(buildTotoro.size());
            else
                break;
                xbybbNum[0] = buildTotoro.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.gameState.board.rootHex.indexX;
                xbybbNum[1] = buildTotoro.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.gameState.board.rootHex.indexY;
                xbybbNum[2] = 2;
                game.playMove(2, buildTotoro.get(n).array[0], buildTotoro.get(n).array[1]);
                break;
            case 3:if(buildTiger.size() > 0)
                n = rand.nextInt(buildTiger.size());
            else
                break;
                xbybbNum[0] = buildTiger.get(n).array[0];
                xbybbNum[0] = xbybbNum[0] - game.gameState.board.rootHex.indexX;
                xbybbNum[1] = buildTiger.get(n).array[1];
                xbybbNum[1] = xbybbNum[1] - game.gameState.board.rootHex.indexY;
                xbybbNum[2] = 3;
                game.playMove(3, buildTiger.get(n).array[0], buildTiger.get(n).array[1]);
                break;
        }
        return xbybbNum;
    }

    private int[] playTile(Tile tile) {
        Random rand = new Random();
        int[] p = new int[4];
        int n = -1;
        if(smart && moveLogger.getBestMove(game.gameState, false) != null){
            Moves m = moveLogger.getBestMove(game.gameState, false);
            movesToLog.put(game.gameState, m);

            p[0] = m.move.array[0];
            p[0] = p[0] - game.gameState.board.rootHex.indexX;
            p[1] = m.move.array[1];
            p[1] = p[1] - game.gameState.board.rootHex.indexY;
            p[3] = m.move.array[3];
            if (p[3] == 1) {
                p[0] = game.gameState.board.getAdjHexIndexX(game.gameState.board.hexArr[ m.move.array[0]][ m.move.array[1]], (3 +  m.move.array[2]) % 6);
                p[0] = p[0] - game.gameState.board.rootHex.indexX;
                p[1] = game.gameState.board.getAdjHexIndexY(game.gameState.board.hexArr[ m.move.array[0]][ m.move.array[1]], (3 +  m.move.array[2]) % 6);
                p[1] = p[1] - game.gameState.board.rootHex.indexY;
            } else if (p[3] == 2) {
                p[0] = game.gameState.board.getAdjHexIndexX(game.gameState.board.hexArr[ m.move.array[0]][ m.move.array[1]], (4 +  m.move.array[2]) % 6);
                p[0] = p[0] - game.gameState.board.rootHex.indexX;
                p[1] = game.gameState.board.getAdjHexIndexY(game.gameState.board.hexArr[ m.move.array[0]][ m.move.array[1]], (4 +  m.move.array[2]) % 6);
                p[1] = p[1] - game.gameState.board.rootHex.indexY;
            }


            p[2] =  m.move.array[2] + 1;
            tile.setOrientation(p[2] - 1);

            game.gameState.board.placeTile(tile, game.gameState.board.hexArr[ m.move.array[0]][ m.move.array[1]],  m.move.array[3]);
            return p;
        }
        else {
            //rand.nextInt(validTilePlacement.size());
            for (int i = 0; i < validTilePlacement.size(); i++) {
                tile.setOrientation(validTilePlacement.get(i).array[2]);
                if (game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]].getLevel() > 1) {
                    n = i;
                    break;
                } else if (game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]].getLevel() > 0 && (!game.gameState.board.isTileNotNukingSettlementThatCanHaveTotoro(tile, game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]], validTilePlacement.get(i).array[3], game.gameState.player1) && game.gameState.board.isTileNukingSettlementThatCanHaveTotoroUnfriendly(tile, game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]], validTilePlacement.get(i).array[3], game.gameState.player2))) {
                    n = i;
                    break;
                }
                /*if (game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]].getLevel() > 1) {
                   if ((game.gameState.board.isTileNukingSettlementHigherThanLevel2(tile, game.gameState.board.hexArr[validTilePlacement.get(i).array[0]][validTilePlacement.get(i).array[1]], validTilePlacement.get(i).array[3], game.gameState.player2)))
                    {n = i;
                    break;
                    }
                }*/
            }
            if (n == -1)
                n = rand.nextInt(validTilePlacement.size());
            p[0] = validTilePlacement.get(n).array[0];
            p[1] = validTilePlacement.get(n).array[1];
            p[3] = validTilePlacement.get(n).array[3];
            p[2] = validTilePlacement.get(n).array[2] + 1;
            tile.setOrientation(p[2] - 1);
//check if need to mod
            int[] tArr = new int[4];
            tArr[0] = p[0];
            tArr[1] = p[1];
            tArr[2] = p[2];
            tArr[3] = p[3];
            Moves m = new Moves();
            m.move = new myIntArr(tArr);
            m.move.array[2] += -1;
            m.typeOfMove = 0;
            movesToLog.put(game.gameState, m);

            p[0] = p[0] - game.gameState.board.rootHex.indexX;
            p[1] = p[1] - game.gameState.board.rootHex.indexY;
            if (p[3] == 1) {
                p[0] = game.gameState.board.getAdjHexIndexX(game.gameState.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (3 + validTilePlacement.get(n).array[2]) % 6);
                p[0] = p[0] - game.gameState.board.rootHex.indexX;
                p[1] = game.gameState.board.getAdjHexIndexY(game.gameState.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (3 + validTilePlacement.get(n).array[2]) % 6);
                p[1] = p[1] - game.gameState.board.rootHex.indexY;
            } else if (p[3] == 2) {
                p[0] = game.gameState.board.getAdjHexIndexX(game.gameState.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (4 + validTilePlacement.get(n).array[2]) % 6);
                p[0] = p[0] - game.gameState.board.rootHex.indexX;
                p[1] = game.gameState.board.getAdjHexIndexY(game.gameState.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], (4 + validTilePlacement.get(n).array[2]) % 6);
                p[1] = p[1] - game.gameState.board.rootHex.indexY;
            }




            game.gameState.board.placeTile(tile, game.gameState.board.hexArr[validTilePlacement.get(n).array[0]][validTilePlacement.get(n).array[1]], validTilePlacement.get(n).array[3]);
            return p;
        }
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb) {
        x = x + game.gameState.board.rootHex.indexX;
        y = y + game.gameState.board.rootHex.indexY;
        xb = xb + game.gameState.board.rootHex.indexX;
        yb = yb + game.gameState.board.rootHex.indexY;

        game.gameState.board.placeTile(t, x, y, connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb);
        resetLists();
        updateLists();
    }

    public void updateEnemyMove(Tile t, int x, int y, int connectingHex, int buildOptionNumber, int xb, int yb, Terrain terrainType) {
        x = x + game.gameState.board.rootHex.indexX;
        y = y + game.gameState.board.rootHex.indexY;
        xb = xb + game.gameState.board.rootHex.indexX;
        yb = yb + game.gameState.board.rootHex.indexY;
        game.gameState.board.placeTile(t, x,y, connectingHex);
        game.setBuildOption(buildOptionNumber, xb, yb, terrainType);
        resetLists();
        updateLists();
    }

    public void endGame(boolean win) {

        for(GameState gs: movesToLog.keySet()){
            moveLogger.recordMove(gs, movesToLog.get(gs),win);
        }
        gameEnd = true;
    }

}