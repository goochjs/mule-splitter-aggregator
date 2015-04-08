package org.mule.transformers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.goochjs.muleSplitterAggregator.xmlMerger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class CollectionToXML extends AbstractMessageTransformer {
    /**
     * Iterate through the collection within the Mule message and put the component parts
     * together into an over-arching XML document.
     * 
     * @param args
     */
    // TODO find a better solution than suppressing the cast checking
	@SuppressWarnings("unchecked")
	public String transformMessage(MuleMessage incomingMessage, String outputEncoding) throws TransformerException {
    	System.out.println(incomingMessage.toString());
    	
    	List<String> payloadMessages = new CopyOnWriteArrayList<String>();
    	payloadMessages = (List<String>) incomingMessage.getPayload();
    	
		xmlMerger merger = new xmlMerger("ItemList", "itm", "http://schemas.goochjs.org/item-v1");

		for (String message : payloadMessages) {
    		merger.appendXml(message);
    	}
    	
    	return merger.toString();
    }
}
	