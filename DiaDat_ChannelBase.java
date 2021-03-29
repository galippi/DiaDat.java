package diaDat;

abstract class DiaDat_ChannelBase
{
    abstract boolean isExplicit();
    String name;
    String description;
    int numOfPoints;
    DiaDat_File datFile;
    double factor;
    double offset;
    double min;
    double max;
}
