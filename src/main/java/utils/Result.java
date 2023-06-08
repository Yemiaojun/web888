package utils;
import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Result {
    public static String okGetString(String message){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("message",message);
        String s = JSONObject.toJSONString(map);
        return s;

    }
    public static String okGetStringByData(String message,Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("message",message);
        map.put("data",data);
        String s = JSONObject.toJSONString(map);
        return s;

    }

    public static String errorGetString(String message){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 400); // 400表示客户端请求出错
        map.put("message", message);
        // 在错误情况下，我们通常不会返回任何数据
        String s = JSONObject.toJSONString(map);
        return s;
    }

}
