/*
Copyright 2019 Tinic Uro

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package tinic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LXGradient {
  
  static final int gradient_points = 256;
  static final int gradient_points_mask = 0xFF;
  static final double gradient_points_mod = 255.0;

  public enum ColorMode {
    RGB,
    HSV
  };
  
  public LXGradient(LXFloat4[] stops, ColorMode mode) {
    List<LXFloat4> colors = new ArrayList<LXFloat4>();

    for (int c = 0; c < gradient_points; c++) {
        double f = (double)c / gradient_points_mod; 

        LXFloat4 a = stops[0];
        LXFloat4 b = stops[1];
        if (stops.length > 2) {
          for (int d = (stops.length-2); d >= 0 ; d--) {
              if ( f >= (stops[d].w) ) {
                a = stops[d+0];
                b = stops[d+1];
                break;
              }
          }
        }

        f -= a.w;
        f /= b.w - a.w;

        switch(mode) {
            case RGB: {
              LXFloat4 l = a.lerp(b,f);
              l.w = 1.0;
              colors.add(l);
            } break;
            case HSV: {
              LXFloat4 l = a.lerp(b,f);
              l.w = 1.0;
              colors.add(LXFloat4.hsv(l.x, l.y, l.z));
            } break;
        }
    }

    this.colors = Collections.unmodifiableList(colors);
  }

  public LXFloat4 repeat(double i) {
      i %= 1;
      i *= gradient_points_mod;
      double f = i % 1;
      LXFloat4 a = colors.get(((int)(i))&gradient_points_mask);
      LXFloat4 b = colors.get(((int)(i)+1)&gradient_points_mask);
      return a.lerp(b, f);
  }

  public LXFloat4 reflect(double i) {
      i = Math.abs(i);
      if (((int)i & 1) == 0) {
        i %= 1;
      } else {
        i %= 1;
        i = 1.0 - i;
      }
      i *= gradient_points_mod;
      double f = i % 1;
      LXFloat4 a = colors.get((int)(i)&gradient_points_mask);
      LXFloat4 b = colors.get((int)(i+1)&gradient_points_mask);
      return a.lerp(b, f);
  }
  
  public LXFloat4 clamp(double i) {
      if (i <= 0.0) {
          return colors.get(0);
      }
      if (i >= 1.0) {
          return colors.get(colors.size()-1);
      }
      i *= gradient_points_mod;
      double f = i % 1;
      LXFloat4 a = colors.get((int)(i)&gradient_points_mask);
      LXFloat4 b = colors.get((int)(i+1)&gradient_points_mask);
      return a.lerp(b, f);
  }

  List<LXFloat4> colors;
}
