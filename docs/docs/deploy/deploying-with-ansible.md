# Deploying with Ansible

In this section you will read how to deploy the Assistant Platform with Ansible.

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