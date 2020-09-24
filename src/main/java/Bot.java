import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    ContentKeeper contentKeeper = new ContentKeeper();
    ArrayList<Beer> list = null;
    Bot() {
        list = contentKeeper.getListOfBeer1();
    }

    File aboutFilePath = new File("C:\\Users\\andre\\Desktop\\about.txt");
    File beerFilePath = new File("C:\\Users\\andre\\Desktop\\list.txt");
    Keyboard keyboard = new Keyboard();
    int admin = 361208695;
    String numberOfPage = "0";

    public static void main(String[] args) {
        System.out.println("AAAAAA!");
    }

    @Override
    public String getBotToken() {
        return "1206497799:AAHaD_piNaWl-MoJa5NZHRSicSMvZsb2Wp0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String about = contentKeeper.getAbout(aboutFilePath);
        Message message = update.getMessage();
        
        // check if the update has a message and the message has text
        if (message != null && message.hasText()) {
            System.out.println(message);
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах" :
                    if (message.getChatId() == admin) {
                        sendMsg(message, list, "setButtonsGeneralAdmin");
                    }
                    else {
                        sendMsg(message,list, "setButtonsGeneral");
                    }
                    break;
                case "\uD83C\uDFE1 о нас":
                    if (message.getChatId() == admin) {
                        sendMsg(message, about, "setButtonsGeneralAdmin");
                    }
                    else {
                        sendMsg(message, about, "setButtonsGeneral");
                    }
                    break;
                case "/start":
                    if (message.getChatId() == admin) {
                        sendMsg(message, "Привет!", "setButtonsGeneralAdmin");
                    }
                    else {
                        sendMsg(message, "Привет!", "setButtonsGeneral");
                    }
                    break;
                case "\uD83D\uDEE0 Настройки":
                    if (message.getChatId() == admin) {
                        sendMsg(message, "Привет, Админ! Что будем настраивать?", "setButtonsAdminPanel");
                        numberOfPage = "1";
                    }
                    break;
                case "Отредактировать краны":
                    if (message.getChatId() == admin) {
                        sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                        numberOfPage = "2";
                    }
                    break;
                case "Отредактировать \"о нас\"":
                    if (message.getChatId() == admin) {
                        sendMsg(message,"Какой текст будет теперь?", "aboutEdit");
                        numberOfPage = "3";
                    }
                    break;

                case "Назад":
                    if (message.getChatId() == admin) {
                        switch (numberOfPage) {
                            case "1":
                                sendMsg(message, "Вернулись назад", "setButtonsGeneralAdmin");
                                break;
                            case "2":
                            case "3":
                                sendMsg(message, "Вернулись назад", "setButtonsAdminPanel");
                                break;
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YaListBot";
    }




    public void sendMsg(Message message,String text, String nameOfKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//      sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText("\n" + text);
        try {
            switch (nameOfKeyboard) {
                case "setButtonsGeneralAdmin" :
                    keyboard.setButtonsGeneralAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral" :
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setTapFix" :
                    keyboard.setTap(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsAdminPanel" :
                    keyboard.setButtonsAdminPanel(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "aboutEdit" :
                    keyboard.aboutEdit(sendMessage);
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
             nextBeer += beer.toString();
        }

        sendMessage.setText(nextBeer);

        try {
            switch (nameOfKeyboard) {
                case "setButtonsGeneralAdmin" :
                    keyboard.setButtonsGeneralAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral" :
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
                case "setButtonsAdminPanel" :
                    keyboard.setButtonsAdminPanel(sendMessage);
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
