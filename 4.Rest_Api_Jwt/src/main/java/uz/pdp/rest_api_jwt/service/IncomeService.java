package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Card;
import uz.pdp.rest_api_jwt.entity.Income;
import uz.pdp.rest_api_jwt.payload.IncomeDto;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.repository.CardRepository;
import uz.pdp.rest_api_jwt.repository.IncomeRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {


      @Autowired
      IncomeRepository  incomeRepository;

      @Autowired
      CardRepository  cardRepository;

      public Result addIncome(IncomeDto incomeDto){

            Income income=new Income();
            income.setAmount(incomeDto.getAmount());

            DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
            try {
                  Date date = dateFormat.parse(incomeDto.getDate());
                  java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                  income.setDate(sqlDate);
            } catch (Exception e) {
                return new Result("enter  date(dd.MM.yyyy)",false);
            }


            Optional<Card> optionalFromCard = cardRepository.findById(incomeDto.getFromCardId());
            if (!optionalFromCard.isPresent())
            return new Result("FromCardId not found",false);

            Card cardFrom = optionalFromCard.get();
            income.setFromCard(cardFrom);

            Optional<Card> optionalToCard = cardRepository.findById(incomeDto.getToCardId());
            if (!optionalToCard.isPresent())
            return new Result("toCardId not found",false);
            income.setToCard(optionalToCard.get());

            Card toCard = optionalToCard.get();
            Double balanceToCard = toCard.getBalance();

            toCard.setBalance(balanceToCard+incomeDto.getAmount());

            cardRepository.save(toCard);
            incomeRepository.save(income);
            return new Result("Transfer accomplished",true);

      }

      public List<Income> getIncomes(){
            return incomeRepository.findAll();
      }


      public Income getIncomeById(Integer id){
            Optional<Income> optionalIncome = incomeRepository.findById(id);
            return optionalIncome.orElse(null);
      }


      public Result editIncomeById(IncomeDto incomeDto,Integer id){

            Optional<Income> optionalIncome = incomeRepository.findById(id);
            if (!optionalIncome.isPresent())
            return new Result("Income not found",false);

            Income income = optionalIncome.get();
            income.setAmount(incomeDto.getAmount());

            DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");
            try {
                  Date date = dateFormat.parse(incomeDto.getDate());
                  java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                  income.setDate(sqlDate);
            } catch (Exception e) {
                  return new Result("enter  date(dd.MM.yyyy)",false);
            }


            Optional<Card> optionalFromCard = cardRepository.findById(incomeDto.getFromCardId());
            if (!optionalFromCard.isPresent())
                  return new Result("FromCardId not found",false);

            Card cardFrom = optionalFromCard.get();
            income.setFromCard(cardFrom);

            Optional<Card> optionalToCard = cardRepository.findById(incomeDto.getToCardId());
            if (!optionalToCard.isPresent())
                  return new Result("toCardId not found",false);

            income.setToCard(optionalToCard.get());

            Card toCard = optionalToCard.get();
            Double balanceToCard = toCard.getBalance();
            toCard.setBalance(balanceToCard+incomeDto.getAmount());

            cardFrom.setBalance(cardFrom.getBalance()-(incomeDto.getAmount()));
            cardRepository.save(cardFrom);

            incomeRepository.save(income);
            cardRepository.save(toCard);
            return new Result("Transfer edited",true);

      }


      public Result deleteIncome(Integer id){
            try {
                  incomeRepository.deleteById(id);
                  return new Result("Income deleted",true);
            }catch (Exception e){
                  return new Result("Income not deleted",false);
            }

      }
}




//    cardFrom.setBalance(cardFrom.getBalance()-(incomeDto.getAmount()));
//    cardRepository.save(cardFrom);