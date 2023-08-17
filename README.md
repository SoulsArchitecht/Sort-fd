# Sort-fd Тестовое задание по курсу Java ШИФТ ЦФТ
## "Сортировка слиянием нескольких файлов"

## Инструкция по запуску
Для сборки и запуска проекта потребуется Java 17 и Maven 3.8.\*. Пожалуйста, проверьте, что они у вас установлены и прописаны в системном окружении.
```
mvn --version
java --version
```
В процессе разработки были использованы следующие версии Java и Maven
* Oracle OpenJDK 17.0.4 - Java
* Maven 3.8.1 - система сборки

При сборке использовались следующие зависимости из репозитория Maven:
* commons-cli 1.5.0 https://mvnrepository.com/artifact/commons-cli/commons-cli/1.5.0
* slf4j-api 2.0.7 https://mvnrepository.com/artifact/org.slf4j/slf4j-api/2.0.7
* slf4j-simple 2.0.7 https://mvnrepository.com/artifact/org.slf4j/slf4j-simple/2.0.7
* maven-assembly 3.6.0 https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-assembly-plugin/3.6.0

### Сборка проекта в JAR-файл
* скачайте и распакуйте проект из репозитория
* откройте терминал в директории проекта и введите:
```
java -jar <FileName.jar> <args>
```
* \<FileName.jar\> - полное имя файла с расширением .jar
* \<args\> - параметры необходимые для запуска программы

### Параметры запуска программы
* ```-a``` (```-d```) - сортировка в порядке возрастания (убывания). (Необязательный параметр). По умолчанию происходит сортировка в порядке возрастания
* ```-i``` (```-s```) - сортировка производится для целых чисел (строк). (Обязательный параметр).
следом нужно указать:
* имя файла, куда будет записан результат. (Обязательно)
* имя файла (файлов), откуда будут браться данные для сортировки. (Обязательно не менее одного). Данные в файлах должны быть заранее отсортированы по возрастанию (убыванию)
и располагаться в столбик. Если в строковых файлах будут пробелы - возникнут обрабатываемые ошибки. Если файлы будут не отсортированы - возникнут обрабатываемые ошибки

### Примеры запуска
```
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -a -s output.txt st1.txt st2.txt (верно)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -i -b output.txt in1.txt in2.txt in3.txt (верно)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -b -s st1.txt (ошибка, не указан файл для записи или чтения)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -i -s output.txt st1.txt st2.txt (ошибка, неопределен тип данных сортировки)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -a output.txt st1.txt st2.txt (ошибка, не указан тип данных сортировки)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -a -ш  (ошбика, не указаны файлы для сортировки и записи)
java -jar Sort-fd-1.0-SNAPSHOT-jar-with-dependencies.jar -s output.txt st1.txt (верно)
```
### Пример файлов для запуска
```
in1.txt  in2.txt  in3.txt
1        3        2
4        4        5
12       8        25
```
### Пример файла с результатами при аргументах -a -i out.txt in1.txt in2.txt in3.txt
```
out.txt
1
2
3
4
4
5
8
12
25
```
-----------------------------------------------------------------------------------


