package cargocat.poofsound;

import cargocat.poofsound.config.PoofConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class PoofSoundClient implements ClientModInitializer {
    public static MinecraftClient minecraftClient = MinecraftClient.getInstance();
    @Override
    public void onInitializeClient() {
        PoofConfig.HANDLER.load();
        PoofConfig poofConfigInstance = PoofConfig.HANDLER.instance();

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.world == null || PoofSound.recentlyDiedEntities.isEmpty()) {
                return;
            }

            PoofSound.recentlyDiedEntities.entrySet().removeIf(entry -> {
                Entity entity = client.world.getEntityById(entry.getKey());

                if (entity != null) {
                    entry.setValue(entity.getPos());
                    return false;
                } else {
                    if (!poofConfigInstance.poofSoundsEnabled) {
                        return true;
                    }
                    Vec3d entityPos = entry.getValue();
                    client.getSoundManager().play(new PositionedSoundInstance(
                            PoofSound.POOF_SOUND_EVENT,
                            SoundCategory.AMBIENT,
                            poofConfigInstance.poofVolume / 100.0f,1.0f, SoundInstance.createRandom(),
                            entityPos.x, entityPos.y, entityPos.z
                    ));
                    return true;
                }
            });
        });

        PoofSound.LOGGER.info("Initialized Poof Sounds client!");
    }
}