package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.exceptions.DomainException;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.models.messages.ErrorOrderMessage;
import es.jaime.exchange.domain.models.messages.ExecutedOrderMessage;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Map;

@ControllerAdvice
public class ExceptionInterceptor {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;

    public ExceptionInterceptor(MessagePublisher messagePublisher, ExchangeConfiguration configuration) {
        this.messagePublisher = messagePublisher;
        this.configuration = configuration;
    }

    @SneakyThrows
    @ExceptionHandler
    public void processSupportedExceptions(Throwable throwable) {
        ensureExceptionIsSupported(throwable);

        Order errorOrder = ((DomainException) throwable).getOrderException();

        messagePublisher.publish(
                configuration.errorOrdersExchangeName(),
                configuration.errorOrdersQueueName(),
                new ErrorOrderMessage(throwable.getMessage(), errorOrder.getClientId(), errorOrder.getOrderId())
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
        return supportedException.getExceptionClass().equals(throwable.getClass());
    }

    private enum SupportedException {
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
