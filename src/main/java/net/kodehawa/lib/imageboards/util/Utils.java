package net.kodehawa.lib.imageboards.util;

import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.util.Map;

public class Utils {
    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<?, ?> entry : map.entrySet()) {
            if(sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    @SneakyThrows
    private static String urlEncodeUTF8(String s) {
        return URLEncoder.encode(s, "UTF-8");
    }
}