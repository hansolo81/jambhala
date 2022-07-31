package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.repository.PushNotificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PushNotificationServiceShould {

    PushNotificationService pushNotificationService;

    @Mock
    PushNotificationRepository notificationRepository;

    String pan;

    @Before
    public void initialize() {
        pan = "1600000000000001";
        pushNotificationService = new PushNotificationService(notificationRepository);
    }

    @Test
    public void returnNewNotificationIfFound() {
        String expectedMessage = "Test message";
        given(notificationRepository.findFirstByPan(pan))
                .willReturn(PushNotification.builder()
                        .pan(pan)
                        .id(1L)
                        .message(expectedMessage).build());

        PushNotification actual = pushNotificationService.getNewNotification(pan);
        assertThat(actual.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    public void throwExceptionIfNoNewNotification() {
        given(notificationRepository.findFirstByPan(pan))
                .willReturn(null);
       assertThrows(EntityNotFoundException.class, () -> pushNotificationService.getNewNotification(pan));
    }

    @Test
    public void createNewPushNotification() {
        PushNotification expected = PushNotification.builder()
                .id(1L)
                .pan("16000000000001")
                .message("Test message").build() ;
        given(notificationRepository.save(expected)).willReturn(expected) ;

        PushNotification actual = pushNotificationService.save(expected);
        assertThat(actual).isSameAs(expected);
    }
}