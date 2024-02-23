package com.kehua.mongdb;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataProvider {

    public static List<JSONObject>  gen(Integer num){
        List<JSONObject> res = new ArrayList<>();
        Random random = new Random(100);
        for(int i = 0; i < num ; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("devId",i);
            for(int j = 1; j <= 220 ; j++){
                jsonObject.put("YC" + j, String.valueOf(random.nextDouble() * 100));
            }
            res.add(jsonObject);
        }
        return res;
    }

}
