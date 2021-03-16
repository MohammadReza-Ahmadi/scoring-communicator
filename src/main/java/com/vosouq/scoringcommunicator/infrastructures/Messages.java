package com.vosouq.scoringcommunicator.infrastructures;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class Messages {

    private final MessageSource creditMessageSource;
    private MessageSourceAccessor accessor;

    public Messages(MessageSource creditMessageSource) {
        this.creditMessageSource = creditMessageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(creditMessageSource, new Locale(Constants.LOCALE_FA));
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }

    public String getEn(String code) {
        return accessor.getMessage(code, Locale.ENGLISH);
    }
}