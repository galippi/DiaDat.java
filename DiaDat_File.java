package diaDat;

import java.io.*;

import util.Util;

enum DiaDat_FileReadState
{
    e_FileBegin,
    e_BeforeFileHeader,
    e_FileHeader,
    e_AfterFileHeader,
    e_Channel
}

class LineData
{
    LineData(String line) throws Exception
    {
        //System.out.println("LineData line="+line);
        int pos = line.indexOf(',');
        if (pos < 0)
            throw new Exception(Util.sprintf("Invalid LineData - no comma in line %s", line));
        String idStr = line.substring(0, pos).trim();
        id = Integer.parseInt(idStr);
        value = line.substring(pos + 1);
        //System.out.println("LineData line="+line+" id="+id+" value="+value);
    }
    int id;
    String value;
}

class ChannelData
{
    String chName;
    String chDesc;
    String unit;
    String mode;
    String dataFileName;
    String dataFileMode;
    String dataType;
    int dataIdx;
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
    }
    boolean isEmpty()
    {
        return  (chName.isEmpty() &&
                 chDesc.isEmpty() &&
                 unit.isEmpty() &&
                 mode.isEmpty() &&
                 dataFileName.isEmpty() &&
                 dataFileMode.isEmpty() &&
                 dataType.isEmpty());
    }
    boolean isValid()
    {
        return true;
    }
}

public class DiaDat_File
{
    public DiaDat_File()
    {
        dir = DiaDat_Direction.e_DiaDat_Dir_None;
    }
    public DiaDat_File(String filename) throws Exception
    {
        open(filename);
    }
    public void open(String filename) throws Exception
    {
        BufferedReader fin = new BufferedReader(new FileReader(filename));
        String line;
        int lineNum = 0;
        DiaDat_FileReadState state = DiaDat_FileReadState.e_FileBegin;
        ChannelData chData = new ChannelData();
        while ((line = fin.readLine()) != null)
        {
            lineNum++;
            if ((line.isEmpty()) || (line.startsWith("//")))
                continue;
            switch(state)
            {
                case e_FileBegin:
                    if (line.equals("DIAEXTENDED  {@:ENGLISH"))
                        state = DiaDat_FileReadState.e_BeforeFileHeader;
                    else
                        throw new Exception("Invalid line content 1");
                    break;
                case e_BeforeFileHeader:
                    if (line.equals("#BEGINGLOBALHEADER"))
                        state = DiaDat_FileReadState.e_FileHeader;
                    else
                        throw new Exception("Invalid line content 2");
                    break;
                case e_FileHeader:
                    if (line.equals("#ENDGLOBALHEADER"))
                        state = DiaDat_FileReadState.e_AfterFileHeader;
                    else
                    {
                        LineData ld = new LineData(line);
                        switch(ld.id)
                        {
                            case 1:
                            case 101:
                            case 102:
                            case 103:
                            case 104:
                            case 105:
                            case 106:
                                break;
                            default:
                                throw new Exception(Util.sprintf("Invalid line content in line %d (%s)!", lineNum, line));
                        }
                    }
                    break;
                case e_AfterFileHeader:
                    if (line.equals("#BEGINCHANNELHEADER"))
                        state = DiaDat_FileReadState.e_Channel;
                    else
                        throw new Exception("Invalid line content 4");
                    break;
                case e_Channel:
                    if (line.equals("#ENDCHANNELHEADER"))
                    {
                        if (!chData.isEmpty())
                        {
                            if (!chData.isValid())
                                throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s!", lineNum, filename));
                            addChannel(chData);
                            chData.reinit();
                        }
                        state = DiaDat_FileReadState.e_AfterFileHeader;
                    }else
                    {
                        LineData ld = new LineData(line);
                        switch(ld.id)
                        {
                            case 200:
                                chData.chName = ld.value;
                                break;
                            case 201: // description
                                chData.chDesc = ld.value;
                                break;
                            case 202:
                                break;
                            case 210:
                                break;
                            case 211:
                                chData.dataFileName = ld.value;
                                break;
                            case 213:
                                chData.dataFileMode = ld.value;
                                break;
                            case 214:
                                break;
                            case 220:
                                break;
                            case 221:
                                break;
                            case 222:
                                break;
                            case 240:
                                break;
                            case 241:
                                break;
                            case 250:
                                break;
                            case 251:
                                break;
                            case 252:
                                break;
                            case 253:
                                break;
                            case 260:
                                break;
                            case 264:
                                break;
                            case 300:
                                break;
                            case 301:
                                break;
                            default:
                                throw new Exception(Util.sprintf("Invalid line content in line %d (%s)!", lineNum, line));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        dir = DiaDat_Direction.e_DiaDat_Dir_Read;
    }
    public DiaDat_Direction getDir()
    {
        return dir;
    }
    void addChannel(ChannelData chData)
    {
        
    }
    DiaDat_Direction dir;
}
