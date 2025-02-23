---
layout: post
title: "AWS: DNS and Version Control"
date: 2025-02-14 15:50:00 -0700
categories: [ beginnings,aws ]
---

From the last article, I wanted to get Version Control for my work so far and to get the contents of the S3 bucket
served through CloudFront. Both of these items have been
accomplished. [Git Repository](https://github.com/tomakeitgo/world) is available on GitHub. I have made it a public
repository. I am fairly confident that I can keep secrets out of it. CloudFront is now serving the S3 bucket with a nice
url: [ToMakeItGo](https://blog.tomakeitgo.com)!

Things of note for this portion of the adventure. I resorted to using the management portal for handling the Certificate
and DNS configurations.
I don't currently have my domain registered with Amazon. I didn't get the key generation scripted out. This has lead to
one weird line where I have to reference the key in stack file by its ARN (Amazon Resource Name, Identifier to identify
an amazon resource).

```
        ViewerCertificate:
          SslSupportMethod: sni-only
          AcmCertificateArn: !Join
            - ''
            - - 'arn:aws:acm:us-east-1:'
              - !Ref AWS::AccountId
              - ':certificate/36c80ae0-8618-4221-a636-039990e65e0a'
```

I had to use that !Join list of lists syntax to get access to the key defined outside of the stack file. I was able to
use a variable, so I don't have my account id in version controlled file. I don't think I am leaking
too much information here. us-east-1 is the only supported region for an
ACM Certificate that is allowed in this usage. The GUID without the account is unlikely to be
useful.

These macros are a bit odd for me to see, but I think I am
still on board with this route for configuration. The few I have used have been text replaces. I don't think there is
any jumps or iterations to make things hard to follow.

Short term I would like to get the build of Jekyll blog more automated and fill in my README.md file.

I have yet to figure out what the next step in this journey of AWS should be. Likely it will involve some sort of API
Gateway. A simple call and response call like an echo might be a good spot to start.