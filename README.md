# JSavings
### Умный менеджер расходов и доходов с возмоностью использования в качестве валюты обычные валюты, крипто валюты, ценные бумаги и драгоценные металы

1. ### Что готово на данный момент:
 - Экран транзакций
 - Добавление новой транзакции
 - Просмотр информации о транзакции
 - Редактирование транзакции
 - Удаление тразнакции
 - Светлая / темная тема

2. ### Что планируется сделать
 - Доделать весь основной функционал (остальные экраны, иконки и т.д. )
 - Более красивый дизайн
 - Экран просмотра детальной информации о конкретной валюте (курс, рейтинг и т. д.)
 - Экспортирование и импортирование базы данных
 - Интеграция с банками (добавление новых транзакций по api, а не вручную) + оповещение об этом (Обычные банки, крипто кошелеки)
 - Добавление транзакции через скан qr кода чека
 - Напоминания о добавлении транзакции
 - Добавление фотографии к транзакции
 - Возможность поделиться транзакцией и ее фото

3. ### Архитектура - MVVM + Clean
 - Модуль Data. Слой с данными, сущностями (DTO) и репозиториями. Репозитории взаимодействуют с api или бд. Сущности передаются через репозитории, через rx
 - Модуль Domain. Слой с бизнес логикой, включает в себя слой Data. Состоит из UseCase'ов, mapper'ов, моделей и interactor'ов. 
 * Модели - сущности (DTO), представленные в более удобном для ui виде
 * Мапперы конвертируют сущности (DTO) в модели данных и обратно
 * UseCase'ы взаимодействуют с репозиториями для получения данных, прогоняют через определенный маппер и отдают дальше
 * Интеракторы включают в себя юз кейсы и служат для более удобной работы с ними (чтобы не приходилось, когда нужно, инжектить по 100500 юз кейсов, а заинжектить пару интеракторов)
 - Модуль Presentation. Ui слой. Включает в себя Domain модуль. Состоит из фрагментов и вью моделей (а также одного активити)
 Во вью модели через конструктор подаются необходимые юз кейсы / интеракторы. При подписки rx конвертируется в livedata через паттерн состояний (Состояние отправки запроса, Состояние успешного ответа, состояние ошибки)
 Фрагменты подписываются на liveData и отображают данные

4. Используемые библиотеки
 - Room для хранения всех данных пользователя
 - Retrofit для отправления и получения запросов из api
 - Gson для парсинга json
 - Koin для DI
 - RxJava3 для многопоточности
 - Material components
 - LiveData
 - ViewBinding
 - [Material color picker](https://github.com/Pes8/android-material-color-picker-dialog) для быстрого имплементирования функции выбора цвета
 - Firebase (в будущем)

5. Используемые библиотеки для тестирования
 - JUnit 4
 - Mockk
 - Google Truth

6. Источники
 - Уже имеющиеся иконки взяты c [Flaticon](www.flaticon.com)
 - [Material color picker](https://github.com/Pes8/android-material-color-picker-dialog)
 - [Api](https://exchangerate.host/#/)
