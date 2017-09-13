package net.kodehawa.lib.imageboards.entities;

public class QueryFailedException extends RuntimeException {
    private final int code;
    private final String url;

    public QueryFailedException(int code, String url) {
        super("Failed to query, " + url + ".");
        this.code = code;
        this.url = url;
    }

    public QueryFailedException(int code, String url, Throwable e) {
        super("Failed to query " + url + ".", e);
        this.code = code;
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }
}
