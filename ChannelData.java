package diaDat;

class ChannelData
{
    ChannelData()
    {
        reinit();
    }
    String chName;
    String chDesc;
    String unit;
    String mode;
    String dataFileName;
    String dataFileMode;
    String dataType;
    int dataIdx;
    int blockLength;
    int numOfRecord;
    double offset;
    double factor;
    double min;
    double max;
    void reinit()
    {
        chName = "";
        chDesc = "";
        unit = "";
        mode = "";
        dataFileName = "";
        dataFileMode = "";
        dataType = "";
        dataIdx = -1;
        numOfRecord = -1;
        blockLength = -1;
    }
    boolean isEmpty()
    {
        return  (chName.isEmpty() &&
                 chDesc.isEmpty() &&
                 unit.isEmpty() &&
                 mode.isEmpty() &&
                 dataFileName.isEmpty() &&
                 dataFileMode.isEmpty() &&
                 dataType.isEmpty() &&
                 (dataIdx == -1) &&
                 (numOfRecord == -1) &&
                 (blockLength == -1) &&
                 true);
    }
    boolean isValid()
    {
        return (!chName.isEmpty() &&
                true);
    }
}