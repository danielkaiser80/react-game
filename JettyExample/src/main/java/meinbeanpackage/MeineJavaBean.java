package meinbeanpackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeineJavaBean {
    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
