#!/bin/bash

if [ -z "$1" ]
  then exit -1;
fi

TAG=$1;

# Second argument if need to send docker image to remote repository
NEED_PUSH_IMAGE=0;
if ! [[ -z "$2" ]] && [[ "$2" == "1" ]]
  then
    echo "Second argument was set to 1. Docker image should be sent to remote repository.";
    NEED_PUSH_IMAGE=1;
  else
    echo "Second argument not exist. Docker image shouldn't be sent to remote repository and will be available only locally.";
fi

REGISTRY_URL='607425910686.dkr.ecr.eu-central-1.amazonaws.com';
echo 'BUILDING image compressor image';
echo '======================================================';
IMAGE_NAME='acroplia-image-compressor:'$TAG;

docker build . -t $IMAGE_NAME;
ret=$?;
if [ $ret -ne 0 ] ; then
    return $ret;
fi

# Image sending if need
echo '======================================================';
if [[ ${NEED_PUSH_IMAGE} -eq 1 ]] && [[ ${ret} -eq 0 ]]; then
    docker tag ${IMAGE_NAME} ${REGISTRY_URL}/${IMAGE_NAME};
    docker push ${REGISTRY_URL}/${IMAGE_NAME};
fi;

echo '';
echo 'done';
echo '';
