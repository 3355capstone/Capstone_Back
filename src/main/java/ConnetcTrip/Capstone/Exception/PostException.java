package ConnetcTrip.Capstone.Exception;

public class PostException extends BaseException {
    private BaseExceptionType baseExceptionType;

    public PostException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
