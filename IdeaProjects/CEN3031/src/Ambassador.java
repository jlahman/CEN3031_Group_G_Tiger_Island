import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Justin Lahman on 4/6/17.
 */
public class Ambassador {
///*
    private String CRLF;
    private AI gameController1;
    private AI gameController2;

    private String hostName;
    private int portNumber;
    private Socket mSocket;
    private PrintWriter out;
    private BufferedReader in;

    private int moveNum1 = 1;
    private int moveNum2 = 1;

    private boolean done;
    private String pid;
    private String cid;
    private String rid;
    private String username;
    private String password;

    public Ambassador(){

    }

    public Ambassador(String hostname, int portnumber, String username, String password){
        this.hostName = hostname;
        this.portNumber = portnumber;
        this.username = username;
        this.password = password;
         CRLF = "\r\n";
    }

    public void init(){
        try{
            mSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(mSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        } catch (Exception e){
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    public void init(String hostname, int portnumber, String username, String password){
        this.hostName = hostname;
        this.portNumber = portnumber;
        this.username = username;
        this.password = password;

        try{
            mSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(mSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        } catch (Exception e){
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    public void listen(){
        String message;
        try{
            while(!done){
                message = in.readLine();

                actOnMessage(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try{
            out.println(message);
        } catch (Exception e) {

        }
    }

    private void actOnMessage(String sentMessage){
        String[] message = sentMessage.split("\\s+");

        String reply = "";
        if(message[0].equals("WELCOME")){
            reply = "ENTER THUNDERDOME " + password + CRLF;
        }
        else if(message[0].equals("TWO")){
            reply = "I AM " + username + " " + username + CRLF;
        }
        else if(message[0].equals("WAIT") && message[3].equals("TOURNAMENT")){
            pid = message[message.length-1];
        }
        else if(message[0].equals("NEW") && message[1].equals("CHALLENGE")){
            cid = message[2];
        }
        else if(message[0].equals("BEGIN")){
            rid = message[2];
        }
        else if(message[0].equals("NEW") && message[1].equals("MATCH")){
            gameController1.startNewGame();
            gameController2.startNewGame();
        }
        else if(message[0].equals("MAKE")){
            reply = actionMakeMove(message);
        }
        else if(message[0].equals("GAME")){
            actionGameUpdate(message);
        }
        else if(message[0].equals("END")){
            if(message[2].equals("ROUND")){
                //no action required
            }
            else if(message[2].equals("CHALLENGES")){
                //no action required
            }
        }
        else if(message[0].equals("WAIT") && message[3] == "NEXT"){
            //no action required
        }
        else if(message[0].equals("THANK")){
            done = true;
        }
        if(!reply.equals("")){
            sendMessage(reply);
        }

    }

    private void actionGameUpdate(String[] message) {
        if (message[6].equals("PLACED")) {
            if (!pid.equals(message[5])) {
                String gid = message[1];
                Tile tile = getTileFromString(message[7]);
                int x = getCoordX(toInt(message[9]), toInt(message[10]), toInt(message[11]));
                int y = getCoordY(toInt(message[9]), toInt(message[10]), toInt(message[11]));
                int orientation = toInt(message[12]);

                int xb = getCoordX(toInt(message[16]), toInt(message[17]), toInt(message[18]));
                int yb = getCoordY(toInt(message[16]), toInt(message[17]), toInt(message[18]));

                int bo = -1;

                if (message[13].equals("FOUNDED")) {
                    bo = 0;
                } else if (message[14].equals("TOTORO")) {
                    bo = 2;
                } else if (message[14].equals("TIGER")) {
                    bo = 3;
                }

                if (gid.equals(gameController1.getGameID())) {
                    moveNum1++;
                    if (message[13].equals("EXPANDED")) {
                        Terrain t = null;
                        switch (message[19].charAt(0)) {
                            case 'J':
                                t = Terrain.JUNGLE;
                                break;
                            case 'L':
                                t = Terrain.LAKE;
                                break;
                            case 'G':
                                t = Terrain.GRASSLANDS;
                                break;
                            case 'R':
                                t = Terrain.ROCKY;
                                break;
                        }
                        gameController1.updateEnemyMove(tile, x, y, orientation, 1, xb, yb, t);//build params)
                    }
                    gameController1.updateEnemyMove(tile, x, y, orientation, bo, xb, yb);

                } else {
                    if (gid.equals(gameController2.getGameID())) {
                        moveNum2++;
                        if (message[13].equals("EXPANDED")) {
                            Terrain t = null;
                            switch (message[19].charAt(0)) {
                                case 'J':
                                    t = Terrain.JUNGLE;
                                    break;
                                case 'L':
                                    t = Terrain.LAKE;
                                    break;
                                case 'G':
                                    t = Terrain.GRASSLANDS;
                                    break;
                                case 'R':
                                    t = Terrain.ROCKY;
                                    break;
                            }
                            gameController2.updateEnemyMove(tile, x, y, orientation, 1, xb, yb, t);//build params)
                        }
                        gameController2.updateEnemyMove(tile, x, y, orientation, bo, xb, yb);
                    }
                }
            } else if (message[2].equals("OVER")) {
                String gid = message[1];
                if (gid.equals(gameController1.getGameID())) {
                    gameController1.endGame();
                } else {
                    gameController2.endGame();
                }
            } else {
                String gid = message[1];
                if (gid.equals(gameController1.getGameID())) {
                    gameController1.endGame();
                } else {
                    gameController2.endGame();
                }
            }
        }
    }

    private Tile getTileFromString(String tile){
        String[] tileArr = tile.split("\\+");
        String temp = "";
        switch (tileArr[0].charAt(0)){
            case 'J': temp = "Jungle-";
                break;
            case 'L': temp = "Lake-";
                break;
            case 'G': temp = "Grasslands-";
                break;
            case 'R': temp = "Rockey-";
                break;
        }
        switch (tileArr[1].charAt(0)){
            case 'J': temp += "Jungle";
                break;
            case 'L': temp += "Lake";
                break;
            case 'G': temp += "Grasslands";
                break;
            case 'R': temp += "Rockey";
                break;
        }
        return new Tile(TileType.valueOf(temp));
    }

    private int toInt(String s){
        return Integer.parseInt(s);
    }

    private int getCoordX(int x, int y, int z){
        return x;
    }

    private int getCoordY(int x, int y, int z){
        return z;
    }

    private String actionMakeMove(String[] message) {
        String reply = "";
        Tile tile = getTileFromString(message[message.length - 1]);
        String gid = message[5];
        String prefix = "";
        prefix = "GAME " + gid + " " + "MOVE" + " ";
        int time = toInt(message[7]) * 1000;
        if(gameController1.getGameID() == null){
            gameController1.setGameID(gid);
            reply = prefix + moveNum1 + " ";
            reply += getMoveAsString(gameController1.playMove(tile, time));
        }
        else if (gameController2.getGameID() == null) {
            gameController2.setGameID(gid);
            reply = prefix + moveNum2 + " ";;
            reply += getMoveAsString(gameController2.playMove(tile, time));
        }
        else if(gid.equals(gameController1.getGameID())){
            reply = prefix + moveNum1 + " ";;
            reply += getMoveAsString(gameController1.playMove(tile, time));
        }
        else if(gid.equals(gameController2.getGameID())){
            reply = prefix + moveNum2 + " ";;
            reply += getMoveAsString(gameController2.playMove(tile, time));
        }

        if(gid.equals(gameController1.getGameID()))
            moveNum1++;
        else if(gid.equals(gameController2.getGameID()))
            moveNum2++;
        return reply;
    }

    private String getMoveAsString(String move){
        String[] temp = move.split("\\s+");
        //reveiw
        String tt = "";
        switch(TileType.valueOf(temp[0])){
            case JJ: tt = "JUNGLE+JUNGLE";
            break;
            case JG: tt = "JUNGLE+GRASS";
            break;
            case JL: tt = "JUNGLE+LAKE";
            break;
            case JR: tt = "JUNGLE+ROCK";
            break;
            case GJ: tt = "GRASS+JUNGLE";
                break;
            case GG: tt = "GRASS+GRASS";
                break;
            case GL: tt = "GRASS+LAKE";
                break;
            case GR: tt = "GRASS+ROCK";
                break;
            case LJ: tt = "LAKE+JUNGLE";
                break;
            case LG: tt = "LAKE+GRASS";
                break;
            case LL: tt = "LAKE+LAKE";
                break;
            case LR: tt = "LAKE+ROCK";
                break;
            case RJ: tt = "ROCK+JUNGLE";
                break;
            case RG: tt = "ROCK+GRASS";
                break;
            case RL: tt = "ROCK+LAKE";
                break;
            case RR: tt = "ROCK+ROCK";
                break;
        }

        String buildText = "";
        switch(toInt(temp[4])){
            case 0: buildText = "FOUND SETTLEMENT AT ";
            break;
            case 1: buildText = "EXPAND SETTLEMENT AT ";
            break;
            case 2: buildText = "BUILD TOTORO SANCTUARY AT ";
            break;
            case 3: buildText = "BUILD TIGER PLAYGROUND AT ";
            break;
        }
        String bleh = "PLACE " + tt + " " + to3D(toInt(temp[1]), toInt(temp[2])) + " " + temp[3] + " " + buildText + to3D(toInt(temp[1]), toInt(temp[2]));
        if(toInt(temp[4]) == 1){
            bleh += " " + temp[7];
        }
        return bleh += CRLF;
    }

    private String to3D(int x, int z){
        int y = -1*(x+z);
        String t = x + " " + y + " " + z;
        return t;
    }

    public void setAI1(AI gameController1) {
        this.gameController1 = gameController1;
    }
    public AI getAI1() {
        return gameController1;
    }

    public void setAI2(AI gameController2) {
        this.gameController2 = gameController2;
    }
    public AI getAI2() {
        return gameController2;
    }
    //*/
}
