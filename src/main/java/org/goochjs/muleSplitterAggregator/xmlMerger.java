package org.goochjs.muleSplitterAggregator;

import static org.goochjs.muleSplitterAggregator.Utilities.stringToXMLDoc;
import static org.goochjs.muleSplitterAggregator.Utilities.xmlDocToString;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Jeremy
 *
 * Instantiates an empty XML document, with root element name of your choice.
 * Additional XML documents can then be added one at a time under the root.
 * The child elements will be named the same as whatever the additional
 * document's root was called.
 * 
 */
public class XmlMerger {
	private Document doc = null;
	private Element rootElement = null;
	
	
	/**
	 * Instantiate the class, accepting a string as the root element name.
	 * 
	 * @param rootElementName
	 */
	public XmlMerger(String rootElementName) {
		initialise();

		rootElement = doc.createElement(rootElementName);
		doc.appendChild(rootElement);
	}

	
	/**
	 * Instantiate the class, accepting a string as the root element name
	 * and a namespace, including prefix.
	 * 
	 * @param rootElementName
	 * @param rootNamespacePrefix
	 * @param rootNamespace
	 */
	public XmlMerger(String rootElementName, String rootNamespacePrefix, String rootNamespace) {
		initialise();
		
		rootElement = doc.createElementNS(rootNamespace, rootElementName);
		
		// set the "xmlns" namespace
		rootElement.setAttributeNS(
				XMLConstants.XMLNS_ATTRIBUTE_NS_URI,
				XMLConstants.XMLNS_ATTRIBUTE + ":xsi",
				XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		
		doc.appendChild(rootElement);
	}

	
	private void initialise() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root element
			doc = docBuilder.newDocument();
			
			// TODO remove this next line to do namespaces and XSDs
			doc.setXmlStandalone(true);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Take an incoming XML string and add it as a new child under
	 * the main document's root element.
	 * 
	 * @param xmlString
	 */
	public void appendXml(String xmlString) {
		try {
			Document newXml = stringToXMLDoc(xmlString);
			
			Element newElement = newXml.getDocumentElement();
			Node importedXml = doc.importNode(newElement, true);

			// TODO we ought to move namespace and schemaLocation stuff to the root element
			rootElement.appendChild(importedXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public String toString() {
		return xmlDocToString(this.getDoc());
	}

	
	public Document getDoc() {
		return doc;
	}
}