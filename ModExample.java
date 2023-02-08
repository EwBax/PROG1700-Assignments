public class ModExample {   
    public static void main(String []args){
	
		int input = 850375;
		int onesPlace = input%10;
		System.out.println("The one's place is:" + onesPlace);
		
		input = input/10;
		int tensPlace = input%10;
		
		input = input/10;
		int hundredsPlace = input%10;
		
		input = input/10;
		int thouPlace = input%10;
		
		input = input/10;
		int tenThouPlace = input%10;
		
		input = input/10;
		int hunThouPlace = input%10;
		
		int final answer = onesPlace * 1 + tensPlace * 10; //...
	}
}