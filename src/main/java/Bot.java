import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    UserManager user = new UserManager();
    ContentKeeper contentKeeper = new ContentKeeper();
    String beerString = "";
    String snackString = "";
    DatabaseConnect databaseConnect = null;
    Keyboard keyboard = new Keyboard();
    ArrayList<Long> adminsList = new ArrayList<>();
    int numberOfPage = 0;
    Integer[] tapEditPageNumbers = {3, 4, 5, 6, 7, 8};
    List<Integer> pageNumbersList = Arrays.asList(tapEditPageNumbers);
    String mailingText = "";
    int counterBeer = 0;
    int counterSnacks = 0;
    int counterAbout = 0;
    HashMap<Long,Boolean> userMap = null;
    String about = "";

    Bot() {
        databaseConnect = new DatabaseConnect();
        databaseConnect.connectEstablish();
        beerString = "Сегодня на кранах:" + "\n" + "\n" + contentKeeper.getItemsString(contentKeeper
                .getListOfBeer(databaseConnect.connection));
        snackString = "У нас есть кое-что к пиву:" + "\n" + "\n" + contentKeeper.getItemsString(contentKeeper
                .getListOfSnacks(databaseConnect.connection)) + "\n"
                + "P.S. Также Вы можете заказать доставку из Додо в наш бар со скидкой 15%!";
        userMap = user.getUsersIdAndSub(databaseConnect.connection);
        adminsList.add(361208695L);
        adminsList.add(337817426L);
        adminsList.add(232084100L);
        setUpTimer();
        about = contentKeeper.getAbout();
    }

    Bot (String s) {
        if (s.equals("plain")) {

        }
        else {
            databaseConnect = new DatabaseConnect();
            databaseConnect.connectEstablish();
        }
    }



    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        // check if the update has a message and the message has text
        if (message != null && message.hasText() && adminsList.contains(message.getChatId())) {
            System.out.println(message.getChat().getUserName() + " написал: " + message.getText());
            // read user's message and send an answer
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах":
                    sendMsg(message, beerString, "setButtonsGeneralAdmin");
                    counterBeer++;
                    break;
                case "\uD83E\uDD68 закуски":
                    sendMsg(message,snackString,"setButtonsGeneralAdmin");
                    counterSnacks++;
                    break;

                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about, "setButtonsGeneralAdmin");
                    counterAbout++;
                    break;

                case "/start":
                    sendMsg(message.getChatId(),"Добро пожаловать в Hoppy craft bar!");
//                    sendPhoto(message);
                    break;

                case "\uD83D\uDEE0 Настройки":
                    sendMsg(message, "Привет, Админ! Что будем настраивать?", "setButtonsAdminPanel");
                    numberOfPage = 1;
                    break;

                case "Отредактировать краны":
                    sendMsg(message, "Какой кран будем редактировать?", "setTapFix");
                    numberOfPage = 2;
                    break;

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
                    int iterator = 0;
                    for (UserManager user : userList) {
                            if (iterator < 30) {
                                stringOfUsers += counter + ": " + user.toString() + "\n";
                                counter++;
                                iterator++;
                            }
                            else {
                                sendMsg(message, stringOfUsers);
                                iterator = 0;
                                stringOfUsers = "";
                            }
                    }
                    sendMsg(message, stringOfUsers);
                    break;

                case "Сделать рассылку":
                    sendMsg(message, "Введи текст рассылки", "sendMessageKeyboard");
                    numberOfPage = 9;
                    break;

                case "Отправить":
                    if (!mailingText.equals("") && numberOfPage == 10) {

                        new MessageSender().sendMessage(mailingText, databaseConnect.connection);
                        sendMsg(message, "Текст отправлен " + new UserManager()
                                .getNumberOfUsers(databaseConnect.connection)
                                + " контакту/ам", "setButtonsGeneralAdmin");
                        mailingText = "";
                        numberOfPage = 0;
                        userMap = user.getUsersIdAndSub(databaseConnect.connection);
                    }
                    else {
                        sendMsg(message, "Введи текст рассылки", "sendMessageKeyboard");
                        numberOfPage = 9;
                    }
                    break;
                case "Фото":
                    //  sendPhoto(message);
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
                        beerString = "Сегодня на кранах:" + "\n" + "\n" + contentKeeper
                                .getItemsString(contentKeeper.getListOfBeer(databaseConnect.connection));
                    }
                    else {
                        switch (numberOfPage) {
                            case 9:
                                sendMsg(message, "Будет отправлен следующий текст: " + message.getText(),
                                        "sendMessageKeyboard");
                                numberOfPage = 10;
                                mailingText = message.getText();
                                break;
                        }
                    }
            }
        } else {
            System.out.println(message.getChat().getUserName() + " написал: " + message.getText());
            switch (message.getText()) {
                case "\uD83C\uDF7A на кранах":
                    sendMsg(message, beerString, "setButtonsGeneral");
                    counterBeer++;
                    break;
                case "\uD83C\uDFE1 о нас":
                    sendMsg(message, about, "setButtonsGeneral");
                    counterAbout++;
                    break;
                case "/start":
                    userMap = user.getUsersIdAndSub(databaseConnect.connection);
                    // check if user exists in database and add if not. Send the welcoming photo
                    if (!userMap.containsKey(message.getChatId())) {
                        UserManager user = new UserManager(message);
                        user.userCheck(databaseConnect.connection);
                        sendMsg(adminsList.get(0),"Пользователь " + message.getChat().getFirstName() + " добавлен!");
                        System.out.println("Пользователь " + message.getChat().getFirstName() + " добавлен!");
                        sendMsg(message.getChatId(),"Добро пожаловать в Hoppy craft bar!");
                    }

                    // if we have the contact in DB send plane text without photo
                    else if (userMap.containsKey(message.getChatId())) {
                        // check if user's is_subscribes status equals false and set it to true if yes
                        if (userMap.get(message.getChatId()).equals(false)) {
                            user.setIsSubscribedTrue(databaseConnect.connection,message.getChatId());
                        }
                        sendMsg(message, "Добро пожаловать в Hoppy craft bar!", "setButtonsGeneral");
                    }
                    userMap = user.getUsersIdAndSub(databaseConnect.connection);
                    break;

                case "\uD83E\uDD68 закуски":
                    sendMsg(message,snackString,"setButtonsGeneral");
                    counterSnacks++;
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_NAME");
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

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
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
            if (!adminsList.contains(chatId)) {
                user.setIsSubscribedFalse(databaseConnect.connection, chatId);
            }
        }
    }
//    public void sendPhoto(Message message) {
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(message.getChatId().toString());
//        keyboard.setButtonsGeneral(sendPhoto);
//        File file = this.getClass().getResourceAsStream("img.jpg");
//        sendPhoto.setPhoto(this.getClass().getResourceAsStream("img.jpg"));
//        sendPhoto.setCaption("Добро пожаловать в Hoppy craft bar!");
//        try {
//            execute(sendPhoto);
//        }
//        catch (TelegramApiException e) {
//            e.printStackTrace();
//
//        }
//    }
    public void sendCounters () {
        for (Long admin :adminsList) {
            sendMsg(admin,"Число запросов кранов: " + counterBeer + "\n" + "Число запросов закусок: " + counterSnacks
                    + "\n" + "Число запросов О нас: " + counterAbout);
        }

    }

    public void setUpTimer() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                userMap = user.getUsersIdAndSub(databaseConnect.connection);
                Method sentToAdm = Method.ADM;
                Method sentToBD = Method.BD;
                LocalTime time1 = LocalTime.of(22,00);
                LocalTime time2 = LocalTime.of(23,00);
                ZoneId z = ZoneId.of("Europe/Moscow");
                LocalTime now = LocalTime.now(z);
                Runnable runnable = new Counter(counterBeer,counterSnacks,counterAbout,databaseConnect.connection,
                        sentToBD);
                runnable.run();
                if (now.isAfter(time1) && now.isBefore(time2)) {
                    Runnable sendToAdm = new Counter(counterBeer,counterSnacks,counterAbout,databaseConnect.connection,
                            sentToAdm);
                    sendToAdm.run();

                }
                counterAbout = 0;
                counterSnacks = 0;
                counterBeer = 0;
                runnable = null;
            }
        }, 0, 1000 * 60 * 60);
    }
}
