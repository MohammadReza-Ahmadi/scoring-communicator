package com.vosouq.scoringcommunicator.infrastructures;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages {

    private final MessageSource messageSource;
    private MessageSourceAccessor accessor;

    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, new Locale(Constants.LOCALE_FA));
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

    public String getEn(String code) {
        return accessor.getMessage(code, Locale.ENGLISH);
    }
}