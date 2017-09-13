package net.kodehawa.lib.imageboards.entities;

public class QueryParseException extends RuntimeException {

    private final String response;

    public QueryParseException(String response, Throwable e) {
        super("Failed to parse response from an imageboard.", e);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

}
