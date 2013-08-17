package generator.dialogue;

public class Driver {
	
	public static void main(String[] args){
		
		if (args.length == 0){
			new GUI(System.getProperty("user.home") + "/dialogue");
		}
		
	}
	
}
