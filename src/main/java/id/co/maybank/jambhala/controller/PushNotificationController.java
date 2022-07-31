package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/v1/push-notifications")
public class PushNotificationController {

    PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @GetMapping(value = "/new")
    public ResponseEntity<PushNotification> getNewNotification(@AuthenticationPrincipal Jwt user) {
        String pan = user.getClaimAsString("pan");
        try {
            PushNotification newNotification = pushNotificationService.getNewNotification(pan);
            return new ResponseEntity<>(
                    newNotification,
                    HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
