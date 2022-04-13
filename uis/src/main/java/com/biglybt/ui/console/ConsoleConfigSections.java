/*
 * Copyright (C) Bigly Software.  All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.biglybt.ui.console;

import com.biglybt.pif.ui.config.Parameter;
import com.biglybt.pifimpl.local.ui.config.ConfigSectionRepository;
import com.biglybt.pifimpl.local.ui.config.ParameterImpl;
import com.biglybt.ui.config.BaseConfigSection;
import com.biglybt.ui.config.ConfigSectionBackupRestore;
import com.biglybt.ui.config.ConfigSectionConnection;
import com.biglybt.ui.config.ConfigSectionConnectionAdvanced;
import com.biglybt.ui.config.ConfigSectionConnectionDNS;
import com.biglybt.ui.config.ConfigSectionConnectionEncryption;
import com.biglybt.ui.config.ConfigSectionConnectionProxy;
import com.biglybt.ui.config.ConfigSectionFile;
import com.biglybt.ui.config.ConfigSectionFileMove;
import com.biglybt.ui.config.ConfigSectionFilePerformance;
import com.biglybt.ui.config.ConfigSectionFileTorrents;
import com.biglybt.ui.config.ConfigSectionFileTorrentsDecoding;
import com.biglybt.ui.config.ConfigSectionIPFilter;
import com.biglybt.ui.config.ConfigSectionInterfaceLanguage;
import com.biglybt.ui.config.ConfigSectionInterfaceTags;
import com.biglybt.ui.config.ConfigSectionLogging;
import com.biglybt.ui.config.ConfigSectionMode;
import com.biglybt.ui.config.ConfigSectionPlugins;
import com.biglybt.ui.config.ConfigSectionSecurity;
import com.biglybt.ui.config.ConfigSectionSharing;
import com.biglybt.ui.config.ConfigSectionStartShutdown;
import com.biglybt.ui.config.ConfigSectionStats;
import com.biglybt.ui.config.ConfigSectionTracker;
import com.biglybt.ui.config.ConfigSectionTrackerClient;
import com.biglybt.ui.config.ConfigSectionTrackerServer;
import com.biglybt.ui.config.ConfigSectionTransfer;
import com.biglybt.ui.config.ConfigSectionTransferAutoSpeedClassic;
import com.biglybt.ui.config.ConfigSectionTransferAutoSpeedSelect;
import com.biglybt.ui.config.ConfigSectionTransferAutoSpeedV2;
import com.biglybt.ui.config.ConfigSectionTransferLAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleConfigSections {
	private static ConsoleConfigSections instance;
	private final BaseConfigSection[] internalSections;

	public ConsoleConfigSections() {
		internalSections = new BaseConfigSection[]{
			new ConfigSectionMode(),
			new ConfigSectionStartShutdown(),
			new ConfigSectionBackupRestore(),
			new ConfigSectionConnection(),
			new ConfigSectionConnectionProxy(),
			new ConfigSectionConnectionAdvanced(),
			new ConfigSectionConnectionEncryption(),
			new ConfigSectionConnectionDNS(),
			new ConfigSectionTransfer(),
			new ConfigSectionTransferAutoSpeedSelect(),
			new ConfigSectionTransferAutoSpeedClassic(),
			new ConfigSectionTransferAutoSpeedV2(),
			new ConfigSectionTransferLAN(),
			new ConfigSectionFile(),
			new ConfigSectionFileMove(),
			new ConfigSectionFileTorrents(),
			new ConfigSectionFileTorrentsDecoding(),
			new ConfigSectionFilePerformance(),
//		  new ConfigSectionInterfaceSWT(),
			new ConfigSectionInterfaceLanguage(),
			new ConfigSectionInterfaceTags(),
//			  new ConfigSectionInterfaceStartSWT(),
//			  new ConfigSectionInterfaceDisplaySWT(),
//			  new ConfigSectionInterfaceTablesSWT(),
//			  new ConfigSectionInterfaceColorSWT(),
//			  new ConfigSectionInterfaceAlertsSWT(),
//			  new ConfigSectionInterfacePasswordSWT(),
//			  new ConfigSectionInterfaceLegacySWT(),
			new ConfigSectionIPFilter(),
			new ConfigSectionPlugins(),
			new ConfigSectionStats(),
			new ConfigSectionTracker(),
			new ConfigSectionTrackerClient(),
			new ConfigSectionTrackerServer(),
			new ConfigSectionSecurity(),
			new ConfigSectionSharing(),
			new ConfigSectionLogging()
		};
	}

	public static ConsoleConfigSections getInstance() {
		synchronized (ConsoleConfigSections.class) {
			if (instance == null) {
				instance = new ConsoleConfigSections();
			}
		}
		return instance;
	}

	public List<BaseConfigSection> getAllConfigSections(boolean sort) {
		List<BaseConfigSection> repoList = ConfigSectionRepository.getInstance().getList();
		if (!sort) {
			repoList.addAll(0, Arrays.asList(internalSections));
			return repoList;
		}

		ArrayList<BaseConfigSection> configSections = new ArrayList<>(
			Arrays.asList(internalSections));
		// Internal Sections are in the order we want them.
		// place ones from repository at the bottom of correct parent
		for (BaseConfigSection repoConfigSection : repoList) {
			String repoParentID = repoConfigSection.getParentSectionID();

			int size = configSections.size();
			int insertAt = size;
			for (int i = 0; i < size; i++) {
				BaseConfigSection configSection = configSections.get(i);
				if (insertAt == i) {
					if (!repoParentID.equals(configSection.getParentSectionID())) {
						break;
					}
					insertAt++;
				} else if (configSection.getConfigSectionID().equals(repoParentID)) {
					insertAt = i + 1;
				}
			}
			if (insertAt >= size) {
				configSections.add(repoConfigSection);
			} else {
				configSections.add(insertAt, repoConfigSection);
			}
		}

		return configSections;
	}

	public ParameterWithConfigSection getParameter(String configKey) {
		List<BaseConfigSection> sections = getAllConfigSections(false);
		for (BaseConfigSection section : sections) {
			boolean needsBuild = !section.isBuilt();
			try {
				if (needsBuild) {
					section.build();
					section.postBuild();
				}

				ParameterImpl pluginParam = section.getPluginParam(configKey);
				if (pluginParam != null) {
					return new ParameterWithConfigSection(section, pluginParam);
				}
			} finally {
				if (needsBuild) {
					section.deleteConfigSection();
				}
			}
		}
		return null;
	}

	public static final class ParameterWithConfigSection {
		public BaseConfigSection configSection;

		public Parameter parameter;

		public ParameterWithConfigSection(BaseConfigSection configSection,
																			Parameter parameter) {
			this.configSection = configSection;
			this.parameter = parameter;
		}
	}
}
