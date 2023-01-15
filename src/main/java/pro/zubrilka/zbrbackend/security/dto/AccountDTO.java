package pro.zubrilka.zbrbackend.security.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pro.zubrilka.zbrbackend.security.validator.annotation.ValidEmail;

@Getter
@Setter
public class AccountDTO {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
