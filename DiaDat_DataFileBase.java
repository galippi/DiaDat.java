package diaDat;

public abstract class DiaDat_DataFileBase
{
    abstract public DiaDat_ChannelBase addChannel(ChannelData chData) throws Exception;
    abstract public void step() throws Exception;
    abstract public DiaDat_ChannelBase getChannel(String chName) throws Exception;
    DiaDat_File parent;
}
