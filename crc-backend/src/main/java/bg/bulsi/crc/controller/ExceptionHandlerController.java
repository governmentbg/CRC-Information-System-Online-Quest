package bg.bulsi.crc.controller;

import bg.bulsi.crc.api.ApiException;
import bg.bulsi.crc.api.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice("bg.bulsi.crc.api")
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            ApiException.class
    })
    @ResponseBody
    public ResponseEntity<ApiResponseMessage> handle(ApiException ex) {

        log.error(ex.toString());

        ApiResponseMessage responseMessage = new ApiResponseMessage(ex.getCode(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseMessage);
    }

    @ExceptionHandler({
            BadCredentialsException.class
    })
    @ResponseBody
    public ResponseEntity<ApiResponseMessage> handleUnauthorized(BadCredentialsException ex) {
        ApiResponseMessage responseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR, ex.getMessage());

        return ResponseEntity

                .status(HttpStatus.UNAUTHORIZED)
                .body(responseMessage);
    }

}
