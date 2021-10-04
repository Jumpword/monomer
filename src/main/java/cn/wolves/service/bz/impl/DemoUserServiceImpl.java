package cn.wolves.service.bz.impl;

import cn.wolves.mapper.DemoUserMapper;
import cn.wolves.model.po.DemoUserPo;
import cn.wolves.service.bz.DemoUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Jumping
 */
@Service
public class DemoUserServiceImpl extends ServiceImpl<DemoUserMapper, DemoUserPo> implements DemoUserService {

}
