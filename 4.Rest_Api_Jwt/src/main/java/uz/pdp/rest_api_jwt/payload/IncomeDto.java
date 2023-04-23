package uz.pdp.rest_api_jwt.payload;

import lombok.Data;

import java.util.Date;

@Data
public class IncomeDto {

    Integer fromCardId;
    Integer toCardId;
    Double  amount;
    String  date;

}
