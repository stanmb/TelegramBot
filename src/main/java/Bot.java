import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
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
    ArrayList<Long> adminsList = new ArrayList<>();
    int numberOfPage = 0;
    Integer[] tapEditPageNumbers = {3, 4, 5, 6, 7, 8};
    List<Integer> pageNumbersList = Arrays.asList(tapEditPageNumbers);
    String mailingText = "";

    Bot() {
        databaseConnect = new DatabaseConnect();
        databaseConnect.connectEstablish();
        list = contentKeeper.getListOfBeer(databaseConnect.connection);
        userList = user.getUsersIds(databaseConnect.connection);
        adminsList.add(361208695L);
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
        if (message != null && message.hasText() && adminsList.contains(message.getChatId())) {
            System.out.println(message);
            // check if user exists in database and add if not
            if (!userList.contains(message.getChatId())) {
                UserManager user = new UserManager(message);
                user.userCheck(databaseConnect.connection);
                System.out.println("Пользователь " + message.getChat().getUserName() + " добавлен!");
                userList = this.user.getUsersIds(databaseConnect.connection);
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
                    numberOfPage = 1;
                    break;

                case "Отредактировать краны":
                    sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                    numberOfPage = 2;
                    break;

                /*case "Отредактировать \"о нас\"":
                    sendMsg(message, "Какой текст будет теперь?", "edit");
                    numberOfPage = "3";
                    break;
*/
                case "В начало":
                    sendMsg(message, "Вернулись на начало", "setButtonsGeneralAdmin");
                    numberOfPage = 0;
                    break;
                case "1":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 3;
                    break;

                case "2":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 4;
                    break;

                case "3":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 5;
                    break;

                case "4":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 6;
                    break;

                case "5":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 7;
                    break;

                case "6":
                    sendMsg(message, "Введи Название, Алкоголь и цену через \"/\" , например:" +
                            " Kriek Boon, вишневый ламбик/4.0%/0.5 л - 300₽", "back");
                    numberOfPage = 8;
                    break;
                case "Список подписчиков":
                    ArrayList<UserManager> userList = new UserManager().getUserList(databaseConnect.connection);
                    String stringOfUsers = "";
                    int counter = 1;
                    for (UserManager user : userList) {
                        stringOfUsers += counter + ": " + user.toString() + "\n";
                    }
                    sendMsg(message, stringOfUsers);
                    break;

                case "Сделать рассылку":
                    sendMsg(message, "Введи текст рассылки", "sendMessageKeyboard");
                    numberOfPage = 9;
                    break;

                case "Отправить":
                    if (!mailingText.equals("") && numberOfPage == 10) {

                        sendMsg(message, "Текст отправлен " + new UserManager().getNumberOfUsers(databaseConnect.connection)
                                + " контакту/ам", "setButtonsGeneralAdmin");
                        new MessageSender().sendMessage(mailingText, databaseConnect.connection);
                        mailingText = "";
                        numberOfPage = 0;
                    }
                    else {
                        sendMsg(message, "Введи текст рассылки", "sendMessageKeyboard");
                    }
                    break;


                case "Назад":
                    switch (numberOfPage) {
                        case 1:
                            sendMsg(message, "Вернулись назад", "setButtonsGeneralAdmin");
                            break;

                        case 2:
                        case 9:
                            sendMsg(message, "Вернулись назад", "setButtonsAdminPanel");
                            numberOfPage = 1;
                            break;

                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                            numberOfPage = 2;
                            break;
                        case 10:
                            mailingText = "";
                            numberOfPage = 9;
                            sendMsg(message,"Введи новый текст", "sendMessageKeyboard" );
                    }
                    break;

                default:
                    if (pageNumbersList.contains(numberOfPage)) {
                        String[] strings = null;
                        strings = message.getText().split("/");
                        Beer beer = new Beer(strings[0], strings[1], strings[2]);
                        switch (numberOfPage) {
                            case 3:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "1"));
                                break;
                            case 4:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "2"));
                                break;
                            case 5:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "3"));
                                break;
                            case 6:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "4"));
                                break;
                            case 7:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "5"));
                                break;
                            case 8:
                                sendMsg(message, contentKeeper.addBeerToDatabase(beer, databaseConnect.connection, "6"));
                                break;

                        }
                        list.clear();
                        list = contentKeeper.getListOfBeer(databaseConnect.connection);
                    }
                    else {
                        switch (numberOfPage) {
                            case 9:
                                sendMsg(message, "Будет отправлен следующий текст: " + message.getText(),"sendMessageKeyboard");
                                numberOfPage = 10;
                                mailingText = message.getText();
                                break;
                        }
                    }
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
                case "sendMessageKeyboard":
                    keyboard.sendMessageKeyboard(sendMessage);
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
    public void sendMsg(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
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
