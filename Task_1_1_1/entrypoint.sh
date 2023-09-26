#cd ./src/main/java/ru/nsu/dolgov/heapSort
#javac ./src/main/java/ru/nsu/dolgov/heapSort/HeapSort.java
#cd ..
#cd ..
#cd ..
#cd ..
#cd ..
#cd ..
#cd ..
#cd ./src/main/java/ru/nsu/dolgov/main
javac ./src/main/java/ru/nsu/dolgov/main/Main.java
#gradle javadoc
#java main.Main
#jar cf heapSort.jar ./main/Main.class
#jar cf file.jar ./src/main/java/ru/nsu/dolgov/main/main.class ./src/main/java/ru/nsu/dolgov/heapSort/HeapSort.class