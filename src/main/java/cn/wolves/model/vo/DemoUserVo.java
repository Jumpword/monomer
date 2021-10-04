package cn.wolves.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jumping
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("demo用户实体")
public class DemoUserVo {

    /**用户名*/
    @ApiModelProperty("用户名")
    private String userName;

    /**用户账户*/
    @ApiModelProperty("用户账户")
    private String userCode;

    /**创建时间 必建*/
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**更新时间 必建*/
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
