package org.goochjs.muleSplitterAggregator;

import static org.goochjs.muleSplitterAggregator.Utilities.fileToString;
import static org.goochjs.muleSplitterAggregator.Utilities.stringToXMLDoc;
import static org.goochjs.muleSplitterAggregator.Utilities.xmlDocToString;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class testXML {

	@Test
	public void testLoadValidXML() {
		String testString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><child>value</child></root>";
		
		Document testXml = null;
		try {
			testXml = stringToXMLDoc(testString);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		assertEquals(testString, xmlDocToString(testXml));
	}

	
	@Rule public ExpectedException thrown= ExpectedException.none();
	@Test
	public void testLoadInvalidXML() throws SAXException {
	    String testString = "bernard";
		
	    thrown.expect( SAXException.class );
	    thrown.expectMessage("String " + testString + " is not valid XML");
	    
		@SuppressWarnings("unused")
		Document testXml = stringToXMLDoc(testString);
	}

	
	@Test
	public void testInstantiation() {
		XmlMerger merger = new XmlMerger("root");
		
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>", merger.toString());
	}

	
	@Test
	public void testMerge() throws TransformerException {
		XmlMerger merger = new XmlMerger("ItemList", "itm", "http://schemas.goochjs.org/item-v1");

		String xmlString = fileToString("src/test/resources/xml/Item-1.xml");
		merger.appendXml(xmlString);
		
		xmlString = fileToString("src/test/resources/xml/Item-2.xml");
		merger.appendXml(xmlString);
		
		xmlString = fileToString("src/test/resources/xml/Item-3.xml");
		merger.appendXml(xmlString);

		xmlString = fileToString("src/test/resources/xml/Item-4.xml");
		merger.appendXml(xmlString);
		
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        System.out.println("Test XML");
        System.out.println(fileToString("src/test/resources/xml/ItemList.xml"));

        System.out.println("New XML");
        System.out.println(merger.toString());
        
        DetailedDiff diff;
		try {
			diff = new DetailedDiff(XMLUnit.compareXML(fileToString("src/test/resources/xml/ItemList.xml"), merger.toString()));

			List<?> allDifferences = diff.getAllDifferences();
			Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
