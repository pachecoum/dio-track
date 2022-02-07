
public class Singleton {

    public static void main(String[] args) {

        SingletonHolded singleton = SingletonHolded.getInstance();

        System.out.println("Created instance of sigleton " + singleton);
        System.out.println("Try get new instance of sigleton " + SingletonHolded.getInstance());

    }
}



class SingletonLazy {
    private static SingletonLazy instance;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance() {
        if (instance == null)
            instance = new SingletonLazy();

        return instance;
    }
}

class SingletonHurried {
    public static SingletonHurried instance = new SingletonHurried();

    private SingletonHurried() {
    }

}

class SingletonHolded {
    private class SingletonHolder {
        public static SingletonHolded instace = new SingletonHolded();
    }

    private SingletonHolded() {
    }

    public static SingletonHolded getInstance() {
        return SingletonHolder.instace;
    }
}
