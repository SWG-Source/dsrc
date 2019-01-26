package script.item.publish_gift;

import script.*;
import script.library.*;

import java.util.Arrays;

public class holopet_emitter extends script.base_script
{
    public holopet_emitter()
    {
    }
    public static final string_id NOT_IN_INVENTORY = new string_id("spam", "not_in_inventory");
    public static final float HEARTBEAT = 600.0f;
    public static final String PID_NAME = "holo-pet";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            obj_id house = getTopMostContainer(self);
            if (isIdValid(house) && player_structure.isBuilding(house))
            {
                if (!hasScript(house, "structure.hologram_control"))
                {
                    attachScript(house, "structure.hologram_control");
                }
                listenToMessage(house, "handleActivateHolograms");
                obj_id[] players = player_structure.getPlayersInBuilding(house);
                if (players != null && players.length > 0)
                {
                    if (hasObjVar(self, "holopet_active") && !isTurnedOn(self))
                    {
                        turnOn(self, null, "");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isValidId(structure) || !exists(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isBuilding(structure) && !isPlayer(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != owner && !player_structure.isAdmin(structure, player))
        {
            return SCRIPT_CONTINUE;
        }
        if ((getTopMostContainer(player) != getTopMostContainer(self)) || (getDistance(player, self) > 7.0f))
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            if (isTurnedOn(self))
            {
                mid.setLabel(new string_id("publish_gift/holopet", "menu_turn_off"));
            }
            else 
            {
                mid.setLabel(new string_id("publish_gift/holopet", "menu_turn_on"));
            }
            mid.setServerNotify(true);
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("publish_gift/holopet", "menu_load_disk"));
        if (hasObjVar(self, "holopet_loaded"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, new string_id("publish_gift/holopet", "menu_unload_disk"));
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU3, new string_id("publish_gift/holopet", "menu_name_pet"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isValidId(structure) || !exists(structure))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player) && getDistance(player, self) > 7.0f)
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "too_far"));
            return SCRIPT_CONTINUE;
        }
        if (getTopMostContainer(player) != getTopMostContainer(self))
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "must_be_inside"));
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isBuilding(structure) && !isPlayer(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != owner && !player_structure.isAdmin(structure, player))
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "cannot_activate_not_owner"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (utils.isNestedWithin(self, pInv))
            {
                sendSystemMessage(player, new string_id("publish_gift/holopet", "cannot_activate_in_inv"));
                return SCRIPT_CONTINUE;
            }
            if (isTurnedOn(self))
            {
                turnOff(self, player);
            }
            else 
            {
                turnOn(self, player, "");
            }
            sendDirtyObjectMenuNotification(self);
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            displayLoadDataCubeSui(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            unloadDataCube(self, player, true);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            showNamePetSui(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isTurnedOn(self))
        {
            turnOff(self, transferer);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (isTurnedOn(self))
        {
            turnOff(self, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(destContainer))
        {
            obj_id house = getTopMostContainer(sourceContainer);
            if (isIdValid(house) && player_structure.isBuilding(house))
            {
                stopListeningToMessage(house, "handleActivateHolograms");
            }
            if (transferer != owner)
            {
                CustomerServiceLog("publish_gift", "Player: " + getName(transferer) + " OID: " + transferer + " has picked up " + getName(self) + " OID: " + self + " from house:" + house);
            }
        }
        else 
        {
            obj_id house = getTopMostContainer(destContainer);
            if (isIdValid(house) && player_structure.isBuilding(house))
            {
                if (!hasScript(house, "structure.hologram_control"))
                {
                    attachScript(house, "structure.hologram_control");
                }
                listenToMessage(house, "handleActivateHolograms");
            }
            if (transferer != owner)
            {
                CustomerServiceLog("publish_gift", "Player: " + getName(transferer) + " OID: " + transferer + " has dropped " + getName(self) + " OID: " + self + " in house:" + house);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void turnOn(obj_id self, obj_id player, String type) throws InterruptedException
    {
        if (type.equals(""))
        {
            if (!hasObjVar(self, "holopet_loaded"))
            {
                if (isIdValid(player))
                {
                    sendSystemMessage(player, new string_id("publish_gift/holopet", "no_cube_loaded"));
                }
                return;
            }
            else 
            {
                type = getStringObjVar(self, "holopet_loaded");
            }
        }
        if (type == null || type.equals(""))
        {
            return;
        }
        dictionary holopetDataDict = new dictionary();
        holopetDataDict.put("template", "");
        holopetDataDict.put("sidName", new string_id("publish_gift/holopet", "default_name"));
        holopetDataDict.put("scale", 1.0f);
        holopetDataDict.put("speed", 1.01f);
        holopetDataDict.put("hologramQuality", HOLOGRAM_TYPE1_QUALITY3);
        if (type.startsWith("bm_"))
        {
            holopetDataDict = getBeastHolopetData(type, self);
        }
        else 
        {
            holopetDataDict = getVeteranRewardHolopetData(type);
        }
        if (holopetDataDict == null || holopetDataDict.isEmpty())
        {
            return;
        }
        String template = holopetDataDict.getString("template");
        string_id name = holopetDataDict.getStringId("sidName");
        float scale = holopetDataDict.getFloat("scale");
        float speed = holopetDataDict.getFloat("speed");
        int hologramQuality = holopetDataDict.getInt("hologramQuality");
        String holobeastName = "";
        if (holopetDataDict.containsKey("holobeastName"))
        {
            holobeastName = holopetDataDict.getString("holobeastName");
        }
        location myLoc = getLocation(self);
        location newLoc = new location();
        for (int i = 0; i < 50; i++)
        {
            newLoc = (location)myLoc.clone();
            newLoc = utils.getRandomAwayLocation(newLoc, 1.0f, 1.5f);
            if (isValidInteriorLocation(newLoc))
            {
                break;
            }
            else if (i == 49)
            {
                newLoc = (location)myLoc.clone();
            }
        }
        obj_id pet = createObject(template, newLoc);
        if (isIdValid(pet))
        {
            int calendarTime = getCalendarTime();
            String realTime = getCalendarTimeStringLocal(calendarTime);
            CustomerServiceLog("holo-pet", "player " + getFirstName(player) + " (" + player + ") has TURNED ON a holo-pet emitter and created pet(" + pet + ") at " + realTime);
            setYaw(pet, rand(0, 359));
            setScale(pet, scale);
            setHologramType(pet, hologramQuality);
            setInvulnerable(pet, true);
            setBaseWalkSpeed(pet, speed);
            if (hasObjVar(self, "holopet_name"))
            {
                String custom_name = getStringObjVar(self, "holopet_name");
                if (custom_name != null && !custom_name.equals(""))
                {
                    setName(pet, custom_name);
                }
                else 
                {
                    setDefaultHolopetName(pet, name, holobeastName);
                }
            }
            else 
            {
                setDefaultHolopetName(pet, name, holobeastName);
            }
            setDescriptionStringId(pet, new string_id("publish_gift/holopet", "default_description"));
            attachScript(pet, "item.special.block_open");
            final float collisionRadius = getObjectCollisionRadius(pet);
            final float minDelay = Math.min(Math.max(8.0f, collisionRadius * 2.0f), 16.0f);
            final float maxDelay = Math.min(Math.max(8.0f, collisionRadius * 4.0f), 16.0f);
            loiterLocation(pet, getLocation(self), 1.0f, 6.0f, minDelay, maxDelay);
            setObjVar(pet, "ai.defaultCalmBehavior", ai_lib.BEHAVIOR_LOITER);
            setObjVar(self, "holopet_active", pet);
            setObjVar(self, "unmoveable", 1);
            setCondition(self, CONDITION_ON);
            int msgId = getGameTime();
            setObjVar(self, "holopet_msgId", msgId);
            dictionary d = new dictionary();
            d.put("msgId", msgId);
            messageTo(self, "handleHologramHeartbeat", d, HEARTBEAT, false);
        }
    }
    public dictionary getBeastHolopetData(String type, obj_id self) throws InterruptedException
    {
        String template = "";
        string_id name = new string_id("publish_gift/holopet", "default_name");
        float scale = 1.0f;
        float speed = 1.01f;
        int hologramQuality = beast_lib.HOLO_BEAST_TYPE2_QUALITY3;
        if (hasObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR))
        {
            hologramQuality = getIntObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR);
        }
        dictionary beastTableData = utils.dataTableGetRow(beast_lib.BEASTS_TABLE, type);
        template = beast_lib.MOBILE_TEMPLATE_PREFIX + beastTableData.getString("template");
        scale = beastTableData.getFloat("minScale");
        String holobeastName = beast_lib.localizedHoloBeastBaseName(type) + "Holo-beast";
        dictionary holobeastDataDict = new dictionary();
        holobeastDataDict.put("template", template);
        holobeastDataDict.put("sidName", name);
        holobeastDataDict.put("holobeastName", holobeastName);
        holobeastDataDict.put("scale", scale);
        holobeastDataDict.put("speed", speed);
        holobeastDataDict.put("hologramQuality", hologramQuality);
        return holobeastDataDict;
    }
    public dictionary getVeteranRewardHolopetData(String type) throws InterruptedException
    {
        String template = "";
        string_id name = new string_id("publish_gift/holopet", "default_name");
        float scale = 1.0f;
        float speed = 1.01f;
        int hologramQuality = HOLOGRAM_TYPE1_QUALITY3;
        switch (type) {
            case "rancor":
                template = "object/mobile/hologram/rancor.iff";
                name = new string_id("publish_gift/holopet", "rancor_name");
                scale = 0.1f;
                break;
            case "quenker":
                template = "object/mobile/hologram/quenker.iff";
                name = new string_id("publish_gift/holopet", "quenker_name");
                scale = 0.5f;
                break;
            case "veermok":
                template = "object/mobile/hologram/veermok.iff";
                name = new string_id("publish_gift/holopet", "veermok_name");
                scale = 0.4f;
                break;
            case "slicehound":
                template = "object/mobile/hologram/corellian_slice_hound.iff";
                name = new string_id("publish_gift/holopet", "slice_hound_name");
                scale = 0.5f;
                break;
            case "pekopeko":
                template = "object/mobile/hologram/peko_peko.iff";
                name = new string_id("publish_gift/holopet", "peko_peko_name");
                scale = 0.2f;
                speed = 7.0f;
                break;
            case "squall":
                template = "object/mobile/hologram/squall.iff";
                name = new string_id("publish_gift/holopet", "squall_name");
                scale = 1.0f;
                break;
            case "jax":
                template = "object/mobile/hologram/bearded_jax.iff";
                name = new string_id("publish_gift/holopet", "jax_name");
                scale = 1.0f;
                break;
            case "ewok":
                template = "object/mobile/hologram/ewok.iff";
                name = new string_id("publish_gift/holopet", "ewok_name");
                scale = 0.5f;
                break;
            case "jawa":
                template = "object/mobile/hologram/jawa.iff";
                name = new string_id("publish_gift/holopet", "jawa_name");
                scale = 0.8f;
                break;
            case "atat":
                template = "object/mobile/hologram/atat.iff";
                name = new string_id("publish_gift/holopet", "atat_name");
                scale = 0.04f;
                break;
            case "cle004":
                template = "object/mobile/hologram/cle004.iff";
                name = new string_id("publish_gift/holopet", "cle004_name");
                scale = 0.4f;
                break;
            case "ins444":
                template = "object/mobile/hologram/ins444.iff";
                name = new string_id("publish_gift/holopet", "ins444_name");
                scale = 0.6f;
                break;
            case "hoth_probe_droid":
                template = "object/mobile/hologram/probe_droid.iff";
                name = new string_id("publish_gift/holopet", "probe_droid_name");
                scale = 0.2f;
                break;
            case "hoth_tauntaun":
                template = "object/mobile/hologram/tauntaun.iff";
                name = new string_id("publish_gift/holopet", "tauntaun_name");
                scale = 0.3f;
                break;
        }
        dictionary vetDataDict = new dictionary();
        vetDataDict.put("template", template);
        vetDataDict.put("sidName", name);
        vetDataDict.put("scale", scale);
        vetDataDict.put("speed", speed);
        vetDataDict.put("hologramQuality", hologramQuality);
        return vetDataDict;
    }
    public void setDefaultHolopetName(obj_id pet, string_id name, String holobeastName) throws InterruptedException
    {
        if (holobeastName != null && holobeastName.length() > 0)
        {
            setName(pet, holobeastName);
        }
        else 
        {
            setName(pet, name);
        }
        return;
    }
    public void turnOff(obj_id self, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(self, "holopet_active"))
        {
            return;
        }
        obj_id pet = getObjIdObjVar(self, "holopet_active");
        if (isIdValid(pet) && exists(pet))
        {
            destroyObject(pet);
        }
        removeObjVar(self, "holopet_active");
        removeObjVar(self, "unmoveable");
        int calendarTime = getCalendarTime();
        String realTime = getCalendarTimeStringLocal(calendarTime);
        if (isIdValid(player) && isPlayer(player))
        {
            CustomerServiceLog("holo-pet", "player " + getFirstName(player) + " (" + player + ") has TURNED OFF a holo-pet emitter at " + realTime);
        }
        else if (player == self && !isPlayer(self))
        {
            CustomerServiceLog("holo-pet", "holo-pet was TURNED OFF due to the Holo-Pet Emitter (" + self + ") " + "destroyed by the 'Destroy All Items In House' Call at " + realTime);
        }
        else 
        {
            CustomerServiceLog("holo-pet", "a holo-pet emitter has been TURNED OFF, but the players ObjId was null, most likely this was done at house packup, at " + realTime);
        }
        clearCondition(self, CONDITION_ON);
    }
    public void showNamePetSui(obj_id self, obj_id player) throws InterruptedException
    {
        String title = "@publish_gift/holopet:sui_name_pet_title";
        String prompt = "@publish_gift/holopet:sui_name_pet_prompt";
        int pid = sui.inputbox(self, player, prompt, title, "handleNamePet", 20, false, "");
    }
    public boolean isTurnedOn(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "holopet_active"))
        {
            return false;
        }
        obj_id pet = getObjIdObjVar(self, "holopet_active");
        if (!isIdValid(pet) || !exists(pet))
        {
            return false;
        }
        return true;
    }
    public boolean unloadDataCube(obj_id self, obj_id player, boolean verbose) throws InterruptedException
    {
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        if (!hasObjVar(self, "holopet_loaded"))
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "no_cube_loaded"));
            return false;
        }
        String loadedHolopetType = getStringObjVar(self, "holopet_loaded");
        obj_id cube = obj_id.NULL_ID;
        if (loadedHolopetType.startsWith("bm_"))
        {
            cube = beast_lib.createBeastHolopet(player, self, loadedHolopetType);
            if (hasObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER))
            {
                removeObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER);
            }
        }
        else 
        {
            String loaded_cube = "item_holopet_";
            loaded_cube += loadedHolopetType;
            loaded_cube += "_data_cube_01_01";
            obj_id inv = utils.getInventoryContainer(player);
            if (!isIdValid(inv))
            {
                return false;
            }
            cube = static_item.createNewItemFunction(loaded_cube, inv);
        }
        if (isIdValid(cube))
        {
            if (hasObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR))
            {
                int holocubeColor = getIntObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR);
                if (holocubeColor > -1 && holocubeColor < beast_lib.HOLOGRAM_MAX_SETTING)
                {
                    setObjVar(cube, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR, holocubeColor);
                }
                removeObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR);
            }
            if (verbose)
            {
                sendSystemMessage(player, new string_id("publish_gift/holopet", "data_cube_unloaded"));
            }
            if (isTurnedOn(self))
            {
                turnOff(self, player);
            }
            removeObjVar(self, "holopet_loaded");
            if (player != owner)
            {
                CustomerServiceLog("publish_gift", "Player: " + getName(player) + " OID: " + player + " has Unloaded a pet (OID: " + cube + ") from " + getName(self) + " OID: " + self + ".");
            }
            return true;
        }
        return false;
    }
    public void displayLoadDataCubeSui(obj_id self, obj_id player) throws InterruptedException
    {
        if (sui.hasPid(player, PID_NAME))
        {
            int pid = sui.getPid(player, PID_NAME);
            forceCloseSUIPage(pid);
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id[] data_cubes = utils.getContainedObjectsWithObjVar(inv, "holopet_type", true);
        if (data_cubes == null || data_cubes.length == 0)
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "no_data_cubes"));
            return;
        }
        String[] sortedArray = new String[data_cubes.length];
        String[] comparisonArray = new String[data_cubes.length];
        obj_id[] final_data_cubes = new obj_id[data_cubes.length];
        for (int k = 0; k < data_cubes.length; k++)
        {
            String sortedType = "";
            String tempName = getName(data_cubes[k]);
            if (tempName.startsWith("static_item"))
            {
                String[] parse = split(tempName, ':');
                tempName = localize(new string_id(parse[0], parse[1]));
            }
            String[] splitName = split(tempName, ' ');
            for (String s : splitName) {
                if (!s.startsWith("Holo-")) {
                    sortedType += s;
                } else {
                    break;
                }
            }
            sortedArray[k] = sortedType;
            comparisonArray[k] = sortedType;
        }
        Arrays.sort(sortedArray);
        for (int j = 0; j < sortedArray.length; j++)
        {
            String sortData = sortedArray[j];
            int index = utils.getElementPositionInArray(comparisonArray, sortData);
            obj_id cube = data_cubes[index];
            final_data_cubes[j] = cube;
            comparisonArray[index] = "_";
        }
        String[] cube_names = new String[final_data_cubes.length];
        for (int i = 0; i < final_data_cubes.length; i++)
        {
            String holopetType = getStringObjVar(final_data_cubes[i], "holopet_type");
            if (holopetType.startsWith("bm_"))
            {
                cube_names[i] = getName(final_data_cubes[i]);
            }
            else 
            {
                cube_names[i] = "@" + getName(final_data_cubes[i]);
            }
        }
        String title = "@publish_gift/holopet:sui_cube_list_title";
        String prompt = "";
        if (hasObjVar(self, "holopet_loaded"))
        {
            prompt = "@publish_gift/holopet:sui_cube_list_prompt1 ";
            String loadedCubeType = getStringObjVar(self, "holopet_loaded");
            if (loadedCubeType.startsWith("bm_"))
            {
                prompt += beast_lib.localizedHoloBeastBaseName(loadedCubeType) + "Holo-beast Data Cube";
            }
            else 
            {
                String loaded_cube = "item_holopet_";
                loaded_cube += loadedCubeType;
                loaded_cube += "_data_cube_01_01";
                prompt += "@static_item_n:" + loaded_cube;
            }
            prompt += " \n\n";
        }
        prompt += "@publish_gift/holopet:sui_cube_list_prompt2";
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, cube_names, "handleLoadDataCube", false, false);
        if (pid > 0)
        {
            sui.setAssociateRange(player, pid, self, 10.0f);
            showSUIPage(pid);
            sui.setPid(player, pid, PID_NAME);
            utils.setScriptVar(player, "data_cubes", final_data_cubes);
        }
    }
    public int handleNamePet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        String name = sui.getInputBoxText(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (name == null || name.equals(""))
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "invalid_name"));
            return SCRIPT_CONTINUE;
        }
        if (!utils.isAppropriateName(name))
        {
            sendSystemMessage(player, new string_id("publish_gift/holopet", "invalid_name"));
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "holopet_name", name);
        if (hasObjVar(self, "holopet_active"))
        {
            obj_id pet = getObjIdObjVar(self, "holopet_active");
            if (isIdValid(pet) && exists(pet))
            {
                setName(pet, name);
            }
            if (player != owner)
            {
                CustomerServiceLog("publish_gift", "Player: " + getName(player) + " OID: " + player + " has renamed a pet on " + getName(self) + " OID: " + self + ".");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLoadDataCube(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int pageId = params.getInt("pageId");
        if (!isIdValid(player))
        {
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            forceCloseSUIPage(pageId);
            utils.removeScriptVar(player, "data_cubes");
            return SCRIPT_CONTINUE;
        }
        int playerPid = sui.getPid(player, PID_NAME);
        if (pageId != playerPid)
        {
            forceCloseSUIPage(pageId);
            forceCloseSUIPage(playerPid);
            sui.removePid(player, PID_NAME);
            utils.removeScriptVar(player, "data_cubes");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            utils.removeScriptVar(player, "data_cubes");
            return SCRIPT_CONTINUE;
        }
        obj_id[] data_cubes = utils.getObjIdArrayScriptVar(player, "data_cubes");
        if (data_cubes == null || data_cubes.length == 0)
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            utils.removeScriptVar(player, "data_cubes");
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(player, "data_cubes");
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > data_cubes.length)
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(data_cubes[idx]))
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(data_cubes[idx], "holopet_type"))
        {
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "holopet_loaded"))
        {
            if (!unloadDataCube(self, player, false))
            {
                sui.removePid(player, PID_NAME);
                forceCloseSUIPage(pageId);
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.getContainingPlayer(data_cubes[idx]) != player)
        {
            sendSystemMessage(player, NOT_IN_INVENTORY);
            sui.removePid(player, PID_NAME);
            forceCloseSUIPage(pageId);
            return SCRIPT_CONTINUE;
        }
        String type = getStringObjVar(data_cubes[idx], "holopet_type");
        setObjVar(self, "holopet_loaded", type);
        if (hasObjVar(data_cubes[idx], beast_lib.OBJVAR_BEAST_ENGINEER))
        {
            String creatorName = getStringObjVar(data_cubes[idx], beast_lib.OBJVAR_BEAST_ENGINEER);
            if (creatorName != null && creatorName.length() > 0)
            {
                setObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER, creatorName);
            }
        }
        if (hasObjVar(data_cubes[idx], beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR))
        {
            int holocubeColor = getIntObjVar(data_cubes[idx], beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR);
            if (holocubeColor > -1 && holocubeColor < beast_lib.HOLOGRAM_MAX_SETTING)
            {
                setObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR, holocubeColor);
            }
        }
        prose_package pp = new prose_package();
        if (type.startsWith("a") || type.startsWith("e") || type.startsWith("i") || type.startsWith("o") || type.startsWith("u"))
        {
            pp = prose.setStringId(pp, new string_id("publish_gift/holopet", "data_cube_loaded_an"));
        }
        else 
        {
            pp = prose.setStringId(pp, new string_id("publish_gift/holopet", "data_cube_loaded"));
        }
        if (type.startsWith("bm_"))
        {
            pp = prose.setTT(pp, getName(data_cubes[idx]));
        }
        else 
        {
            pp = prose.setTT(pp, "@" + getName(data_cubes[idx]));
        }
        sendSystemMessageProse(player, pp);
        destroyObject(data_cubes[idx]);
        turnOn(self, player, type);
        if (player != owner)
        {
            CustomerServiceLog("publish_gift", "Player: " + getName(player) + " OID: " + player + " has Loaded a new pet onto " + getName(self) + " OID: " + self + ".");
        }
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
    public int handleActivateHolograms(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "holopet_active") && !isTurnedOn(self))
        {
            turnOn(self, null, "");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleHologramHeartbeat(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "holopet_msgId"))
        {
            return SCRIPT_CONTINUE;
        }
        int id = getIntObjVar(self, "holopet_msgId");
        int msgId = params.getInt("msgId");
        if (id != msgId)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isTurnedOn(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id house = getTopMostContainer(self);
        if (isIdValid(house) && player_structure.isBuilding(house))
        {
            obj_id[] players = player_structure.getPlayersInBuilding(house);
            if (players == null || players.length == 0)
            {
                if (hasObjVar(self, "holopet_active"))
                {
                    obj_id pet = getObjIdObjVar(self, "holopet_active");
                    if (isIdValid(pet) && exists(pet))
                    {
                        destroyObject(pet);
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, "holopet_active"))
        {
            obj_id pet = getObjIdObjVar(self, "holopet_active");
            if (isIdValid(pet) && exists(pet))
            {
                if (rand(0, 1) == 0)
                {
                    doAnimationAction(pet, "trick_1");
                }
                else 
                {
                    doAnimationAction(pet, "trick_2");
                }
            }
        }
        dictionary d = new dictionary();
        d.put("msgId", msgId);
        messageTo(self, "handleHologramHeartbeat", d, HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isTurnedOn(self))
        {
            turnOff(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
