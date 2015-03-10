package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class DeleteAllChannelDatasAction implements TransactionalPlainAction<Object> {

    public DeleteAllChannelDatasAction() {
        ;
    }

    @Override
    public Object execute(DataConnection connection) throws InternalErrorException {
        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();
        sqlChannelDataDAO.removeAll(connection.getConnection());
        return null;
    }

}
