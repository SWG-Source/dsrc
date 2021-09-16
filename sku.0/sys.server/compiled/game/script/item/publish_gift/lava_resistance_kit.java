package script.item.publish_gift;

import script.*;
import script.library.sui;
import script.library.utils;
import script.library.vehicle;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Script attached to the Vehicle Lava Resistance Kit in-game item
 * (object/tangible/veteran_reward/vehicle_lava_res_kit.iff)
 * to handle use and making a vehicle lava resistant.
 *
 * @since SWG Source 3.1 - September 2021
 * @author Aconite
 */
public class lava_resistance_kit extends script.base_script {

	/**
	 * "Lava Resistance Kit"
	 */
	private static final String STF_SUI_TITLE = "@vehicle/vehicle:lava_kit_sui_title";
	/**
	 * "The following vehicles from your Datapad are currently eligible to become lava resistant.
	 * This does not include any vehicles stored in your hangar (if applicable). If you do not see
	 * a vehicle listed here that you're looking for, it is either already lava resistant or it
	 * cannot become lava resistant."
	 */
	private static final String STF_SUI_PROMPT = "@vehicle/vehicle:lava_kit_sui_prompt";
	/**
	 * "\\#pcontrast2WARNING:\\#. This item is single use and this change cannot be undone.
	 * If you click "YES" then you will consume this Lava Resistance Kit and this vehicle will
	 * forever be lava resistant. Are you sure you want to continue and apply the kit to \\#pcontrast3%TU\\#.?"
	 */
	private static final string_id STF_SUI_CONFIRM = new string_id("vehicle/vehicle", "lava_kit_sui_confirm");
	/**
	 * "No eligible vehicles were found in your Datapad that the Vehicle Lava Resistance Kit can be used on."
	 */
	private static final string_id STF_NO_VEHICLES = new string_id("vehicle/vehicle", "lava_kit_no_vehicles");
	/**
	 * "Success! Your %TU is now lava resistant."
	 */
	private static final string_id STF_SUCCESS = new string_id("vehicle/vehicle", "lava_kit_success");
	/**
	 * "You have canceled the Vehicle Lava Resistant Kit application process."
	 */
	private static final string_id STF_CANCEL = new string_id("vehicle/vehicle", "lava_kit_cancel");
	/**
	 * ScriptVar which holds the dictionary of vehicles for selection processing
	 * or the OID of the vehicle to be altered, depending on the processing stage.
	 */
	private static final String VAR_SELECTION_TRACKING = "vehicle.lava_kit";

	public int OnAttach(obj_id self) throws InterruptedException
	{
		setCount(self, 1);
		return SCRIPT_CONTINUE;
	}

	public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
	{
		if (isDead(player) ||
				isIncapacitated(player) ||
				!utils.isNestedWithinAPlayer(self) ||
				utils.hasScriptVar(self, VAR_SELECTION_TRACKING)) // block concurrent usage
		{
			return SCRIPT_CONTINUE;
		}
		mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
		return SCRIPT_CONTINUE;
	}

	public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
	{
		if(item == menu_info_types.ITEM_USE && !utils.hasScriptVar(self, VAR_SELECTION_TRACKING))
		{
			// get all vehicle control devices in Datapad, does not include mountable beast pets
			final obj_id[] vehicleCds = vehicle.findVehicleControlDevicesForPlayer(player, false);
			if(vehicleCds != null && vehicleCds.length > 0)
			{
				// filter and store them as <obj_id, String name> in dictionary
				dictionary d = new dictionary();
				Arrays.stream(vehicleCds)
						.filter(base_class::isIdValid) // sanity check ID
						.filter(Predicate.not(vehicle::isLavaResistant)) // exclude vehicles that are already lava resistant
						.forEach(o -> d.put(o, localize(getNameStringId(o))));
				if(d.size() > 0)
				{
					// dictionary stored to kit for reference through handling or cleared later
					utils.setScriptVar(self, VAR_SELECTION_TRACKING, d);
					// note: its kind of awkward if you have 4 Speeder Bike Swoops to determine which is which on this prompt
					// since they can't be renamed in the Datapad, but I couldn't think of an easy-to-implement and feasible alternative
					// and there's no way to guarantee insertion order consistency is the same as the viewed order in the Datapad UI
					sui.listbox(self, player, STF_SUI_PROMPT, sui.OK_CANCEL, STF_SUI_TITLE, d.valuesStringVector(), "handleLavaKitSelection");
					return SCRIPT_CONTINUE;
				}
			}
			sendSystemMessage(player, STF_NO_VEHICLES);
		}
		return SCRIPT_CONTINUE;
	}

	/**
	 * Handle the selected vehicle from the rendered SUI Listbox of vehicles
	 * @see #OnObjectMenuSelect
	 */
	public int handleLavaKitSelection(obj_id self, dictionary params) throws InterruptedException
	{
		final int idx = sui.getListboxSelectedRow(params);
		final obj_id player = sui.getPlayerId(params);
		if(idx >= 0 && sui.getIntButtonPressed(params) == sui.BP_OK && isIdValid(player))
		{
			dictionary d = utils.getDictionaryScriptVar(self, VAR_SELECTION_TRACKING);
			if(d != null && d.size() > 0)
			{
				prose_package pp = new prose_package();
				pp.stringId = STF_SUI_CONFIRM;
				pp.actor.set(d.valuesStringVector()[idx]);
				utils.setScriptVar(self, VAR_SELECTION_TRACKING, obj_id.getObjId(Long.parseLong(d.keysStringVector()[idx])));
				sui.msgbox(self, player, pp, sui.OK_CANCEL, STF_SUI_TITLE, "handleLavaKitConfirmation");
			}
		}
		else
		{
			sendSystemMessage(player, STF_CANCEL);
			utils.removeScriptVar(self, VAR_SELECTION_TRACKING);
		}
		return SCRIPT_CONTINUE;
	}

	/**
	 * Handle the response from the confirmation window Yes/No choice
	 * @see #handleLavaKitSelection
	 */
	public int handleLavaKitConfirmation(obj_id self, dictionary params) throws InterruptedException
	{
		final obj_id player = sui.getPlayerId(params);
		if(sui.getIntButtonPressed(params) == sui.BP_OK && isIdValid(player))
		{
			final obj_id vcd = utils.getObjIdScriptVar(self, VAR_SELECTION_TRACKING);
			if(isIdValid(vcd))
			{
				vehicle.storeAllVehicles(player); // need this to make sure if they are applying to called vehicle, the change takes effect
				setObjVar(vcd, vehicle.VAR_LAVA_RESISTANT, true);
				prose_package pp = new prose_package();
				pp.stringId = STF_SUCCESS;
				pp.actor.set(getNameStringId(vcd));
				sendSystemMessageProse(player, pp);
				nLOG(LogChannel.item, player, self, "Player %s (%s) consumed %s (%s) on vehicle control device "+vcd);
				destroyObject(self);
			}
		}
		else
		{
			sendSystemMessage(player, STF_CANCEL);
		}
		utils.removeScriptVar(self, VAR_SELECTION_TRACKING);
		return SCRIPT_CONTINUE;
	}

}
