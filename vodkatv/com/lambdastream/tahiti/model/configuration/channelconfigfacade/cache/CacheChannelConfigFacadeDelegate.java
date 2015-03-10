package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindAllAudioCodecsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindAllChannelAccessAreasAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindAllChannelsDataIfRecordingActiveAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindAllChannelsDataInfoAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindAllVideoCodecsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindChannelAccessAreasByTagTreeNodeIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindChannelAccessAreasByVoDKATVChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindChannelDataByIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindChannelDataByVoDKATVChannelIdAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindDefaultChannelsRedefinitionsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindWelcomeChannelByLocaleAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions.FindWelcomeChannelsDetailsAction;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
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
import com.lambdastream.tahiti.model.util.constants.Constants;
import com.lambdastream.tahiti.model.util.exceptions.DuplicateChannelAudioLanguageInstanceException;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.cache.CacheProcessor;
import com.lambdastream.util.cache.DummyTimestampTO;
import com.lambdastream.util.cache.ExpiredDataTimestampException;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.utils.ChunkTO;
import com.lambdastream.util.utils.ChunkUtils;

public class CacheChannelConfigFacadeDelegate implements
        ChannelConfigFacadeDelegate {

    private ChannelConfigFacadeDelegate delegate;
    private CacheChannelConfigFacadeStorage storage;

    public CacheChannelConfigFacadeDelegate(
            ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
        this.storage = CacheChannelConfigFacadeStorage.getInstance();
    }

    /*
     * Channels redefinition
     */
    @Override
    public Collection<ChannelRedefinitionTO> findDefaultChannelsRedefinitions()
            throws InternalErrorException {
        FindDefaultChannelsRedefinitionsAction action =
                new FindDefaultChannelsRedefinitionsAction(delegate);
        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(
                    storage.getDefaultChannelsRedefinitions(), key, action, null);
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
        FindChannelDataByVoDKATVChannelIdAction action =
                new FindChannelDataByVoDKATVChannelIdAction(delegate,
                        vodkatvChannelId);
        final String key = vodkatvChannelId;

        try {
            return CacheProcessor.process(storage.getChannelDataByVoDKATVChannelId(),
                    key, action, null);
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
    	return delegate.findChannelDataInfoByVoDKATVChannelId(vodkatvChannelId);
    }

    @Override
    public ChannelDataTO findChannelDataById(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        FindChannelDataByIdAction action =
                new FindChannelDataByIdAction(delegate, id);
        final String key = id.toString();

        try {
            return CacheProcessor.process(storage.getChannelDataById(),
                    key, action, null);
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
        return delegate.findChannelDataDetailsById(id);
    }

    @Override
    public ChannelDataInfoTO findChannelDataInfoById(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        return delegate.findChannelDataInfoById(id);
    }

    @Override
    public Collection<ChannelDataTO> findAllChannelsData()
            throws InternalErrorException {

        final String key = Constants.CACHE_KEY_ONE_ELEMENT;
        boolean incomplete = false;

        Collection<ChannelDataTO> channelDataTOs = new ArrayList<ChannelDataTO>();
        try {
            DummyTimestampTO<Collection<String>> allVodkatvChannelIdsTimestampTO =
                    (DummyTimestampTO<Collection<String>>)storage.getAllVodkatvChannelIds().get(
                            Constants.CACHE_KEY_ONE_ELEMENT);
            Collection<String> vodkatvChannelIds = allVodkatvChannelIdsTimestampTO.getData();
            for(String vodkatvChannelId : vodkatvChannelIds) {
                try {
                    DummyTimestampTO<ChannelDataTO> channelDataTimestampTO =
                            (DummyTimestampTO<ChannelDataTO>)storage.getChannelDataByVoDKATVChannelId().get(
                                    vodkatvChannelId);
                    channelDataTOs.add(channelDataTimestampTO.getData());
                } catch (InstanceNotFoundException e) {
                    incomplete = true;
                    break;
                } catch (ExpiredDataTimestampException e) {
                    incomplete = true;
                    break;
                }
            }
        } catch (InstanceNotFoundException e) {
            incomplete = true;
        } catch (ExpiredDataTimestampException e) {
            incomplete = true;
        }

        if(incomplete) {
            channelDataTOs = delegate.findAllChannelsData();

            /*
             * Fill cache manually
             */
            Collection<String> vodkatvChannelIds = new ArrayList<String>();
            for(ChannelDataTO channelDataTO : channelDataTOs) {
                vodkatvChannelIds.add(channelDataTO.getVodkatvChannelId());
                storage.getChannelDataByVoDKATVChannelId().put(
                        channelDataTO.getVodkatvChannelId(),
                        new DummyTimestampTO<ChannelDataTO>(channelDataTO));
            }

            storage.getAllVodkatvChannelIds().put(key,
                    new DummyTimestampTO<Collection<String>>(vodkatvChannelIds));
        }

        return channelDataTOs;

    }

    @Override
    public Collection<ChannelDataDetailsTO> findAllChannelsDataDetails()
            throws InternalErrorException {
        return delegate.findAllChannelsDataDetails();
    }

    @Override
    public ChunkTO<ChannelDataTO> findChannelsData(SearchOptions searchOptions)
            throws InternalErrorException {
        Collection<ChannelDataTO> channelDataTOs = findAllChannelsData();
        if(searchOptions.getStartIndex() != null && searchOptions.getCount() != null) {
            return ChunkUtils.getChunk(channelDataTOs,
                    searchOptions.getStartIndex().intValue(),
                    searchOptions.getCount().intValue());
        } else {
            return ChunkUtils.getChunk(channelDataTOs, 1, channelDataTOs.size());
        }
    }

    @Override
    public ChunkTO<ChannelDataDetailsTO> findChannelsDataDetails(
            SearchOptions searchOptions)
            throws InternalErrorException {
        return delegate.findChannelsDataDetails(searchOptions);
    }

    @Override
    public Collection<ChannelDataTO> findAllActiveChannelsData()
            throws InternalErrorException {
        return delegate.findAllActiveChannelsData();
    }

    @Override
    public Collection<ChannelDataInfoTO> findAllChannelsDataInfo()
            throws InternalErrorException {
        FindAllChannelsDataInfoAction action = new FindAllChannelsDataInfoAction(
                delegate);
        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getChannelDatasInfos(),
                    key, action, null);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public Collection<ChannelDataInfoTO> findActiveChannelsByVideoServerGroupsOriginAction(
            Collection<Long> videoServerGroupIds) throws InternalErrorException{
        return delegate.findActiveChannelsByVideoServerGroupsOriginAction(
        		videoServerGroupIds);
    }

    @Override
    public Collection<ChannelDataTO> findAllChannelsDataIfRecordingActive()
            throws InternalErrorException {
        FindAllChannelsDataIfRecordingActiveAction action =
                new FindAllChannelsDataIfRecordingActiveAction(delegate);

        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(
                    storage.getAllChannelsDataIfRecordingActive(), key, action,
                    null);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public ChannelDataTO createChannelData(ChannelDataTO channelDataTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException {
    	ChannelDataTO createdChannelDataTO = delegate.createChannelData(
                channelDataTO);
        storage.resetChannelDatas();
        return createdChannelDataTO;
    }

    public ChannelDataDetailsTO createChannelDataDetails(
            ChannelDataDetailsTO channelDataDetailsTO)
            throws DuplicateInstanceException, InstanceNotFoundException,
            InternalErrorException {
        return delegate.createChannelDataDetails(channelDataDetailsTO);
    }

    @Override
    public void updateChannelData(ChannelDataTO channelDataTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        delegate.updateChannelData(channelDataTO);
        storage.resetChannelDatas();
    }

    public void updateChannelDataDetails(ChannelDataDetailsTO channelDataDetailsTO)
            throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException {
        delegate.updateChannelDataDetails(channelDataDetailsTO);
    }

    @Override
    public void deleteChannelData(Long id)
            throws InstanceNotFoundException, InternalErrorException {
        delegate.deleteChannelData(id);
        storage.resetChannelDatas();
    }

    @Override
    public void deleteChannelDataByVodkatvChannelId(String vodkatvChannelId)
            throws InstanceNotFoundException, InternalErrorException {
        delegate.deleteChannelDataByVodkatvChannelId(vodkatvChannelId);
        storage.resetChannelDatas();
    }

    @Override
    public void deleteAllChannelDatas() throws InternalErrorException {
        delegate.deleteAllChannelDatas();
        storage.resetChannelDatas();
    }

    @Override
    public Collection<ChannelDataTO> findActiveChannelsDataByCategory(
            Long categoryId) throws InternalErrorException {
        return delegate.findActiveChannelsDataByCategory(categoryId);
    }

    @Override
    public ChannelDataPlayInfoTO findChannelDataPlayInfoByUserAgent(
            ChannelDataTO channelDataTO, String userAgent)
                    throws InternalErrorException {
        return delegate.findChannelDataPlayInfoByUserAgent(channelDataTO,
                userAgent);
    }

    /*
     * ChannelAccessArea
     */
    @Override
    public Collection<ChannelAccessAreaTO> findAllChannelAccessAreas()
            throws InternalErrorException {
        FindAllChannelAccessAreasAction action = new FindAllChannelAccessAreasAction(
                delegate);
        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getAllChannelAccessAreas(), key,
                    action, null);
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
                new FindChannelAccessAreasByVoDKATVChannelIdAction(delegate,
                        vodkatvChannelId);
        final String key = vodkatvChannelId;

        try {
            return CacheProcessor.process(
                    storage.getChannelAccessAreasByVoDKATVChannelId(), key,
                    action, null);
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
                new FindChannelAccessAreasByTagTreeNodeIdAction(delegate,
                        tagTreeNodeId);
        final String key = String.valueOf(tagTreeNodeId);

        try {
            return CacheProcessor.process(
                    storage.getChannelAccessAreasByTagTreeNodeId(), key,
                    action, null);
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
        delegate.updateChannelAccessAreasByTagTreeNodeId(
                tagTreeNodeId, channelAccessAreaTOs);
        storage.getChannelAccessAreasByTagTreeNodeId().remove(
                String.valueOf(tagTreeNodeId));;
    }

    /*
     * Video Codecs
     */
    @Override
    public Collection<VideoCodecTO> findAllVideoCodecs()
            throws InternalErrorException {
        FindAllVideoCodecsAction action = new FindAllVideoCodecsAction(delegate);
        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getAllVideoCodecs(), key,
                    action, null);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public boolean existsVideoCodec(String codec) throws InternalErrorException {
        return delegate.existsVideoCodec(codec);
    }

    /*
     * Audio Codecs
     */
    @Override
    public Collection<AudioCodecTO> findAllAudioCodecs()
            throws InternalErrorException {
        FindAllAudioCodecsAction action = new FindAllAudioCodecsAction(
                delegate);
        final String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getAllAudioCodecs(), key,
                    action, null);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public boolean existsAudioCodec(String codec)
            throws InternalErrorException {
        return delegate.existsAudioCodec(codec);
    }

    /*
     * WelcomeChannel
     */
    @Override
    public WelcomeChannelTO createWelcomeChannel(
            WelcomeChannelTO welcomeChannelTO)
                    throws DuplicateInstanceException, InternalErrorException {
        WelcomeChannelTO result = delegate
                .createWelcomeChannel(welcomeChannelTO);
        storage.resetWelcomeChannelsCaches();
        return result;
    }

    @Override
    public ChannelDataTO findWelcomeChannelByLocale(Locale locale)
            throws InstanceNotFoundException, InternalErrorException {
        FindWelcomeChannelByLocaleAction action = new FindWelcomeChannelByLocaleAction(
                locale, delegate);

        String key = locale != null ? locale.toString() : Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getWelcomeChannelsByLocale(),
                    key, action, null);
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
        FindWelcomeChannelsDetailsAction action =
                new FindWelcomeChannelsDetailsAction(delegate);
        String key = Constants.CACHE_KEY_ONE_ELEMENT;

        try {
            return CacheProcessor.process(storage.getWelcomeChannelsDetails(),
                    key, action, null);
        } catch (InternalErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void deleteWelcomeChannel(Long welcomeChannelId)
            throws InstanceNotFoundException, InternalErrorException {
        delegate.deleteWelcomeChannel(welcomeChannelId);
        storage.resetWelcomeChannelsCaches();
    }


}
