package diaDat;

abstract class DiaDat_ChannelBase
{
    abstract boolean isExplicit();
    abstract double getValueDouble() throws Exception;

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
