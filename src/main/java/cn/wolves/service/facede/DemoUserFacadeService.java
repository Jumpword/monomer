package cn.wolves.service.facede;

import cn.wolves.model.vo.DemoUserVo;
import java.util.List;

/**
 * @author Jumping
 *
 */
public interface DemoUserFacadeService {

    /**
     * demo 查询所有
     * @return
     */
    List<DemoUserVo> findAll();

}
