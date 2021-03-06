package diaDat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import utils.Sprintf;

public class DiaDat_DataFile extends DiaDat_DataFileBase
{
    public DiaDat_DataFile(DiaDat_File _parent, String filename)
    {
        parent = _parent;
        dataFileName = filename;
        channels = new TreeMap<String, DiaDat_ChannelBase>();
        numOfSignals = 0;
    }

    static boolean indexCheckIsEnabled = false;
    public DiaDat_ChannelBase addChannel(ChannelData chData) throws Exception
    {
        if (channels.containsKey(chData.chName))
            throw new Exception(Sprintf.sprintf("DiaDat_DataFile.addChannel: dupplicated channel %s (dataFileName=%s)!", chData.chName, dataFileName));
        if (numOfSignals == 0)
            dataType = DataTypes.get(chData.dataType);
        else
            if (!dataType.name.equals(chData.dataType))
                throw new Exception(Sprintf.sprintf("DiaDat_DataFile.addChannel: wrong datatype (%s <-> %s) (channel=%s dataFileName=%s)!", dataType.name, chData.dataType, chData.chName, dataFileName));
        numOfSignals++;
        if (indexCheckIsEnabled && (chData.dataIdx != numOfSignals))
            throw new Exception(Sprintf.sprintf("DiaDat_DataFile.addChannel: wrong dataIdx (%d <-> %d) (channel=%s dataFileName=%s)!", chData.dataIdx, numOfSignals, chData.chName, dataFileName));
        DiaDat_ChannelExplicit channelExplicit = new DiaDat_ChannelExplicit(this, chData);
        DiaDat_ChannelBase channel;
        if (channelExplicit.isInteger())
            channel = new DiaDat_ChannelExplicitInt(channelExplicit);
        else
            channel = channelExplicit;
        channels.put(chData.chName, channel);
        return channel;
    }

    public void step() throws Exception
    {
        if (recordSize < 0)
        {
            recordSize = numOfSignals * dataType.size;
            Path path = Paths.get(parent.containerDir.toString(), dataFileName);
            if (Files.size(path) != (recordSize * parent.numOfRecords))
                throw new Exception(Sprintf.sprintf("DiaDat_DataFile.step: wrong data file size of file %s (%d <-> %d)!", dataFileName, Files.size(Paths.get(dataFileName)), (recordSize * parent.numOfRecords)));
            fin = new DataFileReaderBase(path.toString(), recordSize);
        }else
            fin.step();
    }

    @Override
    public void seek(int recordIdx) throws Exception
    {
        throw new Exception(Sprintf.sprintf("DiaDat_DataFile.seek: not yet implemented of file %s!", dataFileName));
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
    DataFileReaderBase fin;
}
