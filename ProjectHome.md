# Wall-T #

Wall-T is a build radiator to monitor build status for [Teamcity Jetbrains](http://www.jetbrains.com/teamcity/) continuous integration server. 

Feel free to send any issue or suggestion by opening a report on the _Issue_ tab.

## Overview ##
<a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-overview.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-overview.png' border='0' height='700' /></a>

## Quick Start ##

  1. [Download](#Downloads.md) latest zip archive
  1. Unzip the archive
  1. Edit the included _start_ script or define JAVA8\_HOME environment variable to a JRE 8+
  1. Launch the _start_ script
  1. Enjoy !


---


## Details ##

#### Requirements ####
Wall-T is written using JavaFX and Java 8. You need a Java Runtime Environment version 8 or higher.

You can [donwload Java 8+ on this link](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html).

#### Server Configuration ####

When the application starts, a configuration view is available to configure or customize  the radiator view (we also talk about wall view).

First, you must configure the teamcity server url and credentials. You can use use the guest mode by keeping empty the username or setting it at the value '_guest_'. Note that the guest mode let you connect to the server only if the server authorize the guest mode. You can also use a specific user login to connect to the server. Only HTTP and HTTPS protocol are currently supported: the server url must start with either of them.

Moreover, the application also supports the use of a http proxy that you can configure.

Finally, you should set the api version that you want to use, according to your teamcity server version. You can not use an API version greater than your server version, but you can use a API version less than the server version if it is still supported.

If the connection is impossible, please check the configuration. The error message and the log file can help you what the issue is.

<a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-configuration-view.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-configuration-view.png' border='0' height='500' /></a>

#### View Preferences ####

One connected, the list of all projects and build types are displayed. You can select the ones you wish display on the wall view. By default, the name displayed on the wall view is the name defined on the server for the build type or project, but you can also for each of them choose an alias name to better the display.

You can configure the maximum number of tiles on the wall view, by column and by row. If you have selected more items that the wall can displayed in one screen according to this setting, the wall view will use several screens by switching from one to another alternately.

You can reorder selected items on the configuration view. Please note that right now, you can not mix build types and projects: projects are always displayed at the end of wall view.


##### Tile of a Build Type #####

For each selected build type, the alias name (or build type name if alias is not set) is displayed on a colored background. The color of the background represents the status of the project: grey when no data, green when last build is successful, red when last build is failure.

When a build is running for a build type, the tile is animated and a progress bar appears in the backgound.

At the right, a context part displays some information: the finish date of the last build if there is no running build or the time left for the current running build. A weather icon is also present: a sun if the 3 last builds are successful, cloudy sun if one of them, cloud if two of them, and rain if all of them.

<a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-successful.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-successful.png' border='0' height='100' /></a> <a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-running.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-running.png' border='0' height='100' /></a> <a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-failure.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-failure.png' border='0' height='100' /></a>

##### Tile of a Project #####

Project are rendered in a different way: you have the name, and the number of success and failure of build types included in this project.

Please keep in mind that selecting a project implies to monitor indirectly many other build types (all the children) and may increase significantly the number of requests to the server.

<a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-project.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-tile-project.png' border='0' height='100' /></a>

##### Light mode #####

If you prefer less information on wall view, you can opt for the light mode, which removes all context part on tiles and keep only the name and background status.

<a href='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-light-mode.png'><img src='https://googledrive.com/host/0B5cKQDFfHPOmSXBWSmcxRGRnQ3M/screenshot-light-mode.png' border='0' height='100' /></a>

##### Tips #####
Press F11 to switch to fullscreen mode.

Press ESC to switch to configuration view.

#### Api notes ####

Application currently supports API version from 6 to 8.1.

##### 6.0 -> 8.0 #####
  * Support of build types
  * Support of projects, but project status only reflects status of the direct children

##### 8.1 #####
  * Support of queued status: an icon appears when a build is in queue
  * Project status reflects now status of all child build types (ie also build type included in a subchild project)

#### Known "Issues" ####
  * Right now, application doesn't support branch feature of teamcity server.
  * When modification is applied to server side (addind new projet, new build configuration, or deleting one, etc.), the radiator ignores it. You can press "Connect to server" button on configuration view to refresh data.


---


## Downloads ##

| **Version** | **Release Date** | **File** | **Tags** | |
|:------------|:-----------------|:---------|:---------|:|
| **1.0**     | **2014-03-19**   | <a href='http://goo.gl/jaeizY'>Wall-T-1.0.zip</a>| **#latest** **#recommended** | Downloaded 400+ times |
| _1.0-beta_  | _2014-03-03_     | _removed_ | _#deprecated_ | Downloaded 15+ times |

## Changelog ##

  * **Version 1.0** (2013-03-21)
    * When status can't be retreived from server for a build (timeout for any reason), build is now ignored for monitoring during 20 minutes to not flood server
    * Log files are now rolling to save space on disk
    * Start scripts launch the application in background mode (no console)
    * Fix. Sometimes running build status was not correctly updated when build was finished
    * Fix. ERROR status was ignored for project view (only SUCCESS and FAILURE status were considered)
    * Fix. On Windows, the start script didn't work correctly when space was present into jre 8 path
    * Fix. On linux, resizing the window to fix layout is no more needed


  * **Version 1.0-beta** (2013-03-03)
    * Initial version
    * Uses JavaFX 8 and requires Java 1.8+
    * Support of Teamcity version 6.0 to 8.1
    * Support of both guest and authenticated mode
    * Support of http proxy configuration
    * Wall view can display both in full mode (with information and icons) and light mode (less information)
    * User can choose how many max builds are displayed by screen
    * Support of project status monitoring (reflecting the status of child build types)