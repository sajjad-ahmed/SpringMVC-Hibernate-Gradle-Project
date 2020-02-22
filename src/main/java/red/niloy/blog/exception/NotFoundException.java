package red.niloy.blog.exception;

/**
 * @author sajjad.ahmed
 * @since 10/22/19.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg.concat(" not found."));
    }
}
