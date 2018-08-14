package top.gigabox.ilveroprogrammatore.modello;

public class Frase {

    private String frase;
    private String cit;
    private int votoPos;
    private int votoNeg;

    public Frase(String frase, String cit) {
        this.frase = frase;
        this.cit = cit;
        votoPos = 0;
        votoNeg = 0;
    }

    public int getVotoPos() {
        return votoPos;
    }

    public void setVotoPos(int votoPos) {
        this.votoPos = votoPos;
    }

    public int getVotoNeg() {
        return votoNeg;
    }

    public void setVotoNeg(int votoNeg) {
        this.votoNeg = votoNeg;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getCit() {
        return cit;
    }

    public void setCit(String cit) {
        this.cit = cit;
    }
}
