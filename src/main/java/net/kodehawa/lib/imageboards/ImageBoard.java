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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.boards.DefaultBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.Rating;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;
import net.kodehawa.lib.imageboards.entities.impl.DanbooruImage;
import net.kodehawa.lib.imageboards.entities.impl.FurryImage;
import net.kodehawa.lib.imageboards.entities.impl.SafeFurryImage;
import net.kodehawa.lib.imageboards.requests.RequestAction;
import net.kodehawa.lib.imageboards.requests.RequestFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
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
    private static String userAgent = "ImageBoardAPI/https://github.com/Kodehawa/imageboard-api";

    /**
     * Requester client.
     */
    private final RequestFactory requestFactory;

    /**
     * Image board's endpoint.
     */
    private final Board board;

    /**
     * Deserialization target.
     */
    private final Class<T> cls;

    /**
     * GET return format of the board.
     */
    private final ResponseFormat responseFormat;

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
     * Set a custom user agent to use in the requests to image boards (boorus).
     * The default one is ImageBoardAPI/https://github.com/Kodehawa/imageboard-api
     * This can be changed to basically anything, even a browser agent. Please don't abuse, though.
     * @param agent The new user agent to make HTTP requests with.
     */
    public static void setUserAgent(String agent) {
        userAgent = agent;
    }

    /**
     * Get the current user agent used by ImageBoard. This can be changed with {@link #setUserAgent(String)}
     * @return the current user agent used to make HTTP requests with.
     */
    public static String getUserAgent() {
        return userAgent;
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

    public RequestAction<List<T>> search(List<String> search, Rating rating) {
        return search(0, 60, String.join(" ", search), rating);
    }

    public RequestAction<List<T>> search(String[] search, Rating rating) {
        return search(0, 60, String.join(" ", search), rating);
    }

    public RequestAction<List<T>> search(Rating rating, String... search) {
        return search(0, 60, String.join(" ", search), rating);
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
     * @param limit Maximum number of images.
     * @param search Image tags.
     * @param rating The rating to look for
     * @return A {@link RequestAction request action} that returns a list of images.
     */
    public RequestAction<List<T>> search(int limit, String search, Rating rating) {
        return search(0, limit, search, rating);
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
                // I call cursed.
                boolean appendLongTag = (board == DefaultBoards.GELBOORU || board == DefaultBoards.R34 || board == DefaultBoards.SAFEBOORU);
                tags.append(" rating:").append(appendLongTag ? rating.getLongName() : rating.getShortName());
            }

            // Why, just, why, why would you return anything but?
            // Other imageboards do this aswell, but we can't filter on all
            // Use BoardImage#isPending to check yourself
            if (getImageType() == FurryImage.class || getImageType() == SafeFurryImage.class || getImageType() == DanbooruImage.class) {
                tags.append(" status:active");
            }

            urlBuilder.addQueryParameter("tags", tags.toString());
        }

        HttpUrl url = urlBuilder.build();
        return requestFactory.makeRequest(url, response -> {
            log.debug("Making request to {} (Response format: {}, ImageBoard: {}, Target: {})", url.toString(), responseFormat, board, cls);
            try (ResponseBody body = response.body()) {
                if (body == null) {
                    log.debug("Received empty body from a ImageBoard ({})! Returning empty list.", getImageType());
                    return Collections.emptyList();
                }

                ObjectMapper mapper = responseFormat.mapper;
                List<T> images;
                InputStream json = body.byteStream();

                if(board.getOuterObject() != null) {
                    String posts = mapper.writeValueAsString(mapper.readTree(json).get(board.getOuterObject()));
                    images = mapper.readValue(posts, mapper.getTypeFactory().constructCollectionType(List.class, cls));
                } else {
                    images = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, cls));
                }

                body.close();

                return images;
            } catch (MismatchedInputException eof) {
                log.debug("Received MismatchedInputException from a ImageBoard ({})! Returning empty list.", getImageType(), eof);
                return Collections.emptyList();
            } catch (IOException e) {
                throw new QueryParseException(e);
            }
        });
    }

    /**
     * The type of the specified ImageBoard.
     * This is important because if you specify the wrong type, it'll be impossible to deserialize it properly.
     * For now we only have JSON, as all imageboards now support a JSON response.
     * If your imageboard doesn't by default, make sure it's an option.
     */
    public enum ResponseFormat {
        /** JSON response type. */
        JSON(new ObjectMapper());

        private final ObjectMapper mapper;

        ResponseFormat(ObjectMapper mapper) {
            this.mapper = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
