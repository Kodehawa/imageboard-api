package net.kodehawa.lib.imageboards.requests.auth;

import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.requests.BoardRequestType;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Authorization hook that can inject authentication data into a board request.
 *
 * <p>Implementations may mutate the URL builder before the request is created, the request builder
 * right before execution, or both.</p>
 */
public interface BoardAuthorizationProvider {

    /**
     * Called before URL is built so URL/query/path authentication parts can be injected.
     *
     * @param board Board being queried.
     * @param requestType Type of the request.
     * @param urlBuilder URL builder to mutate.
     */
    default void applyToUrl(Board board, BoardRequestType requestType, HttpUrl.Builder urlBuilder) {}

    /**
     * Called before request is sent so request-level (body etc.) auth parts can be injected.
     *
     * @param board Board being queried.
     * @param requestType Type of the request.
     * @param requestBuilder Request builder to mutate.
     */
    default void applyToRequest(Board board, BoardRequestType requestType, Request.Builder requestBuilder) {}
}
