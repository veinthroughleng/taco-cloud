package veinthrough.taco.service.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import veinthrough.taco.data.UserRepository;
import veinthrough.taco.model.User;

import java.util.Optional;

@Service
@Primary
public class JPAUserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public JPAUserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User '" + username + "' not found!");
    }
}
