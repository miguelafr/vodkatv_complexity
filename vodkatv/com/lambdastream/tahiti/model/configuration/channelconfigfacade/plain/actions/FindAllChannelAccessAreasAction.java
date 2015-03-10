package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAO;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAOFactory;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindAllChannelAccessAreasAction
        implements NonTransactionalPlainAction<Collection<ChannelAccessAreaTO>> {

    public FindAllChannelAccessAreasAction() {
        ;
    }

    @Override
    public Collection<ChannelAccessAreaTO> execute(DataConnection connection)
            throws InternalErrorException {
        SQLChannelAccessAreaDAO sqlChannelAccessAreaDAO =
                SQLChannelAccessAreaDAOFactory.getDAO();
        return sqlChannelAccessAreaDAO.findAll(connection.getConnection());
    }



}
