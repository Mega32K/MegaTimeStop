package com.mega.timestop;

import com.mega.timestop.common.EntityRegister;
import com.mega.timestop.common.SoundsRegister;
import com.mega.timestop.render.entity.KnifeRenderer;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TimestopMod.MODID)
public class TimestopMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "megatimestop";
    // Create a Deferred Register to hold Items which will all be registered under the "example" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> CLOCK = ITEMS.register("time_clock", () ->
            new ClockItem(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant().stacksTo(1)));
    public static final RegistryObject<Item> Knife = ITEMS.register("knife", () ->
            new KnifeItem(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant().stacksTo(16)));
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> MTS_TAB = CREATIVE_MODE_TABS.register("mts_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CLOCK.get().getDefaultInstance())
            .title(Component.translatable("tab.mts.name").withStyle(ChatFormatting.YELLOW))
            .displayItems((parameters, output) -> {
                output.accept(CLOCK.get());
                output.accept(Knife.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public TimestopMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        SoundsRegister.SOUNDS.register(modEventBus);
        EntityRegister.ENTITIES.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        s.scheduleAtFixedRate(() -> {
            if (!Time.get()) Time.millis++;
        }, 1, 1, TimeUnit.MILLISECONDS);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM MTS Mod");
        EntityRenderers.register(EntityRegister.flyingSwordEntity.get(), KnifeRenderer::new);

    }
}
