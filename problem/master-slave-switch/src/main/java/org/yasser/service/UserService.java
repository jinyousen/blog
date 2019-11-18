package org.yasser.service;

import org.yasser.annotation.TargetDataSource;
import org.yasser.param.UserParam;
import org.yasser.po.User;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */


public interface UserService {

    User insertMaster(UserParam param);


    @TargetDataSource("slave")
    Boolean insertSlave(User user);

}
