package diaDat;

public class DiaDat_ChannelExplicitInt extends DiaDat_ChannelBase {

    public DiaDat_ChannelExplicitInt(DiaDat_ChannelExplicit _channel)
    {
        channel = _channel;
    }

    @Override
    public boolean isExplicit() {
        return true;
    }

    @Override
    public int getValueRaw() throws Exception {
        return channel.getValueRaw();
    }

    @Override
    public int getValueInt() throws Exception {
        return channel.getValueRaw();
    }

    public double getValueDouble() throws Exception
    {
        return getValueRaw();
    }

    @Override
    public int getLength() {
        return channel.getLength();
    }

    DiaDat_ChannelExplicit channel;
}
