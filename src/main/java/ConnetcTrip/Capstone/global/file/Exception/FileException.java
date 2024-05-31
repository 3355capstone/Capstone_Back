package ConnetcTrip.Capstone.global.file.Exception;

import ConnetcTrip.Capstone.Exception.BaseException;
import ConnetcTrip.Capstone.Exception.BaseExceptionType;

public class FileException extends BaseException {

    private final BaseExceptionType exceptionType;

    public FileException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
