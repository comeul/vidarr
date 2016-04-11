# Vidarr User Guide #
**For Version 1.6.7**

  1. Purpose
  1. Interface
  1. Tips & Tricks
  1. Known Errors
  1. Screensharing on Mac to solve errors
  1. Credits/Contact

## 1)   Purpose: ##
Vidarr is a Silence Alarm for radio stations that broadcast 24 hours a day.  It listens to a user-selected audio driver, and sends an email or SMS message if the sound levels fall below a threshold for a selectable amount of time.  This way, a station staff member can know that there is dead air, and can remedy the problem.

Vidarr should work with any sound driver.  It can monitor external hardware (if you use and external mixing board) or internal sound (through soundflower).  If you are using Digidesign audio hardware, or routing through the OS X HAL (Hardware Abstraction Layer) you may have some trouble getting it to register in Vidarr. There is an easy workaround though, go to the

---


## 2)   Interface: ##

**Alarm:**

**On/Off** - Determines whether the system is analyzing audio or not

**L and R Channels** - Give a visual of current audio levels on the selected audio driver.

**Silence Threshold** - Level below which the alert timer begins to count.  This is best set at just above the Noise Floor.  If set too high, it may send a false alarm during a particularly quiet song. If your system has a noticeable level of line noise, you may need to adjust it to be louder. If the audio goes back above the threshold before the alarm is triggered, it will reset the alert timer.

**Sensitivity**- Gain control for the audio input into Vidarr.  Does not affect audio playback of the source.

**Alert Timer** - Selectable number of seconds before the alarm is triggered.  This is to prevent false alarms from small dead air breaks between songs or a DJ who might be a little slow to talk. This interim period is a "Warning Stage", indicated by the yellow light in the upper righthand corner. 15 seconds is the default, but a time of 120 seconds is advisable. It may be changed by clicking and dragging up and down, or clicking and typing the desired amount in.

**Alert Repeat** - Selectable number of hours before Vidarr will re-send it's alert messages. Either annoy your engineer until it gets fixed, or send once and then not again for 9999 Hours.  It may be changed by clicking and dragging up and down, or clicking and typing the desired amount in.


**Audio Driver** - Select which audio driver to use (See Known Errors in User Guide if using  Digidesign hardware)

**Audio Input** - Select which input from the given driver to use.

**Left and Right Channels** - Select which input channels to use for monitoring. This may come in handy for users who use 16 channel Soundflower or if the default 1 and 2 are not the main inputs.

**Silence Elapsed**- When the Alarm has been triggered, the alert timer begins counting.  This window displays the number of seconds that the warning stage has been active. When the alarm has been triggered, it continues counting, but turns red.

**Status Lights** - In the upper right hand corner, three lights indicate the status of the alarm.  Green, or Normal, means that the audio is above the threshold.  Yellow, or Warning, means that audio is below the threshold, and the alert timer is counting to prepare to send an alarm. Red, or Alarm Sent, means that the staition has has been below the threshold for the given time, and has sent the error message.

**Save Settings**- Save all current settings in Vidarr.

**Email:**

**Username** - Enter the gmail username (without the @gmail.com entered) of the email account that will send the alarm message.

**Password** - Enter the password for the username. _**Warning**_ While Vidarr does hide your email password, it is still advisable to use an account that is specifically for the alarm, rather than a personal or business account. It is still possible to find the stored password.

**To** - Enter in the email address that the alarm will be sent to. Separate multiple addresses with a comma and space. You can also enter in phone SMS.  For example, you might send an email to 2175551234@phonecarrier.net, and it would send the alert as a text message to your phone.

For a list of phone providers' SMS addresses, see http://www.makeuseof.com/tag/email-to-sms/

_User David Buell noted that AT&T currently does not seem to allow sending messages from an email client to the iPhone. After some testing, I found similar results. Not much can be done on Vidarr's end to fix that._

**Subject & Body** - C'mon, you've sent email before, right?  Enter your alert message here.  If you are running Vidarr on multiple stations, you may want to specialize this to indicate which one is down.

**Send** - send an email without triggering the alarm.  A good way to test the system.

**Terminal:**
The Terminal area is just like typing commands into Terminal on OS X.  This makes it possible for Vidarr to launch applescripts and other software in the case of a silence trigger.  The button in the upper left is a way to manually launch the script.

---


## 3) Tips & Tricks: ##

**Routing Undetectable Audio:**
If you are using the OS X HAL option in Radiologik, or using Digidesign hardware, Vidarr probably won't be able to find the correct drivers in it's selection menu. This is partially due to MaxMSP (Vidarr's build language) and the tricky nature that Digidesign hardware exhibits in it's interactions with it. Have no fear though! It's easy to create a channel for you to monitor, especially if you are using Nicecast.

  1. Download Soundflower at http://cycling74.com/products/soundflower/
  1. Run Soundflowerbed. Set the 2 Channel Soundflower to "None". This will prevent the music from playing over your computer speakers.
  1. In your Nicecast Effects pane, Add an "Auxillary Device Output" plugin.
  1. Route your music into Soundflower (2 Channel)
  1. Set Vidarr to detect the Audio in Soundflower (2 Channel)
  1. Enjoy!

---


## 4)   Known Errors: ##

**Email** - Unfortunately, we were only able to get Vidarr to use gmail to send messages.  Neither of the creators of Vidarr are programmers by nature, and so Vidarr uses a patcher written by Cycling 74 forums member Myer Nore.  It can be found at http://cycling74.com/forums/topic.php?id=12360#post-66651

Hopefully, Universal Email ability will be added in the future. It should be possible.

---


## 5)   Screensharing ##

One of the great things about using OSX is that it has a built-in Screensharing application.  It is in the HD>System>Library>CoreServices folder.  For instructions on using it, go to http://reviews.cnet.com/8301-13727_7-10329122-263.html

Using Screensharing, you can use a Mac at home to login remotely to your studio and fix any software problems.

---



## 6)   Credits/Contact ##
Vidarr was created by Josh Gumiela and Lucas McCallister, two students at Southern Illinois University Carbondale.  It was coded in MaxMSP, a visual Programming language by Cycling74.  Lucas conceptualized the idea after seeing Josh do a presentation on MaxMSP.  Josh did most of the hard work, finding the email patcher and building the original logic, polished by Lucas and released publicly as 1.4.5. Lucas has handled most of the development since then.

The email feature of Vidarr is a patcher made by Cycling74 Forums user Myer Nore.  It can be found in this thread: http://cycling74.com/forums/topic.php?id=12360#post-66651

The Terminal Commands were an idea by Radiologik Forums user Lane.  It was implemented using the aka.shell object created by Masayuki Akamatsu at http://www.iamas.ac.jp/~aka/max/

Special Thanks go to Beta Testers:
Lane, Jay, Snowman, and Udo from the Radiologik Forums

Questions or Comments about Vidarr can be sent to:
Lucas McCallister - feedback@sunnysidesound.com
Josh Gumiela - gumihere@gmail.com

Vidarr is inspired by the Pira.cz Silence Detector, a feature-loaded silence detector for windows systems that can be found here: http://pira.cz/eng/silence.htm

©Josh Gumiela and Lucas McCallister, 2010.

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details: http://creativecommons.org/licenses/GPL/2.0

**The Name:**
Vidarr is the name of a Norse God who is the brother of Thor.  His major role is to slay Fenrir and end Ragnarok, the Norse Armageddon.  He is associated with restoring order from chaos, and has a vow of silence - a very appropriate character to watch over your audio stream! The correct pronunciation (we think) is along the lines of VY-dr.  Lucas says "Vihh-darr" though.