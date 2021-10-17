package diaDat;

public class DiaDat_ChannelImplicit extends DiaDat_ChannelBase
{
    public DiaDat_ChannelImplicit(DiaDat_DataFileBase _parent, ChannelData chData) throws Exception
    {
        if (chData.dataType.isEmpty())
            chData.dataType = "REAL64"; // for implicit channel the dataType is is not mandatory
        set(_parent, chData);
    }

    @Override
    public boolean isExplicit()
    {
        return false;
    }

    @Override
    void step()
    {
        if (recordIdx < 0)
        { // first step
            recordIdx = 0;
        }else
            recordIdx++;
    }

    @Override
    public int getValueRaw() throws Exception
    {
        return recordIdx;
    }

    @Override
    public int getLength()
    {
        return parent.parent.numOfRecords;
    }

    int recordIdx = -1;
}
