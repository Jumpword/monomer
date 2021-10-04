package cn.wolves;

import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class mainTest {

    @Test
    void test() {
        ArrayList<String> strings = CollUtil.newArrayList("1", "2", "3");
        System.out.println(strings.toString());
    }
}
