# prayertime

An adhan (Islamic call to prayer) system for a headless Linux machine. Every
morning it fetches the day's prayer times, rewrites the system crontab to
match, and plays the adhan through connected speakers at each of the five
prayers — with a separate recording for Fajr, and the iqamah following at
configurable offsets.

The interesting constraint: prayer times drift a little every day, so a fixed
schedule can never stay correct. The answer here is that the **last cron entry
the app writes is the one that re-runs the app itself the next morning** — the
schedule regenerates in perpetuity. Set it up once and it runs unattended.

## How it works

Each morning, the self-scheduled run:

1. Fetches today's timings for your city from the public
   [AlAdhan API](https://aladhan.com/prayer-times-api)
2. Layers on iqamah times using the per-prayer offsets in
   `configuration/IqamahOffsets.txt`
3. Generates a fresh crontab: ten audio jobs (adhan + iqamah for each of the
   five prayers) plus tomorrow's self-rerun entry, and installs it with
   `crontab`
4. Emails the day's schedule (Jakarta Mail)

When a job fires, cron plays the right recording from `assets/athaan/`
(`fajr.wav`, `athaan.wav`, or `iqamah.wav`) through the speakers via SoX's
`play`.

## Configuration

Everything lives in `configuration/`:

- **`Config.txt`** — your city (or zip), timezone, and the email address the
  daily schedule goes to
- **`IqamahOffsets.txt`** — minutes between adhan and iqamah, per prayer
- **`SecretConfig.json`** — SMTP credentials for the schedule email.
  Gitignored; never committed. Shape:

  ```json
  { "EmailConfig": { "email": "you@example.com", "password": "app-password" } }
  ```

## Running it

Prerequisites: Java 21, Gradle, cron, SoX (`play`), and speakers on the box.

```bash
gradle run
```

That single run installs the crontab; cron takes it from there.

**Headless note:** audio output needs a logged-in user session, so on a
headless box enable autologin:

```bash
sudo nano /lib/systemd/system/getty@.service
```

and change

```
ExecStart=-/sbin/agetty -o '-p -- \u' --noclear %I $TERM
```

to

```
ExecStart=-/sbin/agetty --noissue --autologin YOURusername %I $TERM Type=idle
```

## Stack

Java 21 · Spring Boot · OkHttp + Gson · Joda-Time · Jakarta Mail ·
JUnit 5 + Mockito
