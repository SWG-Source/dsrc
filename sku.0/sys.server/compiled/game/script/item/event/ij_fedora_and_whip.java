package script.item.event;

import script.*;
import script.library.sui;
import script.library.utils;

public class ij_fedora_and_whip extends script.base_script {

	public ij_fedora_and_whip()
	{
	}

	public int OnInitialize(obj_id self) throws InterruptedException
	{
		if(hasObjVar(self, "item.consumed"))
		{
			destroyObject(self);
		}
		return SCRIPT_CONTINUE;
	}

	public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
	{
		if(!isIdValid(self) || !isIdValid(player))
		{
			return SCRIPT_CONTINUE;
		}
		if(!utils.isNestedWithinAPlayer(self))
		{
			return SCRIPT_CONTINUE;
		}
		if(getTemplateName(self).contains("furniture"))
		{
			mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("spam", "convert"));
		}
		if(getTemplateName(self).contains("wearable"))
		{
			mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("spam", "recolor"));
		}
		return SCRIPT_CONTINUE;
	}

	public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
	{
		if(!isIdValid(self) || !isIdValid(player))
		{
			return SCRIPT_CONTINUE;
		}
		if(!utils.isNestedWithinAPlayer(self))
		{
			return SCRIPT_CONTINUE;
		}
		if(getTemplateName(self).contains("furniture") && item == menu_info_types.SERVER_MENU2)
		{
			sui.msgbox(self, player, "You are about to convert this item from displayable furniture to a wearable. This action cannot be undone. Do you wish to continue?", sui.YES_NO, "Wearable Conversion", "handleSelection");
		}
		if(getTemplateName(self).contains("wearable") && item == menu_info_types.SERVER_MENU3)
		{
			if(utils.isEquipped(self))
			{
				sendSystemMessage(player, new string_id("spam", "must_unequip"));
				return SCRIPT_CONTINUE;
			}

		}
		return SCRIPT_CONTINUE;
	}

	public int handleSelection(obj_id self, dictionary params) throws InterruptedException
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
		obj_id newItem = obj_id.NULL_ID;
		if(getTemplateName(self).contains("fedora"))
		{
			newItem = createObjectInInventoryAllowOverload("object/tangible/wearables/hat/hat_fedora_s01.iff", player);
		}
		else if(getTemplateName(self).contains("whip"))
		{
			newItem = createObjectInInventoryAllowOverload("object/tangible/wearables/belt/belt_whip_s01.iff", player);
		}
		if(isIdValid(newItem))
		{
			attachScript(newItem, "item.special.recolor");
			showLootBox(player, new obj_id[]{newItem});
			setObjVar(self, "item.consumed", true);
			destroyObject(self);
		}
		return SCRIPT_CONTINUE;
	}
}
