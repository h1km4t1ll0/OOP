javac src/main/java/ru/nsu/dolgov/HeapSort.java -d ./build
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.dolgov
java -cp ./build ru.nsu.dolgov.HeapSort
