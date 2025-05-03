package cargocat.poofsound;

import cargocat.poofsound.config.PoofConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.Map;

public class PoofSoundClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PoofConfig.HANDLER.load();
        PoofConfig poofConfig = PoofConfig.HANDLER.instance();

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.world == null) {
                return;
            }

            Iterator<Map.Entry<Integer, Vec3d>> iterator = PoofSound.recentlyDiedEntities.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Integer, Vec3d> entry = iterator.next();
                int id = entry.getKey();
                Entity entity = client.world.getEntityById(id);

                if (entity != null) {
                    entry.setValue(entity.getPos());
                } else {
                    if (!poofConfig.poofSoundsEnabled) {
                        return;
                    }

                    Vec3d pos = entry.getValue();
                    client.execute(() -> client.world.playSound(pos.x, pos.y, pos.z, PoofSound.POOF_SOUND_EVENT, SoundCategory.NEUTRAL, PoofConfig.HANDLER.instance().poofVolume / 100.0f, 1.0f, true));
                    iterator.remove();
                }
            }
        });
    }
}