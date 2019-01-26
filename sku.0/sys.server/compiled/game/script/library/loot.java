package script.library;

import script.*;

import java.util.Enumeration;
import java.util.Vector;

public class loot extends script.base_script
{
    public loot()
    {
    }
    public static final int COL_LOOT_MULTIPLIER_ON = 1;
    public static final String TBL_CREATURES = "datatables/mob/creatures.iff";
    public static final String TBL_COMPONENT_DATA = "datatables/loot/component_data.iff";
    public static final String VAR_DENY_LOOT = "denyLoot";
    public static final String VAR_LOOT_QUALITY = "loot.loot_quality";
    public static final String CHRONICLES_LOOT_TOGGLE_OBJVAR = "chroniclesLoot_toggledOff";
    public static final String TBL_EXCLUSION = "datatables/loot/exclusion.iff";
    public static final String COL_TEMPLATE = "TEMPLATE";
    public static final String COL_EXCLUDE = "EXCLUDE";
    public static final String DATA_ITEM = "dataItem";
    public static final String DATA_ITEM_FACTION = DATA_ITEM + ".faction";
    public static final String DATA_ITEM_VALUE = DATA_ITEM + ".value";
    public static final String TBL_DATA_ITEM_VALUE = "datatables/data_item/redeem_value.iff";
    public static final String ENCODED_DISK = "object/tangible/encoded_disk/encoded_disk_base.iff";
    public static final String SPECIAL_LOOT = "datatables/loot/npc_special_loot.iff";
    public static final String COLLECTIBLE_LOOT = "datatables/loot/npc_collectible_loot.iff";
    public static final String CASH_ITEM_TEMPLATE = "object/tangible/item/loot_cash.iff";
    public static final String MEATLUMP_LUMP = "item_meatlump_lump_01_01";
    public static final String MEATLUMP_LOOT_TABLE = "datatables/loot/dungeon/meatlump_dungeon_container_loot.iff";
    public static final String CREATURES_TABLE = "datatables/mob/creatures.iff";
    public static final String COLLECTIONS_LOOT_TABLE = "datatables/loot/loot_items/collectible/collection_loot.iff";
    public static final String COLLECTIONS_PVP_RANK_TABLE = "datatables/loot/loot_items/collectible/collection_pvp_rank.iff";
    public static final String COLLECTIONS_PVP_LOOT_TABLE = "datatables/loot/loot_items/collectible/collection_pvp_loot.iff";
    public static final int MIN_CREATURE_LEVEL = 1;
    public static final int MAX_CREATURE_LEVEL = 85;
    public static final float MAX_ALLOWED_WEAPON_INTENSITY = 0.90f;
    public static final float MIN_ALLOWED_WEAPON_INTENSITY = 0.35f;
    public static final int BASE_CHANCE_FOR_CASH = 40;
    public static final int BASE_CHANCE_FOR_RESOURCES = 40;
    public static final int BASE_CHANCE_FOR_ENZYME = 25;
    public static final string_id SID_REWARD_ITEM = new string_id("collection", "reward_item");
    public static final int COLLECTION_PVP_ROLL = 80;
    public static final String FORAGE_LOGGING_CATEGORY = "foraging";
    public static final boolean FORAGE_LOGGING_ON = false;
    public static final int RARE_COMPONENT_DIVISOR = 10;
    public static final int SUPER_RARE_COMPONENT_DIVISOR = 5;
    public static final int WORM_ROLL_INT = 80;
    public static final int ENZYME = 0;
    public static final int WORM = 1;
    public static final int COMPONENT = 2;
    public static final int TREASURE = 3;
    public static final int BAIT = 99;
    public static final int NOTHING = 98;
    public static final int FORAGE_BASE_ONE = 14;
    public static final int FORAGE_BASE_TWO = 28;
    public static final int PLAYER_DEFAULT_FORAGE_CHANCE = 0;
    public static final int DEFAULT_ENZYME_CHANCE = 40;
    public static final int DEFAULT_WORM_CHANCE = 40;
    public static final int DEFAULT_TREASURE_CHANCE = 0;
    public static final int DEFAULT_COMPONENT_CHANCE = 40;
    public static final float ENZYME_MODIFIER = 0.0f;
    public static final float TREASURE_MODIFIER = 0.0f;
    public static final float PLAYER_DEFAULT_FIND_MODIFIER = 0.0f;
    public static final float LUCKY_FIND_MODIFIER = 6.0f;
    public static final String FORAGING_RARE_TABLE = "datatables/foraging/forage_global_rare.iff";
    public static final String FORAGING_ENEMY_TABLE = "datatables/foraging/forage_enemy.iff";
    public static final String FORAGING_LOOT_ROLL_TABLE = "datatables/foraging/foraging_loot_roll.iff";
    public static final String LOOT_LOW_COL = "low";
    public static final String LOOT_HIGH_COL = "high";
    public static final String LOOT_ENZYME = "enzyme";
    public static final String LOOT_COMPONENT = "component";
    public static final String LOOT_WORM = "worm";
    public static final String LOOT_BAIT = "bait";
    public static final String LOOT_TREASURE = "treasure";
    public static final String FORAGE_ENEMY_SCRIPT = "creature.foraging_enemy";
    public static final String FORAGING_STF = "player/player_utility";
    public static final string_id FOUND_TREASURE = new string_id("player/player_utility", "forage_found_treasure");
    public static final string_id TREASURE_BONUS_ROLL = new string_id("player/player_utility", "treasure_buff_extra_roll");
    public static final string_id TREASURE_BONUS = new string_id("player/player_utility", "treasure_map_buff_bonus");
    public static final string_id SID_FULL_INVENTORY = new string_id("player/player_utility", "forage_full_inventory");
    public static final string_id FOUND_NOTHING = new string_id("player/player_utility", "forage_found_nothing");
    public static final string_id FOUND_SOMETHING = new string_id("player/player_utility", "forage_found_something");
    public static final string_id FOUND_WORM = new string_id("player/player_utility", "forage_found_worm");
    public static final string_id FOUND_COMPONENT = new string_id("player/player_utility", "forage_found_component");
    public static final string_id FOUND_ENZYME = new string_id("player/player_utility", "forage_found_enzyme");
    public static final string_id ENZYME_BONUS_ROLL = new string_id("player/player_utility", "enzyme_buff_extra_roll");
    public static final string_id ENZYME_BONUS = new string_id("player/player_utility", "truffle_pig_buff_bonus");
    public static final string_id FORAGE_BONUS = new string_id("player/player_utility", "ice_cream_buff_bonus");
    public static final string_id LUCK_BONUS = new string_id("player/player_utility", "luck_buff_bonus");
    public static final String[] COMPONENT_PLANETS = 
    {
        "corellia",
        "dantooine",
        "dathomir",
        "endor",
        "naboo",
        "tatooine",
        "yavin4"
    };
    public static final String[] CHEST_TYPES = {
            "rare",
            "exceptional",
            "legendary"
    };
    private static final String CHEST_BASE = "rare_loot_chest_quality_";
    private static final String RLS_EFFECT = "appearance/pt_rare_chest.prt";
    private static final String RLS_SOUND = "sound/rare_loot_chest.snd";

    public static boolean addLoot(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (hasObjVar(target, VAR_DENY_LOOT))
        {
            return false;
        }
        boolean hasLoot = false;
        obj_id inv = utils.getInventoryContainer(target);
        if (inv == null)
        {
            return false;
        }
        String mobType = ai_lib.getCreatureName(target);
        if (mobType == null || mobType.length() < 1)
        {
            String err = "WARNING: loot::addLoot(" + target + ") returning false because getCreatureName failed. Template=" + getTemplateName(target) + ", IsAuthoritative=" + target.isAuthoritative() + ". Stack Trace as follows:";
            CustomerServiceLog("creatureNameErrors", err);
            debugServerConsoleMsg(target, err);
            Thread.dumpStack();
            return false;
        }
        if (!hasObjVar(target, "storytellerid"))
        {
            int cash = getNpcMoney(target);
            if (addCashAsLoot(target, cash))
            {
                hasLoot = true;
            }
        }
        hasLoot |= setupLootItems(target);
        hasLoot |= addCollectionLoot(target);
        hasLoot |= addRareLoot(target);
        int niche = ai_lib.aiGetNiche(mobType);
        if (niche == NICHE_MONSTER || niche == NICHE_HERBIVORE || niche == NICHE_CARNIVORE || niche == NICHE_PREDATOR)
        {
            int[] hasResource = corpse.hasResource(mobType);
            if (hasResource != null && hasResource.length > 0)
            {
                setObjVar(target, corpse.VAR_HAS_RESOURCE, hasResource);
            }
            if (isInTutorialArea(target))
            {
                return false;
            }
            hasLoot |= addBeastEnzymes(target);
        }
        return hasLoot;
    }
    public static boolean addGoldenTicket(obj_id player, obj_id corpse) throws InterruptedException{
        if(!hasObjVar(player, "lottery.looted") && getIntObjVar(player, "lottery.availableTickets") > 0) {
            obj_id broker = getObjIdObjVar(player, "lottery.broker");
            if(!isValidId(broker)) return false;
            Vector qualifiers = getResizeableObjIdArrayObjVar(broker, "lottery.qualifiers");
            obj_id playerStationId = utils.stringToObjId("" + getPlayerStationId(player));
            if(qualifiers != null && utils.getElementPositionInArray(qualifiers, playerStationId) > -1) return false;
            double dropChance = 2;
            try {
                dropChance = Double.parseDouble(getConfigSetting("EventTeam", "goldenTicketDropChance"));
            }
            catch(Exception e){}
            double rng = Math.random() * 100;
            if(dropChance > 0 && rng <= dropChance) {
                String goldenTicket = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
                obj_id[] items = utils.getAllItemsInBankAndInventory(player);
                if(items != null) {
                    for (obj_id item : items) {
                        if (getTemplateName(item).equals(goldenTicket)) {
                            return false;
                        }
                    }
                }
                obj_id lootItem = createObject(goldenTicket, utils.getInventoryContainer(player), "");
                setName(lootItem, "A Golden Ticket");
                setObjVar(lootItem, "noTrade", 1);
                if(isValidId(lootItem)){
                    setObjVar(player, "lottery.looted", 1);
                    if(ai_lib.isHumanoid(corpse)){
                        chat.chat(corpse, "...you... you've won, Charlie...");
                    }
                    play2dNonLoopingSound(player, "sound/utinni.snd");
                    sendSystemMessage(player, "A particularly shiny Golden Ticket has been placed in your inventory!", null);
                    LOG("live-lottery","Player " + getFirstName(player) + " (" + player + ") has looted a lottery ticket.");
                    obj_id inv = utils.getInventoryContainer(corpse);
                    return true;
                }
            }
        }
        return false;
    }
    public static int getCashForLevel(String mobType, int level) throws InterruptedException
    {
        if (mobType == null || mobType.equals("") || level < 1)
        {
            return 0;
        }
        int lbound = dataTableGetInt(TBL_CREATURES, mobType, "minCash");
        int ubound = dataTableGetInt(TBL_CREATURES, mobType, "maxCash");
        if (ubound == 0)
        {
            return 0;
        }
        if (lbound > 0 && ubound > 0)
        {
            return rand(lbound, ubound);
        }
        int minCash = level;
        if (lbound > 0 && minCash < lbound)
        {
            minCash = lbound;
        }
        int maxCash = level * 17;
        if (ubound > 0 && maxCash > ubound)
        {
            maxCash = ubound;
        }
        return rand(minCash, maxCash);
    }
    public static boolean addResourceLoot(obj_id target) throws InterruptedException
    {
        if (rand(1, 100) > BASE_CHANCE_FOR_RESOURCES)
        {
            return false;
        }
        String mobType = ai_lib.getCreatureName(target);
        int niche = ai_lib.aiGetNiche(mobType);
        if (niche != NICHE_MONSTER && niche != NICHE_HERBIVORE && niche != NICHE_CARNIVORE && niche != NICHE_PREDATOR)
        {
            return false;
        }
        int[] hasResource = corpse.hasResource(mobType);
        if (hasResource == null || hasResource.length == 0)
        {
            return false;
        }
        obj_id inv = utils.getInventoryContainer(target);
        dictionary resourceData = corpse.getRandomHarvestCorpseResources(obj_id.NULL_ID, target);
        java.util.Enumeration keys = resourceData.keys();
        int finalAmount = 0;
        while (keys.hasMoreElements())
        {
            String resourceType = (String)(keys.nextElement());
            int amt = resourceData.getInt(resourceType);
            if (amt <= 0)
            {
                continue;
            }
            String sceneName = getCurrentSceneName();
            String rsrcMapTable = "datatables/creature_resource/resource_scene_map.iff";
            String correctedPlanetName = dataTableGetString(rsrcMapTable, sceneName, 1);
            if (correctedPlanetName == null || correctedPlanetName.equals(""))
            {
                correctedPlanetName = "tatooine";
            }
            resourceType = resourceType + "_" + correctedPlanetName;
            int useDistMap = dataTableGetInt(rsrcMapTable, sceneName, "useDistributionMap");
            location worldLoc = getWorldLocation(target);
            if (useDistMap == 0)
            {
                worldLoc.area = correctedPlanetName;
            }
            finalAmount += corpse.extractCorpseResource(resourceType, amt, worldLoc, obj_id.NULL_ID, inv, 1);
        }
        return (finalAmount > 0);
    }
    public static int getCalculatedAttribute(int minVal, int maxVal, int creatureLevel, int minDropLevel, int maxDropLevel) throws InterruptedException
    {
        return Math.round(getCalculatedAttribute((float)minVal, maxVal, creatureLevel, minDropLevel, maxDropLevel));
    }
    public static float getCalculatedAttribute(float minVal, float maxVal, int creatureLevel, int minDropLevel, int maxDropLevel) throws InterruptedException
    {
        float rank = (float)(creatureLevel - minDropLevel) / (maxDropLevel - minDropLevel);
        if (rank < -1.0f)
        {
            rank = -1.0f;
        }
        else if (rank > 2.0f)
        {
            rank = 2.0f;
        }
        float rslt = random.distributedRand(minVal, maxVal, rank);
        LOG("loot", "getCalculatedAttribute: -> returning " + rslt);
        return rslt;
    }
    public static int getCalculatedAttribute(int minVal, int maxVal, int creatureLevel) throws InterruptedException
    {
        return getCalculatedAttribute(minVal, maxVal, creatureLevel, MIN_CREATURE_LEVEL, MAX_CREATURE_LEVEL);
    }
    public static float getCalculatedAttribute(float minVal, float maxVal, int creatureLevel) throws InterruptedException
    {
        return getCalculatedAttribute(minVal, maxVal, creatureLevel, MIN_CREATURE_LEVEL, MAX_CREATURE_LEVEL);
    }
    public static int getRowIndexForComponent(String componentTemplate) throws InterruptedException
    {
        if (componentTemplate == null || componentTemplate.equals(""))
        {
            return -1;
        }
        return dataTableSearchColumnForString(componentTemplate, "template", TBL_COMPONENT_DATA);
    }
    public static dictionary getComponentData(obj_id component) throws InterruptedException
    {
        return getComponentData(getTemplateName(component));
    }
    public static dictionary getComponentData(String componentTemplate) throws InterruptedException
    {
        int startRow = getRowIndexForComponent(componentTemplate);
        if (startRow < 0)
        {
            return null;
        }
        dictionary dat = new dictionary();
        int totalRows = dataTableGetNumRows(TBL_COMPONENT_DATA);
        if (totalRows < startRow + 1)
        {
            trace.log("loot", "loot::getComponentData: -> Gave starting row of " + startRow + ", but " + TBL_COMPONENT_DATA + " only has " + totalRows + " rows.", null, trace.TL_ERROR_LOG);
            return null;
        }
        String templateString = "";
        for (int i = startRow; i < totalRows; i++)
        {
            templateString = dataTableGetString(TBL_COMPONENT_DATA, i, "template");
            if (!templateString.equals(componentTemplate) && !templateString.equals(""))
            {
                trace.log("loot", "loot::getComponentData: -> Done fetching data for this object.  Stopped at data table row " + i);
                break;
            }
            dat.put(dataTableGetString(TBL_COMPONENT_DATA, i, "stringArg"), new float[]
            {
                dataTableGetFloat(TBL_COMPONENT_DATA, i, "min"),
                dataTableGetFloat(TBL_COMPONENT_DATA, i, "max")
            });
        }
        trace.log("loot", "loot:getComponentData: -> Component data has " + dat.size() + " elements.");
        return dat;
    }
    public static boolean redeemFactionCoupon(obj_id redeemer, obj_id player, obj_id coupon) throws InterruptedException
    {
        if ((redeemer == null) || (player == null) || (coupon == null))
        {
            return false;
        }
        if (!isFactionalDataItem(coupon))
        {
            return false;
        }
        String rFaction = getStringObjVar(redeemer, "faction");
        if ((rFaction == null) || (rFaction.equals("")))
        {
            return false;
        }
        int val = getFactionCouponValue(redeemer, coupon);
        if (val < 0)
        {
            return false;
        }
        factions.awardFactionStanding(player, rFaction, val);
        return destroyObject(coupon);
    }
    public static int getFactionCouponValue(obj_id redeemer, obj_id coupon) throws InterruptedException
    {
        if ((redeemer == null) || (coupon == null))
        {
            return -1;
        }
        if (!isFactionalDataItem(coupon))
        {
            return -1;
        }
        if (!hasObjVar(redeemer, factions.FACTION))
        {
            return -1;
        }
        String rFaction = getStringObjVar(redeemer, factions.FACTION);
        String cFaction = getStringObjVar(coupon, DATA_ITEM_FACTION);
        int base_value = getIntObjVar(coupon, DATA_ITEM_VALUE);
        if (!dataTableOpen(TBL_DATA_ITEM_VALUE))
        {
            return -1;
        }
        float multiplier = utils.dataTableGetFloat(TBL_DATA_ITEM_VALUE, rFaction, cFaction);
        if (multiplier < 0)
        {
            return -1;
        }
        int point_value = (int)(base_value * multiplier);
        return point_value;
    }
    public static boolean isFactionalDataItem(obj_id item) throws InterruptedException
    {
        if ((item == null) || (item == obj_id.NULL_ID))
        {
            return false;
        }
        return getGameObjectType(item) == GOT_data_fictional;
    }
    public static obj_id[] getFactionalDataItems(obj_id target, String faction) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_id dp = utils.getDatapad(target);
        if ((dp == null) || (dp == obj_id.NULL_ID))
        {
            return null;
        }
        else 
        {
            obj_id[] contents = getContents(dp);
            if ((contents == null) || (contents.length == 0))
            {
                return null;
            }
            else 
            {
                for (obj_id item : contents) {
                    if (isFactionalDataItem(item)) {
                        if (faction.equals("")) {
                            ret = utils.addElement(ret, item);
                        } else {
                            String ifac = getStringObjVar(item, DATA_ITEM_FACTION);
                            if (ifac != null) {
                                if (ifac.equals(faction)) {
                                    ret = utils.addElement(ret, item);
                                }
                            }
                        }
                    }
                }
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        else 
        {
            obj_id[] _ret = new obj_id[0];
            if (ret != null)
            {
                _ret = new obj_id[ret.size()];
                ret.toArray(_ret);
            }
            return _ret;
        }
    }
    public static obj_id[] getFactionalDataItems(obj_id target) throws InterruptedException
    {
        return getFactionalDataItems(target, "");
    }
    public static boolean randomizeWeapon(obj_id weapon, int creatureLevel) throws InterruptedException
    {
        if (!isIdValid(weapon))
        {
            return false;
        }
        float percentOfMax = getCalculatedAttribute(MIN_ALLOWED_WEAPON_INTENSITY, MAX_ALLOWED_WEAPON_INTENSITY, creatureLevel);
        dictionary weaponCraftingData = weapons.getWeaponDat(weapon);
        if (weaponCraftingData == null)
        {
            return false;
        }
        weapons.setWeaponAttributes(weapon, weaponCraftingData, percentOfMax);
        return true;
    }
    public static boolean randomizeArmor(obj_id item, int level) throws InterruptedException
    {
        LOG("loot", "loot:randomizeArmor: -> Randomizing " + item);
        if (!isIdValid(item))
        {
            return false;
        }
        String template = getTemplateName(item);
        int armorCat = armor.getArmorCategoryByTemplate(template);
        if (armorCat < 0 || armorCat > AC_max)
        {
            return false;
        }
        if (!armor.setArmorDataPercent(item, getCalculatedAttribute(0, AL_max - 1, level), armorCat, getCalculatedAttribute(0.4f, 0.9f, level), getCalculatedAttribute(0.4f, 1.0f, level)))
        {
            return false;
        }
        if (armorCat == 0)
        {
            armor.setArmorSpecialProtectionPercent(item, armor.DATATABLE_RECON_LAYER, 1.0f);
        }
        if (armorCat == 2)
        {
            armor.setArmorSpecialProtectionPercent(item, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
        }
        if (!armor.isValidArmor(item))
        {
            LOG("loot", "loot:randomizeArmor: -> Not valid armor.");
            return false;
        }
        return true;
    }
    public static boolean randomizeMedicine(obj_id item, int level) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (hasObjVar(item, consumable.VAR_CONSUMABLE_MODS))
        {
            attrib_mod[] am = getAttribModArrayObjVar(item, consumable.VAR_CONSUMABLE_MODS);
            attrib_mod[] am_new = new attrib_mod[am.length];
            for (int i = 0; i < am.length; i++)
            {
                int attrib = am[i].getAttribute();
                int val = am[i].getValue();
                float duration = am[i].getDuration();
                float atk = am[i].getAttack();
                float decay = am[i].getDecay();
                attrib_mod tmp;
                if (healing.isBuffMedicine(item))
                {
                    tmp = new attrib_mod(attrib, getCalculatedAttribute(600, 900, level), getCalculatedAttribute(9000, 12000, level), atk, decay);
                }
                else 
                {
                    tmp = new attrib_mod(attrib, getCalculatedAttribute(150, 400, level), duration, atk, decay);
                }
                am_new[i] = tmp;
            }
            if (am_new.length == 0)
            {
                return false;
            }
            setObjVar(item, consumable.VAR_CONSUMABLE_MODS, am_new);
        }
        if (healing.isCureDotMedicine(item) || healing.isApplyDotMedicine(item))
        {
            setObjVar(item, healing.VAR_HEALING_DOT_POWER, getCalculatedAttribute(40, 100, level));
            if (healing.isApplyDotMedicine(item))
            {
                int potency = healing.getDotPotency(item);
                setObjVar(item, healing.VAR_HEALING_DOT_POTENCY, getCalculatedAttribute(70, 150, level));
                int duration = healing.getDotDuration(item);
                setObjVar(item, healing.VAR_HEALING_DOT_DURATION, getCalculatedAttribute(400, 800, level));
            }
        }
        setCount(item, getCalculatedAttribute(5, 25, level));
        return true;
    }
    public static float[] validateComponentVals(float[] dat) throws InterruptedException
    {
        float[] defaultVal = new float[]
        {
            0.0f,
            0.0f
        };
        if (dat == null)
        {
            return defaultVal;
        }
        switch (dat.length)
        {
            case 1:
            return new float[]
            {
                dat[0],
                dat[0]
            };
            case 2:
            return dat;
        }
        return defaultVal;
    }
    public static boolean randomizeComponent(obj_id item, int level, obj_id container) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (!isIdValid(container))
        {
            return false;
        }
        String template = getTemplateName(item);
        dictionary componentData = getComponentData(template);
        if (componentData == null)
        {
            return false;
        }
        int minToDrop = 1;
        int maxToDrop = 1;
        float[] fVals = null;
        int minCreatureLevelDrop = MIN_CREATURE_LEVEL;
        int maxCreatureLevelDrop = MAX_CREATURE_LEVEL;
        if (componentData.containsKey("level"))
        {
            fVals = validateComponentVals(componentData.getFloatArray("level"));
            minCreatureLevelDrop = (int)fVals[0];
            maxCreatureLevelDrop = (int)fVals[1];
            componentData.remove("level");
        }
        Enumeration keys = componentData.keys();
        boolean hasAttribBonus = false;
        int[] attribBonus = null;
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            fVals = validateComponentVals(componentData.getFloatArray(key));
            key = key.trim();
            LOG("loot", "loot::randomizeComponent: -> processing key '" + key + "' where data[0]=" + fVals[0] + ", data[1]=" + fVals[1]);
            if (key.equals("amount"))
            {
                LOG("loot", "loot::randomizeComponent: -> Grabbing amount from dataset");
                minToDrop = (int)fVals[0];
                maxToDrop = (int)fVals[1];
            }
            else if (key.startsWith("attribute.bonus."))
            {
                String strAttrib = key.substring(key.length() - 1);
                int intAttrib = utils.stringToInt(strAttrib);
                LOG("loot", "loot:randomizeComponent: -> Processing Attributes : " + intAttrib);
                if (intAttrib < 0 || intAttrib >= NUM_ATTRIBUTES)
                {
                    LOG("loot", "loot:randomizeComponent: -> IntAttrib Out of Range : " + intAttrib);
                    continue;
                }
                if (attribBonus == null)
                {
                    attribBonus = new int[NUM_ATTRIBUTES];
                }
                attribBonus[intAttrib] = getCalculatedAttribute((int)fVals[0], (int)fVals[1], level, minCreatureLevelDrop, maxCreatureLevelDrop);
                hasAttribBonus = true;
            }
            else if (key.startsWith("combat_critical"))
            {
                int val = rand((int)fVals[0], (int)fVals[1]);
                LOG("loot", "loot:randomizeComponent: -> Not an attribute modifier or armor modifier : " + key);
                setObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + key, val);
                LOG("loot", "loot:randomizeComponent: -> setting objvar : " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + key);
            }
            else 
            {
                float val = getCalculatedAttribute(fVals[0], fVals[1], level, minCreatureLevelDrop, maxCreatureLevelDrop);
                if (key.equals("armorCategory") || key.equals("armorLevel"))
                {
                    setObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + key, (int)val);
                    int armorCat = armor.getArmorCategory(item);
                    if (armorCat == 0)
                    {
                        armor.setArmorSpecialProtectionPercent(item, armor.DATATABLE_RECON_LAYER, 1.0f);
                    }
                    if (armorCat == 2)
                    {
                        armor.setArmorSpecialProtectionPercent(item, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                    }
                }
                else 
                {
                    setObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + key, key.equals("attackSpeed") || key.equals("attackCost") ? val * -1 : val);
                }
            }
        }
        if (hasAttribBonus && attribBonus != null && attribBonus.length == NUM_ATTRIBUTES)
        {
            LOG("loot", "loot:randomizeComponent: -> Calling Set Attributes : " + attribBonus.toString());
            for (int i = 0; i < attribBonus.length; i++)
            {
                LOG("loot", "loot:randomizeComponent: -> Attribute " + i + " = " + attribBonus[i]);
            }
            setAttributeBonuses(item, attribBonus);
        }
        LOG("loot", "loot:randomizeComponent: -> Amount to drop : " + minToDrop + "/" + maxToDrop);
        setCraftedId(item, container);
        setCrafter(item, container);
        int numDropped = 1;
        if (minToDrop <= maxToDrop)
        {
            numDropped = rand(minToDrop, maxToDrop);
        }
        LOG("loot", "loot:randomizeComponent: -> Dropping " + numDropped + " " + template);
        if (numDropped > 1)
        {
            setObjVar(item, "unstack.serialNumber", container);
            setCount(item, numDropped);
            attachScript(item, "object.onewayunstack");
        }
        return true;
    }
    public static String getCreatureType(String name) throws InterruptedException
    {
        return dataTableGetString("datatables/mob/creatures.iff", name, "lootList");
    }
    public static int getAdjustedCreatureLevel(obj_id target, String name) throws InterruptedException
    {
        int level = getLevel(target);
        int difficultyClass = dataTableGetInt("datatables/mob/creatures.iff", name, "difficultyClass");
        if (difficultyClass > 0)
        {
            float levelMod = 0.4f * difficultyClass;
            level += (int)(level * levelMod);
        }
        return level;
    }
    public static boolean generateTheftLootRare(obj_id container, obj_id mark, int maxItems, obj_id thief) throws InterruptedException
    {
        String name = getCreatureName(mark);
        String type = getCreatureType(name);
        if (type == null || type.equals("none"))
        {
            return false;
        }
        String strTreasureTable = "treasure/treasure_";
        String treasureLevel = "1_10";
        int intLevel = getIntObjVar(mark, "intCombatDifficulty");
        if (intLevel <= 10)
        {
            treasureLevel = "1_10";
        }
        else if (intLevel <= 20)
        {
            treasureLevel = "11_20";
        }
        else if (intLevel <= 30)
        {
            treasureLevel = "21_30";
        }
        else if (intLevel <= 40)
        {
            treasureLevel = "31_40";
        }
        else if (intLevel <= 50)
        {
            treasureLevel = "41_50";
        }
        else if (intLevel <= 60)
        {
            treasureLevel = "51_60";
        }
        else if (intLevel <= 70)
        {
            treasureLevel = "61_70";
        }
        else if (intLevel <= 80)
        {
            treasureLevel = "71_80";
        }
        else if (intLevel <= 90)
        {
            treasureLevel = "81_90";
        }
        else 
        {
            return false;
        }
        String strTable = strTreasureTable + treasureLevel;
        utils.setScriptVar(container, "theft_in_progress", 1);
        return makeLootInContainer(container, strTable, 1, intLevel);
    }
    public static boolean generateTheftLoot(obj_id container, obj_id mark, float chanceMod, int maxItems) throws InterruptedException
    {
        String name = getCreatureName(mark);
        String type = getCreatureType(name);
        if (type == null || type.equals("none"))
        {
            return false;
        }
        int level = getAdjustedCreatureLevel(mark, name);
        String strTable = getStringObjVar(mark, "loot.lootTable");
        int intLevel = getIntObjVar(mark, "intCombatDifficulty");
        utils.setScriptVar(container, "theft_in_progress", 1);
        return makeLootInContainer(container, strTable, 1, intLevel);
    }
    public static int getNpcMoney(obj_id target) throws InterruptedException
    {
        String mobType = ai_lib.getCreatureName(target);
        if (mobType == null)
        {
            return -1;
        }
        int niche = ai_lib.aiGetNiche(target);
        if (niche != NICHE_NPC)
        {
            return -1;
        }
        int level = ai_lib.getLevel(target);
        int cash = getCashForLevel(mobType, level);
        if (cash < 1)
        {
            cash = rand(1, 10);
            cash = cash * level;
        }
        return cash;
    }
    public static boolean addCashAsLoot(obj_id target, int cash) throws InterruptedException
    {
        if (cash < 1)
        {
            return false;
        }
        obj_id inv = utils.getInventoryContainer(target);
        if (inv == null)
        {
            return false;
        }
        obj_id cashItem = createObject(CASH_ITEM_TEMPLATE, inv, "");
        if (!isIdValid(cashItem))
        {
            return false;
        }
        setObjVar(cashItem, "loot.cashAmount", cash);
        setName(cashItem, formatCashAmount(cash));
        return true;
    }
    public static String formatCashAmount(int cash) throws InterruptedException
    {
        final java.text.DecimalFormat CASH_NAME_FORMAT = new java.text.DecimalFormat("#,### cr");
        return CASH_NAME_FORMAT.format(cash);
    }
    public static boolean isCashLootItem(obj_id item) throws InterruptedException
    {
        String template = getTemplateName(item);
        if (template.equals(CASH_ITEM_TEMPLATE))
        {
            return hasObjVar(item, "loot.cashAmount");
        }
        return false;
    }
    public static boolean doGroupLooting(obj_id corpseId, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id leader = group.getLeader(transferer);
        obj_id team = getGroupObject(transferer);
        int groupLootType = getGroupLootRule(team);
        obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(team);
        obj_id corpseInv = utils.getInventoryContainer(corpseId);
        obj_id[] contents = getContents(corpseInv);
        int numContents = contents.length;
        if (objMembersWhoExist == null)
        {
            return false;
        }
        if (groupLootType == 0)
        {
            setGroupLootRule(team, group.FREE_FOR_ALL);
            return true;
        }
        if (groupLootType == 1)
        {
            obj_id master = getGroupMasterLooterId(team);
            String masterName = getEncodedName(master);
            if (transferer != master)
            {
                prose_package pp = new prose_package();
                string_id masterMsg = new string_id(group.GROUP_STF, "master_only");
                pp = prose.setStringId(pp, masterMsg);
                pp = prose.setTO(pp, masterName);
                sendSystemMessageProse(transferer, pp);
                return false;
            }
        }
        if (groupLootType == 2)
        {
            if (hasObjVar(corpseId, "autoLootComplete"))
            {
                return true;
            }
            int numWindows = 0;
            if (hasObjVar(corpseId, "numWindowsOpen"))
            {
                numWindows = getIntObjVar(corpseId, "numWindowsOpen");
            }
            else 
            {
                for (obj_id objMemberWhoExists : objMembersWhoExist) {
                    if (numContents > 0 && !(numContents == 1 && isCashLootItem(contents[0]))) {
                        openLotteryWindow(objMemberWhoExists, corpseInv);
                    } else if (numContents == 1 && isCashLootItem(contents[0])) {
                        return true;
                    } else {
                        string_id emptyCorpse = new string_id(group.GROUP_STF, "corpse_empty");
                        sendSystemMessage(objMemberWhoExists, emptyCorpse);
                        lootAiCorpse(transferer, corpseId);
                        return false;
                    }
                }
                numWindows = objMembersWhoExist.length;
                setObjVar(corpseId, "numWindowsOpen", numWindows);
            }
            if (numWindows > 0)
            {
                dictionary lotto = new dictionary();
                lotto.put("player", transferer);
                lotto.put("corpseInv", corpseInv);
                messageTo(corpseId, "fireLotteryPulse", lotto, 2, true);
                return false;
            }
        }
        if (groupLootType == 3)
        {
            return true;
        }
        return true;
    }
    public static boolean doGroupLootAllCheck(obj_id player, obj_id corpseId) throws InterruptedException
    {
        if (player != null && corpseId != null)
        {
            obj_id leader = group.getLeader(player);
            obj_id team = group.getGroupObject(leader);
            int lootType = getGroupLootRule(team);
            obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(team);
            if (objMembersWhoExist == null)
            {
                return true;
            }
            int number = objMembersWhoExist.length;
            if (number < 1)
            {
                return true;
            }
            obj_id corpseInv = utils.getInventoryContainer(corpseId);
            if (corpseInv == null)
            {
                return false;
            }
            obj_id[] contents = getContents(corpseInv);
            if (contents == null)
            {
                return false;
            }
            int numContents = contents.length;
            if (lootType == 0)
            {
                setGroupLootRule(team, group.FREE_FOR_ALL);
                return true;
            }
            if (lootType == 1)
            {
                obj_id master = getGroupMasterLooterId(team);
                String masterName = getEncodedName(master);
                if (master == player)
                {
                    return true;
                }
                else if (!isIdValid(master))
                {
                    return true;
                }
                else 
                {
                    prose_package pp = new prose_package();
                    string_id masterMsg = new string_id(group.GROUP_STF, "master_only");
                    pp = prose.setStringId(pp, masterMsg);
                    pp = prose.setTO(pp, masterName);
                    sendSystemMessageProse(player, pp);
                    return false;
                }
            }
            else if (lootType == 2)
            {
                if (hasObjVar(corpseId, "autoLootComplete"))
                {
                    return true;
                }
                if (hasObjVar(corpseId, "numWindowsOpen"))
                {
                    return false;
                }
                for (obj_id objMemberWhoExists : objMembersWhoExist) {
                    if (numContents > 0 && !(numContents == 1 && isCashLootItem(contents[0]))) {
                        openLotteryWindow(objMemberWhoExists, corpseInv);
                    } else if (numContents == 1 && isCashLootItem(contents[0])) {
                        return true;
                    } else {
                        string_id emptyCorpse = new string_id(group.GROUP_STF, "corpse_empty");
                        sendSystemMessage(objMemberWhoExists, emptyCorpse);
                        lootAiCorpse(player, corpseId);
                        return false;
                    }
                }
                setObjVar(corpseId, "numWindowsOpen", objMembersWhoExist.length);
                dictionary lotto = new dictionary();
                lotto.put("player", player);
                lotto.put("corpseInv", corpseInv);
                messageTo(corpseId, "fireLotteryPulse", lotto, 2, true);
                return false;
            }
            else if (lootType == 3)
            {
                return true;
            }
            else 
            {
                string_id cantLoot = new string_id(group.GROUP_STF, "no_loot_group");
                sendSystemMessage(player, cantLoot);
                return false;
            }
        }
        return false;
    }

    public static void sendGroupLootSystemMessage(obj_id item, obj_id winner, String stf, String message) throws InterruptedException
    {
        sendGroupLootSystemMessage(item, winner, stf, message, false);
    }
    public static void sendGroupLootSystemMessage(obj_id item, obj_id winner, String stf, String message, boolean skipWinner) throws InterruptedException
    {
        if (group.isGrouped(winner)) {
            obj_id team = getGroupObject(winner);
            obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(team);
            prose_package pp = new prose_package();
            string_id lootMsg = new string_id(stf, message);
            pp = prose.setStringId(pp, lootMsg);
            pp = prose.setTO(pp, item);
            pp = prose.setTT(pp, winner);
            if(objMembersWhoExist != null) {
                if (!skipWinner) {
                    for (obj_id objMemberWhoExists : objMembersWhoExist) {
                        sendSystemMessageProse(objMemberWhoExists, pp);
                    }
                } else {
                    for (obj_id objMemberWhoExists : objMembersWhoExist) {
                        if (objMemberWhoExists != winner) {
                            sendSystemMessageProse(objMemberWhoExists, pp);
                        }
                    }
                }
            }
        }
    }
    public static void setAutoLootComplete(obj_id winner, obj_id corpse, obj_id item) throws InterruptedException
    {
        setObjVar(corpse, "autoLootComplete", 1);
        setObjVar(item, "pickupable", winner);
    }
    public static obj_id chooseRandomLootPlayerFromGroup(obj_id corpse, obj_id player) throws InterruptedException
    {
        obj_id team = getGroupObject(player);
        obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(team);
        if(objMembersWhoExist != null) {
            int teamNumber = objMembersWhoExist.length;
            if (hasObjVar(corpse, "autoLootComplete")) {
                obj_id corpseInv = utils.getInventoryContainer(corpse);
                queueCommand(player, (1880585606), corpseInv, "", COMMAND_PRIORITY_DEFAULT);
            }
            if (teamNumber > 0) {
                int which = rand(0, teamNumber - 1);
                player = objMembersWhoExist[which];
            }
        }
        return player;
    }
    public static boolean isMasterLooter(obj_id toTest, boolean sendMessage) throws InterruptedException
    {
        obj_id team = getGroupObject(toTest);
        if (team != null)
        {
            obj_id masterLooter = getGroupMasterLooterId(team);
            String masterName = getEncodedName(masterLooter);
            if (toTest == masterLooter)
            {
                return true;
            }
            else if (sendMessage)
            {
                prose_package pp = new prose_package();
                string_id masterMsg = new string_id(group.GROUP_STF, "master_only");
                pp = prose.setStringId(pp, masterMsg);
                pp = prose.setTO(pp, masterName);
                sendSystemMessageProse(toTest, pp);
            }
        }
        return false;
    }
    public static void lootAiCorpse(obj_id self, obj_id target) throws InterruptedException
    {
        if (isIdValid(target)
                && isMob(target)
                && isIncapacitated(target)
                && !isIncapacitated(self)
                && !pet_lib.isPet(target)
                && hasObjVar(target, "readyToLoot")
                && corpse.hasLootPermissions(target, self)
                && corpse.lootAICorpse(self, target)) {
            messageTo(target, "handleCorpseEmpty", null, 0, true);
        }
    }
    public static obj_id findItemToStack(obj_id item) throws InterruptedException
    {
        obj_id container = getContainedBy(item);
        if (!isIdValid(container))
        {
            return null;
        }
        String itemStaticName = getStaticItemName(item);
        if (itemStaticName == null)
        {
            itemStaticName = "";
        }
        obj_id[] likeItemsInContainer = utils.getAllItemsInContainerByTemplate(container, getTemplateName(item), false);
        if ((likeItemsInContainer != null) && (likeItemsInContainer.length > 0))
        {
            for (obj_id likeItem : likeItemsInContainer) {
                String curItemStaticName = getStaticItemName(likeItem);
                if (curItemStaticName == null) {
                    curItemStaticName = "";
                }
                if (item != likeItem && curItemStaticName.equals(itemStaticName)) {
                    return likeItem;
                }
            }
        }
        return null;
    }
    public static void stackItem(obj_id objSourceItem, obj_id objDestinationItem) throws InterruptedException
    {
        int sourceCount = getCount(objSourceItem);
        if (sourceCount == 0)
        {
            sourceCount = 1;
        }
        int intCount = getCount(objDestinationItem);
        if (intCount == 0)
        {
            intCount = 1;
        }
        intCount = intCount + sourceCount;
        setCount(objDestinationItem, intCount);
        destroyObject(objSourceItem);
        obj_id container = getContainedBy(objDestinationItem);
        if (utils.hasScriptVar(container, "theft_in_progress"))
        {
            notifyThiefOfItemStolen(container, objDestinationItem);
        }
    }
    public static boolean setupLootItems(obj_id objCreature) throws InterruptedException
    {
        obj_id objContainer = utils.getInventoryContainer(objCreature);
        if (!hasObjVar(objCreature, "loot.lootTable"))
        {
            LOG("npe", "No Loot Table on " + objCreature + " of template " + getTemplateName(objCreature));
            return false;
        }
        String strTable = getStringObjVar(objCreature, "loot.lootTable");
        int intLevel = getIntObjVar(objCreature, "intCombatDifficulty");
        int intItems = getIntObjVar(objCreature, "loot.numItems");
        return makeLootInContainer(objContainer, strTable, intItems, intLevel);
    }
    public static boolean setupLootItems(obj_id objCreature, int intItems) throws InterruptedException
    {
        obj_id objContainer = utils.getInventoryContainer(objCreature);
        if (!hasObjVar(objCreature, "loot.lootTable"))
        {
            LOG("npe", "No Loot Table on " + objCreature + " of template " + getTemplateName(objCreature));
            return false;
        }
        String strTable = getStringObjVar(objCreature, "loot.lootTable");
        int intLevel = getIntObjVar(objCreature, "intCombatDifficulty");
        return makeLootInContainer(objContainer, strTable, intItems, intLevel);
    }
    public static boolean makeLootInContainer(obj_id objContainer, String strTable, int intItems, int intLevel) throws InterruptedException
    {
        boolean boolMadeLoot = false;
        String strRootItems = "datatables/loot/loot_items/";
        String[] parse = split(strTable, ':');
        strTable = parse[0];
        strTable = "datatables/loot/loot_types/" + strTable + ".iff";
        String strItemsHeader = "strItems";
        if (parse.length == 2)
        {
            strItemsHeader = parse[1];
        }
        String[] strLootTypes = dataTableGetStringColumnNoDefaults(strTable, strItemsHeader);
        String[] strRequiredItems = null;
        if (dataTableHasColumn(strTable, "strRequiredItems"))
        {
            strRequiredItems = dataTableGetStringColumnNoDefaults(strTable, "strRequiredItems");
        }
        if ((strLootTypes == null) || (strLootTypes.length == 0))
        {
            return boolMadeLoot;
        }
        for (int intI = 0; intI < intItems; intI++)
        {
            String strItemTable = strLootTypes[rand(0, strLootTypes.length - 1)];
            String[] parseItem = split(strItemTable, ':');
            strItemTable = parseItem[0];
            strItemTable = strRootItems + strItemTable + ".iff";
            String strItemTypeHeader = "strItemType";
            if (parseItem.length == 2)
            {
                strItemTypeHeader = parseItem[1];
            }
            if (dataTableOpen(strItemTable)) {
                String[] strItems = dataTableGetStringColumnNoDefaults(strItemTable, strItemTypeHeader);
                if (strItems != null && strItems.length > 0)
                {
                    String strLootToMake = strItems[rand(0, strItems.length - 1)];
                    createLootItem(objContainer, strLootToMake, intLevel);
                    boolMadeLoot = true;
                }
            }
        }
        if (strRequiredItems != null && strRequiredItems.length > 0)
        {
            for (String strRequiredItem : strRequiredItems) {
                createLootItem(objContainer, strRequiredItem, intLevel);
                boolMadeLoot = true;
            }
        }
        return boolMadeLoot;
    }
    public static obj_id makeRareLootItem(obj_id objContainer, String strTable) throws InterruptedException
    {
        String strRootItems = "datatables/loot/loot_items/";
        String[] parse = split(strTable, ':');
        strTable = parse[0];
        strTable = "datatables/loot/loot_types/" + strTable + ".iff";
        String strItemsHeader = "strItems";
        
        if (parse.length == 2) {
            strItemsHeader = parse[1];
        }
        
        String[] strLootTypes = dataTableGetStringColumnNoDefaults(strTable, strItemsHeader);
        String strItemTable = strLootTypes[rand(0, strLootTypes.length - 1)];
        String[] parseItem = split(strItemTable, ':');
        strItemTable = parseItem[0];
        strItemTable = strRootItems + strItemTable + ".iff";
        String strItemTypeHeader = "strItemType";
        
        if (parseItem.length == 2)
        {
            strItemTypeHeader = parseItem[1];
        }
        if (dataTableOpen(strItemTable)) {
            String[] strItems = dataTableGetStringColumnNoDefaults(strItemTable, strItemTypeHeader);
            if (strItems != null && strItems.length > 0)
            {
                String strLootToMake = strItems[rand(0, strItems.length - 1)];
                obj_id lootItem = createLootItem(objContainer, strLootToMake, 1);
                attachScript(lootItem, "systems.loot.rare_item");
                return lootItem;
            }
        }

        return null;
    }
    public static obj_id createLootItem(obj_id objContainer, String strLootToMake, int intLevel) throws InterruptedException
    {
        int intIndex = strLootToMake.indexOf(".iff");
        if (intIndex > -1)
        {
            obj_id lootItem = createObject(strLootToMake, objContainer, "");
            int got = getGameObjectType(lootItem);
            if (isGameObjectTypeOf(got, GOT_clothing) || got == GOT_armor_foot || got == GOT_armor_hand)
            {
                hue.hueObject(lootItem);
            }
            else if (isGameObjectTypeOf(got, GOT_weapon))
            {
                randomizeWeapon(lootItem, intLevel);
            }
            else if (got >= GOT_armor && got <= GOT_armor_arm)
            {
                randomizeArmor(lootItem, intLevel);
            }
            else if (got == GOT_misc_pharmaceutical)
            {
                randomizeMedicine(lootItem, intLevel);
            }
            else if ((got >= GOT_component && got <= GOT_component_weapon_ranged) || (got >= GOT_armor_layer && got <= GOT_armor_core) || got == GOT_component_new_armor)
            {
                randomizeComponent(lootItem, intLevel, objContainer);
            }
            if (utils.hasScriptVar(objContainer, "theft_in_progress"))
            {
                notifyThiefOfItemStolen(objContainer, lootItem);
            }
            return lootItem;
        }
        else if (strLootToMake.startsWith("loot_items/"))
        {
            String[] parseItem = split(strLootToMake, ':');
            if (parseItem.length == 2)
            {
                String itemDatatable = "datatables/loot/" + parseItem[0] + ".iff";
                String lootColumnHeader = parseItem[1];
                if (dataTableOpen(itemDatatable)) {
                    String[] lootList = dataTableGetStringColumnNoDefaults(itemDatatable, lootColumnHeader);
                    if (lootList != null && lootList.length > 0)
                    {
                        String lootItemsLoot = lootList[rand(0, lootList.length - 1)];
                        return createLootItem(objContainer, lootItemsLoot, intLevel);
                    }
                }
            }
        }
        else if (strLootToMake.startsWith("dynamic_"))
        {
            static_item.makeDynamicObject(strLootToMake, objContainer, intLevel);
        }
        else if (strLootToMake.startsWith("resource"))
        {
            utils.removeScriptVar(objContainer, "theft_in_progress");
        }
        else 
        {
            obj_id lootItem = static_item.createNewItemFunction(strLootToMake, objContainer);
            if (utils.hasScriptVar(objContainer, "theft_in_progress"))
            {
                notifyThiefOfItemStolen(objContainer, lootItem);
            }
            return lootItem;
        }
        return null;
    }
    public static boolean addMilkOrEgg(obj_id objCreature) throws InterruptedException
    {
        if (rand(1, 100) > 20)
        {
            return false;
        }
        String mobType = ai_lib.getCreatureName(objCreature);
        int niche = ai_lib.aiGetNiche(mobType);
        if (niche != NICHE_NPC)
        {
            return false;
        }
        String[] groceryItems = 
        {
            "milk_domesticated",
            "milk_wild",
            "meat_egg",
            "meat_egg"
        };
        obj_id objContainer = utils.getInventoryContainer(objCreature);
        int randItem = rand(0, 3);
        location here = getLocation(objCreature);
        int level = getLevel(objCreature);
        int amount = (int)(rand(1, 20) + (level * rand(1.0f, 2.0f)));
        resource.createRandom(groceryItems[randItem], amount, here, objContainer);
        return true;
    }
    public static void notifyThiefOfItemStolen(obj_id objContainer, obj_id loot) throws InterruptedException
    {
        if (utils.hasScriptVar(objContainer, "theft_in_progress"))
        {
            obj_id thief = getContainedBy(objContainer);
            if (isPlayer(thief))
            {
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("spam", "stolen_item"));
                pp = prose.setTT(pp, loot);
                sendSystemMessageProse(thief, pp);
            }
            utils.removeScriptVar(objContainer, "theft_in_progress");
        }
    }
    public static boolean addBeastEnzymes(obj_id creature) throws InterruptedException
    {
        if (storyteller.isStorytellerNpc(creature))
        {
            return false;
        }
        int chanceSpawn = rand(1, 100);
        if (chanceSpawn >= BASE_CHANCE_FOR_ENZYME && !hasObjVar(creature, "qa.makeEnzyme"))
        {
            return false;
        }
        int type = 1;
        obj_id inv = utils.getInventoryContainer(creature);
        obj_id enzyme = createObject("object/tangible/loot/beast/enzyme_" + type + ".iff", inv, "");
        int level = ai_lib.getLevel(creature);
        if (isIdValid(enzyme) && exists(enzyme))
        {
            setObjVar(enzyme, "beast.enzyme.level", level);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean addChronicleLoot(obj_id target) throws InterruptedException
    {
        String creatureName = ai_lib.getCreatureName(target);
        if (creatureName == null || creatureName.length() < 1)
        {
            return false;
        }
        dictionary creatureRow = dataTableGetRow(CREATURES_TABLE, creatureName);
        if(creatureRow == null){
            CustomerServiceLog("chroniclesLoot", "FIX ME: Add creature (" + creatureName + ") to the creatures table (" + CREATURES_TABLE + ")!");
            return false;
        }

        int mobLevel = getIntObjVar(target, "intCombatDifficulty");
        obj_id mobInv = utils.getInventoryContainer(target);
        int chronicleLootChance = creatureRow.getInt("chronicleLootChance");
        String configChance_string = getConfigSetting("GameServer", "chroniclesLootChanceOverride");
        if (configChance_string != null && configChance_string.length() > 0)
        {
            int configChance = utils.stringToInt(configChance_string);
            if (configChance > 0)
            {
                chronicleLootChance = configChance;
            }
        }
        int chronicleLootRoll = rand(1, 100);
        if (chronicleLootRoll <= chronicleLootChance)
        {
            String lootCatergoryChoice = "";
            int relicCount = rand(1, 3);
            String chronicleLootData = creatureRow.getString("chronicleLootCategory");
            if (chronicleLootData != null && chronicleLootData.length() > 0 && !chronicleLootData.equals("no_loot"))
            {
                String[] rawChronicleLootCategories = split(chronicleLootData, ',');
                Vector chronicleLootCategories = new Vector();
                chronicleLootCategories.setSize(0);
                for (String rawChronicleLootCategory : rawChronicleLootCategories) {
                    if (!rawChronicleLootCategory.equals("rare")) {
                        utils.addElement(chronicleLootCategories, rawChronicleLootCategory);
                    }
                }
                int lootOptions = chronicleLootCategories.size();
                int rareLootRoll = rand(1, 100);
                if (rareLootRoll == 1)
                {
                    lootCatergoryChoice = "rare";
                }
                else if (rareLootRoll < 6)
                {
                    lootCatergoryChoice = "comm_message";
                    relicCount = rand(6, 9);
                }
                else if (rareLootRoll < 10)
                {
                    lootCatergoryChoice = "goto_task";
                    relicCount = rand(6, 9);
                }
                else 
                {
                    int lootCategoryChoiceRoll = rand(0, lootOptions - 1);
                    lootCatergoryChoice = ((String)chronicleLootCategories.get(lootCategoryChoiceRoll));
                }
            }
            if (lootCatergoryChoice.length() > 0)
            {
                String relicName = getChronicleRelicLootOfCategory(lootCatergoryChoice);
                if (relicName != null && relicName.length() > 0)
                {
                    obj_id relic = static_item.createNewItemFunction(relicName, mobInv, relicCount);
                    pgc_quests.logRelic(obj_id.NULL_ID, obj_id.NULL_ID, "Chronicles Relic loot created on creature=(" + target + "," + creatureName + "," + getName(target) + "); relic=(" + relicName + ", count:" + relicCount + ").");
                    return true;
                }
            }
        }
        int fragmentLootChance = pgc_quests.PGC_CHRONICLE_BASE_FRAGEMENT_CHANCE;
        String configFragChance_string = getConfigSetting("GameServer", "fragmentLootChanceOverride");
        if (configFragChance_string != null && configFragChance_string.length() > 0)
        {
            int configFragChance = utils.stringToInt(configFragChance_string);
            if (configFragChance > 0)
            {
                fragmentLootChance = configFragChance;
            }
        }
        int fragmentLootRoll = rand(1, 100);
        if (fragmentLootRoll <= fragmentLootChance)
        {
            int fragmentCount = 1 + rand(0, mobLevel / 10);
            obj_id fragment = static_item.createNewItemFunction(pgc_quests.PGC_CHRONICLES_RELIC_FRAGMENT, mobInv, fragmentCount);
            pgc_quests.logRelic(obj_id.NULL_ID, obj_id.NULL_ID, "Chronicles Relic Fragment loot created on creature=(" + target + "," + creatureName + "," + getName(target) + "); fragment=" + pgc_quests.PGC_CHRONICLES_RELIC_FRAGMENT + ", count:" + fragmentCount + ").");
            return true;
        }
        return false;
    }
    public static String getChronicleRelicLootOfCategory(String lootCategoryChoice) throws InterruptedException
    {
        String relic = "";
        Vector tokenReferences = new Vector();
        tokenReferences.setSize(0);
        int num_items = dataTableGetNumRows(storyteller.STORYTELLER_DATATABLE);
        for (int i = 0; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, i);
            if (row != null && !row.isEmpty())
            {
                String row_lootCategory = row.getString("relicLootCatergory");
                String[] relicLootCategories = split(row_lootCategory, ',');
                for (String relicCategory : relicLootCategories) {
                    if (relicCategory.equals(lootCategoryChoice)) {
                        tokenReferences = utils.addElement(tokenReferences, row.getString("name"));
                    }
                }
            }
        }
        if (tokenReferences != null && tokenReferences.size() > 0)
        {
            relic = ((String)tokenReferences.get(rand(0, tokenReferences.size() - 1)));
        }
        if (lootCategoryChoice.equals("goto_task"))
        {
            int genericChance = rand(1, 3);
            if (genericChance == 3)
            {
                relic = pgc_quests.PGC_RELIC_OBJ_GOTO_GENERIC_LOC;
            }
        }
        return relic;
    }
    public static obj_id chroniclesCraftingLootDrop(obj_id player) throws InterruptedException
    {
        if (hasToggledChroniclesLootOff(player))
        {
            return obj_id.NULL_ID;
        }
        return chroniclesNonCorpseLootDrop(player, "crafting", pgc_quests.PGC_CHRONICLE_BASE_CRAFTING_LOOT_CHANCE, "crafting");
    }
    public static obj_id chroniclesPvpLootDrop(obj_id player) throws InterruptedException
    {
        if (hasToggledChroniclesLootOff(player))
        {
            return obj_id.NULL_ID;
        }
        return chroniclesNonCorpseLootDrop(player, "pvp", pgc_quests.PGC_CHRONICLE_BASE_PVP_LOOT_CHANCE, "pvping");
    }
    public static obj_id chroniclesNonCorpseLootDrop(obj_id player, String relicCategory, int relicChance, String activityType) throws InterruptedException
    {
        String configChance_string = getConfigSetting("GameServer", "chroniclesLootChanceOverride");
        if (configChance_string != null && configChance_string.length() > 0)
        {
            int configChance = utils.stringToInt(configChance_string);
            if (configChance > 0)
            {
                relicChance = configChance;
            }
        }
        int lootRoll = rand(1, 100);
        if (lootRoll <= relicChance)
        {
            String relicName = getChronicleRelicLootOfCategory(relicCategory);
            if (relicName != null && relicName.length() > 0)
            {
                int relicCount = rand(1, 3);
                obj_id playerInv = utils.getInventoryContainer(player);
                obj_id relic = static_item.createNewItemFunction(relicName, playerInv, relicCount);
                if (isIdValid(relic))
                {
                    pgc_quests.sendPlacedInInventorySystemMessage(player, relic);
                    pgc_quests.logRelic(player, obj_id.NULL_ID, "Player looted a Chronicles Relic while " + activityType + ": relic name:" + relicName + " and count: " + relicCount);
                }
                return relic;
            }
        }
        return obj_id.NULL_ID;
    }
    public static boolean hasToggledChroniclesLootOff(obj_id player) throws InterruptedException
    {
        return hasObjVar(player, CHRONICLES_LOOT_TOGGLE_OBJVAR);
    }
    public static void disableChroniclesLoot(obj_id player) throws InterruptedException
    {
        setObjVar(player, CHRONICLES_LOOT_TOGGLE_OBJVAR, true);
    }
    public static void enableChroniclesLoot(obj_id player) throws InterruptedException
    {
        removeObjVar(player, CHRONICLES_LOOT_TOGGLE_OBJVAR);
    }
    public static boolean addCollectionLoot(obj_id target) throws InterruptedException
    {
        return addCollectionLoot(target, false, null);
    }
    public static boolean addCollectionLoot(obj_id target, boolean theftBool, obj_id thief) throws InterruptedException
    {
        String creatureName = ai_lib.getCreatureName(target);
        int collectionLootChance = rand(1, 100);
        dictionary creatureRow = dataTableGetRow(CREATURES_TABLE, creatureName);
        if(creatureRow == null || creatureRow.isEmpty()){
            CustomerServiceLog("chroniclesLoot", "FIX ME: Add creature (" + creatureName + ") to the creatures table (" + CREATURES_TABLE + ")!");
            return false;
        }
        int rollBaseChance = creatureRow.getInt("collectionRoll");
        rollBaseChance *= COL_LOOT_MULTIPLIER_ON;
        if (creatureRow.isEmpty())
        {
            return false;
        }
        if (collectionLootChance <= rollBaseChance)
        {
            String myCollectionLoot = creatureRow.getString("collectionLoot");
            if (myCollectionLoot == null || myCollectionLoot.equals("no_loot"))
            {
                return false;
            }
            else 
            {
                String[] collectionsInColumn = split(myCollectionLoot, ',');
                int collectionIndex = rand(0, (collectionsInColumn.length - 1));
                myCollectionLoot = collectionsInColumn[collectionIndex];
                String[] lootArray = dataTableGetStringColumnNoDefaults(COLLECTIONS_LOOT_TABLE, myCollectionLoot);
                int max = lootArray.length - 1;
                int idx = rand(0, max);
                String lootToGrant = lootArray[idx];
                if (theftBool)
                {
                    if (isIdValid(thief))
                    {
                        obj_id thiefInv = utils.getInventoryContainer(thief);
                        utils.setScriptVar(thiefInv, "theft_in_progress", 1);
                        obj_id lootItem = static_item.createNewItemFunction(lootToGrant, thiefInv);
                        notifyThiefOfItemStolen(thiefInv, lootItem);
                        return true;
                    }
                    return false;
                }
                obj_id mobInv = utils.getInventoryContainer(target);
                obj_id lootItem = static_item.createNewItemFunction(lootToGrant, mobInv);
                if (isIdValid(lootItem) && exists(lootItem))
                {
                    CustomerServiceLog("CollectionLootChannel: ", "LootSuccessful: " + creatureName + "(" + target + ")" + " successfully dropped: " + lootToGrant + "(" + lootItem + ")");
                    return true;
                }
                else 
                {
                    CustomerServiceLog("CollectionLootChannel: ", "BrokenLoot: " + creatureName + "(" + target + ")" + " is having an issue dropping: " + lootToGrant + " Invalid ID or Does not exist.");
                    return false;
                }
            }
        }
        return false;
    }
    public static String getFactionalCollectionItem(String faction) throws InterruptedException
    {
        blog("getFactionalCollectionItem - arrival in function.  Faction of winner is: " + faction);
        if (faction == null || faction.equals(""))
        {
            return null;
        }
        blog("getFactionalCollectionItem - Faction check 1: " + faction + "*");
        if (!faction.equals("Rebel") && !faction.equals("Imperial"))
        {
            return null;
        }
        blog("getFactionalCollectionItem - Faction check 2");
        Vector factionCols = new Vector();
        factionCols.setSize(0);
        int randColInt = 0;
        String randColStr = "";
        String[] allLootColumns = dataTableGetColumnNames(COLLECTIONS_PVP_LOOT_TABLE);
        if (allLootColumns == null || allLootColumns.length <= 0)
        {
            return null;
        }
        blog("getFactionalCollectionItem - allLootColumns.length: " + allLootColumns.length);
        for (String lootColumn : allLootColumns) {
            if (lootColumn.startsWith(toLower(faction))) {
                blog("getFactionalCollectionItem - found a column I am adding to col list");
                utils.addElement(factionCols, lootColumn);
            }
        }
        blog("getFactionalCollectionItem - factionCols.length: " + factionCols.size());
        if (factionCols.size() <= 0)
        {
            return null;
        }
        if (factionCols.size() == 1)
        {
            blog("getFactionalCollectionItem - randColStr: " + factionCols.get(randColInt));
            randColStr = ((String)factionCols.get(randColInt));
        }
        else 
        {
            randColInt = rand(0, factionCols.size() - 1);
            blog("getFactionalCollectionItem - randColInt: " + randColInt);
            randColStr = ((String)factionCols.get(randColInt));
            blog("getFactionalCollectionItem - randColStr: " + randColStr);
        }
        if (randColStr == null || randColStr.equals(""))
        {
            return null;
        }
        String[] colLoot = dataTableGetStringColumnNoDefaults(COLLECTIONS_PVP_LOOT_TABLE, randColStr);
        if (colLoot == null || colLoot.length <= 0)
        {
            return null;
        }
        blog("getFactionalCollectionItem - colLoot.length: " + colLoot.length);
        if (colLoot.length == 1)
        {
            return colLoot[0];
        }
        int randLootInt = rand(0, colLoot.length - 1);
        blog("getFactionalCollectionItem - randLootInt: " + randLootInt);
        blog("getFactionalCollectionItem - RETURNING: " + colLoot[randLootInt]);
        return colLoot[randLootInt];
    }
    public static String getAppropriatePvpRankCollectible(String faction, int rank) throws InterruptedException
    {
        blog("getAppropriatePvpRankCollectible - arrival in function. Faction: " + faction + " rank: " + rank);
        if (faction == null || faction.equals(""))
        {
            return null;
        }
        blog("getAppropriatePvpRankCollectible - Faction checked 1: " + faction + "*");
        if (!faction.equals("Rebel") && !faction.equals("Imperial"))
        {
            return null;
        }
        blog("getAppropriatePvpRankCollectible - Faction checked 2");
        if (rank < 6 || rank > 12)
        {
            return null;
        }
        blog("getFactionalCollectionItem - faction: " + faction);
        blog("getFactionalCollectionItem - faction: " + rank);
        Vector pvpCols = new Vector();
        pvpCols.setSize(0);
        int randColInt = 0;
        String randColStr = "";
        String[] allLootColumns = dataTableGetColumnNames(COLLECTIONS_PVP_RANK_TABLE);
        if (allLootColumns == null || allLootColumns.length <= 0)
        {
            return null;
        }
        blog("getFactionalCollectionItem - allLootColumns.length: " + allLootColumns.length);
        for (String lootColumn : allLootColumns) {
            if (lootColumn.startsWith(toLower(faction))) {
                blog("getFactionalCollectionItem - found a column I wanted: ");
                utils.addElement(pvpCols, lootColumn);
            }
        }
        if (pvpCols.size() <= 0)
        {
            return null;
        }
        blog("getFactionalCollectionItem - pvpCols.length: " + pvpCols.size());
        if (pvpCols.size() == 1)
        {
            randColStr = ((String)pvpCols.get(randColInt));
        }
        else 
        {
            randColInt = rand(0, pvpCols.size() - 1);
            randColStr = ((String)pvpCols.get(randColInt));
        }
        blog("getFactionalCollectionItem - pvpCols[randColInt]: " + pvpCols.get(randColInt));
        blog("getFactionalCollectionItem - randColStr: " + randColStr);
        switch (rank)
        {
            case 7:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 0, randColStr);
            case 8:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 1, randColStr);
            case 9:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 2, randColStr);
            case 10:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 3, randColStr);
            case 11:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 4, randColStr);
            case 12:
            return dataTableGetString(COLLECTIONS_PVP_RANK_TABLE, 5, randColStr);
            default:
            return null;
        }
    }
    public static boolean rollRandomFactionalCollectible(obj_id victim, obj_id winner, int victimRank) throws InterruptedException
    {
        blog("rollRandomFactionalCollectible - arrival in rollRandomFactionalCollectible function");
        if (isIdNull(winner) || isIdNull(victim))
        {
            return false;
        }
        if (victimRank <= 0)
        {
            return false;
        }
        blog("rollRandomFactionalCollectible - about to roll.");
        int roll = rand(1, 100);
        int rollNeeded = COLLECTION_PVP_ROLL;
        if (isGod(winner))
        {
            rollNeeded = 101;
        }
        if (roll <= rollNeeded)
        {
            return false;
        }
        blog("rollRandomFactionalCollectible - won roll.");
        int rewardFunction = 1;
        if (victimRank > 6)
        {
            rewardFunction = rand(1, 2);
            blog("rollRandomFactionalCollectible - victim is officer.");
        }
        String faction = factions.getFaction(victim);
        String rewardItem = "";
        switch (rewardFunction)
        {
            case 1:
            blog("rollRandomFactionalCollectible - send to getFactionalCollectionItem.");
            rewardItem = getFactionalCollectionItem(faction);
            break;
            case 2:
            blog("rollRandomFactionalCollectible - send to getAppropriatePvpRankCollectible.");
            rewardItem = getAppropriatePvpRankCollectible(faction, victimRank);
            break;
        }
        if (rewardItem == null || rewardItem.equals(""))
        {
            blog("rollRandomFactionalCollectible - reward function returned a NULL.");
            return false;
        }
        blog("rollRandomFactionalCollectible - grant reward.");
        grantFactionalCollectible(winner, rewardItem);
        return true;
    }
    public static boolean grantFactionalCollectible(obj_id winner, String staticItem) throws InterruptedException
    {
        blog("grantFactionalCollectible - arrival in grantFactionalCollectible function");
        if (isIdNull(winner)
                || staticItem == null
                || staticItem.equals("")
                || !static_item.isStaticItem(staticItem))
        {
            return false;
        }
        obj_id pInv = utils.getInventoryContainer(winner);
        obj_id itemId = static_item.createNewItemFunction(staticItem, pInv);
        if (!isValidId(itemId) || !exists(itemId))
        {
            blog("grantFactionalCollectible - FAILED TO CREATE ITEM IN INVENTORY.");
            CustomerServiceLog("CollectionLootChannel", "Player " + getFirstName(winner) + "(" + winner + ") has failed to receive item: " + staticItem + ".");
            return false;
        }
        Vector items = new Vector();
        items.setSize(0);
        utils.addElement(items, itemId);
        blog("grantFactionalCollectible - SUCCESS, creating item in inventory.");
        prose_package pp = new prose_package();
        prose.setStringId(pp, SID_REWARD_ITEM);
        prose.setTT(pp, new string_id("static_item_n", getStaticItemName(itemId)));
        sendSystemMessageProse(winner, pp);
        CustomerServiceLog("CollectionLootChannel", "Player " + getFirstName(winner) + "(" + winner + ") has received item: " + staticItem + ".");
        return true;
    }
    public static obj_id addMeatlumpLumpsAsLoot(obj_id target, obj_id targetInventory, int cash) throws InterruptedException
    {
        if (cash < 1
            || !isValidId(target)
            || !exists(target)
            || !isValidId(targetInventory)
            || !exists(targetInventory))
        {
            return null;
        }

        obj_id inv = utils.getInventoryContainer(target);
        if (inv == null)
        {
            return null;
        }
        obj_id cashItem = static_item.createNewItemFunction(MEATLUMP_LUMP, inv);
        if (!isIdValid(cashItem))
        {
            return null;
        }
        incrementCount(cashItem, cash);
        return cashItem;
    }
    public static boolean giveMeatlumpPuzzleLoot(obj_id player, boolean puzzleThreshold, boolean puzzleBuff) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isValidId(pInv) || !exists(pInv))
        {
            return false;
        }
        boolean luckyPlayer = luck.isLucky(player, 0.01f);
        int lumpAmount = rand(0, 2);
        blog("giveMeatlumpPuzzleLoot - lumpAmount: " + lumpAmount);
        if (luckyPlayer)
        {
            int lumpRoll = rand(0, 100);
            if (lumpRoll < 51)
            {
                lumpAmount++;
            }
            else if (lumpRoll < 91)
            {
                lumpAmount += 2;
            }
            else 
            {
                lumpAmount += 3;
            }
        }
        Vector items = new Vector();
        items.setSize(0);
        blog("giveMeatlumpPuzzleLoot - lucky player lumpAmount: " + lumpAmount);
        if (lumpAmount > 0)
        {
            obj_id lumpsReceived = addMeatlumpLumpsAsLoot(player, pInv, lumpAmount);
            blog("giveMeatlumpPuzzleLoot - lumpsReceived: " + lumpsReceived);
            if (lumpsReceived == null)
            {
                return false;
            }
            utils.addElement(items, lumpsReceived);
        }
        int lootItemAmount = rand(1, 3);
        if (rand(0, 100) > 99)
        {
            lootItemAmount += rand(0, 1);
        }
        if (luckyPlayer)
        {
            lootItemAmount += rand(0, 1);
        }
        else if (puzzleThreshold && (rand(0,100) > 95 || puzzleBuff))
        {
            lootItemAmount += rand(0,1);
        }
        for (int i = 0; i < lootItemAmount; i++)
        {
            int cols = 1;
            int randCol = rand(0, 100);
            if (randCol >= 70 && randCol <= 90)
            {
                cols = 3;
            }
            else if (randCol > 90)
            {
                cols = 5;
            }
            blog("giveMeatlumpPuzzleLoot - randCols: " + randCol);
            blog("giveMeatlumpPuzzleLoot - TOTAL cols: " + cols);
            String[] strLootItems = dataTableGetStringColumnNoDefaults(MEATLUMP_LOOT_TABLE, rand(0, cols));
            if (strLootItems == null || strLootItems.length == 0)
            {
                return false;
            }
            blog("giveMeatlumpPuzzleLoot - strLootItems.length: " + strLootItems.length);
            blog("giveMeatlumpPuzzleLoot - strLootItems.length: " + strLootItems.length);
            String strLootToMake = strLootItems[rand(0, strLootItems.length - 1)];
            if (strLootToMake == null || strLootToMake.equals(""))
            {
                return false;
            }
            blog("giveMeatlumpPuzzleLoot - strLootToMake: " + strLootToMake);
            obj_id lootItem = createLootItem(pInv, strLootToMake, 0);
            if (!isValidId(lootItem))
            {
                return false;
            }
            utils.addElement(items, lootItem);
            blog("giveMeatlumpPuzzleLoot - createLootItem success");
        }
        blog("giveMeatlumpPuzzleLoot - items.size(): " + items.size());
        obj_id[] finalLootList = new obj_id[items.size()];
        items.toArray(finalLootList);
        showLootBox(player, finalLootList);
        return true;
    }
    public static boolean playerForaging(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("foraging", "Foraging System could not complete because player OID: " + player + " is invalid or no longer exists.");
            return false;
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " is about to start foraging rolls.");
        obj_id pInv = utils.getInventoryContainer(player);
        if (!isValidId(pInv) || !exists(pInv))
        {
            CustomerServiceLog("foraging", "Foraging System could not find inventory for player OID: " + player + ". Inventory container is invalid or no longer exists.");
            return false;
        }
        if (getVolumeFree(pInv) <= 0)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has a full inventory, foraging rolls aborted.");
            sendSystemMessage(player, SID_FULL_INVENTORY);
            return false;
        }
        location curLoc = getLocation(player);
        String scene = getCurrentSceneName();
        forage_blog("scene: " + scene);
        Vector newListOfLocs = new Vector();
        newListOfLocs.setSize(0);
        int something = 50;
        int nothing = 50;
        int somethingMod = 0;
        int lootMod = 0;
        String bonusMessage = "";
        if (buff.hasBuff(player, "bm_truffle_pig"))
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has buff bm_truffle_pig and is receiving bonus to avoid getting nothing during forage session.");
            float trufflePigVal = buff.getEffectValue("bm_truffle_pig", 2);
            bonusMessage += " Pet";
            forage_blog("playerForaging: bm_truffle_pig increased somethingMod from: " + somethingMod + " to: " + (somethingMod + trufflePigVal));
            somethingMod += (int)trufflePigVal;
        }
        if (buff.hasBuff(player, "ice_cream_forage_buff"))
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has buff ice_cream_forage_buff and is receiving bonus to avoid getting nothing during forage session.");
            float iceCreamVal = buff.getEffectValue("ice_cream_forage_buff", 2);
            if (bonusMessage.equals(""))
            {
                bonusMessage += " Ice Cream";
            }
            else 
            {
                bonusMessage += ", Ice Cream";
            }
            forage_blog("playerForaging: ice_cream_forage_buff increased somethingMod from: " + somethingMod + " to: " + (somethingMod + iceCreamVal));
            somethingMod += (int)iceCreamVal;
        }
        if (buff.hasBuff(player, "treasure_forage"))
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has buff treasure_forage and is receiving bonus to avoid getting nothing during forage session.");
            float treasureVal = buff.getEffectValue("treasure_forage", 1);
            if (bonusMessage.equals(""))
            {
                bonusMessage += " Kommerken Steak";
            }
            else 
            {
                bonusMessage += ", Kommerken Steak";
            }
            forage_blog("playerForaging: treasure_forage increased somethingMod from: " + somethingMod + " to: " + (somethingMod + treasureVal));
            somethingMod += (int)treasureVal;
        }
        boolean isLuckyPlayer = luck.isLucky(player, 0.10f);
        if (isLuckyPlayer)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " got really lucking and is receiving bonus to avoid getting nothing during forage session.");
            if (bonusMessage.equals(""))
            {
                bonusMessage += " Luck";
            }
            else 
            {
                bonusMessage += ", Luck";
            }
            forage_blog("playerForaging: isLuckyPlayer increased somethingMod from: " + somethingMod + " to: " + (somethingMod + LUCKY_FIND_MODIFIER));
            somethingMod += (int)LUCKY_FIND_MODIFIER;
        }
        if (!bonusMessage.equals(""))
        {
            prose_package pp = new prose_package();
            string_id msg = new string_id(FORAGING_STF, "forage_bonus_mesage");
            pp = prose.setStringId(pp, msg);
            pp = prose.setTO(pp, bonusMessage);
            sendSystemMessageProse(player, pp);
        }
        if (somethingMod > 0)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has one or more buffs and/or luck modifiers. The forage system is modifying the players chance of receiving nothing.");
            nothing -= somethingMod;
            if (nothing < 0)
            {
                nothing = 0;
            }
            something += somethingMod;
            if (something > 100)
            {
                something = 100;
            }
            forage_blog("playerForaging: Nothing: " + nothing);
            forage_blog("playerForaging: Something: " + something);
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has a " + nothing + "% chance of receiving nothing within the foraging system.");
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has a " + something + "% chance of receiving something within the foraging system.");
        }
        int initialRoll = rand(1, 100);

        forage_blog("playerForaging: initialRoll: " + initialRoll);
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has rolled a " + initialRoll + ". If this number is less than " + nothing + " the player gets nothing from the foraging system.");
        if (initialRoll <= nothing)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has rolled a " + initialRoll + " and gets nothing from the foraging system.");
            forage_blog("YOU ROLLED: " + initialRoll + " on initial roll and get nothing. You needed to get higher than: " + nothing);
            sendSystemMessage(player, FOUND_NOTHING);
            saveForageLocationOnPlayer(player, newListOfLocs, curLoc);
            return true;
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has rolled a " + initialRoll + " and gets some yet to be determined item from the foraging system.");
        forage_blog("playerForaging: YOU GET SOMETHING");
        int enzymeMod = 0;
        int treasMod = 0;
        String buffMsg = "";
        if (buff.hasBuff(player, "bm_truffle_pig"))
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has the bm_truffle_pig buff and will receive an extra roll for ENZYMES within the foraging system.");
            float enzymeBuff = buff.getEffectValue("bm_truffle_pig", 3);
            forage_blog("playerForaging: bm_truffle_pig increased enzymeMod score from: " + enzymeMod + " to: " + (enzymeMod + enzymeBuff));
            enzymeMod += (int)enzymeBuff;
        }
        if (buff.hasBuff(player, "treasure_forage"))
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has the treasure_forage buff and will receive an extra roll for TREASURE MAPS within the foraging system.");
            float treasureValMap = buff.getEffectValue("treasure_forage", 2);
            treasMod += (int)treasureValMap;
        }
        boolean buffRoll = false;
        if (enzymeMod > 0)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " is NOW receiving their extra roll for ENZYMES within the foraging system.");
            forage_blog("Player receives extra roll because of buffs for: enzyme");
            buffMsg += " Plant Enzymes";
            int enzymRoll = rand(1, 100);

            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + enzymRoll + ". If this number is less than, or equal to " + enzymeMod + ", the player will get an enzyme.");
            if (enzymRoll <= enzymeMod)
            {
                CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + enzymRoll + " and received an enzyme.");
                forage_blog("rareObject reward: enzyme");
                obj_id enzyme = createObject("object/tangible/loot/beast/enzyme_2.iff", pInv, "");
                sendSystemMessage(player, FOUND_ENZYME);
                saveForageLocationOnPlayer(player, newListOfLocs, curLoc);
                buffRoll = true;
            }
        }
        if (treasMod > 0)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " is NOW receiving their extra roll for TREASURE MAPS within the foraging system.");
            forage_blog("Player receives extra roll because of buffs for: treasure map");
            if (buffMsg.equals(""))
            {
                buffMsg += " Treasure Map";
            }
            else 
            {
                buffMsg += ", Treasure Map";
            }
            int treasureRoll = rand(1, 100);
            if (treasureRoll <= 0)
            {
                treasureRoll = 0;
            }
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + treasureRoll + ". If this number is less than, or equal to " + treasMod + ", the player will get an enzyme.");
            if (treasureRoll <= treasMod)
            {
                CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + treasureRoll + " and received a treasure map.");
                forage_blog("rareObject reward: treasure");
                boolean objectReceived = loot.getRareForagedTreasureMap(player, pInv);
                sendSystemMessage(player, FOUND_TREASURE);
                saveForageLocationOnPlayer(player, newListOfLocs, curLoc);
                buffRoll = true;
            }
        }
        if (!buffMsg.equals(""))
        {
            prose_package pp = new prose_package();
            string_id msg = new string_id(FORAGING_STF, "forage_buff_roll_mesage");
            pp = prose.setStringId(pp, msg);
            pp = prose.setTO(pp, buffMsg);
            sendSystemMessageProse(player, pp);
        }
        if (buffRoll)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has received either an ENZYME AND/OR a TREASURE MAP because of their buffs. The forage system is now exiting.");
            return true;
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has not received an enzyme or treasure map as a result of a buf so we will continue through the forage system.");
        forage_blog("Player moves on to get basic rolls");
        int enzLow = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_ENZYME, LOOT_LOW_COL);
        int enzHigh = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_ENZYME, LOOT_HIGH_COL);
        int compLow = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_COMPONENT, LOOT_LOW_COL);
        int compHigh = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_COMPONENT, LOOT_HIGH_COL);
        int wormLow = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_WORM, LOOT_LOW_COL);
        int wormHigh = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_WORM, LOOT_HIGH_COL);
        int baitLow = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_BAIT, LOOT_LOW_COL);
        int baitHigh = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_BAIT, LOOT_HIGH_COL);
        int treasLow = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_TREASURE, LOOT_LOW_COL);
        int treasHigh = dataTableGetInt(FORAGING_LOOT_ROLL_TABLE, LOOT_TREASURE, LOOT_HIGH_COL);
        if (enzLow < 0 || enzHigh < 0 || compLow < 0 || compHigh < 0 || wormLow < 0 || wormHigh < 0 || baitLow < 0 || baitHigh < 0 || treasLow < 0 || treasHigh < 0)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has corrupted data for one or more of the foraging data. Contact development.");
            return false;
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " will receive an enzyme if they roll between " + enzLow + " and " + enzHigh + ".");
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " will receive a component (ice cream fryer food) if they roll between " + compLow + " and " + compHigh + ".");
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " will receive a worm or theif enemy if they roll between " + wormLow + " and " + wormHigh + ".");
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " will receive a bait or bugs if they roll between " + baitLow + " and " + baitHigh + ".");
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " will receive a treasure map if they roll between " + treasLow + " and " + treasHigh + ".");
        int lootRoll = rand(1, 100);

        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + ". The system will now attempt to reward the player with the specific item won.");
        forage_blog("YOU ROLLED: " + lootRoll);
        if (lootRoll >= enzLow && lootRoll <= enzHigh)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + " and has received an ENZYME.");
            forage_blog("rareObject reward: enzyme");
            obj_id enzyme = createObject("object/tangible/loot/beast/enzyme_2.iff", pInv, "");
            sendSystemMessage(player, FOUND_ENZYME);
        }
        else if (lootRoll >= compLow && lootRoll <= compHigh)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + " and has received a COMPONENT.");
            forage_blog("rareObject reward: component");
            if (giveForagedCollectionObject(player, pInv, scene))
            {
                sendSystemMessage(player, FOUND_COMPONENT);
            }
        }
        else if (lootRoll >= wormLow && lootRoll <= wormHigh)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + " and has received a WORM/THEIF ENEMY.");
            forage_blog("rareObject reward: worm");
            int mobLevel = getLevel(player);
            String invis = stealth.getInvisBuff(player);
            if (invis != null)
            {
                stealth.checkForAndMakeVisibleNoRecourse(player);
            }
            String[] enemyList = dataTableGetStringColumnNoDefaults(FORAGING_ENEMY_TABLE, "enemy");
            int idx = rand(0, (enemyList.length - 1));
            obj_id mob = create.object(enemyList[idx], curLoc, mobLevel);
            setObjVar(mob, "player", player);
            attachScript(mob, FORAGE_ENEMY_SCRIPT);
            sendSystemMessage(player, FOUND_WORM);
        }
        else if (lootRoll >= treasLow && lootRoll <= treasHigh)
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + " and has received a TREASURE MAP.");
            forage_blog("rareObject reward: treasure map");
            loot.getRareForagedTreasureMap(player, pInv);
            sendSystemMessage(player, FOUND_TREASURE);
        }
        else 
        {
            CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " rolled " + lootRoll + " and has received BAIT.");
            forage_blog("reward: bait");
            String[] bait = dataTableGetStringColumnNoDefaults(FORAGING_RARE_TABLE, "ITEM_TANGIBLE");
            int itemIndex = rand(0, (bait.length - 1));
            forage_blog("Won BAIT: object/tangible/bait" + itemIndex);
            createObjectOverloaded("object/tangible/" + bait[itemIndex], pInv);
            sendSystemMessage(player, FOUND_SOMETHING);
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " should have received a reward object. The forage location will be stored and the forage session will be completed.");
        saveForageLocationOnPlayer(player, newListOfLocs, curLoc);
        return true;
    }
    public static int getRandomRareForagedLoot(obj_id self, int enzymeChance, int wormChance, int treasureMapChance, int componentChance) throws InterruptedException
    {
        forage_blog("getRandomRareForagedLoot init");
        forage_blog("getRandomRareForagedLoot:treasureMapChance: " + treasureMapChance);
        Vector rareItemList = new Vector();
        rareItemList.setSize(0);
        utils.addElement(rareItemList, ENZYME);
        utils.addElement(rareItemList, WORM);
        utils.addElement(rareItemList, TREASURE);
        utils.addElement(rareItemList, COMPONENT);
        forage_blog("getRandomRareForagedLoot:treasureMapChance: " + treasureMapChance);
        if (enzymeChance > 0)
        {
            int division = enzymeChance / RARE_COMPONENT_DIVISOR;
            for (int i = 0; i < division; ++i)
            {
                utils.addElement(rareItemList, ENZYME);
            }
        }
        if (wormChance > 0)
        {
            int division = wormChance / RARE_COMPONENT_DIVISOR;
            for (int i = 0; i < division; ++i)
            {
                utils.addElement(rareItemList, WORM);
            }
        }
        if (treasureMapChance > 0)
        {
            int division = treasureMapChance / SUPER_RARE_COMPONENT_DIVISOR;
            for (int i = 0; i < division; ++i)
            {
                utils.addElement(rareItemList, TREASURE);
            }
        }
        if (componentChance > 0)
        {
            int division = componentChance / RARE_COMPONENT_DIVISOR;
            for (int i = 0; i < division; ++i)
            {
                utils.addElement(rareItemList, COMPONENT);
            }
        }
        int listSize = rareItemList.size();
        forage_blog("rareItemList.size(): " + listSize);
        int randNum = rand(0, listSize - 1);
        forage_blog("randNum: " + randNum);
        forage_blog("Random return: " + rareItemList.get(randNum));
        CustomerServiceLog("foraging", "Player: " + getName(self) + " OID: " + self + "rolled against a foraging rare item list and received rare loot item category: " + rareItemList.get(randNum));
        forage_blog("getRandomRareForagedLoot do we have the scriptvar: " + utils.hasScriptVar(self, "qa.give_forage_data"));
        if (utils.hasScriptVar(self, "qa.give_forage_data") && isGod(self))
        {
            StringBuilder strForageFeedBack = new StringBuilder(utils.getStringScriptVar(self, "qa.strForageFeedBack"));
            strForageFeedBack.append("Categories: Enzyme = 0, Worm/Thief = 1, Component = 2, Treasure Map = 3\n\r");
            strForageFeedBack.append("Rare Item List Length: ").append(listSize).append("\n\r");
            for (int i = 0; i < listSize; ++i)
            {
                strForageFeedBack.append("Rare Item Number ").append(i).append(" is category: ").append(rareItemList.get(i)).append("\n\r");
            }
            strForageFeedBack.append("Winning Item Category: ").append(rareItemList.get(randNum)).append("\n\r\n\r");
            strForageFeedBack.append("Check Constants - The values should never change.\n\r");
            strForageFeedBack.append("DEFAULT_ENZYME_CHANCE: (40) " + DEFAULT_ENZYME_CHANCE + "\n\r");
            strForageFeedBack.append("DEFAULT_WORM_CHANCE: (40) " + DEFAULT_WORM_CHANCE + "\n\r");
            strForageFeedBack.append("DEFAULT_TREASURE_CHANCE: (0) " + DEFAULT_TREASURE_CHANCE + "\n\r");
            strForageFeedBack.append("DEFAULT_COMPONENT_CHANCE: (40) " + DEFAULT_COMPONENT_CHANCE + "\n\r");
            strForageFeedBack.append("ENZYME_MODIFIER: (0) " + ENZYME_MODIFIER + "\n\r");
            strForageFeedBack.append("TREASURE_MODIFIER: (0) " + TREASURE_MODIFIER + "\n\r");
            strForageFeedBack.append("PLAYER_DEFAULT_FIND_MODIFIER: (0) " + PLAYER_DEFAULT_FIND_MODIFIER + "\n\r");
            strForageFeedBack.append("LUCKY_FIND_MODIFIER: (6) " + LUCKY_FIND_MODIFIER + "\n\r");
            utils.setScriptVar(self, "qa.strForageFeedBack", strForageFeedBack.toString());
            utils.setScriptVar(self, "qa.strForageFeedBackCategory", (Integer) rareItemList.get(randNum));
        }
        return (Integer) rareItemList.get(randNum);
    }
    public static boolean getRareForagedTreasureMap(obj_id player, obj_id pInv) throws InterruptedException
    {
        forage_blog("getRareForagedTreasureMap init");
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        else if (!isIdValid(pInv) || !exists(pInv))
        {
            return false;
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " is rolling for a treasure map.");
        forage_blog("getRareForagedTreasureMap - rareObject reward: treasure map");
        String treasureMap = getPlayerTreasureMapString(player);
        if (treasureMap != null && !treasureMap.equals(""))
        {
            static_item.createNewItemFunction(treasureMap, pInv);
        }
        else 
        {
            forage_blog("getRareForagedTreasureMap - rareObject reward ERROR: treasure map level not found");
            static_item.createNewItemFunction("item_treasure_map_1_10", pInv);
        }
        CustomerServiceLog("foraging", "Player: " + getName(player) + " OID: " + player + " has won treasure map: " + treasureMap);
        forage_blog("getRareForagedTreasureMap do we have the scriptvar: " + utils.hasScriptVar(player, "qa.give_forage_data"));
        return true;
    }
    public static String getPlayerTreasureMapString(obj_id player) throws InterruptedException
    {
        if (!isValidId(player))
        {
            return null;
        }
        int playerLvl = getLevel(player);
        if (playerLvl <= 0)
        {
            return null;
        }
        String mapPrefix = "item_treasure_map_";
        String mapAppend = "1_10";
        if (playerLvl >= 81 && playerLvl <= 90)
        {
            mapAppend = "81_90";
        }
        else if (playerLvl >= 71 && playerLvl <= 80)
        {
            mapAppend = "71_80";
        }
        else if (playerLvl >= 61 && playerLvl <= 70)
        {
            mapAppend = "61_70";
        }
        else if (playerLvl >= 51 && playerLvl <= 60)
        {
            mapAppend = "51_60";
        }
        else if (playerLvl >= 41 && playerLvl <= 50)
        {
            mapAppend = "41_50";
        }
        else if (playerLvl >= 31 && playerLvl <= 40)
        {
            mapAppend = "31_40";
        }
        else if (playerLvl >= 21 && playerLvl <= 30)
        {
            mapAppend = "21_30";
        }
        else if (playerLvl >= 11 && playerLvl <= 20)
        {
            mapAppend = "11_20";
        }
        return mapPrefix + mapAppend;
    }
    public static boolean giveForagedCollectionObject(obj_id self, obj_id pInv, String scene) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        if ((scene == null) || scene.equals(""))
        {
            return false;
        }
        forage_blog("rareObject reward: IN giveCollectionObject");
        forage_blog("rareObject reward: scene: " + scene);
        boolean componentPlanet = false;
        for (String COMPONENT_PLANET : COMPONENT_PLANETS) {
            if (scene.startsWith(COMPONENT_PLANET)) {
                componentPlanet = true;
                break;
            }
        }
        if (!componentPlanet)
        {
            sendSystemMessage(self, FOUND_NOTHING);
            return false;
        }
        String[] component = dataTableGetStringColumnNoDefaults(FORAGING_RARE_TABLE, scene);
        if (component == null || component.length == 0)
        {
            return false;
        }
        forage_blog("rareObject reward: IN giveCollectionObject");
        int itemIndex = rand(0, (component.length - 1));
        forage_blog("rareObject reward: IN giveCollectionObject - itemIndex: " + itemIndex);
        String objTemplate = dataTableGetString(FORAGING_RARE_TABLE, itemIndex, scene);
        forage_blog("rareObject reward: IN giveCollectionObject - objTemplate: " + objTemplate);
        createObjectOverloaded(objTemplate, pInv);
        return true;
    }
    public static boolean saveForageLocationOnPlayer(obj_id self, Vector newListOfLocs, location curLoc) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "forage.listOfAlreadyForagedLocs"))
        {
            location[] oldListOfLocs = utils.getLocationArrayScriptVar(self, "forage.listOfAlreadyForagedLocs");
            for (location loc : oldListOfLocs) {
                utils.addElement(newListOfLocs, loc);
            }
        }
        utils.addElement(newListOfLocs, curLoc);
        utils.setScriptVar(self, "forage.listOfAlreadyForagedLocs", newListOfLocs);
        utils.setScriptVar(self, "forage.lastLocation", curLoc);
        return true;
    }
    public static boolean logForageData(obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        int loopSeed = 1;
        if (utils.hasScriptVar(player, "qa.loopseed"))
        {
            loopSeed = utils.getIntScriptVar(player, "qa.loopseed");
        }
        String strForageFeedBackLog = utils.getStringScriptVar(player, "qa.strForageFeedBack");
        if (strForageFeedBackLog == null || strForageFeedBackLog.equals(""))
        {
            sendSystemMessageTestingOnly(player, "The log was blank");
            return false;
        }
        int strForageFeedBackCategoryLog = utils.getIntScriptVar(player, "qa.strForageFeedBackCategory");
        if (strForageFeedBackCategoryLog < 0)
        {
            sendSystemMessageTestingOnly(player, "The Category Number was invalid");
            return false;
        }
        String categoryName = "";
        switch (strForageFeedBackCategoryLog)
        {
            case loot.ENZYME:
                categoryName = "ENZYME";
                break;
            case loot.WORM:
                categoryName = "WORMTHIEF";
                break;
            case loot.COMPONENT:
                categoryName = "COMPONENT";
                break;
            case loot.TREASURE:
                categoryName = "TREASURE" + getPlayerTreasureMapString(player);
                break;
            case loot.BAIT:
                categoryName = "BAIT";
                break;
            case loot.NOTHING:
                categoryName = "NOTHING";
                break;
            default:
                break;
        }
        if (categoryName.equals(""))
        {
            sendSystemMessageTestingOnly(player, "The Category Name was blank");
            return false;
        }
        int number = getGameTime() + loopSeed + rand(1, 100) + rand(1, 100);
        String title = "foraged" + categoryName + number;
        loopSeed++;
        utils.setScriptVar(player, "qa.loopseed", loopSeed);
        saveTextOnClient(player, title + ".txt", strForageFeedBackLog);
        return true;
    }
    public static boolean blog(String msg) throws InterruptedException
    {
        LOG("minigame", msg);
        return true;
    }
    public static boolean forage_blog(String msg) throws InterruptedException
    {
        if (FORAGE_LOGGING_ON)
        {
            LOG(FORAGE_LOGGING_CATEGORY, msg);
        }
        return true;
    }
    public static boolean addRareLoot(obj_id target) throws InterruptedException
    {
        // get the attacker who did the most damage.
        obj_id player = getObjIdObjVar(target, xp.VAR_TOP_GROUP);

        // make sure the attacker is a player.
        if(!isValidId(player) || !isPlayer(player)){
            return false;
        }

        // if they're AFK then skip to the next.
        if(isAwayFromKeyBoard(player)) return false;

        // get custom settings from the configuration
        boolean rlsEnabled = Boolean.parseBoolean(getConfigSetting("GameServer", "rlsEnabled"));
        if (!rlsEnabled) return false;

        // get RLS Chance - default: 1%
        String rlsc = getConfigSetting("GameServer", "rlsDropChance");
        double rlsChance = rlsc == null ? 0.005d : Double.parseDouble(rlsc) / 100;

        // did they qualify for a RLS chest?
        double rollValue = Math.random();
        if(rollValue > rlsChance){
            // did not qualify due to roll out of range.
            return false;
        }
        LOG("rare_loot", "Player (" + player + ") qualified for an RLS chest (chance: " + rlsChance + " roll: " + rollValue + ")");

        String lb = getConfigSetting("GameServer", "rlsMaxLevelsBelowPlayerLevel");
        int levelsBelow = lb == null ? 6 : Math.abs(Integer.parseInt(lb));
        String la = getConfigSetting("GameServer", "rlsMaxLevelsAbovePlayerLevel");
        int levelsAbove = la == null ? 6 : Math.abs(Integer.parseInt(la));
        String rdc = getConfigSetting("GameServer", "rlsRareDropChance");
        int rareDropChance = rdc == null ? 70 : Math.abs(Integer.parseInt(rdc));
        String edc = getConfigSetting("GameServer", "rlsExceptionalDropChance");
        int exceptionalDropChance = edc == null ? 25 : Math.abs(Integer.parseInt(edc));
        String ldc = getConfigSetting("GameServer", "rlsLegendaryDropChance");
        int legendaryDropChance = ldc == null ? 5 : Math.abs(Integer.parseInt(ldc));
        String dist = getConfigSetting("GameServer", "rlsMinDistanceFromLastLoot");
        int minDistanceFromLast = dist == null ? 0 : Math.abs(Integer.parseInt(dist));
        String tmpTime = getConfigSetting("GameServer", "rlsMinTimeBetweenAwards");

        // check to make sure player is far enough away from the last looted location (if set)
        location targetLocation = getLocation(target);
        if(minDistanceFromLast > 0 && hasObjVar(player, "loot.rls.lastLootedLocation")){
            location last = getLocationObjVar(player, "loot.rls.lastLootedLocation");
            float distanceFromLast = getDistance(last, targetLocation);
            if(distanceFromLast <= minDistanceFromLast){
                LOG("rare_loot", "Player (" + player + ") last looted a chest only " + distanceFromLast + " meters away - which is too close to this current location.");
                return false;
            }
        }

        // make sure it's been longer than minimum time required between chests (default 15 minutes) since last looted chest
        int rlsMinTimeBetweenAwards = (tmpTime == null ? 15 * 60 : Integer.parseInt(tmpTime));
        if(hasObjVar(player, "loot.rls.lastChestAwardTime")) {
            int lastLootTime = getIntObjVar(player, "loot.rls.lastChestAwardTime");
            int elapsedTime = getGameTime() - lastLootTime;
            LOG("rare_loot", "Player (" + player + ") last looted a chest " + elapsedTime + " game seconds ago (should be greater than " + rlsMinTimeBetweenAwards + ")!");
            if (elapsedTime < rlsMinTimeBetweenAwards) return false;
        }
        else{
            LOG("rare_loot", "Player (" + player + ") is looting their first RLS chest!");
        }

        // make sure the level range is appropriate for this attacker.
        int playerLevel = getLevel(player);
        int mobLevel = getLevel(target);
        int mobMinLevel = playerLevel - levelsBelow;
        int mobMaxLevel = playerLevel + levelsAbove;
        if(mobLevel < mobMinLevel || mobLevel > mobMaxLevel) return false;

        int bonus = 0;
        // make sure the player has an extremely more difficult time to loot a chest if they're still going through the tutorial
        if (hasObjVar(player, "npe")) {
            bonus -= 500;
        }

        // give the player a bonus if the mob is higher than their own level, lower bonus if mob is a lower level
        bonus += 10 * (mobLevel - playerLevel);

        // give the player a bonus if the mob is a silver or gold - only if they're equal to or higher than player's level
        if (mobLevel >= playerLevel) {
            LOG("rare_loot", "Player (" + player + ") killed an NPC that is equal to or greater than their level and is being evaluated for an RLS award!");
            String creatureName = ai_lib.getCreatureName(target);
            if (creatureName.equals("")) return false;

            switch (dataTableGetInt("datatables/mob/creatures.iff", creatureName, "difficultyClass")) {
                case 1:
                    if (creatureName.startsWith("heroic_")) {
                        LOG("rare_loot", "Player (" + player + ") killed a silver NPC and is being evaluated for an RLS award!");
                        bonus += 10;
                    }
                    break;
                case 2:
                    if (creatureName.startsWith("heroic_")) {
                        LOG("rare_loot", "Player (" + player + ") killed a gold NPC and is being evaluated for an RLS award!");
                        bonus += 50;
                    }
                    break;
            }
        }

        LOG("rare_loot", "Player (" + player + ") has a bonus of " + bonus + " and is applying that to their RLS award roll");
        double rareLootRoll = (rand(1, 1000 - bonus) / 10);
        LOG("rare_loot", "Player (" + player + ") just rolled a " + rareLootRoll + " value toward their RLS award (*with bonus*)!");

        // now determine what kind of chest is dropped - default to rare quality.
        // DEFAULTS:
        // RARE: 70% chance.
        // EXCEPTIONAL: 25% chance.
        // LEGENDARY: 5% chance.
        int lootType = 1;
        String type = "RARE";

        // evaluate Rare drop chance first, then Exceptional... if it's not either of those two, then it's Rare.
        if (rareLootRoll <= legendaryDropChance) {
            lootType = 3;
            type = "LEGENDARY";
        }
        else if (rareLootRoll <= exceptionalDropChance) {
            lootType = 2;
            type = "EXCEPTIONAL";
        }
        LOG("rare_loot", "Player (" + player + ") just qualified for a " + type + " RLS chest!");

        obj_id chest = createRareLootChest(target, lootType);

        setObjVar(player, "loot.rls.lastChestAwardTime", getGameTime());
        setObjVar(player, "loot.rls.lastLootedChest", chest);
        setObjVar(player, "loot.rls.lastLootedLocation", targetLocation);

        playClientEffectLoc(target, RLS_EFFECT, targetLocation, 1.0f);
        playClientEffectLoc(target, RLS_SOUND, targetLocation, 1.0f);

        return true;
    }
    public static obj_id createRareLootChest(obj_id creature, int lootType) throws InterruptedException {
        obj_id chest = static_item.createNewItemFunction(CHEST_BASE + lootType, utils.getInventoryContainer(creature));
        LOG("rare_loot", "Just created item #" + chest + " in the inventory of creature " + creature);
        return chest;
    }
}
