package top.gigabox.ilveroprogrammatore.controllo.comportamento;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author https://github.com/vincenzopalazzo
 */
public interface IComportamento {

    public boolean verifica(Update update);
}
