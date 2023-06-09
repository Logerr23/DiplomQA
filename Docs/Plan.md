# План автоматизации

## Перечень автоматизируемых сценариев.

### Предусловия:
1) Пользователь находится на странице «Путешествие дня», нажимает кнопку "Купить"
2) Пользователь находится на странице «Путешествие дня», нажимает кнопку "Купить в кредит"

### Тестовые сценарии
#### Позитивные:
1) Успешная покупка
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные отправляются, появляется сообщение об успешной оплате)


#### Негативные:
1) Отказ банка (карта не одобрена)
   + Заполнить поле «Номер карты» (16 цифр, заблокированная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные отправляются, появляется сообщение об отказе в проведении операции)

2) Неверный формат номера карты (меньше 16 цифр)
   + Заполнить поле «Номер карты» (меньше 16 цифр)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Номер карты" подсвечивается с ошибкой "Неверный формат")

3) Поле "Номер карты" не заполнено
   + Поле «Номер карты» оставить пустым
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Номер карты" подсвечивается с ошибкой "Неверный формат")

4) Неверный формат месяца (1 цифра)
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (1 цифра)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Месяц" подсвечивается с ошибкой "Неверный формат")

5) Поле "Месяц" не заполнено
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Поле «Месяц» оставить пустым
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Месяц" подсвечивается с ошибкой "Поле обязательно для заполнения")

6) Неверный формат года (1 цифра)
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Заполнить поле «Год» (1 цифра)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Год" подсвечивается с ошибкой "Неверный формат")

7) Поле "Год" не заполнено
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
   + Поле «Год» оставить пустым
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Год" подсвечивается с ошибкой "Поле обязательно для заполнения")

8) Карта просрочена (прошедшая дата от текущей)
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют прошедшей дате от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют прошедшей дате от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Год" подсвечивается с ошибкой "Истёк срок действия карты")

9) Неверно указан срок действия карты (больше пяти лет от текущей)
   + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
   + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, больше пяти лет от текущей)
   + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, больше пяти лет от текущей)
   + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
   + Заполнить поле «CVC/CVV» (3 цифры)
   + Нажать кнопку «Продолжить» (данные не отправляются, поле "Год" подсвечивается с ошибкой "Неверно указан срок действия карты")

10) Неверный формат поля "CVC/CVV" (меньше 3 цифр)
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
    + Заполнить поле «CVC/CVV» (1-2 цифры)
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "CVC/CVV" подсвечивается с ошибкой "Неверный формат")

11) Поле "CVC/CVV" не заполнено
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на латинице, пробел межу ними)
    + Поле «CVC/CVV» оставить пустым
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "CVC/CVV" подсвечивается с ошибкой "Поле обязательно для заполнения")

12) Поле "Владелец" не заполнено
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Поле «Владелец» оставить пустым
    + Заполнить поле «CVC/CVV» (3 цифры)
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "Владелец" подсвечивается с ошибкой "Поле обязательно для заполнения")

13) Поле "Владелец" заполнено на кириллице
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Владелец» (от 1 символа для имени и фамилии на кириллице, пробел межу ними)
    + Заполнить поле «CVC/CVV» (3 цифры)
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "Владелец" подсвечивается с ошибкой "Неверный формат")

14) Поле "Владелец" заполнено пробелами
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Владелец» (несколько пробелов)
    + Заполнить поле «CVC/CVV» (3 цифры)
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "Владелец" подсвечивается с ошибкой "Неверный формат")

15) Поле "Владелец" заполнено спецсимволами
    + Заполнить поле «Номер карты» (16 цифр, одобренная карта в gate-simulator)
    + Заполнить поле «Месяц» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Год» (2 цифры, соответствуют будущей дате, не больше пяти лет от текущей)
    + Заполнить поле «Владелец» (несколько спецсимволов)
    + Заполнить поле «CVC/CVV» (3 цифры)
    + Нажать кнопку «Продолжить» (данные не отправляются, поле "Владелец" подсвечивается с ошибкой "Неверный формат")

   
## Перечень используемых инструментов с обоснованием выбора.
+ IntelliJ IDEA - ведущая IDE для работы с java
+ Java 11 - для написания тестов
+ Gradle - система сборки проектов
+ фреймворк JUnit 5 - наиболее широко используемая среда тестирования для приложений
+ фреймворк Selenide - для управления браузером в тестах
+ библиотека Faker - для генерации пользовательских данных
+ Allure - для генерации отчетов

## Перечень и описание возможных рисков при автоматизации.
+ Отсутствие документации
+ После реализации плана, при изменениях проекта, необходимо актуализировать тесты.

## Интервальная оценка с учётом рисков в часах.
32 часа
+ 3 рабочих дня на написание автотестов
+ 1 рабочий день на прогон тестов и подготовку отчета

## План сдачи работ.
+ Начало работы - 02.05.2023
+ Проведение тестов, подготовка отчета - 05.05.2023
+ Сдача работы - 06.05.2023