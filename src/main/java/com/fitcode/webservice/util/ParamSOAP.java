/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitcode.webservice.util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.axis.client.Call;

/**
 *
 * @author Max
 */
public class ParamSOAP {

    private Call call;
    private List<Object> param = new ArrayList<Object>();

    public ParamSOAP(Call call) {
        this.call = call;
    }

    public ParamSOAP addParam(String nameParam, Object value) {
        if (value instanceof String) {
            call.addParameter(nameParam, new QName("string"), ParameterMode.IN);
            param.add(value);
        } else if (value instanceof Integer) {
            call.addParameter(nameParam, new QName("int"), ParameterMode.IN);
            param.add(value);
        } else if (value instanceof Double) {
            call.addParameter(nameParam, new QName("double"), ParameterMode.IN);
            param.add(value);
        }
        return this;
    }

    /**
     * Retorna una cadena con JsonArray
     * @return
     * @throws RemoteException 
     */
    public String callOperationToString() throws RemoteException {
        return (String) call.invoke(param.toArray());
    }

    /**
     * Retorna una lista de Objetos, según el modelo que se pase
     * @param <T> Tipo de Modelo a retornar
     * @param type Modelo, Se injectará mediante los SETTER
     * @return
     * @throws RemoteException 
     */
    public <T> List<T> callOperationToModel(Class<T> type) throws RemoteException {
        List<T> list = new ArrayList<T>();
        JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(callOperationToString());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(type);
        for (int i = 0; i < jsonArray.size(); i++) {
            T bean = (T) JSONSerializer.toJava(jsonArray.getJSONObject(i), jsonConfig);
            list.add(bean);
        }
        return list;
    }

}
