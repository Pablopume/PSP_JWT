package modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private String email;
    private String password;
    private boolean activado;
    private LocalDateTime fechaActivacion;
    private String codigoActivacion;
    private String rol;
    private String accessToken;
    private String refreshToken;
    private String temporalPassword;
}
