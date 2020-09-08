import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return "1206497799:AAHaD_piNaWl-MoJa5NZHRSicSMvZsb2Wp0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // set variables
        ContentKeeper contentKeeper = new ContentKeeper();
        ArrayList<Beer> list = contentKeeper.getListOfBeer();
        String about = contentKeeper.getAbout();
        Message message = update.getMessage();
        // check if the update has a message and the message has text
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах" :
                    sendMsg(message,list);
                    break;
                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about);
                    break;
                case "/start":
                    sendMsg(message, "Привет!");
                    break;
                default:

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YaListBot";
    }
    // create custom keyboard
    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow keyBoardFirstRow = new KeyboardRow();

        keyBoardFirstRow.add(new KeyboardButton("🍺 на кранах" ));
        keyBoardFirstRow.add(new KeyboardButton("\uD83C\uDFE1 о нас"));

        keyboardRowList.add(keyBoardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }



    public void sendMsg(Message message,String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//      sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("\n" + text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg(Message message,ArrayList<Beer> list) {
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
            setButtons(sendMessage);
            execute(sendMessage);
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
