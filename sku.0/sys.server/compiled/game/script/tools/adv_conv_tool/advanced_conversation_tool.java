package script.tools.adv_conv_tool;

import script.base_class;
import script.library.groundquests;
import script.library.utils;
import script.obj_id;
import script.string_id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * Declarative conversation builder sitting on top of {@link base_conversation_script} to eliminate
 * boilerplate switch-cases and manual scriptvar management.
 * <p>
 * Instead of juggling massive switch blocks inside {@code OnNpcConversationResponse} and manually managing
 * scriptvars across player dialogue steps, this tool lets you assemble branching dialogue trees using a fluent,
 * builder-based API. You register entry points that test player state, chain dialogue branches with custom
 * messages and animations, attach inline action lambdas to player responses, and let the tool handle the
 * engine networking and state cleanup under the hood.
 * </p>
 *
 * <h3>Architecture & Execution Flow</h3>
 * <ol>
 *   <li><b>Entry Points:</b> When a player speaks to the NPC, the tool walks registered entry points in order.
 *       The first entry point whose condition lambda returns {@code true} selects the starting branch ID.</li>
 *   <li><b>Branch Construction:</b> Branches are registered via {@link #createBranch(int)}, returning a
 *       fluent {@link convo_branch_builder} to set the NPC's spoken text, trigger animations, and add player options.</li>
 *   <li><b>Response Dispatch & Action Hooks:</b> When the player selects a response, the tool verifies the match,
 *       executes any attached {@link BiConsumer} action callback (e.g., dispatching mission signals, giving items),
 *       and moves to the next branch ID or concludes the conversation.</li>
 *   <li><b>Automated State Management:</b> The active branch ID is tracked in a player scriptvar ({@link #COMM_CONVO_BRANCH_ID}).
 *       The script handles resetting this state on new conversations, navigating continuations via {@code npcSpeak} and
 *       {@code npcSetConversationResponses}, and tearing down state upon conversation exit.</li>
 * </ol>
 *
 * @see base_conversation_script
 */
public class advanced_conversation_tool extends base_conversation_script {

    /**
     * Map of registered conversation branches keyed by their branch ID.
     */
    private final Map<Integer, convo_branch> branchRegistry = new HashMap<>();

    /**
     * Priority-ordered list of conditional entry points checked when starting a conversation.
     */
    private final List<convo_entry_point> entryPoints = new ArrayList<>();

    /**
     * Hook to assemble the conversation tree, entry points, and branching logic.
     * <p>
     * Subclasses must override this to register entry points via {@link #addEntryPoint(BiPredicate, int)}
     * and define conversation branches using {@link #createBranch(int)}.
     * </p>
     *
     * @param npc    the NPC running the conversation
     * @param player the player interacting with the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    protected void initializeConversationTree(obj_id npc, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.initializeConversationTree() has not been implemented...");
    }

    /**
     * Prepares conversation state and rebuilds the tree for the interacting player.
     * <p>
     * Clears cached branches and entry points, calls the base script setup, and then invokes
     * {@link #initializeConversationTree(obj_id, obj_id)} so dynamic player state (active quests,
     * inventory items, badges) can be evaluated accurately.
     * </p>
     *
     * @param npc    the NPC running the conversation
     * @param player the player interacting with the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public void initConversation(obj_id npc, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.initConversation()");

        super.initConversation(npc, player);    // Sets up COMM strings

        branchRegistry.clear();
        entryPoints.clear();

        initializeConversationTree(npc, player);   // Builds the logic
    }

    /**
     * Checks if the player can talk to this NPC by testing registered entry point conditions.
     * <p>
     * Iterates through all registered entry points; if at least one condition evaluates to {@code true},
     * the player is permitted to converse and the radial menu option appears.
     * </p>
     *
     * @param npc    the NPC object running the conversation
     * @param player the player opening the radial menu
     * @return true if any entry point condition passes, false to hide the converse option
     */
    @Override
    public boolean canPlayerConverse(obj_id npc, obj_id player) {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.canPlayerConverse()");

        // Return true if ANY entry point condition is met
        for (convo_entry_point entry : entryPoints) {
            if (entry.condition.test(npc, player)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Starts the conversation session for the player.
     * <p>
     * Rebuilds the conversation tree, clears any stale branch scriptvars on the player,
     * turns the NPC to face the player, finds the first matching entry point in priority order,
     * and kicks off the corresponding branch via {@link #startBranch(obj_id, obj_id, int)}.
     * If no entry point conditions pass, it falls back to {@link #doEndConversation(obj_id, obj_id)}.
     * </p>
     *
     * @param npc    the NPC starting the conversation
     * @param player the player conversing with the NPC
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public void doStartConversation(obj_id npc, obj_id player) throws InterruptedException {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.doStartConversation()");

        // Ensure fresh initialization for the conversing player
        initConversation(npc, player);

        // Clear the state variable to ensure startBranch knows this is a NEW conversation
        utils.removeScriptVar(player, COMM_CONVO_BRANCH_ID);

        // make the npc face the player when speaking, but only if it's a valid NPC creature
        if (isNpcCreature(npc) && isMob(npc)) {
            base_class.faceTo(npc, player);
        }

        // Find the first matching entry point (Priority Order)
        for (convo_entry_point entry : entryPoints) {
            if (entry.condition.test(npc, player)) {
                startBranch(npc, player, entry.startBranchId);
                return;
            }
        }

        // Fallback if no conditions met
        doEndConversation(npc, player);
    }

    /**
     * Handles the player's chosen conversation response option.
     * <p>
     * Looks up the player's active branch in {@link #branchRegistry}, locates the matching response text,
     * executes any attached action lambda (such as mission progress signals, inventory rewards, or animations),
     * and transitions either to the next branch or ends the conversation with an exit message.
     * </p>
     *
     * @param npc            the NPC handling the response
     * @param player         the player who clicked the response option
     * @param conversationId the conversation identifier string
     * @param response       the string_id representing the clicked dialogue choice
     * @return {@link base_class#SCRIPT_CONTINUE} if matched and processed, or {@link base_class#SCRIPT_DEFAULT} if unhandled
     * @throws InterruptedException if engine thread execution is interrupted
     */
    @Override
    public int handleNpcConversationResponse(obj_id npc, obj_id player, String conversationId, string_id response) throws InterruptedException {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.handleNpcConversationResponse()");

        int currentBranchId = utils.getIntScriptVar(player, COMM_CONVO_BRANCH_ID);
        convo_branch currentBranch = branchRegistry.get(currentBranchId);

        if (currentBranch != null) {
            for (convo_response option : currentBranch.responses) {
                // Check if the response text matches
                if (option.responseText.equals(response)) {

                    // Run any attached action (Signals, etc.)
                    if (option.action != null) {
                        option.action.accept(npc, player);
                    }

                    // Transition
                    if (option.nextBranchId == -1) {
                        // End Conversation
                        npcEndConversationWithMessage(
                                player,
                                new string_id(option.endMessage != null ? option.endMessage : "Goodbye.")
                        );
                    } else {
                        // Move to the next branch
                        startBranch(npc, player, option.nextBranchId);
                    }

                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_DEFAULT;
    }

    /**
     * Starts or continues conversation playback for a specific branch ID.
     * <p>
     * Pulls the branch from {@link #branchRegistry}, updates the player's branch scriptvar,
     * plays any branch animation on the NPC, and delivers dialogue packets to the client.
     * If this is the initial exchange, {@code npcStartConversation} is used; otherwise,
     * {@code npcSpeak} and {@code npcSetConversationResponses} advance the existing session.
     * </p>
     *
     * @param npc      the NPC speaking
     * @param player   the player conversing
     * @param branchId the ID of the branch to display
     * @throws InterruptedException if engine thread execution is interrupted
     */
    protected void startBranch(obj_id npc, obj_id player, int branchId) throws InterruptedException {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.startBranch()");

        convo_branch branch = branchRegistry.get(branchId);
        if (branch == null) {
            doEndConversation(npc, player);
            return;
        }

        // Detect if this is the start (script var not set yet) or a continuation
        // We rely on doStartConversation clearing this var before calling startBranch
        boolean isInitial = !utils.hasScriptVar(player, COMM_CONVO_BRANCH_ID);

        // Update State
        utils.setScriptVar(player, COMM_CONVO_BRANCH_ID, branchId);

        // Perform Animation
        if (branch.npcAnimation != null) {
            doAnimationAction(npc, branch.npcAnimation);
        }

        // Prepare Responses
        List<string_id> responseIds = new ArrayList<>();
        for (convo_response resp : branch.responses) {
            responseIds.add(resp.responseText);
        }

        // Send it to Engine
        if (isInitial) {
            // Initial Start: Must use npcStartConversation
            npcStartConversation(
                    player,
                    npc,
                    COMM_CONVO_ID,
                    branch.npcMessage,
                    responseIds.toArray(new string_id[0])
            );
        } else {
            // Continuation: Use npcSpeak/SetResponses
            npcSpeak(player, branch.npcMessage);
            npcSetConversationResponses(player, responseIds.toArray(new string_id[0]));
        }
    }

    /**
     * Registers a conditional entry point tested when the player talks to the NPC.
     * <p>
     * Entry points are checked in the order they are registered. The first condition
     * returning {@code true} sets the initial branch ID.
     * </p>
     *
     * @param condition     lambda predicate taking {@code (npc, player)} and returning true if eligible
     * @param startBranchId the branch ID to jump to if this condition succeeds
     */
    protected void addEntryPoint(BiPredicate<obj_id, obj_id> condition, int startBranchId) {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.addEntryPoint()");

        entryPoints.add(new convo_entry_point(condition, startBranchId));
    }

    /**
     * Convenience helper to register an entry point gated on an active ground quest task.
     * <p>
     * Evaluates whether the given quest and task are currently active on the player.
     * If active, routes into {@code startBranchId}.
     * </p>
     *
     * @param questName     the name of the ground quest
     * @param taskName      the target task name within the quest
     * @param startBranchId the branch ID to route to if the task is active
     */
    protected void addQuestEntryPoint(String questName, String taskName, int startBranchId) {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.addQuestEntryPoint()");

        addEntryPoint((npc, player) -> {
            try {
                int qId = groundquests.getQuestIdFromString(questName);
                int tId = groundquests.getTaskId(qId, taskName);
                return groundquests.questIsTaskActive(qId, tId, player);
            } catch (InterruptedException e) {
                return false;
            }
        }, startBranchId);
    }

    /**
     * Creates and registers a new dialogue branch, returning a fluent builder to populate it.
     *
     * @param id the unique branch ID within this conversation tree
     * @return a {@link convo_branch_builder} instance configured for this branch
     */
    protected convo_branch_builder createBranch(int id) {
        WARNING(DEBUG_MODE, "advanced_conversation_tool.addQuestEntryPoint()");

        convo_branch branch = new convo_branch(id);
        branchRegistry.put(id, branch);
        return new convo_branch_builder(branch);
    }

    /**
     * Fluent builder for setting up dialogue lines, animations, responses, and transitions on a branch.
     */
    protected static class convo_branch_builder {
        private final convo_branch branch;

        /**
         * Wraps an underlying conversation branch for fluent configuration.
         *
         * @param branch the branch being assembled
         */
        public convo_branch_builder(convo_branch branch) {
            this.branch = branch;
        }

        /**
         * Sets the dialogue text the NPC speaks upon entering this branch.
         *
         * @param message the dialogue text string
         * @return this builder instance for method chaining
         */
        public convo_branch_builder setNpcMessage(String message) {
            this.branch.npcMessage = new string_id(message);
            return this;
        }

        /**
         * Sets an animation action for the NPC to play upon entering this branch.
         *
         * @param anim the animation action string (e.g., {@code "wave_hail"}, {@code "nod"})
         * @return this builder instance for method chaining
         */
        public convo_branch_builder setNpcAnimation(String anim) {
            this.branch.npcAnimation = anim;
            return this;
        }

        /**
         * Adds a dialogue response option for the player that transitions to another branch.
         *
         * @param text         the response text shown to the player
         * @param nextBranchId the branch ID to transition to when clicked (or -1 to end conversation)
         * @return this builder instance for method chaining
         */
        public convo_branch_builder addResponse(String text, int nextBranchId) {
            branch.responses.add(new convo_response(new string_id(text), nextBranchId, null));
            return this;
        }

        /**
         * Adds a dialogue response option for the player with an inline action callback.
         *
         * @param text         the response text shown to the player
         * @param nextBranchId the branch ID to transition to when clicked (or -1 to end conversation)
         * @param action       action lambda {@code (npc, player)} executed when this option is clicked
         * @return this builder instance for method chaining
         */
        public convo_branch_builder addResponse(String text, int nextBranchId, BiConsumer<obj_id, obj_id> action) {
            branch.responses.add(new convo_response(new string_id(text), nextBranchId, action));
            return this;
        }

        /**
         * Adds an exit response that terminates the conversation with a closing NPC message.
         *
         * @param text       the response text shown to the player
         * @param endMessage the final message the NPC speaks upon conversation termination
         * @return this builder instance for method chaining
         */
        public convo_branch_builder addExitResponse(String text, String endMessage) {
            convo_response resp = new convo_response(new string_id(text), -1, null);
            resp.endMessage = endMessage;
            branch.responses.add(resp);
            return this;
        }

        /**
         * Adds an exit response that executes an action callback before terminating the conversation.
         *
         * @param text       the response text shown to the player
         * @param endMessage the final message the NPC speaks upon conversation termination
         * @param action     action lambda {@code (npc, player)} executed before exiting
         * @return this builder instance for method chaining
         */
        public convo_branch_builder addExitResponse(String text, String endMessage, BiConsumer<obj_id, obj_id> action) {
            convo_response resp = new convo_response(new string_id(text), -1, action);
            resp.endMessage = endMessage;
            branch.responses.add(resp);
            return this;
        }
    }

    /**
     * Internal container pairing a condition predicate with a target starting branch ID.
     */
    private static class convo_entry_point {
        /**
         * Condition lambda evaluated to see if this entry point matches.
         */
        BiPredicate<obj_id, obj_id> condition;

        /**
         * Branch ID to start if the condition passes.
         */
        int startBranchId;

        /**
         * Constructs an entry point definition.
         *
         * @param condition     eligibility check lambda
         * @param startBranchId target starting branch ID
         */
        convo_entry_point(BiPredicate<obj_id, obj_id> condition, int startBranchId) {
            this.condition = condition;
            this.startBranchId = startBranchId;
        }
    }

    /**
     * Internal representation of a conversation branch.
     */
    private static class convo_branch {
        /**
         * Unique numeric identifier for this branch.
         */
        int id;

        /**
         * Dialogue text string_id spoken by the NPC in this branch.
         */
        string_id npcMessage;

        /**
         * Animation action string played by the NPC upon entering this branch.
         */
        String npcAnimation;

        /**
         * List of available player response options for this branch.
         */
        List<convo_response> responses = new ArrayList<>();

        /**
         * Constructs a new branch container.
         *
         * @param id branch ID
         */
        convo_branch(int id) {
            this.id = id;
        }
    }

    /**
     * Internal representation of a player selectable response option.
     */
    private static class convo_response {
        /**
         * Display text string_id for this response option.
         */
        string_id responseText;

        /**
         * Destination branch ID to transition to, or -1 to terminate conversation.
         */
        int nextBranchId; // -1 for end conversation

        /**
         * Optional custom message spoken by the NPC when exiting through this option.
         */
        String endMessage;

        /**
         * Optional action lambda executed when this response is clicked.
         */
        BiConsumer<obj_id, obj_id> action; // Lambda for logic

        /**
         * Constructs a response option container.
         *
         * @param text   the response display text
         * @param nextId target branch ID or -1 for conversation exit
         * @param action callback lambda executed upon selection
         */
        convo_response(string_id text, int nextId, BiConsumer<obj_id, obj_id> action) {
            this.responseText = text;
            this.nextBranchId = nextId;
            this.action = action;
        }
    }
}