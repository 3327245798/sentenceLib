package com.blp.sentenceLib.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class JsonUtils {
    public static <T> T getJavaObject(JSONObject jsonObject, Class<T> clazz) {
        T t = JSON.toJavaObject(jsonObject, clazz);
        return t;
    }

    @Deprecated
    public static JSONArray list2Json(ArrayList<T> arrayList, Class<T> clazz) {
        JSONArray objects = new JSONArray();
        for (T t : arrayList) {
            JSONObject jo = new JSONObject();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                jo.put(field.getName(), "");
            }
            objects.add(jo);
        }
        return objects;
    }

    public static ArrayList<Long> string2Array(String ids) {
        if (ids == null) {
            return null;
        }
        String[] _ids = ids.replace("[", "").replace("]", "").split(",");
        ArrayList<Long> longs = new ArrayList<>();
        for (String item : _ids) {
            long l = Long.parseLong(item.trim());
            longs.add(l);
        }
        return longs;
    }

    @Deprecated
    public static ArrayList<String> string2ArrayString(String scripts) {
        if (scripts == null) {
            return null;
        }
        String[] _scripts = scripts.replace("[", "").replace("]", "").split(",");
        ArrayList<String> strings = new ArrayList<>();
        for (String item : _scripts) {
            String script = item.trim();
            strings.add(script);
        }
        return strings;
    }

    @Deprecated
    public static ArrayList<JSONObject> string2ArrayJson(String scripts) {
        if (scripts == null) {
            return null;
        }
        String[] _scripts = scripts.replace("[", "").replace("]", "").replace("},", "}`").split("`");
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        for (String item : _scripts) {
            String _item = item.trim();
            JSONObject jsonObject = JSONObject.parseObject(_item);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }
}
