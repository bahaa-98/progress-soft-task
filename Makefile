# Makefile

# Variables
JAVA_FILES := $(shell find src -name '*.java')
CLASS_PATH := target/classes
LIB_PATH := lib
JAR_FILE := target/api-*.jar

# Targets
.PHONY: all clean run

all: $(JAR_FILE)

$(JAR_FILE): $(JAVA_FILES)
	@mvn clean package

clean:
	@mvn clean
	@rm -f $(JAR_FILE)

run: $(JAR_FILE)
	@java -jar $(JAR_FILE)
