package airpusher;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.pattern.LXPattern;

//
// Register patterns in the AirpusherApp constructor
//

@LXCategory(LXCategory.COLOR)
public class AirpusherPatternTemplate extends LXPattern {

  public AirpusherPatternTemplate(LX lx) {
    super(lx);
  }

  @Override
  protected void run(double deltaMs) {
    for (LXPoint p : model.points) {
      colors[p.index] = LXColor.hsb(240, p.x, p.y);
    }
  }

}
