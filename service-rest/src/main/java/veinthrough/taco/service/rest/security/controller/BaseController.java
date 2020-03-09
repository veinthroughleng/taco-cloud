package veinthrough.taco.service.rest.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import veinthrough.taco.data.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * 获取用户所拥有的权限列表
     */
    List<String> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            logger.info("权限列表：{}", grantedAuthority.getAuthority());
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }
}
