package veinthrough.taco.service.rest.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * 自定义身份认证验证组件
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 认证逻辑
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (null != userDetails) {
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                // 这里设置权限和角色
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN"));
                authorities.add( new GrantedAuthorityImpl("AUTH_WRITE"));
                // 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
                return new UsernamePasswordAuthenticationToken(name, password, authorities);
            } else {
                throw new BadCredentialsException("密码错误");
            }
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
