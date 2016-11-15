package org.talend.components.jms.runtime_1_1;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.exception.ComponentException;
import org.talend.components.common.datastore.runtime.DatastoreRuntime;
import org.talend.components.jms.JmsDatastoreProperties;
import org.talend.components.jms.JmsMessageType;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;

import java.util.Arrays;
import java.util.Hashtable;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsDatastoreRuntime implements DatastoreRuntime {

    protected transient JmsDatastoreProperties properties;

    private JmsDatastoreProperties.JmsVersion version;

    private String contextProvider;

    private String serverUrl;

    private String connectionFactoryName;

    private String userName;

    private String userPassword;

    private JmsMessageType msgType;

    @Override
    public Iterable<ValidationResult> doHealthChecks(RuntimeContainer container) {
        try {
            // create connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.serverUrl.getValue());
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.close();
            if (connection != null) {
                return Arrays.asList(ValidationResult.OK);
            }

        } catch (JMSException e) {
            throw new ComponentException(e);
        }
        return null;
    }

    @Override
    public ValidationResult initialize(RuntimeContainer container, Properties properties) {
        this.properties = (JmsDatastoreProperties) properties;
        return ValidationResult.OK;
    }

    public ValidationResult initialize(RuntimeContainer container, JmsDatastoreProperties properties) {
        this.properties = properties;
        return ValidationResult.OK;
    }

    public ConnectionFactory getConnectionFactory() {
        Context context;
        Hashtable<String, String> env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, properties.contextProvider.getValue());
        env.put(Context.PROVIDER_URL, properties.serverUrl.getValue());
        ConnectionFactory connection = null;
        try {
            context = new InitialContext(env);
            connection = (ConnectionFactory) context.lookup("ConnectionFactory");
            // TODO check if username required how it works
            /*
             * if (datastore.needUserIdentity.getValue()) {
             * connection = tcf.createConnection(datastore.userName.getValue(),datastore.userPassword.getValue());
             * } else {
             * connection = tcf.createTopicConnection();
             * }
             */
        } catch (NamingException e) {
            throw new ComponentException(e);
        }
        return connection;
    }
}
