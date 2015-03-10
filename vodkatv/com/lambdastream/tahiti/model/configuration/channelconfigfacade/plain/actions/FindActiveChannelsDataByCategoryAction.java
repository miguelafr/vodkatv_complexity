package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelCategoryTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindActiveChannelsDataByCategoryAction {

    private Long categoryId;

    public FindActiveChannelsDataByCategoryAction(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Collection<ChannelDataTO> execute() throws InternalErrorException {

        /*
         * Facades
         */
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        /*
         * Get channels
         */
        Collection<ChannelDataTO> channelTOs =
                channelConfigFacadeDelegate.findAllActiveChannelsData();

        /*
         * Build result
         */
        Collection<ChannelDataTO> result = new ArrayList<ChannelDataTO>();
        for(ChannelDataTO channelDataTO : channelTOs) {
            for(ChannelCategoryTO channelCategoryTO : channelDataTO.getChannelCategoryTOs()) {
                if(channelCategoryTO.getCategoryId().equals(categoryId)) {
                    result.add(channelDataTO);
                    break;
                }
            }
        }


        /*
         * Return result
         */
        return result;

    }
}
