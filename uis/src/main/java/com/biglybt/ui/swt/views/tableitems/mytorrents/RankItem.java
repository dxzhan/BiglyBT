/*
 * File    : RankItem.java
 * Created : 24 nov. 2003
 * By      : Olivier
 *
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details ( see the LICENSE file ).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.biglybt.ui.swt.views.tableitems.mytorrents;

import com.biglybt.core.Core;
import com.biglybt.core.CoreFactory;
import com.biglybt.core.CoreRunningListener;
import com.biglybt.core.config.COConfigurationManager;
import com.biglybt.core.config.ParameterListener;
import com.biglybt.core.download.DownloadManager;
import com.biglybt.core.download.DownloadManagerListener;
import com.biglybt.core.global.GlobalManager;
import com.biglybt.core.global.GlobalManagerListener;
import com.biglybt.core.util.Debug;
import com.biglybt.pif.download.Download;
import com.biglybt.pif.ui.menus.MenuItem;
import com.biglybt.pif.ui.menus.MenuItemFillListener;
import com.biglybt.pif.ui.menus.MenuItemListener;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.pif.ui.tables.TableContextMenuItem;
import com.biglybt.ui.swt.imageloader.ImageLoader;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;
import com.biglybt.ui.swt.views.table.TableCellSWT;
import org.eclipse.swt.graphics.Image;

/**
 * Torrent Position column.
 * <p>
 * One object for all rows to save memory
 *
 * @author Olivier
 * @author TuxPaper (2004/Apr/17: modified to TableCellAdapter)
 */
public class RankItem
	extends CoreTableColumnSWT
	implements TableCellRefreshListener {
	public static final Class DATASOURCE_TYPE = Download.class;

	public static final String COLUMN_ID = "#";
	private final ParameterListener paramShowIconKeyListener;

	private String showIconKey;
	private boolean showIcon;
	private Image imgUp;
	private Image imgDown;
	private GMListener gmListener;
	private boolean bInvalidByTrigger = false;

	/**
	 * Default Constructor
	 */
	public RankItem(String sTableID) {
		super(DATASOURCE_TYPE, COLUMN_ID, ALIGN_TRAIL, 50, sTableID);
		setRefreshInterval(INTERVAL_INVALID_ONLY);
		CoreFactory.addCoreRunningListener(new CoreRunningListener() {
			@Override
			public void coreRunning(Core core) {
				gmListener = new GMListener();
				core.getGlobalManager().addListener(gmListener);
			}
		});

		showIconKey = "RankColumn.showUpDownIcon." + (sTableID.endsWith(".big") ? "big" : "small");

		TableContextMenuItem menuShowIcon = addContextMenuItem(
			"ConfigView.section.style.showRankIcon", MENU_STYLE_HEADER);
		menuShowIcon.setStyle(TableContextMenuItem.STYLE_CHECK);
		menuShowIcon.addFillListener(new MenuItemFillListener() {
			@Override
			public void menuWillBeShown(MenuItem menu, Object data) {
				menu.setData(Boolean.valueOf(showIcon));
			}
		});

		menuShowIcon.addMultiListener(new MenuItemListener() {
			@Override
			public void selected(MenuItem menu, Object target) {
				COConfigurationManager.setParameter(showIconKey,
					((Boolean) menu.getData()).booleanValue());
			}
		});

		paramShowIconKeyListener = new ParameterListener() {
			@Override
			public void parameterChanged(String parameterName) {
				RankItem.this.invalidateCells();
				showIcon = (COConfigurationManager.getBooleanParameter(showIconKey));
			}
		};
		COConfigurationManager.addWeakParameterListener(paramShowIconKeyListener,
			true, showIconKey);

		ImageLoader imageLoader = ImageLoader.getInstance();
		imgUp = imageLoader.getImage("image.torrentspeed.up");
		imgDown = imageLoader.getImage("image.torrentspeed.down");
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{CAT_CONTENT});
	}

	@Override
	public void reset() {
		super.reset();

		COConfigurationManager.removeParameter(showIconKey);
	}

	@Override
	public void
	remove() {
		super.remove();

		if (gmListener != null) {
			try {
				CoreFactory.getSingleton().getGlobalManager().removeListener(gmListener);
			} catch (Throwable ignore) {
			}
			gmListener = null;
		}
		COConfigurationManager.removeWeakParameterListener(paramShowIconKeyListener,
			showIconKey);
	}

	@Override
	public void refresh(TableCell cell) {
		bInvalidByTrigger = false;

		DownloadManager dm = (DownloadManager) cell.getDataSource();
		long value = (dm == null) ? 0 : dm.getPosition();
		String text = "" + value;

		boolean complete = dm == null ? false : dm.getAssumedComplete();
		if (complete) {
			value += 0x10000;
		}

		cell.setSortValue(value);
		cell.setText(text);

		if (cell instanceof TableCellSWT) {
			if (showIcon && dm != null) {
				Image img = dm.getAssumedComplete() ? imgUp : imgDown;
				((TableCellSWT) cell).setIcon(img);
			} else {
				((TableCellSWT) cell).setIcon(null);
			}
		}
	}

	private class GMListener implements GlobalManagerListener {
		DownloadManagerListener listener;

		public GMListener() {
			listener = new DownloadManagerListener() {
				@Override
				public void completionChanged(DownloadManager manager, boolean bCompleted) {
				}

				@Override
				public void downloadComplete(DownloadManager manager) {
				}

				@Override
				public void positionChanged(DownloadManager download, int oldPosition, int newPosition) {
					/** We will be getting multiple position changes, but we only need
					 * to invalidate cells once.
					 */
					if (bInvalidByTrigger)
						return;
					RankItem.this.invalidateCells();
					bInvalidByTrigger = true;
				}

				@Override
				public void stateChanged(DownloadManager manager, int state) {
				}

				@Override
				public void
				filePriorityChanged(DownloadManager download, com.biglybt.core.disk.DiskManagerFileInfo file) {
				}
			};
		}

		@Override
		public void destroyed() {
		}

		@Override
		public void destroyInitiated() {
			try {
				GlobalManager gm = CoreFactory.getSingleton().getGlobalManager();
				gm.removeListener(this);
			} catch (Exception e) {
				Debug.out(e);
			}
		}

		@Override
		public void downloadManagerAdded(DownloadManager dm) {
			dm.addListener(listener);
		}

		@Override
		public void downloadManagerRemoved(DownloadManager dm) {
			dm.removeListener(listener);
		}

		@Override
		public void seedingStatusChanged(boolean seeding_only_mode, boolean b) {
		}
	}

}
