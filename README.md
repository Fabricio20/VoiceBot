Discord voice receive tests.

To start, need the env variable `DISCORDO` with the token of the bot.

The class you want to start is the `net.notfab.alexaondiscord.Main` one.

To make google cloud work, on the `new Thread()` on the `EchoHandler` call `InfiniteStreamRecognize.infiniteStreamingRecognize("en-US");` instead
of all the ffmpeg logic. You also need the google cloud sdk variable thing `GOOGLE_APPLICATION_CREDENTIALS`.

You also need to write data on the provide20Ms method (or the receive one, but I've been testing with the provide one).

For GCloud I made the `sharedQueue` variable static and public, so just write to that the `byte[]`.