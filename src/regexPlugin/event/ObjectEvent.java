package regexPlugin.event;

import java.util.EventObject;

public class ObjectEvent extends EventObject
{
    private String m_message;

    private Object m_body;

    /**
     * Create a very generic event,
     *
     * @param object  The source for the event
     * @param message The message itself.
     * @param body    Any associated object for the message.
     */
    public ObjectEvent( final Notifiable object, final String message, final Object body )
    {
        super( object );
        m_message = message;
        m_body = body;
    }

    public String getMessage()
    {
        return m_message;
    }

    public Object getBody()
    {
        return m_body;
    }

    public String toString()
    {
        return "" + getSource() + ' ' + getMessage() + ' ' + getBody();
    }
}
