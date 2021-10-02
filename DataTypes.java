package diaDat;

import java.util.TreeMap;

import utils.Sprintf;

public class DataTypes
{
    static
    {
        names = new TreeMap<String, DataType>();
        enums = new TreeMap<DataTypesEnum, DataType>();
        add("WORD8",  DataTypesEnum.e_DataType_u8, 1);
        add("WORD16", DataTypesEnum.e_DataType_u16, 2);
        add("INT16",  DataTypesEnum.e_DataType_i16, 2);
        add("WORD32", DataTypesEnum.e_DataType_u32, 4);
        add("INT32",  DataTypesEnum.e_DataType_i32, 4);
        add("REAL32", DataTypesEnum.e_DataType_Real32, 4);
        add("REAL64", DataTypesEnum.e_DataType_Real64, 8);
    }
    static void add(String name, DataTypesEnum typeId, int size)
    {
        DataType type = new DataType(name, typeId, size);
        names.put(name, type);
        enums.put(typeId, type);
    }
    public static DataType get(String name) throws Exception
    {
        DataType type = names.get(name);
        if (type == null)
            throw new Exception(Sprintf.sprintf("DataTypes.get: invalid type name %s!", name));
        return type;
    }
    public static DataType get(DataTypesEnum typeId) throws Exception
    {
        DataType type = enums.get(typeId);
        if (type == null)
            throw new Exception(Sprintf.sprintf("DataTypes.get: invalid type id %s!", typeId));
        return type;
    }
    static TreeMap<String, DataType> names;
    static TreeMap<DataTypesEnum, DataType> enums;
}
