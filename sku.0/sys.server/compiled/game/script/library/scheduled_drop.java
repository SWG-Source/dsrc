package script.library;

import script.dictionary;
import script.obj_id;

import java.util.Vector;

public class scheduled_drop extends script.base_script
{
    public scheduled_drop()
    {
    }
    public static final String DATATABLE_SCHEDULE = "datatables/scheduled_drop/schedule.iff";
    public static final String DATATABLE_PROMOTIONS = "datatables/scheduled_drop/promotions.iff";
    public static final String DATATABLE_SERVER_PERCENTAGES = "datatables/scheduled_drop/server_percentages.iff";
    public static final String DATATABLE_SCHEDULE_PROMO_NAME = "promotion_name";
    public static final String DATATABLE_SCHEDULE_PROMO_LIST = "promotion_list";
    public static final String DATATABLE_SCHEDULE_PROMO_TYPE = "promotion_type";
    public static final String DATATABLE_SCHEDULE_PROMO_MAX_DROPS = "max_drops";
    public static final String DATATABLE_SCHEDULE_PROMO_START_HOUR = "start_hour";
    public static final String DATATABLE_SCHEDULE_PROMO_START_MINUTE = "start_minute";
    public static final String DATATABLE_SCHEDULE_PROMO_START_SECOND = "start_second";
    public static final String DATATABLE_SCHEDULE_PROMO_START_MONTH = "start_month";
    public static final String DATATABLE_SCHEDULE_PROMO_START_DAY = "start_day";
    public static final String DATATABLE_SCHEDULE_PROMO_START_YEAR = "start_year";
    public static final String DATATABLE_SCHEDULE_PROMO_END_HOUR = "end_hour";
    public static final String DATATABLE_SCHEDULE_PROMO_END_MINUTE = "end_minute";
    public static final String DATATABLE_SCHEDULE_PROMO_END_SECOND = "end_second";
    public static final String DATATABLE_SCHEDULE_PROMO_END_MONTH = "end_month";
    public static final String DATATABLE_SCHEDULE_PROMO_END_DAY = "end_day";
    public static final String DATATABLE_SCHEDULE_PROMO_END_YEAR = "end_year";
    public static final String DATATABLE_SERVER_PERCENTAGES_MAX = "max_drop_percentage";
    public static final String DATATABLE_PROMO_LIST = "promotion_list";
    public static final String DATATABLE_PROMO_STATIC_ITEM_NAME = "card_name";
    public static final String DATATABLE_PROMO_WEIGHT = "weight";
    public static final String CLUSTER_OBJVAR_PROMOTIONS = "tcg.promotions";
    public static final String CLUSTER_OBJVAR_PROMOTION_COUNTS = "tcg.counts";
    public static final String CLUSTER_OBJVAR_LAST_UPDATE = "tcg.last_update_date";
    public static final String PLAYER_SCRIPTVAR_DROP_TIME = "tcg.lastCardDropTime";
    public static final int DROP_CHANCE_COMBAT_NORMAL = 400;
    public static final int DROP_CHANCE_COMBAT_ELITE = 320;
    public static final int DROP_CHANCE_COMBAT_BOSS = 200;
    public static final int DROP_CHANCE_COMBAT_SPACE = 400;
    public static final int DROP_CHANCE_ENTERTAINER = 400;
    public static final int DROP_CHANCE_CRAFTER = 300;
    public static final int SYSTEM_UNKNOWN = 0;
    public static final int SYSTEM_COMBAT_NORMAL = 1;
    public static final int SYSTEM_COMBAT_ELITE = 2;
    public static final int SYSTEM_COMBAT_BOSS = 3;
    public static final int SYSTEM_COMBAT_SPACE = 4;
    public static final int SYSTEM_ENTERTAINER = 5;
    public static final int SYSTEM_CRAFTER = 6;
    public static final int CARD_DELAY_COMBAT_NORMAL = 10;
    public static final int CARD_DELAY_COMBAT_ELITE = 12;
    public static final int CARD_DELAY_COMBAT_BOSS = 20;
    public static final int CARD_DELAY_COMBAT_SPACE = 10;
    public static final int CARD_DELAY_ENTERTAINER = 20;
    public static final int CARD_DELAY_CRAFTER = 20;
    public static final int CARD_DELAY_DEFAULT = 20;
    public static final String[] systemTypes = 
    {
        "ERROR_unknown",
        "combat_normal",
        "combat_elite",
        "combat_boss",
        "combat_space",
        "entertainer",
        "crafter"
    };
    public static void testingSpam(obj_id self, String str) throws InterruptedException
    {
        if (isIdValid(self) && isGod(self) && hasObjVar(self, "qa_tcg"))
        {
            sendSystemMessageTestingOnly(self, str);
        }
    }
    public static String[] getPromotionList() throws InterruptedException
    {
        return dataTableGetStringColumn(DATATABLE_PROMOTIONS, DATATABLE_PROMO_LIST);
    }
    public static String[] getStaticItems() throws InterruptedException
    {
        return dataTableGetStringColumn(DATATABLE_PROMOTIONS, DATATABLE_PROMO_STATIC_ITEM_NAME);
    }
    public static int[] getStaticItemWeights() throws InterruptedException
    {
        return dataTableGetIntColumn(DATATABLE_PROMOTIONS, DATATABLE_PROMO_WEIGHT);
    }
    public static int getWeightTotal(dictionary[] promotionList) throws InterruptedException
    {
        int weight = 0;
        if (promotionList == null || promotionList.length <= 0)
        {
            return 0;
        }
        for (dictionary aPromotionList : promotionList) {
            weight += aPromotionList.getInt("promotionWeight");
        }
        return weight;
    }
    public static int getRandomStaticItem(dictionary[] promotionList) throws InterruptedException
    {
        if (promotionList == null || promotionList.length <= 0)
        {
            return -1;
        }
        int weightTotal = getWeightTotal(promotionList);
        int roll = rand(1, weightTotal);
        int index = -1;
        int currentWeight = 0;
        for (int i = 0, j = promotionList.length; i < j; i++)
        {
            currentWeight += promotionList[i].getInt("promotionWeight");
            if (currentWeight >= roll)
            {
                index = i;
                break;
            }
        }
        return index;
    }
    public static dictionary[] getStaticItemsForAllPromotions(String[] promotionNames) throws InterruptedException
    {
        if (promotionNames == null || promotionNames.length <= 0)
        {
            return null;
        }
        String[] allPromotionLists = getPromotionList();
        String[] allItems = getStaticItems();
        int[] allWeights = getStaticItemWeights();
        if (allItems == null || allItems.length <= 0)
        {
            return null;
        }
        Vector allStaticItems = new Vector();
        allStaticItems.setSize(0);
        String promotionList;
        dictionary cardEntry;

        for (String promotionName : promotionNames) {
            for (int k = 0, l = allItems.length; k < l; k++) {
                promotionList = dataTableGetString(DATATABLE_SCHEDULE, promotionName, DATATABLE_SCHEDULE_PROMO_LIST);
                if (allPromotionLists[k].equals(promotionList)) {
                    cardEntry = new dictionary();
                    cardEntry.put("promotionName", promotionName);
                    cardEntry.put("promotionList", allPromotionLists[k]);
                    cardEntry.put("promotionItem", allItems[k]);
                    cardEntry.put("promotionWeight", allWeights[k]);
                    allStaticItems = utils.addElement(allStaticItems, cardEntry);
                }
            }
        }
        dictionary[] _allStaticItems = new dictionary[0];
        if (allStaticItems != null)
        {
            _allStaticItems = new dictionary[allStaticItems.size()];
            allStaticItems.toArray(_allStaticItems);
        }
        return _allStaticItems;
    }
    public static String[] getSchedulerPromotions() throws InterruptedException
    {
        return dataTableGetStringColumn(DATATABLE_SCHEDULE, DATATABLE_SCHEDULE_PROMO_NAME);
    }
    public static int[] getSchedulerMaxDrops() throws InterruptedException
    {
        return dataTableGetIntColumn(DATATABLE_SCHEDULE, DATATABLE_SCHEDULE_PROMO_MAX_DROPS);
    }
    public static String[] getScheduledPromotions(String promotionType) throws InterruptedException
    {
        obj_id self = getSelf();
        String[] promotions = getSchedulerPromotions();
        if (promotions == null || promotions.length <= 0)
        {
            return null;
        }
        Vector scheduledPromotions = new Vector();
        scheduledPromotions.setSize(0);
        int currentDate = getCalendarTime();
        testingSpam(self, "currentDate: " + currentDate + " realTime: " + getCalendarTimeStringLocal(currentDate));
        dictionary currentPromotion;
        for (String promotion : promotions) {
            currentPromotion = dataTableGetRow(DATATABLE_SCHEDULE, promotion);
            if (!(currentPromotion.getString(DATATABLE_SCHEDULE_PROMO_TYPE)).equals(promotionType)) {
                continue;
            }
            int startHour = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_HOUR);
            int startMinute = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_MINUTE);
            int startSecond = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_SECOND);
            int startMonth = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_MONTH);
            int startDay = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_DAY);
            int startYear = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_START_YEAR);
            int startDate = getCalendarTime(startYear, startMonth, startDay, startHour, startMinute, startSecond);
            int endHour = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_HOUR);
            int endMinute = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_MINUTE);
            int endSecond = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_SECOND);
            int endMonth = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_MONTH);
            int endDay = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_DAY);
            int endYear = currentPromotion.getInt(DATATABLE_SCHEDULE_PROMO_END_YEAR);
            int endDate = getCalendarTime(endYear, endMonth, endDay, endHour, endMinute, endSecond);
            testingSpam(self, "Scheduled Promotion: " + currentPromotion.getString(DATATABLE_SCHEDULE_PROMO_NAME) + " Start: " + startDate + " End: " + endDate);
            if (startDate <= currentDate && endDate >= currentDate) {
                scheduledPromotions = utils.addElement(scheduledPromotions, currentPromotion.getString(DATATABLE_SCHEDULE_PROMO_NAME));
            }
        }
        String[] _scheduledPromotions = new String[0];
        if (scheduledPromotions != null)
        {
            _scheduledPromotions = new String[scheduledPromotions.size()];
            scheduledPromotions.toArray(_scheduledPromotions);
        }
        return _scheduledPromotions;
    }
    public static int getPromotionMaxDrop(String promotion) throws InterruptedException
    {
        return modifyPromotionCountByServer(dataTableGetInt(DATATABLE_SCHEDULE, promotion, DATATABLE_SCHEDULE_PROMO_MAX_DROPS));
    }
    public static dictionary[] getPromotionMaxDrops(String[] promotions) throws InterruptedException
    {
        obj_id self = getSelf();
        String[] allPromotions = getSchedulerPromotions();
        int[] allDrops = getSchedulerMaxDrops();
        Vector promotionsWithDrops = new Vector();
        promotionsWithDrops.setSize(0);
        testingSpam(self, "getPromotionMaxDrops allPromotions.length: " + allPromotions.length + " promotions.length: " + promotions.length);
        dictionary tempPromotion;
        for (int i = 0, j = promotions.length; i < j; i++)
        {
            for (int k = 0, l = allPromotions.length; k < l; k++)
            {
                testingSpam(self, "allPromotions[" + k + "]: " + allPromotions[k] + " promotions[" + i + "]: " + promotions[i] + " allDrops[" + k + "]: " + allDrops[k]);
                if (promotions[i].equals(allPromotions[k]))
                {
                    tempPromotion = new dictionary();
                    tempPromotion.put("promotion", promotions[i]);
                    tempPromotion.put("promotionMaxDrops", modifyPromotionCountByServer(allDrops[k]));
                    promotionsWithDrops = utils.addElement(promotionsWithDrops, tempPromotion);
                }
            }
        }
        dictionary[] _promotionsWithDrops = new dictionary[0];
        if (promotionsWithDrops != null)
        {
            _promotionsWithDrops = new dictionary[promotionsWithDrops.size()];
            promotionsWithDrops.toArray(_promotionsWithDrops);
        }
        return _promotionsWithDrops;
    }
    public static int modifyPromotionCountByServer(int promotionCount) throws InterruptedException
    {
        String serverName = toLower(getConfigSetting("CentralServer", "clusterName"));
        float percentOfCount;
        if (serverName == null || serverName.length() <= 0)
        {
            percentOfCount = 0.01f;
        }
        else 
        {
            percentOfCount = dataTableGetFloat(scheduled_drop.DATATABLE_SERVER_PERCENTAGES, serverName, scheduled_drop.DATATABLE_SERVER_PERCENTAGES_MAX);
            if (percentOfCount <= 0.0f)
            {
                percentOfCount = 0.01f;
            }
        }
        return (int)((float)promotionCount * percentOfCount);
    }
    public static int getLastClusterUpdateTime() throws InterruptedException
    {
        return getIntObjVar(getPlanetByName("tatooine"), CLUSTER_OBJVAR_LAST_UPDATE);
    }
    public static void setLastClusterUpdateTime(int date) throws InterruptedException
    {
        setObjVar(getPlanetByName("tatooine"), CLUSTER_OBJVAR_LAST_UPDATE, date);
    }
    public static void removeLastClusterUpdateTime() throws InterruptedException
    {
        obj_id planet = getPlanetByName("tatooine");
        if (!isIdValid(planet))
        {
            return;
        }
        removeObjVar(planet, CLUSTER_OBJVAR_LAST_UPDATE);
    }
    public static void removeClusterPromotions() throws InterruptedException
    {
        obj_id planet = getPlanetByName("tatooine");
        String[] promotions = getSchedulerPromotions();
        for (String promotion : promotions) {
            removeObjVar(planet, "tcg." + promotion + ".count");
        }
    }
    public static void setClusterPromotions(dictionary[] promotionsWithMaxDrops) throws InterruptedException
    {
        obj_id planet = getPlanetByName("tatooine");
        if (!hasScript(planet, "systems.tcg.planet_scheduler"))
        {
            attachScript(planet, "systems.tcg.planet_scheduler");
        }
        if (promotionsWithMaxDrops == null || promotionsWithMaxDrops.length <= 0)
        {
            messageTo(planet, "clearPromotions", null, 1.0f, false);
            return;
        }
        for (dictionary promotionsWithMaxDrop : promotionsWithMaxDrops) {
            messageTo(planet, "setPromotion", promotionsWithMaxDrop, 1.0f, false);
        }
    }
    public static void instantiatePromotionsOnCluster() throws InterruptedException
    {
        int lastUpdate = getLastClusterUpdateTime();
        int currentDate = getCalendarTime();
        if (currentDate - lastUpdate < 1800)
        {
            return;
        }
        String[] currentPromotions = getScheduledPromotions("card");
        obj_id self = getSelf();
        testingSpam(self, "Card promotions length: " + currentPromotions.length);
        setClusterPromotions(getPromotionMaxDrops(currentPromotions));
        currentPromotions = getScheduledPromotions("item");
        testingSpam(self, "Item promotions length: " + currentPromotions.length);
        if (currentPromotions.length > 0)
        {
            setClusterPromotions(getPromotionMaxDrops(currentPromotions));
        }
    }
    public static String[] validatePromotionsVersusCluster(String[] promotionNames) throws InterruptedException
    {
        obj_id planet = getPlanetByName("tatooine");
        if (!isIdValid(planet) || !exists(planet) || promotionNames == null || promotionNames.length <= 0)
        {
            return null;
        }
        Vector validatedPromotions = new Vector();
        validatedPromotions.setSize(0);
        for (String promotionName : promotionNames) {
            int countLeft = getIntObjVar(planet, "tcg." + promotionName + ".count");
            if (countLeft > 0 || countLeft == -1) {
                validatedPromotions = utils.addElement(validatedPromotions, promotionName);
            }
        }
        String[] _validatedPromotions = new String[0];
        if (validatedPromotions != null)
        {
            _validatedPromotions = new String[validatedPromotions.size()];
            validatedPromotions.toArray(_validatedPromotions);
        }
        return _validatedPromotions;
    }
    public static int cardDelay(int systemToDrop) throws InterruptedException
    {
        switch (systemToDrop)
        {
            case SYSTEM_COMBAT_NORMAL:
                return CARD_DELAY_COMBAT_NORMAL;
            case SYSTEM_COMBAT_ELITE:
                return CARD_DELAY_COMBAT_ELITE;
            case SYSTEM_COMBAT_BOSS:
                return CARD_DELAY_COMBAT_BOSS;
            case SYSTEM_COMBAT_SPACE:
                return CARD_DELAY_COMBAT_SPACE;
            case SYSTEM_ENTERTAINER:
                return CARD_DELAY_ENTERTAINER;
            case SYSTEM_CRAFTER:
                return CARD_DELAY_CRAFTER;
            case SYSTEM_UNKNOWN:
                return CARD_DELAY_DEFAULT;
            default:
                return CARD_DELAY_DEFAULT;
        }
    }
    public static boolean hasCardDelay(obj_id player, int systemToDrop) throws InterruptedException
    {
        int gameTime = getGameTime();
        if (!isIdValid(player))
        {
            return true;
        }
        if (isGod(player) && hasObjVar(player, "qa_tcg_always_drop"))
        {
            return false;
        }
        if (utils.hasScriptVar(player, PLAYER_SCRIPTVAR_DROP_TIME))
        {
            int lastDropTime = utils.getIntScriptVar(player, PLAYER_SCRIPTVAR_DROP_TIME);
            if (gameTime < lastDropTime + cardDelay(systemToDrop))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean canDropCard(int systemToDrop) throws InterruptedException
    {
        switch (systemToDrop)
        {
            case SYSTEM_COMBAT_NORMAL:
            return (rand(1, DROP_CHANCE_COMBAT_NORMAL) == 1);
            case SYSTEM_COMBAT_ELITE:
            return (rand(1, DROP_CHANCE_COMBAT_ELITE) == 1);
            case SYSTEM_COMBAT_BOSS:
            return (rand(1, DROP_CHANCE_COMBAT_BOSS) == 1);
            case SYSTEM_COMBAT_SPACE:
            return (rand(1, DROP_CHANCE_COMBAT_SPACE) == 1);
            case SYSTEM_ENTERTAINER:
            return (rand(1, DROP_CHANCE_ENTERTAINER) == 1);
            case SYSTEM_CRAFTER:
            return (rand(1, DROP_CHANCE_CRAFTER) == 1);
            case SYSTEM_UNKNOWN:
            return false;
            default:
            return false;
        }
    }
    public static obj_id dropCard(int systemToDrop, obj_id container) throws InterruptedException
    {
        obj_id self = getSelf();
        String typeName = "card";
        instantiatePromotionsOnCluster();
        if (!isIdValid(container) || !exists(container))
        {
            CustomerServiceLog("tcg", "ERROR: dropCard() Container to place card in is null.");
            return null;
        }
        String[] scheduledPromotions = getScheduledPromotions(typeName);
        if (scheduledPromotions == null || scheduledPromotions.length <= 0)
        {
            typeName = "item";
            scheduledPromotions = getScheduledPromotions(typeName);
            if (scheduledPromotions == null || scheduledPromotions.length <= 0)
            {
                testingSpam(self, "Scheduled Drops: Promotion log is empty.");
                CustomerServiceLog("tcg", "No promotions");
                return null;
            }
        }
        String[] promotions = validatePromotionsVersusCluster(scheduledPromotions);
        if (promotions == null || promotions.length <= 0)
        {
            testingSpam(self, "TCG: Promotions from cluster is empty.");
            CustomerServiceLog("tcg", "No valid promotions left on cluster.");
            return null;
        }
        dictionary[] promotionalItems = getStaticItemsForAllPromotions(promotions);
        int index = getRandomStaticItem(promotionalItems);
        String staticItemName = "";
        String promotionName;
        if (promotionalItems.length > index && index >= 0)
        {
            staticItemName = promotionalItems[index].getString("promotionItem");
            promotionName = promotionalItems[index].getString("promotionName");
        }
        else 
        {
            CustomerServiceLog("tcg", "ERROR dropCard() tcg.getRandomStaticItem(promotionalItems) out of bounds index for type: " + staticItemName + ".");
            return null;
        }
        obj_id card = static_item.createNewItemFunction(staticItemName, container);
        if (!isIdValid(card) || !exists(card))
        {
            if (isIdValid(self) && isGod(self) && hasObjVar(self, "qa_tcg"))
            {
                sendSystemMessageTestingOnly(self, "TCG: Static item creation failed: " + staticItemName);
            }
            CustomerServiceLog("tcg", "ERROR dropCard() could not create a " + typeName + " (" + staticItemName + ") with target container " + container);
            return null;
        }
        obj_id planet = getPlanetByName("tatooine");
        int remainder = getIntObjVar(planet, "tcg." + promotionName + ".count");
        testingSpam(self, ",Source_system," + systemTypes[systemToDrop] + ",Dropped_type," + typeName + ",Remaining_left," + remainder + ",Promotion," + promotionName + ",Dropped_static_item," + staticItemName + ",Container_dropped_in," + container + ",Container_name," + getName(container) + ",Time," + getCalendarTimeStringLocal(getCalendarTime()));
        CustomerServiceLog("tcg_drop", ",Source_system," + systemTypes[systemToDrop] + ",Dropped_type," + typeName + ",Remaining_left," + remainder + ",Promotion," + promotionName + ",Dropped_static_item," + staticItemName + ",Container_dropped_in," + container + ",Container_name," + getName(container) + ",Time," + getCalendarTimeStringLocal(getCalendarTime()));
        dictionary params = new dictionary();
        params.put("promotionName", promotionName);
        messageTo(planet, "reducePromotion", params, 1.0f, true);
        return card;
    }
}
