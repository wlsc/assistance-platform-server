package errors;

/**
 * A exception that is bound to an API error.
 */
public class APIErrorException extends Exception {
    private static final long serialVersionUID = -4106834374452468004L;
    private APIError apiError;

    public APIErrorException(APIError error) {
        this.apiError = error;
    }

    public APIError getError() {
        return this.apiError;
    }
}