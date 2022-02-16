kubectl apply -f mysql-deployment-svc.yaml
kubectl apply -f rabbitmq-deployment-svc.yaml
kubectl apply -f redis-deployment-svc.yaml
kubectl apply -f clienteventdispatcher-deployment-svc.yaml
kubectl apply -f gateway-deployment-svc.yaml
kubectl apply -f ingress.yaml
kubectl apply -f roles.yaml

minikube tunnel