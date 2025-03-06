---
layout: post
title: "AWS: API Gateway Authorizer"
date: 2025-03-05 20:54:09 -0700
categories: [ beginnings,aws ]
---

I have added a layer of "security" to my hello world lambda. Now to get that
awesome string constant out of the API Gateway a header called "magic-token"
must be present. This isn't adding much in the way of protection but it did get me
more knowledge of how to wire an Authorizer to a route.

### The Relationships

An Authorizer is defined with a reference to both the API Gateway and the Lambda or other source.
The additional properties that got me were AuthorizerResultTtlInSeconds required IdentitySource to be
specified because the cache wants a way to determine if the request is the "same".

The Route is required to specify the Authorizer and its type. This makes sense to some routes need to be public and
others need to be secured. For example securing a Login route likely needs to be public and a password change likely
wants to require authorization.

The lambda definition looks identical to the other definition except for the resource policy. The policy
differed by the source arn. The invocation is coming from a different thing in the API Gateway.
