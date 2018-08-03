package top.gigabox.ilveroprogrammatore.modello;

import java.util.ArrayList;
import java.util.List;

public class Utenti {

    private List<String> utenti = new ArrayList<>();

    public List<String> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<String> utenti) {
        this.utenti = utenti;
    }

    public boolean isConteins(String nomeUtente){
        return utenti.contains(nomeUtente);
    }
}
