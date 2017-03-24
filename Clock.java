public class Clock{
    private static boolean turnCounter= true;

    public void increment(){
        this.turnCounter=!(this.turnCounter);
    }

    public void displayCounter(){
        if(this.turnCounter)
            System.out.println("White");
        else if(!(this.turnCounter))
            System.out.println("Black");
    }
}
