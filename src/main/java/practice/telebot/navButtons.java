package practice.telebot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class navButtons {
    
    private InlineKeyboardButton nextButton;
    private InlineKeyboardButton prevButton;
 
    

    public navButtons(int pageIndex) {
        this.nextButton = InlineKeyboardButton.builder().text("next").callbackData( "page" + ":" + Integer.toString(pageIndex + 1)).build();;
        this.prevButton = InlineKeyboardButton.builder().text("previous").callbackData("page" + ":" + Integer.toString(pageIndex - 1)).build();;
    }



    public List<InlineKeyboardButton> getButtonRow() {
        
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();
        buttonRow.add(this.prevButton);
        buttonRow.add(this.nextButton);
        
        return buttonRow;

    }
}
