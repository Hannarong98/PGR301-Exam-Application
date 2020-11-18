#!/bin/bash
if [ "$TRAVIS_BRANCH" == "master" ]; then
  set -ex
  docker build -t "${IMAGE}:${TRAVIS_COMMIT}" . &&
    docker push "${IMAGE}:${TRAVIS_COMMIT}"
  set +x
fi
