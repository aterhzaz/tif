Blueprint
=========
The camel-blueprint component allows you to deploy Camel routes as an OSGi bundle in the TIF container.

The Camel DSL is embedded in a blueprint (http://camel.apache.org/using-osgi-blueprint-with-camel.html) context.

Simple
------
The simple.xml is a blueprint definition allowing you to define a route with a timer.
Every 5 seconds, the timer creates an event and uses a constant "Hello World" body string.
This string is sent to a stream endpoint which displays the string in the TIF console.

Recipient List
--------------
The recipientlist.xml is a blueprint definition quite similar to the simple one.
But messages are sent to both a file and a stream endpoint.

Usage
===============================================================================

Note: Please follow the parent README.txt first for common build and container setup instructions.

Hot deployment
--------------
Start the TIF container

> cd container/bin/tif

Install the required features

> features:install camel-blueprint
> features:install camel-stream

You should be able to see the OSGi bundles for your Camel blueprint using

> osgi:list

Next, simply drop the src/main/resources/simple.xml or src/main/resources/recipientlist.xml 
in the container/deploy directory.

Every 5 seconds, you will see the "Hello World" message in the TIF console.  If you used
recipientlist.xml, you'll also see files created in a new result folder located under the 
container directory.


