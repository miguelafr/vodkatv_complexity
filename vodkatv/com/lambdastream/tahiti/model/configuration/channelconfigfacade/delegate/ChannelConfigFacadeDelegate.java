package com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate;

import java.util.Collection;
import java.util.Locale;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
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
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.utils.ChunkTO;

public interface ChannelConfigFacadeDelegate {

    /*
     * Channels redefinition
     */
    @Deprecated
    public Collection<ChannelRedefinitionTO> findDefaultChannelsRedefinitions()
            throws InternalErrorException;

    /*
     * ChannelData
     */
    public ChannelDataTO findChannelDataByVoDKATVChannelId(
            String vodkatvChannelId) throws InstanceNotFoundException,
            InternalErrorException;

    public ChannelDataInfoTO findChannelDataInfoByVoDKATVChannelId(
            String vodkatvChannelId) throws InstanceNotFoundException,
            InternalErrorException;

    public ChannelDataTO findChannelDataById(Long id)
            throws InstanceNotFoundException, InternalErrorException;

    public ChannelDataDetailsTO findChannelDataDetailsById(Long id)
            throws InstanceNotFoundException, InternalErrorException;

    public ChannelDataInfoTO findChannelDataInfoById(Long id)
            throws InstanceNotFoundException, InternalErrorException;

    public Collection<ChannelDataTO> findAllChannelsData()
            throws InternalErrorException;

    public Collection<ChannelDataDetailsTO> findAllChannelsDataDetails()
            throws InternalErrorException;

    public ChunkTO<ChannelDataTO> findChannelsData(SearchOptions searchOptions)
            throws InternalErrorException;

    public ChunkTO<ChannelDataDetailsTO> findChannelsDataDetails(
            SearchOptions searchOptions)
            throws InternalErrorException;

    public Collection<ChannelDataTO> findAllActiveChannelsData()
            throws InternalErrorException;

    public Collection<ChannelDataInfoTO> findAllChannelsDataInfo()
            throws InternalErrorException;

    public Collection<ChannelDataInfoTO> findActiveChannelsByVideoServerGroupsOriginAction(
            Collection<Long> videoServerGroupIds) throws InternalErrorException;

    public Collection<ChannelDataTO> findAllChannelsDataIfRecordingActive()
            throws InternalErrorException;

    public ChannelDataTO createChannelData(ChannelDataTO channelDataTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException;

    public ChannelDataDetailsTO createChannelDataDetails(
            ChannelDataDetailsTO channelDataDetailsTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException;

    public void updateChannelData(ChannelDataTO channelDataTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException;

    public void updateChannelDataDetails(ChannelDataDetailsTO channelDataDetailsTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException;

    public void deleteChannelData(Long id)
            throws InstanceNotFoundException, InternalErrorException;

    public void deleteChannelDataByVodkatvChannelId(String vodkatvChannelId)
            throws InstanceNotFoundException, InternalErrorException;

    public void deleteAllChannelDatas() throws InternalErrorException;

    public Collection<ChannelDataTO> findActiveChannelsDataByCategory(
            Long categoryId) throws InternalErrorException;

    public ChannelDataPlayInfoTO findChannelDataPlayInfoByUserAgent(
            ChannelDataTO channelDataTO, String userAgent)
                    throws InternalErrorException;

    /*
     * ChannelAccessArea
     */
    public Collection<ChannelAccessAreaTO> findAllChannelAccessAreas()
            throws InternalErrorException;

    public Collection<ChannelAccessAreaTO> findChannelAccessAreasByVoDKATVChannelId(
            String vodkatvChannelId) throws InternalErrorException;

    public Collection<ChannelNetworkAreasCustomTO> findChannelAccessAreasByTagTreeNodeId(
            Long tagTreeNodeId)
    throws InstanceNotFoundException, InternalErrorException;

    public void updateChannelAccessAreasByTagTreeNodeId(
            Long tagTreeNodeId,
            Collection<ChannelNetworkAreasCustomTO> channelAccessAreaTOs)
    throws InstanceNotFoundException, InternalErrorException;

    /*
     * Video Codecs
     */
    public Collection<VideoCodecTO> findAllVideoCodecs()
            throws InternalErrorException;

    public boolean existsVideoCodec(String codec)
            throws InternalErrorException;
    /*
     * Audio Codecs
     */
    public Collection<AudioCodecTO> findAllAudioCodecs()
            throws InternalErrorException;

    public boolean existsAudioCodec(String codec)
            throws InternalErrorException;

    /*
     * WelcomeChannel
     */
    public WelcomeChannelTO createWelcomeChannel(
            WelcomeChannelTO welcomeChannelTO)
                    throws DuplicateInstanceException, InternalErrorException;

    public ChannelDataTO findWelcomeChannelByLocale(Locale locale)
            throws InstanceNotFoundException, InternalErrorException;

    public Collection<WelcomeChannelDetailsTO> findWelcomeChannelsDetails()
            throws InternalErrorException;

    public void deleteWelcomeChannel(Long welcomeChannelId)
            throws InstanceNotFoundException, InternalErrorException;

}
