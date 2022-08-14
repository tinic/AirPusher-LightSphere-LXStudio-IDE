package airpusher;

import java.util.List;

import tinic.LXFloat4;

import heronarts.lx.LX;
import heronarts.lx.model.LXPoint;
import heronarts.lx.structure.LXBasicFixture;
import heronarts.lx.transform.LXMatrix;

public class AirpusherFixture extends LXBasicFixture {

    // We have 12 side brackets, with either 3 or 4 spotlights
    private int bracketCount = 12;

    // 6 * 3 spotlights + 6 * 4 spotlights
    private int ledCount = 42;

    // coordinates are in mm
    private LXPoint[] bracket3template = {
        new LXPoint( + 0.00, 192.07 + 64.0, -197.67 ),
        new LXPoint( + 0.00, 274.57 + 64.0, +  0.00 ),
        new LXPoint( + 0.00, 192.07 + 64.0, +197.67 )
    };

    // coordinates are in mm
    private LXPoint[] bracket4template = {
        new LXPoint( + 0.00, 150.07 + 64.0, -227.21 ),
        new LXPoint( + 0.00, 251.44 + 64.0, - 88.33 ),
        new LXPoint( + 0.00, 251.44 + 64.0, + 88.33 ),
        new LXPoint( + 0.00, 150.07 + 64.0, +227.21 )
    };

    // Calulated in computePointGeometry
    static LXFloat4 boundsMin = new LXFloat4();
    static LXFloat4 boundsMax = new LXFloat4();
    static LXFloat4 boundsSize = new LXFloat4();

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

            // Alternate between the two bracket types
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

        LXFloat4 min = new LXFloat4(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        LXFloat4 max = new LXFloat4(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

        for (LXPoint led : leds) {
            min = min.min(new LXFloat4(led));
            max = max.max(new LXFloat4(led));
        }

        boundsMin = min;
        boundsMax = max;
        boundsSize = LXFloat4.sub(max,min);

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
