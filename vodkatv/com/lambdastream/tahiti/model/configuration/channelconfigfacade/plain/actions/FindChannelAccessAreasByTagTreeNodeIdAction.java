package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAO;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.dao.SQLChannelAccessAreaDAOFactory;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetworkAreasCustomTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.networkarea.to.NetworkAreaTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.exceptions.ModelException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class FindChannelAccessAreasByTagTreeNodeIdAction
implements NonTransactionalPlainAction<Collection<ChannelNetworkAreasCustomTO>> {

    private Long tagTreeNodeId;

    public FindChannelAccessAreasByTagTreeNodeIdAction(Long tagTreeNodeId) {
        super();
        this.tagTreeNodeId = tagTreeNodeId;
    }

    @Override
    public Collection<ChannelNetworkAreasCustomTO> execute(DataConnection dataConnection)
            throws ModelException, InternalErrorException {
        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();

        SQLChannelAccessAreaDAO sqlChannelAccessAreaDAO =
                SQLChannelAccessAreaDAOFactory.getDAO();

        Collection<ChannelAccessAreaTO> accessAreaTOs = sqlChannelAccessAreaDAO.findByTagTreeNodeId(
                dataConnection.getConnection(), tagTreeNodeId);
        Map<String, Collection<NetworkAreaTO>> map = new HashMap<String, Collection<NetworkAreaTO>>();

        for(ChannelAccessAreaTO accessAreaTO : accessAreaTOs){
            String channelId = accessAreaTO.getVodkatvChannelId();
            Collection<NetworkAreaTO> networkAreasIds;
            if(map.containsKey(channelId)){
                networkAreasIds = map.get(channelId);
            }else{
                networkAreasIds = new ArrayList<NetworkAreaTO>();
            }
            NetworkAreaTO networkAreaTO =
                    configurationFacadeDelegate.findNetworkAreaById(
                            accessAreaTO.getNetworkAreaId());
            networkAreasIds.add(networkAreaTO);

            map.put(channelId, networkAreasIds);
        }

        Collection<ChannelNetworkAreasCustomTO> result =
                new ArrayList<ChannelNetworkAreasCustomTO>();
        for(String channelId : map.keySet()){
            result.add(new ChannelNetworkAreasCustomTO(
                    tagTreeNodeId,
                    channelId,
                    map.get(channelId)
                    ));
        }
        return result;
    }

}
