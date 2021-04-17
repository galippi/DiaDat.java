package diaDat;

public class DiaDat_ChannelImplicit extends DiaDat_ChannelBase
{
    public DiaDat_ChannelImplicit(DiaDat_DataFileBase _parent, ChannelData chData)
    {
        set(_parent, chData);
    }

    @Override
    boolean isExplicit()
    {
        return false;
    }

    @Override
    void step()
    {
        if (recordIdx < 0)
        { // first step
            recordIdx = 0;
        }
        recordIdx++;
    }

    @Override
    double getValueDouble()
    {
        return recordIdx * factor + offset;
    }

    int recordIdx = -1;
}
