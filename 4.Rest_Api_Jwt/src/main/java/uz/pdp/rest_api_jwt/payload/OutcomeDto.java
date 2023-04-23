package uz.pdp.rest_api_jwt.payload;
import lombok.Data;


@Data
public class OutcomeDto {

     Double  amount;

     String  date;

     Double  commissionAmount;

     Integer fromCardId;

     Integer toCardId;
}
