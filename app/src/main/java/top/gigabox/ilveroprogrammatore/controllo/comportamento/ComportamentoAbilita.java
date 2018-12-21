package top.gigabox.ilveroprogrammatore.controllo.comportamento;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import top.gigabox.ilveroprogrammatore.Bot;
import top.gigabox.ilveroprogrammatore.Constanti;
import top.gigabox.ilveroprogrammatore.modello.Utenti;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class ComportamentoAbilita extends AbstractComportamento {

    private static final Logger LOGGER = Logger.getLogger(ComportamentoAbilita.class);

    public ComportamentoAbilita() {
        super();
    }

    public ComportamentoAbilita(int priorita) {
        super(priorita);
    }

    @Override
    public boolean verifica(Update update) {
        LOGGER.debug("Comportamento: " + getClass().getSimpleName());
        if(update.getCallbackQuery().getData().equals(Constanti.ABILITA)){
            LOGGER.debug("Query: " + update.getCallbackQuery().getData());
            if (update.getCallbackQuery().getData().equals("cosa_so_fare")) {
                LOGGER.info("Ho riceviuto cosa_so_fare");
                //Verifico la presenza dell username e in caso contrario la salvo per notificare aggiornamenti in futuro.
                Utenti utenti = (Utenti) Bot.getIstance().getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class);
                if (!utenti.isConteins(update.getCallbackQuery().getMessage().getChat().getId())) {
                    utenti.getUtenti().add(update.getMessage().getChat().getId());
                    Bot.getIstance().getModelloPersistente().saveBean(Constanti.UTENTI, utenti);
                }
                LOGGER.trace("aggiungere metodo generalizzato per l'invio dei messaggi");
                //TODO aggiugere metodo generalizzato per l'invio dei messaggi
            }
        }
        return false;
    }
}
