import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by madashi on 4/6/17.
 */
public class Ambassador {
    ///*
    private static String CRLF = "\r\n";
    private AI gameController1;
    private AI gameController2;

    private String hostName;
    private int portNumber;
    private Socket mSocket;
    private PrintWriter out;
    private BufferedReader in;

    private boolean done;
    private String pid;
    private String cid;
    private String rid;
    private String username;
    private String password;

    public Ambassador(){

    }

    public Ambassador(String hostname, int portnumber){
        this.hostName = hostname;
        this.portNumber = portnumber;
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

    public void init(String hostname, int portnumber){
        this.hostName = hostname;
        this.portNumber = portnumber;

        try{
            mSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(mSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        } catch (Exception e){
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    //copied from another project for reference, not needed
    public void runLoop(){
        String fromServer, fromUser;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                fromServer = in.readLine();
                System.out.println("Server(from c): " + fromServer);
                if (fromServer == "quit")
                    break;

                fromUser = stdIn.readLine();

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch(Exception e){

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
            reply = "I AM " + username + " " + password + CRLF;
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
        if(message[6].equals("PLACED")){
            if(!pid.equals(message[5])){
                String gid = message[1];
                Tile tile = getTileFromString(message[7]);
                int x = getCoordX(toInt(message[9]), toInt(message[10], toInt(message[11])));
                int y = getCoordY(toInt(message[9]), toInt(message[10], toInt(message[11])));
                int orientation = toInt(message[12]);
                if(gid.equals(gameController1.getGID())){
                    gameController1.updateWithEnemyMove(tile, x, y, orientation, //build params)
                }
                else{
                    //controller2 same
                }
            }
        }
        else if(message[2].equals("OVER")){
            String gid = message[1];
            if(gid.equals(gameController1.getGID())){
                gameController1.endGame();
            }
            else{
                gameController2.endGame();
            }
        }
        else{
            String gid = message[1];
            if(gid.equals(gameController1.getGID())){
                gameController1.endGame();
            }
            else{
                gameController2.endGame();
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
        return 1;//;figure how to caste string to int.
    }

    private int getCoordX(int x, int y, int z){
        return x;
    }

    private int getCoordY(int x, int y, int z){
        return z;
    }

    private String actionMakeMove(String[] message) {
        String reply;
        Tile tile = getTileFromString(message[message.length - 1]);
        String gid = message[5];
        int time = toInt(message[7]) * 1000;
        if(gameController1.getGID() == null){
            gameController1.setGID(gid);
            reply = getMoveAsString(gameController1.playMove(tile, time));
        }
        else if (gameController2.getGID() == null) {
            gameController2.setGID(gid);
            reply = getMoveAsString(gameController2.playMove(tile, time));
        }
        else if(gid.equals(gameController1.getGID())){
            reply = getMoveAsString(gameController1.playMove(tile, time));
        }
        else{
            reply = getMoveAsString(gameController2.playMove(tile, time));
        }
        return reply;
    }

    private String getMoveAsString(String move){
        //get the move the AI wants to make, then format it correctly
    }
    //*/
}
