package cargocat.poofsound;

import cargocat.poofsound.config.ModMenuIntegration;
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
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		PoofConfig.HANDLER.load();

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
					Vec3d pos = entry.getValue();
					client.execute(() -> {
						if (!PoofConfig.HANDLER.instance().poofSoundsEnabled) {
							return;
						}
						client.world.playSound(pos.x, pos.y, pos.z,
								PoofSound.POOF_SOUND_EVENT, SoundCategory.NEUTRAL, PoofConfig.HANDLER.instance().poofVolume / 100.0f, 1.0f, true);
					});
					iterator.remove();
				}
			}
		});
	}
}