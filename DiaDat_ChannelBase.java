package diaDat;

abstract public class DiaDat_ChannelBase
{
    abstract boolean isExplicit();
    public abstract int getValueRaw() throws Exception;

    void set(DiaDat_DataFileBase _parent, ChannelData chData)
    {
        parent = _parent;
        name = chData.chName;
        unit = chData.unit;
        offset = chData.offset;
        factor = chData.factor;
    }

    void step()
    { // do nothing
    }

    public double getValueDouble() throws Exception
    {
        return getValueRaw() * factor + offset;
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
}
