package teama.service;

/**
 * Error Response class for JSON error message
 * @author MarcinPultyn
 */
public class ErrorResponse {
    public ErrorResponse(String errorMessage)
    {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
    
    private final String error;
}
