package cn.wolves.model.po;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Jumping
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BasePo<T extends Model<?>> extends Model<T> {


    /**创建人 必建*/
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /**创建时间 必建*/
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


    /**更新人 必建*/
    @TableField(value ="update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**更新时间 必建*/
    @TableField(value ="update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}
