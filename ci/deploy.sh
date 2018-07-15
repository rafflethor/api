#!/usr/bin/env bash

set -o pipefail
set -o errexit
set -o nounset

# DESCRIPTION
# ===========
#
# This script can be used to apply a deployment to k8s using any
# continuous integration environment.
#
# - K8S_CA_CRT: Host certificate
# - K8S_CLUSTER_NAME: Cluster name
# - K8S_HOST: Address name of the K8s cluster
# - K8S_CONTEXT_NAME: Namespace and context name
# - K8S_USERNAME: ServiceAccount name
# - K8S_USER_TOKEN: ServiceAccount token
#
# Once the credentials have been set then we can apply the deployment,
# pods, services...etc yaml files
#
# Finally the scripts deletes the server certificate

echo $K8S_CA_CRT | base64 --decode -i > ${HOME}/ca.crt
K8S_USER_TOKEN=$(echo $K8S_USER_TOKEN | base64 -d)

# SET-CREDENTIALS
kubectl config set-cluster ${K8S_CLUSTER_NAME} --embed-certs=true --server=${K8S_HOST} --certificate-authority=${HOME}/ca.crt
kubectl config set-credentials ${K8S_USERNAME} --token=${K8S_USER_TOKEN}
kubectl config set-context ${K8S_CONTEXT_NAME} --cluster=${K8S_CLUSTER_NAME} --user=${K8S_USERNAME}
kubectl config use-context ${K8S_CONTEXT_NAME}

# DEPLOYMENT
kubectl get deployments --namespace=$K8S_CONTEXT_NAME

# CLEANING-UP
function cleanup {
    printf "Cleaning up...\n"
    rm -vf "${HOME}/ca.crt"
    printf "Cleaning done."
}

trap cleanup EXIT
