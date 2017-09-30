/*
 * Copyright 2017 Kodehawa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.kodehawa.lib.imageboards;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;
import net.kodehawa.lib.imageboards.util.Requester;
import net.kodehawa.lib.imageboards.util.Utils;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Image board API instance.
 * @param <T> Board image return type.
 *
 * @author Avarel
 * @author Kodehawa
 */
public class ImageBoardAPI<T extends BoardImage> {
    /**
     * Current version of the image board library.
     */
    public static final String VERSION = "@version@";

    /**
     * Requester client.
     */
    private final Requester requester;
    
    /**
     * The URL of the imageboard's API endpoint.
     */
    private Board apiHome;

    /**
     * Deserialization target.
     */
    private Class<T> cls;

    /**
     * GET return format of the board.
     */
    private ResponseFormat responseFormat;

    /**
     * Create a new image board instance.
     *
     * @param landing Board {@link Board API landing}.
     * @param cls
     */
    public ImageBoardAPI(Board landing, Class<T> cls) {
        this(landing, ResponseFormat.JSON, cls);
    }

    /**
     * Create a new image board instance.
     *
     * @param landing Board {@link Board API landing}.
     * @param responseFormat Response format of the board.
     * @param cls
     */
    public ImageBoardAPI(Board landing, ResponseFormat responseFormat, Class<T> cls) {
        this(Executors.newCachedThreadPool(), new OkHttpClient(), landing, responseFormat, cls);
    }

    /**
     * Create a new image board instance.
     *
     * @param executor {@link Requester}'s executor.
     * @param client {@link Requester}'s request client.
     * @param landing Board {@link Board API landing}.
     * @param responseFormat Response format of the board.
     * @param cls
     */
    public ImageBoardAPI(ExecutorService executor,
                         OkHttpClient client,
                         Board landing,
                         ResponseFormat responseFormat,
                         Class<T> cls) {
        this.requester = new Requester(executor, client);
        this.apiHome = landing;
        this.responseFormat = responseFormat;
        this.cls = cls;
    }

    /**
     * Get the board landing of this instance.
     * @return Board type.
     */
    public Board getBoardType() {
        return apiHome;
    }

    /**
     * Get the image return type of this board.
     * @return A java class of the image return type.
     */
    public Class<?> getImageType() {
        return cls.getComponentType();
    }

    /**
     * Get first page results of the image board, limited at 60 images.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> get() {
        return get(60);
    }

    /**
     * Get first page results of the image board.
     *
     * @param limit Maximum number of images.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> get(int limit) {
        return get(0, limit);
    }

    /**
     * Get the provided page's results of the image board.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> get(int page, int limit) {
        return makeRequest(page, limit, null);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param search Image tags.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> search(String search) {
        return search(60, search);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> search(int limit, String search) {
        return search(0, limit, search);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @return A {@link Requester.Action request action} that returns a list of images.
     */
    public Requester.Action<List<T>> search(int page, int limit, String search) {
        return makeRequest(page, limit, search);
    }

    private Requester.Action<List<T>> makeRequest(int page, int limit, String search) throws QueryParseException, QueryFailedException {
        HashMap<String, Object> queryParams = new HashMap<>();

        if (page != 0) {
            queryParams.put(apiHome.getPageMarker(), page);
        }

        queryParams.put("limit", limit);

        if (search != null) {
            queryParams.put("tags", search.toLowerCase().trim());
        }

        HttpUrl url = HttpUrl.parse(apiHome.getURL() + apiHome.getSeparator() + Utils.urlEncodeUTF8(queryParams));
        return requester.makeRequest(url, response -> {
            try {
                List<T> wallpapers = responseFormat.readValue(response,
                        responseFormat.mapper.getTypeFactory().constructCollectionType(List.class, cls));

                if (wallpapers != null) {
                    return wallpapers;
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new QueryParseException(response, e);
            }
        });
    }

    /**
     * The type of the specified Imageboard.
     * This is important because if you specify the wrong type, it'll be impossible to deserialize it properly.
     */
    public enum ResponseFormat {
        /** JSON response type. */
        JSON(new ObjectMapper()),

        /** XML response type. */
        XML(new XmlMapper());

        private final ObjectMapper mapper;

        ResponseFormat(ObjectMapper mapper) {
            this.mapper = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        private <T> T readValue(String p, JavaType valueType) {
            try {
                return mapper.readValue(p, valueType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
