# Assistance Platform
[![codebeat badge](https://codebeat.co/badges/1d24d333-4d9b-4edc-8bd3-19876d151630)](https://codebeat.co/projects/github-com-telecooperation-assistance-platform-server)

The Assistance Platform is a project which aims on providing assistance to users in all thinkable situations. Therefore it offers a sleek REST API for clients to upload data (e.g. sensor data) related to a user. The user can activate / deactivate so called "modules". A module is some sort of extension of the "Assistance Platform" which consumes user data. These modules implement assistance logic (when to notify the user, when to send a mail etc.) and extract higher-level information from the stream of user / device events.

This project hosts the implementation of the Platform REST web service, the Module API and a Shared API between Modules and the Platform.

## Getting started

Follow these few steps for easy installation with open source [Ansible](https://ansible.com) IT automation tool.

### Prerequisites on your host machine

- Python >= 2.7
- Ansible >= 2.1
- Linux system (tested on Ubuntu 16.04)

### Installation instructions

- **Step 1**: Clone repository into some directory
- **Step 2**: Open **config.yml** ("assistance-platform-server/deployment/ansible/config/config.yml") with an editor and configure your parameters. *Default*: it is ready to be installed on localhost (don't forget to edit hosts.yml if you not willing to install on localhost).
- **Step 3**: After you configured it, cd to "assistance-platform-server\deployment\ansible\" and run command "ansible-playbook playbook.yml". The Ansible installation will begin.
- **Step 4**: Check installation was successfull by visiting http://localhost/
- **Step 5**: Your copy of **Assistance Platform** is up and running. Read documentation for [module development](https://telecooperation.github.io/assistance-platform-server/docs/developer/module-developers.-1.-getting-started-for-module-developers-(wip)/).

## Documentation
Can be found under the [link](https://telecooperation.github.io/assistance-platform-server/docs/).

## Clients
* [Android](https://github.com/Telecooperation/assistance-platform-client-android)
* [iOS](https://github.com/Telecooperation/assistance-platform-client-ios)

## Developers
* [Bennet Jeutter (Backend)](https://github.com/eintopf)
* [Wladimir Schmidt (Android assistant)](https://github.com/wlsc)
* [Nickolas Guendling (iOS assistant)](https://github.com/nickolasguendling)

## License
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
