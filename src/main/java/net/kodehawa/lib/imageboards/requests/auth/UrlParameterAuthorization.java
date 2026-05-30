package net.kodehawa.lib.imageboards.requests.auth;

import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.requests.BoardRequestType;
import okhttp3.HttpUrl;

import java.util.Objects;

/**
 * Adds a fixed query parameter to every request URL.
 */
public class UrlParameterAuthorization implements BoardAuthorizationProvider {

    private final String parameterName;
    private final String parameterValue;

    public UrlParameterAuthorization(String parameterName, String parameterValue) {
        this.parameterName = Objects.requireNonNull(parameterName, "Query parameter name cannot be null");
        this.parameterValue = Objects.requireNonNull(parameterValue, "Query parameter value cannot be null");
    }

    @Override
    public void applyToUrl(Board board, BoardRequestType requestType, HttpUrl.Builder urlBuilder) {
        urlBuilder.addQueryParameter(parameterName, parameterValue);
    }
}
