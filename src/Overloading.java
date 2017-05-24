public class Overloading {
    private static void name(long a) {
        System.out.println("long");
    }

    private static void name(int a) {
        System.out.println("int");
    }

    public static void main(String[] args) {
        name(10L);
    }
}
