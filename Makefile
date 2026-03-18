all: classes

classes:
	javac -sourcepath src  \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/object/balloon/*.java \
		src/object/tower/*.java \
		src/object/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java \
		src/object/tower/evolution/*.java -d classes

tests: classes
	javac -classpath junit-console.jar:classes \
		test/board/*.java \
		test/game/*.java \
		test/object/balloon/*.java \
		test/object/tower/*.java -d classes

runtests: tests
	java -jar junit-console.jar -classpath classes --scan-class-path

jar: classes
	jar cvfe livrable4a.jar Livrables.Livrable4a -C classes .
	jar cvfe livrable4b.jar Livrables.Livrable4b -C classes .

docs:
	javadoc -d doc \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/object/balloon/*.java \
		src/object/tower/*.java \
		src/object/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java \
		src/object/tower/evolution/*.java

clean:
	rm -rf classes doc
	ls *.jar | grep -v '^junit-console\.jar$$' | xargs rm -f