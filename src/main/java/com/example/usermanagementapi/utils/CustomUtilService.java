package com.example.usermanagementapi.utils;


import com.example.usermanagementapi.handlers.exceptions.RequiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomUtilService {

    private static String RE_EMAIL;
    private static String RE_PASS;

    @Value("${custom.re-email}")
    public void setReEmail(String reEmail) {
        RE_EMAIL = reEmail;
    }

    @Value("${custom.re-pass}")
    public void setRePass(String rePass) {
        RE_PASS = rePass;
    }

    private static final String RE_EMAIL2 = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static void ValidateRequired(Object object) {
        if (Objects.isNull(object)) {
            throw new RequiredException(MessageResponse.MISSING_REQUIRED_FIELD);
        }
    }

    public static void ValidateBooleanRequired(Object object) {
        if (!(object instanceof Boolean)) {
            throw new RequiredException(MessageResponse.MISSING_REQUIRED_FIELD);
        }
    }

    public static void ValidateEmail(String email) {
        Pattern pattern = Pattern.compile(RE_EMAIL);
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        if (!matches) {
            throw new RequiredException(MessageResponse.INVALID_EMAIL);
        }
    }

    public static void ValidatePassword(String password) {
        Pattern pattern = Pattern.compile(RE_PASS);
        Matcher matcher = pattern.matcher(password);
        boolean matches = matcher.matches();
        if (!matches) {
            throw new RequiredException(MessageResponse.INVALID_PASS);
        }
    }
}
