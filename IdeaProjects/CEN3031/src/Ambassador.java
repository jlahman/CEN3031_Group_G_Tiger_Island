import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
/*
192.168.1.23 5276 TEAM_G PASS_G FunFunFun

*/
//10.192.246.253 11111 TEAM_G PASS_G Iwanttobelieve
//127.0.0.1 6969 A A heygang
 /**
 * Created by Justin Lahman on 4/6/17.
 */
public class Ambassador {
///*
    public Vector<String> messageQueue = new Vector<String>();
    private String CRLF= "";
    private AI gameController1;
    private AI gameController2;

    private String hostName;
    private int portNumber;
    private Socket mSocket;
    private PrintWriter outt;
    private BufferedReader in;

    public boolean done;
    private String pid;
    private String cid;
    private String rid;
    private String username;
    private String password;
    private String tPassword;

    private  long sTime;

    public Ambassador(){

    }

    public Ambassador(String hostname, int portnumber, String username, String password, String tpassword){
        this.hostName = hostname;
        this.portNumber = portnumber;
        this.username = username;
        this.password = password;
        this.tPassword = tpassword;
        // CRLF = "\r\n";
    }

    public void init(){
        try{
            mSocket = new Socket(hostName, portNumber);
            outt = new PrintWriter(mSocket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        } catch (Exception e){
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    public void init(String hostname, int portnumber, String username, String password, String tpassword){
        this.hostName = hostname;
        this.portNumber = portnumber;
        this.username = username;
        this.password = password;
        this.tPassword = tpassword;

        // CRLF = "\r\n";
        try{
            mSocket = new Socket(hostName, portNumber);
            outt = new PrintWriter(mSocket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        } catch (Exception e){
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    public void listen(){
       // synchronized (messageQueue) {
            String message;

            try {
                //while(!done){
                message = in.readLine();
                sTime = System.nanoTime();
                System.out.println("Server @ T= " + sTime + ": " + message);
                messageQueue.add(message);

                //actOnMessage(message);

                // }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }

    public void runNextMessage(){
        //synchronized(messageQueue){
            String message;

        try{
           // while(!done){
            if(messageQueue.size() != 0) {
                message = messageQueue.get(0);
                //sTime = System.nanoTime();
                //System.out.println("Server @ T= "+ sTime +": "+ message);
                messageQueue.remove(0);
                actOnMessage(message);
            }
                //actOnMessage(message);

           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
       // }
    }
//10.136.31.59 6969 G heygang
    public void sendMessage(String message){
        try{
            System.out.println("Client @ Tdelta = " + (sTime - System.nanoTime()) + ": " +message);
            outt.println(message);
            outt.flush();
        } catch (Exception e) {

        }
    }

    protected void actOnMessage(String sentMessage){
        if(sentMessage == null){
            done = true;
            return;
        }
        String[] message = sentMessage.split("\\s+");

        String reply = "";
        if(message[0].equals("WELCOME")){
            reply = "ENTER THUNDERDOME " + tPassword;
        }
        else if(message[0].equals("TWO")){
            reply = "I AM " + username + " " + password;
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
                done = true;
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
                int orientation = toInt(message[12]) -1;
                tile.setOrientation(orientation);
                int xb = 0;
                int yb = 0;

                int bo = -1;

                if (message[13].equals("FOUNDED")) {
                    bo = 0;
                     xb = getCoordX(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                     yb = getCoordY(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                } else if (message[14].equals("TOTORO")) {
                    bo = 2;
                     xb = getCoordX(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                     yb = getCoordY(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                } else if (message[14].equals("TIGER")) {
                    bo = 3;
                     xb = getCoordX(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                     yb = getCoordY(toInt(message[message.length - 3]), toInt(message[message.length - 2]), toInt(message[message.length - 1]));
                }else if (message[13].equals("EXPANDED")){
                    bo = 1;
                     xb = getCoordX(toInt(message[message.length - 4]), toInt(message[message.length - 3]), toInt(message[message.length - 2]));
                     yb = getCoordY(toInt(message[message.length - 4]), toInt(message[message.length - 3]), toInt(message[message.length - 2]));
                }

                if(gameController1.getGameID() == null){
                    gameController1.setGameID(gid);

                }
                else if (gameController2.getGameID() == null) {
                    gameController2.setGameID(gid);

                }
                if (gid.equals(gameController1.getGameID())) {
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
                        gameController1.updateEnemyMove(tile, x, y, 0, 1, xb, yb, t);//build params)
                    }else
                    gameController1.updateEnemyMove(tile, x, y, 0, bo, xb, yb);

                } else {
                    if (gid.equals(gameController2.getGameID())) {
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
                            gameController2.updateEnemyMove(tile, x, y, 0, 1, xb, yb, t);//build params)
                        }else
                        gameController2.updateEnemyMove(tile, x, y, 0, bo, xb, yb);
                    }
                }
            } else if (message[2].equals("OVER")) {
                String gid = message[1];
                if (gid.equals(gameController1.getGameID())) {
                   if(getWinnerPID(message).equals(pid))
                       gameController1.endGame(true);
                   else
                       gameController1.endGame(false);
                } else {
                    if(getWinnerPID(message).equals(pid))
                        gameController2.endGame(true);
                    else
                        gameController2.endGame(false);
                }
            } else if(message[6].equals("FORFEITED:") || message[6].equals("LOST:"))  {
                /*String gid = message[1];
                if (gid.equals(gameController1.getGameID())) {
                    gameController1.endGame();
                } else {
                    gameController2.endGame();
                }  */
            }
        }
    }

    private String getWinnerPID(String[] message){
        if(message[4].equals(pid)){
            if(message[5].equals("WIN") || (Integer.parseInt(message[5]) >= Integer.parseInt(message[8]))) {
                return pid;
            }
            else{
                return message[7];
            }
        }
        else if(message[7].equals(pid)){
            if(message[8].equals("WIN") || (Integer.parseInt(message[8]) >= Integer.parseInt(message[5]))) {
                return pid;
            }
            else{
                return message[4];
            }
        }

        return "";
    }

    private Tile getTileFromString(String tile){
        String[] tileArr = tile.split("\\+");
        String temp = "";
        switch (tileArr[0].charAt(0)){
            case 'J': temp = "J";
                break;
            case 'L': temp = "L";
                break;
            case 'G': temp = "G";
                break;
            case 'R': temp = "R";
                break;
        }
        switch (tileArr[1].charAt(0)){
            case 'J': temp += "J";
                break;
            case 'L': temp += "L";
                break;
            case 'G': temp += "G";
                break;
            case 'R': temp += "R";
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
        int time = 1500;//toInt(message[7]) * 1000;
        if(gameController1.getGameID() == null){
            gameController1.setGameID(gid);
            reply = prefix + message[10] + " ";
            reply += getMoveAsString(gameController1.playMove(tile, time));
        }
        else if (gameController2.getGameID() == null) {
            gameController2.setGameID(gid);
            reply = prefix + message[10] + " ";
            reply += getMoveAsString(gameController2.playMove(tile, time));
        }
        else if(gid.equals(gameController1.getGameID())){
            reply = prefix + message[10] + " ";
            reply += getMoveAsString(gameController1.playMove(tile, time));
        }
        else if(gid.equals(gameController2.getGameID())){
            reply = prefix + message[10] + " ";
            reply += getMoveAsString(gameController2.playMove(tile, time));
        }

        return reply;
    }

    private String getMoveAsString(String move){
        String[] temp = move.split("\\s+");
        //reveiw
        String[] tileArr = temp[0].split("-");
        String temp2 = "";
        switch (tileArr[0].charAt(0)){
            case 'J': temp2 = "J";
                break;
            case 'L': temp2 = "L";
                break;
            case 'G': temp2 = "G";
                break;
            case 'R': temp2 = "R";
                break;
        }
        switch (tileArr[1].charAt(0)){
            case 'J': temp2 += "J";
                break;
            case 'L': temp2 += "L";
                break;
            case 'G': temp2 += "G";
                break;
            case 'R': temp2 += "R";
                break;
        }
        String tt = "";
        switch(TileType.valueOf(temp2)){
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
        int i = toInt(temp[4]);
        if(i == 0){
            buildText = "FOUND SETTLEMENT AT ";
        }
        switch(i){
            case -1: buildText = "UNABLE TO BUILD";
            break;
            case 0: buildText = "FOUND SETTLEMENT AT ";
            break;
            case 1: buildText = "EXPAND SETTLEMENT AT ";
            break;
            case 2: buildText = "BUILD TOTORO SANCTUARY AT ";
            break;
            case 3: buildText = "BUILD TIGER PLAYGROUND AT ";
            break;
        }
        String bleh = "PLACE " + tt + " AT " + to3D(toInt(temp[1]), toInt(temp[2])) + " " + temp[3] + " " + buildText;
               if(i != -1) bleh +=  to3D(toInt(temp[5]), toInt(temp[6]));
        if(toInt(temp[4]) == 1){
            String daveTermTerrain = "";
            switch (temp[7].charAt(0)){
                case 'J': daveTermTerrain = "JUNGLE";
                    break;
                case 'L': daveTermTerrain = "LAKE";
                    break;
                case 'G': daveTermTerrain = "GRASS";
                    break;
                case 'R': daveTermTerrain = "ROCK";
                    break;
            }
            bleh += " " + daveTermTerrain;
        }
        return bleh;// += CRLF;
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
