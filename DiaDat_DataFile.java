package diaDat;

import java.util.TreeMap;

import util.Util;

public class DiaDat_DataFile extends DiaDat_DataFileBase
{
    public DiaDat_DataFile(DiaDat_File _parent, String filename)
    {
        parent = _parent;
        dataFileName = filename;
        channels = new TreeMap<String, DiaDat_ChannelBase>();
        numOfSignals = 0;
    }

    public DiaDat_ChannelBase addChannel(ChannelData chData) throws Exception
    {
        if (channels.containsKey(chData.chName))
            throw new Exception(Util.sprintf("DiaDat_DataFile.addChannel: dupplicated channel %s (dataFileName=%s)!", chData.chName, dataFileName));
        if (numOfSignals == 0)
            dataType = DataTypes.get(chData.dataType);
        else
            if (!dataType.name.equals(chData.dataType))
                throw new Exception(Util.sprintf("DiaDat_DataFile.addChannel: wrong datatype (%s <-> %s) (channel=%s dataFileName=%s)!", dataType.name, chData.dataType, chData.chName, dataFileName));
        numOfSignals++;
        if (chData.dataIdx != numOfSignals)
            throw new Exception(Util.sprintf("DiaDat_DataFile.addChannel: wrong dataIdx (%d <-> %d) (channel=%s dataFileName=%s)!", chData.dataIdx, numOfSignals, chData.chName, dataFileName));
        DiaDat_ChannelBase channel = new DiaDat_ChannelExplicit(this, chData);
        channels.put(chData.chName, channel);
        return channel;
    }

    public void step()
    {
        if (recordSize < 0)
        {
            recordSize = numOfSignals * dataType.size;
            System.out.println("name=" + dataFileName + " file size=" + (recordSize * parent.numOfRecords));
        }
    }

    @Override
    public DiaDat_ChannelBase getChannel(String chName) throws Exception
    {
        return null;
    }

    String dataFileName;
    TreeMap<String, DiaDat_ChannelBase> channels;
    int numOfSignals;
    DataType dataType;
    int recordSize = -1;
}
