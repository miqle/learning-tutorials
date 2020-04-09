
```
docker run --name docker-nginx -p 8080:80 nginx

export COMPOSE_CONVERT_WINDOWS_PATHS=1
docker run --name docker-nginx -p 8080:80 -d nginx:1.17.4-alpine

docker exec -it server_api_1 sh

docker rm --name docker-nginx

```