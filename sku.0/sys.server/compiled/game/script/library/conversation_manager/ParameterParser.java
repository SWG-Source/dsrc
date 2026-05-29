package script.library.conversation_manager;

import script.library.conversation_manager.ConversationManagerData.ParameterArray;
import script.library.conversation_manager.ConversationManagerData.ParameterObject;
import script.library.conversation_manager.ConversationManagerData.ParameterValue;

public class ParameterParser
{
    private String text;
    private int index;

    public ParameterParser()
    {
        this("");
    }

    private ParameterParser(String text)
    {
        this.text = text;
        index = 0;
    }

    public static ParameterObject parseObject(String text)
    {
        if (text == null || text.trim().length() == 0)
        {
            return new ParameterObject();
        }
        ParameterParser parser = new ParameterParser(text);
        ParameterObject result = parser.readObject();
        parser.skipWhitespace();
        if (!parser.isAtEnd())
        {
            parser.fail("Unexpected text after parameter object");
        }
        return result;
    }

    public static ParameterValue parseValue(String text)
    {
        if (text == null)
        {
            return null;
        }
        ParameterParser parser = new ParameterParser(text);
        ParameterValue result = parser.readValue();
        parser.skipWhitespace();
        if (!parser.isAtEnd())
        {
            parser.fail("Unexpected text after parameter value");
        }
        return result;
    }

    private ParameterObject readObject()
    {
        expect('{');
        ParameterObject object = new ParameterObject();
        skipWhitespace();
        if (peek('}'))
        {
            index++;
            return object;
        }
        while (true)
        {
            String key = readKey();
            skipWhitespace();
            expect(':');
            ParameterValue value = readValue();
            object.put(key, value);
            skipWhitespace();
            if (peek('}'))
            {
                index++;
                return object;
            }
            expect(',');
        }
    }

    private ParameterArray readArray()
    {
        expect('[');
        ParameterArray array = new ParameterArray();
        skipWhitespace();
        if (peek(']'))
        {
            index++;
            return array;
        }
        while (true)
        {
            array.add(readValue());
            skipWhitespace();
            if (peek(']'))
            {
                index++;
                return array;
            }
            expect(',');
        }
    }

    private ParameterValue readValue()
    {
        skipWhitespace();
        if (isAtEnd())
        {
            fail("Expected value");
        }
        char ch = text.charAt(index);
        if (ch == '"')
        {
            return ParameterValue.stringValue(readString());
        }
        if (ch == '{')
        {
            return ParameterValue.objectValue(readObject());
        }
        if (ch == '[')
        {
            return ParameterValue.arrayValue(readArray());
        }
        if (ch == '-' || isDigit(ch))
        {
            return ParameterValue.intValue(readInt());
        }
        return ParameterValue.stringValue(readBareString());
    }

    private String readKey()
    {
        skipWhitespace();
        if (isAtEnd())
        {
            fail("Expected key");
        }
        if (text.charAt(index) == '"')
        {
            return readString();
        }
        int start = index;
        while (!isAtEnd())
        {
            char ch = text.charAt(index);
            if (isKeyChar(ch))
            {
                index++;
            }
            else
            {
                break;
            }
        }
        if (start == index)
        {
            fail("Expected key");
        }
        return text.substring(start, index);
    }

    private String readString()
    {
        expect('"');
        StringBuffer buffer = new StringBuffer();
        while (!isAtEnd())
        {
            char ch = text.charAt(index++);
            if (ch == '"')
            {
                return buffer.toString();
            }
            if (ch == '\\')
            {
                if (isAtEnd())
                {
                    fail("Expected escaped character");
                }
                char escaped = text.charAt(index++);
                if (escaped == '"' || escaped == '\\')
                {
                    buffer.append(escaped);
                }
                else if (escaped == 'n')
                {
                    buffer.append('\n');
                }
                else if (escaped == 't')
                {
                    buffer.append('\t');
                }
                else
                {
                    fail("Unsupported escape sequence");
                }
            }
            else
            {
                buffer.append(ch);
            }
        }
        fail("Unterminated string");
        return null;
    }

    private String readBareString()
    {
        int start = index;
        while (!isAtEnd())
        {
            char ch = text.charAt(index);
            if (ch == ',' || ch == '}' || ch == ']')
            {
                break;
            }
            index++;
        }
        String value = text.substring(start, index).trim();
        if (value.length() == 0)
        {
            fail("Expected string, int, object, or array");
        }
        return value;
    }

    private int readInt()
    {
        int start = index;
        if (peek('-'))
        {
            index++;
        }
        if (isAtEnd() || !isDigit(text.charAt(index)))
        {
            fail("Expected int");
        }
        while (!isAtEnd() && isDigit(text.charAt(index)))
        {
            index++;
        }
        try
        {
            return Integer.parseInt(text.substring(start, index));
        }
        catch (NumberFormatException ex)
        {
            fail("Invalid int");
        }
        return 0;
    }

    private void expect(char expected)
    {
        skipWhitespace();
        if (isAtEnd() || text.charAt(index) != expected)
        {
            fail("Expected '" + expected + "'");
        }
        index++;
    }

    private boolean peek(char ch)
    {
        return !isAtEnd() && text.charAt(index) == ch;
    }

    private void skipWhitespace()
    {
        while (!isAtEnd() && Character.isWhitespace(text.charAt(index)))
        {
            index++;
        }
    }

    private boolean isAtEnd()
    {
        return index >= text.length();
    }

    private boolean isDigit(char ch)
    {
        return ch >= '0' && ch <= '9';
    }

    private boolean isKeyChar(char ch)
    {
        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '-' || ch == '.';
    }

    private void fail(String message)
    {
        throw new IllegalArgumentException(message + " at position " + index);
    }
}
