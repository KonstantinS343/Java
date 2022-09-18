package encryptdecrypt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Main {
    public static void main(String[] args) {
        String mode="enc",data="",buffer="",algorithm="shift";
        BufferedReader myin=null;
        boolean check=false,checkfile=false;
        int key=0,index=0;
        for(int i=0 ; i<args.length;i++){
            if(args[i].equals("-mode")){
                mode = args[i +1];
            }
            if(args[i].equals("-key")){
                key = Integer.valueOf(args[i+1]);
            }
            if(args[i].equals("-data")){
                data = args[i +1];
                check=true;
            }
            if(args[i].equals("-in") && check == false){
                try{
                    myin=new BufferedReader(new FileReader(args[i+1]));
                    while((buffer=myin.readLine())!=null){
                        data=buffer;
                    }
                    myin.close();
                }catch(IOException e){
                    return ;
                }
            }
            if(args[i].equals("-out")){
                index=i+1;
                checkfile=true;
            }
            if(args[i].equals("-alg")){
                algorithm=args[i+1];
            }
        }
        if(algorithm.equals("unicode")){
            Strategy code=null;
            if(checkfile==false){
                switch (mode) {
                    case "enc":
                        code = new Strategy(new EncryptionUnicode());
                        System.out.println(code.result(data, key));
                        break;
                    case "dec":
                        code =  new Strategy(new DescriptionUnicode());
                        System.out.println(code.result(data, key));
                        break;
                }
            }else{
                try {
                    File file =new File(args[index]);
                    PrintWriter write=new PrintWriter(args[index]);
                    switch (mode) {
                        case "enc":
                            code = new Strategy(new EncryptionUnicode());
                            write.println(code.result(data, key));
                            break;
                        case "dec":
                            code =  new Strategy(new DescriptionUnicode());
                            write.println(code.result(data, key));
                            break;
                    }
                    write.close();
                } catch (IOException e) {
                    return ;
                }
            }
        }else{
            Strategy code=null;
            if(checkfile==false){
                switch (mode) {
                    case "enc":
                        code = new Strategy(new EncryptionAlphabet());
                        System.out.println(code.result(data, key));
                        break;
                    case "dec":
                        code =  new Strategy(new DescriptionAlphabet());
                        System.out.println(code.result(data, key));
                        break;
                }
            }else {
                try {
                    File file = new File(args[index]);
                    PrintWriter write = new PrintWriter(args[index]);
                    switch (mode) {
                        case "enc":
                            code = new Strategy(new EncryptionAlphabet());
                            write.println(code.result(data, key));
                            break;
                        case "dec":
                            code = new Strategy(new DescriptionAlphabet());
                            write.println(code.result(data, key));
                            break;
                    }
                    write.close();
                } catch (IOException e) {
                    return;
                }
            }
        }
    }
}

class Strategy{
    private Code myCondition;

    public Strategy(Code myCondition){
        this.myCondition = myCondition;
    }

    public char[] result(String str, int key) {
        return this.myCondition.getResult(str, key);
    }
}
interface Code{

    char[] getResult(String str, int key);

}
class DescriptionUnicode implements Code{

    public char[] getResult(String str, int key) {

        char[] newstr=new char[str.length()];
        for(int i=0;i<str.length();i++){
            int symbol=str.charAt(i);
            newstr[i]=(char)(symbol-key);
        }
        return newstr;
    }

}
class EncryptionUnicode implements Code{

    public char[] getResult(String str, int key) {

        char[] newstr=new char[str.length()];
        for(int i=0;i<str.length();i++){
            int symbol=str.charAt(i);
            newstr[i]=(char)(symbol+key);
        }
        return newstr;
    }

}
class EncryptionAlphabet implements Code{

    public char[] getResult(String str, int key) {

        char [] newstr = new char[str.length()];
        for(int i=0;i<str.length();i++){
            int symbol=str.charAt(i);
            if(symbol >= 65 && symbol<=90){
                if(symbol+key >90){
                    newstr[i]=(char)(key+symbol-26);
                }else{
                    newstr[i]=(char)(symbol+key);
                }
            }else if(symbol >= 97 && symbol<=122){
                if(symbol+key > 122) {
                    newstr[i]=(char)(key+symbol-26);
                }else{
                    newstr[i]=(char)(symbol+key);
                }
            }else{
                newstr[i]=(char)(symbol);
            }
        }
        return newstr;
    }
}
class DescriptionAlphabet implements Code{

    public char[] getResult(String str, int key) {

        char [] newstr = new char[str.length()];
        for(int i=0;i<str.length();i++){
            int symbol=str.charAt(i);
            if(symbol >= 65 && symbol<=90){
                if(symbol-key <65){
                    newstr[i]=(char)(symbol-key+26);
                }else{
                    newstr[i]=(char)(symbol-key);
                }
            }else if(symbol >= 97 && symbol<=122){
                if(symbol-key < 97) {
                    newstr[i]=(char)(symbol -key+26);
                }else{
                    newstr[i]=(char)(symbol-key);
                }
            }else{
                newstr[i]=(char)(symbol);
            }
        }
        return newstr;
    }
}

