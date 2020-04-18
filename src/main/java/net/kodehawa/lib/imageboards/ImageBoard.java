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
import net.kodehawa.lib.imageboards.boards.DefaultBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;
import net.kodehawa.lib.imageboards.requests.RequestAction;
import net.kodehawa.lib.imageboards.requests.RequestFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Image board API instance.
 * @param <T> Board image return type.
 *
 * @author Avarel
 * @author Kodehawa
 */
public class ImageBoard<T extends BoardImage> {
    private static final Logger log = LoggerFactory.getLogger(ImageBoard.class);

    /**
     * Current version of the image board library.
     */
    public static final String VERSION = "@version@";

    /**
     * User agent to send to the services we request data from.
     */
    public static final String userAgent = "ImageboardAPI/@version@/https://github.com/Kodehawa/imageboard-api";

    /**
     * Requester client.
     */
    private final RequestFactory requestFactory;
    
    /**
     * Image board's endpoint.
     */
    private Board board;

    /**
     * Deserialization target.
     */
    private Class<T> cls;

    /**
     * GET return format of the board.
     */
    private ResponseFormat responseFormat;

    /**
     * Changes whether we're gonna throw exceptions on EOF or no
     */
    public static boolean throwExceptionOnEOF = true;

    /**
     * Create a new image board instance.
     *
     * @param landing Board {@link Board API landing}.
     * @param cls The class this refers to.
     */
    public ImageBoard(Board landing, Class<T> cls) {
        this(landing, ResponseFormat.JSON, cls);
    }

    /**
     * Create a new image board instance.
     *
     * @param landing Board {@link Board API landing}.
     * @param responseFormat Response format of the board.
     * @param cls The class this refers to.
     */
    public ImageBoard(Board landing, ResponseFormat responseFormat, Class<T> cls) {
        this(new OkHttpClient.Builder()
                        .readTimeout(2, TimeUnit.SECONDS)
                        .build(),
                landing,
                responseFormat,
                cls);
    }

    /**
     * Create a new image board instance.
     *
     * @param client {@link RequestFactory}'s request client.
     * @param landing Board {@link Board API landing}.
     * @param cls The class this refers to.
     */
    public ImageBoard(OkHttpClient client,
                        Board landing,
                        Class<T> cls) {
        this(client, landing, ResponseFormat.JSON, cls);
    }

    /**
     * Create a new image board instance.
     *
     * @param client {@link RequestFactory}'s request client.
     * @param landing Board {@link Board API landing}.
     * @param responseFormat Response format of the board.
     * @param cls The class this refers to.
     */
    public ImageBoard(OkHttpClient client,
                      Board landing,
                      ResponseFormat responseFormat,
                      Class<T> cls) {
        this.requestFactory = new RequestFactory(client);
        this.board = landing;
        this.responseFormat = responseFormat;
        this.cls = cls;
    }

    /**
     * Get the board landing of this instance.
     * @return Board type.
     */
    public Board getBoardType() {
        return board;
    }

    /**
     * Get the image return type of this board.
     * @return A java class of the image return type.
     */
    public Class<T> getImageType() {
        return cls;
    }

    /**
     * Get first page results of the image board, limited at 60 images.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> get() {
        return get(60);
    }

    /**
     * Get first page results of the image board.
     *
     * @param limit Maximum number of images.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> get(int limit) {
        return get(0, limit);
    }

    /**
     * Get the provided page's results of the image board.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> get(int page, int limit) {
        return get(page, limit, null);
    }

    /**
     * Get the provided page's results of the image board.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @param rating The rating to look for.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> get(int page, int limit, Rating rating) {
        return makeRequest(page, limit, "", rating);
    }

    /**
     * Get the provided page's results of the image board.
     *
     * @param limit Maximum number of images.
     * @param rating The rating to look for.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> get(int limit, Rating rating) {
        return get(0, limit, rating);
    }


    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param search Image tags.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(String search) {
        return search(60, search);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(int limit, String search) {
        return search(0, limit, search);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(int page, int limit, String search) {
        return makeRequest(page, limit, search, null);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @param rating The rating to look for.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(int page, int limit, String search, Rating rating) {
        return makeRequest(page, limit, search, rating);
    }

    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param search Image tags.
     * @param rating The rating to look for.
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(String search, Rating rating) {
        return search(0, 60, search, rating);
    }

    public RequestAction<List<T>> search(List<String> search, Rating rating) {
        return search(0, 60, String.join(" ", search), rating);
    }

    public RequestAction<List<T>> search(String[] search, Rating rating) {
        return search(0, 60, String.join(" ", search), rating);
    }

    public RequestAction<List<T>> search(Rating rating, String... search) {
        return search(0, 60, String.join(" ", search), rating);
    }

    private RequestAction<List<T>> makeRequest(int page, int limit, String search, Rating rating) throws QueryParseException, QueryFailedException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(board.getScheme())
                .host(board.getHost())
                .addPathSegments(board.getPath())
                .query(board.getQuery())
                .addQueryParameter("limit", String.valueOf(limit));

        if (page != 0)
            urlBuilder.addQueryParameter(board.getPageMarker(), String.valueOf(page));
        if (search != null) {
            StringBuilder tags = new StringBuilder(search.toLowerCase().trim());
            if(rating != null) {
                //Fuck you gelbooru you're the only one doing this :(
                tags.append(" rating:").append(board == DefaultBoards.GELBOORU ? rating.getLongName() : rating.getShortName());
            }
            urlBuilder.addQueryParameter("tags", tags.toString());
        }

        HttpUrl url = urlBuilder.build();
        return requestFactory.makeRequest(url, response -> {
            log.info("Making request to {} (Response format: {}, Imageboard: {}, Target: {})", url.toString(), responseFormat, board, cls);
            try (ResponseBody body = response.body()) {
                if (body == null) {
                    if(throwExceptionOnEOF) {
                        throw new QueryParseException(new NullPointerException("Received an empty body from the imageboard URL. (From board: " + board + ", Target: " + cls + ")"));
                    } else {
                        return Collections.emptyList();
                    }
                }

                ObjectMapper mapper = responseFormat.mapper;
                List<T> images;
                InputStream inputStream = body.byteStream();

                if(board.getOuterObject() != null) {
                    String posts = mapper.writeValueAsString(mapper.readTree(inputStream).get(board.getOuterObject()));
                    images = mapper.readValue(posts, mapper.getTypeFactory().constructCollectionType(List.class, cls));
                } else {
                    images = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, cls));
                }

                body.close();

                if (images != null) {
                    return images;
                } else {
                    return null;
                }
            } catch (IOException e) {
                if(e.getMessage().contains("No content to map due to end-of-input") && !throwExceptionOnEOF) {
                    return Collections.emptyList();
                }

                e.printStackTrace();

                throw new QueryParseException(e);
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
