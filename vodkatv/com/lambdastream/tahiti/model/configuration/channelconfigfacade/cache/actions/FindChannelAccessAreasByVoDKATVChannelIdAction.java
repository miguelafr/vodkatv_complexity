package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindChannelAccessAreasByVoDKATVChannelIdAction
        implements CacheProcessorAction<Collection<ChannelAccessAreaTO>> {

    private ChannelConfigFacadeDelegate delegate;

    private String vodkatvChannelId;

    public FindChannelAccessAreasByVoDKATVChannelIdAction(
            ChannelConfigFacadeDelegate delegate, String vodkatvChannelId) {
        this.delegate = delegate;
        this.vodkatvChannelId = vodkatvChannelId;
    }

    public Collection<ChannelAccessAreaTO> execute() throws InternalErrorException {
        return delegate.findChannelAccessAreasByVoDKATVChannelId(vodkatvChannelId);
    }
}
