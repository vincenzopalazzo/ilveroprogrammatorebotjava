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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import top.gigabox.ilveroprogrammatore.modello.Frase;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author https://github.com/vincenzopalazzo
 */

public class DAOFrasi implements IDAOFrasi{

    private static final Logger LOGGER = LogManager.getLogger(DAOFrasi.class);

    @Override
    public List<Frase> load(String nomeFile) throws DAOException {
        List<Frase> frasi = new ArrayList<>();
        try {
            Document doc = (Document) Jsoup.connect("http://xmau.com/humour/veroprogrammatore.html").get();
            LOGGER.debug("Ho scaricato il file");
            LOGGER.debug("Titolo del documento: \n" + doc.title());
            Elements element = doc.select("li");
            for(int i = 0; i < element.size(); i++){
                if(LOGGER.isTraceEnabled()) LOGGER.trace("Sto leggendo: " + element.get(i).text());
                //String unicode = new String(element.get(i).text().getBytes(), Charset.forName("UTF-8"));
                frasi.add(new Frase(DAOFrasi.unicodePaserse(element.get(i).text()), "Il vero programmatore"));
                //TODO risolvere problema del unicode
                //frasi.add(new Frase(element.get(i).text(), "Il vero programmatore"));
            }
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un errore del tipo; " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Dimenzione lista frasi: " + frasi.size());
        return frasi;
    }

    //TODO (BUG) non copre tutti i casi, trovare una strada migliore
    private static String unicodePaserse(String string){
        Map<String, String> caratteriUnicode = new HashMap<>();
        caratteriUnicode.put("90�", "90 gradi");
        caratteriUnicode.put("�", "");
        String unicode = "'";
        caratteriUnicode.put("\u0027", new String(unicode.getBytes(Charset.forName("UTF-8"))));
        unicode = "città";
        caratteriUnicode.put("citt", new String(unicode.getBytes(Charset.forName("UTF-8"))));
        unicode = "Città";
        caratteriUnicode.put("Citt", new String(unicode.getBytes(Charset.forName("UTF-8"))));
        unicode = "può";
        caratteriUnicode.put("pu", new String(unicode.getBytes(Charset.forName("UTF-8"))));
        unicode = "perché";
        caratteriUnicode.put("perch", new String(unicode.getBytes(Charset.forName("UTF-8"))));

        for(Map.Entry<String, String> valore : caratteriUnicode.entrySet()){
            if(string.contains(valore.getKey())){
                LOGGER.debug("Trovato il valore: " + valore.getKey() + " nella stringa: " + string + "\n lo sostituisco con: " + valore.getValue());
                String clear = string.replaceAll(valore.getKey(), valore.getValue());
                string = clear;
                LOGGER.debug("Ora la stringa e': " + string);
            }
        }
        return string;
    }
}
