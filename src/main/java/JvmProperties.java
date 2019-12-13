public class JvmProperties {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.vm.specification.version"));
        System.out.println(System.getProperty("java.vm.specification.vendor"));
        System.out.println(System.getProperty("java.vm.specification.name"));
        System.out.println(System.getProperty("java.vm.version"));
        System.out.println(System.getProperty("java.vm.vendor"));
        System.out.println(System.getProperty("java.vm.name"));

        int[] ints = Integer.toString(6600).chars().map(c -> c - '0').toArray();
    }
}
