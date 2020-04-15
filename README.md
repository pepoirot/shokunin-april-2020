# Shokunin April 2020

## Overview

The application uses a modified A* search algorithm to find a path from a desk at the back row to the front row:
https://en.wikipedia.org/wiki/A*_search_algorithm

The modifications from the standard A* search algorithm are:
- the search finds a path to any position matching a predicate,
- the search does not cache the intermediate paths and only returns whether a
  path could be found.

## Building

### Prerequisites

- Docker or,
- a JDK +11.

### Building and running the tests

    docker run -it \
        -v "$PWD:/tmp/project" \
        -w /tmp/project \
        gradle:6.3-jdk11 \
        sh -c 'gradle clean build'

or:

    ./gradlew clean build

### Running the application

    docker run -it \
        -v "$PWD:/tmp/project" \
        -w /tmp/project \
        gradle:6.3-jdk11 \
        sh -c 'gradle run'

or:

    ./gradlew run

