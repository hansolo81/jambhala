package id.co.rimaubank.jambhala.event;

import id.co.rimaubank.jambhala.model.MonetaryTransaction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TransactionSuccessfulEventPublisher {

    ApplicationEventPublisher applicationEventPublisher;

    public TransactionSuccessfulEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final MonetaryTransaction monetaryTransaction) {
        TransactionEvent event = new TransactionEvent(this, monetaryTransaction);
        applicationEventPublisher.publishEvent(event);
    }
}
