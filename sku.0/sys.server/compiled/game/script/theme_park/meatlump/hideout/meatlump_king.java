package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.buff;
import script.library.chat;
import script.library.groundquests;
import script.library.prose;
import script.library.static_item;
import script.library.utils;

public class meatlump_king extends script.base_script
{
    public meatlump_king()
    {
    }
    public static final String MEATLUMP_KING_DATATABLE = "datatables/theme_park/meatlump/meatlump_king.iff";
    public static final String OFFERING_COLUMN = "offering";
    public static final String REACTION_TYPE_COLUMN = "reactionType";
    public static final String REACTION_COLUMN = "reaction";
    public static final int EMOTE = 0;
    public static final int CHAT = 1;
    public static final int GIVE = 2;
    public static final int SPECIAL = 3;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id emoteSayer, String emoteSeen) throws InterruptedException
    {
        if (!isPlayer(emoteSayer) || ai_lib.isInCombat(emoteSayer) || isIncapacitated(emoteSayer) || isDead(emoteSayer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id emoteTarget = getIntendedTarget(emoteSayer);
        if (!isIdValid(emoteTarget))
        {
            return SCRIPT_CONTINUE;
        }
        if (emoteTarget == self)
        {
            int row = dataTableSearchColumnForString(emoteSeen, OFFERING_COLUMN, MEATLUMP_KING_DATATABLE);
            if (row > -1)
            {
                determineKingReaction(self, emoteSayer, row);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id giver) throws InterruptedException
    {
        if (!isPlayer(giver) || ai_lib.isInCombat(giver) || isIncapacitated(giver) || isDead(giver))
        {
            return SCRIPT_CONTINUE;
        }
        String itemName = getTemplateName(item);
        if (static_item.isStaticItem(item))
        {
            itemName = getStaticItemName(item);
        }
        int row = dataTableSearchColumnForString(itemName, OFFERING_COLUMN, MEATLUMP_KING_DATATABLE);
        if (row > -1)
        {
            determineKingReaction(self, giver, row);
            decrementCount(item);
        }
        return SCRIPT_CONTINUE;
    }
    public void determineKingReaction(obj_id self, obj_id player, int row) throws InterruptedException
    {
        int reactionType = dataTableGetInt(MEATLUMP_KING_DATATABLE, row, REACTION_TYPE_COLUMN);
        String reaction = dataTableGetString(MEATLUMP_KING_DATATABLE, row, REACTION_COLUMN);
        processKingReaction(self, player, reactionType, reaction);
        boolean moreReactions = true;
        int index = 2;
        while (moreReactions)
        {
            int nextReactionType = dataTableGetInt(MEATLUMP_KING_DATATABLE, row, REACTION_TYPE_COLUMN + "_" + index);
            String nextReaction = dataTableGetString(MEATLUMP_KING_DATATABLE, row, REACTION_COLUMN + "_" + index);
            if (nextReaction == null || nextReaction.length() < 1)
            {
                moreReactions = false;
            }
            else 
            {
                processKingReaction(self, player, nextReactionType, nextReaction);
                index = index + 1;
            }
        }
        return;
    }
    public void processKingReaction(obj_id self, obj_id player, int reactionType, String reaction) throws InterruptedException
    {
        faceTo(self, player);
        switch (reactionType)
        {
            case EMOTE:
            doAnimationAction(self, reaction);
            break;
            case CHAT:
            prose_package pp = prose.getPackage(new string_id("theme_park/corellia/quest", reaction), player, player);
            String pronounTT = getGender(player) == GENDER_MALE ? "boy" : "girl";
            prose.setTT(pp, pronounTT);
            chat.chat(self, player, chat.CHAT_SAY, null, pp);
            break;
            case GIVE:
            String[] items = split(reaction, ':');
            String itemToGive = items[rand(0, items.length - 1)];
            if (itemToGive != null && itemToGive.length() > 0)
            {
                obj_id inv = getObjectInSlot(player, "inventory");
                if (isIdValid(inv))
                {
                    obj_id givenItem = static_item.createNewItemFunction(itemToGive, inv);
                    groundquests.sendPlacedInInventorySystemMessage(player, givenItem, reaction);
                }
            }
            break;
            case SPECIAL:
            break;
        }
        return;
    }
}
