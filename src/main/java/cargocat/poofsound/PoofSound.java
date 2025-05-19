package cargocat.poofsound;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PoofSound implements ModInitializer {
	public static final String MOD_ID = "poofsound";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier POOF_SOUND_ID = Identifier.of(MOD_ID, "poof");
	public static final SoundEvent POOF_SOUND_EVENT = SoundEvent.of(POOF_SOUND_ID);
	public static final HashMap<Integer, Vec3d> recentlyDiedEntities = new HashMap<>();
	public static final TagKey<EntityType<?>> TAG_KEY_NO_POOF_SOUND = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "no_poof_sound"));

	@Override
	public void onInitialize() {
		Registry.register(Registries.SOUND_EVENT, POOF_SOUND_ID, POOF_SOUND_EVENT);
		LOGGER.info("Initialized Poof Sounds!");
	}
}