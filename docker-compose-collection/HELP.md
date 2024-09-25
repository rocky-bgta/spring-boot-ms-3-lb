One-Liner to Remove Containers, Images, and Volumes
If you want to remove all stopped containers, unused images, and dangling volumes, you can use this one-liner:

docker system prune --volumes

docker-compose up --build

# To open docker container terminal:
docker exec -it container-name /bin/sh

# Grafana dash-bord url
http://localhost:3000/

# Prometheus dash-bord url
http://localhost:9090/targets?search=


# To give pass env variable while docker container :
docker run --name gateway-service -p 8700:8700 -e EUREKA_DEFAULT_ZONE_URL=http://192.168.1.100:8761/eureka salehinrocky/gateway-service:V1

# To pass multiple path variable:
docker run --name gateway-service -p 8700:8700 \
-e EUREKA_DEFAULT_ZONE_URL=http://192.168.1.100:8761/eureka \
-e ANOTHER_ENV_VAR=value \
salehinrocky/gateway-service:V1


Remove All Stopped Containers
docker container prune


To get kubernetes token from windows:
For windows:

kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}" | % { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
