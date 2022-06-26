package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.model.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
    Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LOGGER.info(request.toString());
        if ("anakin".equals(request.getUserName()) &&
                "ihateyou".equals(request.getPassword()))
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        return new ResponseEntity<>("Failed", HttpStatus.UNAUTHORIZED);
    }

}
