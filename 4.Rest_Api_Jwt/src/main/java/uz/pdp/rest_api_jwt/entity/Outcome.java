package uz.pdp.rest_api_jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private  Double  amount;

    private  Date   date;

    private  Double   commissionAmount;

    @ManyToOne
    private  Card     fromCard;

    @ManyToOne
    private  Card     toCard;

}
