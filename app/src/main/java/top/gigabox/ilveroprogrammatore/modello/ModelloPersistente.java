/**
 * MIT License
 * Copyright (c) 2017 Palazzo Vincenzo vincenzopalazzo1996@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, m
 * erge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package top.gigabox.ilveroprogrammatore.modello;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import top.gigabox.ilveroprogrammatore.*;
import top.gigabox.ilveroprogrammatore.persistenza.DAOException;
import top.gigabox.ilveroprogrammatore.persistenza.DAOGenericoJson;


/**
 * @author https://github.com/unibasdev
 */

public class ModelloPersistente {

    private final static Logger LOGGER = LogManager.getLogger(ModelloPersistente.class);

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