package airpusher;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.effect.LXEffect;
import heronarts.lx.model.LXPoint;

//
// Register effects in the AirpusherApp constructor
//

@LXCategory(LXCategory.TEXTURE)
public class AirpusherEffectTemplate extends LXEffect {

  public AirpusherEffectTemplate(LX lx) {
    super(lx);
  }

  @Override
  protected void run(double deltaMs, double enabledAmount) {
    if (enabledAmount > 0) {
      for (LXPoint p : this.model.points) {
        colors[p.index] = LXColor.hsb(0, 0, enabledAmount * 100);
      }
    }
  }

}
