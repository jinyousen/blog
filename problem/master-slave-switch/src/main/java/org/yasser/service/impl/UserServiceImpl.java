package org.yasser.service.impl;

import org.springframework.stereotype.Service;
import org.yasser.mapper.UserMapper;
import org.yasser.param.UserParam;
import org.yasser.po.User;
import org.yasser.service.UserService;
import org.yasser.util.DozerUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    private final static String sqlFormat = "'%s'";

    @Resource
    private UserMapper userMapper;

    public User insertMaster(UserParam param) {
        User user = DozerUtils.convert(param, User.class);
        userMapper.insert(user);
        return user;
    }

    public Boolean insertSlave(User user) {
        try {
            String sql = convertSql(user, "user");
            userMapper.insertSlave(sql);
        } catch (IllegalAccessException e) {

        }
        return true;
    }


    private String convertSql(Object o, String tableName) throws IllegalAccessException {
        StringBuffer stringBuffer = new StringBuffer("insert into ");
        StringBuffer stringBufferValue = new StringBuffer("(");
        stringBuffer.append(tableName).append("(");
        Field[] fields = DozerUtils.getFields(o.getClass());
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            if (Objects.isNull(f.get(o))) continue;
            stringBuffer.append(f.getName());
            stringBufferValue.append(String.format(sqlFormat, f.get(o)));
            if (i < fields.length - 1) {
                stringBuffer.append(",");
                stringBufferValue.append(",");
            }
        }
        stringBuffer.append(") values ");
        stringBufferValue.append(")");
        stringBuffer.append(stringBufferValue);
        return stringBuffer.toString();
    }

}
