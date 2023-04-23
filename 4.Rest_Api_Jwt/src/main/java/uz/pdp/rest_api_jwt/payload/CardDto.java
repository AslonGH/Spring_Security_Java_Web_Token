package uz.pdp.rest_api_jwt.payload;

import lombok.Data;

@Data
public class CardDto {

    String  username;
    String  number;
    Double  balance;
    String  expiredDate;
    boolean active;
}
