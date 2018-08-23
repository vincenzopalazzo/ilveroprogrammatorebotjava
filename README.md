![Build Status](https://travis-ci.org/vincenzopalazzo/ilveroprogrammatorebotjava.svg?branch=master) [![Coverage Status](https://coveralls.io/repos/github/vincenzopalazzo/ilveroprogrammatorebotjava/badge.svg?branch=master)](https://coveralls.io/github/vincenzopalazzo/ilveroprogrammatorebotjava?branch=master) [![Telegram](http://trellobot.doomdns.org/telegrambadge.svg)](https://t.me/ilVeroProgrammatore_bot) [![License Info](http://img.shields.io/badge/license-The%20MIT%20License-brightgreen.svg)](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/blob/master/LICENSE.md)
# Il vero programmatore bot 

This bot is a bot for telegrams and is meant to be a collection of phrases/images for programmers, like a spacobot but for programmers.

## Getting Started

To start using my bot just go to the telegram and look for the @ilVeroProgrammatore_bot bot.
Instead if you want to use my architecture to create your bot you must know that I use the following frameworks

The loggin system was initially SLF4J with logback but with bugs introduced and not found yet during development, we opted to use log4j, the configuration file and log4j.properties passed as command line information to the main and therefore customizable as you want [il vero programmatore](http://xmau.com/humour/veroprogrammatore.html) I'll place mine

### Installing

To launch the application by activating the log system, tear the following commandm

```
java -jar app-1.0-jar-with-dependencies.jar /position/file/onDirectory/log4j.properties
```


## Built With

* [Utilta](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/blob/master/app/src/lib/utilita.jar) - Framework for using the console in an enhanced way (input control) for creating the interface from the server-side command line
* [Maven](https://maven.apache.org/) - Dependency Management
* [Gson](https://github.com/google/gson) - Framework for the use of json technology where I do operations on sentences that uses the bot in which also save idChat (non-sensitive data where it respects the GDPR policy) for broadcast messages
* [Jsoup](https://github.com/jhy/jsoup) - Framework for parsing html files, allowed me to easily pick up all the phrases that contain the site .....
* [Emoji-java](https://github.com/vdurmont/emoji-java) - Introduces the emoji into java transforming them into a string
* [Telegrambots](https://github.com/rubenlagus/TelegramBots) - Framework for creating bots for telegrams
* [Log4j](https://github.com/apache/log4j) - Framework for loggin sistem

## Contributing

## Versioning

For the versions available, see the [tags on this repository](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/releases). 

## Authors

* **Palazzo Vincenzo** - *Initial work* - [Palazzo Vincenzo](https://github.com/vincenzopalazzo)

See also the list of [contributors](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/graphs/contributors) who participated in this project.

## License
[MIT](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/blob/master/LICENSE.md)

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc


## WIKI
[take a look at the wiki](https://github.com/vincenzopalazzo/ilveroprogrammatorebotjava/wiki) 
