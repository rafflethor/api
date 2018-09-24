#!/usr/bin/env bash

case $1 in
    deploy)
        kubectl apply -f api.yml
        ;;
    delete)
        kubectl delete ingress/api -n rafflethor
        kubectl delete service/api -n rafflethor
        kubectl delete deploy/api -n rafflethor
        ;;
    *)
        echo "deploy/delete"
        ;;
esac
