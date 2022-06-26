package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.model.User;
import id.co.maybank.jambhala.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAccountsForUser(@PathVariable String userName) {
        User user = userService.getUser(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
