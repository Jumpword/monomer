package cn.wolves.mapper;

import cn.wolves.model.po.DemoUserPo;
import cn.wolves.service.bz.DemoUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
@Slf4j
class DemoUserMapperTest {

    @Resource
    private DemoUserMapper demoUserMapper;

    @Resource
    private DemoUserService demoUserService;


    @Test
    void insert() {

        DemoUserPo demoUserPo = new DemoUserPo();
        demoUserPo.setUserName("001");
        demoUserPo.setUserCode("000013");
        demoUserPo.setPassword("1251251251");
        demoUserMapper.insert(demoUserPo);

    }

    //ActiveRecord 模式 CRUD
    @Test
    void insertActiveRecord() {
        DemoUserPo demoUserPo = new DemoUserPo();
        demoUserPo.setUserName("jumping");
        demoUserPo.setUserCode("000001");
        demoUserPo.setPassword("1234567");
        demoUserPo.insert();

    }

    //使用service
    @Test
    void insertUserService() {
        DemoUserPo demoUserPo = new DemoUserPo();
        demoUserPo.setUserName("张三");
        demoUserPo.setUserCode("000004");
        demoUserPo.setPassword("1234567");
        demoUserService.save(demoUserPo);

    }

    @Test
    void findAll() {
        List<DemoUserPo> demoUserPos = demoUserService.list(null);
        System.out.println(demoUserPos.size());
        System.out.println(demoUserPos.toString());

    }



    //有条件的查询用户
    @Test
    public void selectByCondition(){

        DemoUserPo demoUserPo = new DemoUserPo();
        demoUserPo.setId(13L);
        // WolvesWrapper<DemoUserPo> wrap = WrapperKit.wrap(demoUserPo);
        LambdaQueryWrapper<DemoUserPo> wrap = new LambdaQueryWrapper<>(demoUserPo);
        System.out.println(demoUserService.list(wrap).toString());

        System.out.println(demoUserService.getOne(wrap));

        //根据条件
        // WolvesWrapper<DemoUserPo> demoWrapper = WrapperKit.wrap(DemoUserPo.class);
        LambdaQueryWrapper<DemoUserPo> demoWrapper = new LambdaQueryWrapper<>(demoUserPo);
        //版本为1
        demoWrapper.eq(DemoUserPo::getVersion,1);
        //排除张三
        demoWrapper.ne(DemoUserPo::getUserName,"张三");
        System.out.println(demoUserService.list(demoWrapper).toString());


    }


    //更新用户
    @Test
    public void updateById(){
        DemoUserPo demoUserPo = new DemoUserPo();
        demoUserPo.setId(11L);
        demoUserPo.setUserName("阿一");
        //demoUserService.updateAllColumnById(demoUserPo);基本上用不上
        //demoUserService.update() 这个update相当于批量操作
        boolean b = demoUserService.saveOrUpdate(demoUserPo);
        if (!b){
            System.out.println("更新失败");
        }else {
            System.out.println("更新成功");
        }


    }

    //根据id删除
    @Test
    public void  deleteById(){

    }

}