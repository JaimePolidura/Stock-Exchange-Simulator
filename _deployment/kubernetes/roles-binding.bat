kubectl delete clusterroles pod-creator
kubectl create rolebinding default-view --clusterrole=pod-creator --serviceaccount=default:default --namespace=default
pause