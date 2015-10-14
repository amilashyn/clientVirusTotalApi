# clientVirusTotalApi

#From VirusTotal documentation:
#'The chosen format for the VirusTotal Public API v2.0 is HTTP POST requests with JSON object responses and it is limited to at most 4 requests of any nature in any given 1 minute time frame.'
#As a result from this program you able to request only 16 resources for now (I plane to change it:) ).

This is a repository for client VirusTotal public API 2.0 implementation in Scala


For use it:
1. You need for key (was registred in API)
2. Generate jar by Intellij Idea or Sbt-assembly

Examples of using:
- run script in Linux terminal:
script.sh 'keyApi' 'scan|report' url1 url2 url..

script.sh:
#!/bin/sh
java -jar ./projectscala.jar "$@"
