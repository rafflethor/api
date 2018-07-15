#!/usr/bin/env bash

# DESCRIPTION
# ===========
#
# This script can be used to wipe out data related with the deployment
# serviceaccount and the created namespace

kubectl delete role travis-role --namespace=$K8S_CONTEXT_NAME
kubectl delete rolebinding travis-role-binding --namespace=$K8S_CONTEXT_NAME
kubectl delete serviceaccount $K8S_USERNAME --namespace=$K8S_CONTEXT_NAME
kubectl delete serviceaccount default --namespace=$K8S_CONTEXT_NAME
kubectl delete namespace $K8S_CONTEXT_NAME
