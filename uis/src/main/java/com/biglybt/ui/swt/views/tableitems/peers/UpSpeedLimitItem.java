/*
 * Created on 8 oct. 2004
 * Created by Olivier Chalouhi
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
package com.biglybt.ui.swt.views.tableitems.peers;

import com.biglybt.common.constant.Constants;
import com.biglybt.core.internat.MessageText;
import com.biglybt.core.peer.PEPeer;
import com.biglybt.core.util.DisplayFormatters;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;

/**
 * @author Olivier Chalouhi
 */
public class UpSpeedLimitItem
	extends CoreTableColumnSWT
	implements TableCellRefreshListener {
	public static final String COLUMN_ID = "maxupspeed";

	/**
	 * Default Constructor
	 */
	public UpSpeedLimitItem(String table_id) {
		super(COLUMN_ID, ALIGN_TRAIL, POSITION_INVISIBLE, 35, table_id);
		setRefreshInterval(INTERVAL_LIVE);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{
			CAT_SETTINGS,
		});
	}

	@Override
	public void refresh(TableCell cell) {
		PEPeer peer = (PEPeer) cell.getDataSource();
		long value = (peer == null) ? 0 : peer.getUploadRateLimitBytesPerSecond();
		if (!cell.setSortValue(value) && cell.isValid())
			return;

		if (value == -1) {
			cell.setText(MessageText.getString("MyTorrents.items.UpSpeedLimit.disabled"));
		} else if (value == 0) {
			cell.setText(Constants.INFINITY_STRING);
		} else {
			cell.setText(DisplayFormatters.formatByteCountToKiBEtcPerSec(value));
		}
	}
}
