package top.gigabox.ilveroprogrammatore;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import top.gigabox.ilveroprogrammatore.modello.*;
import top.gigabox.ilveroprogrammatore.persistenza.DAOFrasi;
import top.gigabox.ilveroprogrammatore.persistenza.DAOGenericoJson;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author https://github.com/vincenzopalazzo
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
    private DAOFrasi daoFrasi = new DAOFrasi();
    private Archivio archivio = new Archivio();
    private StatusGenerazione statusGenerazione = new StatusGenerazione();
    private Modello modello = new Modello();
    private Operatore operatore = new Operatore();

    public Operatore getOperatore() {
        return operatore;
    }

    public Modello getModello() {
        return modello;
    }

    public DAOFrasi getDaoFrasi() {
        return daoFrasi;
    }

    public Archivio getArchivio() {
        archivio = (Archivio) modelloPersistente.getPersistentBean(Constanti.ARCHIVIO, Archivio.class);
        return archivio;
    }

    public ModelloPersistente getModelloPersistente() {
        return modelloPersistente;
    }

    public DAOGenericoJson getDaoGenericoJson() {
        return daoGenericoJson;
    }

    public static void main(String[] args) {
        LOGGER.error("Attivazione Server");

        if(LOGGER.isTraceEnabled()){
            /**It allows to print the current setting taken from the logback
             used to correct the bug on the log**/
            ch.qos.logback.classic.LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
            StatusPrinter.print(context);
        }


        //Cro la direcrory per il resto
        if(!singleton.creaDirectory()){
            LOGGER.debug("Cartella non creata, controlla se il percorso e' giusto ed ha i permessi necessari.");
            return;
        }

        //Controllo se gli utenti sono inizializzati
        if (singleton.getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class) == null) {
            singleton.getModelloPersistente().saveBean(Constanti.UTENTI, singleton.utenti);
        }

        if(singleton.getModelloPersistente().getPersistentBean(Constanti.ARCHIVIO, Archivio.class) != null){
            LOGGER.debug("Frasi gia' presenti le carico dal file");
            singleton.archivio = (Archivio) singleton.getModelloPersistente().getPersistentBean(Constanti.ARCHIVIO, Archivio.class);
        }

        singleton.getModello().putBean(Constanti.STATO, singleton.statusGenerazione);

        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        BotSession botSession = null;
        IlVeroProgrammatoreBot bot = new IlVeroProgrammatoreBot();

        try {
            botSession = telegramBotsApi.registerBot(bot);
        } catch (Exception e) {
            LOGGER.error("Si e' verificato un errore del tipo: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        singleton.schrmoInteragisciBot(botSession, bot);
        //Deprecated
        /*finally {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (botSession != null) {
                LOGGER.debug("Sto stoppando il bot, attendo sviluoppo api");
                botSession.stop();
            }
        }*/
    }

    private void schrmoInteragisciBot(BotSession botSession, IlVeroProgrammatoreBot bot){
        while (true){
            int scelta = singleton.generaGrafica();
            if(scelta == 0){
                //Uscita
                System.out.println("il bot verra' disconnesso a scanzo di equivoci");
                botSession.stop();
                break;
            }
            if(scelta == 1){
                System.out.println("Disconnessione bot");
                botSession.stop();
                break;
            }
            if(scelta == 2){
                System.out.print("Cosa vuoi comunicare: ");
                String messaggio = it.unibas.utilita.Console.leggiStringa();
                if(messaggio.isEmpty()){
                    System.out.println("Devi iniserire qualcosa da mandare");
                    continue;
                }
                bot.messaggioBrodcast(messaggio);
            }
            if(scelta == 3){
                List<Frase> frasi = new ArrayList<>();
                frasi = daoFrasi.load("non serve a nulla");
                LOGGER.debug("numero di frasi caricate: " + frasi.size());
                archivio.setFrasi(frasi);
                singleton.getModelloPersistente().saveBean(Constanti.ARCHIVIO, archivio);
                LOGGER.debug("Dovrebbe essere andato tutto liscio");
            }
        }
    }

    //TODO inserisci funzionalita' per mandare un messaggio broadcast
    private int generaGrafica() {
        System.out.println(" ______________________________");
        System.out.println("| 1. Interompi sessione bot     |");
        System.out.println("| 2. Scrivi messaggio brod.     |");
        System.out.println("| 3. Carica frasi dal sito.     |");
        System.out.println("|                               |");
        System.out.println("|      0.Esci (non inter.)      |");
        System.out.println("|_______________________________|");
        System.out.print("Scelta ---> ");
        int scelta = it.unibas.utilita.Console.leggiIntero();
        while (scelta < 0 || scelta > 3){
            System.out.print("Scelta sbagliata rinserisci: ");
            scelta = it.unibas.utilita.Console.leggiIntero();
        }
        return scelta;
    }

    private boolean creaDirectory() {
        File dir = new File(Constanti.PERCORSO_DIRECTORY_DATI);
        if(dir.exists()){
            LOGGER.debug("Carterlla esistente, bene");
            return true;
        }
        return dir.mkdir();
    }

    //TODO gestire il log su file

}
