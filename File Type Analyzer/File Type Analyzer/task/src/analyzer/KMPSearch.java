package analyzer;


import java.util.ArrayList;
import java.util.List;

public class KMPSearch {

    private List<String> Answers = new ArrayList<>();

    public void SearchRun(String Content, pattern pattern, String file){

        int [] PrefixArray = Prefix(pattern.getTemplate());
        int i = 0;
        int j = 0;
        boolean check = false;

        while(i < Content.length()){

            if(Content.charAt(i) == pattern.getTemplate().charAt(j)){
                i++;
                j++;
            }

            if(j == PrefixArray.length){
                check = true;
                break;
            }else if (i < Content.length() && Content.charAt(i) != pattern.getTemplate().charAt(j)){
                 if(j!= 0){
                     j = PrefixArray[j-1];
                 } else{
                     i = i+1;
                 }
            }

        }

        if(check){
           this.Answers.add(new Response().OutputRigthAnswer(pattern.getType(), file));
        }else{
            this.Answers.add(new Response().getError(file));
        }

    }

    public static int[] Prefix(String Content){

        int [] prefixarray = new int[Content.length()];

        for(int  i = 1; i < Content.length(); i++) {
            int j = 0;
            while(i+j < Content.length() && Content.charAt(j) == Content.charAt(i+j)){

                prefixarray[i+j] = Math.max(j+1, prefixarray[i+j]);
                j++;
            }
        }
        return  prefixarray;
    }

    public List<String> GetList(){
        return Answers;
    }


}
