package com.teiavirtual.wsteste;

import java.util.Timer;
import java.util.TimerTask;

public class ControleTempo {
    int initialDelay = 5000;
    int period = 3000;
    private Timer timer = new Timer();
    public ControleTempo(final WsCreator wsCreator) {
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                wsCreator.enviarPing();
            }
        }, initialDelay, period);
    }
}
