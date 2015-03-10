package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetworkAreasCustomTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.cache.CacheProcessorException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.exceptions.ModelException;

public class FindChannelAccessAreasByTagTreeNodeIdAction
implements CacheProcessorAction<Collection<ChannelNetworkAreasCustomTO>> {

    private ChannelConfigFacadeDelegate delegate;
    private Long tagTreeNodeId;

    public FindChannelAccessAreasByTagTreeNodeIdAction(
            ChannelConfigFacadeDelegate delegate, Long tagTreeNodeId) {
        super();
        this.delegate = delegate;
        this.tagTreeNodeId = tagTreeNodeId;
    }

    @Override
    public Collection<ChannelNetworkAreasCustomTO> execute() throws ModelException,
            InternalErrorException, CacheProcessorException {
        return delegate.findChannelAccessAreasByTagTreeNodeId(tagTreeNodeId);
    }

}
