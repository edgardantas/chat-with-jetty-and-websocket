package com.teiavirtual.wsteste;

import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WsCreator implements WebSocketCreator {
    // Matriz com os usuários conectados
    private Map<Integer, WSocket> usuarios = new HashMap<Integer, WSocket>();
    private Integer proximoIdUsuario=0; // controle de Id dos usuários
    private ControleTempo controleTempo;

    public WsCreator() {
       controleTempo = new ControleTempo(this);

    }

    @Override
    public WSocket createWebSocket(UpgradeRequest req, UpgradeResponse resp) {
        String nomeUsuario = req.getParameterMap().get("nomeUsuario")[0];
        return new WSocket(++proximoIdUsuario, usuarios, nomeUsuario);
    }

    public void enviarPing() {
        //System.out.println("Enviando ping para os usuários");
        for (WSocket wsocket : usuarios.values()) {
            wsocket.enviarPing();
        }
        removerDesconectados();
    }

    private void removerDesconectados() {
        Map<Integer, WSocket> usuariosRemover = new HashMap<Integer, WSocket>();
        for (WSocket wsocket : usuarios.values()) {
            if(wsocket.isSessaoNull()) {
                usuariosRemover.put(wsocket.getIdUsuario(), wsocket);
            }
        }
        for (WSocket wsocket : usuariosRemover.values()) {
            usuarios.remove(wsocket.getIdUsuario());
        }
    }
}



