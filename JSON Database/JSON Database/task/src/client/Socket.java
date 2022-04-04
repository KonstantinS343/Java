package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;


public class Socket {
    public  void SocketRun(Request request) {
        String address = "127.0.0.1";
        int port = 23456;
        System.out.println("Client started!");

        try (java.net.Socket socket = new java.net.Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
                new PushRequest().push(request, input, output);

        }catch (IOException e){
            System.out.println("ERROR");
        }
    }
}
