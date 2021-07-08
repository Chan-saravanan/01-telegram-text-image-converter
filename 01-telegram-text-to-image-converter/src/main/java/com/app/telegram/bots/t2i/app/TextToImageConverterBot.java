package com.app.telegram.bots.t2i.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.app.telegram.bots.t2i.ImageTransformService;
import com.app.telegram.bots.t2i.configurations.BotConfiguration;
import com.app.telegram.bots.t2i.models.ImageInfo;


@Component
public class TextToImageConverterBot extends TelegramLongPollingBot{

	private static final Logger logger = Logger.getLogger(TextToImageConverterBot.class.getName());
	
	@Autowired
	private ImageTransformService service;
	@Autowired
	private BotConfiguration botConfiguration;
	
	@Override
	public void onUpdateReceived(Update update) {
		logger.info(" update ---------------->"+update);
		logger.info("update received!");
		if(update.hasMessage())
		{
			Message rcvdMsg = update.getMessage();
			Long chatId = rcvdMsg.getChatId();
			SendMessage sendMessage = new SendMessage();
			sendMessage.setChatId(Long.toString(chatId));
			sendMessage.setText("Invalid Response!");
			sendMessage.setAllowSendingWithoutReply(true);
			
			logger.info("Response Message :".concat(rcvdMsg.getText()));
			provideDefaultActionKeyboard(sendMessage);
			
			if(ActionConstants.START.equals(rcvdMsg.getText()))
				sendMessage.setText("Text to image converter bot started!");
			
			//prepare start keyboard!
			if(ActionConstants.ACTION_ENTER_TEXT.equals(rcvdMsg.getText()))
				textInterface(rcvdMsg, sendMessage);
			
			if(rcvdMsg.getText().startsWith("__"))
			{
				convertTextToImage(rcvdMsg);
				return;
			}
			
			try {
				execute(sendMessage);
			}catch(TelegramApiException tae) {
				logger.info("Exception : "+tae.getMessage());
				throw new RuntimeException(tae);
			}
		}
	}

	@Override
	public String getBotUsername() {
		return botConfiguration.getUsername();
	}

	@Override
	public String getBotToken() {
		return botConfiguration.getToken();
	}
	
	private void provideDefaultActionKeyboard(SendMessage sendMessage) {
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        KeyboardButton enterText = new KeyboardButton(ActionConstants.ACTION_ENTER_TEXT);
        row.add(enterText);
        keyboard.add(row);
        
        row = new KeyboardRow();
        KeyboardButton normalText = new KeyboardButton(ActionConstants.ACTION_NORMAL_TEXT);
        KeyboardButton camelText = new KeyboardButton(ActionConstants.ACTION_CAMEL_TEXT);
        row.add(normalText);
        row.add(camelText);
        keyboard.add(row);
        
        row = new KeyboardRow();
        KeyboardButton setFont = new KeyboardButton(ActionConstants.ACTION_CHANGE_FONT);
        KeyboardButton setSize = new KeyboardButton(ActionConstants.ACTION_CHANGE_SIZE);
        row.add(setFont);
        row.add(setSize);
        keyboard.add(row);
        
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        sendMessage.setReplyMarkup(keyboardMarkup);
	}
	
	private void textInterface(Message rcvdMessage, SendMessage message) {
		message.setReplyMarkup(null);
		message.setReplyToMessageId(rcvdMessage.getMessageId());
		message.setText("Enter text with __ as prefix!");
	}
	
	private final void convertTextToImage(Message rcvdMessage) {
		logger.info("Performing text to image conversion!");
		String text = rcvdMessage.getText().substring(rcvdMessage.getText().indexOf("__")+2, rcvdMessage.getText().length());
		logger.info("User Text :"+text);
		
		InputFile file = new InputFile();
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setText(text);
		InputStream is = service.camelTextToImageStream(imageInfo);
		file.setMedia(is, "file");
		
		SendPhoto photo = new SendPhoto(Long.toString(rcvdMessage.getChat().getId()), file);
		photo.setAllowSendingWithoutReply(true);
		try {
			execute(photo);
		}catch(TelegramApiException tae) {
			logger.info("Exception in Send Photo!");
			tae.printStackTrace();
		}
	}
}
