namespace PooRpg.Entities;

public class Knight: Hero 
{
    public Knight(string name, int level): base(name, HeroType.KNIGHT, level)
    {

    }

    public Knight(): base()
    {
        Type = HeroType.KNIGHT;
    }

    public override int Attack(Hero oponent, int bonus = 100)
    {
        var damage = Convert.ToInt32((Level*0.5)*(bonus/100D));
        oponent.Life -= damage;

        return damage;
    }
}
