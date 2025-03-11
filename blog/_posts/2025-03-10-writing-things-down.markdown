---
layout: post
title: "AWS: Writing Data Down."
date: 2025-03-10 16:45:02 -0700
categories: [ beginnings,aws ]
---

With some level of safety provided by my authorizer, I have started the process of allowing data to be stored. The data
is simulator themed. I am going to start the process of building some sort of spaceship themed simulator.
The idea is to be able to move a ship around in three-dimensional integer space (x,y,z). As the project
continues resource management will be added. Some sort of fuel equivalent, run out and stop running.
I can probably keep adding things to a space simulator until I get to Dwarf Fortress levels of complexity.
We will see how far I go.

### DynamoDB

I have created another yaml file (world-data.yml)! This one contains all of my table definitions. I want to be able to
have a separate
data storage stack for running tests against. Having the data layer in its own stack made this as easy as running the
deploy command with another stack. It did introduce the oddity of the code being unable to refer to the table it
operates with a hardcoded name. I pull the names from environment variables in the
lambda handler in the "production" system. In the tests I scrape them out of the test data stack definition.

Tables I have created so far are as follows:

* Player(Id: UUID, name: VARCHAR)
* Ship(Id: UUID, PlayerId UUID)
* ShipLocation(LocationId: Coordinate, ShipId: UUID)

The Coordinate is really a VARCHAR that looks like x:y:z, but I can pretend that it is a type. Building the tables in
CloudFormation was pretty straight forward. Allowing imports between files was also straight forward. Marking an
output with an export name allows the output to be imported in another file. It seemed a bit weird that exported names
are scoped to the AWS region. I am prefacing the variables polluting this namespace with the stack name.
AWS appears to want me to manage the blackboard they provided with prefixes. I didn't invent this idea it was in one the
AWS
examples.

```yaml
# Example Export of an Arn. this lives in world-data.yml
ShipLocationTableArn:
  Value: !GetAtt ShipLocationTable.Arn
  Export:
    Name:
      'Fn::Sub': '${AWS::StackName}-ShipLocationTableArn'
```

```yaml
# Example usage an import. This lives in world.yml 
Resource:
  - !ImportValue 'world-data-prod-ShipLocationTableArn'
```

I have been able to get the "create player" functionality in place.
When a player is created a ship is also created at (0,0,0) and assigned to the new player.
Next up is to allow the player to move her ship around the world. I think I am going to be mean and
keep the world coordinates secret and force movement to be relative to the ship being moved. Move or jump to (2,3,5)
will mean jump the ship 2 units in the x direction, 3 units in the y direction, and 5 units in the z direction.

I will likely need some limiters for allowed distances and how often a ship can move. Hopefully we can hide the int 32
sized coordinates for a good while. 

Until the next time, 

-- Will