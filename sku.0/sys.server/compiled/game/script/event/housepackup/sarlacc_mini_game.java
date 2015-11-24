package script.event.housepackup;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.HashSet;
import script.library.collection;
import script.library.house_pet;
import script.library.smuggler;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class sarlacc_mini_game extends script.base_script
{
    public sarlacc_mini_game()
    {
    }
    public static final boolean BLOGGING_ON = true;
    public static final String BLOG_CATEGORY = "sarlacc";
    public static final string_id SID_MENU_FEED = new string_id("sarlacc_minigame", "mnu_feed");
    public static final string_id SID_MENU_REWARD = new string_id("sarlacc_minigame", "mnu_reward");
    public static final string_id SID_SIFT_SARLACC_BOX = new string_id("sarlacc_minigame", "must_sift_sarlacc_box");
    public static final string_id SID_SARLACC_FED = new string_id("sarlacc_minigame", "sarlacc_fed");
    public static final string_id SID_FOUND_REWARD = new string_id("sarlacc_minigame", "sifted_reward");
    public static final string_id SID_SARLACC_NOTHING_FOUND = new string_id("sarlacc_minigame", "no_junk_inventory");
    public static final String FEED_TITLE = "@sarlacc_minigame:edible_title";
    public static final String FEED_PROMPT = "@sarlacc_minigame:edible_prompt";
    public static final String BTN_SELL = "@sarlacc_minigame:btn_feed";
    public static final String SOUND_BURP = "sound/sarlacc_burp.snd";
    public static final String SOUND_EAT = "sound/sarlacc_eating.snd";
    public static final String SOUND_SIFT = "sound/sarlacc_drawer_open.snd";
    public static final String SOUND_CUTE = "sound/sarlacc_cute.snd";
    public static final String SOUND_BREATHING = "sound/sarlacc_breathing.snd";
    public static final String ANIMATON_GROWL = "vocalize";
    public static final String ANIMATON_FEED = "eat";
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        house_pet.stopPlayingMusic(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkTriggerVolume", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "checkCurrentBaseLocation", null, 0, false);
        if (player != getOwner(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, house_pet.SARLACC_REWARD_AVAILABLE))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_MENU_REWARD);
            return SCRIPT_CONTINUE;
        }
        else if (getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE) >= house_pet.SARLACC_HUNGRY)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_MENU_FEED);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        if (player != getOwner(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE) < house_pet.SARLACC_HUNGRY)
            {
                return SCRIPT_CONTINUE;
            }
            getEdibleContents(player, controller, self);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            getPlayerReward(player, controller);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, house_pet.SARLACC_CURRENT_PHASE))
        {
            int currentStatus = getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE);
            if (currentStatus < house_pet.SARLACC_FED || currentStatus > house_pet.SARLACC_DEATH)
            {
                blog("getSarlaccCollectionColumn currentStatus : " + currentStatus);
                return SCRIPT_CONTINUE;
            }
            names[idx] = "status";
            attribs[idx] = "@obj_attr_n:" + house_pet.SARLACC_PHASES[currentStatus];
            idx++;
            if (hasObjVar(controller, house_pet.SARLACC_LAST_FED))
            {
                int lastFed = getIntObjVar(controller, house_pet.SARLACC_LAST_FED);
                if (lastFed < 0)
                {
                    return SCRIPT_CONTINUE;
                }
                names[idx] = "last_fed";
                attribs[idx] = getCalendarTimeStringLocal(lastFed);
                idx++;
                if (isGod(player))
                {
                    names[idx] = "next_feed";
                    if (currentStatus == 0)
                    {
                        attribs[idx] = getCalendarTimeStringLocal(lastFed + house_pet.getUpdateWeekly(controller));
                    }
                    else if (currentStatus == 1)
                    {
                        attribs[idx] = "Now";
                    }
                    else 
                    {
                        attribs[idx] = "Hopefully Soon";
                    }
                    idx++;
                    names[idx] = "time_now";
                    attribs[idx] = getCalendarTimeStringLocal(getGameTime());
                    idx++;
                }
            }
            else 
            {
                names[idx] = "last_fed";
                attribs[idx] = "Never";
                idx++;
                names[idx] = "next_feed";
                attribs[idx] = "Hopefully Soon";
                idx++;
            }
        }
        if (hasObjVar(controller, house_pet.SARLACC_BORN))
        {
            int birthDay = getIntObjVar(controller, house_pet.SARLACC_BORN);
            if (birthDay <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "born";
            attribs[idx] = getCalendarTimeStringLocal(birthDay);
            idx++;
        }
        if (isValidId(getOwner(controller)))
        {
            obj_id owner = getOwner(controller);
            names[idx] = "owner";
            attribs[idx] = getPlayerFullName(owner);
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(breacher) || !isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(breacher, "breathe"))
        {
            if (utils.getObjIdScriptVar(breacher, "breathe") == self)
            {
                return SCRIPT_CONTINUE;
            }
        }
        utils.setScriptVar(breacher, "breathe", self);
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            house_pet.cleanUpHousePet(self);
            return SCRIPT_CONTINUE;
        }
        if (breacher == getOwner(controller))
        {
            dictionary params = new dictionary();
            params.put("player", breacher);
            messageTo(self, "playCute", params, 0, false);
        }
        messageTo(self, "breathing", null, 0, false);
        playClientEffectObj(breacher, SOUND_BREATHING, self, "", new transform(), house_pet.PET_SOUND_LABEL);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(breacher) || !isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        stopClientEffectObjByLabel(breacher, self, house_pet.PET_SOUND_LABEL);
        return SCRIPT_CONTINUE;
    }
    public int handleSarlaccEatSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("handleSarlaccEatSui - Init");
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx < 0)
        {
            cleanupSui(player);
            return SCRIPT_CONTINUE;
        }
        if (bp == sui.BP_CANCEL)
        {
            cleanupSui(player);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id[] junkFood = utils.getObjIdBatchScriptVar(player, house_pet.EDIBLES_IDS);
            if ((junkFood == null) || (junkFood.length == 0))
            {
                cleanupSui(player);
                return SCRIPT_CONTINUE;
            }
            if (idx > junkFood.length - 1)
            {
                cleanupSui(player);
                return SCRIPT_CONTINUE;
            }
            if (!eatJunkItem(self, player, junkFood[idx]))
            {
                CustomerServiceLog("sarlacc_minigame: ", "Player (" + getPlayerName(player) + " OID: " + player + ") FAILED TO FEED sarlacc: " + self + " item (" + getName(junkFood[idx]) + " OID: " + junkFood[idx] + ").");
            }
        }
        cleanupSui(player);
        return SCRIPT_CONTINUE;
    }
    public int playBurp(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        play2dNonLoopingSound(player, SOUND_BURP);
        doAnimationAction(self, ANIMATON_GROWL);
        return SCRIPT_CONTINUE;
    }
    public int playCute(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (params == null || params.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        play2dNonLoopingSound(player, SOUND_CUTE);
        doAnimationAction(self, ANIMATON_GROWL);
        return SCRIPT_CONTINUE;
    }
    public int checkTriggerVolume(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasTriggerVolume(self, house_pet.PET_TRIG_VOLUME))
        {
            float range = 2f;
            createTriggerVolume(house_pet.PET_TRIG_VOLUME, range, true);
            setObjVar(self, house_pet.MUSIC_RANGE, range);
        }
        return SCRIPT_CONTINUE;
    }
    public int updateSarlaccPet(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("updateSarlaccPet - RECEIVED MESSAGE TO - Current phase: " + getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE) + " time: " + getGameTime());
        if (!house_pet.validateNpcPlacementInStructure(self))
        {
            house_pet.cleanUpHousePet(controller, self);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(controller, house_pet.SARLACC_CURRENT_PHASE))
        {
            blog("updateSarlaccPet - no phase for sarlacc");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION))
        {
            blog("updateSarlaccPet - no feeding iteration for sarlacc");
            return SCRIPT_CONTINUE;
        }
        blog("updateSarlaccPet - Is in structure");
        int currentGameTime = getGameTime();
        int updateWeek = house_pet.getUpdateWeekly(controller);
        int updateDay = house_pet.getUpdateDaily(controller);
        int lastFed = getIntObjVar(controller, house_pet.SARLACC_LAST_FED);
        blog("updateSarlaccPet - lastFed: " + lastFed);
        if (currentGameTime < (lastFed + updateWeek))
        {
            blog("updateSarlaccPet - Last Fed Timer: " + (lastFed + updateWeek) + " currentGameTime: " + currentGameTime);
            if (hasMessageTo(self, "updateSarlaccPet"))
            {
                return SCRIPT_CONTINUE;
            }
            int later = ((lastFed + updateWeek) - currentGameTime);
            blog("updateSarlaccPet - messageTo in : " + later);
            if (later > 1)
            {
                blog("updateSarlaccPet - TOO SOOOOON messageTo in : " + later);
                messageTo(self, "updateSarlaccPet", null, later, false);
            }
            else 
            {
                blog("updateSarlaccPet - WRONG messageTo, Resending in : " + updateDay);
                messageTo(self, "updateSarlaccPet", null, updateDay, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (hasMessageTo(self, "updateSarlaccPet"))
        {
            return SCRIPT_CONTINUE;
        }
        blog("updateSarlaccPet - Is in structure");
        int currentIteration = getIntObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION);
        int currentPhase = getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE);
        if (currentPhase < house_pet.SARLACC_FED)
        {
            blog("updateSarlaccPet - currentPhase WAS -1");
            currentPhase = house_pet.SARLACC_FED;
        }
        currentPhase++;
        if (currentPhase >= house_pet.SARLACC_DEATH)
        {
            setObjVar(controller, house_pet.SARLACC_CURRENT_PHASE, house_pet.SARLACC_DEATH);
        }
        else 
        {
            setObjVar(controller, house_pet.SARLACC_CURRENT_PHASE, currentPhase);
        }
        if (currentPhase == house_pet.SARLACC_DEATH)
        {
            blog("updateSarlaccPet - SARLACC DEAD!!!!!!!!");
            if (!hasMessageTo(self, "updateSarlaccPet"))
            {
                blog("updateSarlaccPet - SARLACC_DEATH messageTo");
                messageTo(self, "updateSarlaccPet", null, updateWeek, false);
            }
            return SCRIPT_CONTINUE;
        }
        else if (currentPhase == house_pet.SARLACC_STARVING)
        {
            blog("updateSarlaccPet - SARLACC STARVING!!!!!!!!");
            setObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION, 0);
            removeObjVar(controller, house_pet.SARLACC_FEED_ARRAY);
            if (!hasMessageTo(self, "updateSarlaccPet"))
            {
                blog("updateSarlaccPet - SARLACC_STARVING messageTo");
                messageTo(self, "updateSarlaccPet", null, updateWeek, false);
            }
            return SCRIPT_CONTINUE;
        }
        else if (getIntObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION) >= house_pet.SARLACC_PHASE_WEEKS)
        {
            blog("updateSarlaccPet - ITERATION DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String collectionCol = getSarlaccCollectionColumn(controller);
            if (collectionCol == null || collectionCol.equals(""))
            {
                blog("updateSarlaccPet - getSarlaccCollectionColumn FAILED!");
                collectionCol = "generic_collectible";
            }
            blog("updateSarlaccPet - getSarlaccCollectionColumn: " + collectionCol);
            setObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION, 0);
            removeObjVar(controller, house_pet.SARLACC_FEED_ARRAY);
            setObjVar(controller, house_pet.SARLACC_REWARD_AVAILABLE, true);
            setObjVar(controller, house_pet.SARLACC_REWARD_COLUMN, collectionCol);
        }
        if (!hasMessageTo(self, "updateSarlaccPet"))
        {
            messageTo(self, "updateSarlaccPet", null, updateWeek, false);
            blog("updateSarlaccPet - messageTo end in one week");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean getEdibleContents(obj_id player, obj_id controller, obj_id sarlacc) throws InterruptedException
    {
        blog("getEdibleContents - Init");
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!isValidId(sarlacc) || !exists(sarlacc))
        {
            return false;
        }
        if (getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE) < house_pet.SARLACC_HUNGRY)
        {
            return false;
        }
        if (getBooleanObjVar(controller, house_pet.SARLACC_REWARD_AVAILABLE))
        {
            sendSystemMessage(player, SID_SIFT_SARLACC_BOX);
            return false;
        }
        blog("getEdibleContents - About to create array of junk");
        if (!checkPlayerInventoryForSarlaccFood(player))
        {
            sendSystemMessage(player, SID_SARLACC_NOTHING_FOUND);
            return false;
        }
        blog("getEdibleContents - Player has junk");
        obj_id[] edibleItems = smuggler.getAllJunkItems(player);
        if (edibleItems == null && edibleItems.length <= 0)
        {
            return false;
        }
        String[] edibleMenu = new String[edibleItems.length];
        for (int i = 0; i < edibleItems.length; i++)
        {
            String name = getAssignedName(edibleItems[i]);
            if (name.equals("") || name == null)
            {
                name = getString(getNameStringId(edibleItems[i]));
            }
            edibleMenu[i] = name;
        }
        if (edibleMenu == null && edibleMenu.length <= 0)
        {
            return false;
        }
        int pid = sui.listbox(sarlacc, player, FEED_PROMPT, sui.YES_NO, FEED_TITLE, edibleMenu, "handleSarlaccEatSui", false, false);
        if (pid > -1)
        {
            setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, BTN_SELL);
            showSUIPage(pid);
            utils.setScriptVar(player, house_pet.EDIBLES_SUI, pid);
            utils.setBatchScriptVar(player, house_pet.EDIBLES_IDS, edibleItems);
        }
        return true;
    }
    public boolean eatJunkItem(obj_id self, obj_id player, obj_id item) throws InterruptedException
    {
        blog("eatJunkItem - Init");
        if (!isIdValid(self) || !exists(self))
        {
            return false;
        }
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(item) || !exists(item))
        {
            return false;
        }
        if (utils.outOfRange(self, player, 10.0f, true))
        {
            return false;
        }
        if (!validateItemForSarlacc(player, item))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isIdValid(controller) || !exists(controller))
        {
            return false;
        }
        String playerName = getName(player);
        String itemName = getEncodedName(item);
        CustomerServiceLog("sarlacc_minigame: ", "Player (" + playerName + " OID: " + player + ") fed sarlacc item (" + itemName + " OID: " + item + ").");
        String templateName = getTemplateName(item);
        if (templateName == null || templateName.equals(""))
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.SARLACC_FEED_ARRAY))
        {
            blog("Received Item - Created new feed list");
            String[] feedingList = new String[1];
            feedingList[0] = templateName;
            setObjVar(controller, house_pet.SARLACC_FEED_ARRAY, feedingList);
        }
        else 
        {
            String[] feedingList = getStringArrayObjVar(controller, house_pet.SARLACC_FEED_ARRAY);
            if (feedingList == null || feedingList.length <= 0)
            {
                blog("Received Item - ERROR -NO PREVIOUS feedingList found, making new.");
                String[] newList = new String[1];
                newList[0] = templateName;
                setObjVar(controller, house_pet.SARLACC_FEED_ARRAY, newList);
            }
            else if (feedingList.length > 0 && feedingList.length < 4)
            {
                blog("Received Item - ARRAY COPY!!!!!!!!!!!!!!!!!!!!!!!!");
                blog("Received Item - feedingList.length " + feedingList.length);
                String[] newList = new String[feedingList.length + 1];
                System.arraycopy(feedingList, 0, newList, 0, feedingList.length);
                newList[newList.length - 1] = templateName;
                blog("Received Item - newList.length " + newList.length);
                setObjVar(controller, house_pet.SARLACC_FEED_ARRAY, newList);
            }
        }
        int currentIteration = getIntObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION);
        currentIteration++;
        setObjVar(controller, house_pet.SARLACC_FEEDING_ITERATION, currentIteration);
        blog("Received Item - setting phase fed and setting time fed");
        setObjVar(controller, house_pet.SARLACC_CURRENT_PHASE, house_pet.SARLACC_FED);
        play2dNonLoopingSound(player, SOUND_EAT);
        doAnimationAction(self, ANIMATON_FEED);
        decrementCount(item);
        sendSystemMessage(player, SID_SARLACC_FED);
        blog("Received Item - end, current phase: " + getIntObjVar(controller, house_pet.SARLACC_CURRENT_PHASE));
        setObjVar(controller, house_pet.SARLACC_LAST_FED, getGameTime());
        if (!hasMessageTo(self, "updateSarlaccPet"))
        {
            blog("Received Item - first feeding kicks off the messageTo");
            messageTo(self, "updateSarlaccPet", null, house_pet.getUpdateWeekly(controller), false);
        }
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(self, "playBurp", params, 3, false);
        return true;
    }
    public boolean validateItemForSarlacc(obj_id player, obj_id item) throws InterruptedException
    {
        if (utils.getContainingPlayer(item) != (player))
        {
            return false;
        }
        if (smuggler.hasItemsInContainer(item))
        {
            return false;
        }
        if (utils.isEquipped(item))
        {
            return false;
        }
        if (hasObjVar(item, "quest_item"))
        {
            return false;
        }
        return true;
    }
    public String getSarlaccCollectionColumn(obj_id controller) throws InterruptedException
    {
        blog("getSarlaccCollectionColumn - Init");
        if (!isValidId(controller) || !exists(controller))
        {
            return null;
        }
        blog("getSarlaccCollectionColumn - initial validation ok");
        if (!hasObjVar(controller, house_pet.SARLACC_FEED_ARRAY))
        {
            return null;
        }
        blog("getSarlaccCollectionColumn - array of feed exists");
        String[] foodList = getStringArrayObjVar(controller, house_pet.SARLACC_FEED_ARRAY);
        if (foodList == null || foodList.length < house_pet.SARLACC_PHASE_WEEKS)
        {
            blog("getSarlaccCollectionColumn - foodList.length: " + foodList.length);
            return null;
        }
        blog("getSarlaccCollectionColumn - foodList[0]: " + foodList[0]);
        blog("getSarlaccCollectionColumn - foodList[1]: " + foodList[1]);
        blog("getSarlaccCollectionColumn - foodList[2]: " + foodList[2]);
        blog("getSarlaccCollectionColumn - foodList[3]: " + foodList[3]);
        HashSet allEdibles = new HashSet();
        for (int i = 0; i < foodList.length - 1; i++)
        {
            if (foodList[i] == null || foodList.equals(""))
            {
                continue;
            }
            allEdibles.add(foodList[i]);
        }
        if (allEdibles.isEmpty())
        {
            blog("getSarlaccCollectionColumn - severe issue with feed array");
            return null;
        }
        blog("getSarlaccCollectionColumn - allEdibles.size(): " + allEdibles.size());
        String[] templateArray = new String[allEdibles.size()];
        allEdibles.toArray(templateArray);
        if (templateArray == null || templateArray.length <= 0)
        {
            blog("getSarlaccCollectionColumn - severe issue with hashset conversion of feed array");
            return null;
        }
        blog("getSarlaccCollectionColumn - templateArray.length: " + templateArray.length);
        if (templateArray.length == 1)
        {
            int lootRow = dataTableSearchColumnForString(templateArray[0], "item", house_pet.SARLACC_CTS_CRC_TABLE);
            if (lootRow < 0)
            {
                blog("getSarlaccCollectionColumn - lootRow: " + lootRow);
                return "generic_collectible";
            }
            String lootColumn = dataTableGetString(house_pet.SARLACC_CTS_CRC_TABLE, lootRow, "collectible_cols");
            blog("getSarlaccCollectionColumn - lootColumn: " + lootColumn);
            if (lootColumn == null || lootColumn.equals(""))
            {
                return "generic_collectible";
            }
            else 
            {
                String[] possibleCols = split(lootColumn, ',');
                if (possibleCols == null || possibleCols.length <= 0)
                {
                    return "generic_collectible";
                }
                else if (possibleCols.length == 1)
                {
                    return possibleCols[0];
                }
                else 
                {
                    return possibleCols[rand(0, possibleCols.length - 1)];
                }
            }
        }
        else 
        {
            String randTemplate = templateArray[rand(0, templateArray.length - 1)];
            int lootRow = dataTableSearchColumnForString(randTemplate, "item", house_pet.SARLACC_CTS_CRC_TABLE);
            String lootColumn = dataTableGetString(house_pet.SARLACC_CTS_CRC_TABLE, lootRow, "collectible_cols");
            blog("getSarlaccCollectionColumn - lootColumn: " + lootColumn);
            if (lootColumn == null || lootColumn.equals(""))
            {
                return "generic_collectible";
            }
            else 
            {
                String[] possibleCols = split(lootColumn, ',');
                if (possibleCols == null || possibleCols.length <= 0)
                {
                    return "generic_collectible";
                }
                else if (possibleCols.length == 1)
                {
                    return possibleCols[0];
                }
                else 
                {
                    return possibleCols[rand(0, possibleCols.length - 1)];
                }
            }
        }
    }
    public boolean getPlayerReward(obj_id owner, obj_id controller) throws InterruptedException
    {
        if (!isValidId(owner) || !exists(owner))
        {
            return false;
        }
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        obj_id playerInventory = utils.getInventoryContainer(owner);
        if (!isValidId(playerInventory) || !exists(playerInventory))
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.SARLACC_REWARD_COLUMN))
        {
            return false;
        }
        String rewardCol = getStringObjVar(controller, house_pet.SARLACC_REWARD_COLUMN);
        if (rewardCol == null || rewardCol.equals(""))
        {
            return false;
        }
        blog("rewardPlayer - getRandomCollectionItem for col: " + rewardCol);
        play2dNonLoopingSound(owner, SOUND_SIFT);
        if (!collection.getRandomCollectionItem(owner, playerInventory, house_pet.SARLACC_LOOT_TABLE, rewardCol))
        {
            blog("rewardPlayer - severe issue with getRandomCollectionItem for col: " + rewardCol);
        }
        removeObjVar(controller, house_pet.SARLACC_REWARD_AVAILABLE);
        removeObjVar(controller, house_pet.SARLACC_REWARD_COLUMN);
        sendSystemMessage(owner, SID_FOUND_REWARD);
        blog("rewardPlayer - REWARD GIVEN");
        return true;
    }
    public boolean checkPlayerInventoryForSarlaccFood(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        if (!isIdValid(inventory) || !exists(inventory))
        {
            return false;
        }
        obj_id[] contents = utils.getContents(inventory, true);
        if (contents == null || contents.length <= 0)
        {
            return false;
        }
        for (int x = 0; x < contents.length; x++)
        {
            if (!validateItemForSarlacc(player, contents[x]))
            {
                continue;
            }
            if (hasObjVar(contents[x], "noTrade") || utils.isEquipped(contents[x]))
            {
                continue;
            }
            if (isCrafted(contents[x]))
            {
                continue;
            }
            if (static_item.getStaticObjectValue(getStaticItemName(contents[x])) > 0)
            {
                return true;
            }
            String itemTemplate = getTemplateName(contents[x]);
            if (dataTableSearchColumnForString(itemTemplate, "items", smuggler.TBL) > -1)
            {
                return true;
            }
        }
        return false;
    }
    public void cleanupSui(obj_id player) throws InterruptedException
    {
        utils.removeScriptVar(player, house_pet.EDIBLES_SUI);
        utils.removeBatchScriptVar(player, house_pet.EDIBLES_IDS);
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (BLOGGING_ON)
        {
            LOG(BLOG_CATEGORY, msg);
        }
        return true;
    }
}
