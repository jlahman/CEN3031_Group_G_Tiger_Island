public class Clock{
	private static boolean turnCounter= true;

	public static void increment(){
		this.turnCounter=!(this.turnCounter);
	}
	
	public static void displayCounter(){
		if(this.turnCounter)
			System.out.println("White");
		else if(!(this.turnCounter))
			System.out.println("Black");
	}
}
