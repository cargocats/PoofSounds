package cargocat.poofsound.config;

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
                .title(Text.literal("Poof Sounds"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Sound Options"))
                        .tooltip(Text.literal("Control sound settings for poof sounds"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Sound"))
                                .description(OptionDescription.of(Text.literal("Control volume and toggle the mod")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Poof Sounds"))
                                        .description(OptionDescription.of(Text.literal("Are poof sounds enabled?")))
                                        .binding(true, () -> PoofConfig.HANDLER.instance().poofSoundsEnabled, newVal -> PoofConfig.HANDLER.instance().poofSoundsEnabled = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build()
                                )
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("Poof Volume"))
                                        .description(OptionDescription.of(Text.literal("The volume of the poof sound from 0% to 100%")))
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
