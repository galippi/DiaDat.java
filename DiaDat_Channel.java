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
    public boolean isExplicit()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    public int getLength()
    {
        //throw new Exception("DiaDat_Channel.getLength: not yet implemented of file!");
        return 0;
    }

    @Override
    public int getValueRaw() throws Exception
    {
        throw new Exception("DiaDat_Channel.getValueRaw: not yet implemented of file!");
    }

    DiaDat_DataFile parent;
    String name;
    DataType type;
}
