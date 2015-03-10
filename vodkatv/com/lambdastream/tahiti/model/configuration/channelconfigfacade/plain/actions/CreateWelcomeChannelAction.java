package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAOFactory;
import com.lambdastream.tahiti.model.configuration.welcomechannel.to.WelcomeChannelTO;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class CreateWelcomeChannelAction implements TransactionalPlainAction<WelcomeChannelTO> {

    private WelcomeChannelTO welcomeChannelTO;

    public CreateWelcomeChannelAction(WelcomeChannelTO welcomeChannelTO) {
        this.welcomeChannelTO = welcomeChannelTO;
    }

    public WelcomeChannelTO execute(DataConnection connection)
    throws DuplicateInstanceException, InternalErrorException {
        SQLWelcomeChannelDAO sqlWelcomeChannelDAO =
            SQLWelcomeChannelDAOFactory.getDAO();
        return sqlWelcomeChannelDAO.create(connection.getConnection(), welcomeChannelTO);
    }

}
