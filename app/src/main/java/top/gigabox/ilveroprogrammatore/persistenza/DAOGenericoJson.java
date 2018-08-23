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
package top.gigabox.ilveroprogrammatore.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import top.gigabox.ilveroprogrammatore.persistenza.*;

/**
 * @author https://github.com/vincenzopalazzo
 */

public class DAOGenericoJson {

    private static String TAG = "DAOGenerico";
    private String datePatternFormat = "dd-MM-yyyy HH:mm:ss";

    /* ******************************************
     *               Conversione
     * ****************************************** */
    public String toJson(Object oggetto){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new AdapterDate());
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(oggetto);
    }

    /* ******************************************
     *               Caricamento
     * ****************************************** */
    public Object carica(InputStream inputStream, Class t) throws DAOException {
        Object oggetto = null;
        Reader flusso = null;
        try {
            flusso = new InputStreamReader(inputStream);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new AdapterDate());
            Gson gson = builder.create();
            oggetto = gson.fromJson(flusso, t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
        return oggetto;
    }

    /* ******************************************
     *               Salvataggio
     * ****************************************** */
    public void salva(Object oggetto, String out) throws DAOException {
        PrintWriter flusso = null;
        try {
            //TODO non so se funziona
            flusso = new java.io.PrintWriter(new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8));
           // flusso = new java.io.PrintWriter(out);
            String stringaJson = toJson(oggetto);
            flusso.print(stringaJson);
        } catch (Exception ioe) {
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }

    }
    public void salva(Object oggetto, OutputStream out) throws DAOException {
        PrintWriter flusso = null;
        try {
            flusso = new java.io.PrintWriter(out);
            String stringaJson = toJson(oggetto);
            flusso.print(stringaJson);
        } catch (Exception ioe) {
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }

    }

    private class AdapterDate implements JsonSerializer<Date>, JsonDeserializer<Date> {

        public JsonElement serialize(Date date, Type tipo, JsonSerializationContext context) {
            DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
            return new JsonPrimitive(dateFormat.format(date.getTime()));
        }

        public Date deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
            try {
                String stringaData = json.getAsString();
                DateFormat dateFormat = new SimpleDateFormat(datePatternFormat);
                Date dataRegistrazione = dateFormat.parse(stringaData);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(dataRegistrazione);
                return calendar.getTime();
            } catch (ParseException ex) {
                throw new JsonParseException(ex);
            }
        }
    }

    public String getDatePatternFormat() {
        return datePatternFormat;
    }

    public void setDatePatternFormat(String datePatternFormat) {
        this.datePatternFormat = datePatternFormat;
    }
}
