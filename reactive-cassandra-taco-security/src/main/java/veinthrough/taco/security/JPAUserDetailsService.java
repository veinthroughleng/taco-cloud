package veinthrough.taco.security;

        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import veinthrough.taco.data.UserRepository;

@Service
@Primary
public class JPAUserDetailsService implements ReactiveUserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public JPAUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepo.findByUsername(username)
                .cast(UserDetails.class)
                .switchIfEmpty(
                        Mono.error(
                                new UsernameNotFoundException("User '" + username + "' not found!")));
    }
}
