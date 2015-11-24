package script.systems.crafting.droid.modules;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;
import script.library.xp;
import script.library.prose;
import script.library.permissions;
import script.library.corpse;
import script.library.sui;
import script.library.skill;
import script.library.resource;
import script.library.group;
import script.library.combat;

public class harvest_module extends script.base_script
{
    public harvest_module()
    {
    }
    public static final String STF = "pet/droid_modules";
    public static final String AUTO_HARVEST = "droidAutoHarvest";
    public static final float MAX_HARVEST_DISTANCE = 256f;
    public static final String DATATABLE = "datatables/mob/creatures.iff";
    public static final String HARVEST_INTEREST = "droid.harvestInterest";
    public static final int INTEREST_MEAT = corpse.CCR_MEAT;
    public static final int INTEREST_HIDE = corpse.CCR_HIDE;
    public static final int INTEREST_BONE = corpse.CCR_BONE;
    public static final int INTEREST_RANDOM = -1;
    public static final String MEAT = "meatType";
    public static final String HIDE = "hideType";
    public static final String BONE = "boneType";
    public static final String OWNER = "droidMove.owner";
    public static final String TARGET = "droidMove.target";
    public static final String HARVEST = "droidMove.harvestInterest";
    public static final string_id SID_HARVEST_OPTIONS = new string_id(STF, "harvest_options");
    public static final string_id SID_TARGET_HARVEST = new string_id(STF, "program_target_harvest");
    public static final string_id SID_TOGGLE_AUTO_HARVEST = new string_id(STF, "toggle_auto_harvest");
    public static final string_id SID_SET_HARVEST_INTEREST = new string_id(STF, "set_harvest_interest");
    public static final string_id SID_AUTO_HARVEST_ON = new string_id(STF, "auto_harvest_on");
    public static final string_id SID_AUTO_HARVEST_OFF = new string_id(STF, "auto_harvest_off");
    public static final string_id SID_INVALID_HARVEST_TARGET = new string_id(STF, "invalid_harvest_target");
    public static final string_id SID_ENABLE_AUTO_HARVEST = new string_id(STF, "auto_harvest_enabled");
    public static final string_id SID_DISABLE_AUTO_HARVEST = new string_id(STF, "auto_harvest_disabled");
    public static final string_id SID_ALREADY_HARVESTED = new string_id(STF, "already_harvested");
    public static final string_id SID_CANNOT_ACCESS_CORPSE = new string_id(STF, "cannot_access_corpse");
    public static final string_id SID_NO_RESOURCES_TO_HARVEST = new string_id(STF, "no_resources_to_harvest");
    public static final string_id SID_TARGET_TYPE_NOT_FOUND = new string_id(STF, "target_type_not_found");
    public static final string_id SID_CORPSE_TOO_FAR = new string_id(STF, "corpse_too_far");
    public static final string_id SID_DROID_HARVEST_MESSAGE = new string_id(STF, "droid_harvest");
    public static final string_id SID_INTEREST_SET_RANDOM = new string_id(STF, "interest_set_random");
    public static final string_id SID_INTEREST_SET_HIDE = new string_id(STF, "interest_set_hide");
    public static final string_id SID_INTEREST_SET_BONE = new string_id(STF, "interest_set_bone");
    public static final string_id SID_INTEREST_SET_MEAT = new string_id(STF, "interest_set_meat");
    public static final string_id SID_SET_INTEREST_RANDOM = new string_id(STF, "set_interest_random");
    public static final string_id SID_SET_INTEREST_MEAT = new string_id(STF, "set_interest_meat");
    public static final string_id SID_SET_INTEREST_HIDE = new string_id(STF, "set_interest_hide");
    public static final string_id SID_SET_INTEREST_BONE = new string_id(STF, "set_interest_bone");
    public static final string_id SET_INTEREST_SUI_D = new string_id(STF, "set_interest_d");
    public static final string_id SET_INTEREST_SUI_T = new string_id(STF, "set_interest_t");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(self);
        if (!isIdValid(controlDevice))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controlDevice, HARVEST_INTEREST))
        {
            copyObjVar(controlDevice, self, HARVEST_INTEREST);
        }
        if (hasObjVar(controlDevice, AUTO_HARVEST))
        {
            copyObjVar(controlDevice, self, AUTO_HARVEST);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == getMaster(self))
        {
            if (true)
            {
                int menu = mi.addRootMenu(menu_info_types.SERVER_HARVEST_CORPSE, SID_HARVEST_OPTIONS);
                mi.addSubMenu(menu, menu_info_types.ELEVATOR_UP, SID_TARGET_HARVEST);
                mi.addSubMenu(menu, menu_info_types.CITY_CITIZENS, SID_SET_HARVEST_INTEREST);
                mi.addSubMenu(menu, menu_info_types.ELEVATOR_DOWN, SID_TOGGLE_AUTO_HARVEST);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isDead(self) || ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF, "not_enough_power"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ELEVATOR_UP)
        {
            pet_lib.prepToLearn(self, pet_lib.COMMAND_TARGET_HARVEST);
        }
        else if (item == menu_info_types.CITY_CITIZENS)
        {
            setHarvestInterest(self, player);
        }
        else if (item == menu_info_types.ELEVATOR_DOWN)
        {
            toggleAutoHarvest(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int doTargetHarvest(obj_id self, dictionary params) throws InterruptedException
    {
        setOnHarvestRun(self, true);
        obj_id droid = params.getObjId("droid");
        obj_id player = params.getObjId("player");
        obj_id target = params.getObjId("target");
        if (pet_lib.isLowOnPower(self))
        {
            sendSystemMessage(player, new string_id(STF, "not_enough_power"));
            setOnHarvestRun(droid, false);
            return SCRIPT_CONTINUE;
        }
        if (player != getMaster(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target) || !target.isLoaded())
        {
            setOnHarvestRun(droid, false);
            sendSystemMessage(player, SID_INVALID_HARVEST_TARGET);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(target, "harvestedBy." + player))
        {
            setOnHarvestRun(droid, false);
            sendSystemMessage(player, SID_ALREADY_HARVESTED);
            return SCRIPT_CONTINUE;
        }
        boolean canOpen = false;
        if (hasObjVar(target, permissions.VAR_PERMISSION_BASE))
        {
            setOnHarvestRun(droid, false);
            canOpen = permissions.hasPermission(target, player, permissions.canOpen);
        }
        if (false)
        {
            sendSystemMessageTestingOnly(player, "Cannot access corpse " + getName(target));
            sendSystemMessage(player, SID_CANNOT_ACCESS_CORPSE);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(target, corpse.VAR_HAS_RESOURCE))
        {
            setOnHarvestRun(droid, false);
            sendSystemMessage(player, SID_NO_RESOURCES_TO_HARVEST);
            return SCRIPT_CONTINUE;
        }
        int harvestInterest = INTEREST_RANDOM;
        if (hasObjVar(droid, HARVEST_INTEREST))
        {
            harvestInterest = getIntObjVar(droid, HARVEST_INTEREST);
        }
        else 
        {
            setObjVar(droid, HARVEST_INTEREST, harvestInterest);
        }
        location targetLoc = getLocation(target);
        location currentLoc = getLocation(player);
        if (getDistance(targetLoc, currentLoc) > MAX_HARVEST_DISTANCE)
        {
            setOnHarvestRun(droid, false);
            sendSystemMessage(player, SID_CORPSE_TOO_FAR);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(droid, TARGET, target);
        utils.setScriptVar(droid, OWNER, player);
        utils.setScriptVar(droid, HARVEST, harvestInterest);
        setMovementRun(droid);
        pathTo(droid, targetLoc);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "runHarvestRoutineOverride", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int runHarvestRoutineOverride(obj_id self, dictionary params) throws InterruptedException
    {
        setOnHarvestRun(self, false);
        messageTo(self, "runHarvestRoutine", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int runHarvestRoutine(obj_id self, dictionary params) throws InterruptedException
    {
        if (!autoHarvestEnabled(self) || onHarvestRun(self) || ai_lib.isInCombat(self) || pet_lib.isLowOnPower(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, pet_lib.DROID_HARVEST_ARRAY))
        {
            Vector toHarvest = utils.getResizeableObjIdArrayScriptVar(self, pet_lib.DROID_HARVEST_ARRAY);
            utils.removeScriptVar(self, pet_lib.DROID_HARVEST_ARRAY);
            if (toHarvest == null || toHarvest.size() == 0)
            {
                return SCRIPT_CONTINUE;
            }
            while (0 < toHarvest.size())
            {
                if (isIdValid(((obj_id)toHarvest.get(0))) && exists(((obj_id)toHarvest.get(0))) && canSee(self, ((obj_id)toHarvest.get(0))) && hasObjVar(((obj_id)toHarvest.get(0)), corpse.VAR_HAS_RESOURCE) && !utils.hasScriptVar(((obj_id)toHarvest.get(0)), "harvestedBy." + getMaster(self)))
                {
                    dictionary dict = new dictionary();
                    dict.put("droid", self);
                    dict.put("player", pet_lib.getMaster(self));
                    dict.put("target", ((obj_id)toHarvest.get(0)));
                    utils.removeElement(toHarvest, ((obj_id)toHarvest.get(0)));
                    messageTo(self, "doTargetHarvest", dict, 0.0f, false);
                    break;
                }
                utils.removeElement(toHarvest, ((obj_id)toHarvest.get(0)));
            }
            if (toHarvest != null && toHarvest.size() > 0)
            {
                utils.setScriptVar(self, pet_lib.DROID_HARVEST_ARRAY, toHarvest);
            }
            else 
            {
                utils.removeScriptVar(self, pet_lib.DROID_HARVEST_ARRAY);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean onHarvestRun(obj_id droid) throws InterruptedException
    {
        return utils.hasScriptVar(droid, "droid_harvest.isActive") ? utils.getBooleanScriptVar(droid, "droid_harvest.isActive") : false;
    }
    public void setOnHarvestRun(obj_id droid, boolean state) throws InterruptedException
    {
        utils.setScriptVar(droid, "droid_harvest.isActive", state);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        obj_id droid = getSelf();
        if (!utils.hasScriptVar(droid, TARGET) || !utils.hasScriptVar(droid, OWNER))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = utils.getObjIdScriptVar(droid, OWNER);
        obj_id target = utils.getObjIdScriptVar(droid, TARGET);
        if (!isIdValid(player) || !isIdValid(target))
        {
            setOnHarvestRun(droid, false);
            return SCRIPT_CONTINUE;
        }
        int harvestInterest = INTEREST_RANDOM;
        if (utils.hasScriptVar(droid, HARVEST))
        {
            harvestInterest = utils.getIntScriptVar(droid, HARVEST);
        }
        if (!hasObjVar(target, corpse.VAR_HAS_RESOURCE))
        {
            setOnHarvestRun(self, false);
            return SCRIPT_CONTINUE;
        }
        int[] hasResource = getIntArrayObjVar(target, corpse.VAR_HAS_RESOURCE);
        if (hasResource.length == 0)
        {
            setOnHarvestRun(droid, false);
            return SCRIPT_CONTINUE;
        }
        if (!harvestRandom(droid, player, target, harvestInterest, hasResource))
        {
            if (hasResource[INTEREST_MEAT] > 0 && harvestInterest == INTEREST_MEAT)
            {
                harvestCreature(droid, player, target, INTEREST_MEAT);
            }
            else if (hasResource[INTEREST_HIDE] > 0 && harvestInterest == INTEREST_HIDE)
            {
                harvestCreature(droid, player, target, INTEREST_HIDE);
            }
            else if (hasResource[INTEREST_BONE] > 0 && harvestInterest == INTEREST_BONE)
            {
                harvestCreature(droid, player, target, INTEREST_BONE);
            }
            else 
            {
                sendSystemMessage(player, SID_TARGET_TYPE_NOT_FOUND);
            }
        }
        utils.removeScriptVar(droid, OWNER);
        utils.removeScriptVar(droid, TARGET);
        if (utils.hasScriptVar(droid, pet_lib.DROID_HARVEST_ARRAY) && autoHarvestEnabled(droid))
        {
            setOnHarvestRun(droid, false);
            messageTo(self, "runHarvestRoutine", null, 0.0f, false);
        }
        else 
        {
            setOnHarvestRun(droid, false);
            ai_lib.aiFollow(droid, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void setHarvestInterest(obj_id self, obj_id player) throws InterruptedException
    {
        String[] harvestInterest = new String[4];
        harvestInterest[0] = "@" + SID_SET_INTEREST_RANDOM;
        harvestInterest[1] = "@" + SID_SET_INTEREST_MEAT;
        harvestInterest[2] = "@" + SID_SET_INTEREST_HIDE;
        harvestInterest[3] = "@" + SID_SET_INTEREST_BONE;
        sui.listbox(self, player, "@" + SET_INTEREST_SUI_D, sui.OK_CANCEL, "@" + SET_INTEREST_SUI_T, harvestInterest, "handleSetHarvestInterest", true);
    }
    public int handleSetHarvestInterest(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        if (idx < 0)
        {
            idx = 0;
        }
        if (idx == 0)
        {
            sendSystemMessage(player, SID_INTEREST_SET_RANDOM);
            idx = INTEREST_RANDOM;
        }
        if (idx == 1)
        {
            sendSystemMessage(player, SID_INTEREST_SET_MEAT);
            idx = INTEREST_MEAT;
        }
        if (idx == 2)
        {
            sendSystemMessage(player, SID_INTEREST_SET_HIDE);
            idx = INTEREST_HIDE;
        }
        if (idx == 3)
        {
            sendSystemMessage(player, SID_INTEREST_SET_BONE);
            idx = INTEREST_BONE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, HARVEST_INTEREST, idx);
        obj_id controlDevice = callable.getCallableCD(self);
        copyObjVar(self, controlDevice, HARVEST_INTEREST);
        return SCRIPT_CONTINUE;
    }
    public void toggleAutoHarvest(obj_id droid) throws InterruptedException
    {
        boolean autoHarvest = autoHarvestEnabled(droid);
        if (autoHarvest)
        {
            sendSystemMessage(getMaster(droid), SID_DISABLE_AUTO_HARVEST);
            setObjVar(droid, AUTO_HARVEST, false);
        }
        else 
        {
            sendSystemMessage(getMaster(droid), SID_ENABLE_AUTO_HARVEST);
            setObjVar(droid, AUTO_HARVEST, true);
        }
        obj_id controlDevice = callable.getCallableCD(droid);
        copyObjVar(droid, controlDevice, AUTO_HARVEST);
    }
    public boolean autoHarvestEnabled(obj_id self) throws InterruptedException
    {
        return hasObjVar(self, AUTO_HARVEST) ? getBooleanObjVar(self, AUTO_HARVEST) : false;
    }
    public boolean harvestRandom(obj_id droid, obj_id player, obj_id corpse, int interest, int[] hasResource) throws InterruptedException
    {
        if (interest != INTEREST_RANDOM)
        {
            return false;
        }
        int[] randType = new int[3];
        int i = -1;
        if (hasResource[INTEREST_MEAT] > 0)
        {
            i++;
            randType[i] = INTEREST_MEAT;
        }
        if (hasResource[INTEREST_HIDE] > 0)
        {
            i++;
            randType[i] = INTEREST_HIDE;
        }
        if (hasResource[INTEREST_BONE] > 0)
        {
            i++;
            randType[i] = INTEREST_BONE;
        }
        if (i == -1)
        {
            return false;
        }
        int j = rand(0, i);
        int type = randType[j];
        if (!harvestCreature(droid, player, corpse, type))
        {
            return false;
        }
        return true;
    }
    public boolean harvestCreature(obj_id droid, obj_id player, obj_id target, int interest) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            setOnHarvestRun(droid, false);
            return false;
        }
        if (!hasObjVar(target, corpse.VAR_HAS_RESOURCE))
        {
            setOnHarvestRun(droid, false);
            return false;
        }
        if (utils.hasScriptVar(target, "harvestedBy." + player))
        {
            setOnHarvestRun(droid, false);
            return false;
        }
        if (getDistance(getLocation(droid), getLocation(target)) > MAX_HARVEST_DISTANCE)
        {
            setOnHarvestRun(droid, false);
            return false;
        }
        utils.setScriptVar(target, "harvestedBy." + player, 1);
        String mobType = ai_lib.getCreatureName(target);
        int droidSklMod = 0;
        if (hasObjVar(droid, "module_data.harvest_power"))
        {
            droidSklMod = getIntObjVar(droid, "module_data.harvest_power");
            droidSklMod = calculateDroidBonus(droidSklMod);
        }
        int playerSklMod = getSkillStatMod(player, "creature_harvesting");
        int sklMod = playerSklMod + droidSklMod;
        float bonusHarvest = sklMod / 100.0f;
        float skillEfficiency = ((sklMod * 10f) + 500f) / 2000f;
        doLogging("xx", "droid skill mod: " + droidSklMod + " player skill mod: " + playerSklMod + " calculated sklMod: " + sklMod);
        if (sklMod < 1)
        {
            sklMod = 1;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id cInv = utils.getInventoryContainer(target);
        obj_id dInv = utils.getDroidInventoryContainer(droid);
        obj_id tInv = pInv;
        if (isIdValid(dInv) && getFilledVolume(dInv) < getTotalVolume(dInv))
        {
            tInv = dInv;
        }
        if (!isIdValid(pInv) || !isIdValid(cInv))
        {
            setOnHarvestRun(droid, false);
            return false;
        }
        boolean baby = false;
        if (hasScript(target, "ai.pet_advance"))
        {
            baby = true;
        }
        int harvestXP = 0;
        int successCount = 0;
        int amt = 0;
        String choice = null;
        String qtyChoice = null;
        if (interest == INTEREST_MEAT)
        {
            choice = dataTableGetString(DATATABLE, mobType, MEAT);
            qtyChoice = "meat";
        }
        else if (interest == INTEREST_HIDE)
        {
            choice = dataTableGetString(DATATABLE, mobType, HIDE);
            qtyChoice = "hide";
        }
        else if (interest == INTEREST_BONE)
        {
            choice = dataTableGetString(DATATABLE, mobType, BONE);
            qtyChoice = "bone";
        }
        if (choice == null)
        {
            return false;
        }
        amt = (int)(Math.pow(ai_lib.getLevel(target), 1.69) * 0.4f + 2.f);
        if (amt < 1)
        {
            amt = 1;
        }
        if (amt > 700)
        {
            amt = 700;
        }
        amt += amt * bonusHarvest;
        amt = (int)(amt * skillEfficiency);
        if (baby)
        {
            amt *= 0.1;
        }
        if (group.isGrouped(player))
        {
            boolean petsOnly = true;
            obj_id[] groupMembers = getGroupMemberIds(getGroupObject(player));
            for (int i = 0; i < groupMembers.length; i++)
            {
                if (!hasScript(groupMembers[i], "ai.pet") && groupMembers[i] != player)
                {
                    petsOnly = false;
                }
            }
            if (petsOnly == false)
            {
                amt *= 1.2;
            }
        }
        if (amt <= 0)
        {
            amt = 1;
        }
        String sceneName = getCurrentSceneName();
        String rsrcMapTable = "datatables/creature_resource/resource_scene_map.iff";
        String correctedPlanetName = dataTableGetString(rsrcMapTable, sceneName, 1);
        if (correctedPlanetName == null || correctedPlanetName.equals(""))
        {
            correctedPlanetName = "tatooine";
        }
        String type = choice + "_" + correctedPlanetName;
        int actualAmount = 0;
        boolean check = false;
        if (amt > 0)
        {
            actualAmount = corpse.extractCorpseResource(type, amt, getLocation(target), tInv, cInv, 1);
            if (actualAmount > 0)
            {
                harvestXP = utils.getIntScriptVar(target, "corpse.harvestXP." + player);
                check = true;
            }
        }
        if (check == true)
        {
            type = resource.getResourceName(type);
            String msg = null;
            prose_package pp = null;
            pp = prose.getPackage(SID_DROID_HARVEST_MESSAGE, null, type, null, null, null, null, null, null, null, actualAmount, 0.f);
            sendSystemMessageProse(player, pp);
        }
        messageTo(target, corpse.HANDLER_CORPSE_EMPTY, null, 1f, isObjectPersisted(target));
        return true;
    }
    public int calculateDroidBonus(int droidSkillMod) throws InterruptedException
    {
        double remainder = 0;
        double bonusSkill = 0.0f;
        double mod = (double)droidSkillMod;
        double tier1 = 33;
        double tier2 = 47;
        double tier3 = 50;
        if (droidSkillMod > tier1)
        {
            remainder = droidSkillMod - tier1;
            bonusSkill = 20.0f;
        }
        else 
        {
            return (int)(20.0f * (droidSkillMod / tier1));
        }
        if (remainder > tier2)
        {
            remainder = remainder - tier2;
            bonusSkill = 25.0f;
        }
        else 
        {
            return (int)(15.0f + (5.0f * (remainder / tier2)));
        }
        if (remainder == tier3)
        {
            bonusSkill = 25.0f;
        }
        else 
        {
            return (int)(25.0f + (5.0f * (remainder / tier3)));
        }
        return (int)bonusSkill;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        LOG("doLogging/harvest_module/" + section, message);
    }
}
