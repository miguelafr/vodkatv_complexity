package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindChannelDataByVoDKATVChannelIdAction implements
NonTransactionalPlainAction<ChannelDataTO> {

    private String channelId;

    public FindChannelDataByVoDKATVChannelIdAction(String channelId) {
        this.channelId = channelId;
    }

    public ChannelDataTO execute(DataConnection connection)
    throws InstanceNotFoundException, InternalErrorException {

        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();

        return sqlChannelDataDAO.findByVoDKATVChannelId(connection.getConnection(), channelId);
    }

}
