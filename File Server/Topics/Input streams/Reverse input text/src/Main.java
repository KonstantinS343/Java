import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        List<Character> input = new ArrayList<>();

        int number = reader.read();
        while (number != -1){
            input.add((char) number);
            number = reader.read();
        }
        Collections.reverse(input);
        input.forEach(x->System.out.print(x));
        reader.close();
    }
}