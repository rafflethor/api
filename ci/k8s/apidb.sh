#!/usr/bin/env bash

case $1 in
    deploy)
        kubectl apply -f apidb.yml
        ;;
    delete)
        kubectl delete services/apidb -n rafflethor
        kubectl delete deploy/apidb -n rafflethor
        ;;
    *)
        echo "deploy/delete"
        ;;
esac
