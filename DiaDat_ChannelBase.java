package diaDat;

abstract public class DiaDat_ChannelBase
{
    public abstract boolean isExplicit();
    abstract public int getValueRaw() throws Exception;
    abstract public int getLength();

    void set(DiaDat_DataFileBase _parent, ChannelData chData) throws Exception
    {
        parent = _parent;
        name = chData.chName;
        unit = chData.unit;
        offset = chData.offset;
        factor = chData.factor;
        type = DataTypes.get(chData.dataType).typeId;
    }

    void step()
    { // do nothing
    }

    public double getValueDouble() throws Exception
    {
        return getValueRaw() * factor + offset;
    }

    public int getValueInt() throws Exception
    {
        return (int)(getValueRaw() * factor + offset);
    }

    public DataTypesEnum getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    DiaDat_DataFileBase parent;
    String name;
    String unit;
    String description;
    //int numOfPoints;
    double factor;
    double offset;
    //double min;
    //double max;
    DataTypesEnum type;
}
