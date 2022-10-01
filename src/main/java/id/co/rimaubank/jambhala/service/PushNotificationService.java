package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.PushNotification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationService {

    public List<PushNotification> getNotifications(String custNo) {
        return List.of(
                PushNotification.builder()
                        .custNo(custNo)
                        .message("Your fund transfer of 10000.00 to padme is successful")
                        .read(false)
                        .build(),
                PushNotification.builder()
                        .custNo(custNo)
                        .message("Welcome to Rimaubank")
                        .read(false)
                        .build()
        );
    }
}
