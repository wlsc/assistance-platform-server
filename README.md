# Assistance Platform
The Assistance Platform is a project which aims on providing assistance to users in all thinkable situations. Therefore it offers a sleek REST API for clients to upload data (e.g. sensor data) related to a user. The user can activate / deactivate so called "modules". A module is some sort of extension of the "Assistance Platform" which consumes user data. These modules implement assistance logic (when to notify the user, when to send a mail etc.) and extract higher-level information from the stream of user / device events.

This project hosts the implementation of the Platform REST web service, the Module API and a Shared API between Modules and the Platform.
