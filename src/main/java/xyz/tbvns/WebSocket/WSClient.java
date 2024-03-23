package xyz.tbvns.WebSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WSClient extends WebSocketClient {
    List<String> msg;
    public WSClient(URI serverUri, ArrayList<String> message) {
        super(serverUri);
        msg = message;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        msg.forEach(message -> {
            send(message);
        });
        close();
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
