package diaDat;

import util.Util;

public class DiaDat_DataFileImplicit extends DiaDat_DataFileBase
{
    public DiaDat_DataFileImplicit(DiaDat_File _parent, String filename)
    {
        parent = _parent;
    }

    @Override
    public DiaDat_ChannelBase addChannel(ChannelData chData) throws Exception
    {
        if (channel != null)
            throw new Exception(Util.sprintf("DiaDat_DataFileImplicit.addChannel: dupplicated implicit channel (channel=%s)!", chData.chName));
        channel = new DiaDat_ChannelImplicit(this, chData);
        return channel;
    }

    @Override
    public void step()
    {
        channel.step();
    }

    @Override
    public DiaDat_ChannelBase getChannel(String chName) throws Exception
    {
        return channel;
    }

    @Override
    public void seek(int recordIdx) throws Exception
    {
        throw new Exception("DiaDat_DataFileImplicit.seek: not yet implemented of file!");
    }

    DiaDat_ChannelBase channel;
}
