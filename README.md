# Felix

### Overview
Felix is an application to be run on Asterisk servers and Kamalio servers when there's<br />
an environment needing a process to register in and out carriers and Asterisk servers from the<br />
cluster.<br /><br />

**Requirements**
* [Maven 2](http://maven.apache.org/)
* [Java JDK 8](https://jdk8.java.net/download.html)

### How It Works
A small agent runs on each Asterisk server and each Kamailio server.  They communicate with<br />
each other via Redis Pub/Sub.  Through it the Asterisk servers let the Kamailio server(s) know<br />
whether they are or aren't available to receive calls.

### Tasks

- [ ] Testing
  - [ ] Write tests
  - [ ] Make them work...
- [x] Decide on how configuration will be managed
- [x] Decided how to handle CLI arguments
- [ ] Redo how logging works
- [ ] Document the message structure

### Settings
Settings and configuration are handled by the application through the use of a properties file provided<br />
as an argument to the jar when executing.

### Authors
* Brian Downs