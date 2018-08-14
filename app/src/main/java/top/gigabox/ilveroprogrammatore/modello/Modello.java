package top.gigabox.ilveroprogrammatore.modello;

import java.util.HashMap;
import java.util.Map;

public class Modello {

    private Map<String, Object> bean = new HashMap<>();

    public Object getBean(String key){
        return bean.get(key);
    }

    public void putBean(String key, Object o){
        bean.put(key, o);
    }

}
