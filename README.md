#YogaShare

######Author:  Eline Hermans
######Version: 1.0
######Date: January 2022

##Introduction

YogaShare is an application build for teachers and their students during the time of COVID. By using
this application they can share video's and keep connected as a community.

This project was developed in IntelliJ using the Spring Boot framework. 

##Functionality

Users can register to the application and login. After doing this successfully, they are able to watch video's that the yoga teachers have posted. The users have their own profile where they can add and update additional info. Their data is stored in a database, in this case PostgreSQL. Their passwords and sensitive data is encrypted and protected. 

Teachers are authorised as admin and are able to upload and delete these video's. They are also able to delete users. 


## Installation and configuration

This application uses the PostgreSQL database. This database can be installed [here](https://www.postgresql.org/download/). An instruction guide can be found there as well.
For more instructions on preparing the database, please read the INSTALLATION.pdf in the delivered .zip file (NOVI Hogeschool only).

To properly run this application, please be mindful of the data.sql file. In some cases, this data will not automatically be linked to the database. After starting the application and the database, this data has to be then run manually in the databases' SQL query. This will ensure the right roles being set in advance. 



### Notice

YogaShare is made as a final assignment for NOVI Hogeschool. Any used names or images are only for use
within this context and have no intent relation to the real world.
