package analyzer;


public class Request {

    private String TemlateOfFile;
    private  String TypeOfFile;

    public Request( String TemlateOfFile, String TypeOfFile){
        this.TemlateOfFile = TemlateOfFile;
        this.TypeOfFile = TypeOfFile;
    }

    public String getTemlateOfFile() {
        return TemlateOfFile;
    }

    public String getTypeOfFile() {
        return TypeOfFile;
    }



}
