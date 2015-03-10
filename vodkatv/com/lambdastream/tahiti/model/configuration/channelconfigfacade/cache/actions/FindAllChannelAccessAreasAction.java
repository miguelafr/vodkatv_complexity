package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindAllChannelAccessAreasAction implements CacheProcessorAction<Collection<ChannelAccessAreaTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindAllChannelAccessAreasAction(ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }

    public Collection<ChannelAccessAreaTO> execute() throws InternalErrorException {
        return delegate.findAllChannelAccessAreas();
    }

}
