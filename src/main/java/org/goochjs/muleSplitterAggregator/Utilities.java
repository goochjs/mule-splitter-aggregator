package org.goochjs.muleSplitterAggregator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utilities {

	/**
	 * Read the contents of a file and place them in a string object.
	 *
	 * @param file
	 *            path to file.
	 * @return String contents of the file.
	 */
	public static String fileToString(String file) {
		String contents = "";

		File f = null;
		try {
			f = new File(file);

			if (f.exists()) {
				FileReader fr = null;
				try {
					fr = new FileReader(f);
					char[] template = new char[(int) f.length()];
					fr.read(template);
					contents = new String(template);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fr != null) {
						fr.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}


	/**
	 * Place the contents of an XML Document into a string object.
	 *
	 * @param doc
	 *            XML Document object.
	 * @return String contents of the document.
	 */
	public static String xmlDocToString(Document doc) {
		
		String output = null;	
		TransformerFactory transformerFactory=TransformerFactory.newInstance();
		Transformer transformer;

		try {
			transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));

			output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;	
	}

	
	/**
	 * Place the contents of a string into an XML Document.
	 *
	 * @param xmlString
	 *            XML string.
	 * @return XML Document object.
	 */
	public static Document stringToXMLDoc(String xml) throws SAXException {
		Document output = null;
		
	    try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			output = docBuilder.parse(is);
		} catch (ParserConfigurationException pe) {
			// TODO Auto-generated catch block
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new SAXException("String " + xml + " is not valid XML");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}
}