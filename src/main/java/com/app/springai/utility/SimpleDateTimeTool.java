package com.app.springai.utility;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

public class SimpleDateTimeTool {

    @Tool(description = "Get the current date and time in the user's timezone",returnDirect = false)
    public String getCurrentDateTime() {
        return LocalDateTime
                .now()
                .atZone(LocaleContextHolder.getTimeZone().toZoneId())
                .toString();
    }
}
