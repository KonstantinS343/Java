package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PatternList {
    private final String PatternName;
    List<pattern> patternList = new ArrayList<>();
    List<String> list = new ArrayList<>();

    public PatternList(String PatterName){
        this.PatternName = PatterName;
        PatternToArray();
    }


    public void PatternToArray(){
        readFile();
        archive();
    }

    private void readFile(){
        try{
            list = Files.readAllLines(Paths.get(PatternName));
        }catch (IOException e){
            System.out.println("ERROR");
        }
    }

    public List<pattern> getvalue(){
        return patternList;
    }

    private void archive(){
        for(String str: list){
            String [] temp = str.trim().split(";");
            patternList.add(new pattern(temp[1].replaceAll("\"",""),temp[2].replaceAll("\"","")));
        }
    }
}

class pattern{

    private String Template;
    private String Type;

    public pattern(String Template, String Type){
        this.Template = Template;
        this.Type = Type;
    }

    public String getTemplate() {
        return Template;
    }

    public String getType() {
        return Type;
    }


}
