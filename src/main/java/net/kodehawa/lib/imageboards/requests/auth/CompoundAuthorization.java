package net.kodehawa.lib.imageboards.requests.auth;

import net.kodehawa.lib.imageboards.boards.Board;
import net.kodehawa.lib.imageboards.requests.BoardRequestType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Combines multiple authorization providers and applies them in sequence.
 */
public class CompoundAuthorization implements BoardAuthorizationProvider {

    private final BoardAuthorizationProvider[] providers;

    private CompoundAuthorization(BoardAuthorizationProvider... providers) {
        this.providers = providers;
    }

    @Override
    public void applyToUrl(Board board, BoardRequestType requestType, okhttp3.HttpUrl.Builder urlBuilder) {
        for (BoardAuthorizationProvider provider : providers) {
            provider.applyToUrl(board, requestType, urlBuilder);
        }
    }

    @Override
    public void applyToRequest(Board board, BoardRequestType requestType, okhttp3.Request.Builder requestBuilder) {
        for (BoardAuthorizationProvider provider : providers) {
            provider.applyToRequest(board, requestType, requestBuilder);
        }
    }

    public static CompoundAuthorization of(@NotNull BoardAuthorizationProvider... providers) {
        Objects.requireNonNull(providers, "providers list cannot be null");
        return new CompoundAuthorization(providers);
    }
}
