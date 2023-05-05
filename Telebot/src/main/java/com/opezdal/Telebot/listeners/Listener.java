package com.opezdal.Telebot.listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class Listener implements UpdatesListener {

    private final TelegramBot bot;

    public Listener(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        var targetFilter = updates.stream().filter(update -> update.message() != null).collect(Collectors.toList());
        if(targetFilter.isEmpty()){
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
        handleUpdates(updates);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void handleUpdates(List<Update> updates) {
        updates.stream().forEach(update -> {
//            System.out.println("UPDATE!");
            long chatId = update.message().chat().id();
            bot.execute(new SendMessage(chatId, update.message().text()));
        });
    }
}
