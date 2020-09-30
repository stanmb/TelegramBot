import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    UserManager user = new UserManager();
    ArrayList<Long> userList = null;
    ContentKeeper contentKeeper = new ContentKeeper();
    ArrayList<Beer> list = null;
    DatabaseConnect databaseConnect = null;
    Keyboard keyboard = new Keyboard();
    int admin = 361208695;
    String numberOfPage = "0";
    String[] pageNumbers = {"2-1","2-2","2-3", "2-4", "2-5","2-6"};
    List<String> pageNumbersList = Arrays.asList(pageNumbers);

    Bot() {
        databaseConnect = new DatabaseConnect();
        databaseConnect.connectEstablish();
        list = contentKeeper.getListOfBeer1(databaseConnect.connection);
        userList = user.getUserList(databaseConnect.connection);
    }


    @Override
    public String getBotToken() {
        return "1206497799:AAHaD_piNaWl-MoJa5NZHRSicSMvZsb2Wp0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String about = contentKeeper.getAbout();
        Message message = update.getMessage();

        // check if the update has a message and the message has text
        if (message != null && message.hasText() && message.getChatId() == admin) {
            // check if user exists in database and add if not
            if (!userList.contains(message.getChatId())) {
                UserManager user = new UserManager(message);
                user.userCheck(databaseConnect.connection);
                System.out.println("Пользователь " + message.getChat().getUserName() + " добавлен!");
                userList = this.user.getUserList(databaseConnect.connection);
            }
            // read user's message and send an answer
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах":
                    sendMsg(message, list, "setButtonsGeneralAdmin");
                    break;

                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about, "setButtonsGeneralAdmin");
                    break;

                case "/start":
                    sendMsg(message, "Привет!", "setButtonsGeneralAdmin");
                    break;

                case "\uD83D\uDEE0 Настройки":
                    sendMsg(message, "Привет, Админ! Что будем настраивать?", "setButtonsAdminPanel");
                    numberOfPage = "1";
                    break;

                case "Отредактировать краны":
                    sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                    numberOfPage = "2";
                    break;

                case "Отредактировать \"о нас\"":
                    sendMsg(message, "Какой текст будет теперь?", "edit");
                    numberOfPage = "3";
                    break;

                case "1":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-1";
                    break;

                case "2":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-2";
                    break;

                case "3":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-3";
                    break;

                case "4":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-4";
                    break;

                case "5":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-5";
                    break;

                case "6":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = "2-6";
                    break;

                case "Назад":
                    switch (numberOfPage) {
                        case "1":
                            sendMsg(message, "Вернулись назад", "setButtonsGeneralAdmin");
                            break;

                        case "2":
                        case "3":
                            sendMsg(message, "Вернулись назад 2 or 3", "setButtonsAdminPanel");
                            numberOfPage = "1";
                            break;

                        case "2-1":
                        case "2-2":
                        case "2-3":
                        case "2-4":
                        case "2-5":
                        case "2-6":
                            sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                            numberOfPage = "2";
                            break;
                    }
                    break;

                default:
                    if (pageNumbersList.contains(numberOfPage));
                    String[] strings = null;
                    strings = message.getText().split("/");
                    Beer beer = new Beer(strings[0],strings[1],strings[2]);
                    switch (numberOfPage) {
                        case "2-1":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"1"));
                            break;
                        case "2-2":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"2"));
                            break;
                        case "2-3":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"3"));
                            break;
                        case "2-4":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"4"));
                            break;
                        case "2-5":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"5"));
                            break;
                        case "2-6":
                            sendMsg(message,contentKeeper.addBeerToDatabase(beer,databaseConnect.connection,"6"));
                            break;
                    }
                    list.clear();
                    list = contentKeeper.getListOfBeer1(databaseConnect.connection);
            }
        } else {
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах":
                    sendMsg(message, list, "setButtonsGeneral");
                    break;
                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about, "setButtonsGeneral");
                    break;
                case "/start":
                    sendMsg(message, "Привет!", "setButtonsGeneral");
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YaListBot";
    }


    public void sendMsg(Message message, String text, String nameOfKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("\n" + text);
        try {
            switch (nameOfKeyboard) {
                case "setButtonsGeneralAdmin":
                    keyboard.setButtonsGeneralAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral":
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setTapFix":
                    keyboard.setTap(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsAdminPanel":
                    keyboard.setButtonsAdminPanel(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "edit":
                    keyboard.edit(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "back":
                    keyboard.back(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, ArrayList<Beer> list, String nameOfKeyboard) {
        String nextBeer = "Сегодня на кранах:" + "\n" + "\n";

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());

        for (Beer beer : list) {
            nextBeer += beer.toString();
        }

        sendMessage.setText(nextBeer);

        try {
            switch (nameOfKeyboard) {
                case "setButtonsGeneralAdmin":
                    keyboard.setButtonsGeneralAdmin(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;

                case "setButtonsGeneral":
                    keyboard.setButtonsGeneral(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
                case "setButtonsAdminPanel":
                    keyboard.setButtonsAdminPanel(sendMessage);
                    execute(sendMessage);
                    // keyboard.clearKeyboard(sendMessage);
                    break;
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
