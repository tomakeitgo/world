---
layout: post
title: "AWS: Cache Invalidation!!!"
date: 2025-02-19 18:43:00 -0700
categories: [ beginnings,aws ]
---

After finishing typing the last article and running the deployment script, I discovered a caching problem.
I need to invalidate the CloudFront cache or the main page of the blog serves the old content.

To get the content serving after a deployment. I have done two things. I adjusted the CloudFormation Template to export the
CloudFront Distribution Id. I also adjusted the deployment script to create a cache invalidation. I was a bit bold and
picked invalidating the entire cache. This is likely not ideal in the long run. I will adjust this strategy when readers start
to complain about longer page load times after new blog entries are posted.

Notes to make the deployment script better. Figure out how to assign multiple variables from the describe-stack
command. Currently, two calls are being made to the same endpoint and filtering the data
differently to get the S3 bucket name and the distribution id. It would be preferable to make one call and extract the two
different pieces of data.