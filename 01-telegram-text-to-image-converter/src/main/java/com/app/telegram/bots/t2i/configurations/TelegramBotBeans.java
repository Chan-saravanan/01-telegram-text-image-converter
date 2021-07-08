package com.app.telegram.bots.t2i.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotBeans {
	@Bean
	public TelegramBotsApi createTelegramBotApi() {
		TelegramBotsApi botApi = null;
		try{
			botApi = new TelegramBotsApi(DefaultBotSession.class);
		}catch(TelegramApiException tae) {
			throw new RuntimeException(tae);
		}
		return botApi;
	}
}
