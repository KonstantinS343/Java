package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PushRequest {
    public void push(Request request, DataInputStream input, DataOutputStream output) throws IOException {
        String message ;
        String pathTojson = "src/client/data/";

        if(request.getInp() != null){

            Path path = new File(pathTojson+request.getInp()).toPath();
            try{
                message = String.join("\n", Files.readAllLines(path));
                System.out.println("Sent: "+message);
                output.writeUTF(message);
            }catch (IOException e){
                System.out.println("ERROR!");
            }
        }else{
            message = request.toJson();
            System.out.println("Sent: "+message);
            output.writeUTF(message);
        }

        String FUllOut = input.readUTF();
        System.out.println("Received: " + FUllOut);
    }
}
