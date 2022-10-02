package id.co.rimaubank.jambhala.repository;

import id.co.rimaubank.jambhala.entity.PushNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushNotificationRepository extends CrudRepository<PushNotification, Long> {
    List<PushNotification> findByCustNo(String custNo);
}
