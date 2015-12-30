#!/usr/bin/env bash

# Set `<vm>true</vm>` in appengine-web.xml

# Switch Java JDK
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

mvn clean validate compile gwt:compile gcloud:stage
cp target/Epvin/WEB-INF/lib/appengine-api-1.0-sdk-1.9.30.jar target/appengine-staging/WEB-INF/lib/
ls target/appengine-staging/WEB-INF/lib/
cd target/appengine-staging

eval "$(docker-machine env default)"
docker build --tag=gcr.io/epvin-loxal/epvin .
gcloud docker push gcr.io/epvin-loxal/epvin

gcloud container clusters create epvin-loxal-dcc \
    --scopes https://www.googleapis.com/auth/userinfo.email,\
https://www.googleapis.com/auth/compute,\
https://www.googleapis.com/auth/cloud.useraccounts,\
https://www.googleapis.com/auth/devstorage.full_control,\
https://www.googleapis.com/auth/taskqueue,\
https://www.googleapis.com/auth/bigquery,\
https://www.googleapis.com/auth/sqlservice.admin,\
https://www.googleapis.com/auth/datastore,\
https://www.googleapis.com/auth/logging.admin,\
https://www.googleapis.com/auth/monitoring,\
https://www.googleapis.com/auth/cloud-platform,\
https://www.googleapis.com/auth/bigtable.data,\
https://www.googleapis.com/auth/bigtable.admin,\
https://www.googleapis.com/auth/pubsub,\
https://www.googleapis.com/auth/logging.write\
    --num-nodes 3 \
    --machine-type g1-small

gcloud container clusters get-credentials epvin-loxal-dcc
kubectl run epvin --image=gcr.io/epvin-loxal/epvin
kubectl expose rc epvin --type=LoadBalancer --port=80 --target-port=8080
kubectl get svc; kubectl get rc; kubectl get po
