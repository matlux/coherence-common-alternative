# coherence-common - An alternative

This is a fork of Oracle Coherence's coherence-common version 2.1.2.31472 which is part of The Coherence Incubator (Release 10). See  http://coherence.oracle.com/display/INC10/coherence-common for more details. This repository is not endorsed by Oracle. It is only a way for users of Coherence to experiment and extend the work done by Oracle to suggest possible evolutions of the Incubator base code.

## Install

   Add coherence.jar into a local nexus repo with group-id=com.oracle, artifact-id=coherence, version=3.7.1
   then run the following commands:
    git clone git://github.com/matlux/coherence-common-alternative.git
    cd coherence-common-alternative
    mvn clean install
    
   Use the jar file inside the target directory as a dependency of your project.

## Added feature so far:

   Project was "Mavenized" although the original Ivy and additional Ant scripts are still there. 

   Added in-process Runtime from JK that can be used for testing. See http://thegridman.com/coherence/coherence-incubator-commons-runtime-package/ for more info.
   
   