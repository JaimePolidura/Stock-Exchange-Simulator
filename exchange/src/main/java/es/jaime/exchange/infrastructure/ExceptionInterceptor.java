package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.*;
import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;

@ControllerAdvice
public class ExceptionInterceptor {
    private final QueuePublisher queuePublisher;

    public ExceptionInterceptor(QueuePublisher queuePublisher) {
        this.queuePublisher = queuePublisher;
    }

    @SneakyThrows
    @ExceptionHandler
    public void processSupportedExceptions(Throwable throwable) {
         Arrays.stream(SupportedException.values())
                .filter(exception -> sameClass(exception, throwable))
                .findFirst()
                .orElseThrow(() -> throwable);

        queuePublisher.enqueue(() -> buildJSONFromException((DomainException) throwable).toString());
    }

    private JSONObject buildJSONFromException(DomainException exception){
        return new JSONObject(Map.of(
                "error", exception.getMessage(),
                "order", exception.getOrderException()
        ));
    }

    private boolean sameClass(SupportedException supportedException, Throwable throwable){
        return supportedException.getClass().equals(throwable.getClass());
    }

    enum SupportedException {
        TTL_EXPIRED(TtlExpired.class),
        UNPROCESSABLE_TRADE(UnprocessableTrade.class);

        private final Class<? extends DomainException> exceptionClass;

        SupportedException(Class<? extends DomainException> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public Class<? extends DomainException> getExceptionClass() {
            return exceptionClass;
        }
    }
}
