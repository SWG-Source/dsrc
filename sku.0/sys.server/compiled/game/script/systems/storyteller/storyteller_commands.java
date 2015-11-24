package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.guild;
import script.library.loot;
import script.library.pgc_quests;
import script.library.prose;
import script.library.skill_template;
import script.library.storyteller;
import script.library.sui;
import script.library.target_dummy;
import script.library.utils;

public class storyteller_commands extends script.base_script
{
    public storyteller_commands()
    {
    }
    public static final string_id SID_STORYTELLER_HELP = new string_id("storyteller", "storyteller_help");
    public static final string_id SID_STORYTELLER_TITLE = new string_id("storyteller", "storyteller_title");
    public static final string_id SID_STORYTELLER_MENU_CMDS = new string_id("storyteller", "storyteller_menu_commands");
    public static final string_id SID_STORYTELLER_MENU_PROPS = new string_id("storyteller", "storyteller_menu_props");
    public static final string_id SID_STORYTELLER_MENU_NPCS = new string_id("storyteller", "storyteller_menu_npcs");
    public static final string_id SID_STORYTELLER_MENU_FXS = new string_id("storyteller", "storyteller_menu_effects");
    public static final string_id SID_STORYTELLER_HELP_CMDS = new string_id("storyteller", "storyteller_help_commands");
    public static final string_id SID_STORYTELLER_HELP_PROPS = new string_id("storyteller", "storyteller_help_props");
    public static final string_id SID_STORYTELLER_HELP_NPCS = new string_id("storyteller", "storyteller_help_npcs");
    public static final string_id SID_STORYTELLER_HELP_FXS = new string_id("storyteller", "storyteller_help_effects");
    public static final string_id SID_CANNOT_INVITE_ASSISTANTS = new string_id("storyteller", "assistant_cannot_invite_assistants");
    public static final string_id SID_ASSISTANT_ADD_INVALID = new string_id("storyteller", "assistant_add_target_invalid");
    public static final string_id SID_ASSISTANT_ADD_NOT_A_PLAYER = new string_id("storyteller", "assistant_add_not_a_player");
    public static final string_id SID_ALREADY_YOUR_ASSISTANT = new string_id("storyteller", "assistant_add_already_yours");
    public static final string_id SID_ALREADY_AN_ASSISTANT = new string_id("storyteller", "assistant_add_already_one");
    public static final string_id SID_DECLINED_ASSISTANT = new string_id("storyteller", "assistant_add_declined");
    public static final string_id SID_ASSISTANT_ADDED = new string_id("storyteller", "assistant_added");
    public static final string_id SID_YOU_QUIT_STORY_ASSISTANT = new string_id("storyteller", "assistant_you_quit");
    public static final string_id SID_YOU_NOT_A_STORY_ASSISTANT = new string_id("storyteller", "assistant_you_not_one");
    public static final string_id SID_ASSISTANT_REMOVE_INVALID = new string_id("storyteller", "assistant_remove_target_invalid");
    public static final string_id SID_ASSISTANT_REMOVE_NOT_PLAYER = new string_id("storyteller", "assistant_remove_not_a_player");
    public static final string_id SID_SOMEONE_ELSES_ASSISTANT = new string_id("storyteller", "assistant_someone_elses");
    public static final string_id SID_NOT_STORYTELLER_ASSISTANT = new string_id("storyteller", "assistant_not_one");
    public static final string_id SID_ASSISTANT_REMOVED = new string_id("storyteller", "assistant_removed");
    public static final string_id SID_ASSISTANT_ADDED_PLAYER = new string_id("storyteller", "assistant_added_player");
    public static final string_id SID_ASSISTANT_ALREADY_STORY = new string_id("storyteller", "assistant_already_your_story");
    public static final string_id SID_NOT_STORYTELLER_STORY = new string_id("storyteller", "not_storyteller_story");
    public static final string_id SID_LEAVING_STORYTELLER = new string_id("storyteller", "leaving_storyteller");
    public static final string_id SID_CANNOT_INVITE = new string_id("storyteller", "cannot_invite");
    public static final string_id SID_INVITE_AREA_INVITE_COMPLETE = new string_id("storyteller", "area_invite_complete");
    public static final string_id SID_INVITE_NOT_IN_A_GUILD = new string_id("storyteller", "invite_not_in_a_guild");
    public static final string_id SID_INVITE_TARGET_INVALID = new string_id("storyteller", "invite_target_invalid");
    public static final string_id SID_INVITE_NOT_A_PLAYER = new string_id("storyteller", "invite_target_not_a_player");
    public static final string_id SID_ADDED_PLAYER = new string_id("storyteller", "storyteller_added_player");
    public static final string_id SID_REMOVE_TARGET_INVALID = new string_id("storyteller", "remove_target_invalid");
    public static final string_id SID_REMOVE_NOT_A_PLAYER = new string_id("storyteller", "remove_target_not_a_player");
    public static final string_id SID_REMOVED_PLAYER = new string_id("storyteller", "storyteller_removed_player");
    public static final string_id SID_ALREADY_STORY = new string_id("storyteller", "already_story");
    public static final string_id SID_ALREADY_YOUR_STORY = new string_id("storyteller", "already_your_story");
    public static final string_id SID_DECLINED_INVITE = new string_id("storyteller", "invite_add_declined");
    public static final string_id SID_YOU_LEFT_STORY = new string_id("storyteller", "you_left_story");
    public static final string_id SID_YOU_NOT_IN_A_STORY = new string_id("storyteller", "you_not_in_a_story");
    public static final String INCLUDE_ALL_STORYTELLERS = "all_storytellers";
    public static final string_id SID_PGC_RATING_TARGET_INVALID = new string_id("saga_system", "pgc_rating_target_invalid");
    public static final string_id SID_PGC_RATING_TARGET_NOT_A_PLAYER = new string_id("saga_system", "pgc_rating_target_not_a_player");
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            obj_id storytellerId = self;
            if (utils.hasScriptVar(self, "storytellerid"))
            {
                obj_id tempStorytellerId = utils.getObjIdScriptVar(self, "storytellerid");
                if (isIdValid(tempStorytellerId))
                {
                    storytellerId = tempStorytellerId;
                }
            }
            storyteller.showStorytellerEffectsInAreaToPlayer(self, storytellerId);
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerMode(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String title = utils.packStringId(SID_STORYTELLER_TITLE);
        String prompt = utils.packStringId(SID_STORYTELLER_HELP);
        int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, title, getHelpTypeArray(), "msgStorytellerHelpOptionSelected");
        if (pid > -1)
        {
            utils.setScriptVar(self, "storytellerHelp.pid", pid);
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getHelpTypeArray() throws InterruptedException
    {
        string_id[] sid_storyteller_help_options = 
        {
            SID_STORYTELLER_MENU_CMDS,
            SID_STORYTELLER_MENU_PROPS,
            SID_STORYTELLER_MENU_NPCS,
            SID_STORYTELLER_MENU_FXS
        };
        Vector storytellerHelpTypes = new Vector();
        storytellerHelpTypes.setSize(0);
        for (int i = 0; i < sid_storyteller_help_options.length; i++)
        {
            String tokenType = utils.packStringId(sid_storyteller_help_options[i]);
            storytellerHelpTypes = utils.addElement(storytellerHelpTypes, tokenType);
        }
        String[] _storytellerHelpTypes = new String[0];
        if (storytellerHelpTypes != null)
        {
            _storytellerHelpTypes = new String[storytellerHelpTypes.size()];
            storytellerHelpTypes.toArray(_storytellerHelpTypes);
        }
        return _storytellerHelpTypes;
    }
    public int msgStorytellerHelpOptionSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (utils.hasScriptVar(self, "storytellerHelp.pid"))
        {
            utils.removeScriptVar(self, "storytellerHelp.pid");
        }
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String prompt = utils.packStringId(SID_STORYTELLER_HELP);
        switch (idx)
        {
            case 0:
            prompt = utils.packStringId(SID_STORYTELLER_HELP_CMDS);
            break;
            case 1:
            prompt = utils.packStringId(SID_STORYTELLER_HELP_PROPS);
            break;
            case 2:
            prompt = utils.packStringId(SID_STORYTELLER_HELP_NPCS);
            break;
            case 3:
            prompt = utils.packStringId(SID_STORYTELLER_HELP_FXS);
            break;
        }
        String title = utils.packStringId(SID_STORYTELLER_TITLE);
        int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, title, getHelpTypeArray(), "msgStorytellerHelpOptionSelected");
        if (pid > -1)
        {
            utils.setScriptVar(self, "storytellerHelp.pid", pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int storyAutoDeclineInvites(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (hasObjVar(self, storyteller.VAR_AUTODECLINE_STORY_INVITES))
        {
            removeObjVar(self, storyteller.VAR_AUTODECLINE_STORY_INVITES);
            sendSystemMessage(self, new string_id("storyteller", "autodecline_story_invites_off"));
        }
        else 
        {
            setObjVar(self, storyteller.VAR_AUTODECLINE_STORY_INVITES, 1);
            sendSystemMessage(self, new string_id("storyteller", "autodecline_story_invites_on"));
        }
        return SCRIPT_CONTINUE;
    }
    public int makeStoryAssistant(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "storytellerAssistant"))
        {
            sendSystemMessage(self, SID_CANNOT_INVITE_ASSISTANTS);
            return SCRIPT_CONTINUE;
        }
        target = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            String invitee = (st.nextToken()).toLowerCase();
            target = getPlayerIdFromFirstName(invitee);
        }
        if (!isIdValid(target))
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_ASSISTANT_ADD_INVALID);
                return SCRIPT_CONTINUE;
            }
            else if (!isPlayer(target))
            {
                prose_package pp = prose.getPackage(SID_ASSISTANT_ADD_NOT_A_PLAYER, self, self);
                prose.setTO(pp, getEncodedName(target));
                sendSystemMessageProse(self, pp);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", self);
        webster.put("storytellerName", getName(self));
        messageTo(target, "handleStoryTellerAssistantRequest", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerAlreadyAnAssistant(obj_id self, dictionary params) throws InterruptedException
    {
        boolean alreadyAStorytellersAssistant = params.getBoolean("alreadyAStorytellersAssistant");
        String targetName = params.getString("targetName");
        prose_package pp = prose.getPackage(SID_ALREADY_AN_ASSISTANT, self, self);
        prose.setTO(pp, targetName);
        if (alreadyAStorytellersAssistant)
        {
            pp = prose.getPackage(SID_ALREADY_YOUR_ASSISTANT, self, self);
            prose.setTO(pp, targetName);
        }
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerAssistantDeclined(obj_id self, dictionary params) throws InterruptedException
    {
        String targetName = params.getString("targetName");
        prose_package pp = prose.getPackage(SID_DECLINED_ASSISTANT, self, self);
        prose.setTO(pp, targetName);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int storyAssistantHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", self);
        webster.put("storytellerName", getName(self));
        if (btn == sui.BP_CANCEL)
        {
            messageTo(player, "handleTellStorytellerIDeclinedAssistant", webster, 0, false);
            return SCRIPT_CONTINUE;
        }
        messageTo(player, "handleStoryTellerAssistantAccepted", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerAssistantHasBeenAdded(obj_id self, dictionary params) throws InterruptedException
    {
        String addedPlayerName = params.getString("assistantPlayerName");
        if (addedPlayerName != null && addedPlayerName.length() > 0)
        {
            prose_package pp = prose.getPackage(SID_ASSISTANT_ADDED, self, self);
            prose.setTO(pp, addedPlayerName);
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int quitStoryAssistant(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "storytellerAssistant"))
        {
            utils.removeScriptVar(self, "storytellerAssistant");
            utils.removeScriptVar(self, "storytellerAssistantName");
            sendSystemMessage(self, SID_YOU_QUIT_STORY_ASSISTANT);
        }
        else 
        {
            sendSystemMessage(self, SID_YOU_NOT_A_STORY_ASSISTANT);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeStoryAssistant(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        target = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            String invitee = (st.nextToken()).toLowerCase();
            target = getPlayerIdFromFirstName(invitee);
        }
        if (!isIdValid(target))
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_ASSISTANT_REMOVE_INVALID);
                return SCRIPT_CONTINUE;
            }
            else if (!isPlayer(target))
            {
                sendSystemMessage(self, SID_ASSISTANT_REMOVE_NOT_PLAYER);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", self);
        webster.put("storytellerName", getName(self));
        messageTo(target, "handleStoryTellerRemoveAssistant", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerRemovePlayerNotAssistant(obj_id self, dictionary params) throws InterruptedException
    {
        String targetName = params.getString("targetName");
        prose_package pp = prose.getPackage(SID_NOT_STORYTELLER_ASSISTANT, self, self);
        prose.setTO(pp, targetName);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerAssistantHasBeenRemoved(obj_id self, dictionary params) throws InterruptedException
    {
        String removedPlayerName = params.getString("removedPlayerName");
        if (removedPlayerName != null && removedPlayerName.length() > 0)
        {
            prose_package pp = prose.getPackage(SID_ASSISTANT_REMOVED, self, self);
            prose.setTO(pp, removedPlayerName);
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int inviteToStory(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id storytellerId = self;
        String storytellerName = getName(self);
        obj_id storytellerAssistant = null;
        if (utils.hasScriptVar(self, "storytellerAssistant"))
        {
            storytellerId = utils.getObjIdScriptVar(self, "storytellerAssistant");
            storytellerName = utils.getStringScriptVar(self, "storytellerAssistantName");
            storytellerAssistant = self;
        }
        target = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            String invitee = (st.nextToken()).toLowerCase();
            if (invitee.equals("myself") || invitee.equals("self"))
            {
                target = self;
            }
            else if (invitee.equals("area_invite") && isGod(self))
            {
                obj_id[] playersInRange = getAllPlayers(getLocation(self), 120f);
                if (playersInRange != null && playersInRange.length > 0)
                {
                    for (int i = 0; i < playersInRange.length; i++)
                    {
                        obj_id playerToInvite = playersInRange[i];
                        if (isIdValid(playerToInvite) && playerToInvite != self)
                        {
                            dictionary webster = new dictionary();
                            webster.put("storytellerPlayer", storytellerId);
                            webster.put("storytellerName", storytellerName);
                            messageTo(playerToInvite, "handleStoryTellerInviteRequest", webster, 0, false);
                        }
                    }
                }
                sendSystemMessage(self, SID_INVITE_AREA_INVITE_COMPLETE);
                return SCRIPT_CONTINUE;
            }
            else if (invitee.equals("guild"))
            {
                int guildId = getGuildId(self);
                if (isGod(self) && st.countTokens() > 0)
                {
                    String guildInvited = "";
                    int numTokensRemaining = st.countTokens();
                    for (int i = 0; i < numTokensRemaining; i++)
                    {
                        if (guildInvited.equals(""))
                        {
                            guildInvited = guildInvited + st.nextToken();
                        }
                        else 
                        {
                            guildInvited = guildInvited + " " + st.nextToken();
                        }
                    }
                    guildId = findGuild(guildInvited);
                    if (guildId < 1)
                    {
                        string_id message = new string_id("storyteller", "invite_god_only_bad_guild");
                        prose_package pp = prose.getPackage(message, self, self);
                        prose.setTO(pp, guildInvited);
                        sendSystemMessageProse(self, pp);
                    }
                    else 
                    {
                        string_id message = new string_id("storyteller", "invite_god_only_other_guild");
                        prose_package pp = prose.getPackage(message, self, self);
                        prose.setTO(pp, guildInvited);
                        sendSystemMessageProse(self, pp);
                    }
                }
                if (guildId > 0)
                {
                    obj_id[] guildMembers = guild.getMemberIds(guildId, false, true);
                    if (guildMembers != null && guildMembers.length > 0)
                    {
                        for (int i = 0; i < guildMembers.length; i++)
                        {
                            obj_id guildMember = guildMembers[i];
                            if (isIdValid(guildMember) && guildMember != self)
                            {
                                dictionary webster = new dictionary();
                                webster.put("storytellerPlayer", storytellerId);
                                webster.put("storytellerName", storytellerName);
                                messageTo(guildMember, "handleStoryTellerInviteRequest", webster, 0, false);
                            }
                        }
                    }
                }
                else 
                {
                    sendSystemMessage(self, SID_INVITE_NOT_IN_A_GUILD);
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                target = getPlayerIdFromFirstName(invitee);
            }
        }
        if (!isIdValid(target))
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_INVITE_TARGET_INVALID);
                return SCRIPT_CONTINUE;
            }
            else if (!isPlayer(target))
            {
                prose_package pp = prose.getPackage(SID_INVITE_NOT_A_PLAYER, self, self);
                prose.setTO(pp, getEncodedName(target));
                sendSystemMessageProse(self, pp);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", storytellerId);
        webster.put("storytellerName", storytellerName);
        if (isIdValid(storytellerAssistant))
        {
            webster.put("storytellerAssistant", storytellerAssistant);
        }
        messageTo(target, "handleStoryTellerInviteRequest", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerInviteDeclined(obj_id self, dictionary params) throws InterruptedException
    {
        String targetName = params.getString("targetName");
        prose_package pp = prose.getPackage(SID_DECLINED_INVITE, self, self);
        prose.setTO(pp, targetName);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerInviteAlreadyInAStory(obj_id self, dictionary params) throws InterruptedException
    {
        boolean alreadyInStorytellersStory = params.getBoolean("alreadyInStorytellersStory");
        String targetName = params.getString("targetName");
        String storytellerName = params.getString("storytellerName");
        prose_package pp = prose.getPackage(SID_ALREADY_STORY, self, self);
        prose.setTO(pp, targetName);
        if (alreadyInStorytellersStory)
        {
            pp = prose.getPackage(SID_ALREADY_YOUR_STORY, self, self);
            prose.setTO(pp, targetName);
            if (utils.hasScriptVar(self, "storytellerAssistant"))
            {
                pp = prose.getPackage(SID_ASSISTANT_ALREADY_STORY, self, self);
                prose.setTO(pp, targetName);
                prose.setTT(pp, storytellerName);
            }
        }
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int storyInviteHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id storytellerId = self;
        String storytellerName = getName(self);
        obj_id storytellerAssistant = null;
        if (utils.hasScriptVar(self, "storytellerAssistant"))
        {
            storytellerId = utils.getObjIdScriptVar(self, "storytellerAssistant");
            storytellerName = utils.getStringScriptVar(self, "storytellerAssistantName");
            storytellerAssistant = self;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", storytellerId);
        webster.put("storytellerName", storytellerName);
        if (btn == sui.BP_CANCEL)
        {
            messageTo(player, "handleTellStorytellerIDeclinedInvite", webster, 0, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(storytellerAssistant))
        {
            webster.put("storytellerAssistant", storytellerAssistant);
        }
        messageTo(player, "handleStoryTellerInviteAccepted", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerPlayerHasBeenAdded(obj_id self, dictionary params) throws InterruptedException
    {
        String addedPlayerName = params.getString("addedPlayerName");
        String storytellerName = params.getString("storytellerName");
        if (addedPlayerName != null && addedPlayerName.length() > 0)
        {
            prose_package pp = prose.getPackage(SID_ADDED_PLAYER, self, self);
            prose.setTO(pp, addedPlayerName);
            if (utils.hasScriptVar(self, "storytellerAssistant"))
            {
                pp = prose.getPackage(SID_ASSISTANT_ADDED_PLAYER, self, self);
                prose.setTO(pp, addedPlayerName);
                prose.setTT(pp, storytellerName);
            }
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int leaveStory(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id storytellerPlayer = obj_id.NULL_ID;
        if (utils.hasScriptVar(self, "storytellerid"))
        {
            storytellerPlayer = utils.getObjIdScriptVar(self, "storytellerid");
            utils.removeScriptVar(self, "storytellerid");
            utils.removeScriptVar(self, "storytellerName");
            sendSystemMessage(self, SID_YOU_LEFT_STORY);
            if (isIdValid(storytellerPlayer))
            {
                storyteller.stopStorytellerEffectsInAreaToPlayer(self, storytellerPlayer);
            }
        }
        else 
        {
            sendSystemMessage(self, SID_YOU_NOT_IN_A_STORY);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeFromStory(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        target = null;
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            String invitee = (st.nextToken()).toLowerCase();
            if (invitee.equals("myself") || invitee.equals("self"))
            {
                target = self;
            }
            else 
            {
                target = getPlayerIdFromFirstName(invitee);
            }
        }
        if (!isIdValid(target))
        {
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_REMOVE_TARGET_INVALID);
                return SCRIPT_CONTINUE;
            }
            else if (!isPlayer(target))
            {
                sendSystemMessage(self, SID_REMOVE_NOT_A_PLAYER);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary webster = new dictionary();
        webster.put("storytellerPlayer", self);
        webster.put("storytellerName", getName(self));
        messageTo(target, "handleStoryTellerRemovedFromStory", webster, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerRemovePlayerNotInStory(obj_id self, dictionary params) throws InterruptedException
    {
        String targetName = params.getString("targetName");
        prose_package pp = prose.getPackage(SID_NOT_STORYTELLER_STORY, self, self);
        prose.setTO(pp, targetName);
        sendSystemMessageProse(self, pp);
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerPlayerHasBeenRemoved(obj_id self, dictionary params) throws InterruptedException
    {
        String removedPlayerName = params.getString("removedPlayerName");
        if (removedPlayerName != null && removedPlayerName.length() > 0)
        {
            prose_package pp = prose.getPackage(SID_REMOVED_PLAYER, self, self);
            prose.setTO(pp, removedPlayerName);
            sendSystemMessageProse(self, pp);
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerDestroyNpcs(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getDestroyStorytellerObjectParams(params);
        obj_id storytellerId = obj_id.NULL_ID;
        if (webster.containsKey("storytellerId"))
        {
            storytellerId = webster.getObjId("storytellerId");
        }
        String storytellerName = webster.getString("storytellerName");
        if (storytellerName == null || storytellerName.length() < 1)
        {
            sendSystemMessage(self, "GodMode Message: -> Syntax: /csStorytellerDestroyNpcs <storyteller_name> (use all_storytellers for all inclusive storyteller NPC destruction)", "");
        }
        if (!isIdValid(storytellerId))
        {
            sendSystemMessage(self, "GodMode Message: Unable to find a valid storyteller Id.", "");
        }
        boolean npcDestroyed = false;
        Vector elligibleStorytellerObjects = getElligibleStorytellerObjectsInRange(self, storytellerId, storytellerName);
        if (elligibleStorytellerObjects != null && elligibleStorytellerObjects.size() > 0)
        {
            for (int i = 0; i < elligibleStorytellerObjects.size(); i++)
            {
                obj_id elligibleStorytellerObject = ((obj_id)elligibleStorytellerObjects.get(i));
                if (isIdValid(elligibleStorytellerObject))
                {
                    if (isMob(elligibleStorytellerObject))
                    {
                        destroyObject(elligibleStorytellerObject);
                        npcDestroyed = true;
                    }
                }
            }
            if (npcDestroyed)
            {
                sendSystemMessage(self, "GodMode Message: All of " + storytellerName + "'s storyteller NPCs destroyed!", "");
                String logMsg = "(" + self + ")" + getName(self) + " has removed storyteller NPCs belonging to: " + storytellerName + "(" + storytellerId + ")";
                CustomerServiceLog("storyteller", logMsg);
            }
            else 
            {
                sendSystemMessage(self, "GodMode Message: No storyteller NPCs were found belonging to " + storytellerName + ".", "");
            }
        }
        else 
        {
            sendSystemMessage(self, "GodMode Message: No storyteller objects were found that belong to " + storytellerName + ".", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerDestroyProps(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getDestroyStorytellerObjectParams(params);
        obj_id storytellerId = obj_id.NULL_ID;
        if (webster.containsKey("storytellerId"))
        {
            storytellerId = webster.getObjId("storytellerId");
        }
        String storytellerName = webster.getString("storytellerName");
        if (storytellerName == null || storytellerName.length() < 1)
        {
            sendSystemMessage(self, "GodMode Message: -> Syntax: /csStorytellerDestroyProps <storyteller_name> (use all_storytellers for all inclusive storyteller prop destruction)", "");
        }
        if (!isIdValid(storytellerId))
        {
            sendSystemMessage(self, "GodMode Message: Unable to find a valid storyteller Id.", "");
        }
        boolean propDestroyed = false;
        Vector elligibleStorytellerObjects = getElligibleStorytellerObjectsInRange(self, storytellerId, storytellerName);
        if (elligibleStorytellerObjects != null && elligibleStorytellerObjects.size() > 0)
        {
            for (int i = 0; i < elligibleStorytellerObjects.size(); i++)
            {
                obj_id elligibleStorytellerObject = ((obj_id)elligibleStorytellerObjects.get(i));
                if (isIdValid(elligibleStorytellerObject))
                {
                    if (!isMob(elligibleStorytellerObject))
                    {
                        destroyObject(elligibleStorytellerObject);
                        propDestroyed = true;
                    }
                }
            }
            if (propDestroyed)
            {
                sendSystemMessage(self, "GodMode Message: All of " + storytellerName + "'s storyteller props destroyed!", "");
                String logMsg = "(" + self + ")" + getName(self) + " has removed storyteller props belonging to: " + storytellerName + "(" + storytellerId + ")";
                CustomerServiceLog("storyteller", logMsg);
            }
            else 
            {
                sendSystemMessage(self, "GodMode Message: No storyteller PROPS were found belonging to " + storytellerName + ".", "");
            }
        }
        else 
        {
            sendSystemMessage(self, "GodMode Message: No storyteller objects were found that belong to " + storytellerName + ".", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerDestroyParticles(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getDestroyStorytellerObjectParams(params);
        obj_id storytellerId = obj_id.NULL_ID;
        if (webster.containsKey("storytellerId"))
        {
            storytellerId = webster.getObjId("storytellerId");
        }
        String storytellerName = webster.getString("storytellerName");
        if (storytellerName == null || storytellerName.length() < 1)
        {
            sendSystemMessage(self, "GodMode Message: -> Syntax: /csStorytellerDestroyParticles <storyteller_name> (use all_storytellers for all inclusive storyteller particle destruction)", "");
        }
        if (!isIdValid(storytellerId))
        {
            sendSystemMessage(self, "GodMode Message: Unable to find a valid storyteller Id.", "");
        }
        boolean particleDestroyed = false;
        Vector elligibleStorytellerObjects = getElligibleStorytellerObjectsInRange(self, storytellerId, storytellerName);
        if (elligibleStorytellerObjects != null && elligibleStorytellerObjects.size() > 0)
        {
            for (int i = 0; i < elligibleStorytellerObjects.size(); i++)
            {
                obj_id elligibleStorytellerObject = ((obj_id)elligibleStorytellerObjects.get(i));
                if (isIdValid(elligibleStorytellerObject))
                {
                    if (hasObjVar(elligibleStorytellerObject, "storytellerPersistedEffectActive"))
                    {
                        messageTo(elligibleStorytellerObject, "handleRemoveStorytellerPersistedEffect", null, 1, false);
                        particleDestroyed = true;
                    }
                }
            }
            if (particleDestroyed)
            {
                sendSystemMessage(self, "GodMode Message: All of " + storytellerName + "'s storyteller particles destroyed!", "");
                String logMsg = "(" + self + ")" + getName(self) + " has removed storyteller particles belonging to: " + storytellerName + "(" + storytellerId + ")";
                CustomerServiceLog("storyteller", logMsg);
            }
            else 
            {
                sendSystemMessage(self, "GodMode Message: No storyteller PARTICLES were found belonging to " + storytellerName + ".", "");
            }
        }
        else 
        {
            sendSystemMessage(self, "GodMode Message: No storyteller objects were found that belong to " + storytellerName + ".", "");
        }
        return SCRIPT_CONTINUE;
    }
    public int storytellerDestroyAllObjects(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = getDestroyStorytellerObjectParams(params);
        obj_id storytellerId = obj_id.NULL_ID;
        if (webster.containsKey("storytellerId"))
        {
            storytellerId = webster.getObjId("storytellerId");
        }
        String storytellerName = webster.getString("storytellerName");
        if (storytellerName == null || storytellerName.length() < 1)
        {
            sendSystemMessage(self, "GodMode Message: -> Syntax: /csStorytellerDestroyAllObjects <storyteller_name> (use all_storytellers for all inclusive storyteller object destruction)", "");
        }
        if (!isIdValid(storytellerId))
        {
            sendSystemMessage(self, "GodMode Message: Unable to find a valid storyteller Id.", "");
        }
        Vector elligibleStorytellerObjects = getElligibleStorytellerObjectsInRange(self, storytellerId, storytellerName);
        if (elligibleStorytellerObjects != null && elligibleStorytellerObjects.size() > 0)
        {
            for (int i = 0; i < elligibleStorytellerObjects.size(); i++)
            {
                obj_id elligibleStorytellerObject = ((obj_id)elligibleStorytellerObjects.get(i));
                if (isIdValid(elligibleStorytellerObject))
                {
                    destroyObject(elligibleStorytellerObject);
                }
            }
            sendSystemMessage(self, "GodMode Message: All of " + storytellerName + "'s storyteller objects destroyed!", "");
            String logMsg = "(" + self + ")" + getName(self) + " has removed storyteller objects belonging to: " + storytellerName + "(" + storytellerId + ")";
            CustomerServiceLog("storyteller", logMsg);
        }
        else 
        {
            sendSystemMessage(self, "GodMode Message: No storyteller objects were found belonging to " + storytellerName + ".", "");
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary getDestroyStorytellerObjectParams(String params) throws InterruptedException
    {
        dictionary webster = new dictionary();
        obj_id storytellerId = obj_id.NULL_ID;
        String storytellerName = "";
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            storytellerName = (st.nextToken()).toLowerCase();
            if (st.hasMoreTokens())
            {
                storytellerName = storytellerName + " " + (st.nextToken()).toLowerCase();
            }
            if (storytellerName != null && storytellerName.length() > 0 && !storytellerName.equals(INCLUDE_ALL_STORYTELLERS))
            {
                storytellerId = getPlayerIdFromFirstName(storytellerName);
            }
        }
        if (isValidId(storytellerId))
        {
            webster.put("storytellerId", storytellerId);
        }
        webster.put("storytellerName", storytellerName);
        return webster;
    }
    public Vector getElligibleStorytellerObjectsInRange(obj_id self, obj_id storytellerId, String storytellerName) throws InterruptedException
    {
        Vector elligibleObjects = new Vector();
        elligibleObjects.setSize(0);
        obj_id[] storytellerObjects = getAllObjectsWithObjVar(getLocation(self), 250f, "storytellerid");
        if (storytellerObjects != null && storytellerObjects.length > 0)
        {
            for (int i = 0; i < storytellerObjects.length; i++)
            {
                obj_id storytellerObject = storytellerObjects[i];
                if (isIdValid(storytellerObject))
                {
                    if (storytellerName.equals(INCLUDE_ALL_STORYTELLERS))
                    {
                        utils.addElement(elligibleObjects, storytellerObject);
                    }
                    else 
                    {
                        obj_id storytellerObjectOwner = getObjIdObjVar(storytellerObject, "storytellerid");
                        String storytellerObjectOwnerName = getStringObjVar(storytellerObject, "storytellerName");
                        if (isIdValid(storytellerId))
                        {
                            if (isIdValid(storytellerObjectOwner) && storytellerId == storytellerObjectOwner)
                            {
                                utils.addElement(elligibleObjects, storytellerObject);
                            }
                        }
                        else 
                        {
                            if (storytellerObjectOwnerName.length() > 0 && storytellerName.equals(storytellerObjectOwnerName.toLowerCase()))
                            {
                                utils.addElement(elligibleObjects, storytellerObject);
                            }
                        }
                    }
                }
            }
        }
        return elligibleObjects;
    }
    public int chroniclerVentriloquism(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        target = getIntendedTarget(self);
        if (!isIdValid(target))
        {
            target = getLookAtTarget(self);
        }
        if (isIdValid(target))
        {
            if (isMob(target) && storyteller.isStorytellerNpc(target))
            {
                if (storyteller.allowedToUseStorytellerObject(target, self))
                {
                    chat.chat(target, pgc_quests.useFilteredQuestText(params));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int chroniclerGetRating(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(params);
        if (st.countTokens() > 0)
        {
            String name = (st.nextToken()).toLowerCase();
            if (name.equals("myself") || name.equals("self"))
            {
                obj_id[] chroniclers = 
                {
                    self
                };
                String firstName = getFirstName(self);
                pgc_quests.showChroniclerRatingTable(self, firstName, chroniclers);
            }
            else 
            {
                obj_id[] chroniclers = pgcGetChroniclerId(name);
                pgc_quests.showChroniclerRatingTable(self, name, chroniclers);
            }
        }
        else 
        {
            target = null;
            obj_id intendedTarget = getIntendedTarget(self);
            if (!isIdValid(intendedTarget))
            {
                target = getLookAtTarget(self);
            }
            else 
            {
                target = intendedTarget;
            }
            if (!isIdValid(target))
            {
                sendSystemMessage(self, SID_PGC_RATING_TARGET_INVALID);
                return SCRIPT_CONTINUE;
            }
            else if (!isPlayer(target))
            {
                sendSystemMessage(self, SID_PGC_RATING_TARGET_NOT_A_PLAYER);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id[] chroniclers = 
                {
                    target
                };
                String firstName = getFirstName(target);
                pgc_quests.showChroniclerRatingTable(self, firstName, chroniclers);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int chroniclerGetStoredXp(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (sui.hasPid(self, "commandPid_chroniclerGetStoredXp"))
        {
            int oldPid = sui.getPid(self, "commandPid_chroniclerGetStoredXp");
            forceCloseSUIPage(oldPid);
        }
        int storedXp = 0;
        int storedSilverTokens = 0;
        int storedGoldTokens = 0;
        int[] pgcRatingData = pgcGetRatingData(self);
        if (pgcRatingData != null && pgcRatingData.length > 0)
        {
            storedXp = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX];
            storedXp = pgc_quests.getConfigModifiedChroniclesXPAmount(storedXp);
            storedSilverTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX];
            storedGoldTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX];
        }
        String storedXp_str = "" + storedXp;
        String storedSilverTokens_str = "" + storedSilverTokens;
        String storedGoldTokens_str = "" + storedGoldTokens;
        String[] templateSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (templateSkills != null && templateSkills.length > 0)
        {
            String finalChronicleSkill = templateSkills[templateSkills.length - 1];
            if (hasSkill(self, finalChronicleSkill))
            {
                storedXp_str = "Chronicle Master";
            }
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, self, "handleChroniclerGetStoredXp");
        String title = utils.packStringId(new string_id("saga_system", "chronicles_stored_data_title"));
        String prompt = utils.packStringId(new string_id("saga_system", "chronicles_stored_data_prompt")) + target_dummy.addLineBreaks(2);
        prompt += target_dummy.GREEN + utils.packStringId(new string_id("saga_system", "chronicles_stored_xp")) + target_dummy.WHITE + storedXp_str + target_dummy.addLineBreaks(1);
        prompt += target_dummy.GREEN + utils.packStringId(new string_id("saga_system", "chronicles_stored_silver_tokens")) + target_dummy.WHITE + storedSilverTokens_str + target_dummy.addLineBreaks(1);
        if (storedGoldTokens > 0)
        {
            prompt += target_dummy.GREEN + utils.packStringId(new string_id("saga_system", "chronicles_stored_gold_tokens")) + target_dummy.WHITE + storedGoldTokens_str + target_dummy.addLineBreaks(1);
        }
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, prompt);
        String OK_BUTTON = utils.packStringId(new string_id("saga_system", "chronicles_stored_data_claim"));
        String CANCEL_BUTTON = utils.packStringId(new string_id("saga_system", "chronicles_stored_data_cancel"));
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
        sui.showSUIPage(pid);
        sui.setPid(self, pid, "commandPid_chroniclerGetStoredXp");
        return SCRIPT_CONTINUE;
    }
    public int handleChroniclerGetStoredXp(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, "commandPid_chroniclerGetStoredXp"))
        {
            return SCRIPT_CONTINUE;
        }
        int playerPid = sui.getPid(player, "commandPid_chroniclerGetStoredXp");
        int suiPid = params.getInt("pageId");
        if (playerPid != suiPid)
        {
            sui.removePid(player, "commandPid_chroniclerGetStoredXp");
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, "commandPid_chroniclerGetStoredXp");
            return SCRIPT_CONTINUE;
        }
        String playerName = getName(player);
        int storedXp = 0;
        int storedSilverTokens = 0;
        int storedGoldTokens = 0;
        int[] pgcRatingData = pgcGetRatingData(player);
        if (pgcRatingData != null && pgcRatingData.length > 0)
        {
            storedXp = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX];
            storedSilverTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX];
            storedGoldTokens = pgcRatingData[pgc_quests.PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX];
        }
        boolean doneWithChronicleXP = false;
        String[] templateSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (templateSkills != null && templateSkills.length > 0)
        {
            String finalChronicleSkill = templateSkills[templateSkills.length - 1];
            if (hasSkill(self, finalChronicleSkill))
            {
                if (storedXp > 0)
                {
                    pgcAdjustRatingData(player, playerName, pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX, storedXp * -1);
                }
                storedXp = 0;
                doneWithChronicleXP = true;
            }
        }
        if (storedXp > 0)
        {
            pgc_quests.grantChronicleXp(player, storedXp);
            pgcAdjustRatingData(player, playerName, pgc_quests.PGC_STORED_CHRONICLE_XP_INDEX, storedXp * -1);
            pgc_quests.logProgression(player, null, "Reserved Chronicle XP claimed = " + storedXp);
        }
        else 
        {
            if (!doneWithChronicleXP)
            {
                sendSystemMessage(self, new string_id("saga_system", "chronicles_stored_data_no_xp"));
            }
        }
        if (storedSilverTokens > 0)
        {
            pgc_quests.grantSilverChroniclesRewardTokens(player, storedSilverTokens);
            pgcAdjustRatingData(player, playerName, pgc_quests.PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX, storedSilverTokens * -1);
            pgc_quests.logProgression(player, null, "Reserved Silver Chronicles Tokens claimed = " + storedSilverTokens);
        }
        if (storedGoldTokens > 0)
        {
            pgc_quests.grantGoldChroniclesRewardTokens(player, storedGoldTokens);
            pgcAdjustRatingData(player, playerName, pgc_quests.PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX, storedGoldTokens * -1);
            pgc_quests.logProgression(player, null, "Reserved Gold Chronicles Tokens claimed = " + storedGoldTokens);
        }
        if (storedSilverTokens <= 0 && storedGoldTokens <= 0)
        {
            sendSystemMessage(self, new string_id("saga_system", "chronicles_stored_data_no_tokens"));
        }
        return SCRIPT_CONTINUE;
    }
    public int chroniclesLootToggle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (sui.hasPid(self, "chroniclesLootToggle"))
        {
            int pid = sui.getPid(self, "chroniclesLootToggle");
            forceCloseSUIPage(pid);
            sui.removePid(self, "chroniclesLootToggle");
        }
        String okButton = "Disable Loot";
        String cancelButton = "Cancel";
        String currentStatus = "Enabled";
        if (loot.hasToggledChroniclesLootOff(self))
        {
            okButton = "Enable Loot";
            currentStatus = "Disabled";
        }
        String title = "Chronicles Loot Toggle";
        String textMsg = "Current Chronicles Loot Status: " + currentStatus;
        textMsg += target_dummy.addLineBreaks(2);
        textMsg += "Would you like to " + okButton + "?";
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, self, "handleChroniclesLootToggleConfirmation");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, textMsg);
        sui.msgboxButtonSetup(pid, sui.YES_NO);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, okButton);
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, cancelButton);
        sui.showSUIPage(pid);
        sui.setPid(self, pid, "chroniclesLootToggle");
        return SCRIPT_CONTINUE;
    }
    public int handleChroniclesLootToggleConfirmation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(self, "chroniclesLootToggle"))
        {
            return SCRIPT_CONTINUE;
        }
        sui.removePid(self, "chroniclesLootToggle");
        String message = "";
        if (loot.hasToggledChroniclesLootOff(self))
        {
            loot.enableChroniclesLoot(self);
            message = "chronicles_loot_back_on";
        }
        else 
        {
            loot.disableChroniclesLoot(self);
            message = "chronicles_loot_now_off";
        }
        if (message.length() > 0)
        {
            sendSystemMessage(self, new string_id("saga_system", message));
        }
        return SCRIPT_CONTINUE;
    }
}
