/*
 * Copyright (C) 2016-2017 David Alejandro Rubio Escares / Kodehawa
 *
 * Mantaro is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Mantaro is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mantaro.  If not, see http://www.gnu.org/licenses/
 */

package net.kodehawa.lib.imageboards;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import net.kodehawa.lib.imageboards.util.Requester;
import net.kodehawa.lib.imageboards.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ImageboardAPI<T> {

    //Async executor.
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    //The XML mapper.
    private static final ObjectMapper XML_MAPPER = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //JSON Mapper.
    private final Gson gson = new Gson();

    private final Requester requester = new Requester();
    //The URL of the imageboard's API endpoint.
    private Boards apiHome;
    //Where will we deserialize the final result?
    private Class<T[]> clazz;

    //What type is this imageboard, JSON or XML?
    private Type type;

    public ImageboardAPI(Boards landing, Type type, Class<T[]> clazz1) {
        this.apiHome = landing;
        this.type = type;
        this.clazz = clazz1;
    }

    // ----- Async methods -----
    public void get(int limit, Consumer<List<T>> handler) {
        get(limit, null, handler);
    }

    public void get(Consumer<List<T>> handler) {
        get(60, null, handler);
    }

    public void onSearch(int limit, String search, Consumer<List<T>> handler) {
        get(limit, search, handler);
    }

    public void onSearch(String search, Consumer<List<T>> handler) {
        get(60, search, handler);
    }

    // ----- Blocking methods -----
    public List<T> getBlocking(int limit) {
        return getBlocking(limit, null);
    }

    public List<T> getBlocking() {
        return getBlocking(60, null);
    }

    public List<T> onSearchBlocking(int limit, String search) {
        return getBlocking(limit, search);
    }

    public List<T> onSearchBlocking(String search) {
        return getBlocking(60, search);
    }

    private List<T> get(int limit, String search) throws Exception {
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        T[] wallpapers;

        if(search != null) queryParams.put("tags", search.toLowerCase().trim());

        try {
            String response = requester.request(apiHome + apiHome.separator + Utils.urlEncodeUTF8(queryParams));
            if(response == null) return null;

            wallpapers = type.equals(Type.JSON) ? gson.fromJson(response, clazz) : XML_MAPPER.readValue(response, clazz);
        } catch(Exception e) {
            return null;
        }

        return Arrays.asList(wallpapers);
    }

    private void get(int limit, String search, Consumer<List<T>> result) {
        executorService.execute(() -> {
            try {
                List<T> wallpapers = get(limit, search);
                result.accept(wallpapers);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private List<T> getBlocking(int limit, String search) {
        try {
            return get(limit, search);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The type of the specified Imageboard.
     * This is important because if you specify the wrong type, it'll be impossible to deserialize it properly.
     */
    public enum Type {
        JSON, XML
    }

    /**
     * The supported Imageboard as in this release.
     * Currently supported: Rule34 (R34), e631 (E621), Konachan (KONACHAN) and Yande.re (YANDERE).
     */
    public enum Boards {
        //Lewd APIs
        R34("https://rule34.xxx/index.php?page=dapi&s=post&q=index&json=1", "&"),
        E621("https://e621.net/post/index.json", "?"),
        //Normal APIs
        KONACHAN("http://konachan.com/post.json", "?"),
        YANDERE("https://yande.re/post.json", "?"),
        DANBOORU("https://danbooru.donmai.us/posts.json", "?");

        private String separator;
        private String url;

        Boards(String url, String separator) {
            this.url = url;
            this.separator = separator;
        }

        @Override
        public String toString() {
            return url;
        }
    }
}
