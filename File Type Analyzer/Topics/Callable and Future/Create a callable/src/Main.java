import java.util.Scanner;
import java.util.concurrent.Callable;

class CallableUtil {
    public static Callable<String> getCallable() {
        Scanner input = new Scanner(System.in);
        Callable<String> str = () -> input.nextLine();
        return str;
    }
}