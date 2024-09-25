https://docs.docker.com/desktop/kubernetes/
https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md

kubectl apply -f dashboard-adminuser.yml

kubectl apply -f secret.yml

# For mac and ubuntu
kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath={".data.token"} | base64 -d


# get long live toke from secret in windows:
$base64Token = kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}"
$base64Token = $base64Token -replace "`n", "" -replace "`r", "" -replace " ", ""
[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($base64Token))


or
[System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String((kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath="{.data.token}" | % {$_ -replace "`n|`r| ", ""})))
