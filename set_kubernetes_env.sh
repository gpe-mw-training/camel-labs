#!/usr/bin/env bash

HOST=${1:-172.28.128.4}
# vagrant.f8

unset DOCKER_CERT_PATH
unset DOCKER_TLS_VERIFY
export DOCKER_HOST=tcp://$HOST:2375
export KUBERNETES_NAMESPACE=default
export KUBERNETES_MASTER=https://$HOST:8443
export KUBERNETES_DOMAIN=vagrant.f8
export KUBERNETES_TRUST_CERT="true"