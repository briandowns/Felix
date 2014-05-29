# Felix



## Overview
Felix is an application to be run on Asterisk servers and Kamalio servers when there's<br />
an environment needing a process to register in and out carriers and Asterisk servers from the<br />
cluster.


### How It Works
A small agent runs on each Asterisk server and each Kamailio server.  They communicate with<br />
each other via Redis Pub/Sub.  Through it the Asterisk servers let the Kamailio server(s) know<br />
whether they are or aren't available to receive calls.

### Authors
* Brian Downs

### License

Copyright (c) 2014 Brian Downs<br />
Licensed under the Apache License, Version 2.0 (the "License");<br />
you may not use this file except in compliance with the License.<br />
You may obtain a copy of the License at<br />
http://www.apache.org/licenses/LICENSE-2.0<br />
Unless required by applicable law or agreed to in writing, software<br />
distributed under the License is distributed on an "AS IS" BASIS,<br />
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br />
See the License for the specific language governing permissions and<br />
limitations under the License.<br />