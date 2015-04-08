# mule-splitter-aggregator

Mule project to split XML, send over JMS and aggregate response.

This project requires an [ActiveMQ][ActiveMQ] broker, running at `tcp://localhost:61616`.

The project was written in AnyPoint Studio 5.0.2 and run on Mule CE 3.6.1.


## Problems addressed

- Split XML message by XPath
- Route XML message by XPath
- Aggregate responses from JMS request-reply queues
- Merge separate XML messages (as held in a Mule message array) under a parent element
- Throw and catch specific exceptions


## Description

The application is invoked over HTTP.  It accepts a XML document structured along the lines of:-

    ItemList
      |
      |---Item
      |
      |---Item
      |
      |---Item (*n)

Each `Item` element has an attribute, `action`, which can be:-

- create
- update
- delete

Mule will break the incoming message down into separate messages, one per `Item` element.  Then, based on the value of the `action` attribute, Mule will route the item to specific JMS-based request-reply flows.

The JMS flows simply log that they have been invoked before sending the payload to the appropriate `ReplyTo` queue.

Mule then aggregates the messages once more, under a new `ItemList` element before responding to the HTTP consumer.


## Resources

Under the `src\test\resources\Postman` folder is a [Postman][Postman] collection, containing some test data.

More Mule stuff is on my [blog][blog].


[ActiveMQ]: http://activemq.apache.org/
[Postman]: https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm
[blog]: http://goochgooch.co.uk