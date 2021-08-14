package script.item.special;

import script.*;
import script.library.hue;
import script.library.sui;
import script.library.utils;

/**
 * This script is a special script that can be attached to an item
 * that a player is allowed to recolor. It works similar to that of
 * the Armor Recolor Kit but is made to be dynamic to the type of
 * object that it is attached to insofar as there are colorable
 * components to the object (palette).
 *
 * Implementation:
 *
 * - Attach this script to an object that a player can re-color.
 *
 * - The default assumption is re-coloring is consumable, meaning the
 * script will detach itself once it has been used 2 times to recolor
 * the object. The amount of re-colors remaining is displayed in the
 * item attributes window. Alternatively, you can enable "unlimited"
 * recoloring by attaching the ObjVar "recolor.unlimited" to the object.
 *
 * No other steps are required.
 *
 * SWG Source Addition - 2021
 * Authors: Aconite
 */
public class recolor extends script.base_script {

	public recolor()
	{
	}

	public static final string_id SID_RECOLOR_MENU_OPTION = new string_id ("spam", "recolor");
	public static final string_id SID_MUST_BE_IN_INVENTORY = new string_id("spam", "recolor_in_inventory");
	public static final string_id SID_MUST_NOT_BE_EQUIPPED = new string_id("spam", "must_unequip");
	public static final String PLAYER_ID = "recolor_process.player_oid";
	public static final String TOOL_ID = "recolor_process.tool_oid";
	public static final String VAR_RECOLOR_UNLIMITED = "recolor.unlimited";
	public static final String VAR_RECOLOR_USED_COUNT = "recolor.used_count";
	public static final int MAX_USAGE_COUNT = 2;

	/**
	 * @return true if the item can be re-colored, based on:
	 * - must be valid item and player
	 * - must not be equipped or equipped appearance
	 * - must be in inventory
	 * - must not be fully consumed
	 * - must have color customization data
	 */
	public static boolean canRecolor(obj_id self, obj_id player) throws InterruptedException
	{
		if(!isIdValid(self) || !isIdValid(player))
		{
			return false;
		}
		final dictionary palData = hue.getPalcolorData(self);
		if(palData == null || palData.size() < 1)
		{
			WARNING(String.format("item.special.recolor was attached to template %s which has no Pal Color Data for customization.", getTemplateName(self)));
			return false;
		}
		if(isGod(player))
		{
			return true;
		}
		if(utils.isEquipped(self) || isContainedByPlayerAppearanceInventory(player, self))
		{
			sendSystemMessage(player, SID_MUST_NOT_BE_EQUIPPED);
			return false;
		}
		if(getContainedBy(self) != utils.getInventoryContainer(player))
		{
			sendSystemMessage(player, SID_MUST_BE_IN_INVENTORY);
			return false;
		}
		if(!isRecolorUnlimited(self) && getUsedCount(self) >= MAX_USAGE_COUNT)
		{
			detachScript(self, "item.special.recolor");
			return false;
		}
		return true;
	}

	/**
	 * @return true if this object has no limit on the
	 * number of times it can be recolored
	 */
	public static boolean isRecolorUnlimited(obj_id self) throws InterruptedException
	{
		return hasObjVar(self, VAR_RECOLOR_UNLIMITED);
	}

	/**
	 * @return the number of times this object has been recolored
	 */
	public static int getUsedCount(obj_id self) throws InterruptedException
	{
		if (hasObjVar(self, VAR_RECOLOR_USED_COUNT))
		{
			return getIntObjVar(self, VAR_RECOLOR_USED_COUNT);
		}
		return 0;
	}

	/**
	 * Append recolor menu option
	 */
	public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
	{
		mi.addRootMenu(menu_info_types.SERVER_MENU37, SID_RECOLOR_MENU_OPTION);
		return SCRIPT_CONTINUE;
	}

	/**
	 * Handle Selection of Recolor Option
	 */
	public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
	{
		if(item != menu_info_types.SERVER_MENU37)
		{
			return SCRIPT_CONTINUE;
		}
		if(!canRecolor(self, player))
		{
			return SCRIPT_CONTINUE;
		}
		if(!isRecolorUnlimited(self))
		{
			String prompt = String.format("Warning: The number of times you may recolor this item is limited. Do you wish to proceed?\n\n" +
					"Number of Recolors Allowed: \\#ed8d16%d\\#.\n\n" +
					"Number of Recolors Used: \\#ed8d16%d\\#.", MAX_USAGE_COUNT, getUsedCount(self));
			sui.msgbox(self, player, prompt, sui.YES_NO, "Recolor", "handleRecolorMsgBox");
		}
		else
		{
			showRecolorBox(self, player);
		}
		return SCRIPT_CONTINUE;
	}

	/**
	 * Handler for warning on usage limits popup box (if applicable)
	 */
	public int handleRecolorMsgBox(obj_id self, dictionary params) throws InterruptedException
	{
		obj_id player = sui.getPlayerId(params);
		if (!isIdValid(player))
		{
			return SCRIPT_CONTINUE;
		}
		int bp = sui.getIntButtonPressed(params);
		if (bp == sui.BP_CANCEL)
		{
			return SCRIPT_CONTINUE;
		}
		showRecolorBox(self, player);
		return SCRIPT_CONTINUE;
	}

	/**
	 * Spawn the recolor window
	 */
	public void showRecolorBox(obj_id self, obj_id player) throws InterruptedException
	{
		utils.setScriptVar(self, PLAYER_ID, player);
		utils.setScriptVar(player, TOOL_ID, self);
		ranged_int_custom_var[] palColors = hue.getPalcolorVars(self);
		if ((palColors == null) || (palColors.length == 0))
		{
			return;
		}
		int palColorsLength = palColors.length;
		String[] indexName = new String[4];
		int loop = 4;
		if (palColorsLength < 4)
		{
			for (int i = 0; i < 4; i++)
			{
				indexName[i] = "";
			}
			loop = palColorsLength;
		}
		for (int i = 0; i < loop; i++)
		{
			ranged_int_custom_var ri = palColors[i];
			if (ri != null)
			{
				String customizationVar = ri.getVarName();
				if (customizationVar.startsWith("/"))
				{
					customizationVar = customizationVar.substring(1);
				}
				indexName[i] = customizationVar;
			}
		}
		if (indexName[0].equals(""))
		{
			return;
		}
		openCustomizationWindow(player, self, indexName[0], -1, -1, indexName[1], -1, -1, indexName[2], -1, -1, indexName[3], -1, -1);
	}

	/**
	 * Handle if the cancel button was pressed on the customization window
	 */
	public int cancelTool(obj_id self, dictionary params) throws InterruptedException
	{
		utils.removeScriptVar(self, PLAYER_ID);
		obj_id player = sui.getPlayerId(params);
		if (isIdValid(player))
		{
			utils.removeScriptVar(player, TOOL_ID);
		}
		return SCRIPT_CONTINUE;
	}

	/**
	 * Handle if the tool was used to recolor the object
	 */
	public int decrementTool(obj_id self, dictionary params) throws InterruptedException
	{
		if(!isRecolorUnlimited(self))
		{
			if(!hasObjVar(self, VAR_RECOLOR_USED_COUNT))
			{
				setObjVar(self, VAR_RECOLOR_USED_COUNT, 1);
			}
			else
			{
				setObjVar(self, VAR_RECOLOR_USED_COUNT, getUsedCount(self) +1);
			}
		}
		utils.removeScriptVar(self, PLAYER_ID);
		obj_id player = sui.getPlayerId(params);
		if (isIdValid(player))
		{
			utils.removeScriptVar(player, TOOL_ID);
		}
		sendDirtyAttributesNotification(self);
		if(!isRecolorUnlimited(self) && getUsedCount(self) >= MAX_USAGE_COUNT)
		{
			final String prompt = "You have reached the limit on the number of times this item can be recolored. " +
					"The recolor option will no longer be available.";
			sui.msgbox(player, player, prompt, sui.OK_ONLY, "Recolor", "noHandler");
			detachScript(self, "item.special.recolor");
		}
		sendDirtyObjectMenuNotification(self);
		LOG("item", getPlayerName(player)+" ("+player+") used item.special.recolor to recolor "+self+". Recolor used count is now: "+getUsedCount(self));
		return SCRIPT_CONTINUE;
	}

	/**
	 * Add tool usage count to attributes window
	 */
	public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
	{
		if (player == null || names == null || attribs == null || names.length != attribs.length)
		{
			return SCRIPT_CONTINUE;
		}
		final int firstFreeIndex = getFirstFreeIndex(names);
		if ((firstFreeIndex >= 0) && (firstFreeIndex < names.length))
		{
			names[firstFreeIndex] = "recolor_remaining";
			attribs[firstFreeIndex] = isRecolorUnlimited(self) ? "\\#ed8d16Unlimited\\#." : String.valueOf(MAX_USAGE_COUNT - getUsedCount(self));
		}
		return SCRIPT_CONTINUE;
	}

	/**
	 * Dirty update attributes pane and menu if we're attached post baselines
	 */
	public int OnAttach(obj_id self) throws InterruptedException
	{
		sendDirtyObjectMenuNotification(self);
		sendDirtyAttributesNotification(self);
		return SCRIPT_CONTINUE;
	}

}
