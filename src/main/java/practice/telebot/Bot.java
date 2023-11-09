package practice.telebot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private String botToken;

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Autowired
    private GetFoodSvc foodSvc;

    List<Listing> shopsOpenNow = new LinkedList<Listing>();
    int pageIndex = 0;
    int listingPerPage = 5;
	
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
                shopsOpenNow = foodSvc.getNearByFood(latitude, longitude);
               
                SendMessageBuilder smsgBuilder = SendMessage.builder();





                // for (int i = pageIndex * 5 ; i < pageIndex * 5 - 1; i++) {
                //     SendMessage msgToSend =smsgBuilder.chatId(chatId).text("Result " + String.valueOf(i + 1) + "\n" + shopsOpenNow.get(i).toString()).build();

                //     try {
                //         execute(msgToSend);
                    
                //     }
                //     catch (TelegramApiException e){
                //         e.printStackTrace();
                //     }
                // }

                // try to get a sublist based on limit/offset
                List <Listing> pageResult = shopsOpenNow.subList(Math.min(shopsOpenNow.size(), pageIndex * listingPerPage), Math.min(shopsOpenNow.size(), pageIndex * listingPerPage + listingPerPage));
                
                for (int i = 0; i < pageResult.size(); i++) {
                    SendMessage msgToSend =smsgBuilder.chatId(chatId).text("Result " + String.valueOf(i + 1) + "\n" + pageResult.get(i).toString()).build();

                    try {
                        execute(msgToSend);
                    
                    }
                    catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                }


                // callback data should contain information about which page it is navigating to
                String nextCallBack = "page" + ":" + Integer.toString(pageIndex + 1);                
                String previousCallBack = "page" + ":" + Integer.toString(pageIndex - 1);


                // send a next button after first 5 results
                var nextButton = InlineKeyboardButton.builder().text("next").callbackData(nextCallBack).build();

                var previousButton = InlineKeyboardButton.builder().text("previous").callbackData(previousCallBack).build();

                List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
                buttonsRow.add(previousButton);
                buttonsRow.add(nextButton);
                
                // keyboard
                InlineKeyboardMarkup kb = InlineKeyboardMarkup.builder().keyboardRow(buttonsRow).build();
                
                String textMessgae = "page " + String.valueOf(pageIndex+1) + "/" + String.valueOf(Math.floorDiv(shopsOpenNow.size(),listingPerPage));
                SendMessage sendButton =SendMessage.builder().chatId(chatId).parseMode("HTML").text(textMessgae).replyMarkup(kb).build();

                    try {
                        execute(sendButton);
                    
                    }
                    catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                    

            }
            
            else {

                SendMessageBuilder smsgBuilder = SendMessage.builder();
               
                SendMessage msgToSend =smsgBuilder.chatId(chatId).text("Send me a location and I will send you food places within 1.5km which are now open!").build();
                try {
                    execute(msgToSend);
                
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
        
        } if (update.hasCallbackQuery()) {


                CallbackQuery cb = update.getCallbackQuery();
                // System.out.println(cb.getData());

                
                int msgId = cb.getMessage().getMessageId();
                // String chatId = cb.getId();
                String chatId = cb.getMessage().getChat().getId().toString();
                
                // System.out.println(cb.getFrom().getUserName());    
                // System.out.println(msgId);                
                // System.out.println(chatId);
                // System.out.println(cb.getData()); correctly return either page1 or page-1

                // Different conditions based on call back
                String callback = cb.getData();
                String pageToShow = callback.split(":")[1];
                
                int pageNum = Integer.valueOf(pageToShow);
                System.out.println("Page to show = " + String.valueOf(pageNum));

                if (pageNum > 0) {
                    
                    pageIndex = pageNum;
                    System.out.println("should list out next page");
                    System.out.println("------------------------------");
                    List <Listing> pageResult = shopsOpenNow.subList(Math.min(shopsOpenNow.size(), pageIndex * listingPerPage), Math.min(shopsOpenNow.size(), pageIndex * listingPerPage + listingPerPage));

                    System.out.println(pageResult.toString());
                
                    for (int i = 0; i < pageResult.size(); i++) {
                        SendMessage msgToSend =SendMessage.builder().chatId(chatId).text("Result " + String.valueOf(i + 1) + "\n" + pageResult.get(i).toString()).build();

                        try {
                            execute(msgToSend);
                        
                        }
                        catch (TelegramApiException e){
                            e.printStackTrace();
                        }
                    }

                    String nextCallBack = "page" + ":" + Integer.toString(pageIndex + 1);                
                    String previousCallBack = "page" + ":" + Integer.toString(pageIndex - 1);


                    // send a next button after first 5 results
                    var nextButton = InlineKeyboardButton.builder().text("next").callbackData(nextCallBack).build();

                    var previousButton = InlineKeyboardButton.builder().text("previous").callbackData(previousCallBack).build();

                    List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
                    buttonsRow.add(previousButton);
                    buttonsRow.add(nextButton);
                    
                    // keyboard
                    InlineKeyboardMarkup kb = InlineKeyboardMarkup.builder().keyboardRow(buttonsRow).build();

                    String textMessgae = "page " + String.valueOf(pageIndex+1) + "/" + String.valueOf(Math.floorDiv(shopsOpenNow.size(),listingPerPage));

                    SendMessage sendButton =SendMessage.builder().chatId(chatId).text(textMessgae).parseMode("HTML").replyMarkup(kb).build();

                        try {
                            execute(sendButton);
                        
                        }
                        catch (TelegramApiException e){
                            e.printStackTrace();
                        }
                        


                }
                


                // EditMessageText newTxt = EditMessageText.builder()
                // .chatId(chatId.toString())
                // .messageId(msgId).text("").build();

                // EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                //         .chatId(chatId.toString()).messageId(msgId).build();
                
                // var button1 = InlineKeyboardButton.builder().text("button1").callbackData("button1").build();

                // var button2 = InlineKeyboardButton.builder().text("button2").callbackData("button2").build();

                // var button3 = InlineKeyboardButton.builder().text("button3").callbackData("button3").build();

                // List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
                // buttonsRow.add(button2);
                // buttonsRow.add(button3);

                // InlineKeyboardMarkup kb1 = InlineKeyboardMarkup.builder().keyboardRow(List.of(button1)).build();

                // // keyboard
                // InlineKeyboardMarkup kb2 = InlineKeyboardMarkup.builder().keyboardRow(buttonsRow).build();

                // if(cb.getData().equals("next")) {
                //     newTxt.setText("MENU 2");
                //     newKb.setReplyMarkup(kb1);
                // } else {
                //     newTxt.setText("MENU 1");
                //     newKb.setReplyMarkup(kb2);
                // }

                AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                            .callbackQueryId(cb.getId()).build();

                try {
                    execute(close);
                } catch (TelegramApiException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // try {
                //     execute(newTxt);
                // } catch (TelegramApiException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }
                // try {
                //     execute(newKb);
                // } catch (TelegramApiException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }
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
