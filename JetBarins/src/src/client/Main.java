package src.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        String address = "localhost", name , choice, AnswerFromServer, second_name;
        int port = 23456;
        int key = 0, ID =0;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        try (
            Socket socket = new Socket(address, port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ){
            while(!exit){
                System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
                choice = scanner.nextLine();
                try{
                    key = Integer.parseInt(choice);
                }catch (NumberFormatException e){
                    System.out.println("The request was sent.");
                    socket.close();
                    break;
                }
                switch (key){
                    case 1:
                        output.writeUTF(choice);
                        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
                        choice = scanner.nextLine();
                        output.writeUTF(choice);
                        if(choice.equals("1")){
                            System.out.print("Enter filename: ");
                            name = scanner.nextLine();
                            output.writeUTF(name);
                        }else{
                            System.out.print("Enter id: ");
                            choice = scanner.nextLine();
                            output.writeUTF(choice);
                        }
                        System.out.println("The request was sent.");
                        AnswerFromServer = input.readUTF();

                        if(AnswerFromServer.equals("200")){
                            name = input.readUTF();
                            System.out.println("The file was downloaded! Specify a name for it: "+name);
                            receivFile(name,input);
                            System.out.println("File saved on the hard drive!");

                        }else{
                            System.out.println("The response says that the file was not found!");
                        }
                        break;
                    case 2:
                        System.out.print("Enter name of the file: ");
                        name = scanner.nextLine();
                        System.out.print("Enter name of the file to be saved on server: ");
                        second_name = scanner.nextLine();

                        if(second_name.equals("")){
                            second_name = name;
                        }
                        output.writeUTF(choice);
                        output.writeUTF(second_name);
                        String FilePath = "A:\\hvhjgjjh\\src\\src\\client\\data\\"+name;
                        sendFile(FilePath,output);
                        AnswerFromServer = input.readUTF();
                        System.out.println("The request was sent.");
                        if(AnswerFromServer.equals("200")){
                            System.out.println("Response says that file is saved!  ID = " + input.readUTF());
                        }else{
                            System.out.println("The response says that creating the file was forbidden!");
                        }
                        break;
                    case 3:
                        output.writeUTF(choice);
                        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
                        choice = scanner.nextLine();
                        output.writeUTF(choice);
                        if(choice.equals("1")){
                            System.out.print("Enter filename: ");
                            name = scanner.nextLine();
                            output.writeUTF(name);
                        }else{
                            System.out.print("Enter id: ");
                            choice = scanner.nextLine();
                            output.writeUTF(choice);
                        }

                        System.out.println("The request was sent.");
                        AnswerFromServer = input.readUTF();

                        if(AnswerFromServer.equals("200")){
                            System.out.println("The response says that the file was successfully deleted!");

                        }else{
                            System.out.println("The response says that the file was not found!");
                        }
                        break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void sendFile(String FilePath, DataOutputStream output)  {
        int bytes = 0;
        int size = -4;
        try{
        File file = new File(FilePath);
        size = (int) file.length();
        if(size == 0){
            file.createNewFile();
            output.writeLong(size);
            return;
        }
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
    public static boolean receivFile(String name,DataInputStream inp) throws IOException {
        int bytes = 0;
        long size = inp.readLong();
        FileOutputStream out  = new FileOutputStream("A:\\hvhjgjjh\\src\\src\\client\\data\\"+name);
        // read file size
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = inp.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            out.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        out.close();
        return false;
    }
}

