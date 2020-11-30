package website.lhc.heron.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.util
 * @ClassName: JsonUtil
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/26 上午 12:58
 */
public class JsonUtil {
    public static String toJsonString(Object o) {
        Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE)));
            }
        }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE)));
            }
        }).registerTypeAdapter(LocalTime.class, new JsonSerializer<LocalTime>() {
            @Override
            public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.SIMPLIFIED_CHINESE)));
            }
        }).create();
        return gson.toJson(o);
    }
}
