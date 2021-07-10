package com.app.telegram.bots.t2i.configurations;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="bot.api")
public class BotConfiguration{
	private static final Logger logger = Logger.getLogger(BotConfiguration.class.getName());
	private String username;
	private String token;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "BotConfiguration [username=" + username + ", token=" + token + "]";
	}
	
	@PostConstruct
	protected void postConstruct() {
		logger.info("BotConfiguration's post construct called!");
		this.username = System.getenv(username);
		this.token = System.getenv(token);
		logger.info("BotConfiguration ->username=" + username + ", token=" + token);
	}
}
