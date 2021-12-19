cd gateway
docker build -t sxs-gateway .

cd ..\client-frontend
docker build -t sxs-client-frontend .

cd ..\client-order-event-dispatcher
docker build -t sxs-client-order-event-dispatcher .

cd ..\exchange
docker build -t sxs-exchange .

cd..

pause