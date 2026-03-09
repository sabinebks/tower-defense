
JC = javac
CLASSES = classes
SRC = src
TEST = test
LIB = junit-console.jar
CP = .:$(SRC):$(TEST):$(CLASSES):$(LIB)

all: classes jar

classes:
	javac -sourcepath src src/object/balloon/*.java src/object/tower/projectil/*.java src/object/tower/*.java src/board/*.java src/board/typeOfBoard/*.java src/game/*.java src/Livrables/*.java -d classes

tests:
	mkdir -p $(CLASSES)
	find $(TEST) -name "*.java" > tests.txt
	$(JC) -d $(CLASSES) -cp $(CP) @tests.txt
	
runtests: classes
	java -jar $(LIB) --class-path $(CLASSES) --scan-class-path

jar: classes
	jar cfe livrable3a.jar Livrables.Livrable3a -C $(CLASSES) .
	jar cfe livrable3b.jar Livrables.Livrable3b -C $(CLASSES) .

livrable3a: jar
	java -jar livrable3a.jar $(ARGS)

livrable3b: jar
	java -jar livrable3b.jar $(ARGS)

clean:
	rm -rf $(CLASSES) *.jar *.txt