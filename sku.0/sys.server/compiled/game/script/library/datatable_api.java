package script.library;

import script.dictionary;

import java.util.Vector;

public class datatable_api
{
    public datatable_api()
    {
    }

    public static dictionary getRow(String table, int row) throws InterruptedException
    {
        if (table == null || row < 0)
        {
            return null;
        }
        int rowCount = utils.dataTableGetNumRows(table);
        if (row >= rowCount)
        {
            return null;
        }
        return utils.dataTableGetRow(table, row);
    }

    public static dictionary getRow(String table, String key) throws InterruptedException
    {
        if (table == null || key == null)
        {
            return null;
        }
        return utils.dataTableGetRow(table, key);
    }

    public static dictionary[] getRows(String table) throws InterruptedException
    {
        if (table == null)
        {
            return new dictionary[0];
        }
        int rowCount = utils.dataTableGetNumRows(table);
        dictionary[] rows = new dictionary[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            rows[i] = utils.dataTableGetRow(table, i);
        }
        return rows;
    }

    public static dictionary firstRowByString(String table, String column, String value) throws InterruptedException
    {
        int row = findFirstRowIndexByString(table, column, value);
        if (row < 0)
        {
            return null;
        }
        return utils.dataTableGetRow(table, row);
    }

    public static dictionary firstRowByInt(String table, String column, int value) throws InterruptedException
    {
        int row = findFirstRowIndexByInt(table, column, value);
        if (row < 0)
        {
            return null;
        }
        return utils.dataTableGetRow(table, row);
    }

    public static dictionary[] findRowsByString(String table, String column, String value) throws InterruptedException
    {
        if (table == null || column == null || value == null)
        {
            return new dictionary[0];
        }
        Vector rows = new Vector();
        int rowCount = utils.dataTableGetNumRows(table);
        for (int i = 0; i < rowCount; i++)
        {
            dictionary row = utils.dataTableGetRow(table, i);
            if (row != null && row.containsKey(column) && value.equals(row.getString(column)))
            {
                rows.add(row);
            }
        }
        return toArray(rows);
    }

    public static dictionary[] findRowsByStrings(String table, String[] columns, String[] values) throws InterruptedException
    {
        if (table == null || !isValidFilter(columns, values))
        {
            return new dictionary[0];
        }
        Vector rows = new Vector();
        int rowCount = utils.dataTableGetNumRows(table);
        for (int i = 0; i < rowCount; i++)
        {
            dictionary row = utils.dataTableGetRow(table, i);
            if (matchesStrings(row, columns, values))
            {
                rows.add(row);
            }
        }
        return toArray(rows);
    }

    public static dictionary[] findRowsByInt(String table, String column, int value) throws InterruptedException
    {
        if (table == null || column == null)
        {
            return new dictionary[0];
        }
        Vector rows = new Vector();
        int rowCount = utils.dataTableGetNumRows(table);
        for (int i = 0; i < rowCount; i++)
        {
            dictionary row = utils.dataTableGetRow(table, i);
            if (row != null && row.containsKey(column) && row.getInt(column) == value)
            {
                rows.add(row);
            }
        }
        return toArray(rows);
    }

    public static dictionary[] findRowsByInts(String table, String[] columns, int[] values) throws InterruptedException
    {
        if (table == null || !isValidFilter(columns, values))
        {
            return new dictionary[0];
        }
        Vector rows = new Vector();
        int rowCount = utils.dataTableGetNumRows(table);
        for (int i = 0; i < rowCount; i++)
        {
            dictionary row = utils.dataTableGetRow(table, i);
            if (matchesInts(row, columns, values))
            {
                rows.add(row);
            }
        }
        return toArray(rows);
    }

    public static int findFirstRowIndexByString(String table, String column, String value) throws InterruptedException
    {
        if (table == null || column == null || value == null)
        {
            return -1;
        }
        return utils.dataTableSearchColumnForString(value, column, table);
    }

    public static int findFirstRowIndexByInt(String table, String column, int value) throws InterruptedException
    {
        if (table == null || column == null)
        {
            return -1;
        }
        return utils.dataTableSearchColumnForInt(value, column, table);
    }

    private static dictionary[] toArray(Vector rows)
    {
        dictionary[] result = new dictionary[rows.size()];
        rows.copyInto(result);
        return result;
    }

    private static boolean isValidFilter(String[] columns, String[] values)
    {
        if (columns == null || values == null || columns.length == 0 || columns.length != values.length)
        {
            return false;
        }
        for (int i = 0; i < columns.length; i++)
        {
            if (columns[i] == null || values[i] == null)
            {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidFilter(String[] columns, int[] values)
    {
        if (columns == null || values == null || columns.length == 0 || columns.length != values.length)
        {
            return false;
        }
        for (int i = 0; i < columns.length; i++)
        {
            if (columns[i] == null)
            {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesStrings(dictionary row, String[] columns, String[] values)
    {
        if (row == null)
        {
            return false;
        }
        for (int i = 0; i < columns.length; i++)
        {
            if (!row.containsKey(columns[i]) || !values[i].equals(row.getString(columns[i])))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesInts(dictionary row, String[] columns, int[] values)
    {
        if (row == null)
        {
            return false;
        }
        for (int i = 0; i < columns.length; i++)
        {
            if (!row.containsKey(columns[i]) || row.getInt(columns[i]) != values[i])
            {
                return false;
            }
        }
        return true;
    }
}
