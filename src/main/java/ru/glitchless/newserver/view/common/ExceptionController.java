package ru.glitchless.newserver.view.common;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.glitchless.newserver.data.model.Message;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleAllError(HttpServletRequest req, Exception ex) {
        final ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            String reason = ex.getMessage();
            if (reason == null) {
                reason = responseStatus.reason();
            }
            return ResponseEntity.status(responseStatus.code()).body(new Message<>(false, reason));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message<>(false, ex.getMessage()));
    }
}
