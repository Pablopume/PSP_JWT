package dao;

import jakarta.security.enterprise.credential.Credential;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCredentials implements Credential {
    private String accessToken;
    private String refreshToken;



}
