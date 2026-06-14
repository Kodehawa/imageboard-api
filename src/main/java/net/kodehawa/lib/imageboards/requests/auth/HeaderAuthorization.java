package net.kodehawa.lib.imageboards.requests.auth;

import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.requests.BoardRequestType;
import okhttp3.Request;

import java.util.Objects;

/**
 * Adds a fixed header to every request.
 */
public class HeaderAuthorization implements BoardAuthorizationProvider{

    private final String name;
    private final String value;

    public HeaderAuthorization(String name, String value) {
        this.name = Objects.requireNonNull(name, "Header name cannot be null");
        this.value = Objects.requireNonNull(value, "Header value cannot be null");
    }

    @Override
    public void applyToRequest(Board board,
                               BoardRequestType requestType,
                               Request.Builder requestBuilder) {
        requestBuilder.header(name, value);
    }
}
