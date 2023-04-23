package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.rest_api_jwt.entity.Card;
import uz.pdp.rest_api_jwt.entity.Outcome;
import uz.pdp.rest_api_jwt.payload.OutcomeDto;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.repository.CardRepository;
import uz.pdp.rest_api_jwt.repository.OutcomeRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {

      @Autowired
      OutcomeRepository outcomeRepository;

      @Autowired
      CardRepository cardRepository;

      @Autowired
      MyAuthService myAuthService;


      public Result addOutcome(OutcomeDto outcomeDto){

        Outcome outcome=new Outcome();

        outcome.setCommissionAmount(outcomeDto.getCommissionAmount());
        outcome.setAmount(outcomeDto.getAmount());

//        outcome.setDate(outcomeDto.getDate());
          DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
          try {
              Date date = dateFormat.parse(outcomeDto.getDate());
              java.sql.Date sqlDate=new java.sql.Date(date.getTime());
              outcome.setDate(sqlDate);
          } catch (Exception e) {
              return new Result("enter  date(dd.MM.yyyy)",false);
          }

        Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFromCardId());
        if (!optionalFromCard.isPresent())
        return new Result("Card not found",false);


        Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalToCard.isPresent())
        return new Result("Card not found",false);
        outcome.setToCard(optionalToCard.get());

        Card card = optionalFromCard.get();
        if (card.getBalance()>=(outcomeDto.getAmount()+outcomeDto.getCommissionAmount())) {
            UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
            if (!userDetails.isEnabled())
            return new Result("User topilmadi",false);

            outcome.setFromCard(card);
            card.setBalance(card.getBalance()-(outcomeDto.getAmount()+outcomeDto.getCommissionAmount()));
            outcomeRepository.save(outcome);
            cardRepository.save(card);

        }else {
              return new Result("Money not enough", false);
        }
        return new Result("Transfer accomplished",true);
      }

      public List<Outcome> getOutcome(){
            return outcomeRepository.findAll();
      }

      public Outcome getOutcomeById(Integer id){
            Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
            return optionalOutcome.orElseGet(Outcome::new);
      }

      public Result editOutcomeById(OutcomeDto outcomeDto,Integer id){

            Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
            if (!optionalOutcome.isPresent())
                  return new Result("Outcome not found",false);

            Outcome outcome = optionalOutcome.get();
            outcome.setCommissionAmount(outcomeDto.getCommissionAmount());
            outcome.setAmount(outcomeDto.getAmount());


          DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
          try {
              Date date = dateFormat.parse(outcomeDto.getDate());
              java.sql.Date sqlDate=new java.sql.Date(date.getTime());
              outcome.setDate(sqlDate);
          } catch (Exception e) {
              return new Result("enter  date(dd.MM.yyyy)",false);
          }


            Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFromCardId());
            if (!optionalFromCard.isPresent())
                  return new Result("FromCardId not found",false);

            Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getToCardId());
            if (!optionalToCard.isPresent())
                  return new Result("ToCardId not found",false);
            outcome.setToCard(optionalToCard.get());

            Card card = optionalFromCard.get();
            if (card.getBalance()>(outcomeDto.getAmount()+outcomeDto.getCommissionAmount())) {
              UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
                if (!userDetails.isEnabled())
                    return new Result("User topilmadi",false);
                  outcome.setFromCard(card);
                  card.setBalance(card.getBalance()-(outcomeDto.getAmount()+outcomeDto.getCommissionAmount()));
                  cardRepository.save(card);
                  outcomeRepository.save(outcome);
            }else {
                  return new Result("Money not enough", false);
            }
            return new Result("Transfer edited",true);
      }


      public Result deleteOutcome(Integer id){
            try {
                  outcomeRepository.deleteById(id);
                  return new Result("Outcome deleted",true);
            }catch (Exception e){
                  return new Result("Outcome not deleted",false);
            }

      }
}
