import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    
    char co;
    int r = 0, s = 0, c = 0, q;
    int a = sc.nextInt();
      
    for(int i = 0; i < a; i++)
    {
      q = sc.nextInt();
        co = sc.next().charAt(0);   

      if(co == 'R'){
        r += q;
      } else if(co == 'C'){ 
        c += q;
      } else{ 
        s += q;
      }
    }
    
    double t = r + s + c;
    System.out.printf("Total: %.0f cobaias\n", t);
    System.out.println("Total de coelhos: "+c);
    System.out.println("Total de ratos: " + r);
    System.out.println("Total de sapos: " + s);

    System.out.printf("Percentual de coelhos: %.2f %%\n", (c/t)*100);
    System.out.printf("Percentual de ratos: %.2f %%\n", (r/t)*100);
    System.out.printf("Percentual de sapos: %.2f %%\n", (s/t)*100);
    }
}