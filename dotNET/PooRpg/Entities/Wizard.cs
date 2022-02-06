namespace PooRpg.Entities;

public class Wizard: Hero 
{
    public Wizard(string name, int level): base(name, HeroType.WIZARD, level)
    {

    }
    
    public Wizard(): base()
    {
        Type = HeroType.WIZARD;
    }

    public override int Attack(Hero oponent, int bonus = 100)
    {
        var damage = Convert.ToInt32((Level*0.3)*(bonus/100D));
        oponent.Life -= damage;

        return damage;
    }

    public override void RegenerateLife()
    {
        Life += Life + 35 <= 100 ? 35 : 100-Life;
    }
}
