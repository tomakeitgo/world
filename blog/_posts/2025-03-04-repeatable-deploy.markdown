---
layout: post
title: "AWS: Repeatable Deploys"
date: 2025-03-04 20:08:35 -0700
categories: [ beginnings,aws ]
---
### Last Time
On my last adventure, I got a Lambda wired to an API Gateway. Missing was a
repeatable way of deploying changes. The pieces were all in place, but nothing was
scripted. Actions performed by hand were:

1. Build the project to get a zip file.
2. Upload the zip file to S3 with a new name.
3. Update the CloudFormation yaml file with the new name for the lambda.
4. Run the aws cloudformation deploy command.

### Today's Adventure!
Today's work was getting the above steps codified into a shell script. First thing I did
was to modify the build file for the project to accept a parameter of the build version.
I picked the current date formatted as yyyyMMddHHmmss (world_20250304183204.zip). This versioning scheme will break when
I start deploying multiple times a second or rapidly changing the timezone of my build machine.
Both are somewhat unlikely to happen.

The next step was to create a shell script. It gets the bucket from the stack using the aws cli, Records the current
time,
builds the project providing the timestamp, uploads the project zip file, and finally runs a cloudformation deploy.
I have yet to add error handling for when one of these steps fails, but I am feeling better that
I have a starting point to be able to iterate on and improve.

### Items to work on next
Figure out a cleanup strategy for the zip files. They will sort of build up forever
or until my wallet runs out. I want some number of previous version to allow a rollback but not all of them.

Figure out a next step for something to build. At the moment I am leaning toward stealing form Ron Jefferies
[Biot World](https://ronjeffries.com/articles/-x024/biot/-bz00/b0/).

Figure out a simple authorization scheme. If I start collecting input, I don't want just anyone to be able to change or
add to it.

