package cn.wolves.common.handler.mpHandler;

import cn.hutool.core.date.DateUtil;
import cn.wolves.model.enums.BasePoEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * <p>
 * time file handler
 * </p>
 *
 * @author Jumping
 */
@Component
public class TimeMpHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName(BasePoEnum.VERSION.getValue(), 1, metaObject);

        this.setFieldValByName(BasePoEnum.CREATE_TIME.getValue(), DateUtil.date(), metaObject);

        this.setFieldValByName(BasePoEnum.UPDATE_TIME.getValue(), DateUtil.date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName(BasePoEnum.UPDATE_TIME.getValue(), DateUtil.date(), metaObject);

    }
}
