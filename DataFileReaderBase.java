package diaDat;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class DataFileReaderBase
{
    DataFileReaderBase(String filename, int recordSize) throws Exception
    {
        fin = new FileInputStream(filename);
        this.recordSize = recordSize;
        record = new byte[recordSize];
        step();
    }

    void step() throws Exception
    {
        int readLen = fin.read(record);
        if (readLen != record.length)
            throw new Exception("DataFileReaderBase.step: error reading next record!");
    }

    void copyRaw(int idx, byte[] raw)
    {
        System.arraycopy(record, idx, raw, 0, raw.length);
    }

    int get_u8(int idx)
    {
        return ((int)record[idx] & 0xFF);
    }

    int get_u16(int idx)
    {
        return ((int)record[idx] & 0xFF) + 
              (((int)record[idx + 1] & 0xFF) * 256);
    }

    double get_float32(int idx)
    {
        int rawVal = 0;
        for(int i = 0; i < 4; i++)
        {
            rawVal = (rawVal << 8) + get_u8(idx + 3 - i);
        }
        return Float.intBitsToFloat(rawVal);
    }

    double get_double64(int idx)
    {
        {
            long rawVal = 0;
            for(int i = 0; i < 8; i++)
            {
                rawVal = (rawVal << 8) + get_u8(idx + 7 - i);
            }
            return Double.longBitsToDouble(rawVal);
        }
    }

    FileInputStream fin;
    int recordSize;
    byte[] record;
}
