package script.theme_park.wod;

import script.library.static_item;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

/**
 * WoD Theme Park Seed Jar
 * Per GU 19
 *
 * Seed Jars are given for completing herbalist quests in both
 * the prologue and during the theme park. Different jars (either
 * an individual collection jar) and the collection-rewarded jars
 * give schematics from the corresponding lists a random item when
 * consumed (they are all NS/SMC or generic plants/trees, with the
 * NS and SMC items having a red or blue glow, respectively)
 *
 * SWG Source Addition 2021
 * Authors: Aconite
 */
public class seed_jar extends script.base_script {

	public seed_jar()
	{
	}
	public static final string_id SID_MNU_USE = new string_id("spam", "open");
	public static final string_id SID_SYS_NOT_IN_INV = new string_id("spam", "cannot_use_not_in_inv");
	public static final String[] PROLOGUE_QUESTS = {
			"item_schematic_wod_pro_tree_01",
			"item_schematic_wod_pro_tree_02",
			"item_schematic_wod_pro_tree_03",
			"item_schematic_wod_pro_tree_04",
			"item_schematic_wod_pro_tree_05",
			"item_schematic_wod_pro_tree_06",
			"item_schematic_wod_pro_tree_07",
			"item_schematic_wod_pro_tree_08"
	};
	public static final String[] PROLOGUE_COLLECTION_NS = {
			"item_schematic_wod_pro_ns_tree_01",
			"item_schematic_wod_pro_ns_tree_02",
			"item_schematic_wod_pro_ns_tree_03",
			"item_schematic_wod_pro_ns_tree_04",
			"item_schematic_wod_pro_ns_tree_05",
			"item_schematic_wod_pro_ns_tree_06",
			"item_schematic_wod_pro_ns_tree_07",
			"item_schematic_wod_pro_ns_tree_08"
	};
	public static final String[] PROLOGUE_COLLECTION_SMC = {
			"item_schematic_wod_pro_sm_tree_01",
			"item_schematic_wod_pro_sm_tree_02",
			"item_schematic_wod_pro_sm_tree_03",
			"item_schematic_wod_pro_sm_tree_04",
			"item_schematic_wod_pro_sm_tree_05",
			"item_schematic_wod_pro_sm_tree_06",
			"item_schematic_wod_pro_sm_tree_07",
			"item_schematic_wod_pro_sm_tree_08"
	};
	public static final String[] THEME_PARK_QUEST = {
			"item_schematic_wod_potted_plant_01",
			"item_schematic_wod_potted_plant_02",
			"item_schematic_wod_potted_plant_03",
			"item_schematic_wod_potted_plant_04",
			"item_schematic_wod_potted_plant_05",
			"item_schematic_wod_potted_plant_06",
			"item_schematic_wod_potted_plant_07",
			"item_schematic_wod_potted_plant_08",
			"item_schematic_wod_potted_plant_09"
	};
	public static final String[] THEME_PARK_COLLECTION_NS = {
			"item_schematic_wod_ns_potted_plant_01",
			"item_schematic_wod_ns_potted_plant_02",
			"item_schematic_wod_ns_potted_plant_03",
			"item_schematic_wod_ns_potted_plant_04",
			"item_schematic_wod_ns_potted_plant_05",
			"item_schematic_wod_ns_potted_plant_06",
			"item_schematic_wod_ns_potted_plant_07",
			"item_schematic_wod_ns_potted_plant_08",
			"item_schematic_wod_ns_potted_plant_09"
	};
	public static final String[] THEME_PARK_COLLECTION_SMC = {
			"item_schematic_wod_sm_potted_plant_01",
			"item_schematic_wod_sm_potted_plant_02",
			"item_schematic_wod_sm_potted_plant_03",
			"item_schematic_wod_sm_potted_plant_04",
			"item_schematic_wod_sm_potted_plant_05",
			"item_schematic_wod_sm_potted_plant_06",
			"item_schematic_wod_sm_potted_plant_07",
			"item_schematic_wod_sm_potted_plant_08",
			"item_schematic_wod_sm_potted_plant_09"
	};

	public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
	{
		if (!isValidId(self) || !exists(self))
		{
			return SCRIPT_CONTINUE;
		}
		if (isDead(player) || isIncapacitated(player))
		{
			return SCRIPT_CONTINUE;
		}
		mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
		return SCRIPT_CONTINUE;
	}

	public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
	{
		if (!isValidId(self) || !exists(self))
		{
			return SCRIPT_CONTINUE;
		}
		if (item != menu_info_types.ITEM_USE)
		{
			return SCRIPT_CONTINUE;
		}
		if (isDead(player) || isIncapacitated(player))
		{
			return SCRIPT_CONTINUE;
		}
		if (!utils.isNestedWithinAPlayer(self))
		{
			sendSystemMessage(player, SID_SYS_NOT_IN_INV);
			return SCRIPT_CONTINUE;
		}
		final int type = getIntObjVar(self, "wod.seedJarRewardType");
		String itemToGive = null;
		switch(type)
		{
			case 1:
				itemToGive = PROLOGUE_QUESTS[rand(0, PROLOGUE_QUESTS.length-1)];
				break;
			case 2:
				itemToGive = PROLOGUE_COLLECTION_NS[rand(0, PROLOGUE_COLLECTION_NS.length-1)];
				break;
			case 3:
				itemToGive = PROLOGUE_COLLECTION_SMC[rand(0, PROLOGUE_COLLECTION_SMC.length-1)];
				break;
			case 4:
				itemToGive = THEME_PARK_QUEST[rand(0, THEME_PARK_QUEST.length-1)];
				break;
			case 5:
				itemToGive = THEME_PARK_COLLECTION_NS[rand(0, THEME_PARK_COLLECTION_NS.length-1)];
				break;
			case 6:
				itemToGive = THEME_PARK_COLLECTION_SMC[rand(0, THEME_PARK_COLLECTION_SMC.length-1)];
				break;
		}
		if(itemToGive != null)
		{
			final obj_id createdSchematic = static_item.createNewItemFunction(itemToGive, utils.getInventoryContainer(player));
			if (isIdValid(createdSchematic))
			{
				showLootBox(player, new obj_id[]{createdSchematic});
				destroyObject(self);
			}
		}
		return SCRIPT_CONTINUE;
	}
}
