kubectl delete -f mysql-deployment-svc.yaml
kubectl delete -f rabbitmq-deployment-svc.yaml
kubectl delete -f redis-deployment-svc.yaml
kubectl delete -f clienteventdispatcher-deployment-svc.yaml
kubectl delete -f gateway-deployment-svc.yaml
kubectl delete -f ingress.yaml

pause