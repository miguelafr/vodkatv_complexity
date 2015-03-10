package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Locale;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.dao.SQLWelcomeChannelDAOFactory;
import com.lambdastream.tahiti.model.configuration.welcomechannel.to.WelcomeChannelTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindWelcomeChannelByLocaleAction
implements NonTransactionalPlainAction<ChannelDataTO> {

    private Locale locale;

    public FindWelcomeChannelByLocaleAction(Locale locale) {
        this.locale = locale;
    }

    public ChannelDataTO execute(DataConnection connection)
    throws InstanceNotFoundException, InternalErrorException {

        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        SQLWelcomeChannelDAO sqlWelcomeChannelDAO =
            SQLWelcomeChannelDAOFactory.getDAO();

        WelcomeChannelTO welcomeChannelTO = null;
        try {
            welcomeChannelTO = sqlWelcomeChannelDAO.findByLocale(
                    connection.getConnection(), locale);
        } catch(InstanceNotFoundException infe) {
            welcomeChannelTO = sqlWelcomeChannelDAO.findByDefaultLocale(
                    connection.getConnection());
        }

        return channelConfigFacadeDelegate.findChannelDataByVoDKATVChannelId(
                welcomeChannelTO.getVodkatvChannelId());
    }

}
