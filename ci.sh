#!/usr/bin/env bash

# DESCRIPTION
# ===========
#
# This script can be used to apply a deployment to k8s using any
# continuous integration environment.
#
# - K8S_CLUSTER_NAME: Cluster name
# - K8S_HOST: Address name of the K8s cluster
# - K8S_CA_CRT: Host certificate
# - K8S_CONTEXT_NAME: Namespace and context name
# - K8S_USERNAME: ServiceAccount name
# - K8S_USER_TOKEN: ServiceAccount token
#
# Once the credentials have been set then we can apply the deployment,
# pods, services...etc yaml files
#
# Finally the scripts deletes the server certificate

set -o pipefail
set -o errexit
# set -o nounset

# VARIABLES
# =========

echo $K8S_CA_CRT | base64 --decode -i > ${HOME}/ca.crt
K8S_USER_TOKEN=$(echo $K8S_USER_TOKEN | base64 -d)

# Docker variables to deploy Docker to a Docker repo
DOCKER_VERSION="1.0.12"
DOCKER_IMAGE="rafflethor-docker-rafflethor.bintray.io/api:${DOCKER_VERSION}"
BINTRAY_REPO="rafflethor-docker-rafflethor.bintray.io"

# FUNCTIONS
# =========

# Usage
function usage {
    echo "./ci.sh build|docker|udpate"
}

# Removes the certificate from ci machine
function cleanup {
    printf "Cleaning up...\n"
    rm -vf "${HOME}/ca.crt"
    printf "Cleaning done."
}

# Sets the required credentials to deploy this
# app to k8s
function set_credentials {
    # SET-CREDENTIALS
    kubectl config set-cluster ${K8S_CLUSTER_NAME} --embed-certs=true --server=${K8S_HOST} --certificate-authority=${HOME}/ca.crt
    kubectl config set-credentials ${K8S_USERNAME} --token=${K8S_USER_TOKEN}
    kubectl config set-context ${K8S_CONTEXT_NAME} --cluster=${K8S_CLUSTER_NAME} --user=${K8S_USERNAME}
    kubectl config use-context ${K8S_CONTEXT_NAME}
}

# builds only the code passing tests
function build {
    ./gradlew clean build
}

# builds the code and pushes docker image to bintray
function build_image {
    ./gradlew \
        build \
        publishApiImage \
        -PdockerRegistryUsername=$DOCKER_REGISTRY_USERNAME \
        -PdockerRegistryPassword=$DOCKER_REGISTRY_PASSWORD
}

# updates k8s deployment image
function update {
    # Updates the current deployment with the next avaiable Docker image
    set_credentials

    kubectl set image deployment/api -n $K8S_CONTEXT_NAME api=$DOCKER_IMAGE

    trap cleanup EXIT
}

# EXECUTION FLOW
# ==============

# Depending on script's first parameter
if (( $# > 0 )); then
    case $1 in
        build)
            build
            ;;
        docker)
            build_image
            ;;
        update)
            update
            ;;
        *)
            usage
            ;;
    esac
else
    usage
fi
