Name: Akhil Parimi

Email: aparimi1@binghamton.edu

The code was tested on remote.cs and working.

Makefile is generated and can be run using 'make' from the root folder.

***To compile:***
javac Client1.java
javac Server1.java

***To run:***
java Server1.java 6489
java Client1.java remote02 6489

***Instructions:***
***Please use password as 123456 wherever it asks for password.

***To genearate Certificate***
1) To Generate the server Certificate:
***keytool -genkey -keyalg RSA -keysize 2048 -validity 360 -alias mykey -keystore mainKeyStore.jks***
2) Export the certficate and the public key that should be send to the client:
***keytool -export -alias mykey -keystore mainKeyStore.jks -file mykey.cert***
3) Add the key at the client side to a TrustedStore to trust the server:
***keytool -import -file mykey.cert -alias mykey -keystore mainTrustStore.jts***

***Note:***
* If the keywork 'ls' is given the list of directories and subdirectories will be printed on Client side [Message sent from server].
* If the keywork 'pwd' is given the corresponding path will be printed on Client side [Message sent from server].
* If the keywork 'exit' is given the connection will be terminated.
* Other than the above mentioned keywords when given - the conenction will be terminated.
