package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CheckFile implements  Callable<String> {

   private List<pattern> Templates;
    private String file;

    public CheckFile(List<pattern> patternList, String file){
        this.Templates = patternList;
        this.file = file;
    }


    @Override
    public String call() throws Exception {
        Path path =new File(file).toPath();
        String Content = " ";

        try {
            byte[] data = Files.readAllBytes(path);

            Content = new String(data, StandardCharsets.UTF_8);


        }catch (IOException e){
            e.printStackTrace();
        }
        KMPSearch KMPSearch = new KMPSearch();

        for(int i = 0 ; i < Templates.size(); i++){

            KMPSearch.SearchRun(Content,Templates.get(i),file);

        }

        List<String> Output = KMPSearch.GetList();
        List<String> RirthAnswer = new ArrayList<>();
        for( int i = 0; i < Output.size(); i++){
            if(!Output.get(i).equals(new Response().getError(file))){
                RirthAnswer.add(Output.get(i));
            }
        }

        if(RirthAnswer.size() != 0){
            return RirthAnswer.get(RirthAnswer.size()-1);
        }else{
            return new Response().getError(file);
        }

    }
}
