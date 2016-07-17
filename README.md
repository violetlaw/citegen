# citegen

Legal citation generator library

## Building / developing citegen

At present, citegen is buildable on Unix systems (Linux, OSX).

### Prerequisites

#### Install Maven

Maven is used to build citegen.

##### Debian/Ubuntu 

`sudo apt-get install maven`

##### Other Unixes, OSX

See [Maven installation instructions](https://maven.apache.org/install.html)
(or use the appropriate packaging/installation system for your platform
-- MacPorts, Homebrew, Fink, etc).

#### Install `protoc`

The input to citegen is defined by language/platform-independent
[protocol buffer messages](https://en.wikipedia.org/wiki/Protocol_Buffers).

[Install the protocol buffer compiler]
(https://github.com/google/protobuf#protocol-compiler-installation).

The `protoc` executable must be on your
[$PATH](https://en.wikipedia.org/wiki/PATH_(variable)).

<details>

<summary>
Adding `protoc` to path.
</summary>

This is usually accomplished by adding the following line to your
`~/.bashrc` file (`~/.bash_profile` for OSX).

`export PATH=$PATH:path/to/protoc_dir`

where `path/to/protoc_dir` is the path to the directory containing
`protoc` executable.

You will need to run `source .bashrc` (or `.bash_profile`) after editing
to pick up the changes.

When working correctly, typing `protoc` at the comand line should
run the protocol buffer compiler.

</details>

### Building

After [cloning the project locally]
(https://help.github.com/articles/fetching-a-remote/#clone),
run the following command from the root directory of the project:

`mvn package`
