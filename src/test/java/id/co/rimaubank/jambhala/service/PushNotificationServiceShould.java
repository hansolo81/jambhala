package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.PushNotification;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class PushNotificationServiceShould {

    PushNotificationService pushNotificationService;
    @Before
    public void setUp()  {
        pushNotificationService = new PushNotificationService();
    }

    @Test
    public void returnPushNotificationListForCustNo() {
        String custNo = "0000000001";
        List<PushNotification> notifications = pushNotificationService.getNotifications(custNo);
        assertThat(notifications).isNotEmpty();

        for (PushNotification noti : notifications) {
            assertThat(custNo).isEqualTo(noti.getCustNo());
        }
    }
}