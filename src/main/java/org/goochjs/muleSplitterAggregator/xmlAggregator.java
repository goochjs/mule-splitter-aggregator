package org.goochjs.muleSplitterAggregator;

import org.mule.DefaultMuleEvent;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.store.ObjectStoreException;
import org.mule.routing.AbstractCorrelationAggregator;
import org.mule.routing.AggregationException;
import org.mule.routing.EventGroup;

public class xmlAggregator extends AbstractCorrelationAggregator{

	@Override
	protected MuleEvent aggregateEvents(EventGroup events)
			throws AggregationException {

//		MuleEvent returnEvent = new DefaultMuleEvent(null, flowConstruct);
		
		MuleEvent[] eventArray = null;

		try {
			eventArray = events.toArray(true);
		} catch (ObjectStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (MuleEvent event : eventArray) {
			String message = "";
			
			try {
				message = event.getMessageAsString();
			} catch (MuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("EVENTY = " + message);
		}
//		returnEvent.setMessage(eventArray[0].getMessage());
		try {
			return super.process(eventArray[0]);
		} catch (MuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AggregationException(events, null);
		}
	}
}
