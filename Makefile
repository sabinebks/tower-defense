JC = javac
CLASSES = classes
SRC = src
TEST = test
LIB = junit-console.jar
CP = ".:$(SRC):$(TEST):$(CLASSES):$(LIB)"

all: classes jar

classes:
	mkdir -p $(CLASSES)
	find $(SRC) $(TEST) -name "*.java" > sources.txt
	$(JC) -d $(CLASSES) -cp $(CP) @sources.txt

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