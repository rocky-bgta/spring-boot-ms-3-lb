https://docs.docker.com/desktop/kubernetes/
https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md
# First install kubernetes from docker UI
# Second install helm (for windows use chocolates)

# To run kubernetes UI execute the following cmd at terminal:

    # Add kubernetes-dashboard repository
        helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard/
    # Deploy a Helm Release named "kubernetes-dashboard" using the kubernetes-dashboard chart
        helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --create-namespace --namespace kubernetes-dashboard
        kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
## 

kubectl apply -f dashboard-adminuser.yml

kubectl apply -f secret.yml

# get long live toke from secret in windows:
$base64Token = kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}"
$base64Token = $base64Token -replace "`n", "" -replace "`r", "" -replace " ", ""
[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($base64Token))

or
[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String((kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}" | % {$_ -replace "`n|`r| ", ""})))


# For mac and ubuntu
kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath={".data.token"} | base64 -d

# Copy the token then now go to:
https://localhost:8443/#/workloads?namespace=default

*** Keep the terminal open ***

kubectl get deployments
kubectl get replicaset
kubectl get deployments


# See deployment sample from 
https://kubernetes.io/docs/concepts/workloads/controllers/deployment/

# See configmap documentation
https://kubernetes.io/docs/concepts/configuration/configmap/

# To deploy a service kubernetes
kubectl apply -f "yml-file-name"
