package com.company.stockman2.web.errorhandlers;

import com.haulmont.cuba.core.global.validation.EntityValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import java.util.stream.Collectors;

@Component("stockman2_CustomValidationExceptionHandler")
public class CustomValidationExceptionHandler  extends AbstractUiExceptionHandler {
    public CustomValidationExceptionHandler() {
        super(EntityValidationException.class.getName());
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        String errorMsg = message;
        if (throwable instanceof EntityValidationException) {
            EntityValidationException ex = (EntityValidationException) throwable;
            errorMsg = ex.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        context.getNotifications()
                .create(Notifications.NotificationType.WARNING)
                .withCaption(errorMsg)
                .withPosition(Notifications.Position.MIDDLE_CENTER)
                .show();
    }
}
