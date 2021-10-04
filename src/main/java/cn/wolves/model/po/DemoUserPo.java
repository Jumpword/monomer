package cn.wolves.model.po;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jumping
 *
 * <p>
 *     表规范示例
 * </p>
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("demo_user")
public class DemoUserPo extends BasePo<DemoUserPo> {


    /**主键 必建*/
    @TableId(type = IdType.AUTO)
    private Long id;

    /**用户名*/
    @TableField("user_name")
    private String userName;

    /**用户账户*/
    @TableField("user_code")
    private String userCode;

    /**密码*/
    private String password;

    /**乐观锁*/
    @Version
    @TableField(value = "version",fill = FieldFill.INSERT)
    private Integer version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
