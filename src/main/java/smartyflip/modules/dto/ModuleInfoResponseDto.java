package smartyflip.modules.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleInfoResponseDto {
    Integer moduleId;
    String moduleName;
    LocalDateTime dateCreated;
    @Setter
    Long stackId;
    @Setter
    String stackName;
    @JsonProperty("cardsCount")
    Integer cardsAmount;
    @JsonProperty("username")
    String userName;
}
