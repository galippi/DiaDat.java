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

    public double getFactor() {
        return factor;
    }
    public double getOffset() {
        return offset;
    }

    public boolean isPureParams() {
        return ((Math.abs(offset) < 1e-12) && (Math.abs(factor - 1.00) < 1e-12));
    }

    public boolean isPureInt() {
        return false;
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
