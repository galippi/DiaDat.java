package diaDat;

public class DiaDat_ChannelExplicit extends DiaDat_ChannelBase
{
    public DiaDat_ChannelExplicit(DiaDat_DataFileBase _parent, ChannelData chData) throws Exception
    {
        set(_parent, chData);
        type = DataTypes.get(chData.dataType);
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
        // TODO Auto-generated method stub
        return 0;
    }

    DataType type;
}
