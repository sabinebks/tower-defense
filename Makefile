all: classes

classes:
	mkdir -p classes
	javac -d classes \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/object/balloon/*.java \
		src/object/tower/*.java \
		src/object/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java

tests: classes
	javac -cp junit-console.jar:classes -d classes \
		test/board/*.java \
		test/game/*.java \
		test/object/balloon/*.java \
		test/object/tower/*.java

runtests: tests
	java -jar junit-console.jar -cp classes --scan-class-path

jar: classes
	jar cvfe livrable3a.jar Livrables.Livrable3a -C classes .
	jar cvfe livrable3b.jar Livrables.Livrable3b -C classes .

docs:
	javadoc -d doc \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/object/balloon/*.java \
		src/object/tower/*.java \
		src/object/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java

clean:
	rm -rf classes *.jar doc