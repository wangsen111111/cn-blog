package org.example.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class URLTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        URL url = URLTest.class.getClassLoader()
                .getResource("config.json");
        System.out.println(url.getPath());
        //如果有中文，就需要解码
        String decode = URLDecoder.decode(url.getPath(), "UTF-8");
        System.out.println(decode);

    }
}
