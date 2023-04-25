package tech.ada.pagamento.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PagamentoExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail exceptionHandler(RuntimeException exception){
        var problemDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(503), exception.getMessage());
        problemDetails.setTitle("Runtime Exception - Object Not Found");
        return problemDetails;
    }
}
