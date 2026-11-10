package br.com.gameshelf.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){this.key=Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secret));this.expiration=expiration;}
 public String gerar(String email){Date agora=new Date();return Jwts.builder().subject(email).issuedAt(agora).expiration(new Date(agora.getTime()+expiration)).signWith(key).compact();}
 public String extrairEmail(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean valido(String token,String email){try{return email.equals(extrairEmail(token));}catch(JwtException|IllegalArgumentException e){return false;}}
 public long getExpiration(){return expiration;}
}
