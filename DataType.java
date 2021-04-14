package diaDat;

public class DataType
{
    DataType(String _name, DataTypesEnum _typeId, int _size)
    {
        name = _name;
        typeId = _typeId;
        size = _size;
    }
    String name;
    DataTypesEnum typeId;
    int size;
}
