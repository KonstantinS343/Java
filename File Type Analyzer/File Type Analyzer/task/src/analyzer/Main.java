package analyzer;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        File Directory = new File(args[0]);
        
        PatternList patternList = new PatternList("./src/analyzer/"+args[1]);

        new Threaded().ProgramRun(patternList.getvalue(), Directory);



    }

}
