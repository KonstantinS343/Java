package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Command{

    public void CommandRun(DataOutputStream output, JsonObject map){
        Gson gson = new Gson();

        ExecutorService executor = Executors.newFixedThreadPool(10);


        switch (map.get("type").getAsString()) {
            case "get":
                executor.submit(()->{
                    try {
                        output.writeUTF(database.get(map.get("key")));
                    }catch (IOException ignored){

                    }

                });
                break;
            case "set":
                executor.submit(()->{
                    try{
                        output.writeUTF(database.set(map.get("key"), map.get("value")));
                    }catch (IOException ignored){

                    }
                });
                break;
            case "delete":
                executor.submit(()->{
                    try{
                        output.writeUTF(database.delete(map.get("key")));
                    }catch (IOException ignored){

                    }
                });
                break;
            case "exit":
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("response","OK");
                try {
                    output.writeUTF(gson.toJson(resultMap));
                }catch (IOException ignored){

                }
                executor.shutdown();
                break;
            default:
                break;


        }
    }

}
