package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.repository.PushNotificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}