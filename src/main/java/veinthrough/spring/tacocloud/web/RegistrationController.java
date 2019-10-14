package veinthrough.spring.tacocloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.spring.tacocloud.data.UserRepository;
import veinthrough.spring.tacocloud.data.model.User;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String registerForm() {
        return "/registration";
    }

    @PostMapping
    public String processRegistration(User user) {
        user.encryptPassword(encoder);
        userRepo.save(user);
        return "redirect:/login";
    }
}
