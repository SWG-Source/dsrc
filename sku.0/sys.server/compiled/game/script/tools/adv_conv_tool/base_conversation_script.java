package script.tools.adv_conv_tool;

import script.*;
import script.library.ai_lib;
import script.library.chat;
import script.library.utils;

import static script.tools.adv_conv_tool.adv_conv_utils.getRandomAnimationString;

/**
 * Base conversation script bridging core SWG engine triggers to the {@link base_conversation} interface.
 * <p>
 * This class hooks directly into native server triggers like {@code OnObjectMenuRequest}, {@code OnStartNpcConversation},
 * and {@code OnNpcConversationResponse}. It manages the NPC's {@code CONDITION_CONVERSABLE} flag across spawns,
 * incapacitations, and server restarts, sparing subclasses from dealing with low-level engine plumbing.
 * </p>
 * <p>
 * Subclasses override {@link #initConversation(obj_id, obj_id)}, {@link #canPlayerConverse(obj_id, obj_id)},
 * {@link #doStartConversation(obj_id, obj_id)}, and {@link #handleNpcConversationResponse(obj_id, obj_id, String, string_id)}
 * to build custom dialogue trees.
 * </p>
 *
 * @see base_conversation
 */
public class base_conversation_script extends base_script implements base_conversation {

    /**
     * Scriptvar name on the player used to track their current branch ID during an active conversation.
     * Subclasses set this in {@link #initConversation(obj_id, obj_id)} to avoid branch collisions between different NPCs.
     */
    protected static String COMM_CONVO_BRANCH_ID = "";

    /**
     * Unique conversation identifier string passed into the engine's conversation networking calls.
     * Subclasses set this in {@link #initConversation(obj_id, obj_id)} to identify the conversation session.
     */
    protected static String COMM_CONVO_ID = "";

    /**
     * Toggles diagnostic warning logs across conversation lifecycle events.
     */
    protected static final boolean DEBUG_MODE = false;

    /**
     * Initializes conversation state, branch scriptvar keys, and conversation IDs before dialogue begins.
     * <p>
     * Fired right before the radial menu is displayed or dialogue starts. Subclasses should set
     * {@link #COMM_CONVO_BRANCH_ID} and {@link #COMM_CONVO_ID} here.
     * </p>
     *
     * @param npc    the NPC running the conversation
     * @param player the player interacting with the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public void initConversation(obj_id npc, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "base_conversation_script.initConversation() has not been implemented...");

        // THIS CODE IS A TEMPLATE FOR INITIALIZING THE CONVERSATION...
//        COMM_CONVO_BRANCH_ID = "bmcstudios.util.base_conversation_script.branchID";
//        COMM_CONVO_ID = "base_conversation_script";
    }

    /**
     * Checks if the player meets all requirements to talk to this NPC.
     * <p>
     * When this returns {@code true}, the converse option appears in the player's radial menu.
     * If {@code false}, the option stays hidden. Override this in subclasses to gate dialogue
     * behind quest flags, combat states, faction affiliations, or level checks.
     * </p>
     *
     * @param self   the NPC object running this script
     * @param player the player opening the radial menu
     * @return true if the converse menu option should appear, false otherwise
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public boolean canPlayerConverse(obj_id self, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "base_conversation_script.canPlayerConverse() has not been implemented...");

        return true;
    }

    /**
     * Kicks off the conversation dialogue with the player.
     * <p>
     * Subclasses override this to face the NPC toward the player, clear old branch state,
     * set the initial branch scriptvar on the player, and call {@code npcStartConversation}
     * with the opening message and response choices.
     * </p>
     *
     * @param self   the NPC object
     * @param player the player talking to the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public void doStartConversation(obj_id self, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "base_conversation_script.doStartConversation() has not been implemented...");

        // THIS CODE IS A TEMPLATE FOR STARTING A CONVERSATION...
//        doAnimationAction(self, "wave_on_directing");
//        string_id npcMessage = new string_id(
//                "Are you " + getPlayerName(player) + "?"
//        );
//
//        doAnimationAction(player, "nod");
//        List<string_id> playerResponses = new ArrayList<>();
//        playerResponses.add(new string_id("Yes I am."));
//
//        utils.setScriptVar(player, COMM_CONVO_BRANCH_ID, 1);
//        npcStartConversation(player, self, COMM_CONVO_ID, npcMessage, playerResponses.toArray(string_id[]::new));
    }

    /**
     * Fallback dialogue handler when no more branches exist or the conversation finishes.
     * <p>
     * Plays a random ambient animation on the NPC and player, and chats a reminder message
     * to implement {@code doEndConversation} for this NPC's template.
     * </p>
     *
     * @param self   the NPC wrapping up dialogue
     * @param player the player ending the interaction
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public void doEndConversation(obj_id self, obj_id player) throws InterruptedException {
        doAnimationAction(self, getRandomAnimationString());
        string_id npcMessage = new string_id("IMPLEMENT THE doEndConversation() METHOD FOR THE " + getTemplateName(self) + " NPC.");
        chat.chat(self, npcMessage);

        doAnimationAction(player, getRandomAnimationString());
    }

    /**
     * Evaluates a player's selected dialogue response and drives the conversation forward.
     * <p>
     * Subclasses check the player's active branch scriptvar, match the chosen response string,
     * execute rewards or quest updates, and jump to subsequent branches or close the dialogue.
     * </p>
     *
     * @param self           the NPC handling the response
     * @param player         the player making the dialogue choice
     * @param conversationId the conversation identifier
     * @param response       the string_id representing the clicked option
     * @return {@link base_class#SCRIPT_CONTINUE} on success, or {@link base_class#SCRIPT_OVERRIDE} on failure
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public int handleNpcConversationResponse(obj_id self, obj_id player, String conversationId, string_id response) throws InterruptedException {
        WARNING(DEBUG_MODE, "base_conversation_script.handleNpcConversationResponse() has not been implemented...");

        // THIS CODE IS A TEMPLATE FOR HANDLING A CONVERSATION RESPONSE...
//        switch (utils.getIntScriptVar(player, COMM_CONVO_BRANCH_ID)) {
//            case 1:
//                return responseBranch1(player, npc, response);
//
//            default:
//                chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
//                utils.removeScriptVar(player, COMM_CONVO_BRANCH_ID);
//                return SCRIPT_CONTINUE;
//        }

        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the script is attached to an object.
     * <p>
     * Flags the NPC with {@link base_class#CONDITION_CONVERSABLE} so the client knows
     * it can be interacted with.
     * </p>
     *
     * @param self the object this script is being attached to
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnAttach(obj_id self) throws InterruptedException {
        setCondition(self, CONDITION_CONVERSABLE);

        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the script is detached from an object.
     *
     * @param self the object this script is being removed from
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnDetach(obj_id self) throws InterruptedException {
        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the object initializes in the game world (upon spawn or server restart).
     * <p>
     * Restores the {@link base_class#CONDITION_CONVERSABLE} flag on the NPC.
     * </p>
     *
     * @param self the initializing NPC object
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnInitialize(obj_id self) throws InterruptedException {
        setCondition(self, CONDITION_CONVERSABLE);

        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the NPC is knocked out or killed.
     * <p>
     * Clears the {@link base_class#CONDITION_CONVERSABLE} flag so players can't talk
     * to downed or dead NPCs.
     * </p>
     *
     * @param self   the NPC that got incapacitated
     * @param killer the killer object
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException {
        clearCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the NPC recovers from incapacitation.
     * <p>
     * Restores the {@link base_class#CONDITION_CONVERSABLE} condition so players can interact again.
     * </p>
     *
     * @param self the recapacitated NPC
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnRecapacitated(obj_id self) throws InterruptedException {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when a player requests the radial menu on this object.
     * <p>
     * Re-applies {@link base_class#CONDITION_CONVERSABLE}, calls {@link #initConversation(obj_id, obj_id)}
     * to prep state, and injects the {@code CONVERSE_START} menu option if {@link #canPlayerConverse(obj_id, obj_id)}
     * checks out.
     * </p>
     *
     * @param self     the NPC being radialed
     * @param player   the player opening the radial menu
     * @param menuInfo the menu builder structure being populated
     * @return {@link base_class#SCRIPT_CONTINUE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException {
        setCondition(self, CONDITION_CONVERSABLE);

        // initialize the conversation before we display it.
        // requires npc and player object data so we must do it here while we're requesting the menu
        initConversation(self, player);

        if (canPlayerConverse(self, player)) {
            int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
            menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
            if (menuInfoData != null) {
                menuInfoData.setServerNotify(false);
            }
        }

        return SCRIPT_CONTINUE;
    }

    /**
     * Trigger fired when the player picks the converse radial option.
     * <p>
     * Verifies neither participant is in combat (bailing with {@link base_class#SCRIPT_OVERRIDE} if they are),
     * and calls {@link #doStartConversation(obj_id, obj_id)} to kick off the dialogue.
     * </p>
     *
     * @param npc    the NPC object
     * @param player the player initiating the conversation
     * @return {@link base_class#SCRIPT_CONTINUE} if conversation started, or {@link base_class#SCRIPT_OVERRIDE} if in combat
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnStartNpcConversation(obj_id npc, obj_id player) throws InterruptedException {
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player)) {
            return SCRIPT_OVERRIDE;
        }

        doStartConversation(npc, player);

        return SCRIPT_CONTINUE;
    }

    /**
     * Engine trigger fired when the player selects a dialogue response in the conversation UI.
     * <p>
     * Validates that the incoming {@code conversationId} matches {@link #COMM_CONVO_ID}. If so, faces
     * the NPC toward the player and delegates handling to {@link #handleNpcConversationResponse(obj_id, obj_id, String, string_id)}.
     * </p>
     *
     * @param npc            the NPC object
     * @param conversationId the conversation identifier string sent by the client
     * @param player         the player who clicked a response
     * @param response       the string_id representing the clicked option
     * @return the result from {@link #handleNpcConversationResponse(obj_id, obj_id, String, string_id)}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    public int OnNpcConversationResponse(obj_id npc, String conversationId, obj_id player, string_id response) throws InterruptedException {
        // validate the correct conversation name is being requested
        if (!conversationId.equals(COMM_CONVO_ID)) {
            return SCRIPT_CONTINUE;
        }

        // make the npc face the player when speaking, but only if it's a valid NPC creature
        if (isNpcCreature(npc) && isMob(npc)) {
            base_class.faceTo(npc, player);
        }

        return handleNpcConversationResponse(npc, player, conversationId, response);
    }

    /**
     * Handles unhandled response fall-throughs or broken dialogue branches.
     * <p>
     * Spits an error bark into chat, wipes the player's conversation branch scriptvar,
     * and returns {@link base_class#SCRIPT_OVERRIDE} to abort the dialogue cleanly.
     * </p>
     *
     * @param npc    the NPC object
     * @param player the player whose conversation stalled
     * @return {@link base_class#SCRIPT_OVERRIDE}
     * @throws InterruptedException if engine thread execution is interrupted
     */
    protected int failedNpcConversationResponse(obj_id npc, obj_id player) throws InterruptedException {
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, COMM_CONVO_BRANCH_ID);
        return SCRIPT_OVERRIDE;
    }
}
