package xyz.tbvns.WebSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import xyz.tbvns.Constant;
import xyz.tbvns.Main;

import java.net.URI;

public class WSGDChecker extends WebSocketClient {
    public WSGDChecker(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        if (!Constant.HasGD) {
            Constant.HasGD = true;
            Main.Refresh();
        }
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
        if (Constant.HasGD) {
            Constant.HasGD = false;
            Main.Refresh();
        }
    }
}
