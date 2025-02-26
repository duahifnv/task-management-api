#!bin/sh

docker-compose up -d

echo "Запуск приложения..."
while ! curl --fail --silent --head http://localhost:9000/api/v1; do
  sleep 1
done
echo "Приложение запущено!"

start http://localhost:9000/api/v1/swagger-ui