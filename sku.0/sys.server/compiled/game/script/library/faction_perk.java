package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.npc;
import script.library.skill;
import script.library.factions;
import script.library.prose;
import script.library.player_structure;
import script.library.structure;
import script.library.regions;
import script.library.utils;
import script.library.sui;
import script.library.datatable;
import script.library.pet_lib;
import script.library.create;
import script.library.ai_lib;
import script.library.temp_schematic;
import script.library.static_item;
import script.library.stealth;
import java.util.StringTokenizer;

public class faction_perk extends script.base_script
{
    public faction_perk()
    {
    }
    public static final String TBL_PREJUDICE = "datatables/faction/prejudice.iff";
    public static final String TBL_PERK_INVENTORY_BASE = "datatables/npc/faction_recruiter/perk_inventory/";
    public static final String VAR_COVERT_DETECTOR = "covert_detector";
    public static final String VAR_COVERT_DETECTOR_FACTION = VAR_COVERT_DETECTOR + ".faction";
    public static final String VAR_COVERT_DETECTOR_RANGE = VAR_COVERT_DETECTOR + ".range";
    public static final String VAR_COVERT_DETECTOR_SPEED = VAR_COVERT_DETECTOR + ".speed";
    public static final float BASE_COVERT_DETECT_TIME = 5f;
    public static final float DETECTOR_NOTIFY_RANGE = 128f;
    public static final string_id PROSE_COVERT_UNCLOAK = new string_id("base_player", "prose_covert_uncloak");
    public static final String VAR_MINEFIELD_BASE = "minefield";
    public static final String VAR_MINEFIELD_SIZE = VAR_MINEFIELD_BASE + ".size";
    public static final String VAR_MINEFIELD_ACTIVE = VAR_MINEFIELD_BASE + ".active";
    public static final String VAR_MINEFIELD_TARGETS = VAR_MINEFIELD_BASE + ".targets";
    public static final String VAR_MINEFIELD_TARGET_IDS = VAR_MINEFIELD_TARGETS + ".ids";
    public static final String VAR_MINEFIELD_TARGET_LOCS = VAR_MINEFIELD_TARGETS + ".locs";
    public static final String VAR_MINEFIELD_EXTENTS_BASE = VAR_MINEFIELD_BASE + ".extents";
    public static final String VAR_MINEFIELD_MIN_X = VAR_MINEFIELD_EXTENTS_BASE + ".minX";
    public static final String VAR_MINEFIELD_MIN_Z = VAR_MINEFIELD_EXTENTS_BASE + ".minZ";
    public static final String VAR_MINEFIELD_MAX_X = VAR_MINEFIELD_EXTENTS_BASE + ".maxX";
    public static final String VAR_MINEFIELD_MAX_Z = VAR_MINEFIELD_EXTENTS_BASE + ".maxZ";
    public static final float BASE_MINEFIELD_TICK = 1f;
    public static final int CHANCE_PER_METER = 25;
    public static final float MINE_DAMAGE_RADIUS = 6f;
    public static final String STF_PERK = "faction_perk";
    public static final string_id MNU_DEPLOY = new string_id(STF_PERK, "deploy");
    public static final string_id MNU_PACKUP = new string_id(STF_PERK, "packup");
    public static final string_id SID_NO_BUILD_AREA = new string_id(STF_PERK, "no_build_area");
    public static final string_id SID_NO_BUILD_INSIDE = new string_id(STF_PERK, "no_build_inside");
    public static final string_id PROSE_BE_DECLARED = new string_id(STF_PERK, "prose_be_declared");
    public static final string_id PROSE_DECLARED_FATION = new string_id(STF_PERK, "prose_be_declared_faction");
    public static final string_id PROSE_MUST_HAVE_SKILL = new string_id(STF_PERK, "prose_must_have_skill");
    public static final string_id PROSE_NSF_LOTS = new string_id(STF_PERK, "prose_nsf_lots");
    public static final string_id PROSE_WRONG_FACTION = new string_id(STF_PERK, "prose_wrong_faction");
    public static final string_id PROSE_NOT_NEUTRAL = new string_id(STF_PERK, "prose_not_neutral");
    public static final string_id[] FACTION_PERK_GROUPS = 
    {
        new string_id("faction_recruiter", "option_purchase_weapons_armor"),
        new string_id("faction_recruiter", "option_purchase_installation")
    };
    public static final float FACTION_LOSING_COST_MODIFIER = .70f;
    public static final String VAR_FACTION = "faction_recruiter.faction";
    public static final String SCRIPT_FACTION_RECRUITER = "npc.faction_recruiter.faction_recruiter";
    public static final String SCRIPT_PLAYER_RECRUITER = "npc.faction_recruiter.player_recruiter";
    public static final String SCRIPT_FACTION_ITEM = "npc.faction_recruiter.faction_item";
    public static final String VAR_TRAINING_SELECTION = "faction_recruiter.training_selection";
    public static final String VAR_TRAINING_COST = "faction_recruiter.cost";
    public static final String VAR_TRAINING_XP = "faction_recruiter.xp";
    public static final String VAR_DECLARED = "faction_recruiter.declared";
    public static final String VAR_FACTION_HIRELING = "faction_recruiter.faction_hireling";
    public static final String VAR_PLAYER = "faction_recruiter.player";
    public static final String VAR_BIO_LINK_FACTION_POINTS = "biolink.faction_points";
    public static final string_id SID_NO_ITEMS_AVAILABLE = new string_id("faction_recruiter", "no_items_available");
    public static final string_id SID_RESIGN_COMPLETE = new string_id("faction_recruiter", "resign_complete");
    public static final string_id SID_COVERT_COMPLETE = new string_id("faction_recruiter", "covert_complete");
    public static final string_id SID_INVALID_AMOUNT_ENTERED = new string_id("faction_recruiter", "invalid_amount_entered");
    public static final string_id SID_NOT_ENOUGH_STANDING_SPEND = new string_id("faction_recruiter", "not_enough_standing_spend");
    public static final string_id SID_NOT_ENOUGH_CREDITS = new string_id("faction_recruiter", "not_enough_credits");
    public static final string_id SID_AMOUNT_TOO_SMALL = new string_id("faction_recruiter", "amount_to_spend_too_small");
    public static final string_id SID_EXPERIENCE_GRANTED = new string_id("faction_recruiter", "training_experience_granted");
    public static final string_id SID_ITEM_PURCHASED = new string_id("faction_recruiter", "item_purchase_complete");
    public static final string_id SID_ACQUIRE_HIRELING = new string_id("faction_recruiter", "hireling_purchase_complete");
    public static final string_id SID_TOO_MANY_HIRELINGS = new string_id("faction_recruiter", "too_many_hirelings");
    public static final string_id SID_HIRELING_RELEASED = new string_id("faction_recruiter", "hireling_released");
    public static final string_id SID_INVENTORY_FULL = new string_id("faction_recruiter", "inventory_full");
    public static final string_id SID_DATAPAD_FULL = new string_id("faction_recruiter", "datapad_full");
    public static final string_id SID_ORDER_PURCHASED = new string_id("faction_recruiter", "order_purchase_complete");
    public static final string_id SID_SCHEMATIC_PURCHASED = new string_id("faction_recruiter", "schematic_purchase_complete");
    public static final string_id SID_SCHEMATIC_DUPLICATE = new string_id("faction_recruiter", "schematic_duplicate");
    public static final int FACTION_NONE = -1;
    public static final int FACTION_REBEL = 0;
    public static final int FACTION_IMPERIAL = 1;
    public static final String COMM_COOLDOWN = "pvp_gcw_comlink.cooldown";
    public static final int COMM_REUSE = 900;
    public static final string_id SID_ALREADY_HAVE = new string_id("gcw", "comm_already_used");
    public static final string_id SID_TOO_LOW_LEVEL = new string_id("gcw", "player_too_low");
    public static final string_id SID_INDOORS = new string_id("gcw", "player_is_indoors");
    public static int prejudicePerkCost(obj_id player, String faction, int base_cost) throws InterruptedException
    {
        if (!isIdValid(player) || (faction == null) || (faction.equals("")) || (base_cost < 1))
        {
            return -1;
        }
        int species = getSpecies(player);
        float mod = getFactionPrejudice(species, faction);
        if (mod > 1.05f)
        {
            mod = 1.05f;
        }
        float expertiseFactionCostBonus = getSkillStatisticModifier(player, "expertise_faction_cost_bonus");
        if (expertiseFactionCostBonus > 3)
        {
            expertiseFactionCostBonus = 3;
        }
        mod = mod - (expertiseFactionCostBonus / 100);
        if (mod > 0)
        {
            float cost = base_cost * mod;
            return (int)cost;
        }
        return -1;
    }
    public static float getFactionPrejudice(int species, String faction) throws InterruptedException
    {
        faction = toLower(faction);
        String strSpecies = utils.getPlayerSpeciesName(species);
        return dataTableGetFloat(TBL_PREJUDICE, strSpecies, faction);
    }
    public static boolean canDeployFactionalDeed(obj_id player, obj_id deed) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(deed))
        {
            return false;
        }
        if (getOwner(deed) != player)
        {
            LOG("LOG_CHANNEL", "canDeployFactionalDeed: player != deed owner");
            return false;
        }
        String pFac = factions.getFaction(player);
        int pvpType = pvpGetType(player);
        if (pvpType == PVPTYPE_NEUTRAL)
        {
            prose_package ppBeDeclared = prose.getPackage(PROSE_NOT_NEUTRAL, deed);
            sendSystemMessageProse(player, ppBeDeclared);
            return false;
        }
        String dFac = factions.getFaction(deed);
        if (dFac != null && !dFac.equals(""))
        {
            if (!pFac.equals(dFac))
            {
                prose_package ppWrongFaction = prose.getPackage(PROSE_WRONG_FACTION, deed, dFac);
                sendSystemMessageProse(player, ppWrongFaction);
                return false;
            }
        }
        String template = player_structure.getDeedTemplate(deed);
        if ((template == null) || (template.equals("")))
        {
            LOG("LOG_CHANNEL", "canDeployFactionalDeed: bad deed template!!");
            return false;
        }
        if (!gcw.canPlaceFactionBaseByPlanet(player, deed, getLocation(player).area))
        {
            sendSystemMessage(player, new string_id("gcw", "cannot_place_additional_base"));
            return false;
        }
        int used = getIntObjVar(player, player_structure.VAR_LOTS_USED);
        String fp_template = player_structure.getFootprintTemplate(template);
        if ((fp_template == null) || (fp_template.equals("")))
        {
            fp_template = template;
        }
        location here = getLocation(player);
        if (isIdValid(here.cell))
        {
            sendSystemMessage(player, SID_NO_BUILD_AREA);
            return false;
        }
        region[] rgnTest = getRegionsWithBuildableAtPoint(here, regions.BUILD_FALSE);
        if (rgnTest != null)
        {
            sendSystemMessage(player, SID_NO_BUILD_AREA);
            return false;
        }
        if (!player_structure.canPlaceFactionPerkDeed(deed, player))
        {
            return false;
        }
        return true;
    }
    public static void decloakCovertFactionMember(obj_id detector, obj_id player) throws InterruptedException
    {
        if (!isIdValid(detector) || !isIdValid(player))
        {
            return;
        }
        if (isPlayer(player))
        {
            int dFac = pvpGetAlignedFaction(detector);
            int pFac = pvpGetAlignedFaction(player);
            if (pvpAreFactionsOpposed(dFac, pFac) && !factions.isDeclared(player))
            {
                String pFacName = factions.getFaction(player);
                String dFacName = factions.getFactionNameByHashCode(dFac);
                boolean hasGlobalTef = false;
                String[] tefs = pvpGetEnemyFlags(player);
                if (tefs != null && tefs.length > 0)
                {
                    for (int i = 0; i < tefs.length; i++)
                    {
                        java.util.StringTokenizer st = new java.util.StringTokenizer(tefs[i]);
                        String sTarget = st.nextToken();
                        String sTefFac = st.nextToken();
                        String sExpiration = st.nextToken();
                        int iTefFac = utils.stringToInt(sTefFac);
                        String tefFac = factions.getFactionNameByHashCode(iTefFac);
                        if (tefFac != null && !tefFac.equals(""))
                        {
                            if (tefFac.equals(dFacName))
                            {
                                obj_id target = utils.stringToObjId(sTarget);
                                if (!isIdValid(target))
                                {
                                    hasGlobalTef = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (hasGlobalTef)
                {
                    return;
                }
                string_id sidPlayerFactionName = factions.getLocalizedFactionName(pFacName);
                prose_package ppUncloak = prose.getPackage(PROSE_COVERT_UNCLOAK, detector, sidPlayerFactionName);
                sendSystemMessageProse(player, ppUncloak);
                if (!factions.setTemporaryEnemyFlag(player, detector))
                {
                }
                else 
                {
                    obj_id[] inRange = getNonCreaturesInRange(detector, DETECTOR_NOTIFY_RANGE);
                    if ((inRange != null) && (inRange.length > 0))
                    {
                        dictionary d = new dictionary();
                        d.put("target", player);
                        for (int i = 0; i < inRange.length; i++)
                        {
                            if (getGameObjectType(inRange[i]) == GOT_installation_turret)
                            {
                                messageTo(inRange[i], "enemyDecloaked", d, 3, false);
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean displayAvailableFactionItemRanks(obj_id player, obj_id npc, int playerGcwRank, String playerGcwFaction) throws InterruptedException
    {
        if (playerGcwRank > 0)
        {
            String perksDatatable = "datatables/npc/faction_recruiter/perk_inventory/gcw_rewards.iff";
            int[] itemsPerRankList = 
            {
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
            };
            int num_items = dataTableGetNumRows(perksDatatable);
            for (int i = 0; i < num_items; i++)
            {
                dictionary row = dataTableGetRow(perksDatatable, i);
                if (row != null && !row.isEmpty())
                {
                    int[] requiredRank = dataTableGetIntColumn(perksDatatable, "requiredGcwRank");
                    String[] requiredFaction = dataTableGetStringColumn(perksDatatable, "requiredFaction");
                    int row_requiredRank = row.getInt("requiredGcwRank");
                    String row_requiredFaction = row.getString("requiredFaction");
                    if (playerGcwRank >= row_requiredRank && (row_requiredFaction.equals(playerGcwFaction) || row_requiredFaction.equals("Either")))
                    {
                        String row_template = row.getString("template");
                        String row_name = row.getString("name");
                        int row_cost = row.getInt("cost");
                        boolean addItem = true;
                        String row_requiredClasses = row.getString("requiredClasses");
                        int row_requiredLevel = row.getInt("requiredLevel");
                        String row_requiredSkill = row.getString("requiredSkill");
                        if (row_template.startsWith("object/draft_schematic"))
                        {
                            if (row_name == null || row_name.length() == 0)
                            {
                                string_id nameId = getProductNameFromSchematic(row_template);
                                if (nameId != null)
                                {
                                    row_name = "@" + nameId;
                                }
                            }
                            if (hasSchematic(player, row_template))
                            {
                                addItem = false;
                            }
                        }
                        if (row_name == null || row_name.length() == 0)
                        {
                            string_id nameId = getNameFromTemplate(row_template);
                            if (nameId != null)
                            {
                                row_name = "@" + nameId;
                            }
                        }
                        if (row_requiredClasses != null && row_requiredClasses.length() > 0)
                        {
                            addItem = utils.testItemClassRequirements(player, row_requiredClasses, true);
                        }
                        if (row_requiredLevel > 1)
                        {
                            if (getLevel(player) < row_requiredLevel)
                            {
                                addItem = false;
                            }
                        }
                        if (row_requiredSkill != null && row_requiredSkill.length() > 0)
                        {
                            addItem = hasSkill(player, row_requiredSkill);
                        }
                        if (row_template.startsWith("static:"))
                        {
                            java.util.StringTokenizer st = new java.util.StringTokenizer(row_template, ":");
                            if (st.countTokens() == 2)
                            {
                                st.nextToken();
                                String itemName = st.nextToken();
                                if (static_item.isUniqueStaticItem(itemName))
                                {
                                    if (!static_item.canCreateUniqueStaticItem(player, itemName))
                                    {
                                        addItem = false;
                                    }
                                }
                            }
                        }
                        if (addItem)
                        {
                            itemsPerRankList[row_requiredRank] = itemsPerRankList[row_requiredRank] + 1;
                        }
                    }
                }
            }
            String scriptvar_path = "recruiter.item_rank." + player;
            Vector rankList = new Vector();
            rankList.setSize(0);
            for (int i = playerGcwRank; i > 0; i--)
            {
                String text = "@gcw_rank:" + toLower(playerGcwFaction) + "_rank" + i;
                if (itemsPerRankList[i] > 0)
                {
                    rankList = utils.addElement(rankList, text);
                }
                else 
                {
                    string_id contrastText_sid = new string_id("faction_recruiter", "rank_list_empty");
                    prose_package pp = prose.getPackage(contrastText_sid);
                    prose.setTO(pp, text);
                    rankList = utils.addElement(rankList, "\0" + packOutOfBandProsePackage(null, pp));
                }
            }
            if (rankList != null && rankList.size() > 0)
            {
                String prompt = getString(new string_id("faction_recruiter", "select_item_rank"));
                int pid = sui.listbox(npc, player, prompt, sui.OK_CANCEL, "@faction_recruiter:faction_purchase_rank", rankList, "msgFactionItemRankSelected");
                if (pid > -1)
                {
                    utils.setScriptVar(npc, scriptvar_path + ".pid", pid);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean displayItemPurchaseSUI(obj_id player, int rank, String faction, obj_id objNPC) throws InterruptedException
    {
        return displayItemPurchaseSUI(player, rank, faction, 1.0f, objNPC);
    }
    public static boolean displayItemPurchaseSUI(obj_id player, int rank, String faction) throws InterruptedException
    {
        return displayItemPurchaseSUI(player, rank, faction, 1.0f);
    }
    public static boolean displayItemPurchaseSUI(obj_id player, int rank, String faction, float systemMultiplier) throws InterruptedException
    {
        return displayItemPurchaseSUI(player, rank, faction, systemMultiplier, null);
    }
    public static boolean displayItemPurchaseSUI(obj_id player, int rank, String faction, float systemMultiplier, obj_id objRecruiter) throws InterruptedException
    {
        obj_id self = getSelf();
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        String scriptvar_path = "recruiter.item_purchase." + player;
        if (utils.hasScriptVar(self, scriptvar_path + ".pid"))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_path + ".pid");
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(self, scriptvar_path + ".pid");
            utils.removeBatchScriptVar(self, scriptvar_path + ".template");
            utils.removeBatchScriptVar(self, scriptvar_path + ".item_names");
            utils.removeScriptVar(self, scriptvar_path + ".rank");
            utils.removeScriptVar(self, scriptvar_path + ".faction");
        }
        int playerGcwRank = pvpGetCurrentGcwRank(player);
        int playerFactionId = pvpGetAlignedFaction(player);
        String playerGcwFaction = factions.getFactionNameByHashCode(playerFactionId);
        if (playerGcwRank < rank || !playerGcwFaction.equals(faction))
        {
            return false;
        }
        String perksDatatable = "datatables/npc/faction_recruiter/perk_inventory/gcw_rewards.iff";
        Vector items = new Vector();
        items.setSize(0);
        Vector templates = new Vector();
        templates.setSize(0);
        int num_items = dataTableGetNumRows(perksDatatable);
        for (int i = 0; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(perksDatatable, i);
            if (row != null && !row.isEmpty())
            {
                int[] requiredRank = dataTableGetIntColumn(perksDatatable, "requiredGcwRank");
                String[] requiredFaction = dataTableGetStringColumn(perksDatatable, "requiredFaction");
                int row_requiredRank = row.getInt("requiredGcwRank");
                String row_requiredFaction = row.getString("requiredFaction");
                if (row_requiredRank == rank && (row_requiredFaction.equals(faction) || row_requiredFaction.equals("Either")))
                {
                    String row_template = row.getString("template");
                    String row_name = row.getString("name");
                    int row_cost = row.getInt("cost");
                    boolean addItem = true;
                    String row_requiredClasses = row.getString("requiredClasses");
                    int row_requiredLevel = row.getInt("requiredLevel");
                    String row_requiredSkill = row.getString("requiredSkill");
                    if (row_template.startsWith("object/draft_schematic"))
                    {
                        if (row_name == null || row_name.length() == 0)
                        {
                            string_id nameId = getProductNameFromSchematic(row_template);
                            if (nameId != null)
                            {
                                row_name = "@" + nameId;
                            }
                        }
                        if (hasSchematic(player, row_template))
                        {
                            addItem = false;
                        }
                    }
                    if (row_name == null || row_name.length() == 0)
                    {
                        string_id nameId = getNameFromTemplate(row_template);
                        if (nameId != null)
                        {
                            row_name = "@" + nameId;
                        }
                    }
                    if (row_requiredClasses != null && row_requiredClasses.length() > 0)
                    {
                        addItem = utils.testItemClassRequirements(player, row_requiredClasses, true);
                    }
                    if (row_requiredLevel > 1)
                    {
                        if (getLevel(player) < row_requiredLevel)
                        {
                            addItem = false;
                        }
                    }
                    if (row_requiredSkill != null && row_requiredSkill.length() > 0)
                    {
                        addItem = hasSkill(player, row_requiredSkill);
                    }
                    if (row_template.startsWith("static:"))
                    {
                        java.util.StringTokenizer st = new java.util.StringTokenizer(row_template, ":");
                        if (st.countTokens() == 2)
                        {
                            st.nextToken();
                            String itemName = st.nextToken();
                            if (static_item.isUniqueStaticItem(itemName))
                            {
                                if (!static_item.canCreateUniqueStaticItem(player, itemName))
                                {
                                    addItem = false;
                                }
                            }
                        }
                    }
                    if (addItem)
                    {
                        int cost = faction_perk.prejudicePerkCost(player, faction.toLowerCase(), row_cost);
                        if (cost > 0)
                        {
                            if (isIdValid(objRecruiter))
                            {
                                float fltCost = (float)cost;
                                fltCost = faction_perk.getModifiedGCWCost(fltCost, objRecruiter, faction);
                                cost = (int)(fltCost);
                            }
                            cost *= systemMultiplier;
                            items = utils.addElement(items, row_name + " (Cost: " + cost + ")");
                            templates = utils.addElement(templates, row_template);
                        }
                    }
                }
            }
        }
        String rankTitle = "@gcw_rank:" + toLower(playerGcwFaction) + "_rank" + rank;
        if (items == null || items.size() < 1)
        {
            faction_perk.displayAvailableFactionItemRanks(player, self, playerGcwRank, playerGcwFaction);
            prose_package pp = prose.getPackage(faction_perk.SID_NO_ITEMS_AVAILABLE, rankTitle);
            sendSystemMessageProse(player, pp);
            return false;
        }
        float totalMultiplier = 1f;
        if (!factions.isFactionWinning(faction))
        {
            totalMultiplier *= faction_perk.FACTION_LOSING_COST_MODIFIER;
        }
        if (systemMultiplier != 1f)
        {
            totalMultiplier *= systemMultiplier;
        }
        String prompt = getString(new string_id("faction_recruiter", "select_item_purchase"));
        if (totalMultiplier != 1f)
        {
            int delta = (int)((totalMultiplier - 1f) * 100);
            prompt += "\n\nCost modifier at this time: " + delta + "%";
        }
        String myHandler = "msgFactionItemPurchaseSelected";
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL_REFRESH, rankTitle, items, myHandler, false, false);
        if (pid > -1)
        {
            sui.listboxUseOtherButton(pid, "Back");
            sui.showSUIPage(pid);
            utils.setScriptVar(self, scriptvar_path + ".pid", pid);
            utils.setBatchScriptVar(self, scriptvar_path + ".template", templates);
            utils.setBatchScriptVar(self, scriptvar_path + ".item_names", items);
            utils.setScriptVar(self, scriptvar_path + ".rank", rank);
            utils.setScriptVar(self, scriptvar_path + ".faction", faction);
            return true;
        }
        return false;
    }
    public static float getModifiedGCWCost(float fltCost, obj_id objNPC, String strFaction) throws InterruptedException
    {
        float MINIMUM_MODIFIER = .50f;
        float MAXIMUM_MODIFIER = 1.50f;
        float fltRatio = 0f;
        if (strFaction.equals("Imperial"))
        {
            fltRatio = gcw.getRebelRatio(objNPC);
        }
        else if (strFaction.equals("Rebel"))
        {
            fltRatio = gcw.getImperialRatio(objNPC);
        }
        else 
        {
            return fltCost;
        }
        LOG("gcw", "Ratio is " + fltRatio);
        float fltModifier = MINIMUM_MODIFIER + fltRatio;
        if (fltModifier > MAXIMUM_MODIFIER)
        {
            fltModifier = MAXIMUM_MODIFIER;
        }
        LOG("gcw", "Modifier is " + fltModifier);
        fltCost = fltCost * fltModifier;
        return fltCost;
    }
    public static void factionItemPurchased(dictionary params) throws InterruptedException
    {
        factionItemPurchased(params, 1.0f);
    }
    public static void factionItemPurchased(dictionary params, float systemMultiplier) throws InterruptedException
    {
        obj_id self = getSelf();
        if (params == null || params.isEmpty())
        {
            return;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return;
        }
        String scriptvar_path = "recruiter.item_purchase." + player;
        if (!utils.hasScriptVar(self, scriptvar_path + ".pid"))
        {
            return;
        }
        int oldPid = utils.getIntScriptVar(self, scriptvar_path + ".pid");
        String[] available_items = utils.getStringBatchScriptVar(self, scriptvar_path + ".template");
        String[] item_names = utils.getStringBatchScriptVar(self, scriptvar_path + ".item_names");
        int rank = utils.getIntScriptVar(self, scriptvar_path + ".rank");
        String faction = utils.getStringScriptVar(self, scriptvar_path + ".faction");
        utils.removeScriptVar(self, scriptvar_path + ".pid");
        utils.removeBatchScriptVar(self, scriptvar_path + ".template");
        utils.removeBatchScriptVar(self, scriptvar_path + ".item_names");
        utils.removeScriptVar(self, scriptvar_path + ".rank");
        utils.removeScriptVar(self, scriptvar_path + ".faction");
        if (available_items == null || available_items.length == 0)
        {
            LOG("LOG_CHANNEL", "faction_recruiter::msgFactionItemPurchaseSelected -- the item template list is null.");
            return;
        }
        if (faction == null || faction.equals(""))
        {
            int faction_id = pvpGetAlignedFaction(self);
            faction = toLower(factions.getFactionNameByHashCode(faction_id));
        }
        int playerGcwRank = pvpGetCurrentGcwRank(player);
        int playerFactionId = pvpGetAlignedFaction(player);
        String playerGcwFaction = factions.getFactionNameByHashCode(playerFactionId);
        if (playerGcwRank < rank || !playerGcwFaction.equals(faction))
        {
            return;
        }
        int button = sui.getIntButtonPressed(params);
        if (button == sui.BP_CANCEL)
        {
            return;
        }
        if (button == sui.BP_REVERT)
        {
            faction_perk.displayAvailableFactionItemRanks(player, self, playerGcwRank, playerGcwFaction);
            return;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected < 0)
        {
            return;
        }
        if (row_selected >= available_items.length)
        {
            return;
        }
        String item_template = available_items[row_selected];
        if (item_template == null)
        {
            LOG("LOG_CHANNEL", "faction_recruiter::msgFactionItemPurchaseSelected -- the item template selected by " + self + " is null.");
            return;
        }
        String perksDatatable = "datatables/npc/faction_recruiter/perk_inventory/gcw_rewards.iff";
        int idx = dataTableSearchColumnForString(item_template, "template", perksDatatable);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "faction_recruiter::msgFactionItemPurchaseSelected -- cannot find " + item_template + " in the perk inventory datatable.");
            return;
        }
        dictionary row = dataTableGetRow(perksDatatable, idx);
        String name = row.getString("name");
        int base_cost = row.getInt("cost");
        int declared = row.getInt("declared");
        String templateName = toLower(item_template);
        obj_id inv = getObjectInSlot(player, "inventory");
        if (inv == null || inv == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "faction_recruiter::msgFactionTrainingAmountSelected --  " + self + "'s inventory object is null.");
            return;
        }
        int cost = faction_perk.prejudicePerkCost(player, faction, base_cost);
        if (cost < 0)
        {
            cost = base_cost;
        }
        float fltCost = (float)cost;
        fltCost = faction_perk.getModifiedGCWCost(fltCost, self, faction);
        cost = (int)(fltCost);
        cost *= systemMultiplier;
        if (!money.hasFunds(player, money.MT_TOTAL, cost))
        {
            String itemPurchased = item_names[row_selected];
            prose_package pp = prose.getPackage(faction_perk.SID_NOT_ENOUGH_CREDITS, name);
            sendSystemMessageProse(player, pp);
        }
        else 
        {
            if (item_template.startsWith("object/draft_schematic"))
            {
                if (hasSchematic(player, item_template))
                {
                    faction_perk.displayItemPurchaseSUI(player, rank, playerGcwFaction, self);
                    sendSystemMessage(player, faction_perk.SID_SCHEMATIC_DUPLICATE);
                    return;
                }
                utils.moneyOutMetric(player, "GCW_REWARDS", cost);
                money.requestPayment(player, self, cost, "pass_fail", null, true);
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + " is attempting to purchase schematic: " + item_template);
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + "'s purchase cost: " + cost);
                int uses = row.getInt("uses");
                if (uses > 0)
                {
                    if (!temp_schematic.grant(player, item_template, uses))
                    {
                        LOG("LOG_CHANNEL", "faction_recruiter::msgFactionTrainingAmountSelected --  unable to create " + item_template + " for " + self);
                        CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + "'s purchase of " + item_template + " has failed!");
                        return;
                    }
                }
                else 
                {
                    if (!grantSchematic(player, item_template))
                    {
                        LOG("LOG_CHANNEL", "faction_recruiter::msgFactionTrainingAmountSelected --  unable to create " + item_template + " for " + self);
                        CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + "'s purchase of " + item_template + " has failed!");
                        return;
                    }
                }
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + " has purchased schematic: " + item_template);
                logBalance("perkPurchase;" + getGameTime() + ";" + faction + ";schematic;" + item_template + ";" + cost);
                sendSystemMessage(player, faction_perk.SID_SCHEMATIC_PURCHASED);
                LOG("LOG_CHANNEL", "faction_recruiter::msgFactionItemPurchaseSelected -- " + item_template + " order purchased for " + self);
            }
            else if (templateName.startsWith("stealth:"))
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(templateName, ":");
                if (st.countTokens() == 4)
                {
                    String waste = st.nextToken();
                    String object = st.nextToken();
                    int level = utils.stringToInt(st.nextToken());
                    int count = utils.stringToInt(st.nextToken());
                    int free = getVolumeFree(inv);
                    if (free < count)
                    {
                        prose_package pp = prose.getPackage(new string_id("spam", "no_room_inventory"), count);
                        sendSystemMessageProse(player, pp);
                        return;
                    }
                    stealth.createRangerLoot(level, object, inv, count);
                    utils.moneyOutMetric(player, "GCW_REWARDS", cost);
                    money.requestPayment(player, self, cost, "pass_fail", null, true);
                }
            }
            else 
            {
                int free_space = getVolumeFree(inv);
                if (free_space < 1)
                {
                    sendSystemMessage(player, faction_perk.SID_INVENTORY_FULL);
                    return;
                }
                utils.moneyOutMetric(player, "GCW_REWARDS", cost);
                money.requestPayment(player, self, cost, "pass_fail", null, true);
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + " is attempting to purchase item: " + item_template);
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + "'s purchase cost: " + cost);
                obj_id item = null;
                if (templateName.startsWith("static:"))
                {
                    java.util.StringTokenizer st = new java.util.StringTokenizer(item_template, ":");
                    if (st.countTokens() == 2)
                    {
                        st.nextToken();
                        String itemName = st.nextToken();
                        if (static_item.isUniqueStaticItem(itemName))
                        {
                            if (static_item.canCreateUniqueStaticItem(player, itemName))
                            {
                                item = static_item.createNewItemFunction(itemName, inv);
                            }
                        }
                        else 
                        {
                            item = static_item.createNewItemFunction(itemName, inv);
                        }
                    }
                }
                else 
                {
                    item = weapons.createPossibleWeapon(item_template, inv, 0.8f);
                }
                if (!isIdValid(item))
                {
                    LOG("LOG_CHANNEL", "faction_recruiter::msgFactionTrainingAmountSelected --  unable to create " + item_template + " for " + self);
                    CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + "'s purchase of " + item_template + " has failed!");
                    return;
                }
                CustomerServiceLog("faction_perk", "(" + player + ")" + getName(player) + " has purchased (" + item + ")" + getName(item));
                logBalance("perkPurchase;" + getGameTime() + ";" + faction + ";item;" + item_template + ";" + cost);
                setObjVar(item, faction_perk.VAR_FACTION, faction);
                if (declared == 1)
                {
                    setObjVar(item, faction_perk.VAR_DECLARED, 1);
                }
                attachScript(item, faction_perk.SCRIPT_FACTION_ITEM);
                prose_package pp = prose.getPackage(faction_perk.SID_ITEM_PURCHASED, player, item);
                sendSystemMessageProse(player, pp);
                LOG("LOG_CHANNEL", "faction_recruiter::msgFactionItemPurchaseSelected -- " + item + " purchased for " + self);
            }
        }
        faction_perk.displayItemPurchaseSUI(player, rank, playerGcwFaction, self);
        return;
    }
    public static void applyFactionCostObjvarFromSchematic(obj_id craftedObject, obj_id manfSchematic) throws InterruptedException
    {
        if (!isIdValid(craftedObject) || !isIdValid(manfSchematic))
        {
            CustomerServiceLog("Faction", "WARNING: faction_perk.applyFactionCostObjvarFromSchematic called with invalid object " + craftedObject + " or schematic " + manfSchematic);
            return;
        }
        String draftSchematic = getDraftSchematic(manfSchematic);
        if (draftSchematic == null || draftSchematic.length() == 0)
        {
            CustomerServiceLog("Faction", "WARNING: faction_perk.applyFactionCostObjvarFromSchematic could not " + "find draft schematic template from manf schematic " + manfSchematic);
            return;
        }
        obj_id player = getCrafter(craftedObject);
        if (!isIdValid(player))
        {
            CustomerServiceLog("Faction", "WARNING: faction_perk.applyFactionCostObjvarFromSchematic could not " + "find owner of crafted object " + craftedObject);
            return;
        }
        String factionName = getStringObjVar(craftedObject, VAR_FACTION);
        if (factionName == null || factionName.length() == 0)
        {
            CustomerServiceLog("Faction", "WARNING: faction_perk.applyFactionCostObjvarFromSchematic could not " + "get faction name from objvar " + VAR_FACTION + " on object ", craftedObject);
            return;
        }
        String tbl = TBL_PERK_INVENTORY_BASE + toLower(factionName) + "/schematic.iff";
        int schematicRow = dataTableSearchColumnForString(draftSchematic, "template", tbl);
        if (schematicRow < 0)
        {
            CustomerServiceLog("Faction", "WARNING: faction_perk.applyFactionCostObjvarFromSchematic could not " + "find draft schematic template " + draftSchematic + " in datatable " + tbl);
            return;
        }
        int cost = dataTableGetInt(tbl, schematicRow, "owner_cost");
        if (cost > 0)
        {
            setObjVar(craftedObject, VAR_BIO_LINK_FACTION_POINTS, cost);
        }
    }
    public static boolean isValidReconTarget(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return false;
        }
        int pFac = pvpGetAlignedFaction(player);
        int tFac = pvpGetAlignedFaction(target);
        if (isPlayer(player) && pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            pFac = 0;
        }
        if (isPlayer(target) && pvpGetType(target) == PVPTYPE_NEUTRAL)
        {
            tFac = 0;
        }
        if (!pvpAreFactionsOpposed(pFac, tFac))
        {
            return false;
        }
        int got = getGameObjectType(target);
        if (isGameObjectTypeOf(got, GOT_installation_turret))
        {
            return true;
        }
        else if (isGameObjectTypeOf(got, GOT_installation_minefield))
        {
            return true;
        }
        else if (isGameObjectTypeOf(got, GOT_building_factional))
        {
            return true;
        }
        return false;
    }
    public static String[] getReconReport(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return null;
        }
        if (!isValidReconTarget(player, target))
        {
            return null;
        }
        Vector dta = new Vector();
        dta.setSize(0);
        int got = getGameObjectType(target);
        if (isGameObjectTypeOf(got, GOT_installation_turret))
        {
        }
        else if (isGameObjectTypeOf(got, GOT_building_factional))
        {
        }
        if (hasScript(target, "faction_perk.minefield.field"))
        {
        }
        String[] _dta = new String[0];
        if (dta != null)
        {
            _dta = new String[dta.size()];
            dta.toArray(_dta);
        }
        return _dta;
    }
    public static void giveBonusBaseDeeds(obj_id player, obj_id inv, String item_template, String faction, int declared) throws InterruptedException
    {
        int level = 1;
        if (item_template.indexOf("object/tangible/deed/faction_perk/hq/hq_s03") != -1)
        {
            level = 2;
        }
        else if (item_template.indexOf("object/tangible/deed/faction_perk/hq/hq_s04") != -1)
        {
            level = 3;
        }
        int level2 = 1;
        if (level == 3)
        {
            level2 = 2;
        }
        String item_template1 = "object/tangible/deed/faction_perk/hq/hq_s0" + level + "_" + toLower(faction) + ".iff";
        String item_template2 = "object/tangible/deed/faction_perk/hq/hq_s0" + level2 + "_" + toLower(faction) + ".iff";
        obj_id item1 = createObjectInInventoryAllowOverload(item_template1, player);
        setObjVar(item1, VAR_FACTION, faction);
        if (declared == 1)
        {
            setObjVar(item1, VAR_DECLARED, 1);
        }
        attachScript(item1, SCRIPT_FACTION_ITEM);
        obj_id item2 = createObjectInInventoryAllowOverload(item_template2, player);
        setObjVar(item2, VAR_FACTION, faction);
        if (declared == 1)
        {
            setObjVar(item2, VAR_DECLARED, 1);
        }
        attachScript(item2, SCRIPT_FACTION_ITEM);
        string_id PROSE_BONUS_BASE = new string_id(STF_PERK, "bonus_base_name");
        string_id strSpam = new string_id("faction_perk", "given_extra_bases");
        prose_package spam1 = prose.getPackage(PROSE_BONUS_BASE, getEncodedName(item1));
        prose_package spam2 = prose.getPackage(PROSE_BONUS_BASE, getEncodedName(item2));
        sendSystemMessage(player, strSpam);
        sendSystemMessageProse(player, spam1);
        sendSystemMessageProse(player, spam2);
        return;
    }
    public static int grabFactionBasePointValue(obj_id base) throws InterruptedException
    {
        String base_template = getTemplateName(base);
        int default_point_value = 1;
        final String TBL_HQ_POINT_VALUE = "datatables/faction_perk/hq/hq_point_values.iff";
        int idx = dataTableSearchColumnForString(base_template, "base_type", TBL_HQ_POINT_VALUE);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "faction_perk::grabFactionBasePointValue -- cannot find " + base_template + " in the hq_point_values datatable.");
            return default_point_value;
        }
        dictionary row = dataTableGetRow(TBL_HQ_POINT_VALUE, idx);
        int point_value = row.getInt("point_value");
        if (point_value < 1)
        {
            point_value = default_point_value;
        }
        return point_value;
    }
    public static boolean executeComlinkReinforcements(obj_id player) throws InterruptedException
    {
        obj_id comlink = getPlayerComlink(player);
        if (!isIdValid(comlink))
        {
            debugSpeakMsg(player, "no link");
            return false;
        }
        if (utils.hasScriptVar(player, COMM_COOLDOWN))
        {
            int timeElapsed = getGameTime() - utils.getIntScriptVar(player, COMM_COOLDOWN);
            if (timeElapsed < COMM_REUSE)
            {
                sendSystemMessage(player, SID_ALREADY_HAVE);
                return false;
            }
        }
        if (!static_item.validateLevelRequired(player, comlink))
        {
            sendSystemMessage(player, SID_TOO_LOW_LEVEL);
            return false;
        }
        obj_id world = getTopMostContainer(player);
        if (world != player)
        {
            sendSystemMessage(player, SID_INDOORS);
            return false;
        }
        if (!spawnTroopers(player))
        {
            debugSpeakMsg(player, "failz to spawn");
            return false;
        }
        sendCooldownGroupTimingOnly(player, (-1145728732), 900.0f);
        utils.setScriptVar(player, COMM_COOLDOWN, getGameTime());
        return true;
    }
    public static obj_id getPlayerComlink(obj_id player) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            return utils.getStaticItemInInventory(player, "item_pvp_lieutenant_comm_link_rebel_reward_04_01");
        }
        else if (factions.isImperial(player))
        {
            return utils.getStaticItemInInventory(player, "item_pvp_lieutenant_comm_link_imperial_reward_04_01");
        }
        else 
        {
            return null;
        }
    }
    public static boolean spawnTroopers(obj_id player) throws InterruptedException
    {
        return spawnTroopers(player, null, -1);
    }
    public static boolean spawnTroopers(obj_id player, String faction, int rank) throws InterruptedException
    {
        if (rank == -1)
        {
            rank = pvpGetCurrentGcwRank(player);
        }
        if (faction == null || (!faction.equals("rebel") && !faction.equals("imperial")))
        {
            faction = factions.isRebel(player) ? "rebel" : "imperial";
        }
        if (rank < 7)
        {
            sendSystemMessage(player, new string_id("gcw", "gcw_officer_only_use"));
            return false;
        }
        String toSpawn = "gcw_comm_link_reinforcement_" + faction + "_" + rank;
        int atLevel = getLevel(player) - 10 + rank;
        obj_id reinforcement = create.object(toSpawn, getLocation(player), atLevel);
        if (!isIdValid(reinforcement))
        {
            return false;
        }
        setMaster(reinforcement, player);
        attachScript(reinforcement, "item.gcw_buff_banner.pvp_lieutenant_comm_link_trooper");
        obj_id[] haters = getWhoIsTargetingMe(player);
        combat.sendCombatSpamMessage(player, new string_id("spam", "reinforcements_arrived"));
        for (int i = 0; haters != null && haters.length > 0 && i < haters.length; i++)
        {
            if (pvpCanAttack(player, haters[i]))
            {
                setHate(reinforcement, haters[i], 1);
            }
        }
        return true;
    }
}
