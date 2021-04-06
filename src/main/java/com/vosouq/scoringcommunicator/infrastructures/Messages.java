package com.vosouq.scoringcommunicator.infrastructures;

import com.vosouq.scoringcommunicator.models.UnitType;
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

    public String getUnitTitle(UnitType type) {
        return accessor.getMessage(Constants.UNIT + Constants.DOT + type.name().toLowerCase());
    }

    public String getHavingTitle(boolean value) {
        return value ? accessor.getMessage(Constants.VALUE_DOT_HAS) : accessor.getMessage(Constants.VALUE_DOT_HAS_NOT);
    }

    public String getEn(String code) {
        return accessor.getMessage(code, Locale.ENGLISH);
    }
}