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
    public boolean isExplicit()
    {
        return true;
    }

    @Override
    public double getValueDouble() throws Exception
    {
        DiaDat_DataFile p = (DiaDat_DataFile)parent;
        switch(type.typeId)
        {
            case e_DataType_u8:
            case e_DataType_u16:
                return getValueRaw() * factor + offset;
            case e_DataType_i16:
                return getValueRaw() * factor + offset;
            case e_DataType_u32:
                return getValueRaw() * factor + offset;
            case e_DataType_i32:
                return getValueRaw() * factor + offset;
            case e_DataType_Real32:
                return p.fin.get_float32(chIdx * type.size);
            case e_DataType_Real64:
                return p.fin.get_double64(chIdx * type.size);
            default:
                throw new Exception("DiaDat_Channel.getValueDouble: Not yet implemented function for channel " + name + "!");
        }
    }

    @Override
    public int getValueRaw() throws Exception
    {
        DiaDat_DataFile p = (DiaDat_DataFile)parent;
        switch(type.typeId)
        {
            case e_DataType_u8:
                return p.fin.get_u8(chIdx);
            case e_DataType_u16:
                return p.fin.get_u16(chIdx * type.size);
            case e_DataType_i16:
            {
                int val = p.fin.get_u16(chIdx * type.size);
                if (val >= 32768)
                    val = val - 65536;
                return val;
            }
            case e_DataType_i32:
                return (int)p.fin.get_u32(chIdx * type.size);
            case e_DataType_u32:
                return (int)p.fin.get_u32(chIdx * type.size);
            default:
                throw new Exception("DiaDat_Channel.getValueRaw: Not yet implemented function for channel " + name + "!");
        }
    }

    @Override
    public int getValueInt() throws Exception
    {
        switch(type.typeId)
        {
            case e_DataType_u8:
                return (int)(getValueRaw() * factor + offset);
            case e_DataType_u16:
                return (int)(getValueRaw() * factor + offset);
            case e_DataType_i16:
                return (int)(getValueRaw() * factor + offset);
            case e_DataType_u32:
                return (int)(getValueRaw() * factor + offset);
            case e_DataType_i32:
                return (int)(getValueRaw() * factor + offset);
            default:
                throw new Exception("DiaDat_Channel.getValueInt: Not yet implemented function for channel " + name + "!");
        }
    }

    @Override
    public int getLength()
    {
        return parent.parent.numOfRecords;
    }

    DataType type;
    int chIdx;
}
