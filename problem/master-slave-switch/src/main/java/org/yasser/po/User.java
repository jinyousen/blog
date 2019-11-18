package org.yasser.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */

@Data
public class User {

    @TableId(value = "userId", type = IdType.AUTO)
    private Long userId;

    private String name;

    private String phone;

}
