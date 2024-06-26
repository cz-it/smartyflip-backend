package smartyflip.stacks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackInfoResponseDto {

    Long stackId;

    String stackName;

    @JsonProperty("modulesCount")
    Integer modulesAmount;

}
