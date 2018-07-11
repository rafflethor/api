#!/usr/bin/env bash
set -o pipefail
set -o errexit
set -o nounset
# set -o xtrace

echo "Installing kubectl"

wget -O kubectl https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/darwin/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl

echo $CA_CRT | base64 --decode -i > ${HOME}/ca.crt

kubectl config set-cluster our-k8s-cluster --embed-certs=true --server=${CLUSTER_ENDPOINT} --certificate-authority=${HOME}/ca.crt
kubectl config set-credentials travis-echo --token=$SA_TOKEN
kubectl config set-context travis --cluster=$CLUSTER_NAME --user=travis-echo --namespace=rafflethor
kubectl config use-context travis
kubectl config current-context

kubectl get nodes

#kubectl apply -f ./k8s/deployment.yml
# kubectl apply -f ./k8s/service.yml
# kubectl apply -f ./k8s/ingress.yml

function cleanup {
    printf "Cleaning up...\n"
    rm -vf "${HOME}/ca.crt"
    printf "Cleaning done."
}

trap cleanup EXIT
