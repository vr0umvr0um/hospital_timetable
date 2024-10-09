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

## CREDITS
My group project mates and the many explanatory tutorial videos on javaFX.
