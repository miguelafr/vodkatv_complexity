package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAO;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAOFactory;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetworkAreasCustomTO;
import com.lambdastream.tahiti.model.configuration.networkarea.to.NetworkAreaTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.exceptions.ModelException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class UpdateChannelAccessAreasByTagTreeNodeIdAction
implements TransactionalPlainAction<Object> {

    private Long tagTreeNodeId;
    private Collection<ChannelNetworkAreasCustomTO> channelAccessAreaTOs;

    public UpdateChannelAccessAreasByTagTreeNodeIdAction(Long tagTreeNodeId,
            Collection<ChannelNetworkAreasCustomTO> channelAccessAreaTOs) {
        this.tagTreeNodeId = tagTreeNodeId;
        this.channelAccessAreaTOs = channelAccessAreaTOs;
    }

    @Override
    public Object execute(DataConnection dataConnection) throws ModelException,
            InternalErrorException {
        SQLChannelAccessAreaDAO sqlChannelAccessAreaDAO =
                SQLChannelAccessAreaDAOFactory.getDAO();

        sqlChannelAccessAreaDAO.removeByTagNodeId(
                dataConnection.getConnection(), tagTreeNodeId);

        for(ChannelNetworkAreasCustomTO channelAccessAreaTO : channelAccessAreaTOs){
            for(NetworkAreaTO networkAreaTO : channelAccessAreaTO.getNetworkAreas()){
                sqlChannelAccessAreaDAO.create(
                        dataConnection.getConnection(),
                        new ChannelAccessAreaTO(
                                null,
                                networkAreaTO.getId(),
                                tagTreeNodeId,
                                channelAccessAreaTO.getVodkatvChannelId()));
            }
        }
        return null;
    }

}
