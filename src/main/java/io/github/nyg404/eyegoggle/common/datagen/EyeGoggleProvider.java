package io.github.nyg404.eyegoggle.common.datagen;

import io.github.nyg404.eyegoggle.api.point.ListExperiencePoint;
import io.github.nyg404.eyegoggle.common.init.EyeGoggleExperiencePoint;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class EyeGoggleProvider extends DataMapProvider {

    protected EyeGoggleProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    private void addToPoint(Holder<EntityType<?>> mobHolder, Consumer<ListExperiencePoint.Builder> builderConsumer){
        ListExperiencePoint.Builder builder = new ListExperiencePoint().builder();
        builderConsumer.accept(builder);
        ListExperiencePoint container = builder.build();
        builder(EyeGoggleDataMaps.EXPERIENCE_POINT_DATA_MAP_TYPE).replace(true).add(mobHolder, container, false);
    }
    @Override
    protected void gather(HolderLookup.Provider provider) {
        LOGGER.info("Gathering experience point data...");
        addToPoint(EntityType.ZOMBIE.builtInRegistryHolder(), builder -> {
            LOGGER.info("Adding combat point for zombie: {}", EyeGoggleExperiencePoint.COMPAT.get().getName());
            builder.add(EyeGoggleExperiencePoint.COMPAT.get(), 25);
        });
        addToPoint(EntityType.SKELETON.builtInRegistryHolder(), builder -> {
            LOGGER.info("Adding combat point for skeleton: {}", EyeGoggleExperiencePoint.COMPAT.get().getName());
            builder.add(EyeGoggleExperiencePoint.COMPAT.get(), 30);
        });
        LOGGER.info("Finished gathering experience point data.");
    }


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new EyeGoggleProvider(output, lookupProvider));
    }

}
