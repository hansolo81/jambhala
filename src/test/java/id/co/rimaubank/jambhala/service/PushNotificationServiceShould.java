package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.event.TransactionEvent;
import id.co.rimaubank.jambhala.repository.PushNotificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PushNotificationServiceShould {

    PushNotificationService pushNotificationService;

    @Mock
    PushNotificationRepository pushNotificationRepository;

    @Before
    public void setUp() {
        pushNotificationService = new PushNotificationService(pushNotificationRepository);
    }

    @Test
    public void returnPushNotificationListForCustNo() {
        String custNo = "0000000001";

        given(pushNotificationRepository.findByCustNo(custNo))
                .willReturn(List.of(
                        PushNotification.builder()
                                .custNo(custNo)
                                .message("Your fund transfer of 10000.00 to padme is successful")
                                .read(false)
                                .build()
                ));

        List<PushNotification> notifications = pushNotificationService.getNotifications(custNo);
        assertThat(notifications).isNotEmpty();

        for (PushNotification noti : notifications) {
            assertThat(custNo).isEqualTo(noti.getCustNo());
        }
    }

    @Test
    public void createNewNotification() {
        PushNotification expected = PushNotification.builder()
                .id(1L)
                .custNo("0000000001")
                .message("Test message").build();

        given(pushNotificationRepository.save(expected)).willReturn(expected);

        PushNotification actual = pushNotificationService.save(expected);
        assertThat(actual).isSameAs(expected);

    }

    @Test
    public void processTransactionEvent() {

        String custNo = "0000000001";
        String payeeName = "palpatine";
        BigDecimal amount = new BigDecimal("10000.00");
        PushNotification expected = PushNotification.builder()
                .id(1L)
                .custNo(custNo)
                .message(String.format("Your transfer of %.2f to %s is successful.", amount, payeeName)).build();

        TransactionEvent transactionEvent = new TransactionEvent(new Object(),
                MonetaryTransaction.builder()
                        .custNo(custNo)
                        .amount(amount)
                        .destinationAccount("100000000066")
                        .sourceAccount("100000000099")
                        .payeeName(payeeName)
                        .transactionType("third party transfer")
                        .transactionDate(new Date())
                        .status("successful")
                        .build());
        given(pushNotificationRepository.save(expected)).willReturn(expected);

        given(pushNotificationRepository.findByCustNo(custNo))
                .willReturn(List.of(expected));

        pushNotificationService.processTransactionEvent(transactionEvent);
        PushNotification unreadNotification = pushNotificationService.getNotifications(custNo).get(0);
        assertThat(unreadNotification).isSameAs(expected);

    }
}