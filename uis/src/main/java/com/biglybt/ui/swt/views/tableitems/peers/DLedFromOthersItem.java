/*
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

import com.biglybt.core.peer.PEPeer;
import com.biglybt.core.util.DisplayFormatters;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;
import com.biglybt.ui.swt.views.table.CoreTableColumnSWT;

/**
 * Downloaded from others WHILE connected to you
 *
 * @author TuxPaper
 * @since 2.1.0.1
 */
public class DLedFromOthersItem
	extends CoreTableColumnSWT
	implements TableCellRefreshListener {
	public static final String COLUMN_ID = "DLedFromOthers";

	/**
	 * Default Constructor
	 */
	public DLedFromOthersItem(String table_id) {
		super(COLUMN_ID, ALIGN_TRAIL, POSITION_INVISIBLE, 70, table_id);
		setRefreshInterval(INTERVAL_LIVE);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{
			CAT_BYTES,
		});
	}

	@Override
	public void refresh(TableCell cell) {
		PEPeer peer = (PEPeer) cell.getDataSource();
		long value = (peer == null) ? 0 : peer.getStats().getTotalBytesDownloadedByPeer() - peer.getStats().getTotalDataBytesSent();
		// Just because we sent data doesn't mean the peer has told us the piece is done yet
		if (value < 0) value = 0;

		if (peer != null) {
			Long prev_value = (Long) peer.getData("DLedFromOther_prev");

			if (prev_value != null) {
				if (value < prev_value.longValue()) {  //dont show decrement while we're actively uploading
					value = prev_value.longValue();
				} else if (value > prev_value.longValue()) {
					peer.setData("DLedFromOther_prev", new Long(value));
				}
			} else {
				peer.setData("DLedFromOther_prev", new Long(value));
			}
		}

		if (!cell.setSortValue(value) && cell.isValid())
			return;

		cell.setText(DisplayFormatters.formatByteCountToKiBEtc(value));
	}
}
