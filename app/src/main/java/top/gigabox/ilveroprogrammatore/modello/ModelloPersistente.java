package top.gigabox.ilveroprogrammatore.modello;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gigabox.ilveroprogrammatore.*;
import top.gigabox.ilveroprogrammatore.persistenza.DAOException;
import top.gigabox.ilveroprogrammatore.persistenza.DAOGenericoJson;


public class ModelloPersistente {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModelloPersistente.class);
    private final DAOGenericoJson daoGenericoJson = new DAOGenericoJson();
    private final Map<String, Object> cache = new HashMap<String, Object>();

    public void saveBean(String key, Object bean) {
        this.cache.put(key, bean);
        save(key, bean);
    }

    public Object getPersistentBean(String key, Class type) {
        Object cachedObject = this.cache.get(key);
        if (cachedObject != null) {
            return cachedObject;
        }
        Object persistentObject = load(key, type);
        if (persistentObject == null) {
            return null;
        }
        cache.put(key, persistentObject);
        return persistentObject;
    }

    private Object load(String key, Class type) {
        File file = new File(Constanti.SAVE_MODEL, getFileName(key));
        if (!file.exists()) {
            return null;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return daoGenericoJson.carica(in, type);
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un errore del tipo: " + e.getLocalizedMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    private void save(String key, Object bean) {
        File file = new File(Constanti.SAVE_MODEL, getFileName(key));
        try {
            daoGenericoJson.salva(bean, new FileOutputStream(file));
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un errore del tipo: " + e.getLocalizedMessage());
            throw new DAOException(e.getLocalizedMessage());
        }
    }

    private String getFileName(String key) {
        return key + ".json";
    }

    private class AdapterString implements JsonSerializer<String>, JsonDeserializer<String> {

        public JsonElement serialize(String string, Type tipo, JsonSerializationContext context) {
            return new JsonPrimitive(string);
        }

        public String deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
            return json.getAsString();
        }
    }
}