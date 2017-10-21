Project from Luxoft Spring Cloud training.

1. Configure ConfigServer: specify files location
2. Copy `config_files` to specific location and add git versioning
3. Run RabbitMQ with docker: `docker run --rm -p 15672:15672 -p 5672:5672 --hostname my-rabbit --name some-rabbit rabbitmq:3-management`
4. Use `spring_cloud.postman_collection.json` for testing purposes