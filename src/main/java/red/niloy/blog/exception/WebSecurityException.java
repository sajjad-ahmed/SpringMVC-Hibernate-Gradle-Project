package red.niloy.blog.exception;

/**
 * @author sajjad.ahmed
 * @since 10/22/19.
 */
public class WebSecurityException extends RuntimeException {
    public WebSecurityException() {
        super("You are unauthorized.");
    }
}
