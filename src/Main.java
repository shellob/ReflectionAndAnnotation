public class Main {
    public static void main(String[] args) {
        Injector injector = new Injector("test.properties");
        SomeBean sb = injector.inject(new SomeBean());
        sb.foo();
    }
}
