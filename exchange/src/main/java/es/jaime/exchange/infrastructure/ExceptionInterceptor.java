package es.jaime.exchange.infrastructure;

import es.jaime.exchange.ExchangeConfigurationSpring;
import es.jaime.exchange.domain.*;
import es.jaime.exchange.domain.exceptions.DomainException;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;

@ControllerAdvice
public class ExceptionInterceptor {
    private final QueuePublisher queuePublisher;
    private final ExchangeConfiguration configuration;

    public ExceptionInterceptor(QueuePublisher queuePublisher, ExchangeConfiguration configuration) {
        this.queuePublisher = queuePublisher;
        this.configuration = configuration;
    }

    @SneakyThrows
    @ExceptionHandler
    public void processSupportedExceptions(Throwable throwable) {
        ensureExceptionIsSupported(throwable);

        queuePublisher.enqueue(
                configuration.errorOrdersExchangeName(),
                configuration.errorOrdersQueueName(),
                () -> buildJSONFromException((DomainException) throwable).toString()
        );
    }

    @SneakyThrows
    private void ensureExceptionIsSupported(Throwable throwable){
        Arrays.stream(SupportedException.values())
                .filter(exception -> sameClass(exception, throwable))
                .findFirst()
                .orElseThrow(() -> throwable);
    }

    private boolean sameClass(SupportedException supportedException, Throwable throwable){
        return supportedException.getClass().equals(throwable.getClass());
    }

    private JSONObject buildJSONFromException(DomainException exception){
        return new JSONObject(Map.of(
                "error", exception.getMessage(),
                "order", exception.getOrderException()
        ));
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
