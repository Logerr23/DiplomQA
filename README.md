## Процедура запуска автотестов

#### Предусловия
+ Установлены приложения IntelliJ IDEA, Docker Desktop.
+ Клонирован [репозиторий](https://github.com/Logerr23/DiplomQA).
+ Порты 8080, 9999, 5432, 3306 должны быть свободны.

### Запуск приложения
1. Запустить докер контейнеры командой `docker-compose up`
2. В новой вкладке терминала проверить запущенные контейнеры командой `docker ps`
3. Запустить тестируемое приложение командой:
  + для MySQL - `java -jar ./artifacts/aqa-shop.jar`
  + для PostgreSQL - `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -Dspring.datasource.username=app -Dspring.datasource.password=pass -jar ./artifacts/aqa-shop.jar`
4. Проверить запущенное приложение по [адресу](http://localhost:8080/)

### Запуск автотестов
В новой вкладке терминала ввести команду:
  + для MySQL - `./gradlew test -Dselenide.headless=true  --info`
  + для PostgreSQL - `./gradlew test -Dselenide.headless=true -Ddb.url=jdbc:postgresql://localhost:5432/app --info`

### Отчет Allure
Ввести команду `./gradlew allureServe`

### Завершение тестирования
+ Во вкладке терминала с сервером Allure ввести `Ctrl+C`.
+ Во вкладке терминала с SUT ввести `Ctrl+C`.
+ Во вкладке терминала с запущенными контейнерами ввести `Ctrl+C`.



