/**
 * Filename: FindChannelsRedefinitionByRulePropertyIdAction.java
 * Created on 01/07/2009
 * @author Miguel Ángel Francisco Fernández <miguel.francisco@lambdastream.com>
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelRedefinitionTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;

/**
 * @author miguelafr
 *
 */
public class FindDefaultChannelsRedefinitionsAction
implements CacheProcessorAction<Collection<ChannelRedefinitionTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindDefaultChannelsRedefinitionsAction(
            ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }

    /* (non-Javadoc)
     * @see com.lambdastream.util.cache.CacheProcessorAction#execute()
     */
    public Collection<ChannelRedefinitionTO> execute() throws InternalErrorException {
        return delegate.findDefaultChannelsRedefinitions();
    }

}
