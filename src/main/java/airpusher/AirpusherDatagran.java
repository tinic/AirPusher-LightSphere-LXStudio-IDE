package airpusher;

import heronarts.lx.output.ArtNetDatagram;
import heronarts.lx.output.LXBufferOutput;
import heronarts.lx.output.IndexBuffer;
import heronarts.lx.LX;
import heronarts.lx.model.LXModel;

public class AirpusherDatagran extends ArtNetDatagram {
    
    public AirpusherDatagran(LX lx, LXModel model) {
        super(lx, model);
    }

    @Override protected LXBufferOutput updateDataBuffer(int[] colors, byte[][] glut, double brightness) {
        byte[] buffer = getDataBuffer();
        for (IndexBuffer.Segment segment : this.indexBuffer.segments) {
            // Determine the appropriate gamma curve for segment brightness
            byte[] gamma = glut[(int) Math.round(255. * brightness * segment.brightness.getValue())];
      
            // Determine data offsets and byte size
            int offset = getDataBufferOffset() + segment.startChannel;
            ByteOrder byteOrder = segment.byteOrder;
            int[] byteOffset = byteOrder.getByteOffset();
            
            for (int i = 0; i < segment.indices.length; ++i) {
                int index = segment.indices[i];
                int color = (index >= 0) ? colors[index] : 0;
                buffer[offset + byteOffset[0]] = gamma[((color >> 16) & 0xff)]; // R
                buffer[offset + byteOffset[1]] = gamma[((color >> 8) & 0xff)]; // G
                buffer[offset + byteOffset[2]] = gamma[(color & 0xff)]; // B
                offset += 6;
            }
        }
        return this;
    }

 }
