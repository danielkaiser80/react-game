package meinbeanpackage;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class MeineJavaBean {
    public String getDateString() {
        return (new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss")).format(new Date()) + " h";
    }
}
