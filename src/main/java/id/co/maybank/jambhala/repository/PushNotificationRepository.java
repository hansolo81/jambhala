package id.co.maybank.jambhala.repository;

import id.co.maybank.jambhala.entity.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository extends CrudRepository<PushNotification, Long> {
    PushNotification findFirstByPan(String pan);
}
