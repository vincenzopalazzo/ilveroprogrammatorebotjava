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

import java.io.Console;
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

        //Cro la direcrory per il resto
        if(!singleton.creaDirectory()){
            LOGGER.error("Cartella non creata, controlla se il percorso e' giusto ed ha i permessi necessari.");
            return;
        }

        //Controllo se gli utenti sono inizializzati
        if (singleton.getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class) == null) {
            singleton.getModelloPersistente().saveBean(Constanti.UTENTI, singleton.utenti);
        }

        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        BotSession botSession = null;
        IlVeroProgrammatoreBot bot = new IlVeroProgrammatoreBot();
        try {
            botSession = telegramBotsApi.registerBot(bot);
        } catch (Exception e) {
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
                ////TODO lettura loggin
            }
        }
    }

    //TODO inserisci funzionalita' per mandare un messaggio broadcast
    private int generaGrafica() {
        System.out.println(" ______________________________");
        System.out.println("| 1. Interompi sessione bot     |");
        System.out.println("| 2. Scrivi messaggio brod.     |");
        System.out.println("|                               |");
        System.out.println("|      0.Esci (non inter.)      |");
        System.out.println("|_______________________________|");
        System.out.print("Scelta ---> ");
        int scelta = it.unibas.utilita.Console.leggiIntero();
        while (scelta < 0 || scelta > 2){
            System.out.print("Scelta sbagliata rinserisci: ");
            scelta = it.unibas.utilita.Console.leggiIntero();
        }
        return scelta;
    }

    private boolean creaDirectory() {
        File dir = new File(Constanti.PERCORSO_DIRECTORY_DATI);
        if(dir.exists()){
            LOGGER.error("Carterlla esistente, bene");
            return true;
        }
        return dir.mkdir();
    }

    //TODO gestire il log su file

}
