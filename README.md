Prerequisites

1. You must have gradle installed
2. You need to have your headless setup login automatically, for some reason, speakers don't work if you are not logged
   into a user. This is pretty annoying as a headless user but this is how you do it:
   try:
   sudo nano /lib/systemd/system/getty@.service  
   And change:
   ExecStart=-/sbin/agetty -o '-p -- \u' --noclear %I $TERM
   for:
   ExecStart=-/sbin/agetty --noissue --autologin YOURusername %I $TERM Type=idle
3. Edit the Config file to use your zip code or city, you don't need to add both.
4. go to project dir and gradle run. viola!
