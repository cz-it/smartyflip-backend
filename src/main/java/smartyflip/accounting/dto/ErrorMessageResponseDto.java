package smartyflip.accounting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorMessageResponseDto {

    private String message;

    public ErrorMessageResponseDto(String message) {
        this.message = message;
        this.errors = new HashMap<>();
    }

    Map<String, String> errors;

    public void addError(String error, String message) {
        this.errors.put(error, message);
    }

}
