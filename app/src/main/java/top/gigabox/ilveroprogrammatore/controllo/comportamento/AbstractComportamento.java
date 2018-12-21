package top.gigabox.ilveroprogrammatore.controllo.comportamento;

/**
 * @author https://github.com/vincenzopalazzo
 */
public abstract class AbstractComportamento implements IComportamento{

    private int priorita;

    public AbstractComportamento() {
    }

    public AbstractComportamento(int priorita) {
        this.priorita = priorita;
    }

    public int getPriorita() {
        return priorita;
    }
}
