---
layout: post
title: "AWS: Words Words Words"
date: 2025-02-13 20:24:00 -0700
categories: [beginnings,aws]
---

I have recovered my AWS acccount, made some choices, and writen some scripts.

I have retrieved my aws account from one of many aborted attempts at figuring out what in the world is an AWS. Past me setup an organization got the majority of the way to having multiple accounts setup. I have finished the setup. I can now login with a nonroot user. Don't worry I promptly made second me an admin as well. I will accept that I shouldn't run as root, but I still need to be able to change everything. I have also made a second account for my blog and maybe other workloads. I hear good things about keeping my management account separate from my workload accounts.

The choices I have made so far are to attempt to get as far as possible with AWS CLI and CloudFormation yaml files. The Cloud Development Kit (CDK) seemed like it might be a trap or atleast too much for me to process at the moment. I have mixed feeling about using a general purpose languageg to define resources and their relationships to each other. I suspect I would just end up building a DSL in the host language and that would put me back at an equivalent to a yaml file with all of the host language's baggage. I don't really see this as quick way to get to the goal.

I say scripts but it is really a script. I have put three aws cli commands in a shell script. I ask cloud formation to deploy my stack with a the yaml definition file. Then I ask the stack for the s3 bucket name. With the those two things done I copy flat files into the bucket!

Next up on the todo list.
 * CloudFront: Serve the contents of the S3 Bucket
 * Version Control: Put this in a Git Repo somewhere. This maybe should have been first.

### Words Words Words

S3 Bucket: Place to store files. It can be configured to have the files available publicly on the internet. This is considered harmful by Amazon, Mabye... they seem conflicted

CloudFormation: A service that eats JSON or YAML configuration files and poops Amazon Resources. May be harmful to my wallet.

AWS CLI: A set of programs bundled into one big one that lets one type at the problem instead of click at it. May make things rerunable.

AWS CDK: A different program that seems to build the files that CloudFormation wants but requires a general purpose language to define the contents of the CloudFormation configuration files. May be helpful... May be harmful.

CloudFront: AWS's Content Delivery Network. Allows content form an S3 bucket to be served from a nice URL with HTTPS. Also faster to serve the content?
