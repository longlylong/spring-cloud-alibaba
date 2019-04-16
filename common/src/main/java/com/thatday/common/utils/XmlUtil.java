package com.thatday.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class XmlUtil {

    /**
     * 获取xml格式的字符串的根节点下的全部子节点内容
     */
    public static SortedMap<String, String> getElements(String xmlStr) throws DocumentException {
        SortedMap<String, String> elementMap = new TreeMap<>();
        Document document = DocumentHelper.parseText(xmlStr);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
            Element element = it.next();
            String key = element.getName();
            String text = element.getText();
            elementMap.put(key, text);
        }
        return elementMap;
    }

    /**
     * 微信请求xml组装
     */
    public static String getRequestXml(SortedMap<String, Object> parameters) {
        return getRequestXml(parameters, "xml");
    }

    /**
     * 将map数据组装成xml格式的字符串
     */
    public static String getRequestXml(SortedMap<String, Object> parameters, String rootKey) {
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<" + rootKey + ">");
        for (String key : parameters.keySet()) {
            String value = parameters.get(key).toString();
            xmlStr.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
        }
        xmlStr.append("</" + rootKey + ">");
        return xmlStr.toString();
    }

}
