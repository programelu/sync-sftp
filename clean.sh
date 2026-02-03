#!/bin/bash

set -e

echo "Stopping all running containers..."
docker stop $(docker ps -q) 2>/dev/null || true

echo "Removing all containers..."
docker rm $(docker ps -aq) 2>/dev/null || true

echo "Removing all images..."
docker rmi -f $(docker images -q) 2>/dev/null || true

echo "Docker cleanup complete ✅"

