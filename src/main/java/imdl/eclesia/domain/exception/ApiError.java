package imdl.eclesia.domain.exception;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {

    private final List<String> error;

    public ApiError(String messageErrors) {
        this.error = List.of(messageErrors);
    }
}
