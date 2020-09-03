package hzau.sa.sensorData.service;

import hzau.sa.sensorData.common.KLHAConstant;
import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.util.ClientAxis2;
import org.springframework.stereotype.Service;
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

/**
 * 传感器业务逻辑
 * @author haokai
 */
@Service
public class SensorService {

    public List<SensorDataRecord> getCurAll(){
        String xml = ClientAxis2.sendService(KLHAConstant.USERNAME,
                KLHAConstant.PASSWORD, KLHAConstant.URL, KLHAConstant.GET_ALL_METHOD);
        List<SensorDataRecord> sensorDataRecords = parsingXMLByCurAll(xml);
        //sensorDataRecords.forEach(System.out::println);
        return sensorDataRecords;
    }
    public List<SensorDataRecord> parsingXMLByCurAll(String xml){
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
}
