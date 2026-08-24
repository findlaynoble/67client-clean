/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.loader.api.FabricLoader
 */
package dev.sixseven.spotify;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sixseven.SixSevenClient;
import dev.sixseven.rt.Deobf;
import dev.sixseven.spotify.SpotifyState;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;
import net.fabricmc.loader.api.FabricLoader;

public final class SpotifyService {
    private final Path artPath = FabricLoader.getInstance().getConfigDir().resolve(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01cc\u01d5\u01ec\u020f\u025f\u025c\u0231\u029e"));
    private volatile SpotifyState state = SpotifyState.INACTIVE;
    private Process process;
    private BufferedWriter commandWriter;
    private volatile boolean stopped;
    private int restarts;

    public SpotifyState state() {
        return this.state;
    }

    public Path artPath() {
        return this.artPath;
    }

    public void start() {
        if (!System.getProperty(Deobf.decrypt("\u001c_f\u0000s\u00a9\u00a9"), Deobf.decrypt("")).toLowerCase(Locale.ROOT).contains(Deobf.decrypt("\u0004E&"))) {
            SixSevenClient.LOGGER.info(Deobf.decrypt(" \\'\u001a{\u00a2\u00b5\u00a2\u009c\u013f\u0149\u0101\u0131\u0199\u01f7\u01d3\u01fb\u025b\u0215\u025c\u022f\u0298\u0297\u02ce\u02be\u031a\u0359\u0354\u034f\u03df\u03e1\u03d6\u038e\u03d4\u0441\u042a\u0400\u047a\u04da\u04fe"));
            return;
        }
        Thread thread = new Thread(this::runBridge, Deobf.decrypt("E\u001b+\u0002{\u00a1\u00a2\u009e\u00e4\u0108\u0119\u010c\u0137\u0199\u01f5\u01cd\u01b3\u0219\u0203\u025c\u0238\u029e\u0290"));
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void stop() {
        this.stopped = true;
        if (this.process != null) {
            this.process.destroy();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void runBridge() {
        while (!this.stopped && this.restarts < 4) {
            try {
                Path script = this.extractScript();
                ProcessBuilder builder = new ProcessBuilder(Deobf.decrypt("\u0003C?\u000b`\u00b7\u00a4\u008f\u00a5\u0117\u0147\u0106\u013b\u0195"), Deobf.decrypt("^b'>`\u00ab\u00aa\u0083\u00a5\u011e"), Deobf.decrypt("^b'\u0000[\u00aa\u00b8\u008f\u00bb\u011a\u010a\u0117\u012a\u0186\u01f6"), Deobf.decrypt("^i0\u000bq\u00b1\u00b8\u0083\u00a6\u0115\u0139\u010c\u012f\u0199\u01f0\u01cd"), Deobf.decrypt("1U8\u000fa\u00b7"), Deobf.decrypt("^j!\u0002w"), script.toString(), this.artPath.toString());
                builder.redirectErrorStream(false);
                SpotifyService spotifyService = this;
                synchronized (spotifyService) {
                    if (this.stopped) {
                        return;
                    }
                    this.process = builder.start();
                    this.commandWriter = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream(), StandardCharsets.UTF_8));
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream(), StandardCharsets.UTF_8));){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        this.parseLine(line.trim());
                    }
                }
                this.process.waitFor();
            }
            catch (Exception e) {
                SixSevenClient.LOGGER.warn(Deobf.decrypt(" \\'\u001a{\u00a2\u00b5\u00ca\u00ab\u0109\u0100\u0107\u0124\u0195\u01b3\u01d0\u01f7\u021e\u0215\u020f\u027c\u0282\u0288"), (Object)e.toString());
            }
            this.state = SpotifyState.INACTIVE;
            ++this.restarts;
        }
    }

    private Path extractScript() throws Exception {
        Path dir = FabricLoader.getInstance().getConfigDir();
        Files.createDirectories(dir, new FileAttribute[0]);
        Path script = dir.resolve(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01cc\u01c7\u01f3\u020f\u0212\u026a\u023e\u028b\u029c\u02c6\u02bc\u031b\u0357\u030c\u0352\u0381"));
        try (InputStream in = SpotifyService.class.getClassLoader().getResourceAsStream(Deobf.decrypt("\u0012_;\u000bf\u00b7\u00e3\u0099\u00a0\u0103\u011a\u0106\u0135\u0195\u01fd\u01d7\u01f2\u0212\u0214\u025b\u0228\u02d6\u0286\u02d2\u02b4\u030a\u0310\u031a\u0358\u039f\u03e6\u039b\u03ad\u03de\u0470\u042c\u041d\u0464\u04cd\u04b0\u04e2\u04af\u0555\u0577\u0509"));){
            if (in == null) {
                throw new IllegalStateException(Deobf.decrypt("\u0011^!\nu\u00a1\u00ec\u0099\u00aa\u0109\u0100\u0113\u0137\u01d0\u01fe\u01dd\u01ed\u0208\u0218\u025b\u023b\u02d9\u0293\u02d0\u02b4\u0313\u0359\u0311\u034e\u03d4\u03b5\u0384\u03bc\u03ce\u0440\u043b\u041d\u046e\u04cc\u04a4"));
            }
            Files.write(script, in.readAllBytes(), new OpenOption[0]);
        }
        return script;
    }

    private void parseLine(String line) {
        if (line.isEmpty() || !line.startsWith(Deobf.decrypt("\b"))) {
            return;
        }
        try {
            JsonObject json = JsonParser.parseString((String)line).getAsJsonObject();
            if (!json.has(Deobf.decrypt("\u0012O<\u0007d\u00a1")) || !json.get(Deobf.decrypt("\u0012O<\u0007d\u00a1")).getAsBoolean()) {
                this.state = SpotifyState.INACTIVE;
                return;
            }
            this.state = new SpotifyState(true, json.get(Deobf.decrypt("\u0007E<\u0002w")).getAsString(), json.get(Deobf.decrypt("\u0012^<\u0007a\u00b0")).getAsString(), json.get(Deobf.decrypt("\u0003C;#a")).getAsLong(), json.get(Deobf.decrypt("\u0017Y:#a")).getAsLong(), json.get(Deobf.decrypt("\u0003@)\u0017{\u00aa\u00ab")).getAsBoolean(), json.has(Deobf.decrypt("\u0010M&=w\u00a1\u00a7")) && json.get(Deobf.decrypt("\u0010M&=w\u00a1\u00a7")).getAsBoolean(), json.has(Deobf.decrypt("\u0012^<8")) ? json.get(Deobf.decrypt("\u0012^<8")).getAsInt() : 0, json.has(Deobf.decrypt("\u0005C$")) ? json.get(Deobf.decrypt("\u0005C$")).getAsInt() : -1, System.nanoTime());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private synchronized void send(String command) {
        if (this.commandWriter == null) {
            return;
        }
        try {
            this.commandWriter.write(command);
            this.commandWriter.newLine();
            this.commandWriter.flush();
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.warn(Deobf.decrypt(" \\'\u001a{\u00a2\u00b5\u00ca\u00ab\u0109\u0100\u0107\u0124\u0195\u01b3\u01d7\u01f1\u0216\u021c\u0254\u0232\u029d\u02d5\u02c4\u02ba\u0317\u0315\u0319\u0345\u038a\u03b5\u038d\u03a4"), (Object)e.toString());
        }
    }

    public void next() {
        this.send(Deobf.decrypt("=i\u0010:"));
    }

    public void previous() {
        this.send(Deobf.decrypt("#~\r8"));
    }

    public void togglePlay() {
        this.send(Deobf.decrypt("#`\t7B\u0085\u0099\u00b9\u008c"));
    }

    public void seekTo(long ms) {
        this.send("SEEK " + Math.max(0L, ms));
        SpotifyState s = this.state;
        if (s.active()) {
            this.state = new SpotifyState(true, s.title(), s.artist(), ms, s.durMs(), s.playing(), s.canSeek(), s.artVersion(), s.volume(), System.nanoTime());
        }
    }

    public void setVolume(int pct) {
        int v = Math.clamp((long)pct, 0, 100);
        this.send("VOLUME " + v);
        SpotifyState s = this.state;
        if (s.active()) {
            this.state = new SpotifyState(true, s.title(), s.artist(), s.posMs(), s.durMs(), s.playing(), s.canSeek(), s.artVersion(), v, s.receivedNanos());
        }
    }
}

