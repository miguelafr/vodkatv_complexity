package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;
import com.lambdastream.util.utils.ChunkTO;
import com.lambdastream.util.utils.ChunkUtils;

public class FindChannelsDataAction implements NonTransactionalPlainAction<ChunkTO<ChannelDataTO>> {

    private SearchOptions searchOptions;

    public FindChannelsDataAction(SearchOptions searchOptions) {
        this.searchOptions = searchOptions;
    }

    public ChunkTO<ChannelDataTO> execute(DataConnection connection)
            throws InternalErrorException {
        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();
        Collection<ChannelDataTO> channelDataTOs = sqlChannelDataDAO.findAll(
                connection.getConnection());

        if(searchOptions.getStartIndex() != null && searchOptions.getCount() != null) {
            return ChunkUtils.getChunk(channelDataTOs,
                    searchOptions.getStartIndex().intValue(),
                    searchOptions.getCount().intValue());
        } else {
            return ChunkUtils.getChunk(channelDataTOs, 1, channelDataTOs.size());
        }
    }
}