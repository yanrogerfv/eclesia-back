package imdl.eclesia.domain.exception;

import java.util.Collections;
import java.util.List;

public class ApiError {

    private final List<String> error;

    public ApiError(String messageErrors) {
        this.error = Collections.singletonList(messageErrors);
    }

    public ApiError(List<String> errors) {
        this.error = errors;
    }

    public List<String> getError() {
        return error;
    }
}
