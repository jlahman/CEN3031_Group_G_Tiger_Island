public class Clock{
	private static int turnCounter= 0;

	public static void increment(){
		this.turnCounter++;
	}
	
	public static void displayCounter(){
		System.out.println(this.turnCounter);
	}
}
