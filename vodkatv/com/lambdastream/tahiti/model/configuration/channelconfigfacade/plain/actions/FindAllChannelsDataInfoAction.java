/**
 * Filename: FindAllChannelDataDetailsAction.java
 * Created on 03/06/2009
 * @author Miguel Ángel Francisco Fernández <miguel.francisco@lambdastream.com>
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;

/**
 * @author miguelafr
 *
 */
public class FindAllChannelsDataInfoAction {

    public FindAllChannelsDataInfoAction() {
        ;
    }

    public Collection<ChannelDataInfoTO> execute() throws InternalErrorException {

        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        
        Collection<ChannelDataTO> channelDataTOs =
                channelConfigFacadeDelegate.findAllChannelsData();
        
        return Transforms.toChannelDataInfoTOs(channelDataTOs);
    }

}
