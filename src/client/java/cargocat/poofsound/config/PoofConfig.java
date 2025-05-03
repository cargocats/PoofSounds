package cargocat.poofsound.config;

import cargocat.poofsound.PoofSound;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class PoofConfig {
    public static ConfigClassHandler<PoofConfig> HANDLER =
            ConfigClassHandler.createBuilder(PoofConfig.class)
            .id(Identifier.of(PoofSound.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve(PoofSound.MOD_ID + ".json5"))
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    public boolean poofSoundsEnabled = true;

    @SerialEntry
    public Integer poofVolume = 100;
}
