package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.WelcomeChannelDetailsTO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAOFactory;
import com.lambdastream.tahiti.model.configuration.welcomechannel.to.WelcomeChannelTO;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegate;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegateFactory;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindWelcomeChannelsDetailsAction
implements NonTransactionalPlainAction<Collection<WelcomeChannelDetailsTO>> {

    public FindWelcomeChannelsDetailsAction() {
        ;
    }

    public Collection<WelcomeChannelDetailsTO> execute(DataConnection connection)
            throws InternalErrorException {

        MediaChannelsFacadeDelegate mediaChannelsFacadeDelegate =
            MediaChannelsFacadeDelegateFactory.getDelegate();
        SQLWelcomeChannelDAO sqlWelcomeChannelDAO =
            SQLWelcomeChannelDAOFactory.getDAO();

        Collection<WelcomeChannelTO> welcomeChannelTOs =
            sqlWelcomeChannelDAO.findAll(connection.getConnection());

        Collection<WelcomeChannelDetailsTO> result =
            new ArrayList<WelcomeChannelDetailsTO>();

        for(WelcomeChannelTO welcomeChannelTO : welcomeChannelTOs) {
            ChannelTO channelTO = null;
            String channelName = null;
            try {
                channelTO =
                    mediaChannelsFacadeDelegate.findChannelByVodkatvChannelId(
                            welcomeChannelTO.getVodkatvChannelId());
                channelName = channelTO.getName();
            } catch (InstanceNotFoundException e) {
                ;
            }
            result.add(new WelcomeChannelDetailsTO(welcomeChannelTO,
                    channelTO, channelName));
        }

        return result;
    }

}
