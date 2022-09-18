import java.util.Scanner;

class StringProcessor extends Thread {

    public static void main(String[] args) {
        Thread t = new StringProcessor();
        t.start();
    }
    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        String line = "";
       while(!line.equals("FINISHED")){
           line = scanner.nextLine();
           if(line.equals(line.toUpperCase())){
               line = "FINISHED";
               System.out.println(line);
           }else {
               line = line.toUpperCase();
               System.out.println(line);
           }
       }
        // implement this method
    }

}