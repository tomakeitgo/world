---
layout: post
title: "AWS: Writing Data Down."
date: 2025-03-12 19:24:02 -0700
categories: [ beginnings,aws ]
---

Previously, I said I would need to come up with a cleanup strategy for
the deployment bucket. The time is now. I looked in the Bucket and there were too many things and I didn't like it.

As a bit of refresher, when we deploy the java application a zip file is
created with a timestamp in its name. It is then uploaded to the S3 bucket and the name is threaded through
the rest of the deploy script.

Changes, 
 * The S3 bucket is now a versioned bucket. 
 * The timestamp from the zip file is no more. 
 * The version id of the S3 Object is captured after the upload. 
 * The version is now used along with the bucket and key in the CloudFormation file.  
 * A new Life Cycle policy exists and will delete non-current versions of the artifact.

 I am feeling much happier now that I have stopped littering. I will check back in to make sure
the bucket is being cleaned up. Hopefully, it is configured how I want it be.

I will report back with the results, expected or otherwise.

 -- Will