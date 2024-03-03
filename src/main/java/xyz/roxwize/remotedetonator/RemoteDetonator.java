package xyz.roxwize.remotedetonator;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.RegistryHelper;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;

public class RemoteDetonator implements ModInitializer, GameStartEntrypoint {
	public static final String MOD_ID = "remotedetonator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int itemID;
	public static int detonatorDurability;
	static {
		Properties properties = new Properties();
		properties.setProperty("item_id", "16800");
		properties.setProperty("detonator_max_uses", "4");

		ConfigHandler config = new ConfigHandler(MOD_ID, properties);
		itemID = config.getInt("item_id");
		detonatorDurability = config.getInt("detonator_max_uses") - 1;
		config.updateConfig();
	}
	public static ToolMaterial detonatorMaterial = new ToolMaterial().setDurability(detonatorDurability);
	public static Item detonator = ItemHelper.createItem(MOD_ID, new Detonator("detonator", itemID++, detonatorMaterial), "detonator.png");

	@Override
	public void onInitialize() {
		LOGGER.info("remotedetonator initialized");
	}

	private void initializeRecipies() {
		RecipeBuilder.Shaped(MOD_ID).setShape(
				"S",
				"S",
				"R")
			.addInput('S', Item.ingotSteel)
			.addInput('R', Item.dustRedstone)
			.create("detonator", detonator.getDefaultStack());
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		initializeRecipies();
	}
}
