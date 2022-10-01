package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.repository.PushNotificationRepository;
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
}
