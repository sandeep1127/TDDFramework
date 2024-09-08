package com.qa.utils;

import java.io.InputStream;
import java.util.HashMap;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class TestUtils {

	
	public final long WAIT = 10;                                                                   // We will use this variable in webDriver wait  method created in BASE class while creating Explicit Wait method
	
	public HashMap<String, String> parseStringXML(InputStream file) throws Exception{              // This method is used to Read XML file expectedRsults 
		HashMap<String, String> stringMap = new HashMap<String, String>();
		//Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		 
		//Build Document
		Document document = (Document) builder.parse(file);
		 
		//Normalize the XML Structure; It's just too important !!
		((Node) document.getDefaultRootElement()).normalize();
		 
		//Here comes the root node
		Element root = (Element) document.getDefaultRootElement();
		 
		//Get all elements
		NodeList nList = ((org.w3c.dom.Document) document).getElementsByTagName("string");   // It will look for 'String' tag mentioned in xml of Expected Results, and will load it in NodeList
		 
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
		 Node node = nList.item(temp);
		 if (node.getNodeType() == Node.ELEMENT_NODE)
		 {
		    Element eElement = (Element) node;
		    // Store each element key value in map
		    stringMap.put((String) ((DocumentBuilderFactory) eElement).getAttribute("name"), ((Node) eElement).getTextContent());  // Name Attriute is the 'Key' in expected results Xml and Value is the 'text Content'
		 }
		}
		return stringMap;                               // SO SINCE we will use STRINGS through our automation, we need to initialize HASMAP in BASE CLASS
	}
	
	
	
	
	
	
	

}
