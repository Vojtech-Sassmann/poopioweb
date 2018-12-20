# PA165 team project - Creatures hunting

[![Build Status](https://travis-ci.org/VECTOUN/pa165project.svg?branch=master)](https://travis-ci.org/VECTOUN/pa165project)

A Java EE school team project.

Team members: 
* Vojtěch Sassmann
* Peter Balčirák
* Jan Gol
* Pavel Vyskočil

Wiki: https://github.com/VECTOUN/pa165project/wiki


# Running the project

`git clone https://github.com/VECTOUN/pa165project.git`
`cd pa165project`
`mvn clean install`
`cd creatures-hunting-rest-with-gui`
`mvn tomcat7:run`

# Testing GUI

The app is located at localhost:8080/pa165.
First, you need to login. 

Admin account: 
   * Email: admin@admin.cz
   * Password: admin

Regular user account:
   * Email: regular@regular.cz
   * Password: regular



# Testing REST

The secured REST is located at localhost:8080/pa165/rest/auth/*
The unsecured REST is located at localhost:8080/pa165/rest/unsecured/*

The unsecured REST is created so you can test it with curl. It is not possible to use curl on secured rest (it will always
return error 403).


Curl examples:

Get all monsters:
`curl -i -X GET http://localhost:8080/pa165/rest/notsecured/monsters`

Get monster by id:
`curl -i -X GET http://localhost:8080/pa165/rest/notsecured/monsters/4`

Get monster by name:
`curl -i -X GET http://localhost:8080/pa165/rest/notsecured/monsters/filter/name/Zombie`

Create monster:
`curl -X POST -i -H "Content-Type: application/json" --data '{"name":"test","height":155.2,"weight":70.5,"agility":"SLOW"}' http://localhost:8080/pa165/rest/notsecured/monsters/create`

Delete monster:
`curl -i -X DELETE http://localhost:8080/pa165/rest/notsecured/monsters/1`

Update monster:
`curl -i -X PUT -H "Content-Type: application/json" --data '{"agility": "FAST","name": "Zidan", "height": 160.2, "weight": 40.2}' http://localhost:8080/pa165/rest/notsecured/monsters/2`

Get the most widespread monsters:
`curl -i -X GET http://localhost:8080/pa165/rest/notsecured/monsters/filter/mostWidespread`

Add monster to area:
`curl -i -X POST -H "Content-Type: application/json" http://localhost:8080/pa165/rest/notsecured/areas/13/addMonsterToArea?id=2`

Remove monster from area:
`curl -i -X POST -H "Content-Type: application/json" http://localhost:8080/pa165/rest/notsecured/areas/13/removeMonsterFromArea?id=2`
