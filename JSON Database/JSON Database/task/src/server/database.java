package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class database {

    private static Response response = new Response();
    private static final String ERROR = "No such key";
    private static final String db_path="src/server/data/db.json";
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    public static final Lock writeLock = lock.writeLock();
    private static JsonObject db = new JsonObject();

    private static void saveDatabase() {
        try {
            writeLock.lock();
            FileWriter writer = new FileWriter(db_path);
            writer.write(new Gson().toJson(db));
            writer.close();
            writeLock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static String set(JsonElement pos, JsonElement value) {
        JsonObject data = db;

        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();

            for (int i = 0; i < path.size(); i++) {
                if (i == path.size() - 1) {
                    data.add(path.get(i).getAsString(), value);

                    break;
                } else if (data.has(path.get(i).getAsString())) {
                    if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                        data.add(path.get(i).getAsString(), new JsonObject());
                    }
                } else {
                    data.add(path.get(i).getAsString(), new JsonObject());
                }

                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            db.add(pos.getAsString(), value);
        }

        saveDatabase();

        return response.getSuccess();
    }
    public static String get(JsonElement pos) {
        JsonObject data = new Gson().fromJson(db, JsonObject.class);

        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();

            if (path.size() == 1) {
                if (!data.has(pos.getAsString())) {
                    return response.getError(ERROR);
                } else {

                    return response.getValue(data.get(path.get(0).getAsString()).getAsJsonObject());
                }
            } else {
                for (int i = 0; i < path.size(); i++) {
                    if (data.has(path.get(i).getAsString())) {
                        if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                            if (i == path.size() - 1) {
                                return response.getValue(data.get(path.get(i).getAsString()).getAsString());
                            } else {
                                return response.getError(ERROR);
                            }
                        } else {
                            data = data.get(path.get(i).getAsString()).getAsJsonObject();
                        }
                    } else {
                        return response.getError(ERROR);
                    }
                }
            }
        } else {
            if (!data.has(pos.getAsString())) {
                return response.getError(ERROR);
            } else {
                return response.getValue(new Gson().toJson(data.get(pos.getAsString())));
            }
        }

        return response.getError(ERROR);
    }

    public static String delete(JsonElement pos) {
        JsonObject data = db;

        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();

            for (int i = 0; i < path.size(); i++) {
                if (!data.has(path.get(i).getAsString())) {
                    return response.getError(ERROR);
                } else if (i == path.size() - 1) {
                    if (data.has(path.get(i).getAsString())) {
                        data.remove(path.get(i).getAsString());

                        break;
                    }
                } else if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                    return response.getError(ERROR);
                }

                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            if (!data.has(pos.getAsString())) {
                return response.getError(ERROR);
            } else {
                db.remove(pos.getAsString());
            }
        }

        saveDatabase();

        return response.getSuccess();
    }
}