# kafka-long-running-jobs

This repo contains examples of dealing with long-running jobs using Spring and Apache Kafka. Its purpose purely informative to give developers an idea of the different approaches to take.

The project has 4 submodules, each independent of one another so that all information needed can be found by looking at a single module. Note that this does mean that there is duplicate code across modules, however as its purpose is to show how to tackle the problem I found it easier if you only had to look in a single module instead of navigating all over the repo.

All the submodules have (mostly) the same classes:
- Application: main class to start the module
- Consumer: the kafka consumer that consumes the event and starts the long-running job
- Controller: contains an endpoint to call which produces an event (which is consumed by the consumer)
   - A postman collection (kafka-long-running-jobs.postman_collection.json) has been added with all endpoint calls
- LongRunningJob: a simulation of a long-running job, performs a thread.sleep of 10 minutes 

Furthermore, the configurations needed for the different approaches are in the resources/application.yaml

Below you can find a summary of the 4 submodules. For a more detailed explanation on the issue and all of the solution I would like to refer to my blog on the subject: TODO

## Module: not-async
This module has nothing async to it. It exists for the sake of showing what the default behaviour is (spoiler alert: the consumer leaves the group)

## Module: spring-async
The most straightforward approach, add an @Async annotation to the long-running job so that it will be executed on a separate thread.

- Pro: easy to set up, if resources (CPU usage/memory) aren't an issue than this approach works well since it will handle multiple events concurrently
- Con: if the long-running job is resource intensive the concurrency can become a bottleneck (too much CPU usage, OOM issues etc.). Also, since it's a fire-and-forget approach, retries and kafka error handlers require more work

## Module: pause-container
Uses the KafkaListenerEndpointRegistry to get the listener container (spring container that contains the consumer) to pause and resume the consumer.

- Pro: more control over success and error callbacks and thus error handling
- Con: requires more effort than the spring-async approach.

## Module: pause-container-with-acknowledge
Same as the previous one except that it has auto-commit disabled and uses the Acknowledge object to acknowledge the event (and commit).

- Pro: More control, and the ability to not acknowledge events when an error occurs
- Con: More effort than the two approaches above, also don't forget to acknowledge

