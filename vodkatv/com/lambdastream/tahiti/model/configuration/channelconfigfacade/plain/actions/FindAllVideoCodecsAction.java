package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLVideoCodecDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLVideoCodecDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.VideoCodecTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindAllVideoCodecsAction  implements NonTransactionalPlainAction<Collection<VideoCodecTO>> {

    public FindAllVideoCodecsAction(){
        ;
    }

    public Collection<VideoCodecTO> execute(DataConnection connection)
    throws InternalErrorException, InstanceNotFoundException {
        SQLVideoCodecDAO sqlVideoCodecDAO =
                SQLVideoCodecDAOFactory.getDAO();

        return sqlVideoCodecDAO.findAll(connection.getConnection());
    }
}
