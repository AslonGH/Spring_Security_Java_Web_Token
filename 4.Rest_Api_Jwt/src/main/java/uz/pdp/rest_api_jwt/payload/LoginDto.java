package uz.pdp.rest_api_jwt.payload;

import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
}
