package guru.springframework.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modified by Pierrot on 2025-11-07.
 */
@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity<String> handleJPAViolations(TransactionSystemException exception){

        if (exception.getCause().getCause() instanceof ConstraintViolationException cvex) {
            cvex = (ConstraintViolationException) exception.getCause().getCause();

            StringBuilder sb = new StringBuilder();

            cvex.getConstraintViolations().forEach(
                    violation ->
                            sb.append(violation.getPropertyPath().toString())
                                    .append(" : ")
                                    .append(violation.getMessage())
                                    .append("\n"));

            return ResponseEntity.badRequest().body(sb.toString());

        }

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<Map<String, String>>> handleBindErrors(MethodArgumentNotValidException exception){

        List<Map<String, String>> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }
}
