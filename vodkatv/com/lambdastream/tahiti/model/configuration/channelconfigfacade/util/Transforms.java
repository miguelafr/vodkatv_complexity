package com.lambdastream.tahiti.model.configuration.channelconfigfacade.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdastream.cas.connector.casconnectorfacade.delegate.CASConnectorFacadeDelegate;
import com.lambdastream.cas.connector.casconnectorfacade.delegate.CASConnectorFacadeDelegateFactory;
import com.lambdastream.cas.connector.casconnectorfacade.to.ChannelCASTO;
import com.lambdastream.metadata.connector.channelsfacade.delegate.ChannelsFacadeDelegate;
import com.lambdastream.metadata.connector.channelsfacade.delegate.ChannelsFacadeDelegateFactory;
import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.sertext.connector.textchannel.to.TextChannelTO;
import com.lambdastream.tahiti.model.configuration.categoryconfigfacade.delegate.CategoryConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.categoryconfigfacade.delegate.CategoryConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataPlayInfoDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelMetaserversTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelNetPVRTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelRedefinitionTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelAccessType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelCategoryTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRPauseSupportType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRStartOverSupportType;
import com.lambdastream.tahiti.model.configuration.channellistitem.to.ChannelListItemTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ChannelListItemDetailsTO;
import com.lambdastream.tahiti.model.configuration.contentcategory.to.ContentCategoryTO;
import com.lambdastream.tahiti.model.configuration.parentalrating.to.ParentalRatingTO;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegate;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegateFactory;
import com.lambdastream.tahiti.model.media.teletextfacade.delegate.TeletextFacadeDelegate;
import com.lambdastream.tahiti.model.media.teletextfacade.delegate.TeletextFacadeDelegateFactory;
import com.lambdastream.tahiti.model.provisioning.devicetype.to.DeviceTypeTO;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegate;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegateFactory;
import com.lambdastream.tahiti.model.util.naming.GlobalNames;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;


public class Transforms {

	private static final Logger logger = LoggerFactory.getLogger(Transforms.class);

	public static Collection<ChannelRedefinitionTO> transform(
			Collection<ChannelListItemDetailsTO> channelListItemDetailsTOs) {

		Collection<ChannelRedefinitionTO> result =
				new ArrayList<ChannelRedefinitionTO>();

		for (ChannelListItemDetailsTO channelListItemDetailsTO : channelListItemDetailsTOs) {

			ChannelListItemTO channelListItemTO =
					channelListItemDetailsTO.getChannelListItemTO();
			ChannelDataTO channelDataTO =
					channelListItemDetailsTO.getChannelDataTO();

			/*
			 * If the channel is not visible, do not add it to the list.
			 */
			 if (channelListItemTO.getVisible()) {
				 ChannelRedefinitionTO channelRedefinitionTO =
						 new ChannelRedefinitionTO(channelListItemTO.getId(),
								 new Long(-1),
								 channelDataTO.getVodkatvChannelId(),
								 new Integer(channelListItemTO.getChannelNumber()),
								 null,
								 null,
								 true);
				 result.add(channelRedefinitionTO);
			 }
		}

		return result;

	}

	public static Collection<ChannelDataTO> toChannelDataTOs(
			Collection<ChannelDataDetailsTO> channelDataDetailsTOs) {

		Collection<ChannelDataTO> result = new ArrayList<ChannelDataTO>();

		for(ChannelDataDetailsTO channelDataDetailsTO : channelDataDetailsTOs) {

			/*
			 * Basic data
			 */
			Long id = channelDataDetailsTO.getId();
			String vodkatvChannelId = channelDataDetailsTO.getVodkatvChannelId();
			String channelType = channelDataDetailsTO.getChannelType();
			String channelName = channelDataDetailsTO.getName();
			String channelDescription = channelDataDetailsTO.getDescription();
			String logoUrl = channelDataDetailsTO.getLogoUrl();
			String languageCode = channelDataDetailsTO.getLanguageCode();
			String countryCode = channelDataDetailsTO.getCountryCode();
			ChannelAccessType accessType = channelDataDetailsTO.getAccessType();
			boolean active = channelDataDetailsTO.isActive();

			/*
			 * Parental rating
			 */
			Long parentalRatingId = null;
			if(channelDataDetailsTO.getParentalRatingTO() != null) {
				parentalRatingId = channelDataDetailsTO.getParentalRatingTO().getId();
			}

			/*
			 * MetaServers
			 */
			String channelId = null;
			String textChannelId = null;
			String casChannelId = null;
			Integer availableEPGHours = null;
			String hbbtvUrl = null;
			if(channelDataDetailsTO.getMetaserversTO() != null) {
				ChannelMetaserversTO metaServersTOs =
						channelDataDetailsTO.getMetaserversTO();
				if(metaServersTOs.getChannelTO() != null) {
					channelId = metaServersTOs.getChannelTO().getChannelId();
				}
				availableEPGHours = metaServersTOs.getAvailableEPGHours();
				if(metaServersTOs.getTextChannelTO() != null) {
					textChannelId = metaServersTOs.getTextChannelTO().getId();
				}
				if(metaServersTOs.getChannelCASTO() != null) {
					casChannelId = metaServersTOs.getChannelCASTO().getChannelId();
				}
				hbbtvUrl = metaServersTOs.getHbbtvUrl();
			}

			/*
			 * NETPVR
			 */
			 NetPVRPauseSupportType netPVRPauseSupported = null;
			Integer pauseLiveTVTGapTimeStart = null;
			Integer pauseLiveTVTGapTimeEnd = null;
			NetPVRStartOverSupportType netPVRStartOverSupported = null;
			Integer startOverGapTimeStart = null;
			Integer startOverGapTimeEnd = null;
			String netPVRPreRollAssetId = null;
			Integer netPVRFragmentSize = null;
			Integer netPVRRecordTime = null;
			Integer startOverDefaultTimeStart = null;
			Integer startOverDefaultTimeEnd = null;
			if(channelDataDetailsTO.getNetPVRTO() != null) {
				ChannelNetPVRTO netPVRTO = channelDataDetailsTO.getNetPVRTO();
				netPVRPauseSupported = netPVRTO.getNetPVRPauseSupported();
				pauseLiveTVTGapTimeStart = netPVRTO.getPauseLiveTVTGapTimeStart();
				pauseLiveTVTGapTimeEnd = netPVRTO.getPauseLiveTVTGapTimeEnd();
				netPVRStartOverSupported = netPVRTO.getNetPVRStartOverSupported();
				startOverGapTimeStart = netPVRTO.getStartOverGapTimeStart();
				startOverGapTimeEnd = netPVRTO.getStartOverGapTimeEnd();
				netPVRPreRollAssetId = netPVRTO.getNetPVRPreRollAssetId();
				netPVRFragmentSize = netPVRTO.getNetPVRFragmentSize();
				netPVRRecordTime = netPVRTO.getNetPVRRecordTime();
				startOverDefaultTimeStart = netPVRTO.getStartOverDefaultTimeStart();
				startOverDefaultTimeEnd = netPVRTO.getStartOverDefaultTimeEnd();
			}

			/*
			 * PVR
			 */
			 boolean pvrSupported = channelDataDetailsTO.isPvrSupported();

			/*
			 * Local PVR
			 */
			 Boolean localPVRSupported = channelDataDetailsTO.getLocalPVRSupported();

			 /*
			  * Categories
			  */
			 Collection<ChannelCategoryTO> channelCategoryTOs = null;
			 if(channelDataDetailsTO.getCategoryTOs() != null) {
				 channelCategoryTOs = new ArrayList<ChannelCategoryTO>();
				 for(ContentCategoryTO categoryTO :
					 channelDataDetailsTO.getCategoryTOs()) {
					 channelCategoryTOs.add(new ChannelCategoryTO(null,
							 vodkatvChannelId,
							 categoryTO.getCategoryId()));
				 }
			 }

			 /*
			  * PlayInfo
			  */
			  Collection<ChannelDataPlayInfoTO> channelDataPlayInfoTOs =
			  null;
			 if(channelDataDetailsTO.getPlayInfoTOs() != null) {
				 channelDataPlayInfoTOs = new ArrayList<ChannelDataPlayInfoTO>();

				 int position = 0;
				 for(ChannelDataPlayInfoDetailsTO channelDataPlayInfoDetailsTO :
					 channelDataDetailsTO.getPlayInfoTOs()) {

					 Long deviceTypeId = null;
					 if(channelDataPlayInfoDetailsTO.getDeviceTypeTO() != null) {
						 deviceTypeId = channelDataPlayInfoDetailsTO.getDeviceTypeTO().getDeviceTypeId();
					 }

					 channelDataPlayInfoTOs.add(new ChannelDataPlayInfoTO(
							 channelDataPlayInfoDetailsTO.getChannelDataPlayInfoId(),
							 vodkatvChannelId,
							 deviceTypeId,
							 position,
							 channelDataPlayInfoDetailsTO.getChannelUrl(),
							 channelDataPlayInfoDetailsTO.getAclChannelId(),
							 channelDataPlayInfoDetailsTO.getAclChannelProtocol(),
							 channelDataPlayInfoDetailsTO.getPreBufferMillis(),
							 channelDataPlayInfoDetailsTO.getNetPVRUrl(),
							 channelDataPlayInfoDetailsTO.getNetPVRChannelName(),
							 channelDataPlayInfoDetailsTO.getPreBufferNetPVRMillis(),
							 channelDataPlayInfoDetailsTO.getChannelVideoPidTOs(),
							 channelDataPlayInfoDetailsTO.getChannelAudioPidTOs(),
							 channelDataPlayInfoDetailsTO.getChannelSourceURIs(),
							 channelDataPlayInfoDetailsTO.getStreamName(),
							 channelDataPlayInfoDetailsTO.getVideoServerGroupsOrigin(),
							 channelDataPlayInfoDetailsTO.getVideoServerGroupsEdge()));
					 position++;
				 }
			 }

			 result.add(new ChannelDataTO(id, vodkatvChannelId, channelId, channelType,
					 channelName, channelDescription, logoUrl, languageCode,
					 countryCode, parentalRatingId, active, textChannelId,
					 netPVRPauseSupported, casChannelId, pauseLiveTVTGapTimeStart,
					 pauseLiveTVTGapTimeEnd, netPVRStartOverSupported,
					 startOverGapTimeStart, startOverGapTimeEnd,
					 netPVRPreRollAssetId, netPVRFragmentSize,
					 netPVRRecordTime, startOverDefaultTimeStart,
					 startOverDefaultTimeEnd, pvrSupported, localPVRSupported,
					 accessType, channelCategoryTOs, availableEPGHours, hbbtvUrl,
					 channelDataPlayInfoTOs));
		}

		return result;
	}

	public static Collection<ChannelDataDetailsTO> toChannelDataDetailsTOs(
			Collection<ChannelDataTO> channelDataTOs)
					throws InternalErrorException {

		Collection<ChannelDataDetailsTO> result = new ArrayList<ChannelDataDetailsTO>();

		MediaChannelsFacadeDelegate mediaChannelsFacadeDelegate =
				MediaChannelsFacadeDelegateFactory.getDelegate();
		ConfigurationFacadeDelegate configurationFacadeDelegate =
				ConfigurationFacadeDelegateFactory.getDelegate();
		CategoryConfigFacadeDelegate categoryConfigFacadeDelegate =
				CategoryConfigFacadeDelegateFactory.getDelegate();
		MappingFacadeDelegate mappingFacadeDelegate =
				MappingFacadeDelegateFactory.getDelegate();

		/*
		 * Text channels
		 */
		 Map<String, TextChannelTO> textChannels = null;
		 Map<String, ChannelCASTO> casChannels = null;

		 for(ChannelDataTO channelDataTO : channelDataTOs) {

			 /*
			  * Basic data
			  */
			 Long id = channelDataTO.getId();
			 String vodkatvChannelId = channelDataTO.getVodkatvChannelId();
			 String channelType = channelDataTO.getChannelType();
			 String name = channelDataTO.getChannelName();
			 String description = channelDataTO.getChannelDescription();
			 String logoUrl = channelDataTO.getLogoUrl();
			 String languageCode = channelDataTO.getLanguageCode();
			 String countryCode = channelDataTO.getCountryCode();
			 ChannelAccessType accessType = channelDataTO.getAccessType();
			 boolean active = channelDataTO.isActive();

			 /*
			  * Parental rating
			  */
			 ParentalRatingTO parentalRatingTO = null;
			 if(channelDataTO.getParentalRatingId() != null) {
				 try {
					 parentalRatingTO = configurationFacadeDelegate.findParentalRatingById(
							 channelDataTO.getParentalRatingId());
				 } catch (InstanceNotFoundException e) {
					 ;
				 }
			 }

			 /*
			  * MetaServers
			  */
			  ChannelTO channelTO = null;
			 if(channelDataTO.getChannelId() != null) {
				 try {
					 channelTO = mediaChannelsFacadeDelegate.findChannelById(
							 channelDataTO.getChannelId());
				 } catch (InstanceNotFoundException e) {
					 ;
				 }
			 }

			 TextChannelTO textChannelTO = null;
			 if(channelDataTO.getTextChannelId() != null) {
				 if(textChannels == null) {
					 textChannels = getTextChannels();
				 }
				 textChannelTO = textChannels.get(channelDataTO.getTextChannelId());
			 }


			 ChannelCASTO channelCASTO = null;
			 if(channelDataTO.getCasChannelId() != null) {
				 if(casChannels == null) {
					 casChannels = getCASChannels();
				 }
				 channelCASTO = casChannels.get(channelDataTO.getCasChannelId());
			 }

			 Integer availableEPGHours = channelDataTO.getAvailableEPGHours();
			 String hbbtvUrl = channelDataTO.getHbbtvUrl();
			 ChannelMetaserversTO metaserversTO = new ChannelMetaserversTO(
					 channelTO, textChannelTO, channelCASTO, availableEPGHours,
					 hbbtvUrl);

			 /*
			  * NETPVR
			  */
			  NetPVRPauseSupportType netPVRPauseSupported = channelDataTO.getNetPVRPauseSupported();
			 Integer pauseLiveTVTGapTimeStart = channelDataTO.getPauseLiveTVTGapTimeStart();
			 Integer pauseLiveTVTGapTimeEnd = channelDataTO.getPauseLiveTVTGapTimeEnd();
			 NetPVRStartOverSupportType netPVRStartOverSupported = channelDataTO.getNetPVRStartOverSupported();
			 Integer startOverGapTimeStart = channelDataTO.getStartOverGapTimeStart();
			 Integer startOverGapTimeEnd = channelDataTO.getStartOverGapTimeEnd();
			 String netPVRPreRollAssetId = channelDataTO.getNetPVRPreRollAssetId();
			 Integer netPVRFragmentSize = channelDataTO.getNetPVRFragmentSize();
			 Integer netPVRRecordTime = channelDataTO.getNetPVRRecordTime();
			 Integer startOverDefaultTimeStart = channelDataTO.getStartOverDefaultTimeStart();
			 Integer startOverDefaultTimeEnd = channelDataTO.getStartOverDefaultTimeEnd();
			 ChannelNetPVRTO netPVRTO = new ChannelNetPVRTO(netPVRPauseSupported,
					 pauseLiveTVTGapTimeStart, pauseLiveTVTGapTimeEnd,
					 netPVRStartOverSupported, startOverGapTimeStart,
					 startOverGapTimeEnd, netPVRPreRollAssetId,
					 netPVRFragmentSize, netPVRRecordTime,
					 startOverDefaultTimeStart, startOverDefaultTimeEnd);

			 /*
			  * PVR
			  */
			 boolean pvrSupported = channelDataTO.isPvrSupported();

			 /*
			  * Local PVR
			  */
			 Boolean localPVRSupported = channelDataTO.getLocalPVRSupported();

			 /*
			  * Categories
			  */
			 Collection<ContentCategoryTO> categoryTOs = null;
			 if(channelDataTO.getChannelCategoryTOs() != null) {
				 categoryTOs = new ArrayList<ContentCategoryTO>();
				 for(ChannelCategoryTO channelCategoryTO :
					 channelDataTO.getChannelCategoryTOs()) {
					 try {
						 ContentCategoryTO categoryTO =
								 categoryConfigFacadeDelegate.findContentCategoryByCategoryId(
										 channelCategoryTO.getCategoryId());
						 categoryTOs.add(categoryTO);
					 } catch (InstanceNotFoundException e) {
						 ;
					 }
				 }
			 }

			 /*
			  * PlayInfo
			  */
			  Collection<ChannelDataPlayInfoDetailsTO> playInfoDetailsTOs =
			  null;
			 if(channelDataTO.getChannelDataPlayInfoTOs() != null) {
				 playInfoDetailsTOs = new ArrayList<ChannelDataPlayInfoDetailsTO>();

				 for(ChannelDataPlayInfoTO channelDataPlayInfoTO :
					 channelDataTO.getChannelDataPlayInfoTOs()) {

					 DeviceTypeTO deviceTypeTO = null;
					 if(channelDataPlayInfoTO.getDeviceTypeId() != null) {
						 try {
							 deviceTypeTO = mappingFacadeDelegate.findDeviceTypeById(
									 channelDataPlayInfoTO.getDeviceTypeId());
						 } catch (InstanceNotFoundException e) {
							 ;
						 }
					 }

					 playInfoDetailsTOs.add(new ChannelDataPlayInfoDetailsTO(
							 channelDataPlayInfoTO.getChannelDataPlayInfoId(),
							 deviceTypeTO,
							 channelDataPlayInfoTO.getChannelUrl(),
							 channelDataPlayInfoTO.getAclChannelId(),
							 channelDataPlayInfoTO.getAclChannelProtocol(),
							 channelDataPlayInfoTO.getPreBufferMillis(),
							 channelDataPlayInfoTO.getNetPVRUrl(),
							 channelDataPlayInfoTO.getNetPVRChannelName(),
							 channelDataPlayInfoTO.getPreBufferNetPVRMillis(),
							 channelDataPlayInfoTO.getChannelVideoPidTOs(),
							 channelDataPlayInfoTO.getChannelAudioPidTOs(),
							 channelDataPlayInfoTO.getChannelSourceURIs(),
							 channelDataPlayInfoTO.getStreamName(),
							 channelDataPlayInfoTO.getVideoServerGroupsOrigin(),
							 channelDataPlayInfoTO.getVideoServerGroupsEdge()));
				 }
			 }

			 result.add(new ChannelDataDetailsTO(id, vodkatvChannelId,
					 channelType, name, description, logoUrl, languageCode,
					 countryCode, parentalRatingTO, active, metaserversTO, netPVRTO,
					 pvrSupported, localPVRSupported, accessType, categoryTOs,
					 playInfoDetailsTOs));

		 }

		 return result;
	}

	public static Collection<ChannelDataInfoTO> toChannelDataInfoTOs(
			Collection<ChannelDataTO> channelDataTOs)
					throws InternalErrorException {
		TeletextFacadeDelegate teletextFacadeDelegate =
				TeletextFacadeDelegateFactory.getDelegate();
		ChannelsFacadeDelegate channelsFacadeDelegate =
				ChannelsFacadeDelegateFactory.getDelegate(
						GlobalNames.TAHITI_DATA_SOURCE);

		CASConnectorFacadeDelegate casConnectorFacadeDelegate =
				CASConnectorFacadeDelegateFactory.getDelegate();
		ConfigurationFacadeDelegate configurationFacadeDelegate =
				ConfigurationFacadeDelegateFactory.getDelegate();


		/*
		 * Retrieve the complete list of channels
		 */
		 Collection<ChannelTO> channelTOs =
				 channelsFacadeDelegate.findAllChannels();
		 Map<String, ChannelTO> channelsMap =
				 new HashMap<String, ChannelTO>();
		 for(ChannelTO channelTO : channelTOs) {
			 channelsMap.put(channelTO.getChannelId(), channelTO);
		 }

		 /*
		  * Retrieve list of teletext channels
		  */
		 Collection<TextChannelTO> textChannelTOs =
				 teletextFacadeDelegate.findTextChannels();
		 Map<String, TextChannelTO> textChannelsMap =
				 new HashMap<String, TextChannelTO>();
		 for(TextChannelTO textChannelTO : textChannelTOs) {
			 textChannelsMap.put(textChannelTO.getId(), textChannelTO);
		 }

		 /*
		  * Find all channelDataTOs
		  */
		 Map<String, ChannelDataTO> channelDataMap =
				 new HashMap<String, ChannelDataTO>();
		 for(ChannelDataTO channelDataTO : channelDataTOs) {
			 channelDataMap.put(channelDataTO.getChannelId(), channelDataTO);
		 }

		 /*
		  * Find all CAS channels
		  */
		 Map<String, ChannelCASTO> channelCASMap =
				 new HashMap<String, ChannelCASTO>();
		 try {
			 Collection<ChannelCASTO> channelCASTOs =
					 casConnectorFacadeDelegate.findChannels();
			 for(ChannelCASTO channelCASTO : channelCASTOs) {
				 channelCASMap.put(channelCASTO.getChannelId(), channelCASTO);
			 }
		 }catch(Exception e){
			 logger.warn("CAS Connector reported an exception trying calling " +
					 "findChannels operation. " + e.getClass() + ": " +
					 e.getMessage());
		 }

		 /*
		  * Find all parental ratings
		  */
		 Map<Long, ParentalRatingTO> parentalRatingsMap =
				 new HashMap<Long, ParentalRatingTO>();
		 Collection<ParentalRatingTO> parentalRatingTOs =
				 configurationFacadeDelegate.findAllParentalRatings();
		 for(ParentalRatingTO parentalRatingTO : parentalRatingTOs) {
			 parentalRatingsMap.put(parentalRatingTO.getId(), parentalRatingTO);
		 }

		 /*
		  * Build result
		  */
		 Collection<ChannelDataInfoTO> channelDataDetailsTOs =
				 new ArrayList<ChannelDataInfoTO>();


		 for(ChannelDataTO channelDataTO : channelDataTOs) {

			 ChannelTO channelTO = (ChannelTO)channelsMap.get(
					 channelDataTO.getChannelId());

			 TextChannelTO textChannelTO = null;
			 if(channelDataTO.getTextChannelId() != null) {
				 textChannelTO = textChannelsMap.get(
						 channelDataTO.getTextChannelId());
			 }

			 ChannelCASTO channelCASTO = channelCASMap.get(
					 channelDataTO.getCasChannelId());

			 ParentalRatingTO parentalRatingTO = null;
			 if(channelDataTO.getParentalRatingId() != null) {
				 parentalRatingTO = parentalRatingsMap.get(
						 channelDataTO.getParentalRatingId());
			 }

			 channelDataDetailsTOs.add(new ChannelDataInfoTO(channelTO,
					 textChannelTO, channelDataTO, channelCASTO,
					 parentalRatingTO));
		 }

		 /*
		  * Return result
		  */
		  return channelDataDetailsTOs;
	}

	public static ChannelDataInfoTO toChannelDataInfoTO(ChannelDataTO channelDataTO)
			throws InternalErrorException {
		Collection<ChannelDataTO> channelDataTOs = new ArrayList<ChannelDataTO>();
		channelDataTOs.add(channelDataTO);
		return toChannelDataInfoTOs(channelDataTOs).iterator().next();
	}

	private static Map<String, TextChannelTO> getTextChannels()
			throws InternalErrorException {
		TeletextFacadeDelegate teletextFacadeDelegate =
				TeletextFacadeDelegateFactory.getDelegate();

		Collection<TextChannelTO> textChannelTOs =
				teletextFacadeDelegate.findTextChannels();

		Map<String, TextChannelTO> result = new HashMap<String, TextChannelTO>();
		for(TextChannelTO textChannelTO : textChannelTOs) {
			result.put(textChannelTO.getId(), textChannelTO);
		}

		return result;
	}

	private static Map<String, ChannelCASTO> getCASChannels()
			throws InternalErrorException {
		CASConnectorFacadeDelegate casConnectorFacadeDelegate =
				CASConnectorFacadeDelegateFactory.getDelegate();

		Collection<ChannelCASTO> casChannelTOs =
				casConnectorFacadeDelegate.findChannels();

		Map<String, ChannelCASTO> result = new HashMap<String, ChannelCASTO>();
		for(ChannelCASTO casChannelTO : casChannelTOs) {
			result.put(casChannelTO.getChannelId(), casChannelTO);
		}

		return result;
	}

}
