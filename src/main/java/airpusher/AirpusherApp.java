package airpusher;

import heronarts.lx.LX;
import java.net.Inet4Address;

public class AirpusherApp {

    public String artNetControllerIP = "10.10.34.2";

    public AirpusherApp(LX lx) {
        // Register patterns
        lx.registry.addPattern(AirpusherPatternTemplate.class);

        // Register effects
        lx.registry.addEffect(AirpusherEffectTemplate.class);

        // Init stationaries
        initStationaries(lx);
    }

    public void initStationaries(LX lx) {

        // Our main and only fixture
        lx.registry.addFixture(AirpusherFixture.class);

        // DMX address/channel config on the globe
        int[] indexBuffer = { 
            0, 1, 2, 3,     // bracket 00
            4, 5, 6,        // bracket 01
            7, 8, 9, 10,    // bracket 02
            11, 12, 13,     // bracket 03
            14, 15, 16, 17, // bracket 04
            18, 19, 20,     // bracket 05
            21, 22, 23, 24, // bracket 06
            25, 26, 27,     // bracket 07
            28, 29, 30, 31, // bracket 08
            32, 33, 34,     // bracket 09
            35, 36, 37, 38, // bracket 10
            39, 40, 31      // bracket 11
        };

        AirpusherDatagram datagram = new AirpusherDatagram(lx, indexBuffer);
        try {
            datagram.setAddress(Inet4Address.getByName(artNetControllerIP));
            lx.addOutput(datagram);
        }
        catch(Exception e) {
        }
    }
}

