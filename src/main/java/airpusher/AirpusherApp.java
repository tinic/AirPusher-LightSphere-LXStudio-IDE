package airpusher;

import heronarts.lx.LX;
import heronarts.lx.model.LXModel;

import java.net.Inet4Address;
import java.net.InetAddress;

import airpusher.AirpusherFixture;

public class AirpusherApp {
    public AirpusherApp(LX lx) {
        lx.registry.addFixture(AirpusherFixture.class);
        AirpusherDatagran datagram = new AirpusherDatagran(lx, lx.getModel());
        try {
            datagram.setAddress(Inet4Address.getByName("10.10.33.2"));
            lx.addOutput(datagram);
        }
        catch(Exception e) {
        }
    }
}
