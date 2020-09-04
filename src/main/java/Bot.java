import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public String getBotToken() {
        return "1206497799:AAHaD_piNaWl-MoJa5NZHRSicSMvZsb2Wp0";
    }

    public void onUpdateReceived(Update update) {
        ListOfBeer listOfBeer = new ListOfBeer();
        ArrayList<Beer> list = listOfBeer.getListOfBeer();
        String about = listOfBeer.getAbout();

        Message message = update.getMessage();
        System.out.println(message);
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

    public String getBotUsername() {
        return "YaListBot";
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
