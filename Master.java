import java.util.Scanner;

/**
 * Created by Justin Lahman on 4/9/17.
 */
/*DO not use the smart option, moves would have to be put into catagories according to their effects
*   then the AI would have to choose a move with a similar effect. Also, Game state would have to
*   change its equals to be "fuzzier" aka compare similar gamestate and hash them to the same
*   value in the Move Logger. -Justin*/
public class Master {
    private Ambassador amb;
    private String[] Cargs;
    private MoveLogger ml;

    public static void main(String[] args) {
       /* Scanner input = new Scanner(System.in);
        String temp = input.nextLine();
        String[] Cargs = temp.split("\\s+");
        Ambassador amb = new Ambassador();
        amb.init(Cargs[0], Integer.getInteger(Cargs[1]), Cargs[2], Cargs[3]);
        AI game1AI = new AI();
        AI game2AI = new AI();
        amb.setAI1(game1AI);
        amb.setAI2(game2AI);
        amb.init();
        amb.listen();*/
       final Master m = new Master();
       Thread listener = new Thread(){
           public void run(){
               while(!m.amb.done){
                m.play();
           }
           }
       };

       Thread actor = new Thread(){
           public void run(){
             while(!m.amb.done){
              m.act();
           }
           }
       };

       listener.start();
       actor.start();
       //m.play();
        /*Thread listener = new Thread(){
            public void run(){
                ambassador.listen();
            }
        };

        listener.start();*/

    }

    public Master(){
        Scanner input = new Scanner(System.in);
        String temp = input.nextLine();
        Cargs = temp.split("\\s+");
        amb = new Ambassador();
        int i  = Integer.parseInt(Cargs[1]);
        //if (i != 4444)
        //System.out.println(i);
        amb.init(Cargs[0], i, Cargs[2], Cargs[3], Cargs[4]);
        AI game1AI ;
        AI game2AI ;
        if(Cargs.length == 6 && Cargs[5].equals("smart")) {
            ml = new MoveLogger();
            game1AI = new AI(ml);
             game2AI = new AI(ml);
        }
        else{
             game1AI = new AI();
             game2AI = new AI();
        }
        amb.setAI1(game1AI);
        amb.setAI2(game2AI);
        amb.init();
    }

    public void play(){

       // while(!amb.done) {
            amb.listen();
        //}
    }

    public void act(){
        //while(!amb.done) {
            amb.runNextMessage();
        //}
    }
}
