package cn.wolves.controller.api;

import cn.wolves.common.annotation.SpendTime;
import cn.wolves.common.frame.spring.ApiResponse;
import cn.wolves.common.frame.spring.ApiResponseBuilder;
import cn.wolves.common.frame.spring.BaseApi;
import cn.wolves.model.vo.DemoUserVo;
import cn.wolves.service.facede.DemoUserFacadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jumping
 */
@Api("测试的接口")
@RestController
@RequestMapping("/demo")
public class DemoUserController extends BaseApi {

    @Resource
    private DemoUserFacadeService demoUserFacadeService;

    @ApiOperation("查询所有用户接口")
    @GetMapping("findAll")
    @SpendTime
    public ApiResponse<List<DemoUserVo>> findAll(){
        return ApiResponseBuilder.ok(demoUserFacadeService.findAll());
    }
}
