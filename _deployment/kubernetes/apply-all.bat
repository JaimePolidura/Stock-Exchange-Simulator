kubectl apply -f mysql-deployment-svc.yaml
kubectl apply -f rabbitmq-deployment-svc.yaml
kubectl apply -f redis-deployment-svc.yaml
kubectl apply -f frontend-deployment-svc.yaml
kubectl apply -f clienteventdispatcher-deployment-svc.yaml
kubectl apply -f gateway-deployment-svc.yaml

pause