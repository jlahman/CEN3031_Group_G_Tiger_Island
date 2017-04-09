import java.util.Scanner;

/**
 * Created by madashi on 4/9/17.
 */
public class Master {
    private Ambassador amb;
    private String[] Cargs;

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
       Master m = new Master();
       m.play();
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

    }

    public void play(){
        int i  = Integer.parseInt(Cargs[1]);
        //if (i != 4444)
            //System.out.println(i);
        amb.init(Cargs[0], i, Cargs[2], Cargs[3]);
        AI game1AI = new AI();
        AI game2AI = new AI();
        amb.setAI1(game1AI);
        amb.setAI2(game2AI);
        amb.init();

        amb.listen();
    }
}
