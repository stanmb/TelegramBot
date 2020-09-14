import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    File aboutFilePath = new File("C:\\Users\\andre\\Desktop\\about.txt");
    File beerFilePath = new File("C:\\Users\\andre\\Desktop\\list.txt");
    Keyboard keyboard = new Keyboard();

    @Override
    public String getBotToken() {
        return "1206497799:AAHaD_piNaWl-MoJa5NZHRSicSMvZsb2Wp0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // set variables
        ContentKeeper contentKeeper = new ContentKeeper();
        ArrayList<Beer> list = contentKeeper.getListOfBeer(beerFilePath);
        String about = contentKeeper.getAbout(aboutFilePath);
        Message message = update.getMessage();
        // check if the update has a message and the message has text
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах" :
                    sendMsg(message,list, "setButtonsGeneral");
                    break;
                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about, "setButtonsAdmin");
                    break;
                case "/start":
                    sendMsg(message, "Привет!", "setButtonsGeneral");
                    break;
                case "\uD83D\uDEE0 Настройки":
                    sendMsg(message,"Привет, Админ! Что будем настраивать?", "setButtonsAdmin" );

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YaListBot";
    }
    // create custom keyboard




    public void sendMsg(Message message,String text, String nameOfKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//      sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("\n" + text);
        try {
            switch (nameOfKeyboard) {
                case "setButtonsAdmin" :
                    keyboard.setButtonsAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral" :
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
            }

        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg(Message message,ArrayList<Beer> list, String nameOfKeyboard) {
        String nextBeer = "Сегодня на кранах:" + "\n" + "\n";

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());

        for (Beer beer: list) {
            nextBeer += beer.getName() + "\n" + beer.getVol() + "\n" + beer.getPriceFor05() + "\n" + "\n" +"\n";
        }

        sendMessage.setText(nextBeer);

        try {
            switch (nameOfKeyboard) {
                case "setButtonsAdmin" :
                    keyboard.setButtonsAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral" :
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
            }

        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(Message message) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setReplyToMessageId(message.getMessageId());
        try {
            sendPhoto.setPhoto("text", new FileInputStream("C:\\Users\\andre\\Desktop\\beer.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            execute(sendPhoto);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
