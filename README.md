# Сервис управления задачами
## Инструкция к запуску

1. Установите Docker Desktop на свой ПК (для daemon-а) https://docs.docker.com/desktop/setup/install/windows-install/
2. Установите и запустите `git bash`
3. Клонируйте репозиторий в желаемую папку

```shell
cd <папка, где будет находится проект>
git clone https://github.com/duahifnv/task-management-api.git
cd task-management-api
```

4. Проверьте, что docker daemon запущен

```shell
docker --version
```

5. Запустите скрипт для локального деплоя приложения

```shell
sh run.sh
```

>Запуск займет некоторое время, т.к. будут подгружаться необходимые образы. При успешном запуске откроется страница с Swagger UI.

