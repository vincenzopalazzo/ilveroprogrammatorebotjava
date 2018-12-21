package top.gigabox.ilveroprogrammatore.controllo.comportamento;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class ComportamentoChainOfResponsability {

    private List<IComportamento> comportamenti = new ArrayList<>();

    public void addComportamento(IComportamento comportamento){
        comportamenti.add(comportamento);
    }

    private void setting(){
        IComportamento comportamento = new ComportamentoHasCallbackQuery(0);
        addComportamento(comportamento);
    }

    public void esegui(Update update){
        Iterator iterator = comportamenti.iterator();
        boolean flag = true;
        while(iterator.hasNext() && flag){
            IComportamento comportamento = (IComportamento) iterator.next();
            flag = comportamento.verifica(update);
        }
    }

}
