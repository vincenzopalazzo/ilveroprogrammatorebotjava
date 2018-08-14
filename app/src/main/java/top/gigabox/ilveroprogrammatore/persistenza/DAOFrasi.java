package top.gigabox.ilveroprogrammatore.persistenza;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gigabox.ilveroprogrammatore.modello.Frase;


import java.util.ArrayList;
import java.util.List;

public class DAOFrasi implements IDAOFrasi{

    private static final Logger LOGGER = LoggerFactory.getLogger(DAOFrasi.class);

    @Override
    public List<Frase> load(String nomeFile) throws DAOException {
        List<Frase> frasi = new ArrayList<>();
        //Prova estrazione file da parser html
        try {
            Document doc = (Document) Jsoup.connect("http://xmau.com/humour/veroprogrammatore.html").get();
            LOGGER.debug("Ho scaricato il file");
            LOGGER.debug("Titolo del documento: \n" + doc.title());
            Elements element = doc.select("li");
            for(int i = 0; i < element.size(); i++){
                if(LOGGER.isTraceEnabled()) LOGGER.trace("Sto leggendo: " + element.get(i).text());
                frasi.add(new Frase(element.get(i).text(), "Il vero programmatore"));
            }
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un errore del tipo; " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        LOGGER.debug("Dimenzione lista frasi: " + frasi.size());
        return frasi;
    }
}
