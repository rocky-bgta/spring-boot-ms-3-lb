# Necessary cmd for helm

helm create 'service name'

# To build dependency chart
helm dependencies build

# To see preview of helm chart to any env for example dev-env
helm template .


helm install ers .\dev-env\

helm uninstall ers

#           release name (ers)
helm upgrade ers prod-env
