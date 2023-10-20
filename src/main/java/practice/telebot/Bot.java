package practice.telebot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private String botToken;

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Autowired
    private GetFoodSvc foodSvc;
    
	
    @Override
    public void onUpdateReceived(Update update) {
        // System.out.println("bot is live");
        // Try to get location from user
        if (update.hasMessage()){
            Message m = update.getMessage();
            String chatId = m.getChatId().toString();

            if (m.hasLocation()){

                Double latitude = m.getLocation().getLatitude();
                Double longitude = m.getLocation().getLongitude();
                String result = foodSvc.getNearByFood(latitude, longitude, botToken);

                SendMessageBuilder smsgBuilder = SendMessage.builder();
                
                SendMessage msgToSend =smsgBuilder.chatId(chatId).text(result).build();
                try {
                    execute(msgToSend);
                
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            } else {

                SendMessageBuilder smsgBuilder = SendMessage.builder();
               
                SendMessage msgToSend =smsgBuilder.chatId(chatId).text("Send me a location and I will send you food places within 1.5km which are now open!").build();
                try {
                    execute(msgToSend);
                
                }
                catch (TelegramApiException e){
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
