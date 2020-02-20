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


