package script.systems.npc_lair;

import script.*;
import script.library.luck;
import script.library.buff;
import script.library.collection;
import script.library.resource;
import script.library.static_item;
import script.library.utils;
import script.library.config_utils;

public class lair_interactivity extends script.base_script
{
    public lair_interactivity()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final String LOGGING_CATEGORY = "lair_interactivity";
    public static final String LAIR_SEARCHED = "lair.searched";
    public static final int LAIR_BUFF_INCREASE = 2;
    public static final string_id SID_SEARCH_LAIR = new string_id("lair_n", "search_lair");
    public static final string_id SID_FOUND_NOTHING = new string_id("lair_n", "found_nothing");
    public static final string_id SID_LAIR_EMPTY = new string_id("lair_n", "lair_empty");
    public static final string_id SID_INVENTORY_FULL = new string_id("lair_n", "inventory_full");
    public static final string_id SID_FOUND_EGGS = new string_id("lair_n", "found_eggs");
    public static final string_id SID_FOUND_BUGS = new string_id("lair_n", "found_bugs");
    public static final String[] BUG_SAMPLE_OBJECTS = 
    {
        "object/tangible/bug_jar/sample_bats.iff",
        "object/tangible/bug_jar/sample_bees.iff",
        "object/tangible/bug_jar/sample_butterflies.iff",
        "object/tangible/bug_jar/sample_flies.iff",
        "object/tangible/bug_jar/sample_glowzees.iff",
        "object/tangible/bug_jar/sample_moths.iff"
    };
    public static final String[] RARE_BUG_SAMPLE_OBJECTS = 
    {
        "object/tangible/fishing/bait/bait_grub.iff",
        "object/tangible/fishing/bait/bait_worm.iff",
        "object/tangible/fishing/bait/bait_insect.iff"
    };

    public static final String CONFIG_SECTION_LAIR_INTERACTIVITY = "LairInteractivity";

    public static final String CONFIG_LAIR_INTERACTIVITY_ENABLED = "enabled";
    public static final String CONFIG_LAIR_EGG_CHANCE = "eggChance";
    public static final String CONFIG_LAIR_NOTHING_CHANCE = "nothingChance";
    public static final String CONFIG_LAIR_EMPTY_CHANCE = "emptyChance";
    public static final String CONFIG_LAIR_EGG_AMOUNT_MULTIPLIER_PERCENT = "eggAmountMultiplierPercent";
    public static final String CONFIG_LAIR_RESET_MINUTES = "resetMinutes";
    public static final String CONFIG_LAIR_USE_LUCK = "useLuck";

    public static final String LAIR_SEARCHED_TIME = "lair.searched_time";

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, "npc_lair.isCreatureLair"))
        {
            detachScript(self, "systems.npc_lair.lair_interactivity");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int searchLair(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        searchLair(self, player);
        return SCRIPT_CONTINUE;
    }

	public void searchLair(obj_id self, obj_id player) throws InterruptedException
	{

		// Missing [LairInteractivity] or enabled=false preserves vanilla/public behavior.
		if (!config_utils.getBooleanConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_INTERACTIVITY_ENABLED))
		{	
		    searchLairVanilla(self, player);
		    return;
		}

		searchLairInteractivity(self, player);
	}

	private void searchLairInteractivity(obj_id self, obj_id player) throws InterruptedException
	{
		if (utils.hasScriptVar(self, LAIR_SEARCHED))
		{


		    if (hasLairSearchResetExpired(self, player))
		    {
		        resetLairSearchState(self);
		    }
		    else
		    {			
		       sendSystemMessage(player, SID_LAIR_EMPTY);
		       return;
		    }
		}

		obj_id pInv = utils.getInventoryContainer(player);

		if (!isValidId(pInv) || !exists(pInv))
		{
		    return;
		}

		if (getVolumeFree(pInv) <= 0)
		{	
		    sendSystemMessage(player, SID_INVENTORY_FULL);
		    return;
		}

		int result = rollLairSearchResult();

		if (result == 0)
		{		
		    sendSystemMessage(player, SID_FOUND_NOTHING);
		    return;
		}

		if (result == 1)
		{
		    searchLairEggs(self, player, pInv);
		}
		else
		{
		    searchLairBugs(player, pInv);
		}

		rollLairEmpty(self, player);
	}

	private int rollLairSearchResult() throws InterruptedException
	{
		int nothingChance = config_utils.getPercentConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_NOTHING_CHANCE, 10);
		int eggChance = config_utils.getPercentConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_EGG_CHANCE, 80);

		int bugChance = 100 - nothingChance - eggChance;

		if (bugChance < 0)
		{
		    bugChance = 0;
		}

		int totalChance = nothingChance + eggChance + bugChance;

		if (totalChance <= 0)
		{
		    nothingChance = 10;
		    eggChance = 80;
		    bugChance = 10;
		    totalChance = 100;
		}

		int roll = rand(1, totalChance);

		if (roll <= nothingChance)
		{
		    return 0; // nothing
		}

		if (roll <= nothingChance + eggChance)
		{
		    return 1; // eggs
		}

		return 2; // bugs
	}

	private void searchLairEggs(obj_id self, obj_id player, obj_id pInv) throws InterruptedException
	{
		sendSystemMessage(player, SID_FOUND_EGGS);

		int amount = rand(10, 20);

		int skillMod = getSkillStatMod(player, "creature_harvesting");

		if (skillMod > 0)
		{
		    amount += rand(1, skillMod);
		}

		if (buff.hasBuff(player, "lair_egg_buff"))
		{
		    amount = amount * LAIR_BUFF_INCREASE;
		}

		int multiplierPercent = config_utils.getIntConfigClamped(
		    CONFIG_SECTION_LAIR_INTERACTIVITY,
		    CONFIG_LAIR_EGG_AMOUNT_MULTIPLIER_PERCENT,
		    100,
		    0,
		    10000
		);

		amount = amount * multiplierPercent / 100;

		if (amount <= 0)
		{
		    sendSystemMessage(player, SID_FOUND_NOTHING);
		    return;
		}

		obj_id[] resourceList = resource.createRandom("meat_egg_" + getCurrentSceneName(), amount, getLocation(self), pInv, player, 2);

		if (resourceList == null)
		{
		    return;
		}

		location curloc = getLocation(player);

		if (curloc == null)
		{
		    return;
		}

		for (obj_id obj_id : resourceList)
		{
		    setLocation(obj_id, curloc);
		    putIn(obj_id, pInv, player);
		}

		if (config_utils.getBooleanConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_USE_LUCK, true))
		{
		    float luckValue = luck.getLevelCappedLuck(player);

		    if (luck.isLucky(player))
		    {
		        static_item.createNewItemFunction(collection.PRISTINE_EGG, player);
		        sendSystemMessage(player, new string_id("collection", "resource_egg"));
		    }

		    collection.collectionResource(player, "egg", Math.round(luckValue / 100));
		}
	}

	private void searchLairBugs(obj_id player, obj_id pInv) throws InterruptedException
	{
		sendSystemMessage(player, SID_FOUND_BUGS);

		if (rand(1, 100) < 50)
		{
		    createObject(RARE_BUG_SAMPLE_OBJECTS[rand(0, RARE_BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
		}
		else
		{
		    createObject(BUG_SAMPLE_OBJECTS[rand(0, BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
		}
	}

	private void rollLairEmpty(obj_id self, obj_id player) throws InterruptedException
	{
		int emptyChance = config_utils.getPercentConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_EMPTY_CHANCE, 34);

		if (emptyChance <= 0)
		{
		    return;
		}

		int roll = rand(1, 100);

		if (config_utils.getBooleanConfig(CONFIG_SECTION_LAIR_INTERACTIVITY, CONFIG_LAIR_USE_LUCK, true))
		{
		    float luckValue = luck.getLevelCappedLuck(player);
		    roll -= Math.round(luckValue / 100);
		}

		if (roll <= emptyChance)
		{
		    markLairSearched(self);
		}
	}

	private void markLairSearched(obj_id self) throws InterruptedException
	{
		utils.setScriptVar(self, LAIR_SEARCHED, 1);

		int resetMinutes = getLairResetMinutes();

		if (resetMinutes > 0)
		{
		    utils.setScriptVar(self, LAIR_SEARCHED_TIME, getGameTime());
		}
	}

	private void resetLairSearchState(obj_id self) throws InterruptedException
	{
		utils.removeScriptVar(self, LAIR_SEARCHED);
		utils.removeScriptVar(self, LAIR_SEARCHED_TIME);
	}


	private boolean hasLairSearchResetExpired(obj_id self, obj_id player) throws InterruptedException
	{
		int resetMinutes = getLairResetMinutes();

		sendSystemMessage(player, "Reset minutes " + resetMinutes, null);

		if (resetMinutes <= 0)
		{
		    return false;
		}

		if (!utils.hasScriptVar(self, LAIR_SEARCHED_TIME))
		{
		    return false;
		}

		int searchedTime = utils.getIntScriptVar(self, LAIR_SEARCHED_TIME);
		int resetSeconds = resetMinutes * 60;

		return getGameTime() - searchedTime >= resetSeconds;
	}


	private int getLairResetMinutes() throws InterruptedException
	{
		return config_utils.getIntConfigClamped(
		    CONFIG_SECTION_LAIR_INTERACTIVITY,
		    CONFIG_LAIR_RESET_MINUTES,
		    10,
		    0,
		    10080
		);
	}


    public void searchLairVanilla(obj_id self, obj_id player) throws InterruptedException
    {

	 	//This is the original lair seraching code

        if (utils.hasScriptVar(self, LAIR_SEARCHED))
        {
            return;
        }
        int searchagain = rand(0, 2);
        if (searchagain == 0)
        {
            utils.setScriptVar(self, LAIR_SEARCHED, 1);
        }
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isValidId(pInv) || !exists(pInv))
        {
            return;
        }
        if (getVolumeFree(pInv) <= 0)
        {
            sendSystemMessage(player, SID_INVENTORY_FULL);
            return;
        }
        int searchRoll = rand(1, 100);
        if (searchRoll < 10)
        {
            sendSystemMessage(player, SID_FOUND_NOTHING);
        }
        else if (searchRoll < 90)
        {
            sendSystemMessage(player, SID_FOUND_EGGS);
            int amt = rand(10, 20);
            if (buff.hasBuff(player, "lair_egg_buff"))
            {
                amt = amt * LAIR_BUFF_INCREASE;
            }
            obj_id[] resourceList = resource.createRandom("meat_egg_" + getCurrentSceneName(), amt, getLocation(self), pInv, player, 2);
            if (resourceList == null)
            {
                blog("ai.handleMilking: cannot get resource data from resource.createRandom function");
                CustomerServiceLog("milking_and_lair_search", "handleMilking: Player: " + getName(player) + " OID: " + player + " attempted to milk but could not resource data from resource.createRandom function while milking " + self + " " + getName(self));
                return;
            }
            location curloc = getLocation(player);
            if (curloc == null)
            {
                blog("ai.handleMilking: cannot get resource data from getLocation cpp");
                CustomerServiceLog("milking_and_lair_search", "handleMilking: Player: " + getName(player) + " OID: " + player + " attempted to milk but could not retrieve location data while milking " + self + " " + getName(self));
                return;
            }
            for (obj_id obj_id : resourceList) {
                blog("" + obj_id);
                setLocation(obj_id, curloc);
                putIn(obj_id, pInv, player);
            }
        }
        else 
        {
            sendSystemMessage(player, SID_FOUND_BUGS);
            if (rand(1, 100) < 50)
            {
                createObject(RARE_BUG_SAMPLE_OBJECTS[rand(0, RARE_BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
            }
            else 
            {
                createObject(BUG_SAMPLE_OBJECTS[rand(0, BUG_SAMPLE_OBJECTS.length - 1)], pInv, "");
            }
        }
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
