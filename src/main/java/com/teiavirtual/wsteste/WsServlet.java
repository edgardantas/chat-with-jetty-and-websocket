package com.teiavirtual.wsteste;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(loadOnStartup=1, urlPatterns="/ws")
public class WsServlet extends WebSocketServlet  {

    private final WsCreator wsCreator = new WsCreator();

    @Override
    public void configure(WebSocketServletFactory wsServletFactory) {
        wsServletFactory.setCreator(wsCreator);
    }
}

