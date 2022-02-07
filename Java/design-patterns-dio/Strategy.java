
public class Strategy {
    public static void main(String[] args) {
        Duck duck = new Duck();

        duck.setFlyBehavior(new NormalFly());
        System.out.println(duck.fly());

        duck.setFlyBehavior(new JetFly());
        System.out.println(duck.fly());
    }
}

interface IFlyBehavior {
    String fly();
}

class NormalFly implements IFlyBehavior {
    public String fly() {
        return "The duck fly normally over lake.";
    }
}

class JetFly implements IFlyBehavior {
    public String fly() {
        return "The duck fly over lake  as a jet.";
    }
}

class Duck {

    private IFlyBehavior flyBehavior;

    public void setFlyBehavior(IFlyBehavior fb) {
        this.flyBehavior = fb;
    }

    public String fly() {
        return flyBehavior.fly();
    }

}
