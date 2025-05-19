package cargocat.poofsound.config;

import cargocat.poofsound.PoofSound;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> YetAnotherConfigLib.createBuilder()
                .save(PoofConfig.HANDLER::save)
                .title(Text.translatable("poofsounds.name"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("poofsounds.sound_options"))
                        .tooltip(Text.translatable("poofsounds.sound_options.tooltip"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("poofsounds.group.sound.title"))
                                .description(OptionDescription.of(Text.translatable("poofsounds.group.sound.description")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("poofsounds.name"))
                                        .description(OptionDescription.of(Text.translatable("poofsounds.option.enabled")))
                                        .binding(true, () -> PoofConfig.HANDLER.instance().poofSoundsEnabled, newVal -> PoofConfig.HANDLER.instance().poofSoundsEnabled = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("poofsounds.name.volume"))
                                        .description(OptionDescription.of(Text.translatable("poofsounds.option.volume")))
                                        .binding(100, () -> PoofConfig.HANDLER.instance().poofVolume, newVal -> PoofConfig.HANDLER.instance().poofVolume = newVal)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 200)
                                                .step(1)
                                                .formatValue(value -> Text.literal(value + "%"))
                                        )
                                        .build()
                                )
                                .build())
                        .build()
                )
                .build()
                .generateScreen(parentScreen);
    }
}
