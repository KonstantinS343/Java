import java.util.HashMap;
import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner input = new Scanner(System.in);
        int begin = input.nextInt();
        int end = input.nextInt();

        int size = input.nextInt();

        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(input.nextInt(), input.next());
        }

        for (var i : map.entrySet()) {
            if (i.getKey() >= begin && i.getKey() <= end) {
                System.out.println(i.getKey() + " " + i.getValue());
            }
        }
    }
}