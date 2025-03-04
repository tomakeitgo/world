---
layout: post
title: "AWS: Hello Lambda, Hello API Gateway"
date: 2025-03-03 20:12:01 -0700
categories: [ beginnings,aws ]
---

From the last time, I listed "Do something with API Gateway".
I have now done something with API Gateway. I have successfully configured
an API Gateway and Lambda to serve a String constant. Added Bonus, I also got the DNS
and certificate configured to provide content from a custom domain (api.tomakeitgo.com)!

I have picked Java as my language of choice for this adventure. It might be known as discount C# with worse
documentation, but I like it. I am using Gradle in the kotlin dialect as the build tool for Java. Getting the zip 
file the Java Lambda wants to consume was fairly straight forward. The AWS documentation was for the Groovy dialect of
Gradle. Translation was straight forward. The structure of the zip file is put all the JARs in a folder called lib. 
Easy enough to do. 

``` kotlin
tasks.register<Zip>("buildZip") {
    archiveFileName.set("world_1.zip")
    into("lib") {
        from(tasks.jar)
        from(configurations.runtimeClasspath)
    }
}
```

### API Gateway
To configure an API Gateway there a few parts. The API (AWS::ApiGatewayV2::Api) the
top level container. It needs wants to have a name and to know what type of api it is. HTTP 
or websockets. This go around I picked HTTP. Maybe building a site driven entirely with websockets 
would be interesting, maybe later.

Next up is a Stage (AWS::ApiGatewayV2::Stage). This looks to be a container for having different versions
of an api. I went with only one and having it auto deploy. I don;t have a current need to different stages or 
complex deployment schemes.

The last two items needed are an integration (AWS::ApiGatewayV2::Integration) and a route (AWS::ApiGatewayV2::Route).
The integration provides the linkage to the thing that does the work. This time it was a lambda but it looks like it can 
be a handful of different things. The route is the method and partial url that is called. I will need an integration and a route
for each new call supported.

Current ~~Problems~~ Opportunities
 * Create a deployment script.
   * Build the project
   * Upload the jar file
   * deploy the change
* Versioning the JAR file - Just uploading the jar doesn't seem to update the lambda with the new code immediately.
* Pick something to build. Wiring random AWS service is fun and all but a goal it is not.
