package xyz.tbvns.WebSocket;

import xyz.tbvns.Constant;

import java.net.URI;
import java.util.TimerTask;

public class CheckGDConnection implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                if (Constant.HasGD && Constant.HasOBJ) {
                    Constant.isReady = true;
                } else {
                    Constant.isReady = false;
                }

                WSGDChecker client = new WSGDChecker(URI.create("ws://127.0.0.1:1313"));
                client.run();
                Thread.sleep(1000);
                client.close();

                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
