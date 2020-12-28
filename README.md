# vaceGaming MusicBot ![CI](https://github.com/jonaznas/vacegaming-musicbot/workflows/CI/badge.svg)

<img align="right" src="https://i.imgur.com/RflqQ0I.png" width=25%>

This is a discord music bot based on [JDA](https://github.com/DV8FromTheWorld/JDA) and [Koin](https://github.com/InsertKoinIO/koin) developed for the vaceGaming community server.
vaceGaming was a registered esport organisation from germany and is now a private community server and invite only.

### 🛠 Development

You have to set the environment as a jvm argument. Choose between ``-Denv=dev`` and ``-Denv=prod``.

#### 🧱 Production:

```
./gradlew build
```

### Environment variables

All config variables can be set as environment variables.

Variable | Description
-------- |  ------------
**BOT__TOKEN** | The discord bot token
**SENTRY_DSN** | DSN for [Sentry](https://sentry.io) (optional)
