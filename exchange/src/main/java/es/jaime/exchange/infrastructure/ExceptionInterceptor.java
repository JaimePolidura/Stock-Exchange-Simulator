package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.*;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionInterceptor {
    private final QueuePublisher queuePublisher;

    public ExceptionInterceptor(QueuePublisher queuePublisher) {
        this.queuePublisher = queuePublisher;
    }

    @SneakyThrows
    @ExceptionHandler
    public void processSupportedExceptions(Throwable throwable) {
        SupportedException supportedException = Arrays.stream(SupportedException.values())
                .filter(exception -> sameClass(exception, throwable))
                .findFirst()
                .orElseThrow(() -> throwable);

        queuePublisher.enqueue(() -> "error: " + throwable.getMessage());
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
