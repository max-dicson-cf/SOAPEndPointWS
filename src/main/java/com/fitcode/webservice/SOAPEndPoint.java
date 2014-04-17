/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitcode.webservice;

import com.fitcode.webservice.util.ParamSOAP;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 *
 * @author Max
 */

public class SOAPEndPoint {

    private Service service = new Service();
    private Call call;

    private String ws_url;
    private String nameSpace = "http://schemas.xmlsoap.org/wsdl/";
    private List<Object> param = new ArrayList<Object>();

    /**
     * Uso solo para JSON
     * @param ws_url Example: http://www.web.com/webservice.php
     * @throws ServiceException 
     */
    public SOAPEndPoint(String ws_url) throws ServiceException {
        this.ws_url = ws_url;
        initValues();
    }

    private void initValues() throws ServiceException {
        service = new Service();
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(ws_url);
        call.clearOperation();
    }
    
    /**
     * Default : http://schemas.xmlsoap.org/wsdl/
     * @return 
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * Default Return Type: "string"
     * @param nameMetodth
     * @return 
     */
    public ParamSOAP getOperationMethod(String nameMetodth) throws ServiceException {
        initValues();
        call.setReturnType(new QName("string"));
        call.setOperationName(new QName(nameSpace, nameMetodth));
        return (new ParamSOAP(call));
    }
    
    /**
     * Default : http://schemas.xmlsoap.org/wsdl/
     *
     * @param nameSpace
     */
    @Deprecated
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

}
