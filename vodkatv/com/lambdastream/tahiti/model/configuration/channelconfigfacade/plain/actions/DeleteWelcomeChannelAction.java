package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAOFactory;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class DeleteWelcomeChannelAction implements TransactionalPlainAction<Object> {

    private Long welcomeChannelId;

    public DeleteWelcomeChannelAction(Long welcomeChannelId) {
        this.welcomeChannelId = welcomeChannelId;
    }

    public Object execute(DataConnection connection)
    throws InstanceNotFoundException, InternalErrorException {
        SQLWelcomeChannelDAO sqlWelcomeChannelDAO =
            SQLWelcomeChannelDAOFactory.getDAO();
        sqlWelcomeChannelDAO.remove(connection.getConnection(), welcomeChannelId);
        return null;
    }

}
