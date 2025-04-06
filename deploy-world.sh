#!/bin/zsh
bucket='deployment-deploymentbucket-fmgact3vwzux'

echo "gradlew clean build buildZip -p world"
/bin/bash gradlew clean build buildZip -p world

echo "Put Object to ${bucket}"
versionId=$(aws s3api put-object \
  --bucket "${bucket}" \
  --key "world.zip" \
  --body "world/build/distributions/world.zip" \
  --query "VersionId" \
  --output text \
  --region us-west-2 \
  )

echo "${versionId}"

aws cloudformation deploy \
  --template-file world-data.yml \
  --stack-name world-data-prod \
  --region us-west-2

aws cloudformation deploy \
  --template-file world.yml \
  --stack-name world \
  --region us-west-2 \
  --capabilities CAPABILITY_IAM \
  --parameter "VersionId=${versionId}"