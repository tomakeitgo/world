#!/bin/zsh

echo 'Run cloud formation deploy. It should not update if there are no changes or make the changes'
aws cloudformation deploy --stack-name blog --region us-west-2 --template-file blog.yml

if [ $? -ne 0 ] 
then
    echo 'Something Bad happened!'
    exit 1
fi

echo 'Find the bucket name of the blog. We will need this to put the files into'
bucket=$(aws cloudformation describe-stacks \
	 --region us-west-2 \
	 --stack-name blog  \
	 --query "Stacks[0].Outputs[?OutputKey=='BlogBucketRef'].OutputValue" \
	 --output text)
distributionId=$(aws cloudformation describe-stacks \
	 --region us-west-2 \
	 --stack-name blog  \
	 --query "Stacks[0].Outputs[?OutputKey=='DistributionId'].OutputValue" \
	 --output text)

echo "Your bucket name is $bucket isn't that neat."
aws s3 sync blog/_site "s3://${bucket}"

aws cloudfront create-invalidation --distribution-id "${distributionId}" --paths "/*"