package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.money;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class crafting_contractor extends script.base_script
{
    public crafting_contractor()
    {
    }
    public static String c_stringFile = "conversation/crafting_contractor";
    public boolean crafting_contractor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean crafting_contractor_condition_is_artisan(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_artisan_novice");
    }
    public boolean crafting_contractor_condition_is_armorsmith(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_armorsmith_novice");
    }
    public boolean crafting_contractor_condition_is_weaponsmith(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_weaponsmith_novice");
    }
    public boolean crafting_contractor_condition_is_chef(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_chef_novice");
    }
    public boolean crafting_contractor_condition_is_tailor(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_tailor_novice");
    }
    public boolean crafting_contractor_condition_is_architect(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_architect_novice");
    }
    public boolean crafting_contractor_condition_is_droidengineer(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "crafting_droidengineer_novice");
    }
    public boolean crafting_contractor_condition_can_do_med(obj_id player, obj_id npc) throws InterruptedException
    {
        String prof = utils.getStringScriptVar(player, "contract.profession");
        if (prof == null || prof.equals(""))
        {
            return false;
        }
        if (prof.equals("weaponsmith"))
        {
            if (hasSkill(player, "crafting_weaponsmith_firearms_02") || hasSkill(player, "crafting_weaponsmith_melee_02") || hasSkill(player, "crafting_weaponsmith_munitions_02"))
            {
                return true;
            }
        }
        else if (prof.equals("tailor"))
        {
            if (hasSkill(player, "crafting_tailor_casual_02") || hasSkill(player, "crafting_tailor_field_02") || hasSkill(player, "crafting_tailor_formal_02"))
            {
                return true;
            }
        }
        else if (prof.equals("droidengineer"))
        {
            if (hasSkill(player, "crafting_droidengineer_production_02") || hasSkill(player, "crafting_droidengineer_techniques_02") || hasSkill(player, "crafting_droidengineer_refinement_02") || hasSkill(player, "crafting_droidengineer_blueprints_02"))
            {
                return true;
            }
        }
        else if (prof.equals("chef"))
        {
            if (hasSkill(player, "crafting_chef_dish_02") || hasSkill(player, "crafting_chef_dessert_02") || hasSkill(player, "crafting_chef_drink_02"))
            {
                return true;
            }
        }
        else if (prof.equals("artisan"))
        {
            if (hasSkill(player, "crafting_artisan_engineering_02") || hasSkill(player, "crafting_artisan_domestic_02"))
            {
                return true;
            }
        }
        else if (prof.equals("armorsmith"))
        {
            if (hasSkill(player, "crafting_armorsmith_personal_02") || hasSkill(player, "crafting_armorsmith_heavy_02") || hasSkill(player, "crafting_armorsmith_deflectors_02"))
            {
                return true;
            }
        }
        else if (prof.equals("architect"))
        {
            if (hasSkill(player, "crafting_architect_production_02") || hasSkill(player, "crafting_architect_techniques_02") || hasSkill(player, "crafting_architect_harvesting_02") || hasSkill(player, "crafting_architect_blueprints_02"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean crafting_contractor_condition_can_do_hard(obj_id player, obj_id npc) throws InterruptedException
    {
        String prof = utils.getStringScriptVar(player, "contract.profession");
        if (prof == null || prof.equals(""))
        {
            return false;
        }
        if (prof.equals("weaponsmith"))
        {
            if (hasSkill(player, "crafting_weaponsmith_firearms_04") || hasSkill(player, "crafting_weaponsmith_melee_04") || hasSkill(player, "crafting_weaponsmith_munitions_04"))
            {
                return true;
            }
        }
        else if (prof.equals("tailor"))
        {
            if (hasSkill(player, "crafting_tailor_casual_04") || hasSkill(player, "crafting_tailor_field_04") || hasSkill(player, "crafting_tailor_formal_04"))
            {
                return true;
            }
        }
        else if (prof.equals("droidengineer"))
        {
            if (hasSkill(player, "crafting_droidengineer_production_04") || hasSkill(player, "crafting_droidengineer_techniques_04") || hasSkill(player, "crafting_droidengineer_refinement_04") || hasSkill(player, "crafting_droidengineer_blueprints_04"))
            {
                return true;
            }
        }
        else if (prof.equals("chef"))
        {
            if (hasSkill(player, "crafting_chef_dish_04") || hasSkill(player, "crafting_chef_dessert_04") || hasSkill(player, "crafting_chef_drink_04"))
            {
                return true;
            }
        }
        else if (prof.equals("artisan"))
        {
            if (hasSkill(player, "crafting_artisan_engineering_04") || hasSkill(player, "crafting_artisan_domestic_04"))
            {
                return true;
            }
        }
        else if (prof.equals("armorsmith"))
        {
            if (hasSkill(player, "crafting_armorsmith_personal_04") || hasSkill(player, "crafting_armorsmith_heavy_04") || hasSkill(player, "crafting_armorsmith_deflectors_04"))
            {
                return true;
            }
        }
        else if (prof.equals("architect"))
        {
            if (hasSkill(player, "crafting_architect_production_04") || hasSkill(player, "crafting_architect_techniques_04") || hasSkill(player, "crafting_architect_harvesting_04") || hasSkill(player, "crafting_architect_blueprints_04"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean crafting_contractor_condition_is_inv_full(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space <= 0)
            {
                return true;
            }
            return false;
        }
        return true;
    }
    public boolean crafting_contractor_condition_has_box(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory, true);
            if (objContents != null)
            {
                for (int i = 0; i < objContents.length; i++)
                {
                    String strItemTemplate = getTemplateName(objContents[i]);
                    if (strItemTemplate.startsWith("object/tangible/container/quest/crafting_contract"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean crafting_contractor_condition_has_full_box(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory, true);
            if (objContents != null)
            {
                for (int i = 0; i < objContents.length; i++)
                {
                    String strItemTemplate = getTemplateName(objContents[i]);
                    if (strItemTemplate.startsWith("object/tangible/container/quest/crafting_contract"))
                    {
                        if (hasObjVar(objContents[i], "contract.complete"))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean crafting_contractor_condition_not_time_yet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "contract.decline"))
        {
            return false;
        }
        int old_time = utils.getIntScriptVar(player, "contract.decline");
        int new_time = getGameTime();
        if ((new_time - old_time) < 60)
        {
            return true;
        }
        return false;
    }
    public boolean crafting_contractor_condition_can_find_job(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "contract.profession"))
        {
            return false;
        }
        String datatable = "datatables/quest/crafting/crafting_quests.iff";
        String prof = utils.getStringScriptVar(player, "contract.profession");
        String diff = utils.getStringScriptVar(player, "contract.difficulty");
        String[] professions = dataTableGetStringColumn(datatable, 0);
        String[] difficulties = dataTableGetStringColumn(datatable, 1);
        int min = -1;
        int max = -1;
        for (int i = 0; i < professions.length; i++)
        {
            if (prof.equals(professions[i]))
            {
                if (min < 0)
                {
                    min = i;
                    max = i;
                }
                else 
                {
                    max++;
                }
            }
        }
        if (min < 0)
        {
            return false;
        }
        int newMin = -1;
        int newMax = -1;
        for (int j = min; j <= max; j++)
        {
            if (diff.equals(difficulties[j]))
            {
                if (newMin < 0)
                {
                    newMin = j;
                    newMax = j;
                }
                else 
                {
                    newMax++;
                }
            }
        }
        if (newMin < 0)
        {
            return false;
        }
        String schematic = null;
        int r = -1;
        int c = 0;
        do
        {
            r = rand(newMin, newMax);
            schematic = dataTableGetString(datatable, r, 2);
            c++;
        } while (!hasSchematic(player, schematic) && c < 100);
        if (c >= 100)
        {
            return false;
        }
        String itemName = localize(getProductNameFromSchematic(schematic));
        utils.setScriptVar(player, "contract.row", r);
        if ((toLower(itemName)).startsWith("a "))
        {
            itemName = itemName.substring(2);
        }
        else if ((toLower(itemName)).startsWith("an "))
        {
            itemName = itemName.substring(3);
        }
        if ((toLower(itemName)).endsWith("h") || (toLower(itemName)).endsWith("z") || (toLower(itemName)).endsWith("o"))
        {
            itemName = itemName + "es";
        }
        else if ((toLower(itemName)).endsWith("y"))
        {
            itemName = itemName.substring(0, (itemName.length() - 1)) + "ies";
        }
        else if ((toLower(itemName)).endsWith("dress") || (toLower(itemName)).endsWith("glass"))
        {
            itemName = itemName + "es";
        }
        else if (!(toLower(itemName)).endsWith("s"))
        {
            itemName = itemName + "s";
        }
        utils.setScriptVar(player, "contract.item_name", itemName);
        return true;
    }
    public void crafting_contractor_action_set_architect_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "architect");
    }
    public void crafting_contractor_action_set_armorsmith_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "armorsmith");
    }
    public void crafting_contractor_action_set_artisan_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "artisan");
    }
    public void crafting_contractor_action_set_chef_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "chef");
    }
    public void crafting_contractor_action_set_droidengineer_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "droidengineer");
    }
    public void crafting_contractor_action_set_tailor_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "tailor");
    }
    public void crafting_contractor_action_set_weaponsmith_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.profession", "weaponsmith");
    }
    public void crafting_contractor_action_cleanup_vars(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.removeScriptVar(player, "contract.profession");
        utils.removeScriptVar(player, "contract.difficulty");
        utils.removeScriptVar(player, "contract.number");
        utils.removeScriptVar(player, "contract.row");
        utils.removeScriptVar(player, "contract.decline");
    }
    public void crafting_contractor_action_set_easy_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.difficulty", "easy");
    }
    public void crafting_contractor_action_set_med_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.difficulty", "medium");
    }
    public void crafting_contractor_action_set_hard_var(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.difficulty", "hard");
    }
    public void crafting_contractor_action_cleanup_decline(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.removeScriptVar(player, "contract.profession");
        utils.removeScriptVar(player, "contract.difficulty");
        utils.removeScriptVar(player, "contract.number");
        utils.removeScriptVar(player, "contract.row");
        utils.setScriptVar(player, "contract.decline", getGameTime());
    }
    public void crafting_contractor_action_set_decline(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "contract.decline", getGameTime());
    }
    public void crafting_contractor_action_give_crate(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!utils.hasScriptVar(player, "contract.profession") || !utils.hasScriptVar(player, "contract.row") || !utils.hasScriptVar(player, "contract.number"))
        {
            return;
        }
        String prof = utils.getStringScriptVar(player, "contract.profession");
        String crate_template = "";
        if (prof.equals("artisan"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/generic_crate.iff";
        }
        else if (prof.equals("droidengineer"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/droid_crate.iff";
        }
        else if (prof.equals("architect"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/furniture_crate.iff";
        }
        else if (prof.equals("tailor"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/clothing_crate.iff";
        }
        else if (prof.equals("chef"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/food_crate.iff";
        }
        else if (prof.equals("weaponsmith"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/weapon_crate.iff";
        }
        else if (prof.equals("armorsmith"))
        {
            crate_template = "object/tangible/container/quest/crafting_contract/armor_crate.iff";
        }
        else 
        {
            return;
        }
        obj_id playerInv = utils.getInventoryContainer(player);
        if (!isIdValid(playerInv))
        {
            return;
        }
        obj_id crate = createObject(crate_template, playerInv, "");
        if (!isIdValid(crate))
        {
            return;
        }
        String xp_type = "";
        if (prof.equals("artisan"))
        {
            xp_type = "crafting_general";
        }
        else if (prof.equals("droidengineer"))
        {
            xp_type = "crafting_droid_general";
        }
        else if (prof.equals("architect"))
        {
            xp_type = "crafting_structure_general";
        }
        else if (prof.equals("tailor"))
        {
            xp_type = "crafting_clothing_general";
        }
        else if (prof.equals("chef"))
        {
            xp_type = "crafting_food_general";
        }
        else if (prof.equals("weaponsmith"))
        {
            xp_type = "crafting_weapons_general";
        }
        else if (prof.equals("armorsmith"))
        {
            xp_type = "crafting_clothing_armor";
        }
        attachScript(crate, "quest.crafting_contract.crate");
        int row = utils.getIntScriptVar(player, "contract.row");
        int number = utils.getIntScriptVar(player, "contract.number");
        String diff = utils.getStringScriptVar(player, "contract.difficulty");
        if (diff == null || diff.equals(""))
        {
            diff = "easy";
        }
        String datatable = "datatables/quest/crafting/crafting_quests.iff";
        String item_template = dataTableGetString(datatable, row, 3);
        setObjVar(crate, "contract.item", item_template);
        setObjVar(crate, "contract.max", number);
        setObjVar(crate, "contract.difficulty", diff);
        setObjVar(crate, "contract.xp_type", xp_type);
        utils.removeScriptVar(player, "contract.profession");
        utils.removeScriptVar(player, "contract.difficulty");
        utils.removeScriptVar(player, "contract.number");
        utils.removeScriptVar(player, "contract.row");
    }
    public void crafting_contractor_action_take_box_quit(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory, true);
            if (objContents != null)
            {
                for (int i = 0; i < objContents.length; i++)
                {
                    String strItemTemplate = getTemplateName(objContents[i]);
                    if (strItemTemplate.startsWith("object/tangible/container/quest/crafting_contract"))
                    {
                        destroyObject(objContents[i]);
                    }
                }
            }
        }
    }
    public void crafting_contractor_action_job_done(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id objInventory = utils.getInventoryContainer(player);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = utils.getContents(objInventory, true);
            if (objContents != null)
            {
                for (int i = 0; i < objContents.length; i++)
                {
                    String strItemTemplate = getTemplateName(objContents[i]);
                    if (strItemTemplate.startsWith("object/tangible/container/quest/crafting_contract"))
                    {
                        obj_id item = objContents[i];
                        int exp = getIntObjVar(item, "contract.xp");
                        int num = getIntObjVar(item, "contract.max");
                        String xp_type = getStringObjVar(item, "contract.xp_type");
                        String diff = getStringObjVar(item, "contract.difficulty");
                        exp = (int)(exp * 0.20f);
                        exp *= num;
                        xp.grantCraftingStyleXp(player, xp_type, exp);
                        int pay = 15;
                        if (diff.equals("medium"))
                        {
                            pay = 25;
                        }
                        else if (diff.equals("hard"))
                        {
                            pay = 35;
                        }
                        pay *= num;
                        money.systemPayout(money.ACCT_CRAFTING_JOB, player, pay, null, null);
                        string_id pay_msg = new string_id("quest/crafting_contract/system_messages", "pay_msg");
                        prose_package pp = prose.getPackage(pay_msg, pay);
                        sendSystemMessageProse(player, pp);
                        destroyObject(item);
                    }
                }
            }
        }
        utils.removeScriptVar(player, "contract.profession");
        utils.removeScriptVar(player, "contract.difficulty");
        utils.removeScriptVar(player, "contract.number");
        utils.removeScriptVar(player, "contract.row");
        utils.removeScriptVar(player, "contract.decline");
    }
    public String crafting_contractor_tokenTO_to_item(obj_id player, obj_id npc) throws InterruptedException
    {
        String itemName = utils.getStringScriptVar(player, "contract.item_name");
        utils.removeScriptVar(player, "contract.item_name");
        return itemName;
    }
    public int crafting_contractor_tokenDI_di_number(obj_id player, obj_id npc) throws InterruptedException
    {
        int r = rand(5, 10);
        utils.setScriptVar(player, "contract.number", r);
        return r;
    }
    public int crafting_contractor_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c7d6f79"))
        {
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51b72d61");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bdac2f18"))
        {
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                crafting_contractor_action_take_box_quit(player, npc);
                string_id message = new string_id(c_stringFile, "s_436186dd");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int crafting_contractor_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb7fcc27"))
        {
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404252ce");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_is_artisan(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_is_droidengineer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition_is_architect(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition_is_tailor(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (crafting_contractor_condition_is_chef(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (crafting_contractor_condition_is_weaponsmith(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (crafting_contractor_condition_is_armorsmith(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9545de7b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f19dea3c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_445effe2");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf890106");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f38e5bb9");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86f52ae1");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc453c0d");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220e8901");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b9dc4832"))
        {
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int crafting_contractor_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9545de7b"))
        {
            crafting_contractor_action_set_artisan_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f19dea3c"))
        {
            crafting_contractor_action_set_droidengineer_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_445effe2"))
        {
            crafting_contractor_action_set_architect_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bf890106"))
        {
            crafting_contractor_action_set_tailor_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f38e5bb9"))
        {
            crafting_contractor_action_set_chef_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86f52ae1"))
        {
            crafting_contractor_action_set_weaponsmith_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bc453c0d"))
        {
            crafting_contractor_action_set_armorsmith_var(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8cadb7f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition_can_do_hard(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition_can_do_med(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b62865");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c56a5af");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f59f8579");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_deaa4415");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_220e8901"))
        {
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int crafting_contractor_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37b62865"))
        {
            crafting_contractor_action_set_hard_var(player, npc);
            if (crafting_contractor_condition_can_find_job(player, npc))
            {
                crafting_contractor_action_set_decline(player, npc);
                string_id message = new string_id(c_stringFile, "s_f58f323c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5278cc5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b95d1f");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                crafting_contractor_action_cleanup_vars(player, npc);
                string_id message = new string_id(c_stringFile, "s_7db7e801");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2c56a5af"))
        {
            crafting_contractor_action_set_med_var(player, npc);
            if (crafting_contractor_condition_can_find_job(player, npc))
            {
                crafting_contractor_action_set_decline(player, npc);
                string_id message = new string_id(c_stringFile, "s_f58f323c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5278cc5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b95d1f");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                crafting_contractor_action_cleanup_vars(player, npc);
                string_id message = new string_id(c_stringFile, "s_7db7e801");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f59f8579"))
        {
            crafting_contractor_action_set_easy_var(player, npc);
            if (crafting_contractor_condition_can_find_job(player, npc))
            {
                crafting_contractor_action_set_decline(player, npc);
                string_id message = new string_id(c_stringFile, "s_f58f323c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (crafting_contractor_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d5278cc5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2b95d1f");
                    }
                    utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.other.set(crafting_contractor_tokenTO_to_item(player, npc));
                    pp.digitInteger = crafting_contractor_tokenDI_di_number(player, npc);
                    chat.chat(npc, player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                crafting_contractor_action_cleanup_vars(player, npc);
                string_id message = new string_id(c_stringFile, "s_7db7e801");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_deaa4415"))
        {
            crafting_contractor_action_cleanup_vars(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int crafting_contractor_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d5278cc5"))
        {
            if (crafting_contractor_condition_is_inv_full(player, npc))
            {
                crafting_contractor_action_cleanup_vars(player, npc);
                string_id message = new string_id(c_stringFile, "s_82c3e20a");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                crafting_contractor_action_give_crate(player, npc);
                string_id message = new string_id(c_stringFile, "s_62092c04");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b2b95d1f"))
        {
            crafting_contractor_action_cleanup_decline(player, npc);
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73c628e8");
                utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.crafting_contractor");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.crafting_contractor");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!crafting_contractor_condition_is_artisan(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e3aab67f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (crafting_contractor_condition_has_full_box(player, npc))
        {
            crafting_contractor_action_job_done(player, npc);
            string_id message = new string_id(c_stringFile, "s_a62477a9");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (crafting_contractor_condition_has_box(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d5a1b00c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9c7d6f79");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bdac2f18");
                }
                utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 3);
                npcStartConversation(player, npc, "crafting_contractor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (crafting_contractor_condition_not_time_yet(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b3d0de8");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (crafting_contractor_condition__defaultCondition(player, npc))
        {
            crafting_contractor_action_cleanup_vars(player, npc);
            string_id message = new string_id(c_stringFile, "s_14e7249");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (crafting_contractor_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eb7fcc27");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9dc4832");
                }
                utils.setScriptVar(player, "conversation.crafting_contractor.branchId", 7);
                npcStartConversation(player, npc, "crafting_contractor", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("crafting_contractor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.crafting_contractor.branchId");
        if (branchId == 3 && crafting_contractor_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && crafting_contractor_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && crafting_contractor_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && crafting_contractor_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && crafting_contractor_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.crafting_contractor.branchId");
        return SCRIPT_CONTINUE;
    }
}
