package hzau.sa.sensorData.util;


import javax.xml.namespace.QName;

import hzau.sa.sensorData.common.KLHAConstant;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import java.util.ArrayList;
import java.util.List;

/**
 * 向昆仑海岸的服务端搜索
 * @author haokai
 */
public class ClientAxis2 {
    /**
     * @Param param     参数列表
     * @param url       url访问地址
     * @param method    服务器端发布的方法
     * @return
     */
    public static String sendService(List<String> param, String method) {
        //生命字符串xml
        String xml =null;
        //声明客户端
        RPCServiceClient serviceClient = null ;
        try {
            //获得客户端
            serviceClient = new RPCServiceClient();
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //可以再该对象中设置服务端的验证信息
        assert serviceClient != null;
        Options options = serviceClient.getOptions();
        EndpointReference targetEPR = new EndpointReference(KLHAConstant.URL);
        options.setTo(targetEPR);
        // 在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，也就是<wsdl:definitions>元素的targetNamespace属性值
        QName opAddEntry = new QName("http://action.web.iot_data_service.com",method);
        // 参数，如果有多个，继续往后面增加即可，不用指定参数的名称
        Object[] opAddEntryArgs = new Object[param.size()+2];
        opAddEntryArgs[0] = KLHAConstant.USERNAME;
        opAddEntryArgs[1] = KLHAConstant.PASSWORD;
        for (int i=0;i<param.size();i++){
            opAddEntryArgs[i+2] = param.get(i);
        }
        // invokeBlocking方法有三个参数，其中第一个参数的类型是QName对象，表示要调用的方法名；

        // 第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；

        // 第三个参数表示WebService方法的返回值类型的Class对象，参数类型为Class[]。

        // 当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}

        // 如果被调用的WebService方法没有返回值，应使用RPCServiceClient类的invokeRobust方法，

        // 该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同
        Class[] classes = new Class[] { String.class };

        try {
            xml=(String) serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0];
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return xml;
    }

}
