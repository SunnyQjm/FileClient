package utils;

import com.google.gson.Gson;

/**
 * Created by Sunny on 2017/7/7 0007.
 */
public class GsonUtil {
    public static Gson instance = null;
    private static Object lock = new byte[0];

    public static Gson getInstance() {
        if(instance == null){
            synchronized (lock){
                instance = new Gson();
            }
        }
        return instance;
    }
}
