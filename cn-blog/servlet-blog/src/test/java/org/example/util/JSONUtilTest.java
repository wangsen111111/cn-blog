package org.example.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JSONUtilTest {
    //单元测试：junit框架使用方法@Test注解，保证方法为public void
    @Test
    public void testSerialize(){
        //测试序列化操作：使用map模式复杂
        Map map=new HashMap();
        map.put("name","java班级");
        map.put("students",new int[]{1,2});
        String json=JSONUtil.serialize(map);
        System.out.println(json);
        Assert.assertNotNull(json);
    }
    @Test
    public void testDeserialize(){
        //类加载器加载某个资源，返回输入流
        InputStream is=JSONUtilTest.class.getClassLoader().getResourceAsStream("login.json");
        Map map=JSONUtil.deserialize(is,Map.class);
        System.out.println(map);
        Assert.assertNotNull(map);
    }
}
