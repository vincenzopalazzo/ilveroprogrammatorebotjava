package top.gigabox.ilveroprogrammatore.modello;

import java.util.ArrayList;
import java.util.List;

public class Utenti {

    private List<Long> utenti = new ArrayList<>();

    public List<Long> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Long> utenti) {
        this.utenti = utenti;
    }

    public boolean isConteins(Long nomeUtente){
        return utenti.contains(nomeUtente);
    }
}
