
import java.util.Scanner;

public class Matrix_Multiplication {
	
	public static void main(String[] args) {
		Scanner inputScan = new Scanner(System.in); 
		int rowNum = inputScan.nextInt();
		int colNum = inputScan.nextInt();
		
		Matrix a = new Matrix(rowNum, colNum);
		
		Scanner inputElement = new Scanner(System.in);
		double Element;
		
		for(int i = 0 ; i<rowNum; i++) {
			for(int j = 0; j<colNum; j++) {
				Element = inputElement.nextDouble();
				a.add(i, j, Element);
			}
		}
			
		rowNum = inputScan.nextInt();
		colNum = inputScan.nextInt();
		
		Matrix b = new Matrix(rowNum, colNum);			
			for(int i = 0 ; i<rowNum; i++) {
				for(int j = 0; j<colNum; j++) {
					Element = inputElement.nextDouble();
					b.add(i, j, Element);
				}
			}
			
		if((a.getColNum() != (b.getRowNum()))){
			System.out.println("The matrices can't be multiplied with each other.");
		}
		a.multiply(b);
	}
	
}


