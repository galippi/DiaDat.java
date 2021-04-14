package diaDat;

public class DiaDat_Channel extends DiaDat_ChannelBase
{
    public DiaDat_Channel(DiaDat_DataFile _parent, ChannelData chData) throws Exception
    {
        parent = _parent;
        type = DataTypes.get(chData.dataType);
        name = chData.chName;
    }

    @Override
    boolean isExplicit()
    {
        // TODO Auto-generated method stub
        return false;
    }
    DiaDat_DataFile parent;
    String name;
    DataType type;
}
