package hzau.sa.sensorData.util;


import hzau.sa.sensorData.common.SensorType;
import hzau.sa.sensorData.entity.SensorDataRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hasee
 */
public class XmlPrasing {
    public static List<SensorDataRecord> parsingXMLByCurAll(String xml){
        List<SensorDataRecord> sensorDataRecords = new ArrayList<>();
        try {
            //创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            //从xml字符串中读取数据
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document doc = builder.parse(inputStream);
            //取的根元素
            Element root = (Element) doc.getDocumentElement();
            //得到根元素的名称
            String rootName = root.getNodeName();
            //得到根元素所有子元素的集合
            NodeList nodelist = root.getChildNodes();
            //得到list集合的size()
            int size = nodelist.getLength();

            for(int i = 0;i < size;i++){
                Node node = nodelist.item(i);
                NodeList nodeList = node.getChildNodes();
                HashMap<String,String> map = new HashMap();
                for(int j = 0;j < nodeList.getLength();j++){
                    Node node1 = nodeList.item(j);
                    NodeList nodeList1 = node1.getChildNodes();
                    map.put(nodeList1.item(0).getTextContent(),nodeList1.item(1).getTextContent());
                }
                sensorDataRecords.add(new SensorDataRecord(map));
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sensorDataRecords;
    }


    public static HashMap<String,List<SensorDataRecord>> parsingXMLByHistory(String xml){
        HashMap<String,List<SensorDataRecord>> sensorDataRecords = new HashMap<>();
        HashMap<String, String> sensorType = SensorType.sensorType;
        for(Map.Entry<String, String> entry : sensorType.entrySet()){
            sensorDataRecords.put(entry.getValue(), new ArrayList<>());
        }
        try {
            //创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            //从xml字符串中读取数据
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document doc = builder.parse(inputStream);
            //取的根元素
            Element root = (Element) doc.getDocumentElement();
            //得到根元素的名称
            String rootName = root.getNodeName();
            //得到根元素所有子元素的集合
            NodeList nodelist = root.getChildNodes();
            //得到list集合的size()
            int size = nodelist.getLength();

            for(int i = 0;i < size;i++){
                Node node = nodelist.item(i);
                NodeList nodeList = node.getChildNodes();
                HashMap<String,String> map = new HashMap();
                for(int j = 0;j < nodeList.getLength();j++){
                    Node node1 = nodeList.item(j);
                    NodeList nodeList1 = node1.getChildNodes();
                    map.put(nodeList1.item(0).getTextContent(),nodeList1.item(1).getTextContent());
                }
                SensorDataRecord sensorDataRecord = new SensorDataRecord(map);
                sensorDataRecords.get(SensorType.sensorType.get(sensorDataRecord.getChannelName())).add(0,sensorDataRecord);
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sensorDataRecords;
    }

    public static List<SensorDataRecord> parsingXMLBySensor(String xml, String sensorChannel) {
        List<SensorDataRecord> sensorDataRecords = new ArrayList<>();
        try {
            //创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            //从xml字符串中读取数据
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document doc = builder.parse(inputStream);
            //取的根元素
            Element root = (Element) doc.getDocumentElement();
            //得到根元素的名称
            String rootName = root.getNodeName();
            //得到根元素所有子元素的集合
            NodeList nodelist = root.getChildNodes();
            //得到list集合的size()
            int size = nodelist.getLength();

            for(int i = 0;i < size;i++){
                Node node = nodelist.item(i);
                NodeList nodeList = node.getChildNodes();
                HashMap<String,String> map = new HashMap();
                for(int j = 0;j < nodeList.getLength();j++){
                    Node node1 = nodeList.item(j);
                    NodeList nodeList1 = node1.getChildNodes();
                    map.put(nodeList1.item(0).getTextContent(),nodeList1.item(1).getTextContent());
                }
                if(sensorChannel.equals(map.get("channel_name"))) {
                    SensorDataRecord sensorDataRecord = new SensorDataRecord(map);
                    sensorDataRecords.add(sensorDataRecord);
                }
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sensorDataRecords;
    }

/*
    public static ArrayList getMaxAndMin(String xml){
        HashMap<String,ExportSensorDataModel> exportSensorDataModels = new HashMap<>();
        for (Map.Entry<String,String> entry : SensorType.sensorType.entrySet()){
            ExportSensorDataModel exportSensorDataModel = new ExportSensorDataModel();
            exportSensorDataModel.setSensorName(entry.getValue());
            exportSensorDataModel.setMaxValue(String.valueOf(Integer.MAX_VALUE));
            exportSensorDataModel.setMinValue(String.valueOf(Integer.MIN_VALUE));
            exportSensorDataModels.put(entry.getKey(),exportSensorDataModel);
        }
        try {
            //创建DocumentBuilderFactory实例,指定DocumentBuilder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            //从xml字符串中读取数据
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document doc = builder.parse(inputStream);
            //取的根元素
            Element root = (Element) doc.getDocumentElement();
            //得到根元素的名称
            String rootName = root.getNodeName();
            //得到根元素所有子元素的集合
            NodeList nodelist = root.getChildNodes();
            //得到list集合的size()
            int size = nodelist.getLength();

            for(int i = 0;i < size;i++){
                Node node = nodelist.item(i);
                NodeList nodeList = node.getChildNodes();
                HashMap<String,String> map = new HashMap();
                for(int j = 0;j < nodeList.getLength();j++){
                    Node node1 = nodeList.item(j);
                    NodeList nodeList1 = node1.getChildNodes();
                    map.put(nodeList1.item(0).getTextContent(),nodeList1.item(1).getTextContent());
                }
                ExportSensorDataModel exportSensorDataModel = exportSensorDataModels.get(map.get("channel_name"));
                //49号传感器为风向 ， 无法比较大小
                if( !"49".equals(map.get("channel_name")) ){
                    if(Double.parseDouble(exportSensorDataModel.getMaxValue())<Double.parseDouble(map.get("value"))){
                        exportSensorDataModel.setMaxValue(map.get("value"));
                        exportSensorDataModel.setMaxValueTime(map.get("data_time"));
                    }else if(Double.parseDouble(exportSensorDataModel.getMinValue())>Double.parseDouble(map.get("value"))){
                        exportSensorDataModel.setMinValue(map.get("value"));
                        exportSensorDataModel.setMinValueTime(map.get("data_time"));
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (ArrayList)exportSensorDataModels.values();
    }
*/



}
