package analyzer;

public class Response {

    private String FileName;
    private  String RigthAnswer;
    private final String Error = "Unknown file type";


    public String OutputRigthAnswer(String rigthAnswer, String FileName) {
        this.RigthAnswer = rigthAnswer;
        this.FileName  = FileName;
        String Answer = FileName + ": "+ rigthAnswer;

        return Answer;
    }

    public String getError(String FileName) {
        this.FileName = FileName;

        String Ans= FileName + ": " + this.Error;

        return Ans;
    }
    


}
