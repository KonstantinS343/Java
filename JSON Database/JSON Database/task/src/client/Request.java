package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

public class Request {
    @Parameter(names = "-t")
    public String type;
    @Parameter(names = "-k")
    public String key;
    @Parameter (names = "-v")
    public String value;
    @Parameter (names = {"--input","-in"})
    public  String input;


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getIndex() {
        return key;
    }

    public String getMess() {
        return value;
    }

    public String getInp() {
        return input;
    }

    public String getCommand() {
        return type;
    }
}
