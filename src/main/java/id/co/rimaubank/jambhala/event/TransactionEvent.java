package id.co.rimaubank.jambhala.event;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionEvent extends ApplicationEvent {
    private final MonetaryTransaction monetaryTransaction;

    public TransactionEvent(Object source, MonetaryTransaction monetaryTransaction) {
        super(source);
        this.monetaryTransaction = monetaryTransaction;
    }
}
