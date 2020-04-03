#!/bin/bash
export VM_IP=127.0.0.1
export COMPOSE_CONVERT_WINDOWS_PATHS=1

minikube start --vm-driver hyperv --memory=2000 --disk-size=5000
