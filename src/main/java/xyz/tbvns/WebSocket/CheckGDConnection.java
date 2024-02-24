package xyz.tbvns;

import java.net.URI;

public class CheckGDConnection implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                if (Constant.HasGD && Constant.HasOBJ && Constant.HasANM) {
                    Constant.isReady = true;
                } else {
                    Constant.isReady = false;
                }

                WSClient client = new WSClient(URI.create("ws://127.0.0.1:1313"));
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
