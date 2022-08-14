package airpusher;

import tinic.LXFloat4;
import tinic.LXGradient;

import heronarts.lx.parameter.BoundedParameter;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXPoint;
import heronarts.lx.pattern.LXPattern;

//
// Register patterns in the AirpusherApp constructor
//

@LXCategory(LXCategory.COLOR)
public class AirpusherPatternTemplate extends LXPattern {

    private LXGradient rainbowGradient;
    private LXGradient rainbowGradientBright;

    public AirpusherPatternTemplate(LX lx) {
        super(lx);

        addParameter("speed", this.speed);

        // Two sample gradients

        // -----------------------
        LXFloat4[] rainbowGradient = {
            new LXFloat4(0.0, 1.0, 1.0, 0.00),
            new LXFloat4(1.0, 1.0, 1.0, 1.00)
        };
        this.rainbowGradient = new LXGradient(rainbowGradient, LXGradient.ColorMode.HSV);
        // -----------------------

        // -----------------------
        LXFloat4[] rainbowGradientBright = {
            new LXFloat4(0xff0000, 0.00),
            new LXFloat4(0xffbd96, 0.10),
            new LXFloat4(0xffff00, 0.17),
            new LXFloat4(0xc3ffa9, 0.25),
            new LXFloat4(0x00ff00, 0.33),
            new LXFloat4(0xd1ffbf, 0.38),
            new LXFloat4(0xaffff3, 0.44),
            new LXFloat4(0x29fefe, 0.50),
            new LXFloat4(0x637eff, 0.59),
            new LXFloat4(0x0000ff, 0.67),
            new LXFloat4(0x9c3fff, 0.75),
            new LXFloat4(0xff00ff, 0.83),
            new LXFloat4(0xffc2b0, 0.92),
            new LXFloat4(0xff0000, 1.00)
        };
        this.rainbowGradientBright = new LXGradient(rainbowGradientBright, LXGradient.ColorMode.RGB);
        // -----------------------

    }

    // One speed parameter
    public final BoundedParameter speed = new BoundedParameter("Speed", 0, -10, 10).setDescription("Speed parameter");

    // Current time for this pattern
    private double tm = 0.0;

    @Override
    protected void run(double deltaMs) {

        double tm_factor = Math.pow(1024,speed.getNormalized() - 0.5);
        tm += ( deltaMs * (1.0 / 1000.0) ) * tm_factor;

        for (LXPoint p : model.points) {
            // Get point on the globe
            LXFloat4 p4 = new LXFloat4(p);

            // Get the bounds of the globe and half it
            LXFloat4 bS = new LXFloat4(AirpusherFixture.boundsSize).div(2.0);

            // normalize the point so that values are between -1.0 .. +1.0
            p4 = p4.div(bS);

            // Apply gradient across height (Z-axis) and shift up based on time
            LXFloat4 col4 = rainbowGradientBright.reflect((double)p4.z * 0.25 + tm / 10.0);

            // Convert to 32bit color
            int colI = col4.toColor();

            // Write color into target buffer
            colors[p.index] = colI;
        }
    }

}
