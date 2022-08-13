package airpusher;

import java.util.List;

import heronarts.lx.LX;
import heronarts.lx.model.LXPoint;
import heronarts.lx.structure.LXBasicFixture;
import heronarts.lx.transform.LXMatrix;

public class AirpusherFixture extends LXBasicFixture {

    private int bracketCount = 12;
    private int ledCount = 42;

    private LXPoint[] bracket3template = {
        new LXPoint( + 0.00, 192.07 + 64.0, -197.67 ),
        new LXPoint( + 0.00, 274.57 + 64.0, +  0.00 ),
        new LXPoint( + 0.00, 192.07 + 64.0, +197.67 )
    };

    private LXPoint[] bracket4template = {
        new LXPoint( + 0.00, 150.07 + 64.0, -227.21 ),
        new LXPoint( + 0.00, 251.44 + 64.0, - 88.33 ),
        new LXPoint( + 0.00, 251.44 + 64.0, + 88.33 ),
        new LXPoint( + 0.00, 150.07 + 64.0, +227.21 )
    };

    public AirpusherFixture(LX lx) {
        super(lx, "Airpusher");
    }

    @Override protected void computePointGeometry(LXMatrix transform, List<LXPoint> points) {

        LXPoint[] leds = new LXPoint[ledCount];

        int ledCount = 0;
        for(int bracket = 0; bracket < bracketCount; bracket++ ) {
            
            // Rotate around Z axis
            LXMatrix rotMat = new LXMatrix();
            rotMat.rotateZ((float)(Math.PI * 2.0 / (double)bracketCount * (double)bracket));

            if ( (bracket % 2) == 0 ) { // 4 leds
                for (int led = 0; led < bracket4template.length; led++) {
                    leds[ledCount] = new LXPoint(bracket4template[led]).multiply(rotMat);
                    ledCount++;
                }
            } else { // 3 leds
                for (int led = 0; led < bracket3template.length; led++) {
                    leds[ledCount] = new LXPoint(bracket3template[led]).multiply(rotMat);
                    ledCount++;
                }
            }
        }

        ledCount = 0;
        for (LXPoint p : points) {
            p.set(leds[ledCount++]);
        }
    }

    @Override public String[] getDefaultTags() {
      return new String[] { "airpusher" };
    }
  
    @Override protected int size() {
        return ledCount;
    }
}
