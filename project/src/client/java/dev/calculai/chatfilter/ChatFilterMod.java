package dev.calculai.chatfilter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class ChatFilterMod implements ClientModInitializer {
    private static final Path CONFIG_FILE =
            FabricLoader.getInstance().getConfigDir().resolve("chatfilter.txt");

    private List<String> blockedWords = List.of();

    @Override
    public void onInitializeClient() {
        loadConfig();

        ClientReceiveMessageEvents.ALLOW_CHAT.register(
                (message, signedMessage, sender, params, timestamp) ->
                        !shouldBlock(message.getString())
        );

        ClientReceiveMessageEvents.ALLOW_GAME.register(
                (message, overlay) -> !shouldBlock(message.getString())
        );
    }

    private void loadConfig() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());

            if (!Files.exists(CONFIG_FILE)) {
                Files.writeString(
                        CONFIG_FILE,
                        "# One blocked word or name per line\n"
                                + "# Matching ignores capitalization\n"
                                + "ExampleGriefer\n",
                        StandardCharsets.UTF_8
                );
            }

            blockedWords = Files.readAllLines(CONFIG_FILE, StandardCharsets.UTF_8)
                    .stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .map(line -> line.toLowerCase(Locale.ROOT))
                    .toList();

            System.out.println("[ChatFilter] Loaded " + blockedWords.size()
                    + " blocked entries.");
        } catch (IOException e) {
            blockedWords = List.of();
            System.err.println("[ChatFilter] Could not load config: " + e.getMessage());
        }
    }

    private boolean shouldBlock(String message) {
        String normalized = message.toLowerCase(Locale.ROOT);
        return blockedWords.stream().anyMatch(normalized::contains);
    }
}

