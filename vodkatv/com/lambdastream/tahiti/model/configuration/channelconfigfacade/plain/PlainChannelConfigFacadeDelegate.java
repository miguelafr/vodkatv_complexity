package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain;

import java.util.Collection;
import java.util.Locale;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.CreateChannelDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.CreateChannelDataDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.CreateWelcomeChannelAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.DeleteAllChannelDatasAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.DeleteChannelDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.DeleteChannelDataByVodkatvChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.DeleteWelcomeChannelAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.ExistsAudioCodecAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.ExistsVideoCodecAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindActiveChannelsByVideoServerGroupsOriginAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindActiveChannelsDataByCategoryAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllActiveChannelsDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllAudioCodecsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllChannelAccessAreasAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllChannelsDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllChannelsDataDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllChannelsDataIfRecordingActiveAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllChannelsDataInfoAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindAllVideoCodecsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelAccessAreasByTagTreeNodeIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelAccessAreasByVoDKATVChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataByIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataByVoDKATVChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataDetailsByIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataInfoByIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataInfoByVoDKATVChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelDataPlayInfoByUserAgentAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelsDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindChannelsDataDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindDefaultChannelsRedefinitionsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindWelcomeChannelByLocaleAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.FindWelcomeChannelsDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.UpdateChannelAccessAreasByTagTreeNodeIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.UpdateChannelDataAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions.UpdateChannelDataDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetworkAreasCustomTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelRedefinitionTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.WelcomeChannelDetailsTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.AudioCodecTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.VideoCodecTO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.to.WelcomeChannelTO;
import com.lambdastream.tahiti.model.util.exceptions.DuplicateChannelAudioLanguageInstanceException;
import com.lambdastream.tahiti.model.util.facade.FacadeDataSource;
import com.lambdastream.tahiti.model.util.facade.PlainFacadeDelegate;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.PlainActionProcessor;
import com.lambdastream.util.utils.ChunkTO;

public class PlainChannelConfigFacadeDelegate
extends PlainFacadeDelegate implements ChannelConfigFacadeDelegate {

    public PlainChannelConfigFacadeDelegate() {
        super(null);
    }

    public PlainChannelConfigFacadeDelegate(FacadeDataSource facadeDataSource) {
        super(facadeDataSource);
    }

    /*
     * Channels redefinition
     */
    @Override
    public Collection<ChannelRedefinitionTO> findDefaultChannelsRedefinitions()
            throws InternalErrorException {
        FindDefaultChannelsRedefinitionsAction action =
                new FindDefaultChannelsRedefinitionsAction();

        try {
            return action.execute();
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    /*
     * ChannelData
     */
    @Override
    public ChannelDataTO findChannelDataByVoDKATVChannelId(
            String vodkatvChannelId) throws InstanceNotFoundException,
            InternalErrorException {
        FindChannelDataByVoDKATVChannelIdAction action = new FindChannelDataByVoDKATVChannelIdAction(
                vodkatvChannelId);
        try {
            return (ChannelDataTO) PlainActionProcessor.process(
                    getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataInfoTO findChannelDataInfoByVoDKATVChannelId(
            String vodkatvChannelId) throws InstanceNotFoundException,
            InternalErrorException {
    	FindChannelDataInfoByVoDKATVChannelIdAction action =
    			new FindChannelDataInfoByVoDKATVChannelIdAction(
    					vodkatvChannelId);
        try {
            return PlainActionProcessor.process(action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataTO findChannelDataById(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        FindChannelDataByIdAction action = new FindChannelDataByIdAction(id);
        try {
            return (ChannelDataTO) PlainActionProcessor.process(
                    getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataDetailsTO findChannelDataDetailsById(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        FindChannelDataDetailsByIdAction action = new FindChannelDataDetailsByIdAction(
                id);
        try {
            return PlainActionProcessor.process(action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataInfoTO findChannelDataInfoById(Long id)
            throws InstanceNotFoundException, InternalErrorException {
    	FindChannelDataInfoByIdAction action = new FindChannelDataInfoByIdAction(
                id);
        try {
            return PlainActionProcessor.process(action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataTO> findAllChannelsData()
            throws InternalErrorException {
        FindAllChannelsDataAction action = new FindAllChannelsDataAction();
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataDetailsTO> findAllChannelsDataDetails()
            throws InternalErrorException {
        FindAllChannelsDataDetailsAction action =
                new FindAllChannelsDataDetailsAction();
        try {
            return PlainActionProcessor.process(action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChunkTO<ChannelDataTO> findChannelsData(SearchOptions searchOptions)
            throws InternalErrorException {
        FindChannelsDataAction action =
                new FindChannelsDataAction(searchOptions);
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChunkTO<ChannelDataDetailsTO> findChannelsDataDetails(
            SearchOptions searchOptions)
            throws InternalErrorException {
        FindChannelsDataDetailsAction action =
                new FindChannelsDataDetailsAction(searchOptions);
        try {
            return PlainActionProcessor.process(action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataTO> findAllActiveChannelsData()
            throws InternalErrorException {
        FindAllActiveChannelsDataAction action = new FindAllActiveChannelsDataAction();
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataInfoTO> findAllChannelsDataInfo()
            throws InternalErrorException {
        FindAllChannelsDataInfoAction action = new FindAllChannelsDataInfoAction();
        try {
            return action.execute();
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataInfoTO> findActiveChannelsByVideoServerGroupsOriginAction(
            Collection<Long> videoServerGroupIds)
                    throws InternalErrorException{
        FindActiveChannelsByVideoServerGroupsOriginAction action =
                new FindActiveChannelsByVideoServerGroupsOriginAction(videoServerGroupIds);
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataTO> findAllChannelsDataIfRecordingActive()
            throws InternalErrorException {
        FindAllChannelsDataIfRecordingActiveAction action = new FindAllChannelsDataIfRecordingActiveAction();
        try {
            return action.execute();
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataTO createChannelData(ChannelDataTO channelDataTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        CreateChannelDataAction action = new CreateChannelDataAction(
                channelDataTO);
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (DuplicateInstanceException e) {
            throw e;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataDetailsTO createChannelDataDetails(
            ChannelDataDetailsTO channelDataDetailsTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        CreateChannelDataDetailsAction action = new CreateChannelDataDetailsAction(
                channelDataDetailsTO);
        try {
            return PlainActionProcessor.process(action);
        } catch (DuplicateInstanceException e) {
            throw e;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void updateChannelData(ChannelDataTO channelDataTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        UpdateChannelDataAction action = new UpdateChannelDataAction(
                channelDataTO);
        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (DuplicateChannelAudioLanguageInstanceException e) {
            throw e;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void updateChannelDataDetails(ChannelDataDetailsTO channelDataDetailsTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        UpdateChannelDataDetailsAction action = new UpdateChannelDataDetailsAction(
                channelDataDetailsTO);
        try {
            PlainActionProcessor.process(action);
        } catch (DuplicateChannelAudioLanguageInstanceException e) {
            throw e;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteChannelData(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        DeleteChannelDataAction action = new DeleteChannelDataAction(id);
        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteChannelDataByVodkatvChannelId(String vodkatvChannelId)
            throws InstanceNotFoundException, InternalErrorException {
        DeleteChannelDataByVodkatvChannelIdAction action =
                new DeleteChannelDataByVodkatvChannelIdAction(vodkatvChannelId);
        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteAllChannelDatas() throws InternalErrorException {
        DeleteAllChannelDatasAction action = new DeleteAllChannelDatasAction();
        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataTO> findActiveChannelsDataByCategory(
            Long categoryId) throws InternalErrorException {
        FindActiveChannelsDataByCategoryAction action =
                new FindActiveChannelsDataByCategoryAction(categoryId);
        try {
            return action.execute();
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataPlayInfoTO findChannelDataPlayInfoByUserAgent(
            ChannelDataTO channelDataTO, String userAgent)
                    throws InternalErrorException {
        FindChannelDataPlayInfoByUserAgentAction action =
                new FindChannelDataPlayInfoByUserAgentAction(channelDataTO,
                        userAgent);
        try {
            return action.execute();
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    /*
     * ChannelAccessArea
     */
    @Override
    public Collection<ChannelAccessAreaTO> findAllChannelAccessAreas()
            throws InternalErrorException {
        FindAllChannelAccessAreasAction action =
                new FindAllChannelAccessAreasAction();
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelAccessAreaTO> findChannelAccessAreasByVoDKATVChannelId(
            String vodkatvChannelId) throws InternalErrorException {
        FindChannelAccessAreasByVoDKATVChannelIdAction action =
                new FindChannelAccessAreasByVoDKATVChannelIdAction(
                        vodkatvChannelId);
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelNetworkAreasCustomTO> findChannelAccessAreasByTagTreeNodeId(
            Long tagTreeNodeId) throws InstanceNotFoundException,
            InternalErrorException {
        FindChannelAccessAreasByTagTreeNodeIdAction action =
                new FindChannelAccessAreasByTagTreeNodeIdAction(
                        tagTreeNodeId);
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InstanceNotFoundException infe) {
            throw infe;
        } catch (InternalErrorException iee) {
            throw iee;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void updateChannelAccessAreasByTagTreeNodeId(Long tagTreeNodeId,
            Collection<ChannelNetworkAreasCustomTO> channelAccessAreaTOs)
                    throws InstanceNotFoundException, InternalErrorException {
        UpdateChannelAccessAreasByTagTreeNodeIdAction action =
                new UpdateChannelAccessAreasByTagTreeNodeIdAction(
                        tagTreeNodeId, channelAccessAreaTOs);
        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (InstanceNotFoundException infe) {
            throw infe;
        } catch (InternalErrorException iee) {
            throw iee;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    /*
     * Video Codecs
     */
    @Override
    public Collection<VideoCodecTO> findAllVideoCodecs()
            throws InternalErrorException {
        FindAllVideoCodecsAction action = new FindAllVideoCodecsAction();
        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException infe) {
            throw infe;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public boolean existsVideoCodec(String codec) throws InternalErrorException {
        ExistsVideoCodecAction action = new ExistsVideoCodecAction(codec);

        try {
            return ((Boolean) PlainActionProcessor.process(getDataSource(),
                    action)).booleanValue();
        } catch (InternalErrorException infe) {
            throw infe;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    /*
     * Audio Codecs
     */
    @Override
    public Collection<AudioCodecTO> findAllAudioCodecs()
            throws InternalErrorException {
        FindAllAudioCodecsAction action = new FindAllAudioCodecsAction();

        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException infe) {
            throw infe;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public boolean existsAudioCodec(String codec) throws InternalErrorException {
        ExistsAudioCodecAction action = new ExistsAudioCodecAction(codec);

        try {
            return ((Boolean) PlainActionProcessor.process(getDataSource(),
                    action)).booleanValue();
        } catch (InternalErrorException infe) {
            throw infe;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    /*
     * WelcomeChannel
     */
    @Override
    public WelcomeChannelTO createWelcomeChannel(
            WelcomeChannelTO welcomeChannelTO)
                    throws DuplicateInstanceException, InternalErrorException {
        CreateWelcomeChannelAction action = new CreateWelcomeChannelAction(
                welcomeChannelTO);

        try {
            return (WelcomeChannelTO) PlainActionProcessor.process(
                    getDataSource(), action);
        } catch (DuplicateInstanceException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }

    }

    @Override
    public ChannelDataTO findWelcomeChannelByLocale(Locale locale)
            throws InstanceNotFoundException, InternalErrorException {
        FindWelcomeChannelByLocaleAction action = new FindWelcomeChannelByLocaleAction(
                locale);
        try {
            return (ChannelDataTO) PlainActionProcessor.process(
                    getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<WelcomeChannelDetailsTO> findWelcomeChannelsDetails()
            throws InternalErrorException {
        FindWelcomeChannelsDetailsAction action = new FindWelcomeChannelsDetailsAction();

        try {
            return PlainActionProcessor.process(getDataSource(), action);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteWelcomeChannel(Long welcomeChannelId)
            throws InstanceNotFoundException, InternalErrorException {
        DeleteWelcomeChannelAction action = new DeleteWelcomeChannelAction(
                welcomeChannelId);

        try {
            PlainActionProcessor.process(getDataSource(), action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

}
