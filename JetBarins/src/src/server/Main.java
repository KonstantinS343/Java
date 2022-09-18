package src.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args)  {

        int port = 23456,ID=-1;
        Map<Integer,String> MyFiles = new HashMap<>();
        boolean exit = false;
        String name = "";
        AddNewFile AddToMyListOfFiles = new AddNewFile();
        DeleteFromFiles DeleteFromFile= new DeleteFromFiles();
        GetFromFiles GetFile = new GetFromFiles();


        System.out.println("Server started!");
        try(
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream inp = new DataInputStream(socket.getInputStream());
        ){
            while(!exit){
                String GetMessage = inp.readUTF();

                switch (GetMessage) {
                    case "2" :
                        name = inp.readUTF();
                        if(receivFile(name,inp) == true){
                            output.writeUTF("403");
                            break;
                        }
                        ID = GeneraionID(name);
                        MyFiles = AddToMyListOfFiles.AddToList(ID,name, MyFiles);
                        if(!AddToMyListOfFiles.CheckAddToList){
                            output.writeUTF("403");
                        }else{
                            output.writeUTF("200");
                            output.writeUTF(String.valueOf(ID));
                            break;
                        }
                    break;
                    case "1" :
                        GetMessage = inp.readUTF();
                        if(GetMessage.equals("1")){
                            name = inp.readUTF();
                            GetFile.GetFile(ID,name, MyFiles);
                        }else{
                            String stringID = inp.readUTF();
                            GetFile.GetFile(Integer.parseInt(stringID),name, MyFiles);
                            name = MyFiles.get(Integer.parseInt(stringID));
                        }

                        if(!GetFile.CheckAddToList){
                            output.writeUTF("404");
                        }else{
                            output.writeUTF("200");
                            output.writeUTF(name);
                            String FilePath = "A:\\hvhjgjjh\\src\\src\\server\\data\\"+name;
                            sendFile(FilePath,output);
                        }
                    break;
                    case "3" :
                        GetMessage = inp.readUTF();
                        if(GetMessage.equals("1")){
                            name = inp.readUTF();
                            DeleteFromFile.DeleteFile(ID,name, MyFiles);
                        }else{
                            String stringID = inp.readUTF();
                            name = MyFiles.get(Integer.parseInt(stringID));
                            DeleteFromFile.DeleteFile(Integer.parseInt(stringID),name, MyFiles);
                        }
                        if(!DeleteFromFile.CheckAddToList){
                            output.writeUTF("404");
                        }else{
                            File file = new File("A:\\hvhjgjjh\\src\\src\\server\\data\\" + name);
                            file.delete();
                            output.writeUTF("200");
                        }
                    break;
                    case "exit" :
                        exit = true;
                        socket.close();
                        server.close();
                    break;
                }
            }
        }catch (IOException e){
            System.out.println("Server close!");
        }

    }
    public static boolean receivFile(String name,DataInputStream inp) throws IOException {
        int bytes = 0;
        long size = inp.readLong();
        if(size == 0){
            File file = new File("A:\\hvhjgjjh\\src\\src\\server\\data\\"+name);
            file.createNewFile();
            return true;
        }
        FileOutputStream out  = new FileOutputStream("A:\\hvhjgjjh\\src\\src\\server\\data\\"+name);
        // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = inp.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            out.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        out.close();
        return false;
    }
    public static int GeneraionID(String name){
        int ID = 0;
        char [] newname = name.toCharArray();
        for(char i : newname){
            ID+=(int)i;
        }
        Random random = new Random();
        ID=ID%random.nextInt(1000);
        return ID;
    }
    public static void sendFile(String FilePath, DataOutputStream output)  {
        int bytes = 0;
        int size = -4;
        try{
            File file = new File(FilePath);
            size = (int) file.length();
            output.writeLong(size);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4*1024];
            while ((bytes=fileInputStream.read(buffer))!=-1){
                output.write(buffer,0,bytes);
                output.flush();
            }
            fileInputStream.close();
        }catch (IOException e){
            System.out.println("Such file not exist!");
            try {
                output.writeInt(-4);
            }catch (IOException r){
                r.printStackTrace();
            }
        }

    }
}
abstract class OperationWithFiles{

    protected  boolean CheckAddToList;

    public boolean getCheckAddToList(){
        return CheckAddToList;
    }

}
class AddNewFile extends OperationWithFiles{
    public Map<Integer, String> AddToList(int ID,String FileName, Map<Integer,String> MyFiles){
        this.CheckAddToList = false;

        if (!MyFiles.containsValue(FileName)){
            MyFiles.put(ID,FileName);
            this.CheckAddToList = true;
        }

        return MyFiles;
    }
}

class DeleteFromFiles extends OperationWithFiles{

    public Map<Integer, String> DeleteFile(int ID, String FileName, Map<Integer,String> MyFiles){
        this.CheckAddToList = false;

        if(ID != -1){
            if(MyFiles.containsKey(ID)){
                MyFiles.remove(ID);
                this.CheckAddToList = true;
            }
        }else{
            if(MyFiles.containsValue(FileName)){
                MyFiles.remove(FileName);
                this.CheckAddToList = true;
            }
        }
        return MyFiles;
    }

}
class GetFromFiles extends OperationWithFiles{

    public void GetFile(int ID,String FileName,Map<Integer,String> MyFiles){
        if(ID != -1){
        this.CheckAddToList = MyFiles.containsKey(ID);
        }else{
            this.CheckAddToList = MyFiles.containsValue(FileName);
        }
    }

}