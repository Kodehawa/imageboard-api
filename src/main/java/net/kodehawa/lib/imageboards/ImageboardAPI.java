package net.kodehawa.lib.imageboards;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.util.Requester;
import net.kodehawa.lib.imageboards.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ImageboardAPI<T extends BoardImage> {

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
    public void get(int page, int limit, Consumer<List<T>> handler) {
        get(page, limit, null, handler);
    }

    public void get(int limit, Consumer<List<T>> handler) {
        get(0, limit, null, handler);
    }

    public void get(Consumer<List<T>> handler) {
        get(0, 60, null, handler);
    }

    public void onSearch(int page, int limit, String search, Consumer<List<T>> handler) {
        get(page, limit, search, handler);
    }

    public void onSearch(int limit, String search, Consumer<List<T>> handler) {
        get(0, limit, search, handler);
    }

    public void onSearch(String search, Consumer<List<T>> handler) {
        get(0, 60, search, handler);
    }

    // ----- Blocking methods -----
    public List<T> getBlocking(int page, int limit) {
        return getBlocking(page, limit, null);
    }

    public List<T> getBlocking(int limit) {
        return getBlocking(0, limit, null);
    }

    public List<T> getBlocking() {
        return getBlocking(0, 60, null);
    }

    public List<T> onSearchBlocking(int page, int limit, String search) {
        return getBlocking(page, limit, search);
    }

    public List<T> onSearchBlocking(int limit, String search) {
        return getBlocking(0, limit, search);
    }

    public List<T> onSearchBlocking(String search) {
        return getBlocking(0, 60, search);
    }

    private List<T> get(int page, int limit, String search) throws Exception {
        HashMap<String, Object> queryParams = new HashMap<>();
        if(page != 0) queryParams.put(apiHome.pageMarker, page);
        queryParams.put("limit", limit);
        T[] wallpapers;

        if(search != null) queryParams.put("tags", search.toLowerCase().trim());

        try {
            String response = requester.request(apiHome + apiHome.separator + Utils.urlEncodeUTF8(queryParams));
            if(response == null) return null;

            wallpapers = type.equals(Type.JSON) ? gson.fromJson(response, clazz) : XML_MAPPER.readValue(response, clazz);
            return Arrays.asList(wallpapers);
        } catch(Exception e) {
            return null;
        }
    }

    private void get(int page, int limit, String search, Consumer<List<T>> result) {
        executorService.execute(() -> {
            try {
                List<T> wallpapers = get(page, limit, search);
                result.accept(wallpapers);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private List<T> getBlocking(int page, int limit, String search) {
        try {
            return get(page, limit, search);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boards getBoardType() {
        return apiHome;
    }

    public Class<?> getImageType() {
        return clazz.getComponentType();
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
     * Currently supported: Rule34 (R34), e631 (E621), Konachan (KONACHAN) and Yande.re (YANDERE), Danbooru (DANBOORU) and Safebooru (SAFEBOORU).
     */
    public enum Boards {
        //Lewd APIs
        R34("https://rule34.xxx/index.php?page=dapi&s=post&q=index&json=1", "&", "pid"),
        E621("https://e621.net/post/index.json", "?", "page"),
        //Normal APIs
        KONACHAN("http://konachan.com/post.json", "?", "page"),
        YANDERE("https://yande.re/post.json", "?", "page"),
        DANBOORU("https://danbooru.donmai.us/posts.json", "?", "page"),
        SAFEBOORU("https://safebooru.org/index.php?page=dapi&s=post&q=index&pid=1&limit=5&json=1", "&", "pid");

        private String separator;
        private String url;
        private String pageMarker;

        Boards(String url, String separator, String pageMarker) {
            this.url = url;
            this.separator = separator;
            this.pageMarker = pageMarker;
        }

        @Override
        public String toString() {
            return url;
        }
    }
}
