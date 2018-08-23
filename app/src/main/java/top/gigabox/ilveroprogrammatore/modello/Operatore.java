/**
 * MIT License
 * Copyright (c) 2017 Palazzo Vincenzo vincenzopalazzo1996@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, m
 * erge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package top.gigabox.ilveroprogrammatore.modello;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import top.gigabox.ilveroprogrammatore.Bot;

import java.util.Random;

/**
 * @author https://github.com/vincenzopalazzo
 */

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
