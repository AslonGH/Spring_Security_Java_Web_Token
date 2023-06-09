package uz.pdp.rest_api_jwt.security;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    // BU FIELD/ static Method (Main) ichiga chaqirilsa, static qilamiz
    static long expireTime = 36_000_000;  // millisecond - 100 soatga teng  360_000_000;
    static String kalitsuz = "buTokenniMaxfiySoziCLIENTBILMASIN!!!";

// CLIENT  LOGIN-PAROL  B-N KIRGANDA, CLIENTGA TOKEN NI GENERATION QILIB BERAMIZ,KEYINGI SAFAR TOKEN BILAN BIRGA KIRADI;
// SHUNDA TOKEN ICHIDAN PARSE BILAN, TARORLANMAS USER YOKI ID OLAMIZ.VA SHU username ni basadan olib,TOKENGA SOLIB CLIENTGA QAYTARAMIZ

    // TOKEN NI GENERATION QILISH u-n parametr listga username yoki id ni ishlatamiz.
    public static String generateToken(String username) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime); //AYNI PAYTGA 10SOATNI QÖSHDIK
        String token = Jwts
                .builder()  // TOKE QURISH
                .setSubject(username)  // payload dagi username
                .setIssuedAt(new Date()) // TOKEN BERILGAN VAQT
                .setExpiration(expireDate)  // Token ning muddati
                .signWith(SignatureAlgorithm.HS512, kalitsuz)  //Tokenni shifirlash JUDA MUHIM
                .compact();
        return token;
    }

    // BROWSER 2-MARTA REQUEST YUBORGANDA TOKEN ICHIDA username ni JÖNATADI.ENDI SHU TOKENNI PARSE QILAMIZ HAMMA TALABGA
    // JAVOB BERSA User ni systemaga kirishiga ruxsat beramiz BUNING UCHUN TOKENNI VALIDATION DAN ÖTKAZAMIZ
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(kalitsuz)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired Jwt token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // TOKEN NI PARSE QILIB ICHIDAN USERNAME NI OLISH
    public String getUsernameFromToken(String token) {

        String username = Jwts
                .parser()
                .setSigningKey(kalitsuz).parseClaimsJws(token)  //kalitsuz orqali tokenni parse qilib,
                .getBody().getSubject();  // token dagi payload dan Subject ni, va undan username ni olamiz
        return username;
    }
}