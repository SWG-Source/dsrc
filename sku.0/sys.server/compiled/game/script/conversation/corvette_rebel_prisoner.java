package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.factions;
import script.library.group;
import script.*;

public class corvette_rebel_prisoner extends script.base_script
{
    public corvette_rebel_prisoner()
    {
    }
    public static String c_stringFile = "conversation/corvette_rebel_prisoner";
    public boolean corvette_rebel_prisoner_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corvette_rebel_prisoner_condition_rescued(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "corl_corvette.alreadyRescued");
    }
    public void corvette_rebel_prisoner_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void corvette_rebel_prisoner_action_factionReward(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        if (!hasObjVar(npc, "corl_corvette.alreadyRescued"))
        {
            factions.addFactionStanding(player, "Rebel", 10);
            setObjVar(npc, "corl_corvette.alreadyRescued", 1);
            messageTo(npc, "escapeNow", null, 2, false);
            if (group.isGrouped(player))
            {
                obj_id groupObj = getGroupObject(player);
                obj_id[] groupMembers = getGroupMemberIds(groupObj);
                int numGroupMembers = groupMembers.length;
                for (obj_id groupie : groupMembers) {
                    if (isIdValid(groupie)) {
                        if (groupie != player) {
                            factions.addFactionStanding(groupie, "Rebel", 7);
                        }
                    }
                }
            }
        }
        return;
    }
    public void corvette_rebel_prisoner_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.corvette_rebel_prisoner");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
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
        detachScript(self, "npc.conversation.corvette_rebel_prisoner");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (corvette_rebel_prisoner_condition_rescued(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_4bc31f77");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (corvette_rebel_prisoner_condition__defaultCondition(player, self))
        {
            corvette_rebel_prisoner_action_factionReward(player, self);
            string_id message = new string_id(c_stringFile, "s_64954a77");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corvette_rebel_prisoner"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.corvette_rebel_prisoner.branchId");
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.corvette_rebel_prisoner.branchId");
        return SCRIPT_CONTINUE;
    }
}
