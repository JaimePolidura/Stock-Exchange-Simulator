package es.jaime.gateway._shared.infrastrocture;

import com.google.common.collect.ImmutableMap;
import es.jaime.gateway._shared.domain.DomainException;
import es.jaime.gateway._shared.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class ExceptionInterceptor {
    @ExceptionHandler
    public ResponseEntity<Map<Object, Object>> processSupportedExceptions(Throwable throwable) {
        Optional<ResponseEntity<Map<Object, Object>>> supportedException = Arrays.stream(SupportedException.values())
                .filter(supportedEx -> hasSameClass(supportedEx, throwable))
                .map(supportedExcep -> createResponseFromException(supportedExcep, throwable.getMessage()))
                .findFirst();

        if(!supportedException.isPresent()) throwable.printStackTrace();

        return supportedException.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private Boolean hasSameClass(SupportedException exception, Throwable throwable) {
        return throwable.getClass().equals(exception.getExceptionClass());
    }

    private ResponseEntity<Map<Object, Object>> createResponseFromException(SupportedException exception, String message){
        return new ResponseEntity<>(ImmutableMap.builder()
                    .put("status", exception.getStatus().value())
                    .put("error", message)
                    .build(),
                exception.getStatus());
    }

    enum SupportedException {
        ALREADY_EXISTS(AlreadyExists.class, HttpStatus.CONFLICT),
        ALREADY_OWNER(AlreadyOwner.class, HttpStatus.BAD_REQUEST),
        CANNOT_BE_NULL(CannotBeNull.class, HttpStatus.BAD_REQUEST),
        CANNOT_BE_YOURSELF(CannotBeYourself.class, HttpStatus.BAD_REQUEST),
        ILLEGAL_LENGTH(IllegalLength.class, HttpStatus.BAD_REQUEST),
        ILLEGAL_QUANTITY(IllegalQuantity.class, HttpStatus.BAD_REQUEST),
        ILLEGAL_TYPE(IllegalType.class, HttpStatus.BAD_REQUEST),
        ILLEGAL_STATE(IllegalState.class, HttpStatus.BAD_REQUEST),
        NOT_THE_OWNER(NotTheOwner.class, HttpStatus.UNAUTHORIZED),
        RESOURCE_NOT_FOUND(ResourceNotFound.class, HttpStatus.NOT_FOUND),
        NOT_VALID(NotValid.class, HttpStatus.BAD_REQUEST);

        private final Class<? extends DomainException> exceptionClass;
        private final HttpStatus status;

        SupportedException(Class<? extends DomainException> exception, HttpStatus status) {
            this.exceptionClass = exception;
            this.status = status;
        }

        public Class<? extends DomainException> getExceptionClass() {
            return this.exceptionClass;
        }

        public HttpStatus getStatus() {
            return this.status;
        }
    }
}
