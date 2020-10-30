package org.application.resources;

import lombok.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final ApplicationContext applicationContext;

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorDto> handleException(Throwable throwable){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getError(throwable));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Component(value = "error")
    @Scope(scopeName = "prototype")
    private static class ErrorDto {
        private String message;
        private Throwable cause;
    }

    private ErrorDto getError(Throwable throwable) {
        ErrorDto bean = applicationContext.getBean(ErrorDto.class);
        bean.setMessage(throwable.getMessage());
        bean.setCause(throwable);
        return bean;
    }
}
