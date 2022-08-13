package airpusher;

import heronarts.lx.LX;
import airpusher.AirpusherFixture;

public class AirpusherApp {
    public AirpusherApp(LX lx) {
        lx.registry.addFixture(AirpusherFixture.class);
    }
}
