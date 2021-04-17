package diaDat;

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

    int get_u8(int idx)
    {
        return ((int)record[idx] & 0xFF);
    }

    int get_u16(int idx)
    {
        return ((int)record[idx] & 0xFF) + 
              (((int)record[idx + 1] & 0xFF) * 256);
    }

    FileInputStream fin;
    int recordSize;
    byte[] record;
}
