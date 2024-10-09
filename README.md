# hospital_timetable

## DESCRIPTION
Hospital timetable is one of the projects in my second year of a computer science degree.  It was carried out in groups of 6 over 2 months. 
It's a hospital timetable application that lets you add/modify/delete/consult staff, create a timetable with different slots, consult the timetable globally and weekly or by staff, and consult legal/speciality constraints whether they're respected or not. Staff functions and specialities can also be consulted.

Here is a list of all the files I have coded and what I've done besides coding:
-  Contrainte,
-  ContrainteDeSpecialité,
-  ContrainteLégale,
-  ContrainteDeSpécialitéDAO,
-  ContrainteLégaleDAO,
-  TestContrainte,
-  TestContrainteDeSpecialite,
-  TestContrainteLegale,
-  TesContrainteDeSpecialiteDAO,
-  TestContrainteLegaleDAO.,
-  DAO,
-  SingleConnection,
-  Main,
-  Backlog,
-  UML scenarios,
-  Coding convention,
-  Javadoc,
-  MCD diagram.

I've coded and documented all of those files along with their FXML files and their corresponding "Controleur..." files. It took approximately 100h for all those files.

## SET-UP GUIDE
1. Download [JavaFX SDK](https://gluonhq.com/products/javafx/) depending on your operating system.
2. Download projects and open it with an IDE through import.
3. Go to Windows -> Preferences -> Libraries -> User Library -> Create a new folder -> Add external jars -> Click on your extracted javaFX directory -> lib -> Select all files -> Add   -> Apply and close.
4. Download eclipse extension called "e(fx)clipse".
5. On your left panel, open "lib" directory -> Show in system explorer -> Select "controlfx", "fontawesomefx", "mysql-connector" and "jfoenix" -> Add them to your project folder.
6. Click on your project -> Build path -> Configure build path -> Module path -> Add Libraries -> User library and add the created library of step 3.
7.  Click on "Module path" -> Add Library -> Add "JavaFX SDK".
8.  Click on "Module path" -> Add External Jars -> Add "controlfx", "fontawesomefx", "mysql-connector" and "jfoenix".
9.  On run configuration -> Arguments -> Add "--module-path(absolute link of the projects) --add-modules javafx.controls,javafx.graphics,javafx.fxml".
10.  Start MySQL servers.
11.  Using phpMyAdmin, create a new databal named "planninghospitalier" and import the file "planninghospitalier.sql".
12.  Go to "package dao" -> "singleConnection.java" -> Change the arguments to connect to the database in method "getInstance()".

## SCREENSHOTS
### HOMEPAGE/TIMESLOTS INTERFACE
![image](https://github.com/user-attachments/assets/577625d6-3d5c-419e-917f-1db5ff17523f)
#### ADD TIMESLOT
![image](https://github.com/user-attachments/assets/2c85c4bd-3749-4658-bb96-32ba783bf1ec)
#### TIMESLOT DETAILS
![image](https://github.com/user-attachments/assets/7b09ea7a-4df1-4a6f-a61f-b47f567c6ff1)
#### TIMESLOT REQUIREMENTS
##### ADD REQUIREMENT
![image](https://github.com/user-attachments/assets/6ae353fa-f01a-4499-b903-d11fe5a666ba)
##### ADD STAFF
![image](https://github.com/user-attachments/assets/d5ed54c6-d26d-41db-9e04-f9e32cdaa383)
### STAFF INTERFACE
#### VIEW
![image](https://github.com/user-attachments/assets/33bfe8f9-1ec1-4569-a360-5f8e3207c5ee)
#### ADD STAFF
![image](https://github.com/user-attachments/assets/8fda69f2-23d6-484d-b25b-8a068409d843)
#### STAFF INFORMATIONS
![image](https://github.com/user-attachments/assets/3f2fae64-0352-4ad6-94ca-a64fe32335f7)
### FUNCTIONS INTERFACE
![image](https://github.com/user-attachments/assets/1792f80f-cbb2-4617-bfb8-0949623d946d)
#### ADD FUNCTION
![image](https://github.com/user-attachments/assets/4c1af257-0bf9-40bb-9bdd-19acf10e0ff8)
### CONSTRAINTS INTERFACE
![image](https://github.com/user-attachments/assets/ebc20fc2-194f-4df3-8990-811ffe023727)
#### ADD CONSTRAINT
![image](https://github.com/user-attachments/assets/d053aa52-ed3a-482b-b863-3bf15a1a88e7)
![image](https://github.com/user-attachments/assets/7e9fccc0-f86e-437b-b34b-fba5d0416910)

## CREDITS
My group project mates and the many explanatory tutorial videos on javaFX.
