system-integration-stubs
========================

Groovy and Camel based skeleton maven project for writing stubs for interfaces external to your application. This (will eventually) consist of examples for these type of interfaces:

* REST
* SOAP
* JMS
* SMTP

Though this can be used standalone to provide a fully-stubbed environment during development, the main intended usecase is as a scriptable tool for automated testing, providing the following functions:

* set stub behaviour programatically (return pre-set response, set latency, return errors etc.)
* interface to retrieve received exchanges (to run assertions against)
