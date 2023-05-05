package com.opezdal.Telebot.config;

import com.opezdal.Telebot.listeners.Listener;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BotInit {
    private TelegramBot telegramBot;
    @Autowired
    private BotConfig botConfig;

    @EventListener({ContextRefreshedEvent.class})
    public void init(){
        System.out.println(botConfig.botName);
        System.out.println(botConfig.token);
        telegramBot = new TelegramBot(botConfig.token);
        telegramBot.setUpdatesListener(new Listener(telegramBot));
    }
}
