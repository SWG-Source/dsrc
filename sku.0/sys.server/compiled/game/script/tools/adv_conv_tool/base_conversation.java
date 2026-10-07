package script.tools.adv_conv_tool;

import script.obj_id;
import script.string_id;

/**
 * Core interface contract for scripted NPC conversations across the server.
 * <p>
 * Any script handling interactive dialogue trees implements this interface to hook into the SWG engine's
 * conversation lifecycle. It standardizes how radial menus check talk eligibility, kick off dialogue windows,
 * navigate branching responses, and tear down active chat sessions cleanly.
 * </p>
 * <p>
 * Standard conversation lifecycle:
 * <ol>
 *   <li>{@link #initConversation(obj_id, obj_id)}: Wires up conversation IDs and prepares session state before radial displays.</li>
 *   <li>{@link #canPlayerConverse(obj_id, obj_id)}: Gates the radial talk menu option; if false, the option never shows up.</li>
 *   <li>{@link #doStartConversation(obj_id, obj_id)}: Fires when the player clicks talk; turns the NPC to face them and serves the opening dialogue.</li>
 *   <li>{@link #handleNpcConversationResponse(obj_id, obj_id, String, string_id)}: Catches player response clicks, executes game actions, and moves to the next branch.</li>
 *   <li>{@link #doEndConversation(obj_id, obj_id)}: Cleans up conversation state and delivers final closing dialogue or animations.</li>
 * </ol>
 * </p>
 */
public interface base_conversation {

    /**
     * Initializes conversation identifiers and session state before dialogue kicks off.
     * <p>
     * Subclasses use this hook to set their conversation identifier strings (like {@code COMM_CONVO_ID})
     * and branch scriptvar keys so the engine knows which dialogue tree is running.
     * </p>
     *
     * @param npc    the NPC or conversational object
     * @param player the player attempting to interact with the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    void initConversation(obj_id npc, obj_id player) throws InterruptedException;

    /**
     * Checks if the player is allowed to initiate a conversation with this NPC.
     * <p>
     * Controls whether the "Converse" radial option appears in the player's radial menu.
     * Use this to check combat status, required quest flags, faction standing, or level gates.
     * </p>
     *
     * @param self   the NPC object running the conversation script
     * @param player the player opening the radial menu
     * @return true if the player meets all conditions to converse, false to suppress the converse menu option
     * @throws InterruptedException if engine thread execution is interrupted
     */
    boolean canPlayerConverse(obj_id self, obj_id player) throws InterruptedException;

    /**
     * Launches the conversation when the player selects the converse option from the radial menu.
     * <p>
     * Responsible for aiming the NPC at the player, clearing stale branch scriptvars, and sending
     * the opening dialogue prompt along with initial response options via {@code npcStartConversation}.
     * </p>
     *
     * @param self   the NPC starting the conversation
     * @param player the player who opened the conversation
     * @throws InterruptedException if engine thread execution is interrupted
     */
    void doStartConversation(obj_id self, obj_id player) throws InterruptedException;

    /**
     * Processes a dialogue response option selected by the player.
     * <p>
     * Evaluates the selected response against the active branch, executes any associated game logic
     * (such as advancing quest tasks, handing out items, or triggering animations), and either shifts
     * to the next dialogue branch or terminates the conversation.
     * </p>
     *
     * @param self           the NPC handling the response
     * @param player         the player who clicked the response
     * @param conversationId the active conversation identifier string
     * @param response       the string_id representing the dialogue choice picked by the player
     * @return {@link script.base_class#SCRIPT_CONTINUE} if handled cleanly, or {@link script.base_class#SCRIPT_DEFAULT} / {@link script.base_class#SCRIPT_OVERRIDE} on failure
     * @throws InterruptedException if engine thread execution is interrupted
     */
    int handleNpcConversationResponse(obj_id self, obj_id player, String conversationId, string_id response) throws InterruptedException;

    /**
     * Tears down an active conversation session and displays fallback or closing dialogue.
     * <p>
     * Fired when dialogue branches run out, when the player bails out, or when a script explicitly
     * wraps up the conversation. Wipes active conversation scriptvars and plays closing animations.
     * </p>
     *
     * @param self   the NPC wrapping up the conversation
     * @param player the player ending the interaction
     * @throws InterruptedException if engine thread execution is interrupted
     */
    void doEndConversation(obj_id self, obj_id player) throws InterruptedException;
}
