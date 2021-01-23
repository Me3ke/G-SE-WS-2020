package de.techfak.se.multiplayer.server.response_body;

/**
 * A response object sent by the server.
 */
public class ResponseObject {

    private boolean success;

    private String message;

    public ResponseObject() {
        super();
    }

    public ResponseObject(final boolean success, final String message) {
        this.success = success;
        this.message = message;
    }

    public static ResponseObject successful(final String message) {
        return new ResponseObject(true, message);
    }

    public static ResponseObject failure(final String message) {
        return new ResponseObject(false, message);
    }

    public static ResponseObject failure(final Exception exception) {
        return new ResponseObject(false, exception.getMessage());
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
