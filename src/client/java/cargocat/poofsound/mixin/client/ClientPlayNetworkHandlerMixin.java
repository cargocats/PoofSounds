package cargocat.poofsound.mixin.client;

import cargocat.poofsound.PoofSound;
import cargocat.poofsound.PoofSoundClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Inject(at = @At("HEAD"), method = "onEntityStatus")
	public void onEntityStatus(EntityStatusS2CPacket packet, CallbackInfo ci) {
		if (packet.getStatus() == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
			PoofSoundClient.minecraftClient.execute(() -> {
				ClientWorld world = PoofSoundClient.minecraftClient.world;

				if (world == null) return;
				Entity entity = packet.getEntity(world);
				if (entity == null) return;
				if (!(entity instanceof LivingEntity)) return;
				if (entity.getType().isIn(PoofSound.TAG_KEY_NO_POOF_SOUND)) return;
				PoofSound.recentlyDiedEntities.put(entity.getId(), entity.getPos());
			});
		}
	}
}