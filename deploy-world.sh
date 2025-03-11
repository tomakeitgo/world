#!/bin/zsh
batchTime=$(date +'%Y%m%d%H%M%S')
bucket=$(aws cloudformation describe-stacks \
	 --region us-west-2 \
	 --stack-name world  \
	 --query "Stacks[0].Outputs[?OutputKey=='DeploymentBucketRef'].OutputValue" \
	 --output text \
	 )

echo gradlew clean build buildZip -p world -P build-version="${batchTime}"
/bin/bash gradlew clean build buildZip -p world -P build-version="${batchTime}"

aws s3 cp "world/build/distributions/world_${batchTime}.zip" "s3://${bucket}/world_${batchTime}.zip"

aws cloudformation deploy \
  --template-file world-data.yml \
  --stack-name world-data-prod \
  --region us-west-2

aws cloudformation deploy \
  --template-file world.yml \
  --stack-name world \
  --region us-west-2 \
  --capabilities CAPABILITY_IAM \
  --parameter "BuildVersion=${batchTime}"