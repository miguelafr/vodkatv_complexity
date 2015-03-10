package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLAudioCodecDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLAudioCodecDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.AudioCodecTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindAllAudioCodecsAction implements NonTransactionalPlainAction<Collection<AudioCodecTO>> {

    public FindAllAudioCodecsAction(){
        ;
    }

    public Collection<AudioCodecTO> execute(DataConnection connection)
    throws InternalErrorException, InstanceNotFoundException {
        SQLAudioCodecDAO sqlAudioCodecDAO =
                SQLAudioCodecDAOFactory.getDAO();

        return sqlAudioCodecDAO.findAll(connection.getConnection());
    }
}
