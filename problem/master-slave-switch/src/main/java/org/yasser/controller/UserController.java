package org.yasser.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasser.param.UserParam;
import org.yasser.po.User;
import org.yasser.service.UserService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */


@Api(tags = "用户接口", value = "用户接口描述")
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("saveUser")
    public Boolean saveUser(@RequestBody UserParam param) {

        User user = userService.insertMaster(param);
        if (Objects.nonNull(user)) {
            userService.insertSlave(user);
        }
        return true;
    }
}
