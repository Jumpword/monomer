package cn.wolves.service.facede.impl;

import cn.wolves.model.vo.DemoUserVo;
import cn.wolves.service.bz.DemoUserService;
import cn.wolves.service.facede.DemoUserFacadeService;
import cn.wolves.common.utils.BeanKit;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author Jumping
 */
@Service
public class DemoUserFacadeServiceImpl implements DemoUserFacadeService {

    @Resource
    private DemoUserService demoUserService;

    @Override
    public List<DemoUserVo> findAll() {
        return BeanKit.from(demoUserService.list(),DemoUserVo.class);
    }
}
