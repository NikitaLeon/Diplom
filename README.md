# Дипломный проект по профессии «Тестировщик»
## Документы
* [Планирование автоматизации тестирования](https://github.com/NikitaLeon/Diplom/blob/main/docs/Plan.md)
* [Отчёт о проведённом тестировании](https://github.com/NikitaLeon/Diplom/blob/main/docs/Report.md)
* [Отчёт об автоматизации тестирования](https://github.com/NikitaLeon/Diplom/blob/main/docs/Summary.md)
## Подготовка
* установить Docker;
* убедиться, что порты 8080, 3306 или 5402 свободны.
## Установка
1. Скачать [архив](https://github.com/NikitaLeon/Diplom/blob/main/artifacts/aqa-shop.jar)
2. Запустить контейнер с базой данных командой `docker-compose up --build`
3. Запустить SUT с указание пути к базе данных командой:
* для MySQL `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
* для PostgreSQL `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
4. Запустить автотесты командой:
* для MySQL `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
* для PostgreSQL `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
5. Для просмотра Allure отчёта ввести команду `./gradlew allureServe`
