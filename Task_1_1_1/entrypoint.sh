javac src/main/java/ru/nsu/dolgov/heapsort/HeapSort.java -d ./build
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.dolgov.heapsort
java -cp ./build ru.nsu.dolgov.heapsort.HeapSort
