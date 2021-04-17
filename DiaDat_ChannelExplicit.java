package diaDat;

public class DiaDat_ChannelExplicit extends DiaDat_ChannelBase
{
    public DiaDat_ChannelExplicit(DiaDat_DataFileBase _parent, ChannelData chData) throws Exception
    {
        set(_parent, chData);
        type = DataTypes.get(chData.dataType);
        chIdx = chData.dataIdx - 1;
    }

    @Override
    boolean isExplicit()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getValueDouble() throws Exception
    {
        throw new Exception("DiaDat_Channel.getValueDouble: Not yet implemented function for channel " + name + "!");
    }

    @Override
    public int getValueRaw() throws Exception
    {
        DiaDat_DataFile p = (DiaDat_DataFile)parent;
        if (type.size == 1)
            return p.fin.get_u8(chIdx);
        else if (type.size == 2)
            return p.fin.get_u16(chIdx * 2);
        else
            throw new Exception("DiaDat_Channel.getValueRaw: Not yet implemented function for channel " + name + "!");
    }

    @Override
    public int getLength()
    {
        return parent.parent.numOfRecords;
    }

    DataType type;
    int chIdx;
}
