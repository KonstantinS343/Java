import java.util.*;
import java.util.concurrent.*;


class FutureUtils {

    public static int howManyIsDone(List<Future> items) {

        int  counter = 0;
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).isDone()){
                counter++;
            }
        }
        return counter;
    }

}