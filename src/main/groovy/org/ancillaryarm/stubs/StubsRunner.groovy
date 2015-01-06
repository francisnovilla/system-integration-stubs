package org.ancillaryarm.stubs

import org.apache.camel.spring.Main


print "!"

///**
// * Created by francis on 1/01/15.
// */

http://camel.apache.org/running-camel-standalone-and-have-it-keep-running.html
// create a Main instance
main = new StubsMain()
// enable hangup support so you can press ctrl + c to terminate the JVM
main.enableHangupSupport();
// bind MyBean into the registery
//main.bind("foo", new MyBean());
//// add routes
//main.addRouteBuilder(new MyRouteBuilder());

// run until you terminate the JVM
System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
String[] args = ["-ac","stubs-spring-context.xml"]
main.run(args)