package top.gigabox.ilveroprogrammatore.controllo.comportamento;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;
import top.gigabox.ilveroprogrammatore.Bot;
import top.gigabox.ilveroprogrammatore.Constanti;
import top.gigabox.ilveroprogrammatore.modello.Utenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class ComportamentoHasCallbackQuery implements IComportamento {

    private static final Logger LOGGER = Logger.getLogger(ComportamentoHasCallbackQuery.class);

    private int priorita;
    private List<IComportamento> comportamenti = new ArrayList<>();

    public ComportamentoHasCallbackQuery(int priorita) {
        this.priorita = priorita;
        setting();
    }

    public ComportamentoHasCallbackQuery() {
        setting();
    }

    public void addComportamento(IComportamento comportamento){
        comportamenti.add(comportamento);
    }

    private void setting(){
        IComportamento comportamento = new ComportamentoCallbackInfo(0);
        addComportamento(comportamento);
        comportamento = new ComportamentoAbilita(0);
        addComportamento(comportamento);
    }

    @Override
    public boolean verifica(Update update) {
        LOGGER.debug("Sono: " + this.getClass().getSimpleName());
        if(update.hasCallbackQuery()){
            LOGGER.debug("E' una callback query");

            Iterator iterator = comportamenti.iterator();
            boolean flag = true;
            while(iterator.hasNext() && flag){
                IComportamento comportamento = (IComportamento) iterator.next();
                flag = comportamento.verifica(update);
            }
        }
        return true;
    }
}
