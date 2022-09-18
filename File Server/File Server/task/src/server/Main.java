package server;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        HashMap<String, String> idMap = new HashMap<>();
        try{
        idMap = (HashMap<String, String>) loadIdMap();
        } catch (IOException | ClassNotFoundException ignored){
        }
        String address = "127.0.0.1";
        int port = 23456;
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));

        while(true) {
            Socket socket = server.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
            String request = "", response;
            String[] requestTokens;

            try {
                request = input.readUTF();
            }
            catch (SocketException e) {

            }

            requestTokens = request.split(" ", 3);

            if (requestTokens[0].equals("exit")) {
                output.writeUTF("Ending.");
                saveIdMap(idMap);
                input.close();
                output.close();
                socket.close();
                server.close();
                break;
            }

            switch(requestTokens[0]) {
                case "PUT":
                    response = addFile(requestTokens, input, idMap);
                    output.writeUTF(response);
                    break;
                case "GET":
                    response = getFile(requestTokens, output, idMap);
                    break;
                case "DELETE":
                    response = deleteFile(requestTokens, output, idMap);
                    break;
                default:
                    response = "null";
                    break;
            }
            try {
                output.writeUTF(response);
            }
            catch (SocketException e){

            }

        }
    }

    public static String addFile(String[] requestTokens, DataInputStream input, HashMap<String, String> idMap) throws IOException {
        String filename;
        String fileId = System.currentTimeMillis() + "";
        filename = requestTokens[1];
        String path = "./src/server/data/" + filename;
        File f = new File(path);
        String response = "";

        int size = input.readInt();
        byte[] fileBytes = new byte[size];
        input.readFully(fileBytes, 0, fileBytes.length);

        if(!f.exists() && !f.isDirectory()) {
            Files.write(Paths.get(path), fileBytes);
            response = "200 " + fileId;

            idMap.put(fileId, filename);
        }
        else {
            response = "403";
        }
        return response;
    }

    public static String getFile(String[] requestTokens, DataOutputStream output, HashMap<String, String> idMap) throws IOException {
        String filename, response;
        if (requestTokens[1].equals("BY_ID")){
            if (idMap.containsKey(requestTokens[2]))
                filename = idMap.get(requestTokens[2]);
            else {
                response = "404";
                output.writeUTF(response);
                return response;
            }
        }
        else{
            filename = requestTokens[2];
        }
        String path = "./src/server/data/" + filename;
        File f = new File(path);

        if(f.exists() && !f.isDirectory()) {
            response = "200";
            output.writeUTF(response);
            byte[] fileBytes = Files.readAllBytes(Paths.get(path));
            output.writeInt(fileBytes.length);
            output.write(fileBytes);
            output.flush();
        }
        else {
            response = "404";
            output.writeUTF(response);
        }
        return response;
    }

    public static String deleteFile(String[] requestTokens, DataOutputStream output, HashMap<String, String> idMap) throws IOException {
        String filename, fileId;
        String response = "";
        if (requestTokens[1].equals("BY_ID")){
            if (idMap.containsKey(requestTokens[2])) {
                filename = idMap.get(requestTokens[2]);
                fileId = requestTokens[2];
            }
            else {
                response = "404";
                output.writeUTF(response);
                return response;
            }

        }
        else{
            filename = requestTokens[2];
            fileId = findId(idMap, requestTokens[2]);
        }
        String path = "./src/server/data/" + filename;
        File f = new File(path);


        if (f.exists() && !f.isDirectory()) {
            f.delete();
            idMap.remove(fileId);
            response = "200";
        }
        else {
            response = "404";
        }
        output.writeUTF(response);
        return response;
    }

    public static void saveIdMap(Object obj) throws IOException{
        String filename = "./src/server/map.bin";
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public static Object loadIdMap() throws IOException, ClassNotFoundException {


        FileInputStream fis = new FileInputStream("./src/server/map.bin");
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static String findId(HashMap<String, String> idMap, String toFind){
        for (String id : idMap.keySet()){
            if (id.equals(toFind))
                return id;
        }
        return "";
    }

}