/**
 * Filename: FindAllChannelsDataAction.java
 * Created on 03/06/2009
 * @author Miguel Ángel Francisco Fernández <miguel.francisco@lambdastream.com>
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

/**
 * @author miguelafr
 *
 */
public class FindAllChannelsDataAction implements NonTransactionalPlainAction<Collection<ChannelDataTO>> {

    public FindAllChannelsDataAction() {
        ;
    }

    public Collection<ChannelDataTO> execute(DataConnection connection)
            throws InternalErrorException {
        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();
        return sqlChannelDataDAO.findAll(connection.getConnection());
    }

}
