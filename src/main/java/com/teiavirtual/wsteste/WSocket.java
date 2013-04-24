package com.teiavirtual.wsteste;


import org.eclipse.jetty.websocket.api.Session;

import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.extensions.Frame;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;


@WebSocket(maxIdleTime = 3600000)
public class WSocket {

    private Session sessao;
    private Map<Integer, WSocket> usuarios;
    private Integer idUsuario;
    private String nomeUsuario;

    public WSocket(Integer idUsuario, Map<Integer, WSocket> usuarios, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.usuarios = usuarios;
        this.nomeUsuario = nomeUsuario;
    }

    @OnWebSocketConnect
    public void onConnect(Session sessao) {
        System.out.println("Usuário ["+ this.nomeUsuario + "] conectado!") ;
        this.sessao = sessao;
        this.usuarios.put(this.idUsuario, this);
        enviarParaOutros("@entrou;"+ this.idUsuario + ";" + this.nomeUsuario);
        enviarUsuariosOnline();

    }

    @OnWebSocketClose
    public void onClose(int codigo, String msg) {
        this.sessao = null;
        enviarParaTodos("@saiu;"+ this.idUsuario + ";" + this.nomeUsuario);
        System.out.println("Conexão fechada, codigo = " + codigo + ", msg = " + msg + ", idUsuario= " + idUsuario);

    }

    @OnWebSocketError
    public void onError(Throwable error) {
       System.out.println(error.getMessage());
    }

    @OnWebSocketFrame
    public void onFrame(Frame quadro) {
        // código para analisar os frames
    }

    @OnWebSocketMessage
    public void onMessageTexto(String texto) {
        System.out.println("Msg: " + texto);
        if(texto.substring(0,1).equals("@")) {
            processarComando(texto);
        } else {
            enviarParaTodos(this.nomeUsuario + ";" +texto);
        }
    }

    @OnWebSocketMessage
    public void onMessageBinario(byte buf[], int offset, int length) {
        System.out.println("Msg Bin: " + buf);
    }



    public boolean enviarMensagem(String texto) {
        if(sessao!=null) {
            sessao.getRemote().sendStringByFuture(texto);
            return true;
        }
        return false;
    }

    public void enviarParaTodos(String texto) {
        for (WSocket wsocket : usuarios.values()) {
            wsocket.enviarMensagem(texto);
        }
    }
    public void enviarParaOutros(String texto) {
        for (WSocket wsocket : usuarios.values()) {
            if(!wsocket.equals(this)) {
                wsocket.enviarMensagem(texto);
            }
        }
    }

    public void enviarUsuariosOnline() {
        String usuariosOnLine = "";
        for (WSocket wsocket : usuarios.values()) {
            if(!wsocket.isSessaoNull()) {
                usuariosOnLine +="<div class='usuario' idUsuario='" + wsocket.getIdUsuario()+ "'><span>" + wsocket.getNomeUsuario() + "</span></div>\n";
            }
        }
        this.enviarMensagem("@usuariosON;" + usuariosOnLine);
    }


    public void processarComando(String mensagem) {
        // Na mensagem de comando, convenciomanos que os campos
        // são separados por ";" e o primeiro campo é
        // o comando, os campos restantes são parametros
        String [] dados = null;
        dados = mensagem.split("\\;");
        String comando = dados[0];
        switch (comando) {
            case "@pegarId":
                enviarMensagem("@defId;"+ this.idUsuario);
            break;
        }

    }

    public void enviarPing() {
        if(sessao!=null) {
            ByteBuffer bb = ByteBuffer.allocate(1);
            bb.put((byte)0xFF);
            try {
                this.sessao.getRemote().sendPing(bb);
            } catch (IOException e) {

            }
        }
    }

    public boolean isSessaoNull() {
        if(this.sessao==null) {
            return true;
        }
        return false;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }
    public String getNomeUsuario() {
        return this.nomeUsuario;
    }
}

