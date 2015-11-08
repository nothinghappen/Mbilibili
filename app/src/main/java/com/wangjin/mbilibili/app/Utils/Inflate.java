package com.wangjin.mbilibili.app.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Created by wangjin on 15/11/4.
 */
public class Inflate {

    public static String inflate(byte[] rawData){

        InputStream in = new ByteArrayInputStream(rawData);
        Inflater inflater = new Inflater(true);
        InflaterInputStream inflaterInputStream = new InflaterInputStream(in,inflater);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inflaterInputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
