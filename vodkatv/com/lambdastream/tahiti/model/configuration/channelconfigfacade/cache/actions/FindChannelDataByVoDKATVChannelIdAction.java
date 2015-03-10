package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindChannelDataByVoDKATVChannelIdAction
implements CacheProcessorAction<ChannelDataTO> {

    private ChannelConfigFacadeDelegate delegate;

    private String channelId;

    public FindChannelDataByVoDKATVChannelIdAction(
            ChannelConfigFacadeDelegate delegate, String channelId) {
        this.delegate = delegate;
        this.channelId = channelId;
    }
    public ChannelDataTO execute() throws InstanceNotFoundException, InternalErrorException {
        return delegate.findChannelDataByVoDKATVChannelId(channelId);
    }

}
