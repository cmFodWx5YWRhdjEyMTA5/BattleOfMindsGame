# BattleOfMindsGame
Проект для обучения в OTUS.
Игра в стиле "Битва Умов", где игроки сорвенуются в правильных ответах на вопросы.
# ДЗ №2. Кастомный бесконечный progressBar

[CustomLoadingValueAnimator.kt](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/custom_views/CustomLoadingValueAnimator.kt)

![](dz2example.gif)

# ДЗ №3. Работа с сетью
При первой загрузке подгружаем картинки с Google Cloud Storage.
Скачиваем через [Retrofit](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/web/ImagesUrlApi.kt) список картинок, затем через [Picasso](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/di/module/PicassoModule.kt) подгружаем картинки в постоянную память. Используем архитектуру MVVM: [View - LoadingAssetsFragmnet.kt ](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/loading_assets/LoadingAssetsFragment.kt), [ViewModel - LoadingAssetsViewModel.kt](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/loading_assets/LoadingAssetsViewModel.kt), [Model - LoadingAssetsModel.kt](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/loading_assets/LoadingAssetsModel.kt)

![](dz3example.gif)

# ДЗ №4. Proguard rules.
POJO классы [CloudStorageItem и Item](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/web/CloudStorageItem.kt) после обфускация выдают ошибку:

```
2020-02-28 23:48:59.916 18907-18907/? W/System.err: java.lang.NullPointerException: throw with null exception
2020-02-28 23:48:59.916 18907-18907/? W/System.err:     at c.a.a.c.b.a(:1)
```
Классы добавлены в [proguard-rules.pro](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/proguard-rules.pro):
```
-keepclassmembers class com.bonusgaming.battleofmindskotlin.web.CloudStorageItem { <fields>; }
-keepclassmembers class com.bonusgaming.battleofmindskotlin.web.Item { <fields>; }
```

# ДЗ №5. Реализация MVVM архитектуры.
Создание аватара отображается во фрагементе [(CreatingAvatarFragment.kt)](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/creating_avatar/CreatingAvatarFragment.kt), который взаимодействует с [CreatingAvatarViewModel.kt](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/creating_avatar/CreatingAvatarViewModel.kt), а последний взаимодействует с [CreatingAvatarModel.kt](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/creating_avatar/CreatingAvatarModel.kt)


# ДЗ №6. Clean Architecture.
Разбил на Presentation, Domain и Data слои фичу-экран главное меню [Main Menu Feature](https://github.com/bonusdev/BattleOfMindsGame/tree/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/main)

![](dz6example.png)

# ДЗ №7. Dagger2.
На каждый фрагмент создаю по Subcomponent(в каждом фрагменте создаю собственную фабирку ViewModel`ей [ViewModelFactory](https://github.com/bonusdev/BattleOfMindsGame/blob/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/ViewModelFactory.kt)), выполняю inject() в Activity/Fragment, а далее используется constructor injection.
[com.bonusgaming.battleofmindskotlin.di](https://github.com/bonusdev/BattleOfMindsGame/tree/master/app/src/main/java/com/bonusgaming/battleofmindskotlin/di)

![](dz7example.png)

# ДЗ №8. Multimoduling.
Разбил на модули:
core:main - то, от чего зависят все и никогда не меняется

* base:web_api - модуль интерфейс, от которого зависят компоненты, которым нужен интернет, причем имплементации можно делать разные(от которых никто зависеть не будет)

* base:web_impl - имплементация base:web_api, от которой никто не зависит

* base:db_api - модуль интерфейс, от которого зависят компоненты, которым нужна база данных, причем имплементации можно делать разные(от которых никто зависеть не будет)

* base:db_impl - имплементация base:db_api, от которой никто не зависит

* base:ui - ресурсы приложения

* features:logo - фича привтствие

* features:loading -  фича загрузка контента

* features:creating_avatar -  фича создание игрока

* features:menu - фича главное меню

[Код в ветке multimodule](https://github.com/bonusdev/BattleOfMindsGame/tree/multimodule)

![](dz8example.png)


