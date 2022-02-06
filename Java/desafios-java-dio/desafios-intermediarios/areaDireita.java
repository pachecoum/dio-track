import java.io.IOException;
import java.util.Scanner;

public class  Main{
	
    public static void main(String[] args) throws IOException {
        Scanner leitor = new Scanner(System.in);
        double soma = 0;
        char O = leitor.next().toUpperCase().charAt(0);
        double[][] M = new double[12][12];
        for (int i = 0; i < 12; i++) {
        	for (int j = 0; j < 12; j++) {
        		M[i][j] = leitor.nextDouble();
        	}
        }
        
        for (int x = 0; x < 12; x++) {
        	for (int y = 0; y < 12; y++) {
        		if ( (y == 7 && x >= 5 && x <= 6) ||  
        		(y == 8 && x >= 4 && x <= 7) ||  
        		(y == 9 && x >= 3 && x <= 8) ||  
        		(y == 10 && x >= 2 && x <= 9) ||  
        		(y == 11 && x >= 1 && x <= 10) 
        		) soma += M[x][y];
        	}
        }

        if (O == 'M') soma /= 30;
    	System.out.println(String.format("%.1f", soma));
    }
	
}