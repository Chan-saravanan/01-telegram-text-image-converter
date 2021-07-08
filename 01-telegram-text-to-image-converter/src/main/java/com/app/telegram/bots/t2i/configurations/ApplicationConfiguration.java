package com.app.telegram.bots.t2i.configurations;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.app.telegram.bots.t2i.app.TextToImageConverterBot;


@Configuration
public class ApplicationConfiguration {
	private static final Logger logger = Logger.getLogger(ApplicationConfiguration.class.getName());
	
	@Autowired
	public ApplicationConfiguration(TelegramBotsApi botApi, TextToImageConverterBot converterBot) {
		logger.info("username :"+converterBot.getBotUsername()+" :: token :"+converterBot.getBotToken());
		try {
			botApi.registerBot(converterBot);
		}catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
		logger.info("ApplicationConfiguration is complete!");
	}
	
}
