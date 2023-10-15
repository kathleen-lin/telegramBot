package practice.telebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker.SendStickerBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private String botToken;

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
    
	
    @Override
    public void onUpdateReceived(Update update) {
        // System.out.println("bot is live");
        // create a few commands - confess, sing song, send sticker
        if (update.hasMessage()){
            Message m = update.getMessage();
            SendMessageBuilder smsgBuilder = SendMessage.builder();
            String chatId = m.getChatId().toString();
            User u = update.getMessage().getFrom();
            String userName = u.getUserName();
            if (m.isCommand()){
                switch (m.getText()){
                    case "/confess":
                        System.out.println(userName + "clicked confess");
                        SendMessage confession =smsgBuilder.chatId(chatId).text("I will confess...").build();
                        try {
                            execute(confession);
                            // System.out.println("token: " + token);

                        } catch (TelegramApiException e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        break;

                    case "/sing": 
                        System.out.println(userName + "clicked sing");
                        SendMessage singsong =smsgBuilder.chatId(chatId).text("I will sing a song...").build();
                        try {
                            execute(singsong);
                        } catch (TelegramApiException e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        break;

                    
                    case "/sticker":
                        System.out.println(userName + "clicked sticker");
                        SendMessage sticker =smsgBuilder.chatId(chatId).text("I will send you a sticker...").build();
                        try {
                            execute(sticker);
                        } catch (TelegramApiException e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        break;
                } 
            } else {
                System.out.println(userName + "clicked default");
                System.out.println("message is: " + m.getText());
                SendMessage def = smsgBuilder.chatId(chatId).text(m.getText()).build();
                try {
                    execute(def);
                } catch (TelegramApiException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        
    }
    

    @Override
    public String getBotUsername() {
        // TODO Auto-generated method stub
        return "KlsnBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    
}
