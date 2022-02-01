
namespace PooRpg.Entities;

public enum HeroType
{
    KNIGHT = 2,
    WIZARD = 4
}

public abstract class Hero
{
    public string? Name {get; set;}
    public HeroType Type {get; set;}
    public int Life {get; set;}
    public int Level {get; set;}


    public Hero(string name, HeroType type, int level)
    {
        Name = name;
        Type = type;
        Level = level;
        Life = 100;
    }

    public Hero()
    {

    }

    public override string ToString()
    {
        return $" Nome: {Name} Classe: {Type} Nível: {Level} Vida: {Life} ";
    }

    public abstract int Attack(Hero oponent, int bonus = 100);
        
    public virtual void RegenerateLife()
    {
        Life += Life + 25 <= 100 ? 25 : 100-Life;
    }
}
