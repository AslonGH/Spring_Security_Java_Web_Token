package uz.pdp.rest_api_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Card;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.repository.CardRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {


      @Autowired
      CardRepository  cardRepository;

      @Autowired
      MyAuthService myAuthService;

      public Result addCard(Card card){

            boolean exists = cardRepository.existsByUsername(card.getUsername());
            if (exists)
             return new Result("Such Card exist",false);
             cardRepository.save(card);
            return new Result("Card added",true);
      }


      public Card getCardById(Integer id){
            Optional<Card> optionalCard = cardRepository.findById(id);
            if (!optionalCard.isPresent())
            return new Card();
            Card card = optionalCard.get();
            UserDetails userDetails = myAuthService.loadUserByUsername(card.getUsername());
            if (userDetails==null)
            return null;
            return optionalCard.orElse(null);
      }}



/*

      public Result editCardById(Card card,Integer id){

            boolean exists = cardRepository.existsByUsernameAndIdNot(card.getUsername(),id);
            if (exists)
            return new Result("Such Card exist",false);

            Optional<Card> optionalCard = cardRepository.findById(id);
            if (!optionalCard.isPresent())
            return new Result("Card not found",false);
            Card card1 = optionalCard.get();
            card1.setBalance(card.getBalance());
            card1.setActive(card.isActive());
            card1.setNumber(card.getNumber());
            card1.setExpiredDate(card.getExpiredDate());
            card1.setUsername(card.getUsername());
            cardRepository.save(card);
            return new Result("Card edited",true);
      }


      public Result deleteCard(Integer id){
            try {
                  cardRepository.deleteById(id);
                  return new Result("Card deleted",true);
            }catch (Exception e){
                  return new Result("Card not deleted",false);
            }

      }
      public List<Card> getCards(){
            return cardRepository.findAll();
      }
*/
