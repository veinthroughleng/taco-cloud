package veinthrough.taco.service.rest.security.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import veinthrough.taco.property.SecurityProps;
import veinthrough.taco.service.rest.security.UserInput;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@Api(value = "登录管理", description = "登录管理")
public class LoginController extends BaseController {
    private SecurityProps securityProps;
    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(SecurityProps securityProps, AuthenticationManager authenticationManager) {
        this.securityProps = securityProps;
        this.authenticationManager = authenticationManager;
    }

    @ApiOperation(value = "自定义登录")
    // login can not be
    @PostMapping(path = "/signin")
    public void login(@RequestBody UserInput user, HttpServletResponse response) {
        Authentication authenticated = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword(),
                        // right now there are no authorities in User.class
                        new ArrayList<>())
        );
        if (authenticated != null) {

            Date now = new Date();
            Calendar expirationTime = Calendar.getInstance();
            expirationTime.add(Calendar.MINUTE, securityProps.getTokenExpirationMinutes().intValue());// 1 hour
            String token = Jwts.builder()
                    .setSubject(authenticated.getPrincipal().toString())
                    .setIssuedAt(now)//签发时间
                    .claim("authorities",
                            authenticated.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                    .setExpiration(expirationTime.getTime())//过期时间
                    .signWith(SignatureAlgorithm.HS512, securityProps.getSigningKey()) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
        }
    }
}
