package top.gigabox.ilveroprogrammatore.modello;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import top.gigabox.ilveroprogrammatore.Bot;

import java.util.Random;

public class Operatore {

    private static final Logger LOGGER = LogManager.getLogger(Operatore.class);

    public Frase generaFrase(){
        Archivio archivio = Bot.getIstance().getArchivio();
        Random random = new Random();
        int frasePrescelta = random.nextInt(archivio.getFrasi().size());
        LOGGER.debug("Frase prescelta: " + frasePrescelta);
        return archivio.getFrasi().get(frasePrescelta);
    }
}
