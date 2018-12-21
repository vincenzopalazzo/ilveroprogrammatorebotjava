package top.gigabox.ilveroprogrammatore.controllo.comportamento;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import top.gigabox.ilveroprogrammatore.Bot;
import top.gigabox.ilveroprogrammatore.Constanti;
import top.gigabox.ilveroprogrammatore.modello.Utenti;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class ComportamentoCallbackInfo implements IComportamento {

    private static final Logger LOGGER = Logger.getLogger(ComportamentoCallbackInfo.class);

    private int priorita;

    public ComportamentoCallbackInfo(int priorita) {
        this.priorita = priorita;
    }

    public ComportamentoCallbackInfo() {
    }

    @Override
    public boolean verifica(Update update) {
        LOGGER.debug("Comportamento: " + getClass().getSimpleName());
        if(update.getCallbackQuery().getData().equalsIgnoreCase(Constanti.INFO_SVILUPPO)){
            LOGGER.debug("Abbiamo ricevuto una query del tipo: " + Constanti.INFO_SVILUPPO);
            Utenti utenti = (Utenti) Bot.getIstance().getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class);
            if (!utenti.isConteins(update.getCallbackQuery().getMessage().getChat().getId())) {
                LOGGER.info("Username non conosciuto: " + update.getMessage().getChat().getUserName());
                utenti.getUtenti().add(update.getMessage().getChat().getId());
                Bot.getIstance().getModelloPersistente().saveBean(Constanti.UTENTI, utenti);
            }
            LOGGER.trace("Funzione rispondi al messaggio in maniera generale ancora da implementare");
            //TODO manca il modo per inviare il messaggio in modo GENERALE
        }
        return true;
    }
}
