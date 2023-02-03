default:
	javac client/Client1.java
	javac server/Server1.java
clean:
	rm -f client/Client1.class
	rm -f server/Server1.class