package ml.huytools.ycnanswer.Core.Exceptions;

public class DoNotCircleShapeException extends RuntimeException {
    public DoNotCircleShapeException() {
        super("Không phải đối tượng Drawable.");
    }
}
