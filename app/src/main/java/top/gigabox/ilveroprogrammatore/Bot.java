package top.gigabox.ilveroprogrammatore;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import top.gigabox.ilveroprogrammatore.modello.IlVeroProgrammatoreBot;
import top.gigabox.ilveroprogrammatore.modello.ModelloPersistente;
import top.gigabox.ilveroprogrammatore.modello.Utenti;
import top.gigabox.ilveroprogrammatore.persistenza.DAOGenericoJson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class Bot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private static final Bot singleton = new Bot();

    public static Bot getIstance() {
        return singleton;
    }


    private DAOGenericoJson daoGenericoJson = new DAOGenericoJson();
    private ModelloPersistente modelloPersistente = new ModelloPersistente();
    private Utenti utenti = new Utenti();

    public ModelloPersistente getModelloPersistente() {
        return modelloPersistente;
    }

    public DAOGenericoJson getDaoGenericoJson() {
        return daoGenericoJson;
    }

    public static void main(String[] args) {
        LOGGER.error("Attivazione Server");

        if(!singleton.creaDirectory()){
            LOGGER.error("Cartella non creata");
            //TODO gestire il caso in cui la cartella e' gia' creata
        }


        if (singleton.getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class) == null) {
            singleton.getModelloPersistente().saveBean(Constanti.UTENTI, singleton.utenti);
        }

        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        //TODO Gestire il caso per bloccare un bot... provare con la tecnologi json.
        BotSession botSession = null;
        try {
            botSession = telegramBotsApi.registerBot(new IlVeroProgrammatoreBot());
        } catch (Exception e) {
            e.printStackTrace();
            //TODO Ricordati ri decommnatre il blocco finally, altrimenti non si puo piu' bloccare il bot.
        } /*finally {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (botSession != null) {
                LOGGER.debug("Sto stoppando il bot, attendo sviluoppo api");
                botSession.stop();
            }*/
    }

    private boolean creaDirectory() {
        File dir = new File(Constanti.PERCORSO_DIRECTORY_DATI);
        return dir.mkdir();
    }

    //TODO gestire il log su file
    //TODO gestire il problema della cartella dati, la cartella deve essere riscreata se non e' presente ogni volta sul server.

}
