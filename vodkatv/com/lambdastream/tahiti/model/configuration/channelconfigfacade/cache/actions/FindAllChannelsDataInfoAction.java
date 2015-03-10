package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindAllChannelsDataInfoAction
implements CacheProcessorAction<Collection<ChannelDataInfoTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindAllChannelsDataInfoAction(ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }
    public Collection<ChannelDataInfoTO> execute()
            throws InstanceNotFoundException, InternalErrorException {
        return delegate.findAllChannelsDataInfo();
    }
}
