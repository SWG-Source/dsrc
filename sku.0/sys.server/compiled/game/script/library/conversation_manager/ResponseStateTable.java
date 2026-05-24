package script.library.conversation_manager;

import script.dictionary;
import script.library.datatable_api;
import script.library.conversation_manager.ConversationManagerData.ResponseState;

public class ResponseStateTable
{
    public static final String DEFAULT_DIRECTORY = "datatables/conversation";
    public static final String TABLE_SUFFIX = "_response_states.iff";

    public ResponseStateTable()
    {
    }

    public static String getTableName(String baseName)
    {
        return getTableName(DEFAULT_DIRECTORY, baseName);
    }

    public static String getTableName(String directory, String baseName)
    {
        if (baseName == null)
        {
            return null;
        }
        String normalizedBaseName = normalizeBaseName(baseName);
        String normalizedDirectory = normalizeDirectory(directory);
        if (normalizedDirectory.length() == 0)
        {
            return normalizedBaseName + TABLE_SUFFIX;
        }
        if (normalizedDirectory.endsWith("/"))
        {
            return normalizedDirectory + normalizedBaseName + TABLE_SUFFIX;
        }
        return normalizedDirectory + "/" + normalizedBaseName + TABLE_SUFFIX;
    }

    public static ResponseState[] getResponseStates(String baseName) throws InterruptedException
    {
        return getResponseStatesFromTable(getTableName(baseName));
    }

    public static ResponseState[] getResponseStates(String directory, String baseName) throws InterruptedException
    {
        return getResponseStatesFromTable(getTableName(directory, baseName));
    }

    public static ResponseState getResponseState(String directory, String baseName, String id) throws InterruptedException
    {
        return getResponseStateFromTable(getTableName(directory, baseName), id);
    }

    public static ResponseState[] getResponseStatesFromTable(String table) throws InterruptedException
    {
        dictionary[] rows = datatable_api.getRows(table);
        ResponseState[] states = fromDictionaries(rows);
        sortByOrder(states);
        return states;
    }

    public static ResponseState getResponseStateFromTable(String table, String id) throws InterruptedException
    {
        dictionary row = datatable_api.firstRowByString(table, ResponseState.COLUMN_ID, id);
        return ResponseState.fromDictionary(row);
    }

    private static ResponseState[] fromDictionaries(dictionary[] rows)
    {
        ResponseState[] states = new ResponseState[rows.length];
        for (int i = 0; i < rows.length; i++)
        {
            states[i] = ResponseState.fromDictionary(rows[i]);
        }
        return states;
    }

    private static void sortByOrder(ResponseState[] states)
    {
        for (int i = 1; i < states.length; i++)
        {
            ResponseState current = states[i];
            int j = i - 1;
            while (j >= 0 && states[j] != null && current != null && states[j].order > current.order)
            {
                states[j + 1] = states[j];
                j--;
            }
            states[j + 1] = current;
        }
    }

    private static String normalizeDirectory(String directory)
    {
        if (directory == null || directory.length() == 0)
        {
            return DEFAULT_DIRECTORY;
        }
        if (directory.startsWith("datatables/"))
        {
            return directory;
        }
        if (directory.startsWith("conversation/"))
        {
            return "datatables/" + directory;
        }
        return DEFAULT_DIRECTORY + "/" + directory;
    }

    private static String normalizeBaseName(String baseName)
    {
        if (baseName.endsWith("_") && TABLE_SUFFIX.startsWith("_"))
        {
            return baseName.substring(0, baseName.length() - 1);
        }
        return baseName;
    }
}
