/*
 * Created on 04/12/2004
 * Created by Paul Duran
 * Copyright (C) 2004 Aelitis, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * AELITIS, SARL au capital de 30,000 euros
 * 8 Allee Lenotre, La Grille Royale, 78600 Le Mesnil le Roi, France.
 *
 */
package com.biglybt.ui.console.commands;

import com.biglybt.core.download.DownloadManager;
import com.biglybt.ui.console.ConsoleInput;

import java.util.List;

/**
 * console command to queue a torrent.
 * extracted from the Torrent class written by tobias
 */
public class TorrentQueue extends TorrentCommand {

	public TorrentQueue() {
		super("queue", "q", "Queueing");
	}

	@Override
	protected boolean performCommand(ConsoleInput ci, DownloadManager dm, List args) {
		try {
			if (dm.getState() == DownloadManager.STATE_STOPPED)
				dm.setStateQueued();
			else if (dm.getState() == DownloadManager.STATE_DOWNLOADING || dm.getState() == DownloadManager.STATE_SEEDING)
				dm.stopIt(DownloadManager.STATE_QUEUED, false, false);
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace(ci.out);
			return false;
		}
		return true;
	}

	@Override
	public String getCommandDescriptions() {
		return ("queue (<torrentoptions>)\tq\tQueue torrent(s).");
	}

}
