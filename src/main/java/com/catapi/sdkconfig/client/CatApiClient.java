package com.catapi.sdkconfig.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.catapi.sdkconfig.Utils.SignUtils;
import com.catapi.sdkconfig.model.User;


import java.util.HashMap;
import java.util.Map;

public class CatApiClient {
    /**
     * 公钥
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;

    public CatApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "神经猫");
        String result= HttpUtil.get("http://localhost:9090/api/name/",map);
        System.out.println(result);
        return  result;
    }



    public String getNameByPost(String name) {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "神经猫");
        String result= HttpUtil.post("http://localhost:9090/api/name/",map);
        System.out.println(result);
        return result;
    }

    /**
     * api签名加密
     * @param body
     * @return
     */
    public Map<String,String> getHeaderMap(String body){
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey",accessKey);
//        map.put("secretKey",secretKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("body",body);
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", SignUtils.genSign(body,secretKey));
        return map;
    }


    public String getUserNameByPost( User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse res = HttpRequest
                .post("http://localhost:9090/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(res.body());
        System.out.println(res.getStatus());
        return res.body();
    }
}