package com.tasks.core;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

public class FieldValidator {

    public static boolean validateMailPattern(Context context, String login) {
        String emailPattern = context.getString(R.string.mailPattern);
        return Pattern.matches(emailPattern, login);
    }

    public static boolean validatePasswordPattern(String password) {
        return password.length() >= 6 && !password.contains(" ");
    }

    public static boolean validateNamePattern(String name) {
        return name.length() >= 2 && !name.substring(0, 2).contains(" ");
    }

    public static <T> boolean toastValidateAnyField(
            Context context,
            T value,
            Validator<T> validator,
            String badReflectString) {
        boolean resultOfValidate = validator.validate(value);
        if (!resultOfValidate) {
            Toast.makeText(context, badReflectString, Toast.LENGTH_LONG).show();
        }
        return resultOfValidate;
    }

    public static <T> boolean alertValidateAnyField(
            Context context,
            T value,
            Validator<T> validator,
            String badReflectString,
            String positiveButtonText,
            Runnable positiveButtonAction,
            String negativeButtonText,
            Runnable negativeButtonAction) {
        boolean resultOfValidate = validator.validate(value);
        if (!resultOfValidate) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle(R.string.networkErrorTitle)
                    .setMessage(badReflectString)
                    .setPositiveButton(positiveButtonText, (dialog, which) -> positiveButtonAction.run());
            if (negativeButtonText != null) {
                dialogBuilder.setNegativeButton(negativeButtonText, (dialog, which) -> negativeButtonAction.run());
            }
            dialogBuilder.create().show();
        }
        return resultOfValidate;
    }

    public interface Validator<T> {
        boolean validate(T value);
    }
}