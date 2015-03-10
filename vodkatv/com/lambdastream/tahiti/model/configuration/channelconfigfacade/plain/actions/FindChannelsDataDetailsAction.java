package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.SimplePlainAction;
import com.lambdastream.util.utils.ChunkTO;

public class FindChannelsDataDetailsAction implements SimplePlainAction<ChunkTO<ChannelDataDetailsTO>> {

    private SearchOptions searchOptions;

    public FindChannelsDataDetailsAction(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
    }

    public ChunkTO<ChannelDataDetailsTO> execute()
            throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        ChunkTO<ChannelDataTO> channelDatas =
                channelConfigFacadeDelegate.findChannelsData(searchOptions);
        return new ChunkTO<ChannelDataDetailsTO>(
                Transforms.toChannelDataDetailsTOs(channelDatas.getObjects()),
                        channelDatas.getExistsMore(),
                        channelDatas.getCountTotal());
    }

}
