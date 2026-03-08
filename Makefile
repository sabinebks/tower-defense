
all:
	mkdir -p classes
	javac -d classes src/board/*.java src/object/balloon/*.java

tests: all
	javac -cp junit-console.jar:classes -d classes test/board/*.java test/object/balloon/*.java

runtests: tests
	java -jar junit-console.jar -cp classes --scan-class-path

jar: all
	jar cvfe livrable2a.jar board.Livrable2a -C classes .
	jar cvfe livrable2b.jar board.Livrable2b -C classes .

docs:
	javadoc -d docs src/board/*.java src/object/balloon/*.java

clean:
	rm -rf classes *.jar docs