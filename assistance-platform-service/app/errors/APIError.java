package errors;

/**
 * Represents an API error by providing an error code and a message.
 */
public class APIError {
    public int code;
    public String message;

    public APIError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
