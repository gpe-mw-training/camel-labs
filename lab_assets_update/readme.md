cd camel-exercise-standalone
mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.MainApp1"
mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.MainApp2"

cd ../camel-exercise-spring-standalone
mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.SpringMainApp"

cd ../camel-exercise-spring-expressions
mvn exec:java -Dexec.mainClass="com.redhat.gpe.training.camel.SpringMainApp"

cd ../camel-exercise-exception
mvn clean camel:run

cd ../camel-exercise-dataformat
mvn clean camel:run

cd ../camel-exercise-unit-test
mvn clean test

cd ../camel-exercise-cbr-endpoint
mvn clean camel:run

