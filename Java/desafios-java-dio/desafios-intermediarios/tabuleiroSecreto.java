import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        
        int n, q, op, v, x;
      
        Scanner sc = new Scanner(System.in);
        
        n = sc.nextInt();
        q = sc.nextInt();
        
        int[][] tabuleiro = new int[n][n];
        
        for(int i = 0; i < q; i++){
            op = sc.nextInt();

            switch(op)
            {
                case 1:
                    x = sc.nextInt();
                    v = sc.nextInt();
                    popule(tabuleiro, v, -1, x-1);
                    break;
                case 2:
                    x = sc.nextInt();
                    v = sc.nextInt();
                    popule(tabuleiro, v, x-1, -1);
                    break;
                case 3:
                    x = sc.nextInt();
                    System.out.println(MaisFrequente(tabuleiro, x-1, -1));
                    break;
                case 4:
                    x = sc.nextInt();
                    System.out.println(MaisFrequente(tabuleiro, -1, x-1));
                    break;
            }
        }
        
    }
    public static void popule(int[][] cl, int v, int l, int c)
    {
        for(int i = 0; i < cl.length; i++){
            if(l >= 0)
                cl[i][l] = v;
            else
                cl[c][i] = v;
        }
    }
    public static int MaisFrequente(int[][] cl, int l, int c)
    {
        int[] myHashMap = new int[51];

        int i, mf = (l >= 0) ? cl[l][0] : cl[0][c];
        if(l >= 0)
            for(i = 0; i < cl.length; i++){
                myHashMap[cl[l][i]]++;
                if(myHashMap[cl[l][i]] > myHashMap[mf] || (myHashMap[cl[l][i]] == myHashMap[mf] && cl[l][i] > mf))
                    mf = cl[l][i];
}
        else
            for(i = 0; i < cl.length; i++){
                myHashMap[cl[i][c]]++;
                if(myHashMap[cl[i][c]] > myHashMap[mf] || (myHashMap[cl[i][c]] == myHashMap[mf] && cl[i][c] > mf))
                    mf = cl[i][c];
}
        return mf;
    }
}