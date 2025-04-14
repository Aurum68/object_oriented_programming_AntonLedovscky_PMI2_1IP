package org.example.handler;

import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

public class SyslogHandler implements ILogHandler{

    private SyslogIF syslog;
    public SyslogHandler() {
        syslog = Syslog.getInstance(SyslogConstants.UDP);
        syslog.getConfig().setHost("127.0.0.1");
        syslog.getConfig().setPort(514);
    }

    @Override
    public void handle(String text) {
        if (text == null) throw new NullPointerException();
        syslog.info(text);
    }
}
