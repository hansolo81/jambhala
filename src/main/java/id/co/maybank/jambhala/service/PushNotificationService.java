package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.repository.PushNotificationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PushNotificationService {

    PushNotificationRepository pushNotificationRepository;

    public PushNotificationService(PushNotificationRepository pushNotificationRepository) {
        this.pushNotificationRepository = pushNotificationRepository;
    }

    public PushNotification getNewNotification(String pan) {
        PushNotification firstByPan = pushNotificationRepository.findFirstByPan(pan);
        if (firstByPan != null)
            return firstByPan;
        throw new EntityNotFoundException();
    }

    public PushNotification save(PushNotification pushNotification) {
        return pushNotificationRepository.save(pushNotification);
    }
}
