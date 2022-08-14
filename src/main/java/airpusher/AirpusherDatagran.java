package airpusher;

import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXBufferOutput;
import heronarts.lx.output.IndexBuffer;
import heronarts.lx.LX;

public class AirpusherDatagran extends ArtNetDatagram {

    //
    // JLPOW Mini COB DMX control channels:
    //
    // CH1: 0-255 Master Dim
    // CH2: 0-255 Red
    // CH3: 0-255 Green
    // CH4: 0-255 Blue
    // CH5: 0-5 Strobe Off, 
    //      6-255 Strobe Speed
    // CH6: 0-50 No effect, 
    //      51-90 Jump, 
    //      91-130 Fixed Color, 
    //      131-170 Color Fade, 
    //      171-210 Auto Mode, 
    //      211-255 Sound Activated
    //
    
    static int dmxControlChannels = 6;

    static int dmxCH1Master = 0;
    static int dmxCH2Red = 1;
    static int dmxCH3Green = 2;
    static int dmxCH4Blue = 3;
    static int dmxCH5Strobe = 4;
    static int dmxCH6Effect = 5;

    public AirpusherDatagran(LX lx, int[] indexBuffer) {
        super(lx, indexBuffer, ByteOrder.RGB, indexBuffer.length * dmxControlChannels, 0);
    }

    @Override protected LXBufferOutput updateDataBuffer(int[] colors, byte[][] glut, double brightness) {

        if (this.indexBuffer.segments.length < 1) {
            return this;
        }

        // There should only be one segment
        IndexBuffer.Segment segment = this.indexBuffer.segments[0];

        if (colors.length < segment.indices.length) {
            // Fixture not initialized yet?!
            return this;
        }

        // Determine the appropriate gamma curve for segment brightness
        byte[] gamma = glut[(int) Math.round(255. * brightness * segment.brightness.getValue())];
    
        // Determine data offsets and byte size
        int offset = getDataBufferOffset() + segment.startChannel;
        ByteOrder byteOrder = segment.byteOrder;
        int[] byteOffset = byteOrder.getByteOffset();
        byte[] buffer = getDataBuffer();

        for (int i = 0; i < segment.indices.length; ++i) {
            int index = segment.indices[i];
            int color = (index >= 0) ? colors[index] : 0;
            buffer[offset + dmxCH1Master]                = (byte)0xff; // CH1: master brightness to max
            buffer[offset + dmxCH2Red   + byteOffset[0]] = gamma[((color >> 16) & 0xff)]; // CH2: Red
            buffer[offset + dmxCH3Green + byteOffset[1]] = gamma[((color >>  8) & 0xff)]; // CH3: Green
            buffer[offset + dmxCH4Blue  + byteOffset[2]] = gamma[((color      ) & 0xff)]; // CH4: Blue
            buffer[offset + dmxCH5Strobe]                = 0; // CH5: turn strobe off
            buffer[offset + dmxCH6Effect]                = 0; // CH6: turn effect off
            offset += dmxControlChannels;
        }

        return this;
    }

 }
