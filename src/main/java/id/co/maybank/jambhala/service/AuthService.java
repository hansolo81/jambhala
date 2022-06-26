package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.model.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean isAuthenticated(LoginRequest request) {
        return "anakin".equals(request.getUserName()) &&
                "ihateyou".equals(request.getPassword());
    }
}
