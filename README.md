# Assistance Platform

The Assistance Platform is a project which aims on providing assistance to users in all thinkable situations. Therefore it offers a sleek REST API for clients to upload data (e.g. sensor data) related to a user. The user can activate / deactivate so called "modules". A module is some sort of extension of the "Assistance Platform" which consumes user data. These modules implement assistance logic (when to notify the user, when to send a mail etc.) and extract higher-level information from the stream of user / device events.

This project hosts the implementation of the Platform REST web service, the Module API and a Shared API between Modules and the Platform.

### Documentation
Can be found under the [link](https://telecooperation.github.io/assistance-platform-server/docs/).

### Developers
* [Bennet Jeutter](https://github.com/eintopf)

### License
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
