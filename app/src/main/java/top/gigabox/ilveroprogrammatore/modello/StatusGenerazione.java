package top.gigabox.ilveroprogrammatore.modello;

import java.util.HashMap;
import java.util.Map;

public class StatusGenerazione {

    private Map<String, Frase> frasiUtenti = new HashMap<>();

    public Frase getFresi(String keyId){
        return frasiUtenti.get(keyId);
    }

    public void putFrase(String keyId, Frase frase){
        frasiUtenti.put(keyId, frase);
    }

    public Map<String, Frase> getFrasiUtenti() {
        return frasiUtenti;
    }


    public void setFrasiUtenti(Map<String, Frase> frasiUtenti) {
        this.frasiUtenti = frasiUtenti;
    }
}
