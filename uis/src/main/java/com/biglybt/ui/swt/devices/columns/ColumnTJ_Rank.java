/*
 * Created on Feb 26, 2009
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package com.biglybt.ui.swt.devices.columns;

import com.biglybt.core.devices.TranscodeFile;
import com.biglybt.core.devices.TranscodeJob;
import com.biglybt.pif.ui.tables.TableCell;
import com.biglybt.pif.ui.tables.TableCellRefreshListener;
import com.biglybt.pif.ui.tables.TableColumn;
import com.biglybt.pif.ui.tables.TableColumnExtraInfoListener;
import com.biglybt.pif.ui.tables.TableColumnInfo;

/**
 * @author TuxPaper
 * @created Feb 26, 2009
 */
public class ColumnTJ_Rank
	implements TableCellRefreshListener, TableColumnExtraInfoListener {
	public static final String COLUMN_ID = "trancode_qpos";

	public ColumnTJ_Rank(TableColumn column) {
		column.initialize(TableColumn.ALIGN_TRAIL, TableColumn.POSITION_LAST, 25);
		column.addListeners(this);
		column.setRefreshInterval(TableColumn.INTERVAL_GRAPHIC);
		column.setType(TableColumn.TYPE_TEXT_ONLY);
	}

	@Override
	public void fillTableColumnInfo(TableColumnInfo info) {
		info.addCategories(new String[]{
			TableColumn.CAT_ESSENTIAL,
		});
		info.setProficiency(TableColumnInfo.PROFICIENCY_BEGINNER);
	}

	// @see com.biglybt.pif.ui.tables.TableCellRefreshListener#refresh(com.biglybt.pif.ui.tables.TableCell)
	@Override
	public void refresh(TableCell cell) {
		TranscodeFile tf = (TranscodeFile) cell.getDataSource();
		if (tf == null) {
			return;
		}
		TranscodeJob job = tf.getJob();

		long value;
		if (job == null) {
			try {
				value = Integer.MAX_VALUE + tf.getCreationDateMillis() + 1;
			} catch (Throwable t) {
				value = Integer.MAX_VALUE + 1L;
			}
		} else {
			value = job.getIndex();
		}
		if (cell.setSortValue(value) || !cell.isValid()) {
			if (value > Integer.MAX_VALUE) {
				cell.setText("");
			} else {
				cell.setText("" + value);
			}
		}
	}

}
