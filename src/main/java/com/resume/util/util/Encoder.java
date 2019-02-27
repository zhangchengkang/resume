package com.resume.util.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class Encoder {
    /**
     * 压缩为 echarts 格式的地图
     *
     * @param geoJson
     * @param fileName
     * @param type     json 或 其他（按js处理）
     * @return
     */
    public static String convert2Echarts(String geoJson, String fileName, String type) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            //注意这里的 encode.js 路径，文件内容就是上面的 js 代码
            engine.eval(new FileReader(Encoder.class.getResource("/encode.js").getPath()));
            Invocable invocable = (Invocable) engine;
            return (String) invocable.invokeFunction("convert2Echarts", geoJson, fileName, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}