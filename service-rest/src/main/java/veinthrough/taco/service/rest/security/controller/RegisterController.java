package veinthrough.taco.service.rest.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import veinthrough.taco.model.User;
import veinthrough.taco.service.rest.security.exception.UsernameExistedException;

/**
 * @author zhaoxinguo on 2018/06/05.
 */
@RestController
@RequestMapping("/users")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController extends BaseController {

    /**
     * 注册用户 默认开启白名单
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        User bizUser = userRepository.findByUsername(user.getUsername());
        if(null != bizUser){
            throw new UsernameExistedException("用户已经存在");
        }
        /*user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));*/
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
