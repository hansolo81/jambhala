package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.event.TransactionEvent;
import id.co.rimaubank.jambhala.repository.PushNotificationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationService {

    PushNotificationRepository pushNotificationRepository;

    public PushNotificationService(PushNotificationRepository pushNotificationRepository) {
        this.pushNotificationRepository = pushNotificationRepository;
    }

    public List<PushNotification> getNotifications(String custNo) {
        return pushNotificationRepository.findByCustNo(custNo);
    }

    public PushNotification save(PushNotification pushNotification) {
        return pushNotificationRepository.save(pushNotification);
    }

    @EventListener
    public void processTransactionEvent(final TransactionEvent transactionEvent) {
        MonetaryTransaction monetaryTransaction = transactionEvent.getMonetaryTransaction();
        save(PushNotification.builder()
                .custNo(monetaryTransaction.getCustNo())
                .message(String.format("Your %s of %.2f to %s is successful.", monetaryTransaction.getTransactionType(), monetaryTransaction.getAmount(), monetaryTransaction.getPayeeName()))
                .read(false).build());
    }
}
