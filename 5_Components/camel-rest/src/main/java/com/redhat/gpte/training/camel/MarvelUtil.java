package com.redhat.gpte.training.camel;

import org.apache.camel.Body;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarvelUtil {

    protected String apiKey;
    protected String privateKey;
    protected long ts;

    public String hash() throws Exception {
        ts = System.currentTimeMillis();
        String stringToHash = ts + privateKey + apiKey;
        return DigestUtils.md5Hex(stringToHash);
    }

    public void read(@Body HashMap<String,Object> map) {
            List<Object> result = getValues(map);
        for (Object element : result) {
            System.out.println(element);
        }
    }

    public List<Object> getValues(Map<String, Object> map) {

        List<Object> retVal = new ArrayList<Object>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();

            if (value instanceof Map) {
                retVal.addAll(getValues((Map) value));
            } else {
                retVal.add(value);
            }
        }

        return retVal;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public long getTs() {
        return Long.valueOf(ts);
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }


}
