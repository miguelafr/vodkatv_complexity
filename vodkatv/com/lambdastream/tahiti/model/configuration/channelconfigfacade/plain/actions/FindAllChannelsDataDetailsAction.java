package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.SimplePlainAction;

public class FindAllChannelsDataDetailsAction implements SimplePlainAction<Collection<ChannelDataDetailsTO>> {

    public FindAllChannelsDataDetailsAction() {
        ;
    }

    public Collection<ChannelDataDetailsTO> execute()
            throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        Collection<ChannelDataTO> channelDataTOs =
                channelConfigFacadeDelegate.findAllChannelsData();
        return Transforms.toChannelDataDetailsTOs(channelDataTOs);
    }

}
