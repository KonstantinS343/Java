package client;


import com.beust.jcommander.JCommander;
import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException {

        Request request = new Request();

        JCommander.newBuilder()
                .addObject(request)
                .build()
                .parse(args);


        new Socket().SocketRun(request);
    }


}
