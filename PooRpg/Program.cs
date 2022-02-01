using static System.Console;

using PooRpg.Entities;

namespace PooRpg
{

    class Program
    {
        static void Main(string[] args)
        {
            Hero arus = new Knight("Arus", 30);
            Hero topapa = new Wizard("Topapa", 40);

            WriteLine(arus);
            WriteLine(topapa);

            WriteLine("\nAttack");
            
            WriteLine("\nAttack");

            WriteLine($" {arus.Name} atacou {topapa.Name} com {arus.Attack(topapa)} de dano.");
            WriteLine($" {topapa.Name} atacou {arus.Name} com {topapa.Attack(arus)} de dano.");
            WriteLine($" {topapa.Name} atacou {arus.Name} com {topapa.Attack(arus, 150)} de dano.");
            WriteLine($" {arus.Name} atacou {topapa.Name} com {arus.Attack(topapa, 120)} de dano.");
            WriteLine($" {topapa.Name} atacou {arus.Name} com {topapa.Attack(arus, 70)} de dano.");
            WriteLine($" {arus.Name} atacou {topapa.Name} com {arus.Attack(topapa, 80)} de dano.");


            WriteLine(arus);
            WriteLine(topapa);

            WriteLine("\nCura");
            arus.RegenerateLife();
            topapa.RegenerateLife();
            WriteLine(arus);
            WriteLine(topapa);

        }
        
    }

}
