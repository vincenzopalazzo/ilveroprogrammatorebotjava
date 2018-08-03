package top.gigabox.ilveroprogrammatore.modello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import top.gigabox.ilveroprogrammatore.Bot;
import top.gigabox.ilveroprogrammatore.Constanti;

import java.util.ArrayList;
import java.util.List;

public class IlVeroProgrammatoreBot extends TelegramLongPollingBot {

    //TODO non funziona il loggin
    private static final Logger LOGGER = LoggerFactory.getLogger(IlVeroProgrammatoreBot.class);

    @Override
    public String getBotToken() {
        return "243429243:AAFjSjMhcGzxGp6d2gqmlyvRhq2thLPSpTI";
    }

    @Override
    public String getBotUsername() {
        return "ilVeroProgrammatore_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {

        //Creazione tastiera
        InlineKeyboardMarkup tastiera = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> bottonis = new ArrayList<>();
        List<InlineKeyboardButton> bottoni = new ArrayList<>();
        InlineKeyboardButton bottone = new InlineKeyboardButton("INFO");
        bottone.setCallbackData("messaggio_info_sviluppo");
        bottoni.add(bottone);
        bottonis.add(bottoni);
        tastiera.setKeyboard(bottonis);

        //TODO Se riceve una vide risposta breve, solleva nullpointer
        if (update.hasCallbackQuery()) {

            LOGGER.debug("e' un messaggio da tastiera inline");


            if (update.getCallbackQuery().getData().equals("messaggio_info_sviluppo")) {
                //Verifico la presenza dell username e in caso contrario la salvo per notificare aggiornamenti in futuro.
                Utenti utenti = (Utenti) Bot.getIstance().getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class);
                if(!utenti.isConteins(update.getCallbackQuery().getMessage().getChat().getUserName())){
                    LOGGER.debug("Username non conosciuto: " +  update.getMessage().getChat().getUserName());
                    utenti.getUtenti().add(update.getMessage().getChat().getUserName());
                    Bot.getIstance().getModelloPersistente().saveBean(Constanti.UTENTI, utenti);
                }

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                String messaggio = "Ciao sto riscrivendo il mio bot in java, Ho bisogno solo di un po di tempo!" +
                        " Per maggiori info puoi contattarmi qui @crazyjoker96";
                LOGGER.debug("Sto inviando il seguente messaggio: " + messaggio);
                sendMessage.setText(messaggio);

                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    LOGGER.debug("Si e' verificato il seguente errore: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
            if(update.getCallbackQuery().getMessage().getText().equals("cosa_so_fare")){
                LOGGER.debug("Ho riceviuto cosa_so_fare");
                //Verifico la presenza dell username e in caso contrario la salvo per notificare aggiornamenti in futuro.
                Utenti utenti = (Utenti) Bot.getIstance().getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class);
                if(!utenti.isConteins(update.getCallbackQuery().getMessage().getChat().getUserName())){
                    LOGGER.debug("Username non conosciuto: " +  update.getMessage().getChat().getUserName());
                    utenti.getUtenti().add(update.getMessage().getChat().getUserName());
                    Bot.getIstance().getModelloPersistente().saveBean(Constanti.UTENTI, utenti);
                }
            }
        }

        if (update.hasMessage()) {

            //Verifico la presenza dell username e in caso contrario la salvo per notificare aggiornamenti in futuro.
            Utenti utenti = (Utenti) Bot.getIstance().getModelloPersistente().getPersistentBean(Constanti.UTENTI, Utenti.class);
            if(!utenti.isConteins(update.getMessage().getChat().getUserName())){
                LOGGER.debug("Username non conosciuto: " +  update.getMessage().getChat().getUserName());
                utenti.getUtenti().add(update.getMessage().getChat().getUserName());
                Bot.getIstance().getModelloPersistente().saveBean(Constanti.UTENTI, utenti);
            }

            LOGGER.debug("e' un messagio normale");
            if(update.getMessage().getText().equals("/hey") || update.getMessage().getText().equals("/hey@ilVeroProgrammatore_bot")) {
                LOGGER.debug("Ha comuniocato con un comando /");
                SendMessage message = new SendMessage();

                message.setChatId(update.getMessage().getChat().getId());
                String messaggio = "Ciao, per chidermi quali sono i miei comandi all'interno di un gruppo chiedimi " +
                        "Ciao @IlVeroProgrammatore_bot cosa sai fare?, oppure basta semplicemente un Ciao @IlVeroProgrammatore_bot";
                message.setText(messaggio);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } finally {
                    return;
                }
            }
            if (update.getMessage().getChat().isUserChat()) {
                LOGGER.debug("Sto comunicando con un utente");
                SendMessage message = new SendMessage();

                message.setReplyMarkup(tastiera);

                message.setChatId(update.getMessage().getChatId());
                String messaggio = "Ciao " + update.getMessage().getChat().getFirstName() + ", mi spiace la tua foto, come stai?";
                message.setText(messaggio);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }else if (update.getMessage().getChat().isGroupChat()) {
                LOGGER.debug("Sto comunicando con un gruppo");
                if(update.getMessage().getText().contains("@ilVeroProgrammatore_bot")){
                    SendMessage message = new SendMessage();
                    message.setReplyMarkup(tastiera);

                    message.setChatId(update.getMessage().getChat().getId());
                    String messaggio = "Ehy ciao Ragazzi, sono felice di stare in questo nuovo gruppo";
                    message.setText(messaggio);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    //Messaggio su cosa so fare
                    message = new SendMessage();

                    //Creazione tastiera secondaria

                    bottonis.clear();
                    bottoni = new ArrayList<>();
                    bottoni.add(bottone);
                    bottone = new InlineKeyboardButton("Ancora nulla");
                    bottone.setCallbackData("cosa_so_fare");
                    bottoni.add(bottone);
                    bottonis.add(bottoni);
                    tastiera.setKeyboard(bottonis);

                    message.setReplyMarkup(tastiera);

                    message.setChatId(update.getMessage().getChat().getId());
                    messaggio = "Come va? ti riassumo molto brevemente cosa so fare cosi puoi decidere se ti sono utile";
                    message.setText(messaggio);
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
