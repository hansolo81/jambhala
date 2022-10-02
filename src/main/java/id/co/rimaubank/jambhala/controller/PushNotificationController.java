package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.service.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/push-notifications")
@Slf4j
public class PushNotificationController {

    PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @GetMapping("/new")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<PushNotification>> getNewNotifications(@AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<List<PushNotification>>(
                pushNotificationService.getNotifications(jwt.getClaimAsString("custNo")),
                HttpStatus.OK
        );
    }
}
