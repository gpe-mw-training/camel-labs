#!/usr/bin/env bash

HOST=${1:-172.28.128.4}

oc login -u admin -p admin https://$HOST:8443