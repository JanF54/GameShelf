package br.com.gameshelf.exception;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,Object> validacao(MethodArgumentNotValidException e){String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).findFirst().orElse("Dados inválidos.");return Map.of("status",400,"erro","Dados inválidos","mensagem",msg);}
 @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,Object>> geral(Exception e){if(e instanceof org.springframework.web.server.ResponseStatusException r){return ResponseEntity.status(r.getStatusCode()).body(Map.of("status",r.getStatusCode().value(),"erro","Requisição não concluída","mensagem",Objects.toString(r.getReason(),"Não foi possível concluir a operação.")));}return ResponseEntity.status(500).body(Map.of("status",500,"erro","Erro interno","mensagem","Ocorreu um erro inesperado."));}
}
