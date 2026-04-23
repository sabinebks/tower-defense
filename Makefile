all: classes

classes:
	javac -sourcepath src \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/entity/balloon/*.java \
		src/entity/tower/*.java \
		src/entity/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java \
		src/entity/tower/evolution/*.java \
		src/ui/*.java \
		src/choose/*.java \
		src/choose/action/*.java \
		src/clock/*.java \
		src/player/*.java \
		src/round/*.java -d classes

tests: classes
	javac -classpath junit-console.jar:classes \
		test/board/*.java \
		test/player/*.java \
		test/round/*.java \
		test/entity/balloon/*.java \
		test/entity/tower/*.java \
		test/choose/action/*.java -d classes

runtests: tests
	java -jar junit-console.jar -classpath classes --scan-class-path

jar: classes
	mkdir -p jar
	jar cvfe jar/towerdefense-a-interactive.jar Livrables.LauncherAInteractive -C classes .
	jar cvfe jar/towerdefense-a-random.jar Livrables.LauncherARandom -C classes .
	jar cvfe jar/towerdefense-b-interactive.jar Livrables.LauncherBInteractive -C classes .
	jar cvfe jar/towerdefense-b-random.jar Livrables.LauncherBRandom -C classes .

	# jars attendus par gitlab
	jar cvfe LivrableA.jar Livrables.LauncherAInteractive -C classes .
	jar cvfe LivrableB.jar Livrables.LauncherBInteractive -C classes .
docs:
	javadoc -d doc \
		src/board/*.java \
		src/board/typeOfBoard/*.java \
		src/entity/balloon/*.java \
		src/entity/tower/*.java \
		src/entity/tower/projectil/*.java \
		src/game/*.java \
		src/Livrables/*.java \
		src/entity/tower/evolution/*.java \
		src/ui/*.java \
		src/choose/*.java \
		src/choose/action/*.java \
		src/clock/*.java \
		src/player/*.java \
		src/round/*.java


clean:
	rm -rf classes doc jar
	ls *.jar | grep -v '^junit-console\.jar$$' | xargs rm -f