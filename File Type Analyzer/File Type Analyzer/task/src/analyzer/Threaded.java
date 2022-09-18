package analyzer;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Threaded {

    public void ProgramRun(List<pattern> patternList, File Directory) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<CheckFile> list = new ArrayList<>();
        String [] files = Directory.list();

        for (String file : files) {
            CheckFile checkFile = new CheckFile(patternList,String.valueOf(Directory+"/"+file));
            list.add(checkFile);
        }

        List<Future<String>> futureList = null;

        try{
            futureList = executorService.invokeAll(list);
        }catch (InterruptedException e){
            System.out.println("ERROR");
        }
        executorService.shutdown();

        for(int i = 0; i < futureList.size(); i++) {
            Future<String> future = futureList.get(i);
            try {
                String output = future.get();
                System.out.println(output);
            }        catch (Exception e){
                System.out.println("ERROR");
            }
        }
    }

}
