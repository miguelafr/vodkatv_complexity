package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.util.facade.PlainFacadeDataSource;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

/**
 * TODO: Check if cache needed:
 * tahitiforus - success: 96.97% (320 / 330) [2012/05/04 13:19:07]
 * @author baleato
 */
public class FindAllActiveChannelsDataAction
implements NonTransactionalPlainAction<Collection<ChannelDataTO>> {

    public FindAllActiveChannelsDataAction() {
        ;
    }

    public Collection<ChannelDataTO> execute(DataConnection connection)
            throws InternalErrorException {

        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate(
                        new PlainFacadeDataSource(connection));
        Collection<ChannelDataTO> channelDataTOs =
                channelConfigFacadeDelegate.findAllChannelsData();

        Collection<ChannelDataTO> result = new ArrayList<ChannelDataTO>();
        for(ChannelDataTO channelDataTO : channelDataTOs){
            if(channelDataTO.isActive()){
                result.add(channelDataTO);
            }
        }

        return result;
    }

}
