package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache;

import java.util.Collection;

import com.lambdastream.tahiti.model.business.basicpackage.to.BasicPackageTO;
import com.lambdastream.tahiti.model.configuration.channelaccessarea.to.ChannelAccessAreaTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetworkAreasCustomTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelRedefinitionTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.WelcomeChannelDetailsTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.AudioCodecTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.VideoCodecTO;
import com.lambdastream.tahiti.model.configuration.channellist.to.ChannelListTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ChannelListDetailsTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.PropertiesConfigurationTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ProtectedTargetAudienceDetailsTO;
import com.lambdastream.tahiti.model.configuration.customizationpath.to.CustomizationPathTO;
import com.lambdastream.tahiti.model.configuration.defaultbasicpackage.to.DefaultBasicPackageTO;
import com.lambdastream.tahiti.model.configuration.filterlistconfiguration.to.FilterListConfigurationTO;
import com.lambdastream.tahiti.model.configuration.listconfiguration.to.ListConfigurationTO;
import com.lambdastream.tahiti.model.configuration.networkarea.to.NetworkAreaTO;
import com.lambdastream.tahiti.model.configuration.parentalrating.to.ParentalRatingTO;
import com.lambdastream.tahiti.model.configuration.player.to.PlayerConfigurationTO;
import com.lambdastream.tahiti.model.configuration.rentingvalidity.to.RentingValidityTO;
import com.lambdastream.tahiti.model.configuration.screensaver.to.ScreenSaverTO;
import com.lambdastream.tahiti.model.configuration.videoserver.to.VideoServerTO;
import com.lambdastream.tahiti.model.configuration.wowzaaessharedkey.to.WowzaAESSharedKeyTO;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.util.MediaChannelsUtils;
import com.lambdastream.tahiti.model.provisioning.channelslistfacade.cache.CacheChannelsListFacadeStorage;
import com.lambdastream.tahiti.model.util.constants.Constants;
import com.lambdastream.tahiti.model.util.notifier.ChangesNotifier;
import com.lambdastream.tahiti.model.util.notifier.DefaultChangesListener;
import com.lambdastream.util.cache.MapCache;
import com.lambdastream.util.cache.ReplicationCache;
import com.lambdastream.util.configuration.ConfigurationParametersManager;
import com.lambdastream.util.configuration.MissingConfigurationParameterException;
import com.lambdastream.util.exceptions.InternalErrorException;

public class CacheChannelConfigFacadeStorage extends DefaultChangesListener {

    private static CacheChannelConfigFacadeStorage instance;

    /*
     * Caches
     */
    private MapCache<Collection<ChannelRedefinitionTO>> defaultChannelsRedefinitions;
    private MapCache<ChannelDataTO> channelDataByVoDKATVChannelId;
    private MapCache<ChannelDataTO> channelDataById;
    private MapCache<Collection<String>> allVodkatvChannelIds;
    private MapCache<Collection<ChannelDataInfoTO>> channelDatasInfos;
    private MapCache<Collection<ChannelAccessAreaTO>> allChannelAccessAreas;
    private MapCache<Collection<ChannelAccessAreaTO>> channelAccessAreasByVoDKATVChannelId;
    private MapCache<Collection<ChannelNetworkAreasCustomTO>> channelAccessAreasByTagTreeNodeId;
    private MapCache<Collection<ChannelDataTO>> channelDatasByVideoServerGroupsOrigin;
    private MapCache<Collection<VideoCodecTO>> allVideoCodecs;
    private MapCache<Collection<AudioCodecTO>> allAudioCodecs;
    private MapCache<Boolean> masterNode;
    private MapCache<Collection<ParentalRatingTO>> ratingsOrder;
    private MapCache<Collection<ProtectedTargetAudienceDetailsTO>> protectedTargetAudiences;
    private MapCache<ListConfigurationTO> listConfigurationsMap;
    private MapCache<ListConfigurationTO> listConfigurationsByNameMap;
    private MapCache<Collection<ListConfigurationTO>> allListConfigurationsMap;
    private MapCache<Collection<FilterListConfigurationTO>> filterListConfigurationsMap;
    private MapCache<Collection<ChannelListTO>> allChannelListsMap;
    private MapCache<Collection<ChannelListTO>> allChannelListsByTypeMap;
    private MapCache<ChannelListTO> channelListsMap;
    private MapCache<ChannelListDetailsTO> channelListsDetailsMap;
    private MapCache<Collection<ChannelListDetailsTO>> allChannelListsDetailsMap;
    private MapCache<ChannelListDetailsTO> channelListDetailsByStbId;
    private MapCache<Collection<ChannelDataTO>> allChannelsDataIfRecordingActive;
    private MapCache<Collection<RentingValidityTO>> rentingValidities;
    private MapCache<RentingValidityTO> rentingValidityByRentingValidityId;
    private MapCache<ChannelDataTO> welcomeChannelsByLocale;
    private MapCache<Collection<WelcomeChannelDetailsTO>> welcomeChannelsDetails;
    private MapCache<Collection<PlayerConfigurationTO>> playerConfAll;
    private MapCache<PlayerConfigurationTO> playerConfByProtocol;
    private MapCache<PlayerConfigurationTO> playerConfByIdl;
    private MapCache<CustomizationPathTO> customizationByDeviceClassAndPath;
    private MapCache<Collection<NetworkAreaTO>> networkAreas;
    private MapCache<NetworkAreaTO> networkAreaById;
    private MapCache<NetworkAreaTO> networkAreaByIpAndCountryCode;
    private MapCache<Collection<ScreenSaverTO>> screenSavers;
    private MapCache<PropertiesConfigurationTO> propertiesConfiguration;
    private MapCache<BasicPackageTO> defaultBasicPackageByTagNodeId;
    private MapCache<DefaultBasicPackageTO> defaultBasicPackageConfigurationByTagNodeId;
    private MapCache<WowzaAESSharedKeyTO> wowzaAESSharedKey;

    private CacheChannelConfigFacadeStorage() {
        initMaps();
        ChangesNotifier.getInstance().registerListener(this);
    }

    public static CacheChannelConfigFacadeStorage getInstance() {
        if(instance == null) {
            instance = new CacheChannelConfigFacadeStorage();
        }
        return instance;
    }

    private void initMaps() {

        defaultChannelsRedefinitions = new MapCache<Collection<ChannelRedefinitionTO>>(
                "ConfigurationFacade-defaultChannelsRedefinitions", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        channelDataByVoDKATVChannelId = new MapCache<ChannelDataTO>(
                "ConfigurationFacade-channelDataByVoDKATVChannelId", 500,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        channelDataById = new MapCache<ChannelDataTO>(
                "ConfigurationFacade/channelDataById",
                Constants.NUM_CHANNELS, Constants.CACHE_TIMEOUT);

        allVodkatvChannelIds = new MapCache<Collection<String>>(
                "ConfigurationFacade-allVodkatvChannelIds", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        channelDatasInfos = new MapCache<Collection<ChannelDataInfoTO>>(
                "ConfigurationFacade-channelDatasInfos", 1, 10 * 60 * 1000,
                new ReplicationCache(true, true, true));

        allChannelAccessAreas = new MapCache<Collection<ChannelAccessAreaTO>>(
                "ConfigurationFacade/allChannelAccessAreas",
                1, Constants.CACHE_TIMEOUT);

        channelAccessAreasByVoDKATVChannelId = new MapCache<Collection<ChannelAccessAreaTO>>(
                "ConfigurationFacade/channelAccessAreasByVoDKATVChannelId",
                Constants.NUM_CHANNELS, Constants.CACHE_TIMEOUT);

        channelAccessAreasByTagTreeNodeId = new MapCache<Collection<ChannelNetworkAreasCustomTO>>(
                "ConfigurationFacade/channelAccessAreasByTagTreeNodeId",
                Constants.NUM_TAG_NODES, Constants.CACHE_TIMEOUT);

        channelDatasByVideoServerGroupsOrigin =
                new MapCache<Collection<ChannelDataTO>>(
                "ConfigurationFacade-channelDatasByVideoServerGroupsOrigin",
                1, 10 * 60 * 1000, new ReplicationCache(true, true, true));

        allVideoCodecs = new MapCache<Collection<VideoCodecTO>>(
                "ConfigurationFacade-allVideoCodecs", 1, 10 * 60 * 1000,
                new ReplicationCache(true, true, true));

        allAudioCodecs = new MapCache<Collection<AudioCodecTO>>(
                "ConfigurationFacade-allAudioCodecs", 1, 10 * 60 * 1000,
                new ReplicationCache(true, true, true));

        rentingValidities = new MapCache<Collection<RentingValidityTO>>(
                "ConfigurationFacade-rentingValidities", 1, 30 * 60 * 1000,
                new ReplicationCache(true, true, true));

        rentingValidityByRentingValidityId = new MapCache<RentingValidityTO>(
                "ConfigurationFacade-rentingValidityByRentingValidityId", 50,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        customizationByDeviceClassAndPath = new MapCache<CustomizationPathTO>(
                "ConfigurationFacade-customizationByDeviceClassAndPath", 10,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        networkAreas = new MapCache<Collection<NetworkAreaTO>>(
                "ConfigurationFacade-networkAreas", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        networkAreaById = new MapCache<NetworkAreaTO>(
                "ConfigurationFacade-networkAreaById", 500,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        networkAreaByIpAndCountryCode = new MapCache<NetworkAreaTO>(
                "ConfigurationFacade-networkAreaByIp", 500,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        screenSavers = new MapCache<Collection<ScreenSaverTO>>(
                "ConfigurationFacade-screenSavers", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        long masterNodeCacheTimeout = 0;
        try {
            long masterNodeCheckMs = new Long(
                    ConfigurationParametersManager
                    .getParameter("MasterNode/checkSeconds"))
            .longValue() * 1000;

            masterNodeCacheTimeout = masterNodeCheckMs / 2;
        } catch (NumberFormatException e1) {
            masterNodeCacheTimeout = 30 * 1000;
        } catch (MissingConfigurationParameterException e1) {
            masterNodeCacheTimeout = 30 * 1000;
        }

        masterNode = new MapCache<Boolean>("ConfigurationFacade-masterNode", 1,
                masterNodeCacheTimeout, new ReplicationCache(false, false,
                        false));

        ratingsOrder = new MapCache<Collection<ParentalRatingTO>>(
                "ConfigurationFacade-ratingsOrder", 1,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        protectedTargetAudiences = new MapCache<Collection<ProtectedTargetAudienceDetailsTO>>(
                "ConfigurationFacade-protectedTargetAudiences", 1,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        listConfigurationsMap = new MapCache<ListConfigurationTO>(
                "Configuration-listConfigurationsMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));
        listConfigurationsByNameMap = new MapCache<ListConfigurationTO>(
                "Configuration-listConfigurationsByNameMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        allListConfigurationsMap = new MapCache<Collection<ListConfigurationTO>>(
                "Configuration-allListConfigurationsMap", 100, -1);

        filterListConfigurationsMap = new MapCache<Collection<FilterListConfigurationTO>>(
                "Configuration-filterListConfigurationsMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        allChannelListsMap = new MapCache<Collection<ChannelListTO>>(
                "Configuration-allChannelListsMap",
                1, masterNodeCacheTimeout, new ReplicationCache(true, true,
                        true));
        allChannelListsByTypeMap = new MapCache<Collection<ChannelListTO>>(
                "Configuration-allChannelListsByTypeMap", 10,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));
        channelListsMap = new MapCache<ChannelListTO>(
                "Configuration-channelListsMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));
        channelListsDetailsMap = new MapCache<ChannelListDetailsTO>(
                "Configuration-channelListsDetailsMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        allChannelListsDetailsMap = new MapCache<Collection<ChannelListDetailsTO>>(
                "Configuration-allChannelListsDetailsMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        channelListDetailsByStbId = new MapCache<ChannelListDetailsTO>(
                "Configuration-channelListDetailsByStbIdMap", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        allChannelsDataIfRecordingActive = new MapCache<Collection<ChannelDataTO>>(
                "ConfigurationFacade-allChannelsDataIfRecordingActive", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        welcomeChannelsByLocale = new MapCache<ChannelDataTO>(
                "ConfigurationFacade-welcomeChannelsByLocale", 100,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        welcomeChannelsDetails = new MapCache<Collection<WelcomeChannelDetailsTO>>(
                "ConfigurationFacade-welcomeChannelsDetails", 1,
                masterNodeCacheTimeout, new ReplicationCache(true, true, true));

        playerConfAll = new MapCache<Collection<PlayerConfigurationTO>>(
                "ConfigurationFacade-playerConfAll", 1,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        playerConfByProtocol = new MapCache<PlayerConfigurationTO>(
                "ConfigurationFacade-playerConfByProtocol", 10,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        playerConfByIdl = new MapCache<PlayerConfigurationTO>(
                "ConfigurationFacade-playerConfByIdl", 10,
                10 * 60 * 1000, new ReplicationCache(true, true, true));

        propertiesConfiguration = new MapCache<PropertiesConfigurationTO>(
                "ConfigurationFacade-propertiesConfiguration", 1,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        defaultBasicPackageByTagNodeId = new MapCache<BasicPackageTO>(
                "ConfigurationFacade-defaultBasicPackageByTagNodeId", 100,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        defaultBasicPackageConfigurationByTagNodeId = new MapCache<DefaultBasicPackageTO>(
                "ConfigurationFacade-defaultBasicPackageConfigurationByTagNodeId", 50,
                30 * 60 * 1000, new ReplicationCache(true, true, true));

        wowzaAESSharedKey = new MapCache<WowzaAESSharedKeyTO>(
                "ConfigurationFacade/wowzaAESSharedKey",
                1, Constants.CACHE_TIMEOUT);

    }

    public MapCache<Collection<ChannelRedefinitionTO>> getDefaultChannelsRedefinitions() {
        return defaultChannelsRedefinitions;
    }

    public MapCache<ChannelDataTO> getChannelDataByVoDKATVChannelId() {
        return channelDataByVoDKATVChannelId;
    }

    public MapCache<ChannelDataTO> getChannelDataById() {
        return channelDataById;
    }

    public MapCache<Collection<String>> getAllVodkatvChannelIds() {
        return allVodkatvChannelIds;
    }

    public MapCache<Collection<ChannelDataInfoTO>> getChannelDatasInfos() {
        return channelDatasInfos;
    }

    public MapCache<Collection<ChannelAccessAreaTO>> getAllChannelAccessAreas() {
        return allChannelAccessAreas;
    }

    public MapCache<Collection<ChannelAccessAreaTO>> getChannelAccessAreasByVoDKATVChannelId() {
        return channelAccessAreasByVoDKATVChannelId;
    }

    public MapCache<Collection<ChannelNetworkAreasCustomTO>> getChannelAccessAreasByTagTreeNodeId() {
        return channelAccessAreasByTagTreeNodeId;
    }

    public MapCache<Collection<ChannelDataTO>> getChannelDatasByVideoServerGroupsOrigin() {
        return channelDatasByVideoServerGroupsOrigin;
    }

    public MapCache<Collection<VideoCodecTO>> getAllVideoCodecs() {
        return allVideoCodecs;
    }

    public MapCache<Collection<AudioCodecTO>> getAllAudioCodecs() {
        return allAudioCodecs;
    }

    public MapCache<Boolean> getMasterNode() {
        return masterNode;
    }

    public MapCache<Collection<ParentalRatingTO>> getRatingsOrder() {
        return ratingsOrder;
    }

    public MapCache<Collection<ProtectedTargetAudienceDetailsTO>> getProtectedTargetAudiences() {
        return protectedTargetAudiences;
    }

    public MapCache<ListConfigurationTO> getListConfigurationsMap() {
        return listConfigurationsMap;
    }

    public MapCache<ListConfigurationTO> getListConfigurationsByNameMap() {
        return listConfigurationsByNameMap;
    }

    public MapCache<Collection<ListConfigurationTO>> getAllListConfigurationsMap() {
        return allListConfigurationsMap;
    }

    public MapCache<Collection<FilterListConfigurationTO>> getFilterListConfigurationsMap() {
        return filterListConfigurationsMap;
    }

    public MapCache<Collection<ChannelListTO>> getAllChannelListsMap() {
        return allChannelListsMap;
    }

    public MapCache<Collection<ChannelListTO>> getAllChannelListsByTypeMap() {
        return allChannelListsByTypeMap;
    }

    public MapCache<ChannelListTO> getChannelListsMap() {
        return channelListsMap;
    }

    public MapCache<ChannelListDetailsTO> getChannelListsDetailsMap() {
        return channelListsDetailsMap;
    }

    public MapCache<Collection<ChannelListDetailsTO>> getAllChannelListsDetailsMap() {
        return allChannelListsDetailsMap;
    }

    public MapCache<ChannelListDetailsTO> getChannelListDetailsByStbId() {
        return channelListDetailsByStbId;
    }

    public MapCache<Collection<ChannelDataTO>> getAllChannelsDataIfRecordingActive() {
        return allChannelsDataIfRecordingActive;
    }

    public MapCache<Collection<RentingValidityTO>> getRentingValidities() {
        return rentingValidities;
    }

    public MapCache<RentingValidityTO> getRentingValidityByRentingValidityId() {
        return rentingValidityByRentingValidityId;
    }

    public MapCache<ChannelDataTO> getWelcomeChannelsByLocale() {
        return welcomeChannelsByLocale;
    }

    public MapCache<Collection<WelcomeChannelDetailsTO>> getWelcomeChannelsDetails() {
        return welcomeChannelsDetails;
    }

    public MapCache<Collection<PlayerConfigurationTO>> getPlayerConfAll() {
        return playerConfAll;
    }

    public MapCache<PlayerConfigurationTO> getPlayerConfByProtocol() {
        return playerConfByProtocol;
    }

    public MapCache<PlayerConfigurationTO> getPlayerConfByIdl() {
        return playerConfByIdl;
    }

    public MapCache<CustomizationPathTO> getCustomizationByDeviceClassAndPath() {
        return customizationByDeviceClassAndPath;
    }

    public MapCache<Collection<NetworkAreaTO>> getNetworkAreas() {
        return networkAreas;
    }

    public MapCache<NetworkAreaTO> getNetworkAreaById() {
        return networkAreaById;
    }

    public MapCache<NetworkAreaTO> getNetworkAreaByIpAndCountryCode() {
        return networkAreaByIpAndCountryCode;
    }

    public MapCache<Collection<ScreenSaverTO>> getScreenSavers() {
        return screenSavers;
    }

    public MapCache<PropertiesConfigurationTO> getPropertiesConfiguration() {
        return propertiesConfiguration;
    }

    public MapCache<BasicPackageTO> getDefaultBasicPackageByTagNodeId() {
        return defaultBasicPackageByTagNodeId;
    }

    public MapCache<DefaultBasicPackageTO> getDefaultBasicPackageConfigurationByTagNodeId() {
        return defaultBasicPackageConfigurationByTagNodeId;
    }

    public MapCache<WowzaAESSharedKeyTO> getWowzaAESSharedKey() {
        return wowzaAESSharedKey;
    }

    public void resetWowzaAESSharedKey() {
        wowzaAESSharedKey.clear();
    }

    public void resetChannelDatas() {
        channelDataByVoDKATVChannelId.clear();
        channelDataById.clear();
        allVodkatvChannelIds.clear();
        channelDatasInfos.clear();
        channelListDetailsByStbId.clear();
        allChannelsDataIfRecordingActive.clear();
        channelDatasByVideoServerGroupsOrigin.clear();
        welcomeChannelsByLocale.clear();
    }

    public void resetChannelLists() {
        channelListsMap.clear();
        channelListsDetailsMap.clear();
        channelListDetailsByStbId.clear();
        allChannelListsMap.clear();
        allChannelListsDetailsMap.clear();
        allChannelListsByTypeMap.clear();
        defaultChannelsRedefinitions.clear();
        MediaChannelsUtils.getInstance().refreshCache();
        CacheChannelsListFacadeStorage.getInstance().refreshChannels();
    }

    public void resetWelcomeChannelsCaches() {
        welcomeChannelsByLocale.clear();
        welcomeChannelsDetails.clear();
    }

    public void refreshCache() {
        defaultChannelsRedefinitions.clear();
        channelDataByVoDKATVChannelId.clear();
        channelDataById.clear();
        allVodkatvChannelIds.clear();
        channelDatasInfos.clear();
        allVideoCodecs.clear();
        allAudioCodecs.clear();
        masterNode.clear();
        ratingsOrder.clear();
        protectedTargetAudiences.clear();
        listConfigurationsMap.clear();
        listConfigurationsByNameMap.clear();
        allListConfigurationsMap.clear();
        filterListConfigurationsMap.clear();
        allChannelListsMap.clear();
        allChannelListsByTypeMap.clear();
        channelListsMap.clear();
        channelListsDetailsMap.clear();
        allChannelListsDetailsMap.clear();
        channelListDetailsByStbId.clear();
        allChannelsDataIfRecordingActive.clear();
        rentingValidities.clear();
        rentingValidityByRentingValidityId.clear();
        welcomeChannelsByLocale.clear();
        welcomeChannelsDetails.clear();
        playerConfAll.clear();
        playerConfByProtocol.clear();
        playerConfByIdl.clear();
        customizationByDeviceClassAndPath.clear();
        networkAreas.clear();
        networkAreaById.clear();
        networkAreaByIpAndCountryCode.clear();
        screenSavers.clear();
        propertiesConfiguration.clear();
        defaultBasicPackageByTagNodeId.clear();
        defaultBasicPackageConfigurationByTagNodeId.clear();
        wowzaAESSharedKey.clear();
        channelDatasByVideoServerGroupsOrigin.clear();
    }

    /*
     * Because this bond:
     * ChannelData > ChannelDataPlayInfo > VideoServerGroups > VideoServer
     * It's necessary to refresh ChannelData caches when updating a video server
     */
    @Override
    public void onUpdateVideoServer(VideoServerTO videoServerTO)
            throws InternalErrorException {
        resetChannelDatas();
    }

}
