package diaDat;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

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
    int getValueInt()
    {
        return Integer.parseInt(value);
    }
    double getValueDouble()
    {
        return Double.parseDouble(value);
    }
    int id;
    String value;
}

public class DiaDat_File
{
    public DiaDat_File()
    {
        dir = DiaDat_Direction.e_DiaDat_Dir_None;
        
    }
    public DiaDat_File(String filename) throws Exception
    {
        super();
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
                                chData.unit = ld.value;
                                break;
                            case 210:
                                chData.mode = ld.value;
                                if ((!chData.mode.equals("IMPLICIT")) && (!chData.mode.equals("EXPLICIT")))
                                    throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 210=%s!", lineNum, filename, ld.value));
                                break;
                            case 211:
                                chData.dataFileName = ld.value;
                                break;
                            case 213:
                                chData.dataFileMode = ld.value;
                                if (!chData.dataFileMode.equals("BLOCK"))
                                    throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 213=%s!", lineNum, filename, ld.value));
                                break;
                            case 214:
                                chData.dataType = ld.value;
                                break;
                            case 220:
                                chData.numOfRecord = ld.getValueInt();
                                break;
                            case 221:
                                chData.dataIdx = ld.getValueInt();
                                break;
                            case 222:
                                chData.blockLength = ld.getValueInt();
                                break;
                            case 240:
                                chData.offset = ld.getValueDouble();
                                break;
                            case 241:
                                chData.factor = ld.getValueDouble();
                                break;
                            case 250:
                                chData.min = ld.getValueDouble();
                                break;
                            case 251:
                                chData.max = ld.getValueDouble();
                                break;
                            case 252:
                                if (!ld.value.equals("No"))
                                    throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 252=%s != No!", lineNum, filename, ld.value));
                                break;
                            case 253:
                                if (((chData.mode.equals("IMPLICIT")) && ld.value.equals("increasing")) ||
                                    ((chData.mode.equals("EXPLICIT")) && ld.value.equals("not monotone")))
                                    { // value is valid
                                    }else 
                                        throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 253=%s != not monotone!", lineNum, filename, ld.value));
                                break;
                            case 260:
                                if (((chData.mode.equals("IMPLICIT")) && ld.value.equals("Time")) ||
                                    ((chData.mode.equals("EXPLICIT")) && ld.value.equals("Numeric")))
                                { // value is valid
                                }else 
                                    throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 260=%s != Numeric!", lineNum, filename, ld.value));
                                break;
                            case 264:
                                if (!chData.dataType.equals(ld.value))
                                    throw new Exception(Util.sprintf("Invalid channel content in line %d in file %s 264=%s != %s!", lineNum, filename, ld.value, chData.dataType));
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
        fin.close();
        dir = DiaDat_Direction.e_DiaDat_Dir_Read;
        step();
    }
    public DiaDat_Direction getDir()
    {
        return dir;
    }
    void addChannel(ChannelData chData) throws Exception
    {
        if (numOfRecords < 0)
        { // first channel
            numOfRecords = chData.numOfRecord;
            if (numOfRecords < 1)
                throw new Exception(Util.sprintf("Invalid record number of channel %s (%d)!", chData.chName, chData.numOfRecord));
        }else
        { // further records
            if (numOfRecords != chData.numOfRecord)
                throw new Exception(Util.sprintf("Invalid record number of channel %s (%d <-> %d)!", chData.chName, numOfRecords, chData.numOfRecord));
        }
        if (chData.mode.equals("IMPLICIT"))
        {
            if (!chData.dataFileName.isEmpty())
                throw new Exception(Util.sprintf("Invalid filename of implicit data of channel %s!", chData.chName));
            chData.dataFileName = "IMPLICIT:";
        }
        DiaDat_DataFileBase dataFile = dataFiles.get(chData.dataFileName);
        if (dataFile == null)
        {
            if (chData.mode.equals("EXPLICIT"))
                dataFile = new DiaDat_DataFile(this, chData.dataFileName);
            else
                dataFile = new DiaDat_DataFileImplicit(this, chData.dataFileName);
            dataFiles.put(chData.dataFileName, dataFile);
        }
        DiaDat_ChannelBase channel = dataFile.addChannel(chData);
        channels.put(chData.chName, channel);
    }

    public void step()
    {
        Collection<DiaDat_DataFileBase> res = dataFiles.values();
        Iterator<DiaDat_DataFileBase> i = res.iterator();
        while (i.hasNext()) {
           ((DiaDat_DataFileBase)i.next()).step();
        }
    }

    public DiaDat_ChannelBase getChannel(String chName)
    {
        return channels.get(chName);
    }

    DiaDat_Direction dir;
    TreeMap<String, DiaDat_DataFileBase> dataFiles = new TreeMap<String, DiaDat_DataFileBase>();
    TreeMap<String, DiaDat_ChannelBase> channels = new TreeMap<String, DiaDat_ChannelBase>();
    int numOfRecords = -1;
}
