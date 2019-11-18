package org.yasser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.yasser.po.User;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */
public interface UserMapper extends BaseMapper<User> {

    @Insert("${sql}")
    void insertSlave(@Param("sql") String sql);
}
