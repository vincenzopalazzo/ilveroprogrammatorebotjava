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
package top.gigabox.ilveroprogrammatore;

/**
 * @author https://github.com/vincenzopalazzo
 */

public class Constanti {
    //TODO Gestire meglio i percorsi, generallizarli sopratutto
    public static final String PERCORSO_DIRECTORY_DATI = "/home/pi/dati/";
    public static final String BOT_SESSION = PERCORSO_DIRECTORY_DATI + "bootSession.json";
    public static final String SAVE_MODEL = PERCORSO_DIRECTORY_DATI;
    //public static final String SAVE_ARCHIVIE = PERCORSO_DIRECTORY_DATI + "/archivio.json";

    //Ciclo di vita del software
    public static final String UTENTI = "UTENTI";
    public static final String ARCHIVIO = "ARCHIVIO";
    public static final String STATO = "STATO";


    //Arrai contenente caratteri supportati dal bot
}
