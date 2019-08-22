package com.company.stockman2.web.errorhandlers;

import com.haulmont.cuba.core.global.validation.CustomValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("stockman2_CustomValidationExceptionHandler")
public class CustomValidationExceptionHandler  extends AbstractUiExceptionHandler {
    public CustomValidationExceptionHandler() {
        super(CustomValidationException.class.getName());
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        context.getNotifications()
                .create(Notifications.NotificationType.WARNING)
                .withCaption(throwable.getMessage())
                .withPosition(Notifications.Position.MIDDLE_CENTER)
                .show();
    }
}
