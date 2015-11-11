package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.MBeanServerConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Hashtable;

@Path("/")
public class GatewayResource {

    private static final Logger log= LoggerFactory.getLogger(GatewayResource.class);
    private final static String RESYNC_QUEUE_JNDI_NAME = "/queue/resync";

    @POST
    @Path("resync")
    @Consumes("application/xml")
    public Response printMessage(String message) {

        try {
            Hashtable env = new Hashtable();
            Context ctx = null;
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.PROVIDER_URL, System.getenv("HOSTNAME")+":1099");
            env.put("jnp.disableDiscovery", "true");

            ctx = new InitialContext(env);

            String adapterName = "jmx/rmi/RMIAdaptor";
            Object obj = ctx.lookup(adapterName);
            ConnectionFactory myConnFactory;
            Queue myQueue;
            myConnFactory = (javax.jms.ConnectionFactory) ctx.lookup("ConnectionFactory");
            if (!(obj instanceof MBeanServerConnection)) {
                throw new RuntimeException("Object not of type: MBeanServerConnection, but: "
                        + (obj == null ? "not found" : obj.getClass().getName()));
            }

            MBeanServerConnection mbeanServer = (MBeanServerConnection) obj;
            Connection myConn = myConnFactory.createConnection();
            Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            myQueue = (javax.jms.Queue)ctx.lookup(RESYNC_QUEUE_JNDI_NAME);
            MessageProducer myMsgProducer = mySess.createProducer(myQueue);
            TextMessage myTextMsg = mySess.createTextMessage();
            myTextMsg.setText(message);
            myMsgProducer.send(myTextMsg);
            mySess.close();
            log.info("Successful resync: "+message);
        } catch (JMSException e) {
            log.error("JMSException occurred upon resync : " + e.getMessage());
            return Response.status(503).build();
        } catch (NamingException e) {
            log.error("NamingException occurred upon resync : "  + e.getMessage());
            return Response.status(503).build();
        }
        return Response.status(200).build();
    }

}
