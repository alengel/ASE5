ASE Team 5 Group Project
====

This is the repository for the Advanced Software Engineering module at University of Sussex, 2013/2014.

This repository contains the Android codebase for the application PinMe. 

The Android folder contains the main code base and is linked to two dependencies. It relies on the Google Play Services to load it's location data and the AndroidTestTest folder contains the unit tests for the PinMe application.

Steps to install and run:

1. Load the projects into an Eclipse workspace.
2. Open the Java Build Path on the Android project and link the Google Play Services as a dependent library.
3. Open the Java Build Path on the AndroidTestTest project and link Android as a project.
4. Run the Android project and install the .apk on a mobile phone for testing/using the PinMe app. Ensure the phone has debugging enabled.
5. Run the AndroidTestTest project as an Android jUnit Test to see the test specifications pass/fail.