package top.gigabox.ilveroprogrammatore.modello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gigabox.ilveroprogrammatore.Bot;

import java.util.Random;

public class Operatore {

    private static final Logger LOGGER = LoggerFactory.getLogger(Operatore.class);

    public Frase generaFrase(){
        Archivio archivio = Bot.getIstance().getArchivio();
        Random random = new Random();
        int frasePrescelta = random.nextInt(archivio.getFrasi().size());
        LOGGER.debug("Frase prescelta: " + frasePrescelta);
        return archivio.getFrasi().get(frasePrescelta);
    }
}
