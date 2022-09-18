package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void ServerRun(){
        System.out.println("Server started!");
        String address = "127.0.0.1";
        int port = 23456;
        boolean exit = false;

        try ( ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {

            while (!exit) {

                Socket socket = server.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String InputJson = inp.readUTF();
                JsonObject map = JsonParser.parseString(InputJson).getAsJsonObject();

                new Command().CommandRun(output,map);

                if(map.get("type").getAsString().equals("exit")){
                    exit = true;
                    server.close();
                    socket.close();
                    inp.close();
                    output.close();
                }


            }
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }
}
